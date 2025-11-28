package net.multiverse.dynamicheight.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.multiverse.dynamicheight.worldheight.WorldHeightManager;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerMixin {
    @Shadow
    public ServerPlayer player;

    @Redirect(
            method = {"handlePlayerAction", "handleUseItemOn"},
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;getMaxBuildHeight()I")
    )
    private int dynamicheight$useConfiguredBuildLimit(MinecraftServer server) {
        ServerLevel serverLevel = this.player.getLevel();
        return WorldHeightManager.currentData(serverLevel).maxY();
    }
}
