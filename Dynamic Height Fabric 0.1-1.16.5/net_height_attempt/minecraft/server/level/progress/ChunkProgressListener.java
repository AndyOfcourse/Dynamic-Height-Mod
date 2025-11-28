package net.minecraft.server.level.progress;

import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;
import org.jetbrains.annotations.Nullable;

public interface ChunkProgressListener {
   void updateSpawnPos(ChunkPos chunkPos);

   void onStatusChange(ChunkPos chunkPos, @Nullable ChunkStatus chunkStatus);

   void stop();
}
