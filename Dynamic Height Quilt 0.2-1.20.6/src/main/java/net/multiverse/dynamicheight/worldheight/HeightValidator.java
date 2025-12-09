package net.multiverse.dynamicheight.worldheight;

/**
 * Shared sanitisation helpers for world height inputs. Ensures values respect Vanilla's hard bounds
 * and align to valid Minecraft chunk boundaries (min Y aligned to 16, height a multiple of 16).
 */
public final class HeightValidator {
    public static final int INPUT_STEP = 16;
    public static final int CHUNK_ALIGNMENT = 16;
    public static final int ABS_MIN_Y = -2016;
    public static final int ABS_MAX_Y = 2016;
    public static final int DEFAULT_MIN_Y = ABS_MIN_Y;
    public static final int DEFAULT_MAX_Y = ABS_MAX_Y;

    private HeightValidator() {
    }

    public static HeightRange sanitize(int minCandidate, int maxCandidate) {
        int min = sanitizeMin(minCandidate);
        int max = sanitizeMax(min, maxCandidate);
        return new HeightRange(min, max);
    }

    public static int sanitizeMin(int value) {
        int maxMin = ABS_MAX_Y - (CHUNK_ALIGNMENT - 1);
        int clamped = Math.max(ABS_MIN_Y, Math.min(value, maxMin));
        // DimensionType requires minY to be a multiple of 16.
        int snapped = snapToNearestMultiple(clamped, CHUNK_ALIGNMENT);
        if (snapped > maxMin) {
            snapped = snapDownToMultiple(maxMin, CHUNK_ALIGNMENT);
        }
        if (snapped < ABS_MIN_Y) {
            snapped = ABS_MIN_Y;
        }
        return snapped;
    }

    public static int sanitizeMax(int min, int value) {
        int maxAllowed = ABS_MAX_Y;
        int minAllowed = min + (CHUNK_ALIGNMENT - 1);
        int clamped = Math.max(minAllowed, Math.min(value, maxAllowed));

        // Snap input to 16-block steps to keep stable outcomes.
        clamped = snapToNearestMultiple(clamped, INPUT_STEP);
        if (clamped < minAllowed) {
            clamped = minAllowed;
        }

        // Height must be a multiple of 16, so top Y = min + (k * 16) - 1.
        int height = snapToNearestMultiple((clamped - min) + 1, CHUNK_ALIGNMENT);
        if (height < CHUNK_ALIGNMENT) {
            height = CHUNK_ALIGNMENT;
        }

        int maxHeight = snapDownToMultiple((maxAllowed - min) + 1, CHUNK_ALIGNMENT);
        if (height > maxHeight) {
            height = maxHeight;
        }

        int candidateMax = min + height - 1;
        return Math.min(candidateMax, maxAllowed);
    }

    private static int snapDownToMultiple(int value, int multiple) {
        return Math.floorDiv(value, multiple) * multiple;
    }

    private static int snapToNearestMultiple(int value, int multiple) {
        return Math.round((float) value / multiple) * multiple;
    }

    private static int snapUpToMultiple(int value, int multiple) {
        return Math.floorDiv(value + multiple - 1, multiple) * multiple;
    }

    public record HeightRange(int minY, int maxY) {
        public int height() {
            return maxY - minY + 1;
        }
    }
}
