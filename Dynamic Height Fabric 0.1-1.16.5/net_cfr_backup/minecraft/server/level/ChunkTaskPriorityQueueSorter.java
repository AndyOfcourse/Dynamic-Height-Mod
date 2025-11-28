/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Sets
 *  net.minecraft.Util
 *  net.minecraft.server.level.ChunkHolder
 *  net.minecraft.server.level.ChunkHolder$LevelChangeListener
 *  net.minecraft.server.level.ChunkTaskPriorityQueue
 *  net.minecraft.server.level.ChunkTaskPriorityQueueSorter$Message
 *  net.minecraft.server.level.ChunkTaskPriorityQueueSorter$Release
 *  net.minecraft.util.Unit
 *  net.minecraft.util.thread.ProcessorHandle
 *  net.minecraft.util.thread.ProcessorMailbox
 *  net.minecraft.util.thread.StrictQueue
 *  net.minecraft.util.thread.StrictQueue$FixedPriorityQueue
 *  net.minecraft.util.thread.StrictQueue$IntRunnable
 *  net.minecraft.world.level.ChunkPos
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server.level;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkTaskPriorityQueue;
import net.minecraft.server.level.ChunkTaskPriorityQueueSorter;
import net.minecraft.util.Unit;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.util.thread.StrictQueue;
import net.minecraft.world.level.ChunkPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Exception performing whole class analysis ignored.
 */
public class ChunkTaskPriorityQueueSorter
implements AutoCloseable,
ChunkHolder.LevelChangeListener {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<ProcessorHandle<?>, ChunkTaskPriorityQueue<? extends Function<ProcessorHandle<Unit>, ?>>> queues;
    private final Set<ProcessorHandle<?>> sleeping;
    private final ProcessorMailbox<StrictQueue.IntRunnable> mailbox;

    public ChunkTaskPriorityQueueSorter(List<ProcessorHandle<?>> list, Executor executor, int i) {
        this.queues = list.stream().collect(Collectors.toMap(Function.identity(), processorHandle -> new ChunkTaskPriorityQueue(processorHandle.name() + "_queue", i)));
        this.sleeping = Sets.newHashSet(list);
        this.mailbox = new ProcessorMailbox((StrictQueue)new StrictQueue.FixedPriorityQueue(4), executor, "sorter");
    }

    public static Message<Runnable> message(Runnable runnable, long l, IntSupplier intSupplier) {
        return new Message(processorHandle -> () -> {
            runnable.run();
            processorHandle.tell((Object)Unit.INSTANCE);
        }, l, intSupplier, null);
    }

    public static Message<Runnable> message(ChunkHolder chunkHolder, Runnable runnable) {
        return ChunkTaskPriorityQueueSorter.message(runnable, chunkHolder.getPos().toLong(), () -> ((ChunkHolder)chunkHolder).getQueueLevel());
    }

    public static Release release(Runnable runnable, long l, boolean bl) {
        return new Release(runnable, l, bl, null);
    }

    public <T> ProcessorHandle<Message<T>> getProcessor(ProcessorHandle<T> processorHandle, boolean bl) {
        return (ProcessorHandle)this.mailbox.ask(processorHandle2 -> new StrictQueue.IntRunnable(0, () -> {
            this.getQueue(processorHandle);
            processorHandle2.tell((Object)ProcessorHandle.of((String)("chunk priority sorter around " + processorHandle.name()), message -> this.submit(processorHandle, Message.method_17636((Message)message), Message.method_17637((Message)message), Message.method_17638((Message)message), bl)));
        })).join();
    }

    public ProcessorHandle<Release> getReleaseProcessor(ProcessorHandle<Runnable> processorHandle) {
        return (ProcessorHandle)this.mailbox.ask(processorHandle2 -> new StrictQueue.IntRunnable(0, () -> processorHandle2.tell((Object)ProcessorHandle.of((String)("chunk priority sorter around " + processorHandle.name()), release -> this.release(processorHandle, Release.method_17639((Release)release), Release.method_17640((Release)release), Release.method_17641((Release)release)))))).join();
    }

    public void onLevelChange(ChunkPos chunkPos, IntSupplier intSupplier, int i, IntConsumer intConsumer) {
        this.mailbox.tell((Object)new StrictQueue.IntRunnable(0, () -> {
            int j = intSupplier.getAsInt();
            this.queues.values().forEach(chunkTaskPriorityQueue -> chunkTaskPriorityQueue.resortChunkTasks(j, chunkPos, i));
            intConsumer.accept(i);
        }));
    }

    private <T> void release(ProcessorHandle<T> processorHandle, long l, Runnable runnable, boolean bl) {
        this.mailbox.tell((Object)new StrictQueue.IntRunnable(1, () -> {
            ChunkTaskPriorityQueue chunkTaskPriorityQueue = this.getQueue(processorHandle);
            chunkTaskPriorityQueue.release(l, bl);
            if (this.sleeping.remove(processorHandle)) {
                this.pollTask(chunkTaskPriorityQueue, processorHandle);
            }
            runnable.run();
        }));
    }

    private <T> void submit(ProcessorHandle<T> processorHandle, Function<ProcessorHandle<Unit>, T> function, long l, IntSupplier intSupplier, boolean bl) {
        this.mailbox.tell((Object)new StrictQueue.IntRunnable(2, () -> {
            ChunkTaskPriorityQueue chunkTaskPriorityQueue = this.getQueue(processorHandle);
            int i = intSupplier.getAsInt();
            chunkTaskPriorityQueue.submit(Optional.of(function), l, i);
            if (bl) {
                chunkTaskPriorityQueue.submit(Optional.empty(), l, i);
            }
            if (this.sleeping.remove(processorHandle)) {
                this.pollTask(chunkTaskPriorityQueue, processorHandle);
            }
        }));
    }

    private <T> void pollTask(ChunkTaskPriorityQueue<Function<ProcessorHandle<Unit>, T>> chunkTaskPriorityQueue, ProcessorHandle<T> processorHandle) {
        this.mailbox.tell((Object)new StrictQueue.IntRunnable(3, () -> {
            Stream stream = chunkTaskPriorityQueue.pop();
            if (stream == null) {
                this.sleeping.add(processorHandle);
            } else {
                Util.sequence(stream.map(either -> (CompletableFuture)either.map(arg_0 -> ((ProcessorHandle)processorHandle).ask(arg_0), runnable -> {
                    runnable.run();
                    return CompletableFuture.completedFuture(Unit.INSTANCE);
                })).collect(Collectors.toList())).thenAccept(list -> this.pollTask(chunkTaskPriorityQueue, processorHandle));
            }
        }));
    }

    private <T> ChunkTaskPriorityQueue<Function<ProcessorHandle<Unit>, T>> getQueue(ProcessorHandle<T> processorHandle) {
        ChunkTaskPriorityQueue<? extends Function<ProcessorHandle<Unit>, ?>> chunkTaskPriorityQueue = this.queues.get(processorHandle);
        if (chunkTaskPriorityQueue == null) {
            throw (IllegalArgumentException)Util.pauseInIde((Throwable)new IllegalArgumentException("No queue for: " + processorHandle));
        }
        return chunkTaskPriorityQueue;
    }

    @VisibleForTesting
    public String getDebugStatus() {
        return this.queues.entrySet().stream().map(entry -> ((ProcessorHandle)entry.getKey()).name() + "=[" + ((ChunkTaskPriorityQueue)entry.getValue()).getAcquired().stream().map(long_ -> long_ + ":" + new ChunkPos(long_.longValue())).collect(Collectors.joining(",")) + "]").collect(Collectors.joining(",")) + ", s=" + this.sleeping.size();
    }

    @Override
    public void close() {
        this.queues.keySet().forEach(ProcessorHandle::close);
    }
}
