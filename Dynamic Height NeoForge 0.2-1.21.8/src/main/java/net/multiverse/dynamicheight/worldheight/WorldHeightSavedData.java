package net.multiverse.dynamicheight.worldheight;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
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

    private static SavedDataType<WorldHeightSavedData> type(ResourceKey<Level> dimension, DimensionType dimensionType) {
        return new SavedDataType<>(
                storageKey(dimension),
                context -> new WorldHeightSavedData(dimensionType.minY(), dimensionType.minY() + dimensionType.height()),
                ctx -> CODEC,
                DataFixTypes.LEVEL
        );
    }

    public WorldHeightSavedData() {
        this(HeightValidator.DEFAULT_MIN_Y, HeightValidator.MIN_MAX_Y);
    }

    public WorldHeightSavedData(int minY, int maxY) {
        applyRangeInternal(HeightValidator.sanitize(minY, maxY));
    }

    public static SavedDataType<WorldHeightSavedData> typeFor(ResourceKey<Level> dimension, DimensionType dimensionType) {
        return type(dimension, dimensionType);
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

    private static String storageKey(ResourceKey<Level> dimension) {
        String path = dimension.location().getPath().replace('/', '_');
        return "dynamic_height_" + dimension.location().getNamespace() + "_" + path;
    }
}
