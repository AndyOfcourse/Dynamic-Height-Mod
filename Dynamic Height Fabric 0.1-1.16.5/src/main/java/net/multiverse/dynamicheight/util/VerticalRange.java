package net.multiverse.dynamicheight.util;

import net.multiverse.dynamicheight.worldheight.HeightValidator;

/**
 * Shared helpers for translating block Y coordinates to chunk-section indices using the
 * extended height window supported by Dynamic Height. Sections span the full absolute
 * bounds (currently -2016 .. 2016).
 */
public final class VerticalRange {
    public static final int SECTION_HEIGHT = HeightValidator.SECTION_SIZE;
    public static final int ABSOLUTE_MIN_Y = HeightValidator.ABSOLUTE_MIN_Y;
    public static final int ABSOLUTE_MAX_Y = HeightValidator.ABSOLUTE_MAX_Y;
    public static final int MIN_SECTION = Math.floorDiv(ABSOLUTE_MIN_Y, SECTION_HEIGHT);
    public static final int MAX_SECTION = Math.floorDiv(ABSOLUTE_MAX_Y - 1, SECTION_HEIGHT);
    public static final int SECTION_COUNT = MAX_SECTION - MIN_SECTION + 1;

    private VerticalRange() {
    }

    public static int clampBlockY(int y) {
        if (y < ABSOLUTE_MIN_Y) {
            return ABSOLUTE_MIN_Y;
        }
        if (y >= ABSOLUTE_MAX_Y) {
            return ABSOLUTE_MAX_Y - 1;
        }
        return y;
    }

    public static boolean inBounds(int y) {
        return y >= ABSOLUTE_MIN_Y && y < ABSOLUTE_MAX_Y;
    }

    public static int sectionIndex(int blockY) {
        return Math.floorDiv(blockY - ABSOLUTE_MIN_Y, SECTION_HEIGHT);
    }

    public static int sectionIndexFromSectionCoord(int sectionCoord) {
        return sectionCoord - MIN_SECTION;
    }

    public static int sectionCoordFromIndex(int index) {
        return MIN_SECTION + index;
    }

    public static int sectionBottomY(int index) {
        return sectionCoordFromIndex(index) << 4;
    }
}
