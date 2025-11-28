package net.minecraft.world;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public interface Nameable {
	Component getName();

	default boolean hasCustomName() {
		return this.getCustomName() != null;
	}

	default Component getDisplayName() {
		return this.getName();
	}

	@Nullable
	default Component getCustomName() {
		return null;
	}
}
