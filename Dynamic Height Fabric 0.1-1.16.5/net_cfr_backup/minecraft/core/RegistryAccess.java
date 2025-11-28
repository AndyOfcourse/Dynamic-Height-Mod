/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.Encoder
 *  com.mojang.serialization.JsonOps
 *  net.minecraft.Util
 *  net.minecraft.core.MappedRegistry
 *  net.minecraft.core.Registry
 *  net.minecraft.core.RegistryAccess$RegistryData
 *  net.minecraft.core.RegistryAccess$RegistryHolder
 *  net.minecraft.core.WritableRegistry
 *  net.minecraft.data.BuiltinRegistries
 *  net.minecraft.resources.RegistryReadOps
 *  net.minecraft.resources.RegistryReadOps$ResourceAccess
 *  net.minecraft.resources.RegistryReadOps$ResourceAccess$MemoryMap
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.dimension.DimensionType
 *  net.minecraft.world.level.levelgen.NoiseGeneratorSettings
 *  net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver
 *  net.minecraft.world.level.levelgen.feature.ConfiguredFeature
 *  net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature
 *  net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType
 *  net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.core;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import java.util.Map;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.RegistryReadOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Exception performing whole class analysis ignored.
 */
public abstract class RegistryAccess {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<ResourceKey<? extends Registry<?>>, RegistryData<?>> REGISTRIES = (Map)Util.make(() -> {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        RegistryAccess.put(builder, Registry.DIMENSION_TYPE_REGISTRY, DimensionType.DIRECT_CODEC, DimensionType.DIRECT_CODEC);
        RegistryAccess.put(builder, Registry.BIOME_REGISTRY, Biome.DIRECT_CODEC, Biome.NETWORK_CODEC);
        RegistryAccess.put(builder, Registry.CONFIGURED_SURFACE_BUILDER_REGISTRY, ConfiguredSurfaceBuilder.DIRECT_CODEC);
        RegistryAccess.put(builder, Registry.CONFIGURED_CARVER_REGISTRY, ConfiguredWorldCarver.DIRECT_CODEC);
        RegistryAccess.put(builder, Registry.CONFIGURED_FEATURE_REGISTRY, ConfiguredFeature.DIRECT_CODEC);
        RegistryAccess.put(builder, Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, ConfiguredStructureFeature.DIRECT_CODEC);
        RegistryAccess.put(builder, Registry.PROCESSOR_LIST_REGISTRY, StructureProcessorType.DIRECT_CODEC);
        RegistryAccess.put(builder, Registry.TEMPLATE_POOL_REGISTRY, StructureTemplatePool.DIRECT_CODEC);
        RegistryAccess.put(builder, Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, NoiseGeneratorSettings.DIRECT_CODEC);
        return builder.build();
    });
    private static final RegistryHolder BUILTIN = (RegistryHolder)Util.make(() -> {
        RegistryHolder registryHolder = new RegistryHolder();
        DimensionType.registerBuiltin((RegistryHolder)registryHolder);
        REGISTRIES.keySet().stream().filter(resourceKey -> !resourceKey.equals(Registry.DIMENSION_TYPE_REGISTRY)).forEach(resourceKey -> RegistryAccess.copyBuiltin(registryHolder, resourceKey));
        return registryHolder;
    });

    public abstract <E> Optional<WritableRegistry<E>> registry(ResourceKey<? extends Registry<E>> var1);

    public <E> WritableRegistry<E> registryOrThrow(ResourceKey<? extends Registry<E>> resourceKey) {
        return this.registry(resourceKey).orElseThrow(() -> new IllegalStateException("Missing registry: " + resourceKey));
    }

    public Registry<DimensionType> dimensionTypes() {
        return this.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
    }

    private static <E> void put(ImmutableMap.Builder<ResourceKey<? extends Registry<?>>, RegistryData<?>> builder, ResourceKey<? extends Registry<E>> resourceKey, Codec<E> codec) {
        builder.put(resourceKey, (Object)new RegistryData(resourceKey, codec, null));
    }

    private static <E> void put(ImmutableMap.Builder<ResourceKey<? extends Registry<?>>, RegistryData<?>> builder, ResourceKey<? extends Registry<E>> resourceKey, Codec<E> codec, Codec<E> codec2) {
        builder.put(resourceKey, (Object)new RegistryData(resourceKey, codec, codec2));
    }

    public static RegistryHolder builtin() {
        RegistryHolder registryHolder = new RegistryHolder();
        RegistryReadOps.ResourceAccess.MemoryMap memoryMap = new RegistryReadOps.ResourceAccess.MemoryMap();
        for (RegistryData<?> registryData : REGISTRIES.values()) {
            RegistryAccess.addBuiltinElements(registryHolder, memoryMap, registryData);
        }
        RegistryReadOps.create((DynamicOps)JsonOps.INSTANCE, (RegistryReadOps.ResourceAccess)memoryMap, (RegistryHolder)registryHolder);
        return registryHolder;
    }

    private static <E> void addBuiltinElements(RegistryHolder registryHolder, RegistryReadOps.ResourceAccess.MemoryMap memoryMap, RegistryData<E> registryData) {
        ResourceKey resourceKey = registryData.key();
        boolean bl = !resourceKey.equals(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY) && !resourceKey.equals(Registry.DIMENSION_TYPE_REGISTRY);
        WritableRegistry registry = BUILTIN.registryOrThrow(resourceKey);
        WritableRegistry writableRegistry = registryHolder.registryOrThrow(resourceKey);
        for (Map.Entry entry : registry.entrySet()) {
            Object object = entry.getValue();
            if (bl) {
                memoryMap.add(BUILTIN, (ResourceKey)entry.getKey(), (Encoder)registryData.codec(), registry.getId(object), object, registry.lifecycle(object));
                continue;
            }
            writableRegistry.registerMapping(registry.getId(object), (ResourceKey)entry.getKey(), object, registry.lifecycle(object));
        }
    }

    private static <R extends Registry<?>> void copyBuiltin(RegistryHolder registryHolder, ResourceKey<R> resourceKey) {
        Registry registry = BuiltinRegistries.REGISTRY;
        Registry registry2 = (Registry)registry.get(resourceKey);
        if (registry2 == null) {
            throw new IllegalStateException("Missing builtin registry: " + resourceKey);
        }
        RegistryAccess.copy(registryHolder, registry2);
    }

    private static <E> void copy(RegistryHolder registryHolder, Registry<E> registry) {
        WritableRegistry writableRegistry = (WritableRegistry)registryHolder.registry(registry.key()).orElseThrow(() -> new IllegalStateException("Missing registry: " + registry.key()));
        for (Map.Entry entry : registry.entrySet()) {
            Object object = entry.getValue();
            writableRegistry.registerMapping(registry.getId(object), (ResourceKey)entry.getKey(), object, registry.lifecycle(object));
        }
    }

    public static void load(RegistryHolder registryHolder, RegistryReadOps<?> registryReadOps) {
        for (RegistryData<?> registryData : REGISTRIES.values()) {
            RegistryAccess.readRegistry(registryReadOps, registryHolder, registryData);
        }
    }

    private static <E> void readRegistry(RegistryReadOps<?> registryReadOps, RegistryHolder registryHolder, RegistryData<E> registryData) {
        ResourceKey resourceKey = registryData.key();
        MappedRegistry mappedRegistry2 = Optional.ofNullable(RegistryHolder.method_30541((RegistryHolder)registryHolder).get(resourceKey)).map(mappedRegistry -> mappedRegistry).orElseThrow(() -> new IllegalStateException("Missing registry: " + resourceKey));
        DataResult dataResult = registryReadOps.decodeElements(mappedRegistry2, registryData.key(), registryData.codec());
        dataResult.error().ifPresent(partialResult -> LOGGER.error("Error loading registry data: {}", (Object)partialResult.message()));
    }

    static /* synthetic */ Map method_31142() {
        return REGISTRIES;
    }
}
