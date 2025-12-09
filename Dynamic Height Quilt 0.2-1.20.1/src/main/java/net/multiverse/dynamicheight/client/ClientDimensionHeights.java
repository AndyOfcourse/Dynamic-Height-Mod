package net.multiverse.dynamicheight.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.resources.ResourceLocation;
import net.multiverse.dynamicheight.worldheight.WorldHeightData;

public final class ClientDimensionHeights {
    private static final Map<ResourceLocation, WorldHeightData.Snapshot> OVERRIDES = new ConcurrentHashMap<>();

    private ClientDimensionHeights() {
    }

    public static void remember(ResourceLocation dimension, int minY, int maxY) {
        OVERRIDES.put(dimension, new WorldHeightData.Snapshot(minY, maxY));
    }

    public static WorldHeightData.Snapshot lookup(ResourceLocation dimension) {
        return OVERRIDES.get(dimension);
    }
}

