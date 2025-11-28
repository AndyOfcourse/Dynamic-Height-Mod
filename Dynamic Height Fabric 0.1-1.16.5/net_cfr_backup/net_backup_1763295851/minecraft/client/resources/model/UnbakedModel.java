package net.minecraft.client.resources.model;

import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public interface UnbakedModel {
	Collection<ResourceLocation> getDependencies();

	Collection<Material> getMaterials(Function<ResourceLocation, UnbakedModel> function, Set<Pair<String, String>> set);

	@Nullable
	BakedModel bake(ModelBakery modelBakery, Function<Material, TextureAtlasSprite> function, ModelState modelState, ResourceLocation resourceLocation);
}
