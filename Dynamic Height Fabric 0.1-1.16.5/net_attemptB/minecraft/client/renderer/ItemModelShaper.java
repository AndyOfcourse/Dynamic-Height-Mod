package net.minecraft.client.renderer;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Map.Entry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ItemModelShaper {
   public final Int2ObjectMap<ModelResourceLocation> shapes = new Int2ObjectOpenHashMap(256);
   private final Int2ObjectMap<BakedModel> shapesCache = new Int2ObjectOpenHashMap(256);
   private final ModelManager modelManager;

   public ItemModelShaper(ModelManager modelManager) {
      this.modelManager = modelManager;
   }

   public TextureAtlasSprite getParticleIcon(ItemLike itemLike) {
      return this.getParticleIcon(new ItemStack(itemLike));
   }

   public TextureAtlasSprite getParticleIcon(ItemStack itemStack) {
      BakedModel bakedModel = this.getItemModel(itemStack);
      return bakedModel == this.modelManager.getMissingModel() && itemStack.getItem() instanceof BlockItem
         ? this.modelManager.getBlockModelShaper().getParticleIcon(((BlockItem)itemStack.getItem()).getBlock().defaultBlockState())
         : bakedModel.getParticleIcon();
   }

   public BakedModel getItemModel(ItemStack itemStack) {
      BakedModel bakedModel = this.getItemModel(itemStack.getItem());
      return bakedModel == null ? this.modelManager.getMissingModel() : bakedModel;
   }

   @Nullable
   public BakedModel getItemModel(Item item) {
      return (BakedModel)this.shapesCache.get(getIndex(item));
   }

   private static int getIndex(Item item) {
      return Item.getId(item);
   }

   public void register(Item item, ModelResourceLocation modelResourceLocation) {
      this.shapes.put(getIndex(item), modelResourceLocation);
   }

   public ModelManager getModelManager() {
      return this.modelManager;
   }

   public void rebuildCache() {
      this.shapesCache.clear();
      ObjectIterator var1 = this.shapes.entrySet().iterator();

      while(var1.hasNext()) {
         Entry<Integer, ModelResourceLocation> entry = (Entry)var1.next();
         this.shapesCache.put(entry.getKey(), this.modelManager.getModel(entry.getValue()));
      }
   }
}
