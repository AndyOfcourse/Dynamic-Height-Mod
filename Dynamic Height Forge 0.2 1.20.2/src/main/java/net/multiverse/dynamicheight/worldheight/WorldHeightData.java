/*
 * Decompiled with CFR 0.152.
 */
package net.multiverse.dynamicheight.worldheight;

import java.util.concurrent.atomic.AtomicBoolean;
import net.multiverse.dynamicheight.worldheight.HeightValidator;

public final class WorldHeightData {
    private static final int DEFAULT_MIN_Y = -64;
    private static final int DEFAULT_MAX_Y = 320;
    private static int minY;
    private static int maxY;
    private static final AtomicBoolean DIRTY;

    private WorldHeightData() {
    }

    public static void updateFromClient(int newMinY, int newMaxY) {
        HeightValidator.HeightRange range = HeightValidator.sanitize(newMinY, newMaxY);
        minY = range.minY();
        maxY = range.maxY();
        DIRTY.set(true);
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

    static {
        DIRTY = new AtomicBoolean(false);
        HeightValidator.HeightRange range = HeightValidator.sanitize(-64, 320);
        minY = range.minY();
        maxY = range.maxY();
    }

    public record Snapshot(int minY, int maxY) {
        public int height() {
            return this.maxY - this.minY;
        }
    }
}

