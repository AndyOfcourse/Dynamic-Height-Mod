package net.multiverse.dynamicheight.worldheight;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.multiverse.dynamicheight.network.WorldHeightNetwork;
import net.multiverse.dynamicheight.util.DimensionTypeUtil;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

/**
 * Server-side orchestration for loading the height configuration and enforcing placement rules.
 */
public final class WorldHeightManager {
    private static final Map<ResourceKey<Level>, WorldHeightSavedData> BY_DIMENSION = new ConcurrentHashMap<>();

    private WorldHeightManager() {
    }

    public static void onLevelLoad(LevelEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }
        WorldHeightSavedData data = getData(level);
        if (level.dimension().equals(Level.OVERWORLD) && WorldHeightData.hasPendingClientSelection()) {
            data.update(WorldHeightData.consumeSnapshot());
        }
        applyDimensionSettings(level, data);
    }

    public static void onLevelUnload(LevelEvent.Unload event) {
        if (event.getLevel() instanceof ServerLevel level) {
            BY_DIMENSION.remove(level.dimension());
        }
    }

    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }
        WorldHeightSavedData data = getData(level);
        BlockPos target = event.getPos().relative(event.getFace());
        int y = target.getY();
        if (y < data.minY() || y >= data.maxY()) {
            if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                serverPlayer.displayClientMessage(Component.literal("Cannot place blocks outside the configured vertical range (" + data.minY() + " .. " + (data.maxY() - 1) + ") in " + level.dimension().location()).withStyle(ChatFormatting.RED), true);
            }
            event.setCancellationResult(InteractionResult.FAIL);
            event.setCanceled(true);
        }
    }
    public static void applyClientSelection(ServerPlayer player, WorldHeightData.Snapshot snapshot) {
        var server = player.level().getServer();
        if (server == null) {
            return;
        }
        HeightValidator.HeightRange range = HeightValidator.sanitize(snapshot.minY(), snapshot.maxY());
        WorldHeightData.Snapshot sanitized = new WorldHeightData.Snapshot(range.minY(), range.maxY());
        for (ServerLevel level : server.getAllLevels()) {
            WorldHeightSavedData data = getData(level);
            data.update(sanitized);
            applyDimensionSettings(level, data);
        }
    }

    public static WorldHeightSavedData currentData(ServerLevel level) {
        return getData(level);
    }

    private static WorldHeightSavedData getData(ServerLevel level) {
        return BY_DIMENSION.compute(level.dimension(), (key, existing) -> {
            if (existing != null) {
                return existing;
            }
            WorldHeightSavedData loaded = level.getDataStorage().computeIfAbsent(
                    WorldHeightSavedData.factory(level.dimension(), level.dimensionType()),
                    WorldHeightSavedData.storageKey(level.dimension())
            );
            return loaded;
        });
    }

    private static void applyDimensionSettings(ServerLevel level, WorldHeightSavedData data) {
        var holder = level.dimensionTypeRegistration();
        DimensionType current = holder.value();
        if (current.minY() != data.minY() || current.height() != data.height()) {
            DimensionType updated = DimensionTypeUtil.copyWithHeight(current, data.minY(), data.maxY());
            DimensionTypeUtil.bindUpdatedDimensionType(level.registryAccess(), holder, updated);
        }
        WorldHeightNetwork.broadcast(level, data);
    }
}
