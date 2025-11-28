package net.multiverse.dynamicheight.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.multiverse.dynamicheight.network.WorldHeightNetwork;
import net.multiverse.dynamicheight.worldheight.WorldHeightData;

public final class WorldHeightClientNetworking {
    private WorldHeightClientNetworking() {
    }

    public static void register() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> sendCurrentSelection());
        ClientPlayNetworking.registerGlobalReceiver(WorldHeightNetwork.APPLIED_CHANNEL, (client, handler, buf, responseSender) -> {
            ResourceLocation dimensionId = buf.readResourceLocation();
            int minY = buf.readInt();
            int maxY = buf.readInt();
            client.execute(() -> handleServerApplied(dimensionId, minY, maxY));
        });
    }

    public static void sendCurrentSelection() {
        Minecraft client = Minecraft.getInstance();
        if (client.getConnection() == null) {
            return;
        }
        WorldHeightData.Snapshot snapshot = WorldHeightData.currentSnapshot();
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeInt(snapshot.minY());
        buf.writeInt(snapshot.maxY());
        ClientPlayNetworking.send(WorldHeightNetwork.SYNC_CHANNEL, buf);
    }

    private static void handleServerApplied(ResourceLocation dimension, int minY, int maxY) {
        WorldHeightData.applyServerRange(minY, maxY);
        ClientDimensionHeights.remember(dimension, minY, maxY);
    }
}
