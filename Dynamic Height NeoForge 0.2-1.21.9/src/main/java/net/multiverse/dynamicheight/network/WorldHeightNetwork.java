package net.multiverse.dynamicheight.network;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.multiverse.dynamicheight.client.ClientDimensionHeights;
import net.multiverse.dynamicheight.worldheight.WorldHeightData;
import net.multiverse.dynamicheight.worldheight.WorldHeightManager;
import net.multiverse.dynamicheight.worldheight.WorldHeightSavedData;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class WorldHeightNetwork {
    private WorldHeightNetwork() {
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1.0.0");
        registrar.playToServer(WorldHeightSyncPayload.TYPE, WorldHeightSyncPayload.CODEC, WorldHeightNetwork::handleSyncFromClient);
        registrar.playToClient(WorldHeightAppliedPayload.TYPE, WorldHeightAppliedPayload.CODEC, WorldHeightNetwork::handleAppliedOnClient);
    }

    public static void sendCurrentRange(ServerPlayer player, ServerLevel level) {
        var data = WorldHeightManager.currentData(level);
        PacketDistributor.sendToPlayer(player, new WorldHeightAppliedPayload(level.dimension().location(), data.minY(), data.maxY()));
    }

    public static void broadcast(ServerLevel level, WorldHeightSavedData data) {
        for (ServerPlayer player : level.players()) {
            PacketDistributor.sendToPlayer(player, new WorldHeightAppliedPayload(level.dimension().location(), data.minY(), data.maxY()));
        }
    }

    private static void handleSyncFromClient(WorldHeightSyncPayload payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)) {
            return;
        }
        context.enqueueWork(() -> {
            WorldHeightData.updateFromClient(payload.minY(), payload.maxY());
            WorldHeightManager.applyClientSelection(player, WorldHeightData.consumeSnapshot());
            sendCurrentRange(player, (ServerLevel) player.level());
        });
    }

    private static void handleAppliedOnClient(WorldHeightAppliedPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            WorldHeightData.applyServerRange(payload.minY(), payload.maxY());
            ClientDimensionHeights.remember(payload.dimension(), payload.minY(), payload.maxY());
        });
    }

    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && player.level() instanceof ServerLevel level) {
            sendCurrentRange(player, level);
        }
    }

    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && player.level() instanceof ServerLevel level) {
            sendCurrentRange(player, level);
        }
    }
}
