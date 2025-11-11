package net.multiverse.dynamicheight.worldheight;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.multiverse.dynamicheight.util.NoiseGeneratorUtil;
import net.multiverse.dynamicheight.util.ServerGeneratorReloader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.LevelEvent;

/**
 * Server-side orchestration for loading the persisted height configuration and enforcing it at runtime.
 */
public final class WorldHeightManager {
    private static final Map<ResourceKey<Level>, WorldHeightSavedData> BY_DIMENSION = new ConcurrentHashMap<>();
    private static final Map<ResourceKey<Level>, NoiseGeneratorSettings> ORIGINAL_NOISE = new ConcurrentHashMap<>();

    private WorldHeightManager() {
    }

    public static void register() {
        MinecraftForge.EVENT_BUS.addListener(WorldHeightManager::onLevelLoad);
        MinecraftForge.EVENT_BUS.addListener(WorldHeightManager::onLevelUnload);
        MinecraftForge.EVENT_BUS.addListener(WorldHeightManager::onBlockPlaced);
    }

    private static void onLevelLoad(LevelEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }

        var storage = serverLevel.getDataStorage();
        WorldHeightSavedData data = storage.computeIfAbsent(WorldHeightSavedData::load, WorldHeightSavedData::new, WorldHeightSavedData.dataName());

        WorldHeightData.Snapshot snapshot = WorldHeightData.currentSnapshot();
        if (serverLevel.dimension().equals(Level.OVERWORLD) && WorldHeightData.hasPendingClientSelection()) {
            snapshot = WorldHeightData.consumeSnapshot();
        }

        data.update(snapshot);
        applyDimensionSettings(serverLevel, data);
        BY_DIMENSION.put(serverLevel.dimension(), data);
    }

    private static void onLevelUnload(LevelEvent.Unload event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }
        BY_DIMENSION.remove(serverLevel.dimension());
        ORIGINAL_NOISE.remove(serverLevel.dimension());
    }

    private static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        var level = event.getLevel();
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        WorldHeightSavedData data = getData(serverLevel);
        if (data == null) {
            return;
        }

        int y = event.getPos().getY();
        if (y < data.minY() || y >= data.maxY()) {
            event.setCanceled(true);
            if (event.getEntity() instanceof Player player) {
                player.displayClientMessage(Component.literal("Cannot place blocks outside the configured vertical range (" + data.minY() + " .. " + (data.maxY() - 1) + ") in " + serverLevel.dimension().location()).withStyle(ChatFormatting.RED), true);
            }
        }
    }

    private static WorldHeightSavedData getData(ServerLevel level) {
        WorldHeightSavedData cached = BY_DIMENSION.get(level.dimension());
        if (cached != null) {
            return cached;
        }
        var storage = level.getDataStorage();
        WorldHeightSavedData data = storage.computeIfAbsent(WorldHeightSavedData::load, WorldHeightSavedData::new, WorldHeightSavedData.dataName());
        BY_DIMENSION.put(level.dimension(), data);
        applyDimensionSettings(level, data);
        return data;
    }

    private static void applyDimensionSettings(ServerLevel level, WorldHeightSavedData data) {
        var holder = level.dimensionTypeRegistration();
        if (holder instanceof net.minecraft.core.Holder.Reference<DimensionType> reference) {
            DimensionType current = reference.value();
            if (current.minY() == data.minY() && current.height() == data.height()) {
                applyNoiseSettings(level, data);
                return;
            }

            int logicalHeight = data.height();
            DimensionType updated = new DimensionType(
                    current.fixedTime(),
                    current.hasSkyLight(),
                    current.hasCeiling(),
                    current.ultraWarm(),
                    current.natural(),
                    current.coordinateScale(),
                    current.bedWorks(),
                    current.respawnAnchorWorks(),
                    data.minY(),
                    data.height(),
                    logicalHeight,
                    current.infiniburn(),
                    current.effectsLocation(),
                    current.ambientLight(),
                    current.monsterSettings()
            );
            reference.bindValue(updated);
            applyNoiseSettings(level, data);
        }
    }

    public static void applyClientSelection(ServerPlayer player, WorldHeightData.Snapshot snapshot) {
        var server = player.getServer();
        if (server == null) {
            return;
        }
        applySnapshotToDimension(server.getLevel(Level.OVERWORLD), snapshot);
        applySnapshotToDimension(server.getLevel(Level.NETHER), snapshot);
        applySnapshotToDimension(server.getLevel(Level.END), snapshot);
        applySnapshotToDimension(player.serverLevel(), snapshot);
    }

    private static void applySnapshotToDimension(ServerLevel level, WorldHeightData.Snapshot snapshot) {
        if (level == null) {
            return;
        }
        WorldHeightSavedData data = getData(level);
        data.update(snapshot);
        applyDimensionSettings(level, data);
    }

    private static void applyNoiseSettings(ServerLevel level, WorldHeightSavedData data) {
        if (!(level.getChunkSource().getGenerator() instanceof NoiseBasedChunkGenerator noiseGenerator)) {
            return;
        }
        NoiseGeneratorSettings base = ORIGINAL_NOISE.computeIfAbsent(level.dimension(), key -> noiseGenerator.generatorSettings().value());
        NoiseGeneratorSettings target = NoiseGeneratorUtil.stretch(base, data.minY(), data.height());

        if (noiseGenerator.generatorSettings().value().equals(target)) {
            return;
        }

        NoiseBasedChunkGenerator replacement = NoiseGeneratorUtil.recreate(noiseGenerator, target);
        ServerGeneratorReloader.swapGenerator(level, replacement, target);
    }

}
