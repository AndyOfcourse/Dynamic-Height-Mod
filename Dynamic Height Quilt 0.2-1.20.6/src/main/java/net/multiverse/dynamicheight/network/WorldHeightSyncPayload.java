package net.multiverse.dynamicheight.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.multiverse.dynamicheight.DynamicHeightMod;

public record WorldHeightSyncPayload(int minY, int maxY) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<WorldHeightSyncPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(DynamicHeightMod.MOD_ID, "sync"));

    public static final StreamCodec<FriendlyByteBuf, WorldHeightSyncPayload> CODEC = StreamCodec.of(
            WorldHeightSyncPayload::write,
            WorldHeightSyncPayload::read
    );

    private static void write(FriendlyByteBuf buffer, WorldHeightSyncPayload payload) {
        buffer.writeInt(payload.minY());
        buffer.writeInt(payload.maxY());
    }

    private static WorldHeightSyncPayload read(FriendlyByteBuf buffer) {
        int minY = buffer.readInt();
        int maxY = buffer.readInt();
        return new WorldHeightSyncPayload(minY, maxY);
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
