package net.minecraft.client.renderer.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public interface ClampedItemPropertyFunction extends ItemPropertyFunction {
	@Deprecated
	@Override
	default float call(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
		return Mth.clamp(this.unclampedCall(itemStack, clientLevel, livingEntity, i), 0.0F, 1.0F);
	}

	float unclampedCall(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i);
}
