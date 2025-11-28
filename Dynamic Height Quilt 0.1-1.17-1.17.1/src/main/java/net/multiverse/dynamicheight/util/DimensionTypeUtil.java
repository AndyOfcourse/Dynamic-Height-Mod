package net.multiverse.dynamicheight.util;

import net.minecraft.world.level.dimension.DimensionType;
import net.multiverse.dynamicheight.mixin.DimensionTypeAccessor;

/**
 * Simplified helpers for tweaking dimension type bounds on 1.18.1, where the
 * newer holder-based registry APIs are not available yet.
 */
public final class DimensionTypeUtil {
    private DimensionTypeUtil() {
    }

    public static DimensionType copyWithHeight(DimensionType base, int minY, int maxY) {
        int height = maxY - minY + 1;
        DimensionTypeAccessor accessor = (DimensionTypeAccessor) (Object) base;
        accessor.dynamicheight$setMinY(minY);
        accessor.dynamicheight$setHeight(height);
        accessor.dynamicheight$setLogicalHeight(Math.max(16, height));
        return base;
    }

    public static DimensionType bindUpdatedDimensionType(DimensionType original, DimensionType updated) {
        return updated;
    }
}
