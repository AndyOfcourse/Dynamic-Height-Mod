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
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.multiverse.dynamicheight.util.NoiseGeneratorUtil;
import net.multiverse.dynamicheight.util.ServerGeneratorReloader;
import net.multiverse.dynamicheight.worldheight.WorldHeightData;
import net.multiverse.dynamicheight.worldheight.WorldHeightSavedData;

public final class WorldHeightManager {
    private static final Map<ResourceKey<Level>, WorldHeightSavedData> BY_DIMENSION = new ConcurrentHashMap<ResourceKey<Level>, WorldHeightSavedData>();
    private static final Map<ResourceKey<Level>, NoiseGeneratorSettings> ORIGINAL_NOISE = new ConcurrentHashMap<ResourceKey<Level>, NoiseGeneratorSettings>();

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
        DimensionDataStorage storage = serverLevel.m_8895_();
        WorldHeightSavedData data = (WorldHeightSavedData)storage.m_164861_(WorldHeightSavedData::load, WorldHeightSavedData::new, WorldHeightSavedData.dataName());
        WorldHeightData.Snapshot snapshot = WorldHeightData.currentSnapshot();
        if (serverLevel.m_46472_().equals((Object)Level.f_46428_) && WorldHeightData.hasPendingClientSelection()) {
            snapshot = WorldHeightData.consumeSnapshot();
        }
        data.update(snapshot);
        WorldHeightManager.applyDimensionSettings(serverLevel, data);
        BY_DIMENSION.put((ResourceKey<Level>)serverLevel.m_46472_(), data);
    }

    private static void onLevelUnload(LevelEvent.Unload event) {
        LevelAccessor levelAccessor = event.getLevel();
        if (!(levelAccessor instanceof ServerLevel)) {
            return;
        }
        ServerLevel serverLevel = (ServerLevel)levelAccessor;
        BY_DIMENSION.remove(serverLevel.m_46472_());
        ORIGINAL_NOISE.remove(serverLevel.m_46472_());
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
        int y = event.getPos().m_123342_();
        if (y < data.minY() || y >= data.maxY()) {
            event.setCanceled(true);
            Entity entity = event.getEntity();
            if (entity instanceof Player) {
                Player player = (Player)entity;
                player.m_5661_((Component)Component.m_237113_((String)("Cannot place blocks outside the configured vertical range (" + data.minY() + " .. " + (data.maxY() - 1) + ") in " + String.valueOf(serverLevel.m_46472_().m_135782_()))).m_130940_(ChatFormatting.RED), true);
            }
        }
    }

    private static WorldHeightSavedData getData(ServerLevel level) {
        WorldHeightSavedData cached = BY_DIMENSION.get(level.m_46472_());
        if (cached != null) {
            return cached;
        }
        DimensionDataStorage storage = level.m_8895_();
        WorldHeightSavedData data = (WorldHeightSavedData)storage.m_164861_(WorldHeightSavedData::load, WorldHeightSavedData::new, WorldHeightSavedData.dataName());
        BY_DIMENSION.put((ResourceKey<Level>)level.m_46472_(), data);
        WorldHeightManager.applyDimensionSettings(level, data);
        return data;
    }

    private static void applyDimensionSettings(ServerLevel level, WorldHeightSavedData data) {
        Holder holder = level.m_204156_();
        if (holder instanceof Holder.Reference) {
            Holder.Reference reference = (Holder.Reference)holder;
            DimensionType current = (DimensionType)reference.m_203334_();
            if (current.f_156647_() == data.minY() && current.f_156648_() == data.height()) {
                WorldHeightManager.applyNoiseSettings(level, data);
                return;
            }
            int logicalHeight = data.height();
            DimensionType updated = new DimensionType(current.f_63854_(), current.f_223549_(), current.f_63856_(), current.f_63857_(), current.f_63858_(), current.f_63859_(), current.f_63862_(), current.f_63863_(), data.minY(), data.height(), logicalHeight, current.f_63836_(), current.f_63837_(), current.f_63838_(), current.f_223550_());
            reference.m_247654_((Object)updated);
            WorldHeightManager.applyNoiseSettings(level, data);
        }
    }

    public static void applyClientSelection(ServerPlayer player, WorldHeightData.Snapshot snapshot) {
        MinecraftServer server = player.m_20194_();
        if (server == null) {
            return;
        }
        WorldHeightManager.applySnapshotToDimension(server.m_129880_(Level.f_46428_), snapshot);
        WorldHeightManager.applySnapshotToDimension(server.m_129880_(Level.f_46429_), snapshot);
        WorldHeightManager.applySnapshotToDimension(server.m_129880_(Level.f_46430_), snapshot);
        WorldHeightManager.applySnapshotToDimension(player.m_284548_(), snapshot);
    }

    private static void applySnapshotToDimension(ServerLevel level, WorldHeightData.Snapshot snapshot) {
        if (level == null) {
            return;
        }
        WorldHeightSavedData data = WorldHeightManager.getData(level);
        data.update(snapshot);
        WorldHeightManager.applyDimensionSettings(level, data);
    }

    private static void applyNoiseSettings(ServerLevel level, WorldHeightSavedData data) {
        ChunkGenerator chunkGenerator = level.m_7726_().m_8481_();
        if (!(chunkGenerator instanceof NoiseBasedChunkGenerator)) {
            return;
        }
        NoiseBasedChunkGenerator noiseGenerator = (NoiseBasedChunkGenerator)chunkGenerator;
        NoiseGeneratorSettings base = ORIGINAL_NOISE.computeIfAbsent((ResourceKey<Level>)level.m_46472_(), key -> (NoiseGeneratorSettings)noiseGenerator.m_224341_().m_203334_());
        NoiseGeneratorSettings target = NoiseGeneratorUtil.stretch(base, data.minY(), data.height());
        if (((NoiseGeneratorSettings)noiseGenerator.m_224341_().m_203334_()).equals((Object)target)) {
            return;
        }
        NoiseBasedChunkGenerator replacement = NoiseGeneratorUtil.recreate(noiseGenerator, target);
        ServerGeneratorReloader.swapGenerator(level, replacement, target);
    }
}

