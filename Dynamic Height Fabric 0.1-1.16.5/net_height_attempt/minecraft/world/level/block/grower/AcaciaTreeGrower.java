package net.minecraft.world.level.block.grower;

import java.util.Random;
import net.minecraft.data.worldgen.Features;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import org.jetbrains.annotations.Nullable;

public class AcaciaTreeGrower extends AbstractTreeGrower {
   @Nullable
   @Override
   protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random random, boolean bl) {
      return Features.ACACIA;
   }
}
