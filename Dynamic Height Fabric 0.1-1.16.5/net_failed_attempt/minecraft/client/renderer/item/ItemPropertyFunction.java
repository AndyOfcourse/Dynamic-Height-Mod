package net.minecraft.client.renderer.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public interface ItemPropertyFunction {
	float call(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity);
}
