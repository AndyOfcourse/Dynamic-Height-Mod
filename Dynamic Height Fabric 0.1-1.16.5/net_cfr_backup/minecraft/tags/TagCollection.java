/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableBiMap
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.ImmutableSet$Builder
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.core.DefaultedRegistry
 *  net.minecraft.core.Registry
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.Tag
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.tags;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import org.jetbrains.annotations.Nullable;

public interface TagCollection<T> {
    public Map<ResourceLocation, Tag<T>> getAllTags();

    @Nullable
    default public Tag<T> getTag(ResourceLocation resourceLocation) {
        return this.getAllTags().get(resourceLocation);
    }

    public Tag<T> getTagOrEmpty(ResourceLocation var1);

    @Nullable
    public ResourceLocation getId(Tag<T> var1);

    default public ResourceLocation getIdOrThrow(Tag<T> tag) {
        ResourceLocation resourceLocation = this.getId(tag);
        if (resourceLocation == null) {
            throw new IllegalStateException("Unrecognized tag");
        }
        return resourceLocation;
    }

    default public Collection<ResourceLocation> getAvailableTags() {
        return this.getAllTags().keySet();
    }

    @Environment(value=EnvType.CLIENT)
    default public Collection<ResourceLocation> getMatchingTags(T object) {
        ArrayList list = Lists.newArrayList();
        for (Map.Entry<ResourceLocation, Tag<T>> entry : this.getAllTags().entrySet()) {
            if (!entry.getValue().contains(object)) continue;
            list.add(entry.getKey());
        }
        return list;
    }

    default public void serializeToNetwork(FriendlyByteBuf friendlyByteBuf, DefaultedRegistry<T> defaultedRegistry) {
        Map<ResourceLocation, Tag<T>> map = this.getAllTags();
        friendlyByteBuf.writeVarInt(map.size());
        for (Map.Entry<ResourceLocation, Tag<T>> entry : map.entrySet()) {
            friendlyByteBuf.writeResourceLocation(entry.getKey());
            friendlyByteBuf.writeVarInt(entry.getValue().getValues().size());
            for (Object object : entry.getValue().getValues()) {
                friendlyByteBuf.writeVarInt(defaultedRegistry.getId(object));
            }
        }
    }

    public static <T> TagCollection<T> loadFromNetwork(FriendlyByteBuf friendlyByteBuf, Registry<T> registry) {
        HashMap map = Maps.newHashMap();
        int i = friendlyByteBuf.readVarInt();
        for (int j = 0; j < i; ++j) {
            ResourceLocation resourceLocation = friendlyByteBuf.readResourceLocation();
            int k = friendlyByteBuf.readVarInt();
            ImmutableSet.Builder builder = ImmutableSet.builder();
            for (int l = 0; l < k; ++l) {
                builder.add(registry.byId(friendlyByteBuf.readVarInt()));
            }
            map.put(resourceLocation, Tag.fromSet((Set)builder.build()));
        }
        return TagCollection.of(map);
    }

    public static <T> TagCollection<T> empty() {
        return TagCollection.of(ImmutableBiMap.of());
    }

    public static <T> TagCollection<T> of(Map<ResourceLocation, Tag<T>> map) {
        ImmutableBiMap biMap = ImmutableBiMap.copyOf(map);
        return new /* Unavailable Anonymous Inner Class!! */;
    }
}
