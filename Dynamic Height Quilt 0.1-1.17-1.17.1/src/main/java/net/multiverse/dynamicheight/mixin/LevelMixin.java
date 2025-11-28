package net.multiverse.dynamicheight.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelHeightAccessor.class)
public interface LevelMixin {
    @Inject(method = "getMinBuildHeight()I", at = @At("HEAD"), cancellable = true, remap = false)
    private void dynamicheight$minBuildHeight(CallbackInfoReturnable<Integer> cir) {
        if ((Object) this instanceof Level level) {
            cir.setReturnValue(level.dimensionType().minY());
        }
    }

    @Inject(method = "getMaxBuildHeight()I", at = @At("HEAD"), cancellable = true, remap = false)
    private void dynamicheight$maxBuildHeight(CallbackInfoReturnable<Integer> cir) {
        if ((Object) this instanceof Level level) {
            cir.setReturnValue(level.dimensionType().minY() + level.dimensionType().height());
        }
    }

    @Inject(method = "getHeight()I", at = @At("HEAD"), cancellable = true, remap = false)
    private void dynamicheight$height(CallbackInfoReturnable<Integer> cir) {
        if ((Object) this instanceof Level level) {
            cir.setReturnValue(level.dimensionType().height());
        }
    }

    @Inject(method = "getSectionsCount()I", at = @At("HEAD"), cancellable = true, remap = false)
    private void dynamicheight$sections(CallbackInfoReturnable<Integer> cir) {
        if ((Object) this instanceof Level level) {
            cir.setReturnValue(Math.max(1, level.dimensionType().height() / 16));
        }
    }

    @Inject(method = "isOutsideBuildHeight(I)Z", at = @At("HEAD"), cancellable = true, remap = false)
    private void dynamicheight$inBuildRange(int y, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Level level) {
            int min = level.dimensionType().minY();
            int max = min + level.dimensionType().height();
            cir.setReturnValue(y < min || y >= max);
        }
    }

    @Inject(method = "isOutsideBuildHeight(Lnet/minecraft/core/BlockPos;)Z", at = @At("HEAD"), cancellable = true, remap = false)
    private void dynamicheight$inBuildRangePos(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Level level) {
            int y = pos.getY();
            int min = level.dimensionType().minY();
            int max = min + level.dimensionType().height();
            cir.setReturnValue(y < min || y >= max);
        }
    }
}
