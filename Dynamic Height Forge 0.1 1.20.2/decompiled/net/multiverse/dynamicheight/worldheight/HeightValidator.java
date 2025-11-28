/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.dimension.DimensionType
 */
package net.multiverse.dynamicheight.worldheight;

import net.minecraft.world.level.dimension.DimensionType;

public final class HeightValidator {
    public static final int SECTION_SIZE = 16;
    public static final int MAX_MIN_Y = -64;
    public static final int MIN_MAX_Y = 135;

    private HeightValidator() {
    }

    public static HeightRange sanitize(int minCandidate, int maxCandidate) {
        int min = HeightValidator.sanitizeMin(minCandidate);
        int max = HeightValidator.sanitizeMax(min, maxCandidate);
        return new HeightRange(min, max);
    }

    public static int sanitizeMin(int value) {
        int maxMin = Math.min(DimensionType.f_156652_ - 16, -64);
        int clamped = Math.max(DimensionType.f_156653_, Math.min(value, maxMin));
        return HeightValidator.snapDownToSection(clamped);
    }

    public static int sanitizeMax(int min, int value) {
        int minAllowed = Math.max(min + 16, 135);
        int maxAllowed = DimensionType.f_156652_;
        int target = Math.max(minAllowed, Math.min(value, maxAllowed));
        int minHeight = HeightValidator.snapUpToSection(Math.max(16, minAllowed - min));
        int maxHeight = HeightValidator.snapDownToSection(maxAllowed - min);
        if (maxHeight < 16) {
            maxHeight = 16;
        }
        if (minHeight > maxHeight) {
            return min + maxHeight;
        }
        int desiredHeight = HeightValidator.snapUpToSection(Math.max(16, target - min));
        if (desiredHeight < minHeight) {
            desiredHeight = minHeight;
        }
        if (desiredHeight > maxHeight) {
            desiredHeight = maxHeight;
        }
        return min + desiredHeight;
    }

    private static int snapDownToSection(int value) {
        return Math.floorDiv(value, 16) * 16;
    }

    public static int snapUpToSection(int value) {
        return Math.floorDiv(value + 16 - 1, 16) * 16;
    }

    public record HeightRange(int minY, int maxY) {
        public int height() {
            return this.maxY - this.minY;
        }
    }
}

