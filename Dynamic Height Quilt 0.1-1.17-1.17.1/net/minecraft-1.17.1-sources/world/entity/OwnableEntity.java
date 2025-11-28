package net.minecraft.world.entity;

import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public interface OwnableEntity {
	@Nullable
	UUID getOwnerUUID();

	@Nullable
	Entity getOwner();
}
