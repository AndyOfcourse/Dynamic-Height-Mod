package net.minecraft.world.entity;

import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.Nullable;

public interface Saddleable {
	boolean isSaddleable();

	void equipSaddle(@Nullable SoundSource soundSource);

	boolean isSaddled();
}
