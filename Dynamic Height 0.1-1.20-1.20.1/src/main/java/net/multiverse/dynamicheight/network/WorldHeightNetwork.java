package net.multiverse.dynamicheight.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.multiverse.dynamicheight.DynamicHeightMod;

public final class WorldHeightNetwork {
    private static final String PROTOCOL_VERSION = "1";
    private static int nextMessageId = 0;

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(DynamicHeightMod.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private WorldHeightNetwork() {
    }

    public static void register() {
        CHANNEL.registerMessage(
                nextMessageId++,
                WorldHeightSyncPacket.class,
                WorldHeightSyncPacket::encode,
                WorldHeightSyncPacket::decode,
                WorldHeightSyncPacket::handle
        );
    }
}
