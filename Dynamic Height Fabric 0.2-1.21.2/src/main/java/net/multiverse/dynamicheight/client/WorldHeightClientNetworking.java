package net.multiverse.dynamicheight.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.multiverse.dynamicheight.network.WorldHeightSyncPayload;
import net.multiverse.dynamicheight.network.WorldHeightAppliedPayload;
import net.multiverse.dynamicheight.worldheight.WorldHeightData;

public final class WorldHeightClientNetworking {
    private WorldHeightClientNetworking() {
    }

    public static void register() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            sendCurrentSelection();
        });
        ClientPlayNetworking.registerGlobalReceiver(WorldHeightAppliedPayload.TYPE, (payload, context) -> {
            context.client().execute(() -> handleServerApplied(payload));
        });
    }

    public static void sendCurrentSelection() {
        Minecraft client = Minecraft.getInstance();
        if (client.getConnection() == null) {
            return;
        }
        WorldHeightData.Snapshot snapshot = WorldHeightData.currentSnapshot();
        ClientPlayNetworking.send(new WorldHeightSyncPayload(snapshot.minY(), snapshot.maxY()));
    }

    private static void handleServerApplied(WorldHeightAppliedPayload payload) {
        Minecraft client = Minecraft.getInstance();
        WorldHeightData.applyServerRange(payload.minY(), payload.maxY());
        ClientDimensionHeights.remember(payload.dimension(), payload.minY(), payload.maxY());
    }
}
