package net.multiverse.dynamicheight.client;

import net.minecraft.client.Minecraft;
import net.multiverse.dynamicheight.network.WorldHeightSyncPayload;
import net.multiverse.dynamicheight.worldheight.WorldHeightData;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;

public final class WorldHeightClientNetworking {
    private WorldHeightClientNetworking() {
    }

    public static void onClientLoggedIn(ClientPlayerNetworkEvent.LoggingIn event) {
        sendCurrentSelection();
    }

    public static void sendCurrentSelection() {
        Minecraft client = Minecraft.getInstance();
        if (client.getConnection() == null) {
            return;
        }
        WorldHeightData.Snapshot snapshot = WorldHeightData.currentSnapshot();
        client.getConnection().send(new WorldHeightSyncPayload(snapshot.minY(), snapshot.maxY()));
    }
}
