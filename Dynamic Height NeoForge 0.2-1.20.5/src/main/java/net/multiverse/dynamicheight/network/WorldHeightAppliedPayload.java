package net.multiverse.dynamicheight.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.multiverse.dynamicheight.DynamicHeightMod;

public record WorldHeightAppliedPayload(ResourceLocation dimension, int minY, int maxY) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<WorldHeightAppliedPayload> TYPE =
            new CustomPacketPayload.Type<>(new ResourceLocation(DynamicHeightMod.MOD_ID, "applied"));

    public static final StreamCodec<FriendlyByteBuf, WorldHeightAppliedPayload> CODEC = StreamCodec.of(
            WorldHeightAppliedPayload::write,
            WorldHeightAppliedPayload::read
    );

    private static void write(FriendlyByteBuf buffer, WorldHeightAppliedPayload payload) {
        buffer.writeResourceLocation(payload.dimension());
        buffer.writeInt(payload.minY());
        buffer.writeInt(payload.maxY());
    }

    private static WorldHeightAppliedPayload read(FriendlyByteBuf buffer) {
        ResourceLocation dimension = buffer.readResourceLocation();
        int minY = buffer.readInt();
        int maxY = buffer.readInt();
        return new WorldHeightAppliedPayload(dimension, minY, maxY);
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
