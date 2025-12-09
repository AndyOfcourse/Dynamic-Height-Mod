package net.multiverse.dynamicheight.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.multiverse.dynamicheight.worldheight.WorldHeightData;
import net.multiverse.dynamicheight.network.WorldHeightSyncPayload;

public final class WorldHeightClientNetworking {
    private WorldHeightClientNetworking() {
    }

    public static void register() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> sendCurrentSelection());
    }

    public static void sendCurrentSelection() {
        Minecraft client = Minecraft.getInstance();
        if (client.getConnection() == null) {
            return;
        }
        WorldHeightData.Snapshot snapshot = WorldHeightData.currentSnapshot();
        ClientPlayNetworking.send(new WorldHeightSyncPayload(snapshot.minY(), snapshot.maxY()));
    }
}
