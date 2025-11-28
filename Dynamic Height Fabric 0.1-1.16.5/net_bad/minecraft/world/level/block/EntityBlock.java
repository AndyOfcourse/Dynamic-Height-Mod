package net.minecraft.world.level.block;

import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public interface EntityBlock {
   @Nullable
   BlockEntity newBlockEntity(BlockGetter blockGetter);
}
