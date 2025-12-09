package net.multiverse.dynamicheight.worldheight;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
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

    private int minY;
    private int maxY;

    public WorldHeightSavedData() {
        this(HeightValidator.DEFAULT_MIN_Y, HeightValidator.DEFAULT_MAX_Y);
    }

    public WorldHeightSavedData(int minY, int maxY) {
        applyRangeInternal(HeightValidator.sanitize(minY, maxY));
    }

    public static WorldHeightSavedData createFromDimension(DimensionType type) {
        return new WorldHeightSavedData(type.minY(), type.minY() + type.height() - 1);
    }

    public static WorldHeightSavedData load(CompoundTag tag, DimensionType type) {
        int min = tag.contains(KEY_MIN) ? tag.getInt(KEY_MIN) : type.minY();
        int max = tag.contains(KEY_MAX) ? tag.getInt(KEY_MAX) : type.minY() + type.height() - 1;
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
}
