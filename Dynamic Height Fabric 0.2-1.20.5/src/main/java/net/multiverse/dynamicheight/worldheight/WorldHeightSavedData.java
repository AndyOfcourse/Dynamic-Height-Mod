package net.multiverse.dynamicheight.worldheight;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.datafix.DataFixTypes;
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

    public static SavedData.Factory<WorldHeightSavedData> factory(DimensionType dimensionType) {
        return new SavedData.Factory<>(
                () -> new WorldHeightSavedData(dimensionType.minY(), dimensionType.minY() + dimensionType.height()),
                (tag, provider) -> load(tag, dimensionType),
                DataFixTypes.LEVEL
        );
    }

    public WorldHeightSavedData() {
        this(HeightValidator.DEFAULT_MIN_Y, HeightValidator.MIN_MAX_Y);
    }

    public WorldHeightSavedData(int minY, int maxY) {
        applyRangeInternal(HeightValidator.sanitize(minY, maxY));
    }

    private static WorldHeightSavedData load(CompoundTag tag, DimensionType dimensionType) {
        int fallbackMin = dimensionType.minY();
        int fallbackMax = fallbackMin + dimensionType.height();
        int min = tag.contains(KEY_MIN) ? tag.getInt(KEY_MIN) : fallbackMin;
        int max = tag.contains(KEY_MAX) ? tag.getInt(KEY_MAX) : fallbackMax;
        return new WorldHeightSavedData(min, max);
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
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
}
