package net.multiverse.dynamicheight.worldheight;

import net.minecraft.world.level.dimension.DimensionType;

/**
 * Shared sanitisation helpers for world height inputs. Ensures values respect Vanilla's hard bounds
 * and align to chunk-section (16 block) increments.
 */
public final class HeightValidator {
    public static final int SECTION_SIZE = 16;
    public static final int DEFAULT_MIN_Y = -64;
    public static final int MIN_MAX_Y = 135;
    public static final int ABS_MIN_Y = -2016;
    public static final int ABS_MAX_Y = 2016;

    private HeightValidator() {
    }

    public static HeightRange sanitize(int minCandidate, int maxCandidate) {
        int min = sanitizeMin(minCandidate);
        int max = sanitizeMax(min, maxCandidate);
        return new HeightRange(min, max);
    }

    public static int sanitizeMin(int value) {
        int minAllowed = ABS_MIN_Y;
        int maxMin = ABS_MAX_Y - SECTION_SIZE;
        int clamped = Math.max(minAllowed, Math.min(value, maxMin));
        return snapDownToSection(clamped);
    }

    public static int sanitizeMax(int min, int value) {
        int minAllowed = Math.max(min + SECTION_SIZE - 1, MIN_MAX_Y);
        int maxAllowed = ABS_MAX_Y;
        return Math.max(minAllowed, Math.min(value, maxAllowed));
    }

    private static int snapDownToSection(int value) {
        return Math.floorDiv(value, SECTION_SIZE) * SECTION_SIZE;
    }

    public static int snapUpToSection(int value) {
        return Math.floorDiv(value + SECTION_SIZE - 1, SECTION_SIZE) * SECTION_SIZE;
    }

    public record HeightRange(int minY, int maxY) {
        public int height() {
            return maxY - minY + 1;
        }
    }
}
