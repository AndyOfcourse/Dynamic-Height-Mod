package net.multiverse.dynamicheight.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.DimensionType;
import net.multiverse.dynamicheight.client.ClientDimensionHeights;
import net.multiverse.dynamicheight.util.DimensionTypeUtil;
import net.multiverse.dynamicheight.worldheight.WorldHeightData;
import net.multiverse.dynamicheight.worldheight.WorldHeightData.Snapshot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void dynamicheight$applyCustomHeights(
            net.minecraft.client.multiplayer.ClientPacketListener clientPacketListener,
            ClientLevel.ClientLevelData clientLevelData,
            net.minecraft.resources.ResourceKey<net.minecraft.world.level.Level> resourceKey,
            DimensionType dimensionType,
            int chunkRadius,
            java.util.function.Supplier<net.minecraft.util.profiling.ProfilerFiller> profilerSupplier,
            net.minecraft.client.renderer.LevelRenderer levelRenderer,
            boolean bl,
            long seed,
            CallbackInfo ci) {
        ClientLevel level = (ClientLevel) (Object) this;
        ResourceLocation dimensionId = level.dimension().location();
        Snapshot override = Optional.ofNullable(ClientDimensionHeights.lookup(dimensionId))
                .orElseGet(() -> {
                    if (Minecraft.getInstance().hasSingleplayerServer()) {
                        return WorldHeightData.currentSnapshot();
                    }
                    return null;
                });
        if (override == null) {
            return;
        }
        applyDimensionOverride(level, dimensionType, override);
    }

    private static void applyDimensionOverride(ClientLevel level, DimensionType current, Snapshot override) {
        if (DimensionTypeUtil.minY(current) == override.minY() && DimensionTypeUtil.height(current) == override.height()) {
            return;
        }
        DimensionTypeUtil.copyWithHeight(current, override.minY(), override.maxY());
    }
}
