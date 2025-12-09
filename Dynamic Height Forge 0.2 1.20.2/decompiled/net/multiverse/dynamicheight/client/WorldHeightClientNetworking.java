/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.client.event.ClientPlayerNetworkEvent$LoggingIn
 *  net.minecraftforge.common.MinecraftForge
 */
package net.multiverse.dynamicheight.client;

import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.multiverse.dynamicheight.client.WorldHeightSettings;
import net.multiverse.dynamicheight.network.WorldHeightNetwork;
import net.multiverse.dynamicheight.network.WorldHeightSyncPacket;

public final class WorldHeightClientNetworking {
    private WorldHeightClientNetworking() {
    }

    public static void register() {
        MinecraftForge.EVENT_BUS.addListener(WorldHeightClientNetworking::onLogin);
    }

    private static void onLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        WorldHeightNetwork.CHANNEL.sendToServer((Object)new WorldHeightSyncPacket(WorldHeightSettings.getMinY(), WorldHeightSettings.getMaxY()));
    }
}

