/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraftforge.network.NetworkRegistry
 *  net.minecraftforge.network.simple.SimpleChannel
 */
package net.multiverse.dynamicheight.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.multiverse.dynamicheight.network.WorldHeightSyncPacket;

public final class WorldHeightNetwork {
    private static final String PROTOCOL_VERSION = "1";
    private static int nextMessageId = 0;
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel((ResourceLocation)new ResourceLocation("dynamicheight", "main"), () -> "1", "1"::equals, "1"::equals);

    private WorldHeightNetwork() {
    }

    public static void register() {
        CHANNEL.registerMessage(nextMessageId++, WorldHeightSyncPacket.class, WorldHeightSyncPacket::encode, WorldHeightSyncPacket::decode, WorldHeightSyncPacket::handle);
    }
}

