package net.multiverse.dynamicheight.worldheight;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.saveddata.SavedData;
import net.multiverse.dynamicheight.util.DimensionTypeUtil;
import net.multiverse.dynamicheight.worldheight.HeightValidator.HeightRange;

/**
 * Simple holder for the configured height range per dimension.
 */
public final class WorldHeightSavedData extends SavedData {
    private static final String KEY_MIN = "MinY";
    private static final String KEY_MAX = "MaxY";

    private int minY;
    private int maxY;

    public WorldHeightSavedData(String id) {
        this(id, HeightValidator.DEFAULT_MIN_Y, HeightValidator.DEFAULT_MAX_Y);
    }

    public WorldHeightSavedData(String id, int minY, int maxY) {
        super(id);
        applyRange(minY, maxY);
    }

    public static WorldHeightSavedData createFromDimension(String id, DimensionType type) {
        int min = DimensionTypeUtil.minY(type);
        int max = DimensionTypeUtil.maxY(type);
        return new WorldHeightSavedData(id, min, max);
    }

    @Override
    public void load(CompoundTag tag) {
        int min = tag.contains(KEY_MIN) ? tag.getInt(KEY_MIN) : this.minY;
        int max = tag.contains(KEY_MAX) ? tag.getInt(KEY_MAX) : this.maxY;
        applyRange(min, max);
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
        applyRange(snapshot.minY(), snapshot.maxY());
        setDirty();
    }

    private void applyRange(int minY, int maxY) {
        HeightRange range = HeightValidator.sanitize(minY, maxY);
        this.minY = range.minY();
        this.maxY = range.maxY();
    }
}
