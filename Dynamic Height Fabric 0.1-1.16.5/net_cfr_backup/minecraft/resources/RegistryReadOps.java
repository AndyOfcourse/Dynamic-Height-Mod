/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  com.google.common.base.Suppliers
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonElement
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  com.mojang.serialization.Lifecycle
 *  net.minecraft.core.MappedRegistry
 *  net.minecraft.core.Registry
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.core.RegistryAccess$RegistryHolder
 *  net.minecraft.core.WritableRegistry
 *  net.minecraft.resources.DelegatingOps
 *  net.minecraft.resources.RegistryReadOps$ReadCache
 *  net.minecraft.resources.RegistryReadOps$ResourceAccess
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.ResourceManager
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.resources;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.DelegatingOps;
import net.minecraft.resources.RegistryReadOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Exception performing whole class analysis ignored.
 */
public class RegistryReadOps<T>
extends DelegatingOps<T> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ResourceAccess resources;
    private final RegistryAccess.RegistryHolder registryHolder;
    private final Map<ResourceKey<? extends Registry<?>>, ReadCache<?>> readCache;
    private final RegistryReadOps<JsonElement> jsonOps;

    public static <T> RegistryReadOps<T> create(DynamicOps<T> dynamicOps, ResourceManager resourceManager, RegistryAccess.RegistryHolder registryHolder) {
        return RegistryReadOps.create(dynamicOps, ResourceAccess.forResourceManager((ResourceManager)resourceManager), registryHolder);
    }

    public static <T> RegistryReadOps<T> create(DynamicOps<T> dynamicOps, ResourceAccess resourceAccess, RegistryAccess.RegistryHolder registryHolder) {
        RegistryReadOps<T> registryReadOps = new RegistryReadOps<T>(dynamicOps, resourceAccess, registryHolder, Maps.newIdentityHashMap());
        RegistryAccess.load((RegistryAccess.RegistryHolder)registryHolder, registryReadOps);
        return registryReadOps;
    }

    private RegistryReadOps(DynamicOps<T> dynamicOps, ResourceAccess resourceAccess, RegistryAccess.RegistryHolder registryHolder, IdentityHashMap<ResourceKey<? extends Registry<?>>, ReadCache<?>> identityHashMap) {
        super(dynamicOps);
        this.resources = resourceAccess;
        this.registryHolder = registryHolder;
        this.readCache = identityHashMap;
        this.jsonOps = dynamicOps == JsonOps.INSTANCE ? this : new RegistryReadOps<T>(JsonOps.INSTANCE, resourceAccess, registryHolder, (IdentityHashMap<ResourceKey<Registry<?>>, ReadCache<?>>)identityHashMap);
    }

    protected <E> DataResult<Pair<java.util.function.Supplier<E>, T>> decodeElement(T object, ResourceKey<? extends Registry<E>> resourceKey, Codec<E> codec, boolean bl) {
        Optional optional = this.registryHolder.registry(resourceKey);
        if (!optional.isPresent()) {
            return DataResult.error((String)("Unknown registry: " + resourceKey));
        }
        WritableRegistry writableRegistry = (WritableRegistry)optional.get();
        DataResult dataResult = ResourceLocation.CODEC.decode(this.delegate, object);
        if (!dataResult.result().isPresent()) {
            if (!bl) {
                return DataResult.error((String)"Inline definitions not allowed here");
            }
            return codec.decode((DynamicOps)this, object).map(pair -> pair.mapFirst(object -> () -> object));
        }
        Pair pair2 = (Pair)dataResult.result().get();
        ResourceLocation resourceLocation = (ResourceLocation)pair2.getFirst();
        return this.readAndRegisterElement(resourceKey, writableRegistry, codec, resourceLocation).map(supplier -> Pair.of((Object)supplier, (Object)pair2.getSecond()));
    }

    public <E> DataResult<MappedRegistry<E>> decodeElements(MappedRegistry<E> mappedRegistry2, ResourceKey<? extends Registry<E>> resourceKey, Codec<E> codec) {
        Collection collection = this.resources.listResources(resourceKey);
        DataResult dataResult = DataResult.success(mappedRegistry2, (Lifecycle)Lifecycle.stable());
        String string = resourceKey.location().getPath() + "/";
        for (ResourceLocation resourceLocation : collection) {
            String string2 = resourceLocation.getPath();
            if (!string2.endsWith(".json")) {
                LOGGER.warn("Skipping resource {} since it is not a json file", (Object)resourceLocation);
                continue;
            }
            if (!string2.startsWith(string)) {
                LOGGER.warn("Skipping resource {} since it does not have a registry name prefix", (Object)resourceLocation);
                continue;
            }
            String string3 = string2.substring(string.length(), string2.length() - ".json".length());
            ResourceLocation resourceLocation2 = new ResourceLocation(resourceLocation.getNamespace(), string3);
            dataResult = dataResult.flatMap(mappedRegistry -> this.readAndRegisterElement(resourceKey, (WritableRegistry)mappedRegistry, codec, resourceLocation2).map(supplier -> mappedRegistry));
        }
        return dataResult.setPartial(mappedRegistry2);
    }

    private <E> DataResult<java.util.function.Supplier<E>> readAndRegisterElement(ResourceKey<? extends Registry<E>> resourceKey, WritableRegistry<E> writableRegistry, Codec<E> codec, ResourceLocation resourceLocation) {
        ResourceKey resourceKey2 = ResourceKey.create(resourceKey, (ResourceLocation)resourceLocation);
        ReadCache<E> readCache = this.readCache(resourceKey);
        DataResult dataResult = (DataResult)ReadCache.method_29770(readCache).get(resourceKey2);
        if (dataResult != null) {
            return dataResult;
        }
        Supplier supplier = Suppliers.memoize(() -> {
            Object object = writableRegistry.get(resourceKey2);
            if (object == null) {
                throw new RuntimeException("Error during recursive registry parsing, element resolved too early: " + resourceKey2);
            }
            return object;
        });
        ReadCache.method_29770(readCache).put(resourceKey2, DataResult.success((Object)supplier));
        DataResult dataResult2 = this.resources.parseElement(this.jsonOps, resourceKey, resourceKey2, codec);
        Optional optional = dataResult2.result();
        if (optional.isPresent()) {
            Pair pair2 = (Pair)optional.get();
            writableRegistry.registerOrOverride((OptionalInt)pair2.getSecond(), resourceKey2, pair2.getFirst(), dataResult2.lifecycle());
        }
        DataResult dataResult3 = !optional.isPresent() && writableRegistry.get(resourceKey2) != null ? DataResult.success(() -> writableRegistry.get(resourceKey2), (Lifecycle)Lifecycle.stable()) : dataResult2.map(pair -> () -> writableRegistry.get(resourceKey2));
        ReadCache.method_29770(readCache).put(resourceKey2, dataResult3);
        return dataResult3;
    }

    private <E> ReadCache<E> readCache(ResourceKey<? extends Registry<E>> resourceKey2) {
        return this.readCache.computeIfAbsent(resourceKey2, resourceKey -> new ReadCache(null));
    }

    protected <E> DataResult<Registry<E>> registry(ResourceKey<? extends Registry<E>> resourceKey) {
        return this.registryHolder.registry(resourceKey).map(writableRegistry -> DataResult.success((Object)writableRegistry, (Lifecycle)writableRegistry.elementsLifecycle())).orElseGet(() -> DataResult.error((String)("Unknown registry: " + resourceKey)));
    }

    static /* synthetic */ Logger method_31149() {
        return LOGGER;
    }
}
