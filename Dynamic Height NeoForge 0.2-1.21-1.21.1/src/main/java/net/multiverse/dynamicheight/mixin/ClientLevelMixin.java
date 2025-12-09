package net.multiverse.dynamicheight.mixin;

import java.util.Optional;
import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.multiverse.dynamicheight.client.ClientDimensionHeights;
import net.multiverse.dynamicheight.util.DimensionTypeUtil;
import net.multiverse.dynamicheight.worldheight.WorldHeightData;
import net.multiverse.dynamicheight.worldheight.WorldHeightData.Snapshot;
import net.multiverse.dynamicheight.worldheight.WorldHeightManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void dynamicheight$applyCustomHeights(
            net.minecraft.client.multiplayer.ClientPacketListener clientPacketListener,
            ClientLevel.ClientLevelData clientLevelData,
            net.minecraft.resources.ResourceKey<net.minecraft.world.level.Level> resourceKey,
            Holder<DimensionType> holder,
            int chunkRadius,
            int simulationDistance,
            Supplier<ProfilerFiller> profilerSupplier,
            net.minecraft.client.renderer.LevelRenderer levelRenderer,
            boolean bl,
            long seed,
            CallbackInfo ci) {
        ClientLevel level = (ClientLevel) (Object) this;
        ResourceLocation dimensionId = level.dimension().location();
        Snapshot override = Optional.ofNullable(ClientDimensionHeights.lookup(dimensionId))
                .orElseGet(() -> resolveIntegratedServerSnapshot(resourceKey));
        if (override == null) {
            return;
        }
        applyDimensionOverride(level, holder, override);
    }

    private static Snapshot resolveIntegratedServerSnapshot(ResourceKey<Level> dimension) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!minecraft.hasSingleplayerServer()) {
            return null;
        }
        MinecraftServer server = minecraft.getSingleplayerServer();
        if (server == null) {
            return WorldHeightData.currentSnapshot();
        }
        ServerLevel serverLevel = server.getLevel(dimension);
        if (serverLevel == null) {
            return WorldHeightData.currentSnapshot();
        }
        var data = WorldHeightManager.currentData(serverLevel);
        return new Snapshot(data.minY(), data.maxY());
    }

    private static void applyDimensionOverride(ClientLevel level, Holder<DimensionType> holder, Snapshot override) {
        DimensionType current = holder.value();
        if (current.minY() == override.minY() && current.height() == override.height()) {
            return;
        }
        DimensionType updated = DimensionTypeUtil.copyWithHeight(current, override.minY(), override.maxY());
        DimensionTypeUtil.bindUpdatedDimensionType(level.registryAccess(), holder, updated);
    }
}
