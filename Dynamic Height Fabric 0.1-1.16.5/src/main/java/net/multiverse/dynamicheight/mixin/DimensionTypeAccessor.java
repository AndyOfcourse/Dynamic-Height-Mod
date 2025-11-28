package net.multiverse.dynamicheight.mixin;

import java.util.OptionalLong;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.BiomeZoomer;
import net.minecraft.world.level.dimension.DimensionType;
import net.multiverse.dynamicheight.duck.DimensionTypeBridge;

/**
 * Adds the notion of a custom vertical range onto {@link DimensionType} so we can mirror
 * the 1.19 APIs that this project was originally written against.
 */
@Mixin(DimensionType.class)
public abstract class DimensionTypeAccessor implements DimensionTypeBridge {
    @Unique
    private int dynamicheight$minY;
    @Unique
    private int dynamicheight$height;

    @Inject(
            method = "<init>(Ljava/util/OptionalLong;ZZZZDZZZZZILnet/minecraft/world/level/biome/BiomeZoomer;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;F)V",
            at = @At("RETURN")
    )
    private void dynamicheight$initCustomHeights(
            OptionalLong optionalLong,
            boolean hasSkyLight,
            boolean hasCeiling,
            boolean ultraWarm,
            boolean natural,
            double coordinateScale,
            boolean createDragonFight,
            boolean piglinSafe,
            boolean bedWorks,
            boolean respawnAnchorWorks,
            boolean hasRaids,
            int logicalHeight,
            BiomeZoomer biomeZoomer,
            ResourceLocation infiniburn,
            ResourceLocation effectsLocation,
            float ambientLight,
            CallbackInfo ci) {
        this.dynamicheight$minY = 0;
        this.dynamicheight$height = logicalHeight;
    }

    @Override
    public int dynamicheight$getMinY() {
        return this.dynamicheight$minY;
    }

    @Override
    public int dynamicheight$getMaxY() {
        return this.dynamicheight$minY + this.dynamicheight$height;
    }

    @Override
    public int dynamicheight$getHeight() {
        return this.dynamicheight$height;
    }

    @Override
    public void dynamicheight$setMinY(int minY) {
        this.dynamicheight$minY = minY;
    }

    @Override
    public void dynamicheight$setHeight(int height) {
        this.dynamicheight$height = height;
    }
}
