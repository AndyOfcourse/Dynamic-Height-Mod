/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraftforge.network.NetworkEvent$Context
 */
package net.multiverse.dynamicheight.network;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.multiverse.dynamicheight.worldheight.WorldHeightData;
import net.multiverse.dynamicheight.worldheight.WorldHeightManager;

public record WorldHeightSyncPacket(int minY, int maxY) {
    public static void encode(WorldHeightSyncPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.minY);
        buffer.writeInt(packet.maxY);
    }

    public static WorldHeightSyncPacket decode(FriendlyByteBuf buffer) {
        int minY = buffer.readInt();
        int maxY = buffer.readInt();
        return new WorldHeightSyncPacket(minY, maxY);
    }

    public static void handle(WorldHeightSyncPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer sender = context.getSender();
            if (sender != null) {
                WorldHeightData.updateFromClient(packet.minY, packet.maxY);
                WorldHeightManager.applyClientSelection(sender, WorldHeightData.consumeSnapshot());
            }
        });
        context.setPacketHandled(true);
    }
}

