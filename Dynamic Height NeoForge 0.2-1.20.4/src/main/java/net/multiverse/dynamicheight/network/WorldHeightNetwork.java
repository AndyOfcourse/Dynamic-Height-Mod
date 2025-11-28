package net.multiverse.dynamicheight.network;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.multiverse.dynamicheight.DynamicHeightMod;
import net.multiverse.dynamicheight.client.ClientDimensionHeights;
import net.multiverse.dynamicheight.worldheight.WorldHeightData;
import net.multiverse.dynamicheight.worldheight.WorldHeightManager;
import net.multiverse.dynamicheight.worldheight.WorldHeightSavedData;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.handling.IPlayPayloadHandler;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

public final class WorldHeightNetwork {
    private WorldHeightNetwork() {
    }

    public static void register(RegisterPayloadHandlerEvent event) {
        IPayloadRegistrar registrar = event.registrar(DynamicHeightMod.MOD_ID).versioned("1.0.0");
        registrar.play(WorldHeightSyncPayload.ID, WorldHeightSyncPayload::new, wrapServerHandler(WorldHeightNetwork::handleSyncFromClient));
        registrar.play(WorldHeightAppliedPayload.ID, WorldHeightAppliedPayload::new, wrapClientHandler(WorldHeightNetwork::handleAppliedOnClient));
    }

    public static void sendCurrentRange(ServerPlayer player, ServerLevel level) {
        var data = WorldHeightManager.currentData(level);
        PacketDistributor.PLAYER.with(player).send(new WorldHeightAppliedPayload(level.dimension().location(), data.minY(), data.maxY()));
    }

    public static void broadcast(ServerLevel level, WorldHeightSavedData data) {
        for (ServerPlayer player : level.players()) {
            PacketDistributor.PLAYER.with(player).send(new WorldHeightAppliedPayload(level.dimension().location(), data.minY(), data.maxY()));
        }
    }

    private static void handleSyncFromClient(WorldHeightSyncPayload payload, PlayPayloadContext context) {
        context.workHandler().execute(() -> context.player().filter(p -> p instanceof ServerPlayer).map(p -> (ServerPlayer) p).ifPresent(player -> {
            WorldHeightData.updateFromClient(payload.minY(), payload.maxY());
            WorldHeightManager.applyClientSelection(player, WorldHeightData.consumeSnapshot());
            sendCurrentRange(player, (ServerLevel) player.level());
        }));
    }

    private static void handleAppliedOnClient(WorldHeightAppliedPayload payload, PlayPayloadContext context) {
        context.workHandler().execute(() -> {
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

    private static <T extends net.minecraft.network.protocol.common.custom.CustomPacketPayload> IPlayPayloadHandler<T> wrapServerHandler(IPlayPayloadHandler<T> handler) {
        return (payload, context) -> {
            if (context.flow().isServerbound()) {
                handler.handle(payload, context);
            }
        };
    }

    private static <T extends net.minecraft.network.protocol.common.custom.CustomPacketPayload> IPlayPayloadHandler<T> wrapClientHandler(IPlayPayloadHandler<T> handler) {
        return (payload, context) -> {
            if (context.flow().isClientbound()) {
                handler.handle(payload, context);
            }
        };
    }
}
