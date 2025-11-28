/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.SetTag
 *  net.minecraft.tags.TagCollection
 */
package net.minecraft.tags;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SetTag;
import net.minecraft.tags.TagCollection;

public interface Tag<T> {
    public static <T> Codec<Tag<T>> codec(Supplier<TagCollection<T>> supplier) {
        return ResourceLocation.CODEC.flatXmap(resourceLocation -> Optional.ofNullable(((TagCollection)supplier.get()).getTag(resourceLocation)).map(DataResult::success).orElseGet(() -> DataResult.error((String)("Unknown tag: " + resourceLocation))), tag -> Optional.ofNullable(((TagCollection)supplier.get()).getId(tag)).map(DataResult::success).orElseGet(() -> DataResult.error((String)("Unknown tag: " + tag))));
    }

    public boolean contains(T var1);

    public List<T> getValues();

    default public T getRandomElement(Random random) {
        List<T> list = this.getValues();
        return list.get(random.nextInt(list.size()));
    }

    public static <T> Tag<T> fromSet(Set<T> set) {
        return SetTag.create(set);
    }
}
