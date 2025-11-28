/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.minecraft.world.level.storage.loot.GsonAdapterFactory$Builder
 *  net.minecraft.world.level.storage.loot.SerializerType
 */
package net.minecraft.world.level.storage.loot;

import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.GsonAdapterFactory;
import net.minecraft.world.level.storage.loot.SerializerType;

public class GsonAdapterFactory {
    public static <E, T extends SerializerType<E>> Builder<E, T> builder(Registry<T> registry, String string, String string2, Function<E, T> function) {
        return new Builder(registry, string, string2, function, null);
    }
}
