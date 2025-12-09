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
        this.m_77762_();
    }

    public CompoundTag m_7176_(CompoundTag tag) {
        tag.m_128405_("MinY", this.minY);
        tag.m_128405_("MaxY", this.maxY);
        tag.m_128405_("Height", this.height());
        return tag;
    }

    public static WorldHeightSavedData load(CompoundTag tag) {
        int min = tag.m_128451_("MinY");
        int max = tag.m_128441_("MaxY") ? tag.m_128451_("MaxY") : min + tag.m_128451_("Height");
        return new WorldHeightSavedData(min, max);
    }

    public static String dataName() {
        return DATA_NAME;
    }
}

