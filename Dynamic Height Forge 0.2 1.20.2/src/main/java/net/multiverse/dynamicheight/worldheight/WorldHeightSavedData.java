/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.level.saveddata.SavedData
 */
package net.multiverse.dynamicheight.worldheight;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;
import net.multiverse.dynamicheight.worldheight.HeightValidator;
import net.multiverse.dynamicheight.worldheight.WorldHeightData;

public class WorldHeightSavedData
extends SavedData {
    private static final String DATA_NAME = "dynamicheight_config";
    private int minY;
    private int maxY;

    public WorldHeightSavedData() {
        this(-64, 135);
    }

    public WorldHeightSavedData(int minY, int maxY) {
        HeightValidator.HeightRange range = HeightValidator.sanitize(minY, maxY);
        this.minY = range.minY();
        this.maxY = range.maxY();
    }

    public int minY() {
        return this.minY;
    }

    public int maxY() {
        return this.maxY;
    }

    public int height() {
        return this.maxY - this.minY;
    }

    public void update(WorldHeightData.Snapshot snapshot) {
        HeightValidator.HeightRange range = HeightValidator.sanitize(snapshot.minY(), snapshot.maxY());
        this.minY = range.minY();
        this.maxY = range.maxY();
        this.setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putInt("MinY", this.minY);
        tag.putInt("MaxY", this.maxY);
        tag.putInt("Height", this.height());
        return tag;
    }

    public static WorldHeightSavedData load(CompoundTag tag) {
        int min = tag.getInt("MinY");
        int max = tag.contains("MaxY") ? tag.getInt("MaxY") : min + tag.getInt("Height");
        return new WorldHeightSavedData(min, max);
    }

    public static String dataName() {
        return DATA_NAME;
    }
}
