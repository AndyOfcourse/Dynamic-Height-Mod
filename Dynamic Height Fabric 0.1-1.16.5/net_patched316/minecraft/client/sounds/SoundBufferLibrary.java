package net.minecraft.client.sounds;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.audio.OggAudioStream;
import com.mojang.blaze3d.audio.SoundBuffer;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

@Environment(EnvType.CLIENT)
public class SoundBufferLibrary {
   private final ResourceManager resourceManager;
   private final Map<ResourceLocation, CompletableFuture<SoundBuffer>> cache = Maps.newHashMap();

   public SoundBufferLibrary(ResourceManager resourceManager) {
      this.resourceManager = resourceManager;
   }

   public CompletableFuture<SoundBuffer> getCompleteBuffer(ResourceLocation resourceLocation) {
      return this.cache.computeIfAbsent(resourceLocation, resourceLocationx -> CompletableFuture.supplyAsync(() -> {
            try (
               Resource resource = this.resourceManager.getResource(resourceLocationx);
               InputStream inputStream = resource.getInputStream();
               OggAudioStream oggAudioStream = new OggAudioStream(inputStream);
            ) {
               ByteBuffer byteBuffer = oggAudioStream.readAll();
               return new SoundBuffer(byteBuffer, oggAudioStream.getFormat());
            } catch (IOException var62) {
               throw new CompletionException(var62);
            }
         }, Util.backgroundExecutor()));
   }

   public CompletableFuture<AudioStream> getStream(ResourceLocation resourceLocation, boolean bl) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            Resource resource = this.resourceManager.getResource(resourceLocation);
            InputStream inputStream = resource.getInputStream();
            return (AudioStream)(bl ? new LoopingAudioStream(OggAudioStream::new, inputStream) : new OggAudioStream(inputStream));
         } catch (IOException var5) {
            throw new CompletionException(var5);
         }
      }, Util.backgroundExecutor());
   }

   public void clear() {
      this.cache.values().forEach(completableFuture -> completableFuture.thenAccept(SoundBuffer::discardAlBuffer));
      this.cache.clear();
   }

   public CompletableFuture<?> preload(Collection<Sound> collection) {
      return CompletableFuture.allOf(collection.stream().map(sound -> this.getCompleteBuffer(sound.getPath())).toArray(i -> new CompletableFuture[i]));
   }
}
