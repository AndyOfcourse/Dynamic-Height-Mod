package net.multiverse.dynamicheight.util;

import java.util.Map;

import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;

/**
 * Helpers for cloning Mojang's noise generator settings with new vertical ranges.
 */
public final class NoiseGeneratorUtil {
    private NoiseGeneratorUtil() {
    }

    public static NoiseGeneratorSettings stretch(NoiseGeneratorSettings base, int minY, int height) {
        NoiseSettings baseNoise = base.noiseSettings();
        NoiseSettings updatedNoise = RecordReflection.cloneRecordWith(baseNoise, Map.of("minY", minY, "height", height));
        return RecordReflection.cloneRecordWith(base, Map.of("noiseSettings", updatedNoise));
    }

}
