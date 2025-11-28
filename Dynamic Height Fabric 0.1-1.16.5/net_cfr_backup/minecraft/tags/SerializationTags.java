/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.EntityTypeTags
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.tags.Tag$Named
 *  net.minecraft.tags.TagCollection
 *  net.minecraft.tags.TagContainer
 */
package net.minecraft.tags;

import java.util.stream.Collectors;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.tags.TagContainer;

public class SerializationTags {
    private static volatile TagContainer instance = TagContainer.of((TagCollection)TagCollection.of(BlockTags.getWrappers().stream().collect(Collectors.toMap(Tag.Named::getName, named -> named))), (TagCollection)TagCollection.of(ItemTags.getWrappers().stream().collect(Collectors.toMap(Tag.Named::getName, named -> named))), (TagCollection)TagCollection.of(FluidTags.getWrappers().stream().collect(Collectors.toMap(Tag.Named::getName, named -> named))), (TagCollection)TagCollection.of(EntityTypeTags.getWrappers().stream().collect(Collectors.toMap(Tag.Named::getName, named -> named))));

    public static TagContainer getInstance() {
        return instance;
    }

    public static void bind(TagContainer tagContainer) {
        instance = tagContainer;
    }
}
