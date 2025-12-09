package net.multiverse.dynamicheight.client;

import net.multiverse.dynamicheight.worldheight.HeightValidator;
import net.multiverse.dynamicheight.worldheight.HeightValidator.HeightRange;
import net.multiverse.dynamicheight.worldheight.WorldHeightData;

/**
 * Simple holder for the user-selected world height configuration that we capture on the create world screen.
 * The values will later be passed to the server side when the world actually initializes.
 */
public final class WorldHeightSettings {
    private static final int DEFAULT_MIN_Y = -64;
    private static final int DEFAULT_MAX_Y = 319;

    private static int minY;
    private static int maxY;

    static {
        HeightRange range = HeightValidator.sanitize(DEFAULT_MIN_Y, DEFAULT_MAX_Y);
        minY = range.minY();
        maxY = range.maxY();
    }

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
        HeightRange range = HeightValidator.sanitize(value, maxY);
        minY = range.minY();
        maxY = range.maxY();
        syncSharedState();
    }

    public static void setMaxY(int value) {
        HeightRange range = HeightValidator.sanitize(minY, value);
        minY = range.minY();
        maxY = range.maxY();
        syncSharedState();
    }

    public static void resetToDefaults() {
        resetToDefaults(true);
    }

    public static void resetToDefaults(boolean syncSharedState) {
        HeightRange range = HeightValidator.sanitize(DEFAULT_MIN_Y, DEFAULT_MAX_Y);
        minY = range.minY();
        maxY = range.maxY();
        if (syncSharedState) {
            syncSharedState();
        }
    }

    private static void syncSharedState() {
        WorldHeightData.updateFromClient(minY, maxY);
    }
}
