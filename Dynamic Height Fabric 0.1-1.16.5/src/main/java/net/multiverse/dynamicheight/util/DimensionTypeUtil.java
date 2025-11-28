package net.multiverse.dynamicheight.util;

import net.minecraft.world.level.dimension.DimensionType;
import net.multiverse.dynamicheight.duck.DimensionTypeBridge;

public final class DimensionTypeUtil {
    private DimensionTypeUtil() {
    }

    public static DimensionType copyWithHeight(DimensionType base, int minY, int maxY) {
        DimensionTypeBridge bridge = asBridge(base);
        bridge.dynamicheight$setMinY(minY);
        bridge.dynamicheight$setHeight(maxY - minY);
        return base;
    }

    public static int minY(DimensionType type) {
        return asBridge(type).dynamicheight$getMinY();
    }

    public static int maxY(DimensionType type) {
        return asBridge(type).dynamicheight$getMaxY();
    }

    public static int height(DimensionType type) {
        return asBridge(type).dynamicheight$getHeight();
    }

    private static DimensionTypeBridge asBridge(DimensionType type) {
        if (!(type instanceof DimensionTypeBridge bridge)) {
            throw new IllegalStateException("DimensionType missing dynamic height bridge: " + type);
        }
        return bridge;
    }
}
