package net.minecraft.world;

import org.jetbrains.annotations.Nullable;

public interface Clearable {
   void clearContent();

   static void tryClear(@Nullable Object object) {
      if (object instanceof Clearable) {
         ((Clearable)object).clearContent();
      }
   }
}
