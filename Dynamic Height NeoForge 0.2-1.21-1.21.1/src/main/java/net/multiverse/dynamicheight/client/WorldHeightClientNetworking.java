package net.multiverse.dynamicheight.client;

import net.minecraft.client.Minecraft;
import net.multiverse.dynamicheight.network.WorldHeightSyncPayload;
import net.multiverse.dynamicheight.worldheight.WorldHeightData;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public final class WorldHeightClientNetworking {
    private WorldHeightClientNetworking() {
    }

    public static void onClientLoggedIn(ClientPlayerNetworkEvent.LoggingIn event) {
        sendCurrentSelection();
    }

    public static void sendCurrentSelection() {
        Minecraft client = Minecraft.getInstance();
        if (client.getConnection() == null || !WorldHeightData.hasPendingClientSelection()) {
            return;
        }
        WorldHeightData.Snapshot snapshot = WorldHeightData.currentSnapshot();
        PacketDistributor.sendToServer(new WorldHeightSyncPayload(snapshot.minY(), snapshot.maxY()));
    }
}
