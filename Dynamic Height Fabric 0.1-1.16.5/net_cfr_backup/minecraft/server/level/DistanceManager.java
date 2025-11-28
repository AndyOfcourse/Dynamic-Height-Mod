/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Sets
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  it.unimi.dsi.fastutil.objects.ObjectIterator
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  net.minecraft.core.SectionPos
 *  net.minecraft.server.level.ChunkHolder
 *  net.minecraft.server.level.ChunkMap
 *  net.minecraft.server.level.ChunkTaskPriorityQueueSorter
 *  net.minecraft.server.level.ChunkTaskPriorityQueueSorter$Message
 *  net.minecraft.server.level.ChunkTaskPriorityQueueSorter$Release
 *  net.minecraft.server.level.DistanceManager$ChunkTicketTracker
 *  net.minecraft.server.level.DistanceManager$FixedPlayerDistanceChunkTracker
 *  net.minecraft.server.level.DistanceManager$PlayerTicketTracker
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.level.Ticket
 *  net.minecraft.server.level.TicketType
 *  net.minecraft.util.SortedArraySet
 *  net.minecraft.util.thread.ProcessorHandle
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.chunk.ChunkStatus
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.server.level;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ChunkTaskPriorityQueueSorter;
import net.minecraft.server.level.DistanceManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.Ticket;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.SortedArraySet;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public abstract class DistanceManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int PLAYER_TICKET_LEVEL = 33 + ChunkStatus.getDistance((ChunkStatus)ChunkStatus.FULL) - 2;
    private final Long2ObjectMap<ObjectSet<ServerPlayer>> playersPerChunk = new Long2ObjectOpenHashMap();
    private final Long2ObjectOpenHashMap<SortedArraySet<Ticket<?>>> tickets = new Long2ObjectOpenHashMap();
    private final ChunkTicketTracker ticketTracker = new ChunkTicketTracker(this);
    private final FixedPlayerDistanceChunkTracker naturalSpawnChunkCounter = new FixedPlayerDistanceChunkTracker(this, 8);
    private final PlayerTicketTracker playerTicketManager = new PlayerTicketTracker(this, 33);
    private final Set<ChunkHolder> chunksToUpdateFutures = Sets.newHashSet();
    private final ChunkTaskPriorityQueueSorter ticketThrottler;
    private final ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> ticketThrottlerInput;
    private final ProcessorHandle<ChunkTaskPriorityQueueSorter.Release> ticketThrottlerReleaser;
    private final LongSet ticketsToRelease = new LongOpenHashSet();
    private final Executor mainThreadExecutor;
    private long ticketTickCounter;

    protected DistanceManager(Executor executor, Executor executor2) {
        ChunkTaskPriorityQueueSorter chunkTaskPriorityQueueSorter;
        ProcessorHandle processorHandle = ProcessorHandle.of((String)"player ticket throttler", executor2::execute);
        this.ticketThrottler = chunkTaskPriorityQueueSorter = new ChunkTaskPriorityQueueSorter((List)ImmutableList.of((Object)processorHandle), executor, 4);
        this.ticketThrottlerInput = chunkTaskPriorityQueueSorter.getProcessor(processorHandle, true);
        this.ticketThrottlerReleaser = chunkTaskPriorityQueueSorter.getReleaseProcessor(processorHandle);
        this.mainThreadExecutor = executor2;
    }

    protected void purgeStaleTickets() {
        ++this.ticketTickCounter;
        ObjectIterator objectIterator = this.tickets.long2ObjectEntrySet().fastIterator();
        while (objectIterator.hasNext()) {
            Long2ObjectMap.Entry entry = (Long2ObjectMap.Entry)objectIterator.next();
            if (((SortedArraySet)entry.getValue()).removeIf(ticket -> ticket.timedOut(this.ticketTickCounter))) {
                this.ticketTracker.update(entry.getLongKey(), DistanceManager.getTicketLevelAt((SortedArraySet)entry.getValue()), false);
            }
            if (!((SortedArraySet)entry.getValue()).isEmpty()) continue;
            objectIterator.remove();
        }
    }

    private static int getTicketLevelAt(SortedArraySet<Ticket<?>> sortedArraySet) {
        return !sortedArraySet.isEmpty() ? ((Ticket)sortedArraySet.first()).getTicketLevel() : ChunkMap.MAX_CHUNK_DISTANCE + 1;
    }

    protected abstract boolean isChunkToRemove(long var1);

    @Nullable
    protected abstract ChunkHolder getChunk(long var1);

    @Nullable
    protected abstract ChunkHolder updateChunkScheduling(long var1, int var3, @Nullable ChunkHolder var4, int var5);

    public boolean runAllUpdates(ChunkMap chunkMap) {
        boolean bl;
        this.naturalSpawnChunkCounter.runAllUpdates();
        this.playerTicketManager.runAllUpdates();
        int i = Integer.MAX_VALUE - this.ticketTracker.runDistanceUpdates(Integer.MAX_VALUE);
        boolean bl2 = bl = i != 0;
        if (bl) {
            // empty if block
        }
        if (!this.chunksToUpdateFutures.isEmpty()) {
            this.chunksToUpdateFutures.forEach(chunkHolder -> chunkHolder.updateFutures(chunkMap));
            this.chunksToUpdateFutures.clear();
            return true;
        }
        if (!this.ticketsToRelease.isEmpty()) {
            LongIterator longIterator = this.ticketsToRelease.iterator();
            while (longIterator.hasNext()) {
                long l = longIterator.nextLong();
                if (!this.getTickets(l).stream().anyMatch(ticket -> ticket.getType() == TicketType.PLAYER)) continue;
                ChunkHolder chunkHolder2 = chunkMap.getUpdatingChunkIfPresent(l);
                if (chunkHolder2 == null) {
                    throw new IllegalStateException();
                }
                CompletableFuture completableFuture = chunkHolder2.getEntityTickingChunkFuture();
                completableFuture.thenAccept(either -> this.mainThreadExecutor.execute(() -> this.ticketThrottlerReleaser.tell((Object)ChunkTaskPriorityQueueSorter.release(() -> {}, (long)l, (boolean)false))));
            }
            this.ticketsToRelease.clear();
        }
        return bl;
    }

    private void addTicket(long l, Ticket<?> ticket) {
        SortedArraySet<Ticket<?>> sortedArraySet = this.getTickets(l);
        int i = DistanceManager.getTicketLevelAt(sortedArraySet);
        Ticket ticket2 = (Ticket)sortedArraySet.addOrGet(ticket);
        ticket2.setCreatedTick(this.ticketTickCounter);
        if (ticket.getTicketLevel() < i) {
            this.ticketTracker.update(l, ticket.getTicketLevel(), true);
        }
    }

    private void removeTicket(long l, Ticket<?> ticket) {
        SortedArraySet<Ticket<?>> sortedArraySet = this.getTickets(l);
        if (sortedArraySet.remove(ticket)) {
            // empty if block
        }
        if (sortedArraySet.isEmpty()) {
            this.tickets.remove(l);
        }
        this.ticketTracker.update(l, DistanceManager.getTicketLevelAt(sortedArraySet), false);
    }

    public <T> void addTicket(TicketType<T> ticketType, ChunkPos chunkPos, int i, T object) {
        this.addTicket(chunkPos.toLong(), new Ticket(ticketType, i, object));
    }

    public <T> void removeTicket(TicketType<T> ticketType, ChunkPos chunkPos, int i, T object) {
        Ticket ticket = new Ticket(ticketType, i, object);
        this.removeTicket(chunkPos.toLong(), ticket);
    }

    public <T> void addRegionTicket(TicketType<T> ticketType, ChunkPos chunkPos, int i, T object) {
        this.addTicket(chunkPos.toLong(), new Ticket(ticketType, 33 - i, object));
    }

    public <T> void removeRegionTicket(TicketType<T> ticketType, ChunkPos chunkPos, int i, T object) {
        Ticket ticket = new Ticket(ticketType, 33 - i, object);
        this.removeTicket(chunkPos.toLong(), ticket);
    }

    private SortedArraySet<Ticket<?>> getTickets(long l2) {
        return (SortedArraySet)this.tickets.computeIfAbsent(l2, l -> SortedArraySet.create((int)4));
    }

    protected void updateChunkForced(ChunkPos chunkPos, boolean bl) {
        Ticket ticket = new Ticket(TicketType.FORCED, 31, (Object)chunkPos);
        if (bl) {
            this.addTicket(chunkPos.toLong(), ticket);
        } else {
            this.removeTicket(chunkPos.toLong(), ticket);
        }
    }

    public void addPlayer(SectionPos sectionPos, ServerPlayer serverPlayer) {
        long l2 = sectionPos.chunk().toLong();
        ((ObjectSet)this.playersPerChunk.computeIfAbsent(l2, l -> new ObjectOpenHashSet())).add((Object)serverPlayer);
        this.naturalSpawnChunkCounter.update(l2, 0, true);
        this.playerTicketManager.update(l2, 0, true);
    }

    public void removePlayer(SectionPos sectionPos, ServerPlayer serverPlayer) {
        long l = sectionPos.chunk().toLong();
        ObjectSet objectSet = (ObjectSet)this.playersPerChunk.get(l);
        objectSet.remove((Object)serverPlayer);
        if (objectSet.isEmpty()) {
            this.playersPerChunk.remove(l);
            this.naturalSpawnChunkCounter.update(l, Integer.MAX_VALUE, false);
            this.playerTicketManager.update(l, Integer.MAX_VALUE, false);
        }
    }

    protected String getTicketDebugString(long l) {
        SortedArraySet sortedArraySet = (SortedArraySet)this.tickets.get(l);
        String string = sortedArraySet == null || sortedArraySet.isEmpty() ? "no_ticket" : ((Ticket)sortedArraySet.first()).toString();
        return string;
    }

    protected void updatePlayerTickets(int i) {
        this.playerTicketManager.updateViewDistance(i);
    }

    public int getNaturalSpawnChunkCount() {
        this.naturalSpawnChunkCounter.runAllUpdates();
        return this.naturalSpawnChunkCounter.chunks.size();
    }

    public boolean hasPlayersNearby(long l) {
        this.naturalSpawnChunkCounter.runAllUpdates();
        return this.naturalSpawnChunkCounter.chunks.containsKey(l);
    }

    public String getDebugStatus() {
        return this.ticketThrottler.getDebugStatus();
    }

    static /* synthetic */ Long2ObjectMap method_17643(DistanceManager distanceManager) {
        return distanceManager.playersPerChunk;
    }

    static /* synthetic */ int method_17649() {
        return PLAYER_TICKET_LEVEL;
    }

    static /* synthetic */ ProcessorHandle method_17648(DistanceManager distanceManager) {
        return distanceManager.ticketThrottlerInput;
    }

    static /* synthetic */ ProcessorHandle method_17650(DistanceManager distanceManager) {
        return distanceManager.ticketThrottlerReleaser;
    }

    static /* synthetic */ ChunkTaskPriorityQueueSorter method_17651(DistanceManager distanceManager) {
        return distanceManager.ticketThrottler;
    }

    static /* synthetic */ Executor method_17653(DistanceManager distanceManager) {
        return distanceManager.mainThreadExecutor;
    }

    static /* synthetic */ void method_17644(DistanceManager distanceManager, long l, Ticket ticket) {
        distanceManager.removeTicket(l, ticket);
    }

    static /* synthetic */ void method_17647(DistanceManager distanceManager, long l, Ticket ticket) {
        distanceManager.addTicket(l, ticket);
    }

    static /* synthetic */ LongSet method_17654(DistanceManager distanceManager) {
        return distanceManager.ticketsToRelease;
    }

    static /* synthetic */ Long2ObjectOpenHashMap method_18744(DistanceManager distanceManager) {
        return distanceManager.tickets;
    }

    static /* synthetic */ Set method_18745(DistanceManager distanceManager) {
        return distanceManager.chunksToUpdateFutures;
    }
}
