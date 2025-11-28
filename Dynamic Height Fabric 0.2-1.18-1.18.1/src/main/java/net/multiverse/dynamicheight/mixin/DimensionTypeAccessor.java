package net.multiverse.dynamicheight.mixin;

import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DimensionType.class)
public interface DimensionTypeAccessor {
    @Mutable
    @Accessor("minY")
    void dynamicheight$setMinY(int value);

    @Mutable
    @Accessor("height")
    void dynamicheight$setHeight(int value);

    @Mutable
    @Accessor("logicalHeight")
    void dynamicheight$setLogicalHeight(int value);
}
