package net.multiverse.dynamicheight.network;

import java.util.concurrent.atomic.AtomicBoolean;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.multiverse.dynamicheight.client.ClientDimensionHeights;
import net.multiverse.dynamicheight.worldheight.WorldHeightData;
import net.multiverse.dynamicheight.worldheight.WorldHeightManager;
import net.multiverse.dynamicheight.worldheight.WorldHeightSavedData;

public final class WorldHeightNetwork {
    private static final AtomicBoolean PAYLOADS_REGISTERED = new AtomicBoolean(false);

    private WorldHeightNetwork() {
    }

    private static void registerPayloadTypes() {
        if (PAYLOADS_REGISTERED.compareAndSet(false, true)) {
            PayloadTypeRegistry.playC2S().register(WorldHeightSyncPayload.TYPE, WorldHeightSyncPayload.CODEC);
            PayloadTypeRegistry.playS2C().register(WorldHeightAppliedPayload.TYPE, WorldHeightAppliedPayload.CODEC);
        }
    }

    public static void registerServer() {
        registerPayloadTypes();
        ServerPlayNetworking.registerGlobalReceiver(WorldHeightSyncPayload.TYPE, WorldHeightNetwork::handleSyncFromClient);

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayer player = handler.player;
            ServerLevel level = player.serverLevel();
            sendCurrentRange(player, level);
        });

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof ServerPlayer player && world instanceof ServerLevel level) {
                sendCurrentRange(player, level);
            }
        });
    }

    public static void registerClient() {
        registerPayloadTypes();
        ClientPlayNetworking.registerGlobalReceiver(WorldHeightAppliedPayload.TYPE, WorldHeightNetwork::handleAppliedOnClient);
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

    private static void handleSyncFromClient(WorldHeightSyncPayload payload, ServerPlayNetworking.Context context) {
        ServerPlayer player = context.player();
        ServerLevel level = player.serverLevel();
        level.getServer().execute(() -> {
            WorldHeightData.updateFromClient(payload.minY(), payload.maxY());
            WorldHeightManager.applyClientSelection(player, WorldHeightData.consumeSnapshot());
            sendCurrentRange(player, level);
        });
    }

    private static void handleAppliedOnClient(WorldHeightAppliedPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            WorldHeightData.applyServerRange(payload.minY(), payload.maxY());
            ClientDimensionHeights.remember(payload.dimension(), payload.minY(), payload.maxY());
        });
    }
}
