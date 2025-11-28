package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkBiomeContainer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import net.multiverse.dynamicheight.util.VerticalRange;
import org.jetbrains.annotations.Nullable;

public class ClientboundLevelChunkPacket implements Packet<ClientGamePacketListener> {
	private int x;
	private int z;
	private long[] availableSections;
	private CompoundTag heightmaps;
	@Nullable
	private int[] biomes;
	private byte[] buffer;
	private List<CompoundTag> blockEntitiesTags;
	private boolean fullChunk;

	public ClientboundLevelChunkPacket() {
	}

	public ClientboundLevelChunkPacket(LevelChunk levelChunk, int iMask) {
		ChunkPos chunkPos = levelChunk.getPos();
		this.x = chunkPos.x;
		this.z = chunkPos.z;
		this.fullChunk = iMask == -1 || iMask == 65535;
		this.heightmaps = new CompoundTag();

		for (Entry<Heightmap.Types, Heightmap> entry : levelChunk.getHeightmaps()) {
			if (((Heightmap.Types)entry.getKey()).sendToClient()) {
				this.heightmaps.put(((Heightmap.Types)entry.getKey()).getSerializationKey(), new LongArrayTag(((Heightmap)entry.getValue()).getRawData()));
			}
		}

		if (this.fullChunk) {
			this.biomes = levelChunk.getBiomes().writeBiomes();
		}

		this.buffer = new byte[this.calculateChunkSize(levelChunk, iMask)];
		this.availableSections = this.extractChunkData(new FriendlyByteBuf(this.getWriteBuffer()), levelChunk, iMask);
		this.blockEntitiesTags = Lists.<CompoundTag>newArrayList();

		for (Entry<BlockPos, BlockEntity> entryx : levelChunk.getBlockEntities().entrySet()) {
			BlockPos blockPos = (BlockPos)entryx.getKey();
			BlockEntity blockEntity = (BlockEntity)entryx.getValue();
			int j = blockPos.getY() >> 4;
			int word = j >> 6;
			int bit = j & 63;
			boolean included = this.isFullChunk() || (word < this.availableSections.length && (this.availableSections[word] & (1L << bit)) != 0);
			if (included) {
				CompoundTag compoundTag = blockEntity.getUpdateTag();
				this.blockEntitiesTags.add(compoundTag);
			}
		}
	}

	@Override
	public void read(FriendlyByteBuf friendlyByteBuf) throws IOException {
		this.x = friendlyByteBuf.readInt();
		this.z = friendlyByteBuf.readInt();
		this.fullChunk = friendlyByteBuf.readBoolean();
		int maskLength = friendlyByteBuf.readVarInt();
		this.availableSections = new long[maskLength];
		for (int idx = 0; idx < maskLength; idx++) {
			this.availableSections[idx] = friendlyByteBuf.readLong();
		}
		this.heightmaps = friendlyByteBuf.readNbt();
		if (this.fullChunk) {
			this.biomes = friendlyByteBuf.readVarIntArray(ChunkBiomeContainer.BIOMES_SIZE);
		}

		int i = friendlyByteBuf.readVarInt();
		if (i > 2097152) {
			throw new RuntimeException("Chunk Packet trying to allocate too much memory on read.");
		} else {
			this.buffer = new byte[i];
			friendlyByteBuf.readBytes(this.buffer);
			int j = friendlyByteBuf.readVarInt();
			this.blockEntitiesTags = Lists.<CompoundTag>newArrayList();

			for (int k = 0; k < j; k++) {
				this.blockEntitiesTags.add(friendlyByteBuf.readNbt());
			}
		}
	}

	@Override
	public void write(FriendlyByteBuf friendlyByteBuf) throws IOException {
		friendlyByteBuf.writeInt(this.x);
		friendlyByteBuf.writeInt(this.z);
		friendlyByteBuf.writeBoolean(this.fullChunk);
		friendlyByteBuf.writeVarInt(this.availableSections.length);
		for (long l : this.availableSections) {
			friendlyByteBuf.writeLong(l);
		}
		friendlyByteBuf.writeNbt(this.heightmaps);
		if (this.biomes != null) {
			friendlyByteBuf.writeVarIntArray(this.biomes);
		}

		friendlyByteBuf.writeVarInt(this.buffer.length);
		friendlyByteBuf.writeBytes(this.buffer);
		friendlyByteBuf.writeVarInt(this.blockEntitiesTags.size());

		for (CompoundTag compoundTag : this.blockEntitiesTags) {
			friendlyByteBuf.writeNbt(compoundTag);
		}
	}

	public void handle(ClientGamePacketListener clientGamePacketListener) {
		clientGamePacketListener.handleLevelChunk(this);
	}

	@Environment(EnvType.CLIENT)
	public FriendlyByteBuf getReadBuffer() {
		return new FriendlyByteBuf(Unpooled.wrappedBuffer(this.buffer));
	}

	private ByteBuf getWriteBuffer() {
		ByteBuf byteBuf = Unpooled.wrappedBuffer(this.buffer);
		byteBuf.writerIndex(0);
		return byteBuf;
	}

	public long[] extractChunkData(FriendlyByteBuf friendlyByteBuf, LevelChunk levelChunk, int legacyMask) {
		long[] mask = new long[Math.max(1, (levelChunk.getSections().length + 63) / 64)];
		LevelChunkSection[] levelChunkSections = levelChunk.getSections();

		for (int k = 0; k < levelChunkSections.length; k++) {
			LevelChunkSection levelChunkSection = levelChunkSections[k];
			boolean requested = this.isFullChunk() || (legacyMask & 1 << k) != 0;
			if (levelChunkSection != LevelChunk.EMPTY_SECTION && (!this.isFullChunk() || !levelChunkSection.isEmpty()) && requested) {
				int word = k >> 6;
				int bit = k & 63;
				mask[word] |= (1L << bit);
				levelChunkSection.write(friendlyByteBuf);
			}
		}

		return mask;
	}

	protected int calculateChunkSize(LevelChunk levelChunk, int legacyMask) {
		int j = 0;
		LevelChunkSection[] levelChunkSections = levelChunk.getSections();

		for (int k = 0; k < levelChunkSections.length; k++) {
			LevelChunkSection levelChunkSection = levelChunkSections[k];
			boolean requested = this.isFullChunk() || (legacyMask & 1 << k) != 0;
			if (levelChunkSection != LevelChunk.EMPTY_SECTION && (!this.isFullChunk() || !levelChunkSection.isEmpty()) && requested) {
				j += levelChunkSection.getSerializedSize();
			}
		}

		return j;
	}

	@Environment(EnvType.CLIENT)
	public int getX() {
		return this.x;
	}

	@Environment(EnvType.CLIENT)
	public int getZ() {
		return this.z;
	}

	@Environment(EnvType.CLIENT)
	public long[] getAvailableSections() {
		return this.availableSections;
	}

	public boolean isFullChunk() {
		return this.fullChunk;
	}

	@Environment(EnvType.CLIENT)
	public CompoundTag getHeightmaps() {
		return this.heightmaps;
	}

	@Environment(EnvType.CLIENT)
	public List<CompoundTag> getBlockEntitiesTags() {
		return this.blockEntitiesTags;
	}

	@Nullable
	@Environment(EnvType.CLIENT)
	public int[] getBiomes() {
		return this.biomes;
	}
}
