package net.multiverse.dynamicheight.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.Level;
import net.multiverse.dynamicheight.worldheight.WorldHeightManager;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerMixin {
    @Shadow
    public ServerPlayer player;

    @Redirect(
            method = {"handlePlayerAction", "handleUseItemOn"},
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getMaxBuildHeight()I")
    )
    private int dynamicheight$useConfiguredBuildLimit(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            return WorldHeightManager.currentData(serverLevel).maxY();
        }
        return level.getMaxBuildHeight();
    }
}
