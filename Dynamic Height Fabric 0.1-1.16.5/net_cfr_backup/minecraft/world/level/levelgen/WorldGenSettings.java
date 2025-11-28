/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.google.common.collect.ImmutableSet
 *  com.google.gson.JsonObject
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  com.mojang.serialization.Lifecycle
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.core.MappedRegistry
 *  net.minecraft.core.Registry
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.core.WritableRegistry
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.biome.BiomeSource
 *  net.minecraft.world.level.biome.OverworldBiomeSource
 *  net.minecraft.world.level.chunk.ChunkGenerator
 *  net.minecraft.world.level.dimension.DimensionType
 *  net.minecraft.world.level.dimension.LevelStem
 *  net.minecraft.world.level.levelgen.DebugLevelSource
 *  net.minecraft.world.level.levelgen.FlatLevelSource
 *  net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator
 *  net.minecraft.world.level.levelgen.NoiseGeneratorSettings
 *  net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.level.levelgen;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Properties;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.OverworldBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.DebugLevelSource;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldGenSettings {
    public static final Codec<WorldGenSettings> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)Codec.LONG.fieldOf("seed").stable().forGetter(WorldGenSettings::seed), (App)Codec.BOOL.fieldOf("generate_features").orElse((Object)true).stable().forGetter(WorldGenSettings::generateFeatures), (App)Codec.BOOL.fieldOf("bonus_chest").orElse((Object)false).stable().forGetter(WorldGenSettings::generateBonusChest), (App)MappedRegistry.dataPackCodec((ResourceKey)Registry.LEVEL_STEM_REGISTRY, (Lifecycle)Lifecycle.stable(), (Codec)LevelStem.CODEC).xmap(LevelStem::sortMap, Function.identity()).fieldOf("dimensions").forGetter(WorldGenSettings::dimensions), (App)Codec.STRING.optionalFieldOf("legacy_custom_options").stable().forGetter(worldGenSettings -> worldGenSettings.legacyCustomOptions)).apply((Applicative)instance, instance.stable(WorldGenSettings::new))).comapFlatMap(WorldGenSettings::guardExperimental, Function.identity());
    private static final Logger LOGGER = LogManager.getLogger();
    private final long seed;
    private final boolean generateFeatures;
    private final boolean generateBonusChest;
    private final MappedRegistry<LevelStem> dimensions;
    private final Optional<String> legacyCustomOptions;

    private DataResult<WorldGenSettings> guardExperimental() {
        LevelStem levelStem = (LevelStem)this.dimensions.get(LevelStem.OVERWORLD);
        if (levelStem == null) {
            return DataResult.error((String)"Overworld settings missing");
        }
        if (this.stable()) {
            return DataResult.success((Object)this, (Lifecycle)Lifecycle.stable());
        }
        return DataResult.success((Object)this);
    }

    private boolean stable() {
        return LevelStem.stable((long)this.seed, this.dimensions);
    }

    public WorldGenSettings(long l, boolean bl, boolean bl2, MappedRegistry<LevelStem> mappedRegistry) {
        this(l, bl, bl2, mappedRegistry, Optional.empty());
        LevelStem levelStem = (LevelStem)mappedRegistry.get(LevelStem.OVERWORLD);
        if (levelStem == null) {
            throw new IllegalStateException("Overworld settings missing");
        }
    }

    private WorldGenSettings(long l, boolean bl, boolean bl2, MappedRegistry<LevelStem> mappedRegistry, Optional<String> optional) {
        this.seed = l;
        this.generateFeatures = bl;
        this.generateBonusChest = bl2;
        this.dimensions = mappedRegistry;
        this.legacyCustomOptions = optional;
    }

    public static WorldGenSettings demoSettings(RegistryAccess registryAccess) {
        WritableRegistry registry = registryAccess.registryOrThrow(Registry.BIOME_REGISTRY);
        int i = "North Carolina".hashCode();
        WritableRegistry registry2 = registryAccess.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
        WritableRegistry registry3 = registryAccess.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY);
        return new WorldGenSettings(i, true, true, WorldGenSettings.withOverworld((Registry<DimensionType>)registry2, (MappedRegistry<LevelStem>)DimensionType.defaultDimensions((Registry)registry2, (Registry)registry, (Registry)registry3, (long)i), (ChunkGenerator)WorldGenSettings.makeDefaultOverworld((Registry<Biome>)registry, (Registry<NoiseGeneratorSettings>)registry3, i)));
    }

    public static WorldGenSettings makeDefault(Registry<DimensionType> registry, Registry<Biome> registry2, Registry<NoiseGeneratorSettings> registry3) {
        long l = new Random().nextLong();
        return new WorldGenSettings(l, true, false, WorldGenSettings.withOverworld(registry, (MappedRegistry<LevelStem>)DimensionType.defaultDimensions(registry, registry2, registry3, (long)l), (ChunkGenerator)WorldGenSettings.makeDefaultOverworld(registry2, registry3, l)));
    }

    public static NoiseBasedChunkGenerator makeDefaultOverworld(Registry<Biome> registry, Registry<NoiseGeneratorSettings> registry2, long l) {
        return new NoiseBasedChunkGenerator((BiomeSource)new OverworldBiomeSource(l, false, false, registry), l, () -> (NoiseGeneratorSettings)registry2.getOrThrow(NoiseGeneratorSettings.OVERWORLD));
    }

    public long seed() {
        return this.seed;
    }

    public boolean generateFeatures() {
        return this.generateFeatures;
    }

    public boolean generateBonusChest() {
        return this.generateBonusChest;
    }

    public static MappedRegistry<LevelStem> withOverworld(Registry<DimensionType> registry, MappedRegistry<LevelStem> mappedRegistry, ChunkGenerator chunkGenerator) {
        LevelStem levelStem = (LevelStem)mappedRegistry.get(LevelStem.OVERWORLD);
        Supplier<DimensionType> supplier = () -> levelStem == null ? (DimensionType)registry.getOrThrow(DimensionType.OVERWORLD_LOCATION) : levelStem.type();
        return WorldGenSettings.withOverworld(mappedRegistry, supplier, chunkGenerator);
    }

    public static MappedRegistry<LevelStem> withOverworld(MappedRegistry<LevelStem> mappedRegistry, Supplier<DimensionType> supplier, ChunkGenerator chunkGenerator) {
        MappedRegistry mappedRegistry2 = new MappedRegistry(Registry.LEVEL_STEM_REGISTRY, Lifecycle.experimental());
        mappedRegistry2.register(LevelStem.OVERWORLD, (Object)new LevelStem(supplier, chunkGenerator), Lifecycle.stable());
        for (Map.Entry entry : mappedRegistry.entrySet()) {
            ResourceKey resourceKey = (ResourceKey)entry.getKey();
            if (resourceKey == LevelStem.OVERWORLD) continue;
            mappedRegistry2.register(resourceKey, entry.getValue(), mappedRegistry.lifecycle(entry.getValue()));
        }
        return mappedRegistry2;
    }

    public MappedRegistry<LevelStem> dimensions() {
        return this.dimensions;
    }

    public ChunkGenerator overworld() {
        LevelStem levelStem = (LevelStem)this.dimensions.get(LevelStem.OVERWORLD);
        if (levelStem == null) {
            throw new IllegalStateException("Overworld settings missing");
        }
        return levelStem.generator();
    }

    public ImmutableSet<ResourceKey<Level>> levels() {
        return (ImmutableSet)this.dimensions().entrySet().stream().map(entry -> ResourceKey.create((ResourceKey)Registry.DIMENSION_REGISTRY, (ResourceLocation)((ResourceKey)entry.getKey()).location())).collect(ImmutableSet.toImmutableSet());
    }

    public boolean isDebug() {
        return this.overworld() instanceof DebugLevelSource;
    }

    public boolean isFlatWorld() {
        return this.overworld() instanceof FlatLevelSource;
    }

    @Environment(value=EnvType.CLIENT)
    public boolean isOldCustomizedWorld() {
        return this.legacyCustomOptions.isPresent();
    }

    public WorldGenSettings withBonusChest() {
        return new WorldGenSettings(this.seed, this.generateFeatures, true, this.dimensions, this.legacyCustomOptions);
    }

    @Environment(value=EnvType.CLIENT)
    public WorldGenSettings withFeaturesToggled() {
        return new WorldGenSettings(this.seed, !this.generateFeatures, this.generateBonusChest, this.dimensions);
    }

    @Environment(value=EnvType.CLIENT)
    public WorldGenSettings withBonusChestToggled() {
        return new WorldGenSettings(this.seed, this.generateFeatures, !this.generateBonusChest, this.dimensions);
    }

    public static WorldGenSettings create(RegistryAccess registryAccess, Properties properties) {
        String string2 = (String)MoreObjects.firstNonNull((Object)((String)properties.get("generator-settings")), (Object)"");
        properties.put("generator-settings", string2);
        String string22 = (String)MoreObjects.firstNonNull((Object)((String)properties.get("level-seed")), (Object)"");
        properties.put("level-seed", string22);
        String string3 = (String)properties.get("generate-structures");
        boolean bl = string3 == null || Boolean.parseBoolean(string3);
        properties.put("generate-structures", Objects.toString(bl));
        String string4 = (String)properties.get("level-type");
        String string5 = Optional.ofNullable(string4).map(string -> string.toLowerCase(Locale.ROOT)).orElse("default");
        properties.put("level-type", string5);
        long l = new Random().nextLong();
        if (!string22.isEmpty()) {
            try {
                long m = Long.parseLong(string22);
                if (m != 0L) {
                    l = m;
                }
            }
            catch (NumberFormatException numberFormatException) {
                l = string22.hashCode();
            }
        }
        WritableRegistry registry = registryAccess.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
        WritableRegistry registry2 = registryAccess.registryOrThrow(Registry.BIOME_REGISTRY);
        WritableRegistry registry3 = registryAccess.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY);
        MappedRegistry mappedRegistry = DimensionType.defaultDimensions((Registry)registry, (Registry)registry2, (Registry)registry3, (long)l);
        switch (string5) {
            case "flat": {
                JsonObject jsonObject = !string2.isEmpty() ? GsonHelper.parse((String)string2) : new JsonObject();
                Dynamic dynamic = new Dynamic((DynamicOps)JsonOps.INSTANCE, (Object)jsonObject);
                return new WorldGenSettings(l, bl, false, WorldGenSettings.withOverworld((Registry<DimensionType>)registry, (MappedRegistry<LevelStem>)mappedRegistry, (ChunkGenerator)new FlatLevelSource(FlatLevelGeneratorSettings.CODEC.parse(dynamic).resultOrPartial(arg_0 -> ((Logger)LOGGER).error(arg_0)).orElseGet(() -> WorldGenSettings.method_31170((Registry)registry2)))));
            }
            case "debug_all_block_states": {
                return new WorldGenSettings(l, bl, false, WorldGenSettings.withOverworld((Registry<DimensionType>)registry, (MappedRegistry<LevelStem>)mappedRegistry, (ChunkGenerator)new DebugLevelSource((Registry)registry2)));
            }
            case "amplified": {
                return new WorldGenSettings(l, bl, false, WorldGenSettings.withOverworld((Registry<DimensionType>)registry, (MappedRegistry<LevelStem>)mappedRegistry, (ChunkGenerator)new NoiseBasedChunkGenerator((BiomeSource)new OverworldBiomeSource(l, false, false, (Registry)registry2), l, () -> WorldGenSettings.method_30646((Registry)registry3))));
            }
            case "largebiomes": {
                return new WorldGenSettings(l, bl, false, WorldGenSettings.withOverworld((Registry<DimensionType>)registry, (MappedRegistry<LevelStem>)mappedRegistry, (ChunkGenerator)new NoiseBasedChunkGenerator((BiomeSource)new OverworldBiomeSource(l, false, true, (Registry)registry2), l, () -> WorldGenSettings.method_30645((Registry)registry3))));
            }
        }
        return new WorldGenSettings(l, bl, false, WorldGenSettings.withOverworld((Registry<DimensionType>)registry, (MappedRegistry<LevelStem>)mappedRegistry, (ChunkGenerator)WorldGenSettings.makeDefaultOverworld((Registry<Biome>)registry2, (Registry<NoiseGeneratorSettings>)registry3, l)));
    }

    @Environment(value=EnvType.CLIENT)
    public WorldGenSettings withSeed(boolean bl, OptionalLong optionalLong) {
        MappedRegistry mappedRegistry;
        long l = optionalLong.orElse(this.seed);
        if (optionalLong.isPresent()) {
            mappedRegistry = new MappedRegistry(Registry.LEVEL_STEM_REGISTRY, Lifecycle.experimental());
            long m = optionalLong.getAsLong();
            for (Map.Entry entry : this.dimensions.entrySet()) {
                ResourceKey resourceKey = (ResourceKey)entry.getKey();
                mappedRegistry.register(resourceKey, (Object)new LevelStem(((LevelStem)entry.getValue()).typeSupplier(), ((LevelStem)entry.getValue()).generator().withSeed(m)), this.dimensions.lifecycle(entry.getValue()));
            }
        } else {
            mappedRegistry = this.dimensions;
        }
        WorldGenSettings worldGenSettings = this.isDebug() ? new WorldGenSettings(l, false, false, (MappedRegistry<LevelStem>)mappedRegistry) : new WorldGenSettings(l, this.generateFeatures(), this.generateBonusChest() && !bl, (MappedRegistry<LevelStem>)mappedRegistry);
        return worldGenSettings;
    }

    private static /* synthetic */ NoiseGeneratorSettings method_30645(Registry registry) {
        return (NoiseGeneratorSettings)registry.getOrThrow(NoiseGeneratorSettings.OVERWORLD);
    }

    private static /* synthetic */ NoiseGeneratorSettings method_30646(Registry registry) {
        return (NoiseGeneratorSettings)registry.getOrThrow(NoiseGeneratorSettings.AMPLIFIED);
    }

    private static /* synthetic */ FlatLevelGeneratorSettings method_31170(Registry registry) {
        return FlatLevelGeneratorSettings.getDefault((Registry)registry);
    }
}
