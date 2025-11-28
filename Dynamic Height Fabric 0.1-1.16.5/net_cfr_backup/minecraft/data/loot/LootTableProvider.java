/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Multimap
 *  com.google.common.collect.Sets
 *  com.google.common.collect.Sets$SetView
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.mojang.datafixers.util.Pair
 *  net.minecraft.data.DataGenerator
 *  net.minecraft.data.DataProvider
 *  net.minecraft.data.HashCache
 *  net.minecraft.data.loot.BlockLoot
 *  net.minecraft.data.loot.ChestLoot
 *  net.minecraft.data.loot.EntityLoot
 *  net.minecraft.data.loot.FishingLoot
 *  net.minecraft.data.loot.GiftLoot
 *  net.minecraft.data.loot.PiglinBarterLoot
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.storage.loot.BuiltInLootTables
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.LootTable$Builder
 *  net.minecraft.world.level.storage.loot.LootTables
 *  net.minecraft.world.level.storage.loot.ValidationContext
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSet
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.data.loot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.ChestLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.FishingLoot;
import net.minecraft.data.loot.GiftLoot;
import net.minecraft.data.loot.PiglinBarterLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTableProvider
implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator generator;
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> subProviders = ImmutableList.of((Object)Pair.of(FishingLoot::new, (Object)LootContextParamSets.FISHING), (Object)Pair.of(ChestLoot::new, (Object)LootContextParamSets.CHEST), (Object)Pair.of(EntityLoot::new, (Object)LootContextParamSets.ENTITY), (Object)Pair.of(BlockLoot::new, (Object)LootContextParamSets.BLOCK), (Object)Pair.of(PiglinBarterLoot::new, (Object)LootContextParamSets.PIGLIN_BARTER), (Object)Pair.of(GiftLoot::new, (Object)LootContextParamSets.GIFT));

    public LootTableProvider(DataGenerator dataGenerator) {
        this.generator = dataGenerator;
    }

    public void run(HashCache hashCache) {
        Path path = this.generator.getOutputFolder();
        HashMap map = Maps.newHashMap();
        this.subProviders.forEach(pair -> ((Consumer)((Supplier)pair.getFirst()).get()).accept((resourceLocation, builder) -> {
            if (map.put(resourceLocation, builder.setParamSet((LootContextParamSet)pair.getSecond()).build()) != null) {
                throw new IllegalStateException("Duplicate loot table " + resourceLocation);
            }
        }));
        ValidationContext validationContext = new ValidationContext(LootContextParamSets.ALL_PARAMS, resourceLocation -> null, map::get);
        Sets.SetView set = Sets.difference((Set)BuiltInLootTables.all(), map.keySet());
        for (ResourceLocation resourceLocation2 : set) {
            validationContext.reportProblem("Missing built-in table: " + resourceLocation2);
        }
        map.forEach((resourceLocation, lootTable) -> LootTables.validate((ValidationContext)validationContext, (ResourceLocation)resourceLocation, (LootTable)lootTable));
        Multimap multimap = validationContext.getProblems();
        if (!multimap.isEmpty()) {
            multimap.forEach((string, string2) -> LOGGER.warn("Found validation problem in " + string + ": " + string2));
            throw new IllegalStateException("Failed to validate loot tables, see logs");
        }
        map.forEach((resourceLocation, lootTable) -> {
            Path path2 = LootTableProvider.createPath(path, resourceLocation);
            try {
                DataProvider.save((Gson)GSON, (HashCache)hashCache, (JsonElement)LootTables.serialize((LootTable)lootTable), (Path)path2);
            }
            catch (IOException iOException) {
                LOGGER.error("Couldn't save loot table {}", (Object)path2, (Object)iOException);
            }
        });
    }

    private static Path createPath(Path path, ResourceLocation resourceLocation) {
        return path.resolve("data/" + resourceLocation.getNamespace() + "/loot_tables/" + resourceLocation.getPath() + ".json");
    }

    public String getName() {
        return "LootTables";
    }
}
