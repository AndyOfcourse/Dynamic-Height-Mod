package net.multiverse.dynamicheight.worldheight;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.multiverse.dynamicheight.network.WorldHeightNetwork;
import net.multiverse.dynamicheight.util.DimensionTypeUtil;

/**
 * Server-side orchestration for loading the height configuration and enforcing placement rules.
 */
public final class WorldHeightManager {
    private static final Map<ResourceKey<Level>, WorldHeightSavedData> BY_DIMENSION = new ConcurrentHashMap<>();
    private static final String STORAGE_KEY_PREFIX = "dynamic_height";

    private WorldHeightManager() {
    }

    public static void register() {
        ServerWorldEvents.LOAD.register((server, world) -> {
            if (world instanceof ServerLevel level) {
                WorldHeightSavedData data = getData(level);
                if (level.dimension().equals(Level.OVERWORLD) && WorldHeightData.hasPendingClientSelection()) {
                    data.update(WorldHeightData.consumeSnapshot());
                }
                applyDimensionSettings(level, data);
            }
        });
        ServerWorldEvents.UNLOAD.register((server, world) -> {
            if (world instanceof ServerLevel level) {
                BY_DIMENSION.remove(level.dimension());
            }
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (!(world instanceof ServerLevel level)) {
                return InteractionResult.PASS;
            }
            WorldHeightSavedData data = getData(level);
            BlockPos target = hitResult.getBlockPos().relative(hitResult.getDirection());
            int y = target.getY();
            if (y < data.minY() || y >= data.maxY()) {
                if (player instanceof ServerPlayer serverPlayer) {
                    serverPlayer.displayClientMessage(Component.literal("Cannot place blocks outside the configured vertical range (" + data.minY() + " .. " + (data.maxY() - 1) + ") in " + level.dimension().location()).withStyle(ChatFormatting.RED), true);
                }
                return InteractionResult.FAIL;
            }
            return InteractionResult.PASS;
        });
    }

    public static void applyClientSelection(ServerPlayer player, WorldHeightData.Snapshot snapshot) {
        var server = player.getServer();
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
                    WorldHeightSavedData.factory(level.dimensionType()),
                    storageKey(level.dimension())
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

    private static String storageKey(ResourceKey<Level> dimension) {
        ResourceLocation id = dimension.location();
        String path = id.getPath().replace('/', '_');
        return STORAGE_KEY_PREFIX + "_" + id.getNamespace() + "_" + path;
    }
}
