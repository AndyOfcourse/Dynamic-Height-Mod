/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraftforge.network.ChannelBuilder
 *  net.minecraftforge.network.NetworkRegistry
 *  net.minecraftforge.network.SimpleChannel
 */
package net.multiverse.dynamicheight.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.SimpleChannel;
import net.multiverse.dynamicheight.network.WorldHeightSyncPacket;

public final class WorldHeightNetwork {
    private static final int PROTOCOL_VERSION = 1;
    private static int nextMessageId = 0;
    public static final SimpleChannel CHANNEL = ChannelBuilder
            .named(new ResourceLocation("dynamicheight", "main"))
            .networkProtocolVersion(PROTOCOL_VERSION)
            .clientAcceptedVersions(Channel.VersionTest.exact(PROTOCOL_VERSION))
            .serverAcceptedVersions(Channel.VersionTest.exact(PROTOCOL_VERSION))
            .simpleChannel();

    private WorldHeightNetwork() {
    }

    public static void register() {
        CHANNEL.messageBuilder(WorldHeightSyncPacket.class, nextMessageId++)
                .encoder(WorldHeightSyncPacket::encode)
                .decoder(WorldHeightSyncPacket::decode)
                .consumerNetworkThread(WorldHeightSyncPacket::handle)
                .add();
    }
}
