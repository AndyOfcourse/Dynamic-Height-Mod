package net.minecraft.world.level.dimension;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public final class LevelStem {
   public static final Codec<LevelStem> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
               DimensionType.CODEC.fieldOf("type").forGetter(LevelStem::typeSupplier),
               ChunkGenerator.CODEC.fieldOf("generator").forGetter(LevelStem::generator)
            )
            .apply(instance, instance.stable(LevelStem::new))
   );
   public static final ResourceKey<LevelStem> OVERWORLD = ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, new ResourceLocation("overworld"));
   public static final ResourceKey<LevelStem> NETHER = ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, new ResourceLocation("the_nether"));
   public static final ResourceKey<LevelStem> END = ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, new ResourceLocation("the_end"));
   private static final LinkedHashSet<ResourceKey<LevelStem>> BUILTIN_ORDER = Sets.newLinkedHashSet(ImmutableList.of(OVERWORLD, NETHER, END));
   private final Supplier<DimensionType> type;
   private final ChunkGenerator generator;

   public LevelStem(Supplier<DimensionType> supplier, ChunkGenerator chunkGenerator) {
      this.type = supplier;
      this.generator = chunkGenerator;
   }

   public Supplier<DimensionType> typeSupplier() {
      return this.type;
   }

   public DimensionType type() {
      return this.type.get();
   }

   public ChunkGenerator generator() {
      return this.generator;
   }

   public static MappedRegistry<LevelStem> sortMap(MappedRegistry<LevelStem> mappedRegistry) {
      MappedRegistry<LevelStem> mappedRegistry2 = new MappedRegistry<>(Registry.LEVEL_STEM_REGISTRY, Lifecycle.experimental());

      for(ResourceKey<LevelStem> resourceKey : BUILTIN_ORDER) {
         LevelStem levelStem = mappedRegistry.get(resourceKey);
         if (levelStem != null) {
            mappedRegistry2.register(resourceKey, levelStem, mappedRegistry.lifecycle(levelStem));
         }
      }

      for(Entry<ResourceKey<LevelStem>, LevelStem> entry : mappedRegistry.entrySet()) {
         ResourceKey<LevelStem> resourceKey2 = entry.getKey();
         if (!BUILTIN_ORDER.contains(resourceKey2)) {
            mappedRegistry2.register(resourceKey2, entry.getValue(), mappedRegistry.lifecycle(entry.getValue()));
         }
      }

      return mappedRegistry2;
   }

   public static boolean stable(long l, MappedRegistry<LevelStem> mappedRegistry) {
      List<Entry<ResourceKey<LevelStem>, LevelStem>> list = Lists.newArrayList(mappedRegistry.entrySet());
      if (list.size() != BUILTIN_ORDER.size()) {
         return false;
      } else {
         Entry<ResourceKey<LevelStem>, LevelStem> entry = list.get(0);
         Entry<ResourceKey<LevelStem>, LevelStem> entry2 = list.get(1);
         Entry<ResourceKey<LevelStem>, LevelStem> entry3 = list.get(2);
         if (entry.getKey() != OVERWORLD || entry2.getKey() != NETHER || entry3.getKey() != END) {
            return false;
         } else if (!entry.getValue().type().equalTo(DimensionType.DEFAULT_OVERWORLD) && entry.getValue().type() != DimensionType.DEFAULT_OVERWORLD_CAVES) {
            return false;
         } else if (!entry2.getValue().type().equalTo(DimensionType.DEFAULT_NETHER)) {
            return false;
         } else if (!entry3.getValue().type().equalTo(DimensionType.DEFAULT_END)) {
            return false;
         } else if (entry2.getValue().generator() instanceof NoiseBasedChunkGenerator && entry3.getValue().generator() instanceof NoiseBasedChunkGenerator) {
            NoiseBasedChunkGenerator noiseBasedChunkGenerator = (NoiseBasedChunkGenerator)entry2.getValue().generator();
            NoiseBasedChunkGenerator noiseBasedChunkGenerator2 = (NoiseBasedChunkGenerator)entry3.getValue().generator();
            if (!noiseBasedChunkGenerator.stable(l, NoiseGeneratorSettings.NETHER)) {
               return false;
            } else if (!noiseBasedChunkGenerator2.stable(l, NoiseGeneratorSettings.END)) {
               return false;
            } else if (!(noiseBasedChunkGenerator.getBiomeSource() instanceof MultiNoiseBiomeSource)) {
               return false;
            } else {
               MultiNoiseBiomeSource multiNoiseBiomeSource = (MultiNoiseBiomeSource)noiseBasedChunkGenerator.getBiomeSource();
               if (!multiNoiseBiomeSource.stable(l)) {
                  return false;
               } else if (!(noiseBasedChunkGenerator2.getBiomeSource() instanceof TheEndBiomeSource)) {
                  return false;
               } else {
                  TheEndBiomeSource theEndBiomeSource = (TheEndBiomeSource)noiseBasedChunkGenerator2.getBiomeSource();
                  return theEndBiomeSource.stable(l);
               }
            }
         } else {
            return false;
         }
      }
   }
}
