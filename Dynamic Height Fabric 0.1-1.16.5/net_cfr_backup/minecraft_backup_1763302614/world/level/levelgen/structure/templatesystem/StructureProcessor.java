package net.minecraft.world.level.levelgen.structure.templatesystem;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.Nullable;

public abstract class StructureProcessor {
	@Nullable
	public abstract StructureTemplate.StructureBlockInfo processBlock(
		LevelReader levelReader,
		BlockPos blockPos,
		BlockPos blockPos2,
		StructureTemplate.StructureBlockInfo structureBlockInfo,
		StructureTemplate.StructureBlockInfo structureBlockInfo2,
		StructurePlaceSettings structurePlaceSettings
	);

	protected abstract StructureProcessorType<?> getType();
}
