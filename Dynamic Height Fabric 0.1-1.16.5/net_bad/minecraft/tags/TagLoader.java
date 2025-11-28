package net.minecraft.tags;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TagLoader<T> {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new Gson();
   private static final int PATH_SUFFIX_LENGTH = ".json".length();
   private final Function<ResourceLocation, Optional<T>> idToValue;
   private final String directory;
   private final String name;

   public TagLoader(Function<ResourceLocation, Optional<T>> function, String string, String string2) {
      this.idToValue = function;
      this.directory = string;
      this.name = string2;
   }

   public CompletableFuture<Map<ResourceLocation, Tag.Builder>> prepare(ResourceManager resourceManager, Executor executor) {
      return CompletableFuture.supplyAsync(
         () -> {
            Map<ResourceLocation, Tag.Builder> map = Maps.newHashMap();
   
            for(ResourceLocation resourceLocation : resourceManager.listResources(this.directory, stringx -> stringx.endsWith(".json"))) {
               String string = resourceLocation.getPath();
               ResourceLocation resourceLocation2 = new ResourceLocation(
                  resourceLocation.getNamespace(), string.substring(this.directory.length() + 1, string.length() - PATH_SUFFIX_LENGTH)
               );
   
               try {
                  for(Resource resource : resourceManager.getResources(resourceLocation)) {
                     try (
                        InputStream inputStream = resource.getInputStream();
                        Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                     ) {
                        JsonObject jsonObject = GsonHelper.fromJson(GSON, reader, JsonObject.class);
                        if (jsonObject == null) {
                           LOGGER.error(
                              "Couldn't load {} tag list {} from {} in data pack {} as it is empty or null",
                              this.name,
                              resourceLocation2,
                              resourceLocation,
                              resource.getSourceName()
                           );
                        } else {
                           map.computeIfAbsent(resourceLocation2, resourceLocationx -> Tag.Builder.tag()).addFromJson(jsonObject, resource.getSourceName());
                        }
                     } catch (RuntimeException | IOException var57) {
                        LOGGER.error(
                           "Couldn't read {} tag list {} from {} in data pack {}",
                           this.name,
                           resourceLocation2,
                           resourceLocation,
                           resource.getSourceName(),
                           var57
                        );
                     } finally {
                        IOUtils.closeQuietly(resource);
                     }
                  }
               } catch (IOException var59) {
                  LOGGER.error("Couldn't read {} tag list {} from {}", this.name, resourceLocation2, resourceLocation, var59);
               }
            }
   
            return map;
         },
         executor
      );
   }

   public TagCollection<T> load(Map<ResourceLocation, Tag.Builder> map) {
      Map<ResourceLocation, Tag<T>> map2 = Maps.newHashMap();
      Function<ResourceLocation, Tag<T>> function = map2::get;
      Function<ResourceLocation, T> function2 = resourceLocation -> this.idToValue.apply(resourceLocation).orElse((T)null);

      while(!map.isEmpty()) {
         boolean bl = false;
         Iterator<Entry<ResourceLocation, Tag.Builder>> iterator = map.entrySet().iterator();

         while(iterator.hasNext()) {
            Entry<ResourceLocation, Tag.Builder> entry = iterator.next();
            Optional<Tag<T>> optional = entry.getValue().build(function, function2);
            if (optional.isPresent()) {
               map2.put(entry.getKey(), optional.get());
               iterator.remove();
               bl = true;
            }
         }

         if (!bl) {
            break;
         }
      }

      map.forEach(
         (resourceLocation, builder) -> LOGGER.error(
               "Couldn't load {} tag {} as it is missing following references: {}",
               this.name,
               resourceLocation,
               builder.getUnresolvedEntries(function, function2).map(Objects::toString).collect(Collectors.joining(","))
            )
      );
      return TagCollection.of(map2);
   }
}
