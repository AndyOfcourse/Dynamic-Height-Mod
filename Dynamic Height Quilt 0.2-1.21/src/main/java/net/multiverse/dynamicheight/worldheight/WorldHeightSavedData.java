package net.multiverse.dynamicheight.worldheight;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.saveddata.SavedData;
import net.multiverse.dynamicheight.worldheight.HeightValidator.HeightRange;

/**
 * Persistent holder for the configured height range per dimension.
 */
public final class WorldHeightSavedData extends SavedData {
    private static final String KEY_MIN = "MinY";
    private static final String KEY_MAX = "MaxY";
    private static final Codec<WorldHeightSavedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf(KEY_MIN).forGetter(WorldHeightSavedData::minY),
            Codec.INT.fieldOf(KEY_MAX).forGetter(WorldHeightSavedData::maxY)
    ).apply(instance, WorldHeightSavedData::new));

    private int minY;
    private int maxY;

    private static SavedData.Factory<WorldHeightSavedData> factory(DimensionType dimensionType) {
        return new SavedData.Factory<>(
                () -> new WorldHeightSavedData(dimensionType.minY(), dimensionType.minY() + dimensionType.height() - 1),
                WorldHeightSavedData::load,
                DataFixTypes.LEVEL
        );
    }

    public WorldHeightSavedData() {
        this(HeightValidator.DEFAULT_MIN_Y, HeightValidator.DEFAULT_MAX_Y);
    }

    public WorldHeightSavedData(int minY, int maxY) {
        applyRangeInternal(HeightValidator.sanitize(minY, maxY));
    }

    public static SavedData.Factory<WorldHeightSavedData> typeFor(ResourceKey<Level> dimension, DimensionType dimensionType) {
        return factory(dimensionType);
    }

    public int minY() {
        return minY;
    }

    public int maxY() {
        return maxY;
    }

    public int height() {
        return maxY - minY + 1;
    }

    public void update(WorldHeightData.Snapshot snapshot) {
        applyRange(snapshot.minY(), snapshot.maxY());
    }

    public boolean applyRange(int newMinY, int newMaxY) {
        return applyRangeInternal(HeightValidator.sanitize(newMinY, newMaxY));
    }

    private boolean applyRangeInternal(HeightRange range) {
        if (range.minY() == this.minY && range.maxY() == this.maxY) {
            return false;
        }
        this.minY = range.minY();
        this.maxY = range.maxY();
        setDirty();
        return true;
    }

    public static String storageKey(ResourceKey<Level> dimension) {
        String path = dimension.location().getPath().replace('/', '_');
        return "dynamic_height_" + dimension.location().getNamespace() + "_" + path;
    }

    private static WorldHeightSavedData load(CompoundTag tag, net.minecraft.core.HolderLookup.Provider lookup) {
        return CODEC.parse(NbtOps.INSTANCE, tag).result().orElseGet(WorldHeightSavedData::new);
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider lookup) {
        tag.putInt(KEY_MIN, minY);
        tag.putInt(KEY_MAX, maxY);
        return tag;
    }
}
