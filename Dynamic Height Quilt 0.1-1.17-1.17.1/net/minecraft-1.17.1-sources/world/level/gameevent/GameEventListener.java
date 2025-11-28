package net.minecraft.world.level.gameevent;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public interface GameEventListener {
	PositionSource getListenerSource();

	int getListenerRadius();

	boolean handleGameEvent(Level level, GameEvent gameEvent, @Nullable Entity entity, BlockPos blockPos);
}
