package net.multiverse.dynamicheight.duck;

/**
 * Simple bridge that exposes custom vertical range metadata on {@link net.minecraft.world.level.dimension.DimensionType}.
 * In modern Minecraft versions the values are part of the DimensionType itself, but 1.16 requires us to synthesise them.
 */
public interface DimensionTypeBridge {
    int dynamicheight$getMinY();

    int dynamicheight$getMaxY();

    int dynamicheight$getHeight();

    void dynamicheight$setMinY(int minY);

    void dynamicheight$setHeight(int height);
}
