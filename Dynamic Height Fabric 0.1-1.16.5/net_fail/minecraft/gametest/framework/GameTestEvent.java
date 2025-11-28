package net.minecraft.gametest.framework;

import org.jetbrains.annotations.Nullable;

class GameTestEvent {
   @Nullable
   public final Long expectedDelay;
   public final Runnable assertion;
}
