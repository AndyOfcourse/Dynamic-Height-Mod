/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.util.Either
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.core.Registry
 *  net.minecraft.data.worldgen.biome.Biomes
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.biome.Biome$ClimateParameters
 *  net.minecraft.world.level.biome.BiomeSource
 *  net.minecraft.world.level.biome.MultiNoiseBiomeSource$NoiseParameters
 *  net.minecraft.world.level.biome.MultiNoiseBiomeSource$Preset
 *  net.minecraft.world.level.biome.MultiNoiseBiomeSource$PresetInstance
 *  net.minecraft.world.level.levelgen.WorldgenRandom
 *  net.minecraft.world.level.levelgen.synth.NormalNoise
 */
package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.biome.Biomes;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class MultiNoiseBiomeSource
extends BiomeSource {
    private static final NoiseParameters DEFAULT_NOISE_PARAMETERS = new NoiseParameters(-7, (List)ImmutableList.of((Object)1.0, (Object)1.0));
    public static final MapCodec<MultiNoiseBiomeSource> DIRECT_CODEC = RecordCodecBuilder.mapCodec(instance2 -> instance2.group((App)Codec.LONG.fieldOf("seed").forGetter(multiNoiseBiomeSource -> multiNoiseBiomeSource.seed), (App)RecordCodecBuilder.create(instance -> instance.group((App)Biome.ClimateParameters.CODEC.fieldOf("parameters").forGetter(Pair::getFirst), (App)Biome.CODEC.fieldOf("biome").forGetter(Pair::getSecond)).apply((Applicative)instance, Pair::of)).listOf().fieldOf("biomes").forGetter(multiNoiseBiomeSource -> multiNoiseBiomeSource.parameters), (App)NoiseParameters.CODEC.fieldOf("temperature_noise").forGetter(multiNoiseBiomeSource -> multiNoiseBiomeSource.temperatureParams), (App)NoiseParameters.CODEC.fieldOf("humidity_noise").forGetter(multiNoiseBiomeSource -> multiNoiseBiomeSource.humidityParams), (App)NoiseParameters.CODEC.fieldOf("altitude_noise").forGetter(multiNoiseBiomeSource -> multiNoiseBiomeSource.altitudeParams), (App)NoiseParameters.CODEC.fieldOf("weirdness_noise").forGetter(multiNoiseBiomeSource -> multiNoiseBiomeSource.weirdnessParams)).apply((Applicative)instance2, MultiNoiseBiomeSource::new));
    public static final Codec<MultiNoiseBiomeSource> CODEC = Codec.mapEither((MapCodec)PresetInstance.CODEC, DIRECT_CODEC).xmap(either -> (MultiNoiseBiomeSource)((Object)((Object)either.map(PresetInstance::biomeSource, Function.identity()))), multiNoiseBiomeSource -> multiNoiseBiomeSource.preset().map(Either::left).orElseGet(() -> Either.right((Object)multiNoiseBiomeSource))).codec();
    private final NoiseParameters temperatureParams;
    private final NoiseParameters humidityParams;
    private final NoiseParameters altitudeParams;
    private final NoiseParameters weirdnessParams;
    private final NormalNoise temperatureNoise;
    private final NormalNoise humidityNoise;
    private final NormalNoise altitudeNoise;
    private final NormalNoise weirdnessNoise;
    private final List<Pair<Biome.ClimateParameters, Supplier<Biome>>> parameters;
    private final boolean useY;
    private final long seed;
    private final Optional<Pair<Registry<Biome>, Preset>> preset;

    private MultiNoiseBiomeSource(long l, List<Pair<Biome.ClimateParameters, Supplier<Biome>>> list, Optional<Pair<Registry<Biome>, Preset>> optional) {
        this(l, list, DEFAULT_NOISE_PARAMETERS, DEFAULT_NOISE_PARAMETERS, DEFAULT_NOISE_PARAMETERS, DEFAULT_NOISE_PARAMETERS, optional);
    }

    private MultiNoiseBiomeSource(long l, List<Pair<Biome.ClimateParameters, Supplier<Biome>>> list, NoiseParameters noiseParameters, NoiseParameters noiseParameters2, NoiseParameters noiseParameters3, NoiseParameters noiseParameters4) {
        this(l, list, noiseParameters, noiseParameters2, noiseParameters3, noiseParameters4, Optional.empty());
    }

    private MultiNoiseBiomeSource(long l, List<Pair<Biome.ClimateParameters, Supplier<Biome>>> list, NoiseParameters noiseParameters, NoiseParameters noiseParameters2, NoiseParameters noiseParameters3, NoiseParameters noiseParameters4, Optional<Pair<Registry<Biome>, Preset>> optional) {
        super(list.stream().map(Pair::getSecond));
        this.seed = l;
        this.preset = optional;
        this.temperatureParams = noiseParameters;
        this.humidityParams = noiseParameters2;
        this.altitudeParams = noiseParameters3;
        this.weirdnessParams = noiseParameters4;
        this.temperatureNoise = NormalNoise.create((WorldgenRandom)new WorldgenRandom(l), (int)noiseParameters.firstOctave(), (DoubleList)noiseParameters.amplitudes());
        this.humidityNoise = NormalNoise.create((WorldgenRandom)new WorldgenRandom(l + 1L), (int)noiseParameters2.firstOctave(), (DoubleList)noiseParameters2.amplitudes());
        this.altitudeNoise = NormalNoise.create((WorldgenRandom)new WorldgenRandom(l + 2L), (int)noiseParameters3.firstOctave(), (DoubleList)noiseParameters3.amplitudes());
        this.weirdnessNoise = NormalNoise.create((WorldgenRandom)new WorldgenRandom(l + 3L), (int)noiseParameters4.firstOctave(), (DoubleList)noiseParameters4.amplitudes());
        this.parameters = list;
        this.useY = false;
    }

    protected Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Environment(value=EnvType.CLIENT)
    public BiomeSource withSeed(long l) {
        return new MultiNoiseBiomeSource(l, this.parameters, this.temperatureParams, this.humidityParams, this.altitudeParams, this.weirdnessParams, this.preset);
    }

    private Optional<PresetInstance> preset() {
        return this.preset.map(pair -> new PresetInstance((Preset)pair.getSecond(), (Registry)pair.getFirst(), this.seed, null));
    }

    public Biome getNoiseBiome(int i, int j, int k) {
        int l = this.useY ? j : 0;
        Biome.ClimateParameters climateParameters = new Biome.ClimateParameters((float)this.temperatureNoise.getValue((double)i, (double)l, (double)k), (float)this.humidityNoise.getValue((double)i, (double)l, (double)k), (float)this.altitudeNoise.getValue((double)i, (double)l, (double)k), (float)this.weirdnessNoise.getValue((double)i, (double)l, (double)k), 0.0f);
        return this.parameters.stream().min(Comparator.comparing(pair -> Float.valueOf(((Biome.ClimateParameters)pair.getFirst()).fitness(climateParameters)))).map(Pair::getSecond).map(Supplier::get).orElse(Biomes.THE_VOID);
    }

    public boolean stable(long l) {
        return this.seed == l && this.preset.isPresent() && Objects.equals(this.preset.get().getSecond(), Preset.NETHER);
    }
}
