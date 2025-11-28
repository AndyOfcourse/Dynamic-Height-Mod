/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterators
 *  com.google.common.collect.Lists
 *  com.google.common.util.concurrent.MoreExecutors
 *  com.mojang.datafixers.DSL$TypeReference
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.types.Type
 *  com.mojang.serialization.DataResult
 *  it.unimi.dsi.fastutil.Hash$Strategy
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.CharPredicate
 *  net.minecraft.DefaultUncaughtExceptionHandler
 *  net.minecraft.ReportedException
 *  net.minecraft.SharedConstants
 *  net.minecraft.Util$1
 *  net.minecraft.Util$7
 *  net.minecraft.Util$IdentityStrategy
 *  net.minecraft.Util$OS
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.Bootstrap
 *  net.minecraft.util.Mth
 *  net.minecraft.util.datafix.DataFixers
 *  net.minecraft.world.level.block.state.properties.Property
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.DataResult;
import it.unimi.dsi.fastutil.Hash;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.CharPredicate;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.Mth;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.world.level.block.state.properties.Property;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class Util {
    private static final AtomicInteger WORKER_COUNT = new AtomicInteger(1);
    private static final ExecutorService BOOTSTRAP_EXECUTOR = Util.makeExecutor("Bootstrap");
    private static final ExecutorService BACKGROUND_EXECUTOR = Util.makeExecutor("Main");
    private static final ExecutorService IO_POOL = Util.makeIoExecutor();
    public static LongSupplier timeSource = System::nanoTime;
    public static final UUID NIL_UUID = new UUID(0L, 0L);
    private static final Logger LOGGER = LogManager.getLogger();

    public static <K, V> Collector<Map.Entry<? extends K, ? extends V>, ?, Map<K, V>> toMap() {
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    public static <T extends Comparable<T>> String getPropertyName(Property<T> property, Object object) {
        return property.getName((Comparable)object);
    }

    public static String makeDescriptionId(String string, @Nullable ResourceLocation resourceLocation) {
        if (resourceLocation == null) {
            return string + ".unregistered_sadface";
        }
        return string + '.' + resourceLocation.getNamespace() + '.' + resourceLocation.getPath().replace('/', '.');
    }

    public static long getMillis() {
        return Util.getNanos() / 1000000L;
    }

    public static long getNanos() {
        return timeSource.getAsLong();
    }

    public static long getEpochMillis() {
        return Instant.now().toEpochMilli();
    }

    private static ExecutorService makeExecutor(String string) {
        int i = Mth.clamp((int)(Runtime.getRuntime().availableProcessors() - 1), (int)1, (int)7);
        Object executorService = i <= 0 ? MoreExecutors.newDirectExecutorService() : new ForkJoinPool(i, forkJoinPool -> {
            1 forkJoinWorkerThread = new /* Unavailable Anonymous Inner Class!! */;
            forkJoinWorkerThread.setName("Worker-" + string + "-" + WORKER_COUNT.getAndIncrement());
            return forkJoinWorkerThread;
        }, Util::onThreadException, true);
        return executorService;
    }

    public static Executor bootstrapExecutor() {
        return BOOTSTRAP_EXECUTOR;
    }

    public static Executor backgroundExecutor() {
        return BACKGROUND_EXECUTOR;
    }

    public static Executor ioPool() {
        return IO_POOL;
    }

    public static void shutdownExecutors() {
        Util.shutdownExecutor(BACKGROUND_EXECUTOR);
        Util.shutdownExecutor(IO_POOL);
    }

    private static void shutdownExecutor(ExecutorService executorService) {
        boolean bl;
        executorService.shutdown();
        try {
            bl = executorService.awaitTermination(3L, TimeUnit.SECONDS);
        }
        catch (InterruptedException interruptedException) {
            bl = false;
        }
        if (!bl) {
            executorService.shutdownNow();
        }
    }

    private static ExecutorService makeIoExecutor() {
        return Executors.newCachedThreadPool(runnable -> {
            Thread thread = new Thread(runnable);
            thread.setName("IO-Worker-" + WORKER_COUNT.getAndIncrement());
            thread.setUncaughtExceptionHandler(Util::onThreadException);
            return thread;
        });
    }

    @Environment(value=EnvType.CLIENT)
    public static <T> CompletableFuture<T> failedFuture(Throwable throwable) {
        CompletableFuture completableFuture = new CompletableFuture();
        completableFuture.completeExceptionally(throwable);
        return completableFuture;
    }

    @Environment(value=EnvType.CLIENT)
    public static void throwAsRuntime(Throwable throwable) {
        throw throwable instanceof RuntimeException ? (RuntimeException)throwable : new RuntimeException(throwable);
    }

    private static void onThreadException(Thread thread, Throwable throwable) {
        Util.pauseInIde(throwable);
        if (throwable instanceof CompletionException) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof ReportedException) {
            Bootstrap.realStdoutPrintln((String)((ReportedException)throwable).getReport().getFriendlyReport());
            System.exit(-1);
        }
        LOGGER.error(String.format("Caught exception in thread %s", thread), throwable);
    }

    @Nullable
    public static Type<?> fetchChoiceType(DSL.TypeReference typeReference, String string) {
        if (!SharedConstants.CHECK_DATA_FIXER_SCHEMA) {
            return null;
        }
        return Util.doFetchChoiceType(typeReference, string);
    }

    @Nullable
    private static Type<?> doFetchChoiceType(DSL.TypeReference typeReference, String string) {
        Type type;
        block2: {
            type = null;
            try {
                type = DataFixers.getDataFixer().getSchema(DataFixUtils.makeKey((int)SharedConstants.getCurrentVersion().getWorldVersion())).getChoiceType(typeReference, string);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                LOGGER.error("No data fixer registered for {}", (Object)string);
                if (!SharedConstants.IS_RUNNING_IN_IDE) break block2;
                throw illegalArgumentException;
            }
        }
        return type;
    }

    public static OS getPlatform() {
        String string = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (string.contains("win")) {
            return OS.WINDOWS;
        }
        if (string.contains("mac")) {
            return OS.OSX;
        }
        if (string.contains("solaris")) {
            return OS.SOLARIS;
        }
        if (string.contains("sunos")) {
            return OS.SOLARIS;
        }
        if (string.contains("linux")) {
            return OS.LINUX;
        }
        if (string.contains("unix")) {
            return OS.LINUX;
        }
        return OS.UNKNOWN;
    }

    public static Stream<String> getVmArguments() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return runtimeMXBean.getInputArguments().stream().filter(string -> string.startsWith("-X"));
    }

    public static <T> T lastOf(List<T> list) {
        return list.get(list.size() - 1);
    }

    public static <T> T findNextInIterable(Iterable<T> iterable, @Nullable T object) {
        Iterator<T> iterator = iterable.iterator();
        T object2 = iterator.next();
        if (object != null) {
            T object3 = object2;
            while (true) {
                if (object3 == object) {
                    if (!iterator.hasNext()) break;
                    return iterator.next();
                }
                if (!iterator.hasNext()) continue;
                object3 = iterator.next();
            }
        }
        return object2;
    }

    public static <T> T findPreviousInIterable(Iterable<T> iterable, @Nullable T object) {
        Iterator<T> iterator = iterable.iterator();
        T object2 = null;
        while (iterator.hasNext()) {
            T object3 = iterator.next();
            if (object3 == object) {
                if (object2 != null) break;
                object2 = (T)(iterator.hasNext() ? Iterators.getLast(iterator) : object);
                break;
            }
            object2 = object3;
        }
        return object2;
    }

    public static <T> T make(Supplier<T> supplier) {
        return supplier.get();
    }

    public static <T> T make(T object, Consumer<T> consumer) {
        consumer.accept(object);
        return object;
    }

    public static <K> Hash.Strategy<K> identityStrategy() {
        return IdentityStrategy.INSTANCE;
    }

    public static <V> CompletableFuture<List<V>> sequence(List<? extends CompletableFuture<? extends V>> list) {
        ArrayList list2 = Lists.newArrayListWithCapacity((int)list.size());
        CompletableFuture[] completableFutures = new CompletableFuture[list.size()];
        CompletableFuture completableFuture = new CompletableFuture();
        list.forEach(completableFuture2 -> {
            int i = list2.size();
            list2.add(null);
            completableFutures[i] = completableFuture2.whenComplete((object, throwable) -> {
                if (throwable != null) {
                    completableFuture.completeExceptionally((Throwable)throwable);
                } else {
                    list2.set(i, object);
                }
            });
        });
        return CompletableFuture.allOf(completableFutures).applyToEither((CompletionStage)completableFuture, void_ -> list2);
    }

    public static <T> Stream<T> toStream(Optional<? extends T> optional) {
        return (Stream)DataFixUtils.orElseGet(optional.map(Stream::of), Stream::empty);
    }

    public static <T> Optional<T> ifElse(Optional<T> optional, Consumer<T> consumer, Runnable runnable) {
        if (optional.isPresent()) {
            consumer.accept(optional.get());
        } else {
            runnable.run();
        }
        return optional;
    }

    public static Runnable name(Runnable runnable, Supplier<String> supplier) {
        return runnable;
    }

    public static <T extends Throwable> T pauseInIde(T throwable) {
        if (SharedConstants.IS_RUNNING_IN_IDE) {
            LOGGER.error("Trying to throw a fatal exception, pausing in IDE", throwable);
            try {
                while (true) {
                    Thread.sleep(1000L);
                    LOGGER.error("paused");
                }
            }
            catch (InterruptedException interruptedException) {
                return throwable;
            }
        }
        return throwable;
    }

    public static String describeError(Throwable throwable) {
        if (throwable.getCause() != null) {
            return Util.describeError(throwable.getCause());
        }
        if (throwable.getMessage() != null) {
            return throwable.getMessage();
        }
        return throwable.toString();
    }

    public static <T> T getRandom(T[] objects, Random random) {
        return objects[random.nextInt(objects.length)];
    }

    public static int getRandom(int[] is, Random random) {
        return is[random.nextInt(is.length)];
    }

    private static BooleanSupplier createRenamer(Path path, Path path2) {
        return new /* Unavailable Anonymous Inner Class!! */;
    }

    private static BooleanSupplier createDeleter(Path path) {
        return new /* Unavailable Anonymous Inner Class!! */;
    }

    private static BooleanSupplier createFileDeletedCheck(Path path) {
        return new /* Unavailable Anonymous Inner Class!! */;
    }

    private static BooleanSupplier createFileCreatedCheck(Path path) {
        return new /* Unavailable Anonymous Inner Class!! */;
    }

    private static boolean executeInSequence(BooleanSupplier ... booleanSuppliers) {
        for (BooleanSupplier booleanSupplier : booleanSuppliers) {
            if (booleanSupplier.getAsBoolean()) continue;
            LOGGER.warn("Failed to execute {}", (Object)booleanSupplier);
            return false;
        }
        return true;
    }

    private static boolean runWithRetries(int i, String string, BooleanSupplier ... booleanSuppliers) {
        for (int j = 0; j < i; ++j) {
            if (Util.executeInSequence(booleanSuppliers)) {
                return true;
            }
            LOGGER.error("Failed to {}, retrying {}/{}", (Object)string, (Object)j, (Object)i);
        }
        LOGGER.error("Failed to {}, aborting, progress might be lost", (Object)string);
        return false;
    }

    public static void safeReplaceFile(File file, File file2, File file3) {
        Util.safeReplaceFile(file.toPath(), file2.toPath(), file3.toPath());
    }

    public static void safeReplaceFile(Path path, Path path2, Path path3) {
        int i = 10;
        if (Files.exists(path, new LinkOption[0]) && !Util.runWithRetries(10, "create backup " + path3, Util.createDeleter(path3), Util.createRenamer(path, path3), Util.createFileCreatedCheck(path3))) {
            return;
        }
        if (!Util.runWithRetries(10, "remove old " + path, Util.createDeleter(path), Util.createFileDeletedCheck(path))) {
            return;
        }
        if (!Util.runWithRetries(10, "replace " + path + " with " + path2, Util.createRenamer(path2, path), Util.createFileCreatedCheck(path))) {
            Util.runWithRetries(10, "restore " + path + " from " + path3, Util.createRenamer(path3, path), Util.createFileCreatedCheck(path));
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static int offsetByCodepoints(String string, int i, int j) {
        int k = string.length();
        if (j >= 0) {
            for (int l = 0; i < k && l < j; ++l) {
                if (!Character.isHighSurrogate(string.charAt(i++)) || i >= k || !Character.isLowSurrogate(string.charAt(i))) continue;
                ++i;
            }
        } else {
            for (int l = j; i > 0 && l < 0; ++l) {
                if (!Character.isLowSurrogate(string.charAt(--i)) || i <= 0 || !Character.isHighSurrogate(string.charAt(i - 1))) continue;
                --i;
            }
        }
        return i;
    }

    public static Consumer<String> prefix(String string, Consumer<String> consumer) {
        return string2 -> consumer.accept(string + string2);
    }

    public static DataResult<int[]> fixedSize(IntStream intStream, int i) {
        int[] is = intStream.limit(i + 1).toArray();
        if (is.length != i) {
            String string = "Input is not a list of " + i + " ints";
            if (is.length >= i) {
                return DataResult.error((String)string, (Object)Arrays.copyOf(is, i));
            }
            return DataResult.error((String)string);
        }
        return DataResult.success((Object)is);
    }

    public static void startTimerHackThread() {
        7 thread = new /* Unavailable Anonymous Inner Class!! */;
        thread.setDaemon(true);
        thread.setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new DefaultUncaughtExceptionHandler(LOGGER));
        thread.start();
    }

    @Environment(value=EnvType.CLIENT)
    public static void copyBetweenDirs(Path path, Path path2, Path path3) throws IOException {
        Path path4 = path.relativize(path3);
        Path path5 = path2.resolve(path4);
        Files.copy(path3, path5, new CopyOption[0]);
    }

    @Environment(value=EnvType.CLIENT)
    public static String sanitizeName(String string, CharPredicate charPredicate) {
        return string.toLowerCase(Locale.ROOT).chars().mapToObj(i -> charPredicate.test((char)i) ? Character.toString((char)i) : "_").collect(Collectors.joining());
    }

    static /* synthetic */ Logger method_667() {
        return LOGGER;
    }
}
