/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  net.minecraft.Util
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.SectionPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.util.datafix.DataFixTypes
 *  net.minecraft.world.entity.ai.village.poi.PoiManager$DistanceTracker
 *  net.minecraft.world.entity.ai.village.poi.PoiManager$Occupancy
 *  net.minecraft.world.entity.ai.village.poi.PoiRecord
 *  net.minecraft.world.entity.ai.village.poi.PoiSection
 *  net.minecraft.world.entity.ai.village.poi.PoiType
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.ChunkStatus
 *  net.minecraft.world.level.chunk.LevelChunkSection
 *  net.minecraft.world.level.chunk.storage.SectionStorage
 */
package net.minecraft.world.entity.ai.village.poi;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiSection;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.storage.SectionStorage;

public class PoiManager
extends SectionStorage<PoiSection> {
    private final DistanceTracker distanceTracker;
    private final LongSet loadedChunks = new LongOpenHashSet();

    public PoiManager(File file, DataFixer dataFixer, boolean bl) {
        super(file, PoiSection::codec, PoiSection::new, dataFixer, DataFixTypes.POI_CHUNK, bl);
        this.distanceTracker = new DistanceTracker(this);
    }

    public void add(BlockPos blockPos, PoiType poiType) {
        ((PoiSection)this.getOrCreate(SectionPos.of((BlockPos)blockPos).asLong())).add(blockPos, poiType);
    }

    public void remove(BlockPos blockPos) {
        ((PoiSection)this.getOrCreate(SectionPos.of((BlockPos)blockPos).asLong())).remove(blockPos);
    }

    public long getCountInRange(Predicate<PoiType> predicate, BlockPos blockPos, int i, Occupancy occupancy) {
        return this.getInRange(predicate, blockPos, i, occupancy).count();
    }

    public boolean existsAtPosition(PoiType poiType, BlockPos blockPos) {
        Optional optional = ((PoiSection)this.getOrCreate(SectionPos.of((BlockPos)blockPos).asLong())).getType(blockPos);
        return optional.isPresent() && ((PoiType)optional.get()).equals(poiType);
    }

    public Stream<PoiRecord> getInSquare(Predicate<PoiType> predicate, BlockPos blockPos, int i, Occupancy occupancy) {
        int j = Math.floorDiv(i, 16) + 1;
        return ChunkPos.rangeClosed((ChunkPos)new ChunkPos(blockPos), (int)j).flatMap(chunkPos -> this.getInChunk(predicate, (ChunkPos)chunkPos, occupancy)).filter(poiRecord -> {
            BlockPos blockPos2 = poiRecord.getPos();
            return Math.abs(blockPos2.getX() - blockPos.getX()) <= i && Math.abs(blockPos2.getZ() - blockPos.getZ()) <= i;
        });
    }

    public Stream<PoiRecord> getInRange(Predicate<PoiType> predicate, BlockPos blockPos, int i, Occupancy occupancy) {
        int j = i * i;
        return this.getInSquare(predicate, blockPos, i, occupancy).filter(poiRecord -> poiRecord.getPos().distSqr((Vec3i)blockPos) <= (double)j);
    }

    public Stream<PoiRecord> getInChunk(Predicate<PoiType> predicate, ChunkPos chunkPos, Occupancy occupancy) {
        return IntStream.range(0, 16).boxed().map(integer -> this.getOrLoad(SectionPos.of((ChunkPos)chunkPos, (int)integer).asLong())).filter(Optional::isPresent).flatMap(optional -> ((PoiSection)optional.get()).getRecords(predicate, occupancy));
    }

    public Stream<BlockPos> findAll(Predicate<PoiType> predicate, Predicate<BlockPos> predicate2, BlockPos blockPos, int i, Occupancy occupancy) {
        return this.getInRange(predicate, blockPos, i, occupancy).map(PoiRecord::getPos).filter(predicate2);
    }

    public Stream<BlockPos> findAllClosestFirst(Predicate<PoiType> predicate, Predicate<BlockPos> predicate2, BlockPos blockPos, int i, Occupancy occupancy) {
        return this.findAll(predicate, predicate2, blockPos, i, occupancy).sorted(Comparator.comparingDouble(blockPos2 -> blockPos2.distSqr((Vec3i)blockPos)));
    }

    public Optional<BlockPos> find(Predicate<PoiType> predicate, Predicate<BlockPos> predicate2, BlockPos blockPos, int i, Occupancy occupancy) {
        return this.findAll(predicate, predicate2, blockPos, i, occupancy).findFirst();
    }

    public Optional<BlockPos> findClosest(Predicate<PoiType> predicate, BlockPos blockPos, int i, Occupancy occupancy) {
        return this.getInRange(predicate, blockPos, i, occupancy).map(PoiRecord::getPos).min(Comparator.comparingDouble(blockPos2 -> blockPos2.distSqr((Vec3i)blockPos)));
    }

    public Optional<BlockPos> take(Predicate<PoiType> predicate, Predicate<BlockPos> predicate2, BlockPos blockPos, int i) {
        return this.getInRange(predicate, blockPos, i, Occupancy.HAS_SPACE).filter(poiRecord -> predicate2.test(poiRecord.getPos())).findFirst().map(poiRecord -> {
            poiRecord.acquireTicket();
            return poiRecord.getPos();
        });
    }

    public Optional<BlockPos> getRandom(Predicate<PoiType> predicate, Predicate<BlockPos> predicate2, Occupancy occupancy, BlockPos blockPos, int i, Random random) {
        List list = this.getInRange(predicate, blockPos, i, occupancy).collect(Collectors.toList());
        Collections.shuffle(list, random);
        return list.stream().filter(poiRecord -> predicate2.test(poiRecord.getPos())).findFirst().map(PoiRecord::getPos);
    }

    public boolean release(BlockPos blockPos) {
        return ((PoiSection)this.getOrCreate(SectionPos.of((BlockPos)blockPos).asLong())).release(blockPos);
    }

    public boolean exists(BlockPos blockPos, Predicate<PoiType> predicate) {
        return this.getOrLoad(SectionPos.of((BlockPos)blockPos).asLong()).map(poiSection -> poiSection.exists(blockPos, predicate)).orElse(false);
    }

    public Optional<PoiType> getType(BlockPos blockPos) {
        PoiSection poiSection = (PoiSection)this.getOrCreate(SectionPos.of((BlockPos)blockPos).asLong());
        return poiSection.getType(blockPos);
    }

    public int sectionsToVillage(SectionPos sectionPos) {
        this.distanceTracker.runAllUpdates();
        return this.distanceTracker.getLevel(sectionPos.asLong());
    }

    private boolean isVillageCenter(long l) {
        Optional optional = this.get(l);
        if (optional == null) {
            return false;
        }
        return optional.map(poiSection -> poiSection.getRecords(PoiType.ALL, Occupancy.IS_OCCUPIED).count() > 0L).orElse(false);
    }

    public void tick(BooleanSupplier booleanSupplier) {
        super.tick(booleanSupplier);
        this.distanceTracker.runAllUpdates();
    }

    protected void setDirty(long l) {
        super.setDirty(l);
        this.distanceTracker.update(l, this.distanceTracker.getLevelFromSource(l), false);
    }

    protected void onSectionLoad(long l) {
        this.distanceTracker.update(l, this.distanceTracker.getLevelFromSource(l), false);
    }

    public void checkConsistencyWithBlocks(ChunkPos chunkPos, LevelChunkSection levelChunkSection) {
        SectionPos sectionPos = SectionPos.of((ChunkPos)chunkPos, (int)(levelChunkSection.bottomBlockY() >> 4));
        Util.ifElse((Optional)this.getOrLoad(sectionPos.asLong()), poiSection -> poiSection.refresh(biConsumer -> {
            if (PoiManager.mayHavePoi(levelChunkSection)) {
                this.updateFromSection(levelChunkSection, sectionPos, (BiConsumer<BlockPos, PoiType>)biConsumer);
            }
        }), () -> {
            if (PoiManager.mayHavePoi(levelChunkSection)) {
                PoiSection poiSection = (PoiSection)this.getOrCreate(sectionPos.asLong());
                this.updateFromSection(levelChunkSection, sectionPos, (arg_0, arg_1) -> ((PoiSection)poiSection).add(arg_0, arg_1));
            }
        });
    }

    private static boolean mayHavePoi(LevelChunkSection levelChunkSection) {
        return levelChunkSection.maybeHas(PoiType.ALL_STATES::contains);
    }

    private void updateFromSection(LevelChunkSection levelChunkSection, SectionPos sectionPos, BiConsumer<BlockPos, PoiType> biConsumer) {
        sectionPos.blocksInside().forEach(blockPos -> {
            BlockState blockState = levelChunkSection.getBlockState(SectionPos.sectionRelative((int)blockPos.getX()), SectionPos.sectionRelative((int)blockPos.getY()), SectionPos.sectionRelative((int)blockPos.getZ()));
            PoiType.forState((BlockState)blockState).ifPresent(poiType -> biConsumer.accept((BlockPos)blockPos, (PoiType)poiType));
        });
    }

    public void ensureLoadedAndValid(LevelReader levelReader, BlockPos blockPos, int i) {
        SectionPos.aroundChunk((ChunkPos)new ChunkPos(blockPos), (int)Math.floorDiv(i, 16)).map(sectionPos -> Pair.of((Object)sectionPos, (Object)this.getOrLoad(sectionPos.asLong()))).filter(pair -> ((Optional)pair.getSecond()).map(PoiSection::isValid).orElse(false) == false).map(pair -> ((SectionPos)pair.getFirst()).chunk()).filter(chunkPos -> this.loadedChunks.add(chunkPos.toLong())).forEach(chunkPos -> levelReader.getChunk(chunkPos.x, chunkPos.z, ChunkStatus.EMPTY));
    }

    static /* synthetic */ boolean method_19110(PoiManager poiManager, long l) {
        return poiManager.isVillageCenter(l);
    }
}
