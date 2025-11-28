package net.minecraft.tags;

import java.util.function.Function;
import java.util.stream.Collectors;

public class SerializationTags {
   private static volatile TagContainer instance = TagContainer.of(
      TagCollection.of(
         BlockTags.getWrappers().stream().collect(Collectors.toMap(Tag.Named::getName, (Function<? super Tag.Named, ? extends Tag.Named>)(named -> named)))
      ),
      TagCollection.of(
         ItemTags.getWrappers().stream().collect(Collectors.toMap(Tag.Named::getName, (Function<? super Tag.Named, ? extends Tag.Named>)(named -> named)))
      ),
      TagCollection.of(
         FluidTags.getWrappers().stream().collect(Collectors.toMap(Tag.Named::getName, (Function<? super Tag.Named, ? extends Tag.Named>)(named -> named)))
      ),
      TagCollection.of(
         EntityTypeTags.getWrappers()
            .stream()
            .collect(Collectors.toMap(Tag.Named::getName, (Function<? super Tag.Named, ? extends Tag.Named>)(named -> named)))
      )
   );

   public static TagContainer getInstance() {
      return instance;
   }

   public static void bind(TagContainer tagContainer) {
      instance = tagContainer;
   }
}
