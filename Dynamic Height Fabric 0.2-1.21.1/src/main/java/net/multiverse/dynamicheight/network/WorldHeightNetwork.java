package net.multiverse.dynamicheight.network;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.multiverse.dynamicheight.worldheight.WorldHeightData;
import net.multiverse.dynamicheight.worldheight.WorldHeightManager;
import net.multiverse.dynamicheight.worldheight.WorldHeightSavedData;

public final class WorldHeightNetwork {
    private WorldHeightNetwork() {
    }

    public static void register() {
        PayloadTypeRegistry.playC2S().register(WorldHeightSyncPayload.TYPE, WorldHeightSyncPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(WorldHeightAppliedPayload.TYPE, WorldHeightAppliedPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(WorldHeightSyncPayload.TYPE, (payload, context) -> {
            context.player().server.execute(() -> {
                WorldHeightData.updateFromClient(payload.minY(), payload.maxY());
                WorldHeightManager.applyClientSelection(context.player(), WorldHeightData.consumeSnapshot());
                sendCurrentRange(context.player(), context.player().serverLevel());
            });
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            sendCurrentRange(handler.player, handler.player.serverLevel());
        });

        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> {
            sendCurrentRange(player, destination);
        });
    }

    public static void sendCurrentRange(ServerPlayer player, ServerLevel level) {
        var data = WorldHeightManager.currentData(level);
        ServerPlayNetworking.send(player, new WorldHeightAppliedPayload(level.dimension().location(), data.minY(), data.maxY()));
    }

    public static void broadcast(ServerLevel level, WorldHeightSavedData data) {
        for (ServerPlayer player : level.players()) {
            ServerPlayNetworking.send(player, new WorldHeightAppliedPayload(level.dimension().location(), data.minY(), data.maxY()));
        }
    }
}
