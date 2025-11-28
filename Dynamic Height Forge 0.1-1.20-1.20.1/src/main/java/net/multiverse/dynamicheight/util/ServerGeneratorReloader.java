package net.multiverse.dynamicheight.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.lang.reflect.Field;

public final class ServerGeneratorReloader {
    private static final Field GENERATOR_FIELD = findField(ServerChunkCache.class, net.minecraft.world.level.chunk.ChunkGenerator.class, "chunkGenerator", "f_8321_");
    private static final Field RANDOM_STATE_FIELD = findField(ServerChunkCache.class, RandomState.class, "randomState", "f_214832_");

    private ServerGeneratorReloader() {
    }

    public static void swapGenerator(ServerLevel level, NoiseBasedChunkGenerator generator, NoiseGeneratorSettings settings) {
        try {
            ServerChunkCache chunkSource = level.getChunkSource();

            HolderLookup.RegistryLookup<NormalNoise.NoiseParameters> noiseLookup = level.registryAccess().lookupOrThrow(Registries.NOISE);
            RandomState randomState = RandomState.create(settings, noiseLookup, level.getSeed());

            GENERATOR_FIELD.set(chunkSource, generator);
            RANDOM_STATE_FIELD.set(chunkSource, randomState);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to swap chunk generator reflectively", e);
        }
    }

    private static Field findField(Class<?> owner, Class<?> fieldType, String... names) {
        for (String candidate : names) {
            if (candidate == null || candidate.isEmpty()) {
                continue;
            }
            try {
                Field field = owner.getDeclaredField(candidate);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException ignored) {
            }
        }

        Class<?> current = owner;
        while (current != null && current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                if (fieldType.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    return field;
                }
            }
            current = current.getSuperclass();
        }
        throw new IllegalStateException("Unable to find field of type " + fieldType.getName() + " on " + owner.getName());
    }
}
