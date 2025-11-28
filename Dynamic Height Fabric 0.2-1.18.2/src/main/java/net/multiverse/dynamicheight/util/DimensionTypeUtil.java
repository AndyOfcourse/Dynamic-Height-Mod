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
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.dimension.DimensionType;
import net.multiverse.dynamicheight.mixin.DimensionTypeAccessor;

/**
 * Reflective helpers for re-binding dimension types inside Mojang's registries.
 */
public final class DimensionTypeUtil {
    private static final Method HOLDER_REFERENCE_BIND_METHOD = locateHolderReferenceBindMethod();
    private static final Class<?> HOLDER_REFERENCE_BIND_PARAM =
            HOLDER_REFERENCE_BIND_METHOD != null ? HOLDER_REFERENCE_BIND_METHOD.getParameterTypes()[0] : null;
    private static final Field HOLDER_REFERENCE_VALUE_FIELD = locateHolderReferenceValueField();
    @SuppressWarnings("ConstantConditions")
    private static final Field MAPPED_REGISTRY_VALUE_TO_ID_FIELD = locateMappedRegistryValueToIdField();

    private DimensionTypeUtil() {
    }

    public static DimensionType copyWithHeight(DimensionType base, int minY, int maxY) {
        int height = maxY - minY;
        DimensionTypeAccessor accessor = (DimensionTypeAccessor) (Object) base;
        accessor.dynamicheight$setMinY(minY);
        accessor.dynamicheight$setHeight(height);
        accessor.dynamicheight$setLogicalHeight(Math.max(16, height));
        return base;
    }

    public static Holder<DimensionType> bindUpdatedDimensionType(RegistryAccess registryAccess, Holder<DimensionType> original, DimensionType updated) {
        Registry<DimensionType> registry = registryAccess.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
        Holder.Reference<DimensionType> targetReference = null;
        if (original instanceof Holder.Reference) {
            @SuppressWarnings("unchecked")
            Holder.Reference<DimensionType> reference = (Holder.Reference<DimensionType>) original;
            targetReference = reference;
        } else {
            Optional<net.minecraft.resources.ResourceKey<DimensionType>> key = original.unwrapKey();
            if (key.isPresent()) {
                Optional<Holder<DimensionType>> lookup = registry.getHolder(key.get());
                if (lookup.isPresent() && lookup.get() instanceof Holder.Reference) {
                    @SuppressWarnings("unchecked")
                    Holder.Reference<DimensionType> ref = (Holder.Reference<DimensionType>) lookup.get();
                    targetReference = ref;
                }
            }
        }
        if (targetReference != null) {
            DimensionType previous = targetReference.value();
            if (previous == updated) {
                return targetReference;
            }
            bindHolderReference(targetReference, updated);
            if (registry instanceof MappedRegistry) {
                @SuppressWarnings("unchecked")
                MappedRegistry<DimensionType> mapped = (MappedRegistry<DimensionType>) registry;
                updateMappedRegistryEntry(mapped, previous, updated, targetReference);
            }
            return targetReference;
        }
        return Holder.direct(updated);
    }

    private static <T> void bindHolderReference(Holder.Reference<T> reference, T value) {
        if (HOLDER_REFERENCE_BIND_METHOD != null && HOLDER_REFERENCE_BIND_PARAM != null) {
            try {
                if (value == null || HOLDER_REFERENCE_BIND_PARAM.isInstance(value)
                        || (!HOLDER_REFERENCE_BIND_PARAM.isPrimitive()
                        && HOLDER_REFERENCE_BIND_PARAM.isAssignableFrom(value.getClass()))) {
                    HOLDER_REFERENCE_BIND_METHOD.invoke(reference, value);
                    return;
                }
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException("Failed to bind updated registry value reflectively", e);
            }
        }
        if (HOLDER_REFERENCE_VALUE_FIELD != null) {
            try {
                HOLDER_REFERENCE_VALUE_FIELD.set(reference, value);
                return;
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException("Failed to assign updated registry value reflectively", e);
            }
        }
        throw new IllegalStateException("Unable to assign updated registry value");
    }

    private static Method locateHolderReferenceBindMethod() {
        for (Method method : Holder.Reference.class.getDeclaredMethods()) {
            if (!Modifier.isStatic(method.getModifiers()) && method.getParameterCount() == 1) {
                method.setAccessible(true);
                return method;
            }
        }
        return null;
    }

    private static Field locateHolderReferenceValueField() {
        for (Field field : Holder.Reference.class.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if (field.getType() == Object.class) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
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
        return null;
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
                    if (raw instanceof IdentityHashMap) {
                        IdentityHashMap<?, ?> identity = (IdentityHashMap<?, ?>) raw;
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
        if (MAPPED_REGISTRY_VALUE_TO_ID_FIELD == null) {
            return;
        }
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
