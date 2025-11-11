package net.multiverse.dynamicheight.worldheight;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

import net.multiverse.dynamicheight.worldheight.HeightValidator.HeightRange;

/**
 * Persists the chosen height configuration inside the world's data storage so the same values are reused on reload.
 */
public class WorldHeightSavedData extends SavedData {
    private static final String DATA_NAME = "dynamicheight_config";

    private int minY;
    private int maxY;

    public WorldHeightSavedData() {
        this(HeightValidator.MAX_MIN_Y, HeightValidator.MIN_MAX_Y);
    }

    public WorldHeightSavedData(int minY, int maxY) {
        HeightRange range = HeightValidator.sanitize(minY, maxY);
        this.minY = range.minY();
        this.maxY = range.maxY();
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

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putInt("MinY", minY);
        tag.putInt("MaxY", maxY);
        tag.putInt("Height", height());
        return tag;
    }

    public static WorldHeightSavedData load(CompoundTag tag) {
        int min = tag.getInt("MinY");
        int max;
        if (tag.contains("MaxY")) {
            max = tag.getInt("MaxY");
        } else {
            max = min + tag.getInt("Height");
        }
        return new WorldHeightSavedData(min, max);
    }

    public static String dataName() {
        return DATA_NAME;
    }
}
