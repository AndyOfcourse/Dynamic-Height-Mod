package net.minecraft.world.level.chunk;

import net.minecraft.core.SectionPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LightLayer;
import org.jetbrains.annotations.Nullable;

public interface LightChunkGetter {
	@Nullable
	BlockGetter getChunkForLighting(int i, int j);

	default void onLightUpdate(LightLayer lightLayer, SectionPos sectionPos) {
	}

	BlockGetter getLevel();
}
