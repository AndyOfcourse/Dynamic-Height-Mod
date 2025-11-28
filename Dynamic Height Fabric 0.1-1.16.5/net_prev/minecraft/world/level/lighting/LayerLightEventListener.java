package net.minecraft.world.level.lighting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.chunk.DataLayer;
import org.jetbrains.annotations.Nullable;

public interface LayerLightEventListener extends LightEventListener {
	@Nullable
	DataLayer getDataLayerData(SectionPos sectionPos);

	int getLightValue(BlockPos blockPos);

	public static enum DummyLightLayerEventListener implements LayerLightEventListener {
		INSTANCE;

		@Nullable
		@Override
		public DataLayer getDataLayerData(SectionPos sectionPos) {
			return null;
		}

		@Override
		public int getLightValue(BlockPos blockPos) {
			return 0;
		}

		@Override
		public void updateSectionStatus(SectionPos sectionPos, boolean bl) {
		}
	}
}
