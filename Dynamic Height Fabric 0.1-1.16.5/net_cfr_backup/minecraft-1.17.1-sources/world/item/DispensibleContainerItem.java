package net.minecraft.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public interface DispensibleContainerItem {
	default void checkExtraContent(@Nullable Player player, Level level, ItemStack itemStack, BlockPos blockPos) {
	}

	boolean emptyContents(@Nullable Player player, Level level, BlockPos blockPos, @Nullable BlockHitResult blockHitResult);
}
