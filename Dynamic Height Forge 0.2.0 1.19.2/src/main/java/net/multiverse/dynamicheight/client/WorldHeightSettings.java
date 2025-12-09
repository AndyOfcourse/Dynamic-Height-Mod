/*
 * Decompiled with CFR 0.152.
 */
package net.multiverse.dynamicheight.client;

import net.multiverse.dynamicheight.worldheight.HeightValidator;
import net.multiverse.dynamicheight.worldheight.WorldHeightData;

public final class WorldHeightSettings {
    private static final int DEFAULT_MIN_Y = -64;
    private static final int DEFAULT_MAX_Y = 320;
    private static int minY;
    private static int maxY;

    private WorldHeightSettings() {
    }

    public static int getMinY() {
        return minY;
    }

    public static int getMaxY() {
        return maxY;
    }

    public static int getHeight() {
        return maxY - minY;
    }

    public static void setMinY(int value) {
        HeightValidator.HeightRange range = HeightValidator.sanitize(value, maxY);
        minY = range.minY();
        maxY = range.maxY();
        WorldHeightSettings.syncSharedState();
    }

    public static void setMaxY(int value) {
        HeightValidator.HeightRange range = HeightValidator.sanitize(minY, value);
        minY = range.minY();
        maxY = range.maxY();
        WorldHeightSettings.syncSharedState();
    }

    public static void resetToDefaults() {
        HeightValidator.HeightRange range = HeightValidator.sanitize(-64, 320);
        minY = range.minY();
        maxY = range.maxY();
        WorldHeightSettings.syncSharedState();
    }

    private static void syncSharedState() {
        WorldHeightData.updateFromClient(minY, maxY);
    }

    static {
        HeightValidator.HeightRange range = HeightValidator.sanitize(-64, 320);
        minY = range.minY();
        maxY = range.maxY();
    }
}

