package net.minecraft.world.level.chunk.storage;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.util.ExceptionCollector;
import net.minecraft.world.level.ChunkPos;
import org.jetbrains.annotations.Nullable;

public final class RegionFileStorage implements AutoCloseable {
   private final Long2ObjectLinkedOpenHashMap<RegionFile> regionCache = new Long2ObjectLinkedOpenHashMap();
   private final File folder;
   private final boolean sync;

   RegionFileStorage(File file, boolean bl) {
      this.folder = file;
      this.sync = bl;
   }

   private RegionFile getRegionFile(ChunkPos chunkPos) throws IOException {
      long l = ChunkPos.asLong(chunkPos.getRegionX(), chunkPos.getRegionZ());
      RegionFile regionFile = (RegionFile)this.regionCache.getAndMoveToFirst(l);
      if (regionFile != null) {
         return regionFile;
      } else {
         if (this.regionCache.size() >= 256) {
            ((RegionFile)this.regionCache.removeLast()).close();
         }

         if (!this.folder.exists()) {
            this.folder.mkdirs();
         }

         File file = new File(this.folder, "r." + chunkPos.getRegionX() + "." + chunkPos.getRegionZ() + ".mca");
         RegionFile regionFile2 = new RegionFile(file, this.folder, this.sync);
         this.regionCache.putAndMoveToFirst(l, regionFile2);
         return regionFile2;
      }
   }

   @Nullable
   public CompoundTag read(ChunkPos chunkPos) throws IOException {
      RegionFile regionFile = this.getRegionFile(chunkPos);

      Object var5;
      try (DataInputStream dataInputStream = regionFile.getChunkDataInputStream(chunkPos)) {
         if (dataInputStream != null) {
            return NbtIo.read(dataInputStream);
         }

         var5 = null;
      }

      return (CompoundTag)var5;
   }

   protected void write(ChunkPos chunkPos, CompoundTag compoundTag) throws IOException {
      RegionFile regionFile = this.getRegionFile(chunkPos);

      try (DataOutputStream dataOutputStream = regionFile.getChunkDataOutputStream(chunkPos)) {
         NbtIo.write(compoundTag, dataOutputStream);
      }
   }

   @Override
   public void close() throws IOException {
      ExceptionCollector<IOException> exceptionCollector = new ExceptionCollector<>();
      ObjectIterator var2 = this.regionCache.values().iterator();

      while(var2.hasNext()) {
         RegionFile regionFile = (RegionFile)var2.next();

         try {
            regionFile.close();
         } catch (IOException var5) {
            exceptionCollector.add(var5);
         }
      }

      exceptionCollector.throwIfPresent();
   }

   public void flush() throws IOException {
      ObjectIterator var1 = this.regionCache.values().iterator();

      while(var1.hasNext()) {
         RegionFile regionFile = (RegionFile)var1.next();
         regionFile.flush();
      }
   }
}
