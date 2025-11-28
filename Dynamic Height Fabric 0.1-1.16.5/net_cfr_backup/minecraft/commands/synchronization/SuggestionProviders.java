/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.brigadier.suggestion.SuggestionProvider
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  net.minecraft.Util
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.SharedSuggestionProvider
 *  net.minecraft.commands.synchronization.SuggestionProviders$Wrapper
 *  net.minecraft.core.Registry
 *  net.minecraft.network.chat.TranslatableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EntityType
 */
package net.minecraft.commands.synchronization;

import com.google.common.collect.Maps;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

/*
 * Exception performing whole class analysis ignored.
 */
public class SuggestionProviders {
    private static final Map<ResourceLocation, SuggestionProvider<SharedSuggestionProvider>> PROVIDERS_BY_NAME = Maps.newHashMap();
    private static final ResourceLocation DEFAULT_NAME = new ResourceLocation("ask_server");
    public static final SuggestionProvider<SharedSuggestionProvider> ASK_SERVER = SuggestionProviders.register(DEFAULT_NAME, (SuggestionProvider<SharedSuggestionProvider>)((SuggestionProvider)(commandContext, suggestionsBuilder) -> ((SharedSuggestionProvider)commandContext.getSource()).customSuggestion(commandContext, suggestionsBuilder)));
    public static final SuggestionProvider<CommandSourceStack> ALL_RECIPES = SuggestionProviders.register(new ResourceLocation("all_recipes"), (SuggestionProvider<SharedSuggestionProvider>)((SuggestionProvider)(commandContext, suggestionsBuilder) -> SharedSuggestionProvider.suggestResource((Stream)((SharedSuggestionProvider)commandContext.getSource()).getRecipeNames(), (SuggestionsBuilder)suggestionsBuilder)));
    public static final SuggestionProvider<CommandSourceStack> AVAILABLE_SOUNDS = SuggestionProviders.register(new ResourceLocation("available_sounds"), (SuggestionProvider<SharedSuggestionProvider>)((SuggestionProvider)(commandContext, suggestionsBuilder) -> SharedSuggestionProvider.suggestResource((Iterable)((SharedSuggestionProvider)commandContext.getSource()).getAvailableSoundEvents(), (SuggestionsBuilder)suggestionsBuilder)));
    public static final SuggestionProvider<CommandSourceStack> AVAILABLE_BIOMES = SuggestionProviders.register(new ResourceLocation("available_biomes"), (SuggestionProvider<SharedSuggestionProvider>)((SuggestionProvider)(commandContext, suggestionsBuilder) -> SharedSuggestionProvider.suggestResource((Iterable)((SharedSuggestionProvider)commandContext.getSource()).registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).keySet(), (SuggestionsBuilder)suggestionsBuilder)));
    public static final SuggestionProvider<CommandSourceStack> SUMMONABLE_ENTITIES = SuggestionProviders.register(new ResourceLocation("summonable_entities"), (SuggestionProvider<SharedSuggestionProvider>)((SuggestionProvider)(commandContext, suggestionsBuilder) -> SharedSuggestionProvider.suggestResource(Registry.ENTITY_TYPE.stream().filter(EntityType::canSummon), (SuggestionsBuilder)suggestionsBuilder, EntityType::getKey, entityType -> new TranslatableComponent(Util.makeDescriptionId((String)"entity", (ResourceLocation)EntityType.getKey((EntityType)entityType))))));

    public static <S extends SharedSuggestionProvider> SuggestionProvider<S> register(ResourceLocation resourceLocation, SuggestionProvider<SharedSuggestionProvider> suggestionProvider) {
        if (PROVIDERS_BY_NAME.containsKey(resourceLocation)) {
            throw new IllegalArgumentException("A command suggestion provider is already registered with the name " + resourceLocation);
        }
        PROVIDERS_BY_NAME.put(resourceLocation, suggestionProvider);
        return new Wrapper(resourceLocation, suggestionProvider);
    }

    public static SuggestionProvider<SharedSuggestionProvider> getProvider(ResourceLocation resourceLocation) {
        return PROVIDERS_BY_NAME.getOrDefault(resourceLocation, ASK_SERVER);
    }

    public static ResourceLocation getName(SuggestionProvider<SharedSuggestionProvider> suggestionProvider) {
        if (suggestionProvider instanceof Wrapper) {
            return Wrapper.method_10031((Wrapper)((Wrapper)suggestionProvider));
        }
        return DEFAULT_NAME;
    }

    public static SuggestionProvider<SharedSuggestionProvider> safelySwap(SuggestionProvider<SharedSuggestionProvider> suggestionProvider) {
        if (suggestionProvider instanceof Wrapper) {
            return suggestionProvider;
        }
        return ASK_SERVER;
    }
}
