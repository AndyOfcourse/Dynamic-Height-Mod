package net.minecraft.client.resources;

import com.mojang.blaze3d.platform.NativeImage;
import java.io.IOException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

@Environment(EnvType.CLIENT)
public class LegacyStuffWrapper {
   @Deprecated
   public static int[] getPixels(ResourceManager resourceManager, ResourceLocation resourceLocation) throws IOException {
      int[] var6;
      try (
         Resource resource = resourceManager.getResource(resourceLocation);
         NativeImage nativeImage = NativeImage.read(resource.getInputStream());
      ) {
         var6 = nativeImage.makePixelArray();
      }

      return var6;
   }
}
