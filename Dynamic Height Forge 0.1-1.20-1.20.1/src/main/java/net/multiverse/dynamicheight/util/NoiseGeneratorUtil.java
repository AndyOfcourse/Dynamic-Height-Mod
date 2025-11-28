package net.multiverse.dynamicheight.util;

import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;

import java.util.Map;

import net.multiverse.dynamicheight.util.RecordReflection;

public final class NoiseGeneratorUtil {
    private NoiseGeneratorUtil() {
    }

    public static NoiseGeneratorSettings stretch(NoiseGeneratorSettings base, int minY, int height) {
        NoiseSettings baseNoise = base.noiseSettings();
        NoiseSettings updatedNoise = RecordReflection.cloneRecordWith(baseNoise, Map.of("minY", minY, "height", height));
        return RecordReflection.cloneRecordWith(base, Map.of("noiseSettings", updatedNoise));
    }

    public static NoiseBasedChunkGenerator recreate(NoiseBasedChunkGenerator template, NoiseGeneratorSettings targetSettings) {
        return new NoiseBasedChunkGenerator(template.getBiomeSource(), Holder.direct(targetSettings));
    }
}
