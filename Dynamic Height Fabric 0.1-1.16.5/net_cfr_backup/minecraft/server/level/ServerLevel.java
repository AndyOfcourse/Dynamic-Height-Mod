/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Queues
 *  com.google.common.collect.Sets
 *  it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  it.unimi.dsi.fastutil.longs.LongSets
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectIterator
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.CrashReport
 *  net.minecraft.Util
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.DefaultedRegistry
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Position
 *  net.minecraft.core.Registry
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.core.SectionPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.TranslatableComponent
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket
 *  net.minecraft.network.protocol.game.ClientboundBlockEventPacket
 *  net.minecraft.network.protocol.game.ClientboundEntityEventPacket
 *  net.minecraft.network.protocol.game.ClientboundExplodePacket
 *  net.minecraft.network.protocol.game.ClientboundGameEventPacket
 *  net.minecraft.network.protocol.game.ClientboundLevelEventPacket
 *  net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket
 *  net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket
 *  net.minecraft.network.protocol.game.ClientboundSoundEntityPacket
 *  net.minecraft.network.protocol.game.ClientboundSoundPacket
 *  net.minecraft.network.protocol.game.DebugPackets
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.ServerScoreboard
 *  net.minecraft.server.level.ChunkMap
 *  net.minecraft.server.level.ServerChunkCache
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.level.TicketType
 *  net.minecraft.server.level.progress.ChunkProgressListener
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.TagContainer
 *  net.minecraft.util.ClassInstanceMultiMap
 *  net.minecraft.util.CsvOutput
 *  net.minecraft.util.Mth
 *  net.minecraft.util.ProgressListener
 *  net.minecraft.util.Unit
 *  net.minecraft.util.profiling.ProfilerFiller
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LightningBolt
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobCategory
 *  net.minecraft.world.entity.ReputationEventHandler
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.ai.village.ReputationEventType
 *  net.minecraft.world.entity.ai.village.poi.PoiManager
 *  net.minecraft.world.entity.ai.village.poi.PoiType
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.entity.animal.WaterAnimal
 *  net.minecraft.world.entity.animal.horse.SkeletonHorse
 *  net.minecraft.world.entity.boss.EnderDragonPart
 *  net.minecraft.world.entity.boss.enderdragon.EnderDragon
 *  net.minecraft.world.entity.npc.Npc
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.raid.Raid
 *  net.minecraft.world.entity.raid.Raids
 *  net.minecraft.world.item.crafting.RecipeManager
 *  net.minecraft.world.level.BlockEventData
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.CustomSpawner
 *  net.minecraft.world.level.Explosion
 *  net.minecraft.world.level.Explosion$BlockInteraction
 *  net.minecraft.world.level.ExplosionDamageCalculator
 *  net.minecraft.world.level.ForcedChunksSavedData
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.ServerTickList
 *  net.minecraft.world.level.StructureFeatureManager
 *  net.minecraft.world.level.TickList
 *  net.minecraft.world.level.TickNextTickData
 *  net.minecraft.world.level.WorldGenLevel
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.biome.Biome$Precipitation
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.ChunkAccess
 *  net.minecraft.world.level.chunk.ChunkGenerator
 *  net.minecraft.world.level.chunk.ChunkSource
 *  net.minecraft.world.level.chunk.ChunkStatus
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.chunk.LevelChunkSection
 *  net.minecraft.world.level.dimension.DimensionType
 *  net.minecraft.world.level.dimension.end.EndDragonFight
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.level.levelgen.feature.StructureFeature
 *  net.minecraft.world.level.levelgen.structure.BoundingBox
 *  net.minecraft.world.level.levelgen.structure.StructureStart
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.portal.PortalForcer
 *  net.minecraft.world.level.saveddata.SavedData
 *  net.minecraft.world.level.saveddata.maps.MapIndex
 *  net.minecraft.world.level.saveddata.maps.MapItemSavedData
 *  net.minecraft.world.level.storage.DimensionDataStorage
 *  net.minecraft.world.level.storage.LevelStorageSource$LevelStorageAccess
 *  net.minecraft.world.level.storage.ServerLevelData
 *  net.minecraft.world.level.storage.WritableLevelData
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.BooleanOp
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraft.world.scores.Scoreboard
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.server.level;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.CrashReport;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagContainer;
import net.minecraft.util.ClassInstanceMultiMap;
import net.minecraft.util.CsvOutput;
import net.minecraft.util.Mth;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ReputationEventHandler;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raids;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.BlockEventData;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.ForcedChunksSavedData;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerTickList;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.TickList;
import net.minecraft.world.level.TickNextTickData;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.portal.PortalForcer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.maps.MapIndex;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.scores.Scoreboard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ServerLevel
extends Level
implements WorldGenLevel {
    public static final BlockPos END_SPAWN_POINT = new BlockPos(100, 50, 0);
    private static final Logger LOGGER = LogManager.getLogger();
    private final Int2ObjectMap<Entity> entitiesById = new Int2ObjectLinkedOpenHashMap();
    private final Map<UUID, Entity> entitiesByUuid = Maps.newHashMap();
    private final Queue<Entity> toAddAfterTick = Queues.newArrayDeque();
    private final List<ServerPlayer> players = Lists.newArrayList();
    private final ServerChunkCache chunkSource;
    boolean tickingEntities;
    private final MinecraftServer server;
    private final ServerLevelData serverLevelData;
    public boolean noSave;
    private boolean allPlayersSleeping;
    private int emptyTime;
    private final PortalForcer portalForcer;
    private final ServerTickList<Block> blockTicks = new ServerTickList(this, block -> block == null || block.defaultBlockState().isAir(), arg_0 -> ((DefaultedRegistry)Registry.BLOCK).getKey(arg_0), this::tickBlock);
    private final ServerTickList<Fluid> liquidTicks = new ServerTickList(this, fluid -> fluid == null || fluid == Fluids.EMPTY, arg_0 -> ((DefaultedRegistry)Registry.FLUID).getKey(arg_0), this::tickLiquid);
    private final Set<PathNavigation> navigations = Sets.newHashSet();
    protected final Raids raids;
    private final ObjectLinkedOpenHashSet<BlockEventData> blockEvents = new ObjectLinkedOpenHashSet();
    private boolean handlingTick;
    private final List<CustomSpawner> customSpawners;
    @Nullable
    private final EndDragonFight dragonFight;
    private final StructureFeatureManager structureFeatureManager;
    private final boolean tickTime;

    public ServerLevel(MinecraftServer minecraftServer, Executor executor, LevelStorageSource.LevelStorageAccess levelStorageAccess, ServerLevelData serverLevelData, ResourceKey<Level> resourceKey, DimensionType dimensionType, ChunkProgressListener chunkProgressListener, ChunkGenerator chunkGenerator, boolean bl, long l, List<CustomSpawner> list, boolean bl2) {
        super((WritableLevelData)serverLevelData, resourceKey, dimensionType, () -> ((MinecraftServer)minecraftServer).getProfiler(), false, bl, l);
        this.tickTime = bl2;
        this.server = minecraftServer;
        this.customSpawners = list;
        this.serverLevelData = serverLevelData;
        this.chunkSource = new ServerChunkCache(this, levelStorageAccess, minecraftServer.getFixerUpper(), minecraftServer.getStructureManager(), executor, chunkGenerator, minecraftServer.getPlayerList().getViewDistance(), minecraftServer.forceSynchronousWrites(), chunkProgressListener, () -> minecraftServer.overworld().getDataStorage());
        this.portalForcer = new PortalForcer(this);
        this.updateSkyBrightness();
        this.prepareWeather();
        this.getWorldBorder().setAbsoluteMaxSize(minecraftServer.getAbsoluteMaxWorldSize());
        this.raids = (Raids)this.getDataStorage().computeIfAbsent(() -> new Raids(this), Raids.getFileId((DimensionType)this.dimensionType()));
        if (!minecraftServer.isSingleplayer()) {
            serverLevelData.setGameType(minecraftServer.getDefaultGameType());
        }
        this.structureFeatureManager = new StructureFeatureManager((LevelAccessor)this, minecraftServer.getWorldData().worldGenSettings());
        this.dragonFight = this.dimensionType().createDragonFight() ? new EndDragonFight(this, minecraftServer.getWorldData().worldGenSettings().seed(), minecraftServer.getWorldData().endDragonFightData()) : null;
    }

    public void setWeatherParameters(int i, int j, boolean bl, boolean bl2) {
        this.serverLevelData.setClearWeatherTime(i);
        this.serverLevelData.setRainTime(j);
        this.serverLevelData.setThunderTime(j);
        this.serverLevelData.setRaining(bl);
        this.serverLevelData.setThundering(bl2);
    }

    public Biome getUncachedNoiseBiome(int i, int j, int k) {
        return this.getChunkSource().getGenerator().getBiomeSource().getNoiseBiome(i, j, k);
    }

    public StructureFeatureManager structureFeatureManager() {
        return this.structureFeatureManager;
    }

    public void tick(BooleanSupplier booleanSupplier) {
        boolean bl4;
        ProfilerFiller profilerFiller = this.getProfiler();
        this.handlingTick = true;
        profilerFiller.push("world border");
        this.getWorldBorder().tick();
        profilerFiller.popPush("weather");
        boolean bl = this.isRaining();
        if (this.dimensionType().hasSkyLight()) {
            if (this.getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE)) {
                int i = this.serverLevelData.getClearWeatherTime();
                int j = this.serverLevelData.getThunderTime();
                int k = this.serverLevelData.getRainTime();
                boolean bl2 = this.levelData.isThundering();
                boolean bl3 = this.levelData.isRaining();
                if (i > 0) {
                    --i;
                    j = bl2 ? 0 : 1;
                    k = bl3 ? 0 : 1;
                    bl2 = false;
                    bl3 = false;
                } else {
                    if (j > 0) {
                        if (--j == 0) {
                            bl2 = !bl2;
                        }
                    } else {
                        j = bl2 ? this.random.nextInt(12000) + 3600 : this.random.nextInt(168000) + 12000;
                    }
                    if (k > 0) {
                        if (--k == 0) {
                            bl3 = !bl3;
                        }
                    } else {
                        k = bl3 ? this.random.nextInt(12000) + 12000 : this.random.nextInt(168000) + 12000;
                    }
                }
                this.serverLevelData.setThunderTime(j);
                this.serverLevelData.setRainTime(k);
                this.serverLevelData.setClearWeatherTime(i);
                this.serverLevelData.setThundering(bl2);
                this.serverLevelData.setRaining(bl3);
            }
            this.oThunderLevel = this.thunderLevel;
            this.thunderLevel = this.levelData.isThundering() ? (float)((double)this.thunderLevel + 0.01) : (float)((double)this.thunderLevel - 0.01);
            this.thunderLevel = Mth.clamp((float)this.thunderLevel, (float)0.0f, (float)1.0f);
            this.oRainLevel = this.rainLevel;
            this.rainLevel = this.levelData.isRaining() ? (float)((double)this.rainLevel + 0.01) : (float)((double)this.rainLevel - 0.01);
            this.rainLevel = Mth.clamp((float)this.rainLevel, (float)0.0f, (float)1.0f);
        }
        if (this.oRainLevel != this.rainLevel) {
            this.server.getPlayerList().broadcastAll((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.RAIN_LEVEL_CHANGE, this.rainLevel), this.dimension());
        }
        if (this.oThunderLevel != this.thunderLevel) {
            this.server.getPlayerList().broadcastAll((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.THUNDER_LEVEL_CHANGE, this.thunderLevel), this.dimension());
        }
        if (bl != this.isRaining()) {
            if (bl) {
                this.server.getPlayerList().broadcastAll((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.STOP_RAINING, 0.0f));
            } else {
                this.server.getPlayerList().broadcastAll((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.START_RAINING, 0.0f));
            }
            this.server.getPlayerList().broadcastAll((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.RAIN_LEVEL_CHANGE, this.rainLevel));
            this.server.getPlayerList().broadcastAll((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.THUNDER_LEVEL_CHANGE, this.thunderLevel));
        }
        if (this.allPlayersSleeping && this.players.stream().noneMatch(serverPlayer -> !serverPlayer.isSpectator() && !serverPlayer.isSleepingLongEnough())) {
            this.allPlayersSleeping = false;
            if (this.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
                long l = this.levelData.getDayTime() + 24000L;
                this.setDayTime(l - l % 24000L);
            }
            this.wakeUpAllPlayers();
            if (this.getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE)) {
                this.stopWeather();
            }
        }
        this.updateSkyBrightness();
        this.tickTime();
        profilerFiller.popPush("chunkSource");
        this.getChunkSource().tick(booleanSupplier);
        profilerFiller.popPush("tickPending");
        if (!this.isDebug()) {
            this.blockTicks.tick();
            this.liquidTicks.tick();
        }
        profilerFiller.popPush("raid");
        this.raids.tick();
        profilerFiller.popPush("blockEvents");
        this.runBlockEvents();
        this.handlingTick = false;
        profilerFiller.popPush("entities");
        boolean bl2 = bl4 = !this.players.isEmpty() || !this.getForcedChunks().isEmpty();
        if (bl4) {
            this.resetEmptyTime();
        }
        if (bl4 || this.emptyTime++ < 300) {
            Entity entity3;
            if (this.dragonFight != null) {
                this.dragonFight.tick();
            }
            this.tickingEntities = true;
            ObjectIterator objectIterator = this.entitiesById.int2ObjectEntrySet().iterator();
            while (objectIterator.hasNext()) {
                Int2ObjectMap.Entry entry = (Int2ObjectMap.Entry)objectIterator.next();
                Entity entity = (Entity)entry.getValue();
                Entity entity2 = entity.getVehicle();
                if (!this.server.isSpawningAnimals() && (entity instanceof Animal || entity instanceof WaterAnimal)) {
                    entity.remove();
                }
                if (!this.server.areNpcsEnabled() && entity instanceof Npc) {
                    entity.remove();
                }
                profilerFiller.push("checkDespawn");
                if (!entity.removed) {
                    entity.checkDespawn();
                }
                profilerFiller.pop();
                if (entity2 != null) {
                    if (!entity2.removed && entity2.hasPassenger(entity)) continue;
                    entity.stopRiding();
                }
                profilerFiller.push("tick");
                if (!entity.removed && !(entity instanceof EnderDragonPart)) {
                    this.guardEntityTick(this::tickNonPassenger, entity);
                }
                profilerFiller.pop();
                profilerFiller.push("remove");
                if (entity.removed) {
                    this.removeFromChunk(entity);
                    objectIterator.remove();
                    this.onEntityRemoved(entity);
                }
                profilerFiller.pop();
            }
            this.tickingEntities = false;
            while ((entity3 = this.toAddAfterTick.poll()) != null) {
                this.add(entity3);
            }
            this.tickBlockEntities();
        }
        profilerFiller.pop();
    }

    protected void tickTime() {
        if (!this.tickTime) {
            return;
        }
        long l = this.levelData.getGameTime() + 1L;
        this.serverLevelData.setGameTime(l);
        this.serverLevelData.getScheduledEvents().tick((Object)this.server, l);
        if (this.levelData.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
            this.setDayTime(this.levelData.getDayTime() + 1L);
        }
    }

    public void setDayTime(long l) {
        this.serverLevelData.setDayTime(l);
    }

    public void tickCustomSpawners(boolean bl, boolean bl2) {
        for (CustomSpawner customSpawner : this.customSpawners) {
            customSpawner.tick(this, bl, bl2);
        }
    }

    private void wakeUpAllPlayers() {
        this.players.stream().filter(LivingEntity::isSleeping).collect(Collectors.toList()).forEach(serverPlayer -> serverPlayer.stopSleepInBed(false, false));
    }

    public void tickChunk(LevelChunk levelChunk, int i) {
        BlockPos blockPos;
        ChunkPos chunkPos = levelChunk.getPos();
        boolean bl = this.isRaining();
        int j = chunkPos.getMinBlockX();
        int k = chunkPos.getMinBlockZ();
        ProfilerFiller profilerFiller = this.getProfiler();
        profilerFiller.push("thunder");
        if (bl && this.isThundering() && this.random.nextInt(100000) == 0 && this.isRainingAt(blockPos = this.findLightingTargetAround(this.getBlockRandomPos(j, 0, k, 15)))) {
            boolean bl2;
            DifficultyInstance difficultyInstance = this.getCurrentDifficultyAt(blockPos);
            boolean bl3 = bl2 = this.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING) && this.random.nextDouble() < (double)difficultyInstance.getEffectiveDifficulty() * 0.01;
            if (bl2) {
                SkeletonHorse skeletonHorse = (SkeletonHorse)EntityType.SKELETON_HORSE.create((Level)this);
                skeletonHorse.setTrap(true);
                skeletonHorse.setAge(0);
                skeletonHorse.setPos((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ());
                this.addFreshEntity((Entity)skeletonHorse);
            }
            LightningBolt lightningBolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create((Level)this);
            lightningBolt.moveTo(Vec3.atBottomCenterOf((Vec3i)blockPos));
            lightningBolt.setVisualOnly(bl2);
            this.addFreshEntity((Entity)lightningBolt);
        }
        profilerFiller.popPush("iceandsnow");
        if (this.random.nextInt(16) == 0) {
            blockPos = this.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, this.getBlockRandomPos(j, 0, k, 15));
            BlockPos blockPos2 = blockPos.below();
            Biome biome = this.getBiome(blockPos);
            if (biome.shouldFreeze((LevelReader)this, blockPos2)) {
                this.setBlockAndUpdate(blockPos2, Blocks.ICE.defaultBlockState());
            }
            if (bl && biome.shouldSnow((LevelReader)this, blockPos)) {
                this.setBlockAndUpdate(blockPos, Blocks.SNOW.defaultBlockState());
            }
            if (bl && this.getBiome(blockPos2).getPrecipitation() == Biome.Precipitation.RAIN) {
                this.getBlockState(blockPos2).getBlock().handleRain((Level)this, blockPos2);
            }
        }
        profilerFiller.popPush("tickBlocks");
        if (i > 0) {
            for (LevelChunkSection levelChunkSection : levelChunk.getSections()) {
                if (levelChunkSection == LevelChunk.EMPTY_SECTION || !levelChunkSection.isRandomlyTicking()) continue;
                int l = levelChunkSection.bottomBlockY();
                for (int m = 0; m < i; ++m) {
                    FluidState fluidState;
                    BlockPos blockPos3 = this.getBlockRandomPos(j, l, k, 15);
                    profilerFiller.push("randomTick");
                    BlockState blockState = levelChunkSection.getBlockState(blockPos3.getX() - j, blockPos3.getY() - l, blockPos3.getZ() - k);
                    if (blockState.isRandomlyTicking()) {
                        blockState.randomTick(this, blockPos3, this.random);
                    }
                    if ((fluidState = blockState.getFluidState()).isRandomlyTicking()) {
                        fluidState.randomTick((Level)this, blockPos3, this.random);
                    }
                    profilerFiller.pop();
                }
            }
        }
        profilerFiller.pop();
    }

    protected BlockPos findLightingTargetAround(BlockPos blockPos) {
        BlockPos blockPos2 = this.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockPos);
        AABB aABB = new AABB(blockPos2, new BlockPos(blockPos2.getX(), this.getMaxBuildHeight(), blockPos2.getZ())).inflate(3.0);
        List list = this.getEntitiesOfClass(LivingEntity.class, aABB, livingEntity -> livingEntity != null && livingEntity.isAlive() && this.canSeeSky(livingEntity.blockPosition()));
        if (!list.isEmpty()) {
            return ((LivingEntity)list.get(this.random.nextInt(list.size()))).blockPosition();
        }
        if (blockPos2.getY() == -1) {
            blockPos2 = blockPos2.above(2);
        }
        return blockPos2;
    }

    public boolean isHandlingTick() {
        return this.handlingTick;
    }

    public void updateSleepingPlayerList() {
        this.allPlayersSleeping = false;
        if (!this.players.isEmpty()) {
            int i = 0;
            int j = 0;
            for (ServerPlayer serverPlayer : this.players) {
                if (serverPlayer.isSpectator()) {
                    ++i;
                    continue;
                }
                if (!serverPlayer.isSleeping()) continue;
                ++j;
            }
            this.allPlayersSleeping = j > 0 && j >= this.players.size() - i;
        }
    }

    public ServerScoreboard getScoreboard() {
        return this.server.getScoreboard();
    }

    private void stopWeather() {
        this.serverLevelData.setRainTime(0);
        this.serverLevelData.setRaining(false);
        this.serverLevelData.setThunderTime(0);
        this.serverLevelData.setThundering(false);
    }

    public void resetEmptyTime() {
        this.emptyTime = 0;
    }

    private void tickLiquid(TickNextTickData<Fluid> tickNextTickData) {
        FluidState fluidState = this.getFluidState(tickNextTickData.pos);
        if (fluidState.getType() == tickNextTickData.getType()) {
            fluidState.tick((Level)this, tickNextTickData.pos);
        }
    }

    private void tickBlock(TickNextTickData<Block> tickNextTickData) {
        BlockState blockState = this.getBlockState(tickNextTickData.pos);
        if (blockState.is((Block)tickNextTickData.getType())) {
            blockState.tick(this, tickNextTickData.pos, this.random);
        }
    }

    public void tickNonPassenger(Entity entity) {
        if (!(entity instanceof Player) && !this.getChunkSource().isEntityTickingChunk(entity)) {
            this.updateChunkPos(entity);
            return;
        }
        entity.setPosAndOldPos(entity.getX(), entity.getY(), entity.getZ());
        entity.yRotO = entity.yRot;
        entity.xRotO = entity.xRot;
        if (entity.inChunk) {
            ++entity.tickCount;
            ProfilerFiller profilerFiller = this.getProfiler();
            profilerFiller.push(() -> Registry.ENTITY_TYPE.getKey((Object)entity.getType()).toString());
            profilerFiller.incrementCounter("tickNonPassenger");
            entity.tick();
            profilerFiller.pop();
        }
        this.updateChunkPos(entity);
        if (entity.inChunk) {
            for (Entity entity2 : entity.getPassengers()) {
                this.tickPassenger(entity, entity2);
            }
        }
    }

    public void tickPassenger(Entity entity, Entity entity2) {
        if (entity2.removed || entity2.getVehicle() != entity) {
            entity2.stopRiding();
            return;
        }
        if (!(entity2 instanceof Player) && !this.getChunkSource().isEntityTickingChunk(entity2)) {
            return;
        }
        entity2.setPosAndOldPos(entity2.getX(), entity2.getY(), entity2.getZ());
        entity2.yRotO = entity2.yRot;
        entity2.xRotO = entity2.xRot;
        if (entity2.inChunk) {
            ++entity2.tickCount;
            ProfilerFiller profilerFiller = this.getProfiler();
            profilerFiller.push(() -> Registry.ENTITY_TYPE.getKey((Object)entity2.getType()).toString());
            profilerFiller.incrementCounter("tickPassenger");
            entity2.rideTick();
            profilerFiller.pop();
        }
        this.updateChunkPos(entity2);
        if (entity2.inChunk) {
            for (Entity entity3 : entity2.getPassengers()) {
                this.tickPassenger(entity2, entity3);
            }
        }
    }

    public void updateChunkPos(Entity entity) {
        if (!entity.checkAndResetUpdateChunkPos()) {
            return;
        }
        this.getProfiler().push("chunkCheck");
        int i = Mth.floor((double)(entity.getX() / 16.0));
        int j = Mth.floor((double)(entity.getY() / 16.0));
        int k = Mth.floor((double)(entity.getZ() / 16.0));
        if (!entity.inChunk || entity.xChunk != i || entity.yChunk != j || entity.zChunk != k) {
            if (entity.inChunk && this.hasChunk(entity.xChunk, entity.zChunk)) {
                this.getChunk(entity.xChunk, entity.zChunk).removeEntity(entity, entity.yChunk);
            }
            if (entity.checkAndResetForcedChunkAdditionFlag() || this.hasChunk(i, k)) {
                this.getChunk(i, k).addEntity(entity);
            } else {
                if (entity.inChunk) {
                    LOGGER.warn("Entity {} left loaded chunk area", (Object)entity);
                }
                entity.inChunk = false;
            }
        }
        this.getProfiler().pop();
    }

    public boolean mayInteract(Player player, BlockPos blockPos) {
        return !this.server.isUnderSpawnProtection(this, blockPos, player) && this.getWorldBorder().isWithinBounds(blockPos);
    }

    public void save(@Nullable ProgressListener progressListener, boolean bl, boolean bl2) {
        ServerChunkCache serverChunkCache = this.getChunkSource();
        if (bl2) {
            return;
        }
        if (progressListener != null) {
            progressListener.progressStartNoAbort((Component)new TranslatableComponent("menu.savingLevel"));
        }
        this.saveLevelData();
        if (progressListener != null) {
            progressListener.progressStage((Component)new TranslatableComponent("menu.savingChunks"));
        }
        serverChunkCache.save(bl);
    }

    private void saveLevelData() {
        if (this.dragonFight != null) {
            this.server.getWorldData().setEndDragonFightData(this.dragonFight.saveData());
        }
        this.getChunkSource().getDataStorage().save();
    }

    public List<Entity> getEntities(@Nullable EntityType<?> entityType, Predicate<? super Entity> predicate) {
        ArrayList list = Lists.newArrayList();
        ServerChunkCache serverChunkCache = this.getChunkSource();
        for (Entity entity : this.entitiesById.values()) {
            if (entityType != null && entity.getType() != entityType || !serverChunkCache.hasChunk(Mth.floor((double)entity.getX()) >> 4, Mth.floor((double)entity.getZ()) >> 4) || !predicate.test((Entity)entity)) continue;
            list.add(entity);
        }
        return list;
    }

    public List<EnderDragon> getDragons() {
        ArrayList list = Lists.newArrayList();
        for (Entity entity : this.entitiesById.values()) {
            if (!(entity instanceof EnderDragon) || !entity.isAlive()) continue;
            list.add((EnderDragon)entity);
        }
        return list;
    }

    public List<ServerPlayer> getPlayers(Predicate<? super ServerPlayer> predicate) {
        ArrayList list = Lists.newArrayList();
        for (ServerPlayer serverPlayer : this.players) {
            if (!predicate.test((ServerPlayer)serverPlayer)) continue;
            list.add(serverPlayer);
        }
        return list;
    }

    @Nullable
    public ServerPlayer getRandomPlayer() {
        List<ServerPlayer> list = this.getPlayers(LivingEntity::isAlive);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(this.random.nextInt(list.size()));
    }

    public boolean addFreshEntity(Entity entity) {
        return this.addEntity(entity);
    }

    public boolean addWithUUID(Entity entity) {
        return this.addEntity(entity);
    }

    public void addFromAnotherDimension(Entity entity) {
        boolean bl = entity.forcedLoading;
        entity.forcedLoading = true;
        this.addWithUUID(entity);
        entity.forcedLoading = bl;
        this.updateChunkPos(entity);
    }

    public void addDuringCommandTeleport(ServerPlayer serverPlayer) {
        this.addPlayer(serverPlayer);
        this.updateChunkPos((Entity)serverPlayer);
    }

    public void addDuringPortalTeleport(ServerPlayer serverPlayer) {
        this.addPlayer(serverPlayer);
        this.updateChunkPos((Entity)serverPlayer);
    }

    public void addNewPlayer(ServerPlayer serverPlayer) {
        this.addPlayer(serverPlayer);
    }

    public void addRespawnedPlayer(ServerPlayer serverPlayer) {
        this.addPlayer(serverPlayer);
    }

    private void addPlayer(ServerPlayer serverPlayer) {
        Entity entity = this.entitiesByUuid.get(serverPlayer.getUUID());
        if (entity != null) {
            LOGGER.warn("Force-added player with duplicate UUID {}", (Object)serverPlayer.getUUID().toString());
            entity.unRide();
            this.removePlayerImmediately((ServerPlayer)entity);
        }
        this.players.add(serverPlayer);
        this.updateSleepingPlayerList();
        ChunkAccess chunkAccess = this.getChunk(Mth.floor((double)(serverPlayer.getX() / 16.0)), Mth.floor((double)(serverPlayer.getZ() / 16.0)), ChunkStatus.FULL, true);
        if (chunkAccess instanceof LevelChunk) {
            chunkAccess.addEntity((Entity)serverPlayer);
        }
        this.add((Entity)serverPlayer);
    }

    private boolean addEntity(Entity entity) {
        if (entity.removed) {
            LOGGER.warn("Tried to add entity {} but it was marked as removed already", (Object)EntityType.getKey((EntityType)entity.getType()));
            return false;
        }
        if (this.isUUIDUsed(entity)) {
            return false;
        }
        ChunkAccess chunkAccess = this.getChunk(Mth.floor((double)(entity.getX() / 16.0)), Mth.floor((double)(entity.getZ() / 16.0)), ChunkStatus.FULL, entity.forcedLoading);
        if (!(chunkAccess instanceof LevelChunk)) {
            return false;
        }
        chunkAccess.addEntity(entity);
        this.add(entity);
        return true;
    }

    public boolean loadFromChunk(Entity entity) {
        if (this.isUUIDUsed(entity)) {
            return false;
        }
        this.add(entity);
        return true;
    }

    private boolean isUUIDUsed(Entity entity) {
        UUID uUID = entity.getUUID();
        Entity entity2 = this.findAddedOrPendingEntity(uUID);
        if (entity2 == null) {
            return false;
        }
        LOGGER.warn("Trying to add entity with duplicated UUID {}. Existing {}#{}, new: {}#{}", (Object)uUID, (Object)EntityType.getKey((EntityType)entity2.getType()), (Object)entity2.getId(), (Object)EntityType.getKey((EntityType)entity.getType()), (Object)entity.getId());
        return true;
    }

    @Nullable
    private Entity findAddedOrPendingEntity(UUID uUID) {
        Entity entity = this.entitiesByUuid.get(uUID);
        if (entity != null) {
            return entity;
        }
        if (this.tickingEntities) {
            for (Entity entity2 : this.toAddAfterTick) {
                if (!entity2.getUUID().equals(uUID)) continue;
                return entity2;
            }
        }
        return null;
    }

    public boolean tryAddFreshEntityWithPassengers(Entity entity) {
        if (entity.getSelfAndPassengers().anyMatch(this::isUUIDUsed)) {
            return false;
        }
        this.addFreshEntityWithPassengers(entity);
        return true;
    }

    public void unload(LevelChunk levelChunk) {
        this.blockEntitiesToUnload.addAll(levelChunk.getBlockEntities().values());
        for (ClassInstanceMultiMap classInstanceMultiMap : levelChunk.getEntitySections()) {
            for (Entity entity : classInstanceMultiMap) {
                if (entity instanceof ServerPlayer) continue;
                if (this.tickingEntities) {
                    throw (IllegalStateException)Util.pauseInIde((Throwable)new IllegalStateException("Removing entity while ticking!"));
                }
                this.entitiesById.remove(entity.getId());
                this.onEntityRemoved(entity);
            }
        }
    }

    public void onEntityRemoved(Entity entity) {
        if (entity instanceof EnderDragon) {
            for (EnderDragonPart enderDragonPart : ((EnderDragon)entity).getSubEntities()) {
                enderDragonPart.remove();
            }
        }
        this.entitiesByUuid.remove(entity.getUUID());
        this.getChunkSource().removeEntity(entity);
        if (entity instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer)entity;
            this.players.remove(serverPlayer);
        }
        this.getScoreboard().entityRemoved(entity);
        if (entity instanceof Mob) {
            this.navigations.remove(((Mob)entity).getNavigation());
        }
    }

    private void add(Entity entity) {
        if (this.tickingEntities) {
            this.toAddAfterTick.add(entity);
        } else {
            this.entitiesById.put(entity.getId(), (Object)entity);
            if (entity instanceof EnderDragon) {
                for (EnderDragonPart enderDragonPart : ((EnderDragon)entity).getSubEntities()) {
                    this.entitiesById.put(enderDragonPart.getId(), (Object)enderDragonPart);
                }
            }
            this.entitiesByUuid.put(entity.getUUID(), entity);
            this.getChunkSource().addEntity(entity);
            if (entity instanceof Mob) {
                this.navigations.add(((Mob)entity).getNavigation());
            }
        }
    }

    public void despawn(Entity entity) {
        if (this.tickingEntities) {
            throw (IllegalStateException)Util.pauseInIde((Throwable)new IllegalStateException("Removing entity while ticking!"));
        }
        this.removeFromChunk(entity);
        this.entitiesById.remove(entity.getId());
        this.onEntityRemoved(entity);
    }

    private void removeFromChunk(Entity entity) {
        ChunkAccess chunkAccess = this.getChunk(entity.xChunk, entity.zChunk, ChunkStatus.FULL, false);
        if (chunkAccess instanceof LevelChunk) {
            ((LevelChunk)chunkAccess).removeEntity(entity);
        }
    }

    public void removePlayerImmediately(ServerPlayer serverPlayer) {
        serverPlayer.remove();
        this.despawn((Entity)serverPlayer);
        this.updateSleepingPlayerList();
    }

    public void destroyBlockProgress(int i, BlockPos blockPos, int j) {
        for (ServerPlayer serverPlayer : this.server.getPlayerList().getPlayers()) {
            double f;
            double e;
            double d;
            if (serverPlayer == null || serverPlayer.level != this || serverPlayer.getId() == i || !((d = (double)blockPos.getX() - serverPlayer.getX()) * d + (e = (double)blockPos.getY() - serverPlayer.getY()) * e + (f = (double)blockPos.getZ() - serverPlayer.getZ()) * f < 1024.0)) continue;
            serverPlayer.connection.send((Packet)new ClientboundBlockDestructionPacket(i, blockPos, j));
        }
    }

    public void playSound(@Nullable Player player, double d, double e, double f, SoundEvent soundEvent, SoundSource soundSource, float g, float h) {
        this.server.getPlayerList().broadcast(player, d, e, f, g > 1.0f ? (double)(16.0f * g) : 16.0, this.dimension(), (Packet)new ClientboundSoundPacket(soundEvent, soundSource, d, e, f, g, h));
    }

    public void playSound(@Nullable Player player, Entity entity, SoundEvent soundEvent, SoundSource soundSource, float f, float g) {
        this.server.getPlayerList().broadcast(player, entity.getX(), entity.getY(), entity.getZ(), f > 1.0f ? (double)(16.0f * f) : 16.0, this.dimension(), (Packet)new ClientboundSoundEntityPacket(soundEvent, soundSource, entity, f, g));
    }

    public void globalLevelEvent(int i, BlockPos blockPos, int j) {
        this.server.getPlayerList().broadcastAll((Packet)new ClientboundLevelEventPacket(i, blockPos, j, true));
    }

    public void levelEvent(@Nullable Player player, int i, BlockPos blockPos, int j) {
        this.server.getPlayerList().broadcast(player, (double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), 64.0, this.dimension(), (Packet)new ClientboundLevelEventPacket(i, blockPos, j, false));
    }

    public void sendBlockUpdated(BlockPos blockPos, BlockState blockState, BlockState blockState2, int i) {
        this.getChunkSource().blockChanged(blockPos);
        VoxelShape voxelShape = blockState.getCollisionShape((BlockGetter)this, blockPos);
        VoxelShape voxelShape2 = blockState2.getCollisionShape((BlockGetter)this, blockPos);
        if (!Shapes.joinIsNotEmpty((VoxelShape)voxelShape, (VoxelShape)voxelShape2, (BooleanOp)BooleanOp.NOT_SAME)) {
            return;
        }
        for (PathNavigation pathNavigation : this.navigations) {
            if (pathNavigation.hasDelayedRecomputation()) continue;
            pathNavigation.recomputePath(blockPos);
        }
    }

    public void broadcastEntityEvent(Entity entity, byte b) {
        this.getChunkSource().broadcastAndSend(entity, (Packet)new ClientboundEntityEventPacket(entity, b));
    }

    public ServerChunkCache getChunkSource() {
        return this.chunkSource;
    }

    public Explosion explode(@Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionDamageCalculator explosionDamageCalculator, double d, double e, double f, float g, boolean bl, Explosion.BlockInteraction blockInteraction) {
        Explosion explosion = new Explosion((Level)this, entity, damageSource, explosionDamageCalculator, d, e, f, g, bl, blockInteraction);
        explosion.explode();
        explosion.finalizeExplosion(false);
        if (blockInteraction == Explosion.BlockInteraction.NONE) {
            explosion.clearToBlow();
        }
        for (ServerPlayer serverPlayer : this.players) {
            if (!(serverPlayer.distanceToSqr(d, e, f) < 4096.0)) continue;
            serverPlayer.connection.send((Packet)new ClientboundExplodePacket(d, e, f, g, explosion.getToBlow(), (Vec3)explosion.getHitPlayers().get(serverPlayer)));
        }
        return explosion;
    }

    public void blockEvent(BlockPos blockPos, Block block, int i, int j) {
        this.blockEvents.add((Object)new BlockEventData(blockPos, block, i, j));
    }

    private void runBlockEvents() {
        while (!this.blockEvents.isEmpty()) {
            BlockEventData blockEventData = (BlockEventData)this.blockEvents.removeFirst();
            if (!this.doBlockEvent(blockEventData)) continue;
            this.server.getPlayerList().broadcast(null, (double)blockEventData.getPos().getX(), (double)blockEventData.getPos().getY(), (double)blockEventData.getPos().getZ(), 64.0, this.dimension(), (Packet)new ClientboundBlockEventPacket(blockEventData.getPos(), blockEventData.getBlock(), blockEventData.getParamA(), blockEventData.getParamB()));
        }
    }

    private boolean doBlockEvent(BlockEventData blockEventData) {
        BlockState blockState = this.getBlockState(blockEventData.getPos());
        if (blockState.is(blockEventData.getBlock())) {
            return blockState.triggerEvent((Level)this, blockEventData.getPos(), blockEventData.getParamA(), blockEventData.getParamB());
        }
        return false;
    }

    public ServerTickList<Block> getBlockTicks() {
        return this.blockTicks;
    }

    public ServerTickList<Fluid> getLiquidTicks() {
        return this.liquidTicks;
    }

    @NotNull
    public MinecraftServer getServer() {
        return this.server;
    }

    public PortalForcer getPortalForcer() {
        return this.portalForcer;
    }

    public StructureManager getStructureManager() {
        return this.server.getStructureManager();
    }

    public <T extends ParticleOptions> int sendParticles(T particleOptions, double d, double e, double f, int i, double g, double h, double j, double k) {
        ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(particleOptions, false, d, e, f, (float)g, (float)h, (float)j, (float)k, i);
        int l = 0;
        for (int m = 0; m < this.players.size(); ++m) {
            ServerPlayer serverPlayer = this.players.get(m);
            if (!this.sendParticles(serverPlayer, false, d, e, f, (Packet<?>)clientboundLevelParticlesPacket)) continue;
            ++l;
        }
        return l;
    }

    public <T extends ParticleOptions> boolean sendParticles(ServerPlayer serverPlayer, T particleOptions, boolean bl, double d, double e, double f, int i, double g, double h, double j, double k) {
        ClientboundLevelParticlesPacket packet = new ClientboundLevelParticlesPacket(particleOptions, bl, d, e, f, (float)g, (float)h, (float)j, (float)k, i);
        return this.sendParticles(serverPlayer, bl, d, e, f, (Packet<?>)packet);
    }

    private boolean sendParticles(ServerPlayer serverPlayer, boolean bl, double d, double e, double f, Packet<?> packet) {
        if (serverPlayer.getLevel() != this) {
            return false;
        }
        BlockPos blockPos = serverPlayer.blockPosition();
        if (blockPos.closerThan((Position)new Vec3(d, e, f), bl ? 512.0 : 32.0)) {
            serverPlayer.connection.send(packet);
            return true;
        }
        return false;
    }

    @Nullable
    public Entity getEntity(int i) {
        return (Entity)this.entitiesById.get(i);
    }

    @Nullable
    public Entity getEntity(UUID uUID) {
        return this.entitiesByUuid.get(uUID);
    }

    @Nullable
    public BlockPos findNearestMapFeature(StructureFeature<?> structureFeature, BlockPos blockPos, int i, boolean bl) {
        if (!this.server.getWorldData().worldGenSettings().generateFeatures()) {
            return null;
        }
        return this.getChunkSource().getGenerator().findNearestMapFeature(this, structureFeature, blockPos, i, bl);
    }

    @Nullable
    public BlockPos findNearestBiome(Biome biome, BlockPos blockPos, int i, int j) {
        return this.getChunkSource().getGenerator().getBiomeSource().findBiomeHorizontal(blockPos.getX(), blockPos.getY(), blockPos.getZ(), i, j, biome2 -> biome2 == biome, this.random, true);
    }

    public RecipeManager getRecipeManager() {
        return this.server.getRecipeManager();
    }

    public TagContainer getTagManager() {
        return this.server.getTags();
    }

    public boolean noSave() {
        return this.noSave;
    }

    public RegistryAccess registryAccess() {
        return this.server.registryAccess();
    }

    public DimensionDataStorage getDataStorage() {
        return this.getChunkSource().getDataStorage();
    }

    @Nullable
    public MapItemSavedData getMapData(String string) {
        return (MapItemSavedData)this.getServer().overworld().getDataStorage().get(() -> new MapItemSavedData(string), string);
    }

    public void setMapData(MapItemSavedData mapItemSavedData) {
        this.getServer().overworld().getDataStorage().set((SavedData)mapItemSavedData);
    }

    public int getFreeMapId() {
        return ((MapIndex)this.getServer().overworld().getDataStorage().computeIfAbsent(MapIndex::new, "idcounts")).getFreeAuxValueForMap();
    }

    public void setDefaultSpawnPos(BlockPos blockPos, float f) {
        ChunkPos chunkPos = new ChunkPos(new BlockPos(this.levelData.getXSpawn(), 0, this.levelData.getZSpawn()));
        this.levelData.setSpawn(blockPos, f);
        this.getChunkSource().removeRegionTicket(TicketType.START, chunkPos, 11, (Object)Unit.INSTANCE);
        this.getChunkSource().addRegionTicket(TicketType.START, new ChunkPos(blockPos), 11, (Object)Unit.INSTANCE);
        this.getServer().getPlayerList().broadcastAll((Packet)new ClientboundSetDefaultSpawnPositionPacket(blockPos, f));
    }

    public BlockPos getSharedSpawnPos() {
        BlockPos blockPos = new BlockPos(this.levelData.getXSpawn(), this.levelData.getYSpawn(), this.levelData.getZSpawn());
        if (!this.getWorldBorder().isWithinBounds(blockPos)) {
            blockPos = this.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, new BlockPos(this.getWorldBorder().getCenterX(), 0.0, this.getWorldBorder().getCenterZ()));
        }
        return blockPos;
    }

    public float getSharedSpawnAngle() {
        return this.levelData.getSpawnAngle();
    }

    public LongSet getForcedChunks() {
        ForcedChunksSavedData forcedChunksSavedData = (ForcedChunksSavedData)this.getDataStorage().get(ForcedChunksSavedData::new, "chunks");
        return forcedChunksSavedData != null ? LongSets.unmodifiable((LongSet)forcedChunksSavedData.getChunks()) : LongSets.EMPTY_SET;
    }

    public boolean setChunkForced(int i, int j, boolean bl) {
        boolean bl2;
        ForcedChunksSavedData forcedChunksSavedData = (ForcedChunksSavedData)this.getDataStorage().computeIfAbsent(ForcedChunksSavedData::new, "chunks");
        ChunkPos chunkPos = new ChunkPos(i, j);
        long l = chunkPos.toLong();
        if (bl) {
            bl2 = forcedChunksSavedData.getChunks().add(l);
            if (bl2) {
                this.getChunk(i, j);
            }
        } else {
            bl2 = forcedChunksSavedData.getChunks().remove(l);
        }
        forcedChunksSavedData.setDirty(bl2);
        if (bl2) {
            this.getChunkSource().updateChunkForced(chunkPos, bl);
        }
        return bl2;
    }

    public List<ServerPlayer> players() {
        return this.players;
    }

    public void onBlockStateChange(BlockPos blockPos, BlockState blockState, BlockState blockState2) {
        Optional optional2;
        Optional optional = PoiType.forState((BlockState)blockState);
        if (Objects.equals(optional, optional2 = PoiType.forState((BlockState)blockState2))) {
            return;
        }
        BlockPos blockPos2 = blockPos.immutable();
        optional.ifPresent(poiType -> this.getServer().execute(() -> {
            this.getPoiManager().remove(blockPos2);
            DebugPackets.sendPoiRemovedPacket((ServerLevel)this, (BlockPos)blockPos2);
        }));
        optional2.ifPresent(poiType -> this.getServer().execute(() -> {
            this.getPoiManager().add(blockPos2, poiType);
            DebugPackets.sendPoiAddedPacket((ServerLevel)this, (BlockPos)blockPos2);
        }));
    }

    public PoiManager getPoiManager() {
        return this.getChunkSource().getPoiManager();
    }

    public boolean isVillage(BlockPos blockPos) {
        return this.isCloseToVillage(blockPos, 1);
    }

    public boolean isVillage(SectionPos sectionPos) {
        return this.isVillage(sectionPos.center());
    }

    public boolean isCloseToVillage(BlockPos blockPos, int i) {
        if (i > 6) {
            return false;
        }
        return this.sectionsToVillage(SectionPos.of((BlockPos)blockPos)) <= i;
    }

    public int sectionsToVillage(SectionPos sectionPos) {
        return this.getPoiManager().sectionsToVillage(sectionPos);
    }

    public Raids getRaids() {
        return this.raids;
    }

    @Nullable
    public Raid getRaidAt(BlockPos blockPos) {
        return this.raids.getNearbyRaid(blockPos, 9216);
    }

    public boolean isRaided(BlockPos blockPos) {
        return this.getRaidAt(blockPos) != null;
    }

    public void onReputationEvent(ReputationEventType reputationEventType, Entity entity, ReputationEventHandler reputationEventHandler) {
        reputationEventHandler.onReputationEventFrom(reputationEventType, entity);
    }

    public void saveDebugReport(Path path) throws IOException {
        Object entry2;
        Object spawnState;
        ChunkMap chunkMap = this.getChunkSource().chunkMap;
        try (BufferedWriter writer = Files.newBufferedWriter(path.resolve("stats.txt"), new OpenOption[0]);){
            writer.write(String.format("spawning_chunks: %d\n", chunkMap.getDistanceManager().getNaturalSpawnChunkCount()));
            spawnState = this.getChunkSource().getLastSpawnState();
            if (spawnState != null) {
                for (Object entry2 : spawnState.getMobCategoryCounts().object2IntEntrySet()) {
                    writer.write(String.format("spawn_count.%s: %d\n", ((MobCategory)entry2.getKey()).getName(), entry2.getIntValue()));
                }
            }
            writer.write(String.format("entities: %d\n", this.entitiesById.size()));
            writer.write(String.format("block_entities: %d\n", this.blockEntityList.size()));
            writer.write(String.format("block_ticks: %d\n", this.getBlockTicks().size()));
            writer.write(String.format("fluid_ticks: %d\n", this.getLiquidTicks().size()));
            writer.write("distance_manager: " + chunkMap.getDistanceManager().getDebugStatus() + "\n");
            writer.write(String.format("pending_tasks: %d\n", this.getChunkSource().getPendingTasksCount()));
        }
        CrashReport crashReport = new CrashReport("Level dump", (Throwable)new Exception("dummy"));
        this.fillReportDetails(crashReport);
        BufferedWriter writer2 = Files.newBufferedWriter(path.resolve("example_crash.txt"), new OpenOption[0]);
        spawnState = null;
        try {
            writer2.write(crashReport.getFriendlyReport());
        }
        catch (Throwable throwable) {
            spawnState = throwable;
            throw throwable;
        }
        finally {
            if (writer2 != null) {
                if (spawnState != null) {
                    try {
                        ((Writer)writer2).close();
                    }
                    catch (Throwable throwable) {
                        ((Throwable)spawnState).addSuppressed(throwable);
                    }
                } else {
                    ((Writer)writer2).close();
                }
            }
        }
        Path path2 = path.resolve("chunks.csv");
        BufferedWriter writer3 = Files.newBufferedWriter(path2, new OpenOption[0]);
        Object object = null;
        try {
            chunkMap.dumpChunks((Writer)writer3);
        }
        catch (Throwable entry2) {
            object = entry2;
            throw entry2;
        }
        finally {
            if (writer3 != null) {
                if (object != null) {
                    try {
                        ((Writer)writer3).close();
                    }
                    catch (Throwable entry2) {
                        ((Throwable)object).addSuppressed(entry2);
                    }
                } else {
                    ((Writer)writer3).close();
                }
            }
        }
        Path path3 = path.resolve("entities.csv");
        BufferedWriter writer4 = Files.newBufferedWriter(path3, new OpenOption[0]);
        entry2 = null;
        try {
            ServerLevel.dumpEntities(writer4, (Iterable<Entity>)this.entitiesById.values());
        }
        catch (Throwable throwable) {
            entry2 = throwable;
            throw throwable;
        }
        finally {
            if (writer4 != null) {
                if (entry2 != null) {
                    try {
                        ((Writer)writer4).close();
                    }
                    catch (Throwable throwable) {
                        ((Throwable)entry2).addSuppressed(throwable);
                    }
                } else {
                    ((Writer)writer4).close();
                }
            }
        }
        Path path4 = path.resolve("block_entities.csv");
        try (BufferedWriter writer5 = Files.newBufferedWriter(path4, new OpenOption[0]);){
            this.dumpBlockEntities(writer5);
        }
    }

    private static void dumpEntities(Writer writer, Iterable<Entity> iterable) throws IOException {
        CsvOutput csvOutput = CsvOutput.builder().addColumn("x").addColumn("y").addColumn("z").addColumn("uuid").addColumn("type").addColumn("alive").addColumn("display_name").addColumn("custom_name").build(writer);
        for (Entity entity : iterable) {
            Component component = entity.getCustomName();
            Component component2 = entity.getDisplayName();
            csvOutput.writeRow(new Object[]{entity.getX(), entity.getY(), entity.getZ(), entity.getUUID(), Registry.ENTITY_TYPE.getKey((Object)entity.getType()), entity.isAlive(), component2.getString(), component != null ? component.getString() : null});
        }
    }

    private void dumpBlockEntities(Writer writer) throws IOException {
        CsvOutput csvOutput = CsvOutput.builder().addColumn("x").addColumn("y").addColumn("z").addColumn("type").build(writer);
        for (BlockEntity blockEntity : this.blockEntityList) {
            BlockPos blockPos = blockEntity.getBlockPos();
            csvOutput.writeRow(new Object[]{blockPos.getX(), blockPos.getY(), blockPos.getZ(), Registry.BLOCK_ENTITY_TYPE.getKey((Object)blockEntity.getType())});
        }
    }

    @VisibleForTesting
    public void clearBlockEvents(BoundingBox boundingBox) {
        this.blockEvents.removeIf(blockEventData -> boundingBox.isInside((Vec3i)blockEventData.getPos()));
    }

    public void blockUpdated(BlockPos blockPos, Block block) {
        if (!this.isDebug()) {
            this.updateNeighborsAt(blockPos, block);
        }
    }

    @Environment(value=EnvType.CLIENT)
    public float getShade(Direction direction, boolean bl) {
        return 1.0f;
    }

    public Iterable<Entity> getAllEntities() {
        return Iterables.unmodifiableIterable((Iterable)this.entitiesById.values());
    }

    public String toString() {
        return "ServerLevel[" + this.serverLevelData.getLevelName() + "]";
    }

    public boolean isFlat() {
        return this.server.getWorldData().worldGenSettings().isFlatWorld();
    }

    public long getSeed() {
        return this.server.getWorldData().worldGenSettings().seed();
    }

    @Nullable
    public EndDragonFight dragonFight() {
        return this.dragonFight;
    }

    public Stream<? extends StructureStart<?>> startsForFeature(SectionPos sectionPos, StructureFeature<?> structureFeature) {
        return this.structureFeatureManager().startsForFeature(sectionPos, structureFeature);
    }

    public ServerLevel getLevel() {
        return this;
    }

    @VisibleForTesting
    public String getWatchdogStats() {
        return String.format("players: %s, entities: %d [%s], block_entities: %d [%s], block_ticks: %d, fluid_ticks: %d, chunk_source: %s", this.players.size(), this.entitiesById.size(), ServerLevel.getTypeCount(this.entitiesById.values(), entity -> Registry.ENTITY_TYPE.getKey((Object)entity.getType())), this.tickableBlockEntities.size(), ServerLevel.getTypeCount(this.tickableBlockEntities, blockEntity -> Registry.BLOCK_ENTITY_TYPE.getKey((Object)blockEntity.getType())), this.getBlockTicks().size(), this.getLiquidTicks().size(), this.gatherChunkSourceStats());
    }

    private static <T> String getTypeCount(Collection<T> collection, Function<T, ResourceLocation> function) {
        try {
            Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
            for (T object : collection) {
                ResourceLocation resourceLocation = function.apply(object);
                object2IntOpenHashMap.addTo((Object)resourceLocation, 1);
            }
            return object2IntOpenHashMap.object2IntEntrySet().stream().sorted(Comparator.comparing(Object2IntMap.Entry::getIntValue).reversed()).limit(5L).map(entry -> entry.getKey() + ":" + entry.getIntValue()).collect(Collectors.joining(","));
        }
        catch (Exception exception) {
            return "";
        }
    }

    public static void makeObsidianPlatform(ServerLevel serverLevel) {
        BlockPos blockPos2 = END_SPAWN_POINT;
        int i = blockPos2.getX();
        int j = blockPos2.getY() - 2;
        int k = blockPos2.getZ();
        BlockPos.betweenClosed((int)(i - 2), (int)(j + 1), (int)(k - 2), (int)(i + 2), (int)(j + 3), (int)(k + 2)).forEach(blockPos -> serverLevel.setBlockAndUpdate((BlockPos)blockPos, Blocks.AIR.defaultBlockState()));
        BlockPos.betweenClosed((int)(i - 2), (int)j, (int)(k - 2), (int)(i + 2), (int)j, (int)(k + 2)).forEach(blockPos -> serverLevel.setBlockAndUpdate((BlockPos)blockPos, Blocks.OBSIDIAN.defaultBlockState()));
    }

    public /* synthetic */ Scoreboard getScoreboard() {
        return this.getScoreboard();
    }

    public /* synthetic */ ChunkSource getChunkSource() {
        return this.getChunkSource();
    }

    public /* synthetic */ TickList getLiquidTicks() {
        return this.getLiquidTicks();
    }

    public /* synthetic */ TickList getBlockTicks() {
        return this.getBlockTicks();
    }
}
