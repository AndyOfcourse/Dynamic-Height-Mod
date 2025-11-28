package net.multiverse.dynamicheight.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.multiverse.dynamicheight.DynamicHeightMod;

public record WorldHeightAppliedPayload(ResourceLocation dimension, int minY, int maxY) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(DynamicHeightMod.MOD_ID, "applied");

    public WorldHeightAppliedPayload(net.minecraft.network.FriendlyByteBuf buffer) {
        this(buffer.readResourceLocation(), buffer.readInt(), buffer.readInt());
    }

    @Override
    public void write(net.minecraft.network.FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(dimension);
        buffer.writeInt(minY);
        buffer.writeInt(maxY);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
