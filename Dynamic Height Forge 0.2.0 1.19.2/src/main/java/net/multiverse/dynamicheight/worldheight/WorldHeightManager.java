/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Holder$Reference
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.chunk.ChunkGenerator
 *  net.minecraft.world.level.dimension.DimensionType
 *  net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator
 *  net.minecraft.world.level.levelgen.NoiseGeneratorSettings
 *  net.minecraft.world.level.storage.DimensionDataStorage
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.level.BlockEvent$EntityPlaceEvent
 *  net.minecraftforge.event.level.LevelEvent$Load
 *  net.minecraftforge.event.level.LevelEvent$Unload
 */
package net.multiverse.dynamicheight.worldheight;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.multiverse.dynamicheight.worldheight.WorldHeightData;
import net.multiverse.dynamicheight.worldheight.WorldHeightSavedData;

public final class WorldHeightManager {
    private static final Map<ResourceKey<Level>, WorldHeightSavedData> BY_DIMENSION = new ConcurrentHashMap<ResourceKey<Level>, WorldHeightSavedData>();
    private static final Field LEVEL_DIMENSION_TYPE_FIELD = findDimensionTypeField();

    private WorldHeightManager() {
    }

    public static void register() {
        MinecraftForge.EVENT_BUS.addListener(WorldHeightManager::onLevelLoad);
        MinecraftForge.EVENT_BUS.addListener(WorldHeightManager::onLevelUnload);
        MinecraftForge.EVENT_BUS.addListener(WorldHeightManager::onBlockPlaced);
    }

    private static void onLevelLoad(LevelEvent.Load event) {
        LevelAccessor levelAccessor = event.getLevel();
        if (!(levelAccessor instanceof ServerLevel)) {
            return;
        }
        ServerLevel serverLevel = (ServerLevel)levelAccessor;
        DimensionDataStorage storage = serverLevel.getDataStorage();
        WorldHeightSavedData data = storage.computeIfAbsent(WorldHeightSavedData::load, WorldHeightSavedData::new, WorldHeightSavedData.dataName());
        WorldHeightData.Snapshot snapshot = WorldHeightData.currentSnapshot();
        if (serverLevel.dimension().equals(Level.OVERWORLD) && WorldHeightData.hasPendingClientSelection()) {
            snapshot = WorldHeightData.consumeSnapshot();
        }
        data.update(snapshot);
        WorldHeightManager.applyDimensionSettings(serverLevel, data);
        BY_DIMENSION.put(serverLevel.dimension(), data);
    }

    private static void onLevelUnload(LevelEvent.Unload event) {
        LevelAccessor levelAccessor = event.getLevel();
        if (!(levelAccessor instanceof ServerLevel)) {
            return;
        }
        ServerLevel serverLevel = (ServerLevel)levelAccessor;
        BY_DIMENSION.remove(serverLevel.dimension());
    }

    private static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        LevelAccessor level = event.getLevel();
        if (!(level instanceof ServerLevel)) {
            return;
        }
        ServerLevel serverLevel = (ServerLevel)level;
        WorldHeightSavedData data = WorldHeightManager.getData(serverLevel);
        if (data == null) {
            return;
        }
        int y = event.getPos().getY();
        if (y < data.minY() || y >= data.maxY()) {
            event.setCanceled(true);
            Entity entity = event.getEntity();
            if (entity instanceof Player) {
                Player player = (Player)entity;
                player.displayClientMessage(Component.literal("Cannot place blocks outside the configured vertical range (" + data.minY() + " .. " + (data.maxY() - 1) + ") in " + serverLevel.dimension().location()).withStyle(ChatFormatting.RED), true);
            }
        }
    }

    private static WorldHeightSavedData getData(ServerLevel level) {
        WorldHeightSavedData cached = BY_DIMENSION.get(level.dimension());
        if (cached != null) {
            return cached;
        }
        DimensionDataStorage storage = level.getDataStorage();
        WorldHeightSavedData data = storage.computeIfAbsent(WorldHeightSavedData::load, WorldHeightSavedData::new, WorldHeightSavedData.dataName());
        BY_DIMENSION.put(level.dimension(), data);
        WorldHeightManager.applyDimensionSettings(level, data);
        return data;
    }

    private static void applyDimensionSettings(ServerLevel level, WorldHeightSavedData data) {
        Holder<DimensionType> holder = level.dimensionTypeRegistration();
        DimensionType current = holder.value();
        if (current.minY() == data.minY() && current.height() == data.height()) {
            return;
        }
        int logicalHeight = data.height();
        DimensionType updated = new DimensionType(current.fixedTime(), current.hasSkyLight(), current.hasCeiling(), current.ultraWarm(), current.natural(), current.coordinateScale(), current.bedWorks(), current.respawnAnchorWorks(), data.minY(), data.height(), logicalHeight, current.infiniburn(), current.effectsLocation(), current.ambientLight(), current.monsterSettings());
        WorldHeightManager.replaceDimensionType(level, updated);
    }

    public static void applyClientSelection(ServerPlayer player, WorldHeightData.Snapshot snapshot) {
        MinecraftServer server = player.getServer();
        if (server == null) {
            return;
        }
        WorldHeightManager.applySnapshotToDimension(server.getLevel(Level.OVERWORLD), snapshot);
        WorldHeightManager.applySnapshotToDimension(server.getLevel(Level.NETHER), snapshot);
        WorldHeightManager.applySnapshotToDimension(server.getLevel(Level.END), snapshot);
        WorldHeightManager.applySnapshotToDimension(player.getLevel(), snapshot);
    }

    private static void applySnapshotToDimension(ServerLevel level, WorldHeightData.Snapshot snapshot) {
        if (level == null) {
            return;
        }
        WorldHeightSavedData data = WorldHeightManager.getData(level);
        data.update(snapshot);
        WorldHeightManager.applyDimensionSettings(level, data);
    }

    private static void replaceDimensionType(ServerLevel level, DimensionType updated) {
        try {
            LEVEL_DIMENSION_TYPE_FIELD.set(level, Holder.direct(updated));
        }
        catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to replace dimension type holder", e);
        }
    }

    private static Field findDimensionTypeField() {
        try {
            Field field = Level.class.getDeclaredField("dimensionTypeRegistration");
            field.setAccessible(true);
            return field;
        }
        catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Unable to access Level#dimensionTypeRegistration", e);
        }
    }
}
