package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RuleProcessor extends StructureProcessor {
   public static final Codec<RuleProcessor> CODEC = ProcessorRule.CODEC
      .listOf()
      .fieldOf("rules")
      .xmap(RuleProcessor::new, ruleProcessor -> ruleProcessor.rules)
      .codec();
   private final ImmutableList<ProcessorRule> rules;

   public RuleProcessor(List<? extends ProcessorRule> list) {
      this.rules = ImmutableList.copyOf(list);
   }

   @Nullable
   @Override
   public StructureTemplate.StructureBlockInfo processBlock(
      LevelReader levelReader,
      BlockPos blockPos,
      BlockPos blockPos2,
      StructureTemplate.StructureBlockInfo structureBlockInfo,
      StructureTemplate.StructureBlockInfo structureBlockInfo2,
      StructurePlaceSettings structurePlaceSettings
   ) {
      Random random = new Random(Mth.getSeed(structureBlockInfo2.pos));
      BlockState blockState = levelReader.getBlockState(structureBlockInfo2.pos);
      UnmodifiableIterator var9 = this.rules.iterator();

      while(var9.hasNext()) {
         ProcessorRule processorRule = (ProcessorRule)var9.next();
         if (processorRule.test(structureBlockInfo2.state, blockState, structureBlockInfo.pos, structureBlockInfo2.pos, blockPos2, random)) {
            return new StructureTemplate.StructureBlockInfo(structureBlockInfo2.pos, processorRule.getOutputState(), processorRule.getOutputTag());
         }
      }

      return structureBlockInfo2;
   }

   @Override
   protected StructureProcessorType<?> getType() {
      return StructureProcessorType.RULE;
   }
}
