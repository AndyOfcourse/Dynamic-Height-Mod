/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.HolderGetter
 *  net.minecraft.core.HolderLookup$RegistryLookup
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.server.level.ServerChunkCache
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.chunk.ChunkGenerator
 *  net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator
 *  net.minecraft.world.level.levelgen.NoiseGeneratorSettings
 *  net.minecraft.world.level.levelgen.RandomState
 */
package net.multiverse.dynamicheight.util;

import java.lang.reflect.Field;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;

public final class ServerGeneratorReloader {
    private static final Field GENERATOR_FIELD = ServerGeneratorReloader.findField(ServerChunkCache.class, ChunkGenerator.class, "chunkGenerator", "f_8321_");
    private static final Field RANDOM_STATE_FIELD = ServerGeneratorReloader.findField(ServerChunkCache.class, RandomState.class, "randomState", "f_214832_");

    private ServerGeneratorReloader() {
    }

    public static void swapGenerator(ServerLevel level, NoiseBasedChunkGenerator generator, NoiseGeneratorSettings settings) {
        try {
            ServerChunkCache chunkSource = level.m_7726_();
            HolderLookup.RegistryLookup noiseLookup = level.m_9598_().m_255025_(Registries.f_256865_);
            RandomState randomState = RandomState.m_255302_((NoiseGeneratorSettings)settings, (HolderGetter)noiseLookup, (long)level.m_7328_());
            GENERATOR_FIELD.set(chunkSource, generator);
            RANDOM_STATE_FIELD.set(chunkSource, randomState);
        }
        catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to swap chunk generator reflectively", e);
        }
    }

    private static Field findField(Class<?> owner, Class<?> fieldType, String ... names) {
        for (String candidate : names) {
            if (candidate == null || candidate.isEmpty()) continue;
            try {
                Field field = owner.getDeclaredField(candidate);
                field.setAccessible(true);
                return field;
            }
            catch (NoSuchFieldException field) {
                // empty catch block
            }
        }
        for (Class<?> current = owner; current != null && current != Object.class; current = current.getSuperclass()) {
            for (Field field : current.getDeclaredFields()) {
                if (!fieldType.isAssignableFrom(field.getType())) continue;
                field.setAccessible(true);
                return field;
            }
        }
        throw new IllegalStateException("Unable to find field of type " + fieldType.getName() + " on " + owner.getName());
    }
}

