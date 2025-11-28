package net.multiverse.dynamicheight.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;

import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.dimension.DimensionType;

/**
 * Reflective helpers for re-binding dimension types inside Mojang's registries.
 */
public final class DimensionTypeUtil {
    private static final Method HOLDER_REFERENCE_BIND_METHOD = locateHolderReferenceBindMethod();
    private static final Field MAPPED_REGISTRY_VALUE_TO_ID_FIELD = locateMappedRegistryValueToIdField();

    private DimensionTypeUtil() {
    }

    public static DimensionType copyWithHeight(DimensionType base, int minY, int maxY) {
        int height = maxY - minY;
        return new DimensionType(
                base.fixedTime(),
                base.hasSkyLight(),
                base.hasCeiling(),
                base.ultraWarm(),
                base.natural(),
                base.coordinateScale(),
                base.bedWorks(),
                base.respawnAnchorWorks(),
                minY,
                height,
                Math.max(16, height),
                base.infiniburn(),
                base.effectsLocation(),
                base.ambientLight(),
                base.monsterSettings()
        );
    }

    public static Holder<DimensionType> bindUpdatedDimensionType(RegistryAccess registryAccess, Holder<DimensionType> original, DimensionType updated) {
        Registry<DimensionType> registry = registryAccess.registryOrThrow(Registries.DIMENSION_TYPE);
        Holder.Reference<DimensionType> targetReference = null;
        if (original instanceof Holder.Reference<DimensionType> reference) {
            targetReference = reference;
        } else {
            Optional<net.minecraft.resources.ResourceKey<DimensionType>> key = original.unwrapKey();
            if (key.isPresent()) {
                if (registry instanceof MappedRegistry<DimensionType> mapped) {
                    HolderGetter<DimensionType> lookup = mapped.createRegistrationLookup();
                    targetReference = lookup.get(key.get()).orElse(null);
                }
            }
        }
        if (targetReference != null) {
            DimensionType previous = targetReference.value();
            bindHolderReference(targetReference, updated);
            if (registry instanceof MappedRegistry<DimensionType> mapped) {
                updateMappedRegistryEntry(mapped, previous, updated, targetReference);
            }
            return targetReference;
        }
        return Holder.direct(updated);
    }

    private static <T> void bindHolderReference(Holder.Reference<T> reference, T value) {
        try {
            HOLDER_REFERENCE_BIND_METHOD.invoke(reference, value);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to bind updated registry value reflectively", e);
        }
    }

    private static Method locateHolderReferenceBindMethod() {
        for (Method method : Holder.Reference.class.getDeclaredMethods()) {
            if (!Modifier.isStatic(method.getModifiers())
                    && method.getReturnType() == void.class
                    && method.getParameterCount() == 1
                    && method.getParameterTypes()[0] == Object.class) {
                method.setAccessible(true);
                return method;
            }
        }
        throw new IllegalStateException("Unable to locate Holder.Reference#bindValue equivalent");
    }

    private static Field locateMappedRegistryValueToIdField() {
        Class<?> current = MappedRegistry.class;
        while (current != null && current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                if (Reference2IntMap.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    return field;
                }
            }
            current = current.getSuperclass();
        }
        throw new IllegalStateException("Unable to locate MappedRegistry value-id map field");
    }

    private static <T> void updateMappedRegistryEntry(MappedRegistry<T> registry, T previous, T updated, Holder.Reference<T> reference) {
        if (previous == updated) {
            return;
        }
        Map<T, Holder.Reference<T>> valueMap = findValueToHolderMap(registry, previous);
        if (valueMap != null) {
            valueMap.remove(previous);
            valueMap.put(updated, reference);
        }
        rebuildIdMap(registry, previous, updated);
    }

    private static <T> Map<T, Holder.Reference<T>> findValueToHolderMap(MappedRegistry<T> registry, T probe) {
        Class<?> current = registry.getClass();
        while (current != null && current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                if (!Map.class.isAssignableFrom(field.getType())) {
                    continue;
                }
                field.setAccessible(true);
                try {
                    Object raw = field.get(registry);
                    if (raw instanceof IdentityHashMap<?, ?> identity) {
                        @SuppressWarnings("unchecked")
                        Map<T, Holder.Reference<T>> map = (Map<T, Holder.Reference<T>>) identity;
                        if (map.containsKey(probe)) {
                            return map;
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException("Failed to inspect registry value map reflectively", e);
                }
            }
            current = current.getSuperclass();
        }
        return null;
    }

    private static <T> void rebuildIdMap(MappedRegistry<T> registry, T previous, T updated) {
        try {
            @SuppressWarnings("unchecked")
            Reference2IntMap<T> original = (Reference2IntMap<T>) MAPPED_REGISTRY_VALUE_TO_ID_FIELD.get(registry);
            if (original == null || original.isEmpty()) {
                return;
            }
            Reference2IntOpenHashMap<T> rebuilt = new Reference2IntOpenHashMap<>(original.size());
            rebuilt.defaultReturnValue(original.defaultReturnValue());
            for (Entry<T> entry : original.reference2IntEntrySet()) {
                T key = entry.getKey();
                int value = entry.getIntValue();
                if (key == previous) {
                    key = updated;
                }
                rebuilt.put(key, value);
            }
            MAPPED_REGISTRY_VALUE_TO_ID_FIELD.set(registry, rebuilt);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to rebuild registry id map reflectively", e);
        }
    }
}
