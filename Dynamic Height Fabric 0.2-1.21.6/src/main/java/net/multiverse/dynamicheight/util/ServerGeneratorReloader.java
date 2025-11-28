package net.multiverse.dynamicheight.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.chunk.status.WorldGenContext;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.multiverse.dynamicheight.mixin.ChunkMapAccessor;

/**
 * Swaps the chunk generator and random state on a running server level.
 */
public final class ServerGeneratorReloader {
    private ServerGeneratorReloader() {
    }

    public static void swapGenerator(ServerLevel level, NoiseBasedChunkGenerator generator, NoiseGeneratorSettings settings) {
        ServerChunkCache chunkSource = level.getChunkSource();
        ChunkMap chunkMap = chunkSource.chunkMap;
        ChunkMapAccessor accessor = (ChunkMapAccessor) chunkMap;

        var registryAccess = level.registryAccess();
        var noiseLookup = registryAccess.lookupOrThrow(Registries.NOISE);
        RandomState randomState = RandomState.create(settings, noiseLookup, level.getSeed());
        var structureSets = registryAccess.lookupOrThrow(Registries.STRUCTURE_SET);
        ChunkGeneratorStructureState generatorState = generator.createState(structureSets, randomState, level.getSeed());

        WorldGenContext previous = accessor.dynamicheight$getWorldGenContext();
        WorldGenContext updated = new WorldGenContext(
                level,
                generator,
                previous.structureManager(),
                previous.lightEngine(),
                previous.mainThreadExecutor(),
                previous.unsavedListener()
        );

        accessor.dynamicheight$setWorldGenContext(updated);
        accessor.dynamicheight$setRandomState(randomState);
        accessor.dynamicheight$setChunkGeneratorState(generatorState);
    }
}
