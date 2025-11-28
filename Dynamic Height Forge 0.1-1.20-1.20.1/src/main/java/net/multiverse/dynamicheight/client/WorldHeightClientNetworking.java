package net.multiverse.dynamicheight.client;

import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.multiverse.dynamicheight.network.WorldHeightNetwork;
import net.multiverse.dynamicheight.network.WorldHeightSyncPacket;

/**
 * Sends the latest client-side selection to the server as soon as the player joins a world.
 */
public final class WorldHeightClientNetworking {
    private WorldHeightClientNetworking() {
    }

    public static void register() {
        MinecraftForge.EVENT_BUS.addListener(WorldHeightClientNetworking::onLogin);
    }

    private static void onLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        WorldHeightNetwork.CHANNEL.sendToServer(new WorldHeightSyncPacket(
                WorldHeightSettings.getMinY(),
                WorldHeightSettings.getMaxY()
        ));
    }
}
