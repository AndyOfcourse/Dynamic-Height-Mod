package net.multiverse.dynamicheight.worldheight;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.saveddata.SavedData;
import net.multiverse.dynamicheight.worldheight.HeightValidator.HeightRange;

/**
 * Simple holder for the configured height range per dimension.
 */
public final class WorldHeightSavedData extends SavedData {
    private static final String KEY_MIN = "MinY";
    private static final String KEY_MAX = "MaxY";

    private int minY;
    private int maxY;

    public WorldHeightSavedData() {
        this(HeightValidator.DEFAULT_MIN_Y, HeightValidator.MIN_MAX_Y);
    }

    public WorldHeightSavedData(int minY, int maxY) {
        HeightRange range = HeightValidator.sanitize(minY, maxY);
        this.minY = range.minY();
        this.maxY = range.maxY();
    }

    public static WorldHeightSavedData createFromDimension(DimensionType type) {
        int min = type.minY();
        int max = min + type.height();
        return new WorldHeightSavedData(min, max);
    }

    public static WorldHeightSavedData load(CompoundTag tag, DimensionType type) {
        int min = tag.contains(KEY_MIN) ? tag.getInt(KEY_MIN) : type.minY();
        int max = tag.contains(KEY_MAX) ? tag.getInt(KEY_MAX) : type.minY() + type.height();
        return new WorldHeightSavedData(min, max);
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putInt(KEY_MIN, minY);
        tag.putInt(KEY_MAX, maxY);
        return tag;
    }

    public int minY() {
        return minY;
    }

    public int maxY() {
        return maxY;
    }

    public int height() {
        return maxY - minY;
    }

    public void update(WorldHeightData.Snapshot snapshot) {
        HeightRange range = HeightValidator.sanitize(snapshot.minY(), snapshot.maxY());
        this.minY = range.minY();
        this.maxY = range.maxY();
        setDirty();
    }
}
