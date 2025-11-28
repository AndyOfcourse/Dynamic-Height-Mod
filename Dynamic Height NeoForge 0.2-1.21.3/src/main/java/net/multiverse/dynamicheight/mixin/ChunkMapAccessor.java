package net.multiverse.dynamicheight.mixin;

import net.minecraft.server.level.ChunkMap;
import net.minecraft.world.level.chunk.status.WorldGenContext;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.RandomState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChunkMap.class)
public interface ChunkMapAccessor {
    @Accessor("worldGenContext")
    WorldGenContext dynamicheight$getWorldGenContext();

    @Mutable
    @Accessor("worldGenContext")
    void dynamicheight$setWorldGenContext(WorldGenContext context);

    @Mutable
    @Accessor("randomState")
    void dynamicheight$setRandomState(RandomState state);

    @Mutable
    @Accessor("chunkGeneratorState")
    void dynamicheight$setChunkGeneratorState(ChunkGeneratorStructureState state);
}

