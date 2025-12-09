package net.multiverse.dynamicheight.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.multiverse.dynamicheight.DynamicHeightMod;
import net.multiverse.dynamicheight.client.ClientDimensionHeights;
import net.multiverse.dynamicheight.worldheight.WorldHeightData;
import net.multiverse.dynamicheight.worldheight.WorldHeightManager;
import net.multiverse.dynamicheight.worldheight.WorldHeightSavedData;

public final class WorldHeightNetwork {
    public static final ResourceLocation SYNC_CHANNEL = new ResourceLocation(DynamicHeightMod.MOD_ID, "sync");
    public static final ResourceLocation APPLIED_CHANNEL = new ResourceLocation(DynamicHeightMod.MOD_ID, "applied");

    private WorldHeightNetwork() {
    }

    public static void registerServer() {
        ServerPlayNetworking.registerGlobalReceiver(SYNC_CHANNEL, (server, player, handler, buf, responseSender) -> {
            int minY = buf.readInt();
            int maxY = buf.readInt();
            server.execute(() -> {
                WorldHeightData.updateFromClient(minY, maxY);
                WorldHeightManager.applyClientSelection(player, WorldHeightData.consumeSnapshot());
                sendCurrentRange(player, player.serverLevel());
            });
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> sendCurrentRange(handler.player, handler.player.serverLevel()));

        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> sendCurrentRange(player, destination));
    }

    public static void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(APPLIED_CHANNEL, (client, handler, buf, responseSender) -> {
            ResourceLocation dimension = buf.readResourceLocation();
            int minY = buf.readInt();
            int maxY = buf.readInt();
            client.execute(() -> {
                WorldHeightData.applyServerRange(minY, maxY);
                ClientDimensionHeights.remember(dimension, minY, maxY);
            });
        });
    }

    public static void sendCurrentRange(ServerPlayer player, ServerLevel level) {
        WorldHeightSavedData data = WorldHeightManager.currentData(level);
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeResourceLocation(level.dimension().location());
        buf.writeInt(data.minY());
        buf.writeInt(data.maxY());
        ServerPlayNetworking.send(player, APPLIED_CHANNEL, buf);
    }

    public static void broadcast(ServerLevel level, WorldHeightSavedData data) {
        for (ServerPlayer player : level.players()) {
            FriendlyByteBuf buf = PacketByteBufs.create();
            buf.writeResourceLocation(level.dimension().location());
            buf.writeInt(data.minY());
            buf.writeInt(data.maxY());
            ServerPlayNetworking.send(player, APPLIED_CHANNEL, buf);
        }
    }
}
