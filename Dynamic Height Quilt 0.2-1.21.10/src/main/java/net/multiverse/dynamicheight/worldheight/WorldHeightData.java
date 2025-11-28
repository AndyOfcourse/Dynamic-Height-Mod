package net.multiverse.dynamicheight.worldheight;

import java.util.concurrent.atomic.AtomicBoolean;

import net.multiverse.dynamicheight.worldheight.HeightValidator.HeightRange;

/**
 * Holds the latest parameters selected on the client and exposes them to server logic when the world boots.
 */
public final class WorldHeightData {
    private static final int DEFAULT_MIN_Y = HeightValidator.DEFAULT_MIN_Y;
    private static final int DEFAULT_MAX_Y = HeightValidator.DEFAULT_MAX_Y;

    private static int minY;
    private static int maxY;
    private static final AtomicBoolean DIRTY = new AtomicBoolean(false);

    private WorldHeightData() {
    }

    static {
        HeightRange range = HeightValidator.sanitize(DEFAULT_MIN_Y, DEFAULT_MAX_Y);
        minY = range.minY();
        maxY = range.maxY();
    }

    public static void updateFromClient(int newMinY, int newMaxY) {
        HeightRange range = HeightValidator.sanitize(newMinY, newMaxY);
        applyRange(range, true);
    }

    public static void applyServerRange(int newMinY, int newMaxY) {
        HeightRange range = HeightValidator.sanitize(newMinY, newMaxY);
        applyRange(range, false);
    }

    public static Snapshot consumeSnapshot() {
        DIRTY.set(false);
        return new Snapshot(minY, maxY);
    }

    public static Snapshot currentSnapshot() {
        return new Snapshot(minY, maxY);
    }

    public static boolean hasPendingClientSelection() {
        return DIRTY.get();
    }

    private static void applyRange(HeightRange range, boolean markDirty) {
        minY = range.minY();
        maxY = range.maxY();
        DIRTY.set(markDirty);
    }

    public record Snapshot(int minY, int maxY) {
        public int height() {
            return maxY - minY + 1;
        }
    }
}
