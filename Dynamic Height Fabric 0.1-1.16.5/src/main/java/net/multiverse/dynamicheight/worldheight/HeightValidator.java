package net.multiverse.dynamicheight.worldheight;

/**
 * Shared sanitisation helpers for world height inputs. Ensures values respect Vanilla's hard bounds
 * and align to chunk-section (16 block) increments.
 */
public final class HeightValidator {
    public static final int SECTION_SIZE = 16;
    public static final int DEFAULT_MIN_Y = 0;
    public static final int DEFAULT_MAX_Y = 256;
    public static final int MIN_MAX_Y = 135;
    public static final int ABSOLUTE_MIN_Y = -2016;
    public static final int ABSOLUTE_MAX_Y = 2016;

    private HeightValidator() {
    }

    public static HeightRange sanitize(int minCandidate, int maxCandidate) {
        int min = sanitizeMin(minCandidate);
        int max = sanitizeMax(min, maxCandidate);
        return new HeightRange(min, max);
    }

    public static int sanitizeMin(int value) {
        int maxMin = ABSOLUTE_MAX_Y + 1 - SECTION_SIZE;
        int clamped = Math.max(ABSOLUTE_MIN_Y, Math.min(value, maxMin));
        return snapDownToSection(clamped);
    }

    public static int sanitizeMax(int min, int value) {
        int minAllowed = Math.max(min + SECTION_SIZE, MIN_MAX_Y);
        int maxAllowed = ABSOLUTE_MAX_Y + 1;
        int target = Math.max(minAllowed, Math.min(value, maxAllowed));

        int minHeight = snapUpToSection(Math.max(SECTION_SIZE, minAllowed - min));
        int maxHeight = snapDownToSection(maxAllowed - min);
        if (maxHeight < SECTION_SIZE) {
            maxHeight = SECTION_SIZE;
        }

        if (minHeight > maxHeight) {
            return min + maxHeight;
        }

        int desiredHeight = snapUpToSection(Math.max(SECTION_SIZE, target - min));
        if (desiredHeight < minHeight) {
            desiredHeight = minHeight;
        }
        if (desiredHeight > maxHeight) {
            desiredHeight = maxHeight;
        }

        return min + desiredHeight;
    }

    private static int snapDownToSection(int value) {
        return Math.floorDiv(value, SECTION_SIZE) * SECTION_SIZE;
    }

    public static int snapUpToSection(int value) {
        return Math.floorDiv(value + SECTION_SIZE - 1, SECTION_SIZE) * SECTION_SIZE;
    }

    public record HeightRange(int minY, int maxY) {
        public int height() {
            return maxY - minY;
        }
    }
}
