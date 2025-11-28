/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashBasedTable
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.RecipeBookCategories
 *  net.minecraft.client.gui.screens.recipebook.RecipeCollection
 *  net.minecraft.core.Registry
 *  net.minecraft.stats.RecipeBook
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Recipe
 *  net.minecraft.world.item.crafting.RecipeType
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.apache.logging.log4j.util.Supplier
 */
package net.minecraft.client;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.core.Registry;
import net.minecraft.stats.RecipeBook;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;

@Environment(value=EnvType.CLIENT)
public class ClientRecipeBook
extends RecipeBook {
    private static final Logger LOGGER = LogManager.getLogger();
    private Map<RecipeBookCategories, List<RecipeCollection>> collectionsByTab = ImmutableMap.of();
    private List<RecipeCollection> allCollections = ImmutableList.of();

    public void setupCollections(Iterable<Recipe<?>> iterable) {
        Map<RecipeBookCategories, List<List<Recipe<?>>>> map = ClientRecipeBook.categorizeAndGroupRecipes(iterable);
        HashMap map2 = Maps.newHashMap();
        ImmutableList.Builder builder = ImmutableList.builder();
        map.forEach((recipeBookCategories, list) -> {
            List cfr_ignored_0 = (List)map2.put(recipeBookCategories, list.stream().map(RecipeCollection::new).peek(arg_0 -> ((ImmutableList.Builder)builder).add(arg_0)).collect(ImmutableList.toImmutableList()));
        });
        RecipeBookCategories.AGGREGATE_CATEGORIES.forEach((recipeBookCategories2, list) -> {
            List cfr_ignored_0 = (List)map2.put(recipeBookCategories2, list.stream().flatMap(recipeBookCategories -> ((List)map2.getOrDefault(recipeBookCategories, ImmutableList.of())).stream()).collect(ImmutableList.toImmutableList()));
        });
        this.collectionsByTab = ImmutableMap.copyOf((Map)map2);
        this.allCollections = builder.build();
    }

    private static Map<RecipeBookCategories, List<List<Recipe<?>>>> categorizeAndGroupRecipes(Iterable<Recipe<?>> iterable) {
        HashMap map = Maps.newHashMap();
        HashBasedTable table = HashBasedTable.create();
        for (Recipe<?> recipe : iterable) {
            if (recipe.isSpecial()) continue;
            RecipeBookCategories recipeBookCategories2 = ClientRecipeBook.getCategory(recipe);
            String string = recipe.getGroup();
            if (string.isEmpty()) {
                map.computeIfAbsent(recipeBookCategories2, recipeBookCategories -> Lists.newArrayList()).add(ImmutableList.of(recipe));
                continue;
            }
            List list = (List)table.get((Object)recipeBookCategories2, (Object)string);
            if (list == null) {
                list = Lists.newArrayList();
                table.put((Object)recipeBookCategories2, (Object)string, (Object)list);
                map.computeIfAbsent(recipeBookCategories2, recipeBookCategories -> Lists.newArrayList()).add(list);
            }
            list.add(recipe);
        }
        return map;
    }

    private static RecipeBookCategories getCategory(Recipe<?> recipe) {
        RecipeType recipeType = recipe.getType();
        if (recipeType == RecipeType.CRAFTING) {
            ItemStack itemStack = recipe.getResultItem();
            CreativeModeTab creativeModeTab = itemStack.getItem().getItemCategory();
            if (creativeModeTab == CreativeModeTab.TAB_BUILDING_BLOCKS) {
                return RecipeBookCategories.CRAFTING_BUILDING_BLOCKS;
            }
            if (creativeModeTab == CreativeModeTab.TAB_TOOLS || creativeModeTab == CreativeModeTab.TAB_COMBAT) {
                return RecipeBookCategories.CRAFTING_EQUIPMENT;
            }
            if (creativeModeTab == CreativeModeTab.TAB_REDSTONE) {
                return RecipeBookCategories.CRAFTING_REDSTONE;
            }
            return RecipeBookCategories.CRAFTING_MISC;
        }
        if (recipeType == RecipeType.SMELTING) {
            if (recipe.getResultItem().getItem().isEdible()) {
                return RecipeBookCategories.FURNACE_FOOD;
            }
            if (recipe.getResultItem().getItem() instanceof BlockItem) {
                return RecipeBookCategories.FURNACE_BLOCKS;
            }
            return RecipeBookCategories.FURNACE_MISC;
        }
        if (recipeType == RecipeType.BLASTING) {
            if (recipe.getResultItem().getItem() instanceof BlockItem) {
                return RecipeBookCategories.BLAST_FURNACE_BLOCKS;
            }
            return RecipeBookCategories.BLAST_FURNACE_MISC;
        }
        if (recipeType == RecipeType.SMOKING) {
            return RecipeBookCategories.SMOKER_FOOD;
        }
        if (recipeType == RecipeType.STONECUTTING) {
            return RecipeBookCategories.STONECUTTER;
        }
        if (recipeType == RecipeType.CAMPFIRE_COOKING) {
            return RecipeBookCategories.CAMPFIRE;
        }
        if (recipeType == RecipeType.SMITHING) {
            return RecipeBookCategories.SMITHING;
        }
        Supplier[] supplierArray = new Supplier[2];
        supplierArray[0] = () -> Registry.RECIPE_TYPE.getKey((Object)recipe.getType());
        supplierArray[1] = () -> recipe.getId();
        LOGGER.warn("Unknown recipe category: {}/{}", supplierArray);
        return RecipeBookCategories.UNKNOWN;
    }

    public List<RecipeCollection> getCollections() {
        return this.allCollections;
    }

    public List<RecipeCollection> getCollection(RecipeBookCategories recipeBookCategories) {
        return this.collectionsByTab.getOrDefault(recipeBookCategories, Collections.emptyList());
    }
}
