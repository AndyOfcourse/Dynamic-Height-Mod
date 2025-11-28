/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.Brain$MemoryValue
 *  net.minecraft.world.entity.ai.Brain$Provider
 *  net.minecraft.world.entity.ai.behavior.Behavior
 *  net.minecraft.world.entity.ai.behavior.Behavior$Status
 *  net.minecraft.world.entity.ai.memory.ExpirableValue
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.memory.MemoryStatus
 *  net.minecraft.world.entity.ai.sensing.Sensor
 *  net.minecraft.world.entity.ai.sensing.SensorType
 *  net.minecraft.world.entity.schedule.Activity
 *  net.minecraft.world.entity.schedule.Schedule
 *  org.apache.commons.lang3.mutable.MutableObject
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.world.entity.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.ExpirableValue;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class Brain<E extends LivingEntity> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Supplier<Codec<Brain<E>>> codec;
    private final Map<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> memories = Maps.newHashMap();
    private final Map<SensorType<? extends Sensor<? super E>>, Sensor<? super E>> sensors = Maps.newLinkedHashMap();
    private final Map<Integer, Map<Activity, Set<Behavior<? super E>>>> availableBehaviorsByPriority = Maps.newTreeMap();
    private Schedule schedule = Schedule.EMPTY;
    private final Map<Activity, Set<Pair<MemoryModuleType<?>, MemoryStatus>>> activityRequirements = Maps.newHashMap();
    private final Map<Activity, Set<MemoryModuleType<?>>> activityMemoriesToEraseWhenStopped = Maps.newHashMap();
    private Set<Activity> coreActivities = Sets.newHashSet();
    private final Set<Activity> activeActivities = Sets.newHashSet();
    private Activity defaultActivity = Activity.IDLE;
    private long lastScheduleUpdate = -9999L;

    public static <E extends LivingEntity> Provider<E> provider(Collection<? extends MemoryModuleType<?>> collection, Collection<? extends SensorType<? extends Sensor<? super E>>> collection2) {
        return new Provider(collection, collection2, null);
    }

    public static <E extends LivingEntity> Codec<Brain<E>> codec(Collection<? extends MemoryModuleType<?>> collection, Collection<? extends SensorType<? extends Sensor<? super E>>> collection2) {
        MutableObject mutableObject = new MutableObject();
        mutableObject.setValue((Object)new /* Unavailable Anonymous Inner Class!! */.fieldOf("memories").codec());
        return (Codec)mutableObject.getValue();
    }

    public Brain(Collection<? extends MemoryModuleType<?>> collection, Collection<? extends SensorType<? extends Sensor<? super E>>> collection2, ImmutableList<MemoryValue<?>> immutableList, Supplier<Codec<Brain<E>>> supplier) {
        this.codec = supplier;
        for (MemoryModuleType<?> memoryModuleType : collection) {
            this.memories.put(memoryModuleType, Optional.empty());
        }
        for (SensorType sensorType : collection2) {
            this.sensors.put(sensorType, sensorType.create());
        }
        for (Sensor sensor : this.sensors.values()) {
            for (MemoryModuleType memoryModuleType2 : sensor.requires()) {
                this.memories.put(memoryModuleType2, Optional.empty());
            }
        }
        for (MemoryValue memoryValue : immutableList) {
            MemoryValue.method_28327((MemoryValue)memoryValue, (Brain)this);
        }
    }

    public <T> DataResult<T> serializeStart(DynamicOps<T> dynamicOps) {
        return this.codec.get().encodeStart(dynamicOps, (Object)this);
    }

    private Stream<MemoryValue<?>> memories() {
        return this.memories.entrySet().stream().map(entry -> MemoryValue.method_28329((MemoryModuleType)((MemoryModuleType)entry.getKey()), (Optional)((Optional)entry.getValue())));
    }

    public boolean hasMemoryValue(MemoryModuleType<?> memoryModuleType) {
        return this.checkMemory(memoryModuleType, MemoryStatus.VALUE_PRESENT);
    }

    public <U> void eraseMemory(MemoryModuleType<U> memoryModuleType) {
        this.setMemory(memoryModuleType, Optional.empty());
    }

    public <U> void setMemory(MemoryModuleType<U> memoryModuleType, @Nullable U object) {
        this.setMemory(memoryModuleType, Optional.ofNullable(object));
    }

    public <U> void setMemoryWithExpiry(MemoryModuleType<U> memoryModuleType, U object, long l) {
        this.setMemoryInternal(memoryModuleType, Optional.of(ExpirableValue.of(object, (long)l)));
    }

    public <U> void setMemory(MemoryModuleType<U> memoryModuleType, Optional<? extends U> optional) {
        this.setMemoryInternal(memoryModuleType, optional.map(ExpirableValue::of));
    }

    private <U> void setMemoryInternal(MemoryModuleType<U> memoryModuleType, Optional<? extends ExpirableValue<?>> optional) {
        if (this.memories.containsKey(memoryModuleType)) {
            if (optional.isPresent() && this.isEmptyCollection(optional.get().getValue())) {
                this.eraseMemory(memoryModuleType);
            } else {
                this.memories.put(memoryModuleType, optional);
            }
        }
    }

    public <U> Optional<U> getMemory(MemoryModuleType<U> memoryModuleType) {
        return this.memories.get(memoryModuleType).map(ExpirableValue::getValue);
    }

    public <U> boolean isMemoryValue(MemoryModuleType<U> memoryModuleType, U object) {
        if (!this.hasMemoryValue(memoryModuleType)) {
            return false;
        }
        return this.getMemory(memoryModuleType).filter(object2 -> object2.equals(object)).isPresent();
    }

    public boolean checkMemory(MemoryModuleType<?> memoryModuleType, MemoryStatus memoryStatus) {
        Optional<ExpirableValue<?>> optional = this.memories.get(memoryModuleType);
        if (optional == null) {
            return false;
        }
        return memoryStatus == MemoryStatus.REGISTERED || memoryStatus == MemoryStatus.VALUE_PRESENT && optional.isPresent() || memoryStatus == MemoryStatus.VALUE_ABSENT && !optional.isPresent();
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setCoreActivities(Set<Activity> set) {
        this.coreActivities = set;
    }

    @Deprecated
    public List<Behavior<? super E>> getRunningBehaviors() {
        ObjectArrayList list = new ObjectArrayList();
        for (Map<Activity, Set<Behavior<E>>> map : this.availableBehaviorsByPriority.values()) {
            for (Set<Behavior<E>> set : map.values()) {
                for (Behavior<? super E> behavior : set) {
                    if (behavior.getStatus() != Behavior.Status.RUNNING) continue;
                    list.add(behavior);
                }
            }
        }
        return list;
    }

    public void useDefaultActivity() {
        this.setActiveActivity(this.defaultActivity);
    }

    public Optional<Activity> getActiveNonCoreActivity() {
        for (Activity activity : this.activeActivities) {
            if (this.coreActivities.contains(activity)) continue;
            return Optional.of(activity);
        }
        return Optional.empty();
    }

    public void setActiveActivityIfPossible(Activity activity) {
        if (this.activityRequirementsAreMet(activity)) {
            this.setActiveActivity(activity);
        } else {
            this.useDefaultActivity();
        }
    }

    private void setActiveActivity(Activity activity) {
        if (this.isActive(activity)) {
            return;
        }
        this.eraseMemoriesForOtherActivitesThan(activity);
        this.activeActivities.clear();
        this.activeActivities.addAll(this.coreActivities);
        this.activeActivities.add(activity);
    }

    private void eraseMemoriesForOtherActivitesThan(Activity activity) {
        for (Activity activity2 : this.activeActivities) {
            Set<MemoryModuleType<?>> set;
            if (activity2 == activity || (set = this.activityMemoriesToEraseWhenStopped.get(activity2)) == null) continue;
            for (MemoryModuleType<?> memoryModuleType : set) {
                this.eraseMemory(memoryModuleType);
            }
        }
    }

    public void updateActivityFromSchedule(long l, long m) {
        if (m - this.lastScheduleUpdate > 20L) {
            this.lastScheduleUpdate = m;
            Activity activity = this.getSchedule().getActivityAt((int)(l % 24000L));
            if (!this.activeActivities.contains(activity)) {
                this.setActiveActivityIfPossible(activity);
            }
        }
    }

    public void setActiveActivityToFirstValid(List<Activity> list) {
        for (Activity activity : list) {
            if (!this.activityRequirementsAreMet(activity)) continue;
            this.setActiveActivity(activity);
            break;
        }
    }

    public void setDefaultActivity(Activity activity) {
        this.defaultActivity = activity;
    }

    public void addActivity(Activity activity, int i, ImmutableList<? extends Behavior<? super E>> immutableList) {
        this.addActivity(activity, this.createPriorityPairs(i, immutableList));
    }

    public void addActivityAndRemoveMemoryWhenStopped(Activity activity, int i, ImmutableList<? extends Behavior<? super E>> immutableList, MemoryModuleType<?> memoryModuleType) {
        ImmutableSet set = ImmutableSet.of((Object)Pair.of(memoryModuleType, (Object)MemoryStatus.VALUE_PRESENT));
        ImmutableSet set2 = ImmutableSet.of(memoryModuleType);
        this.addActivityAndRemoveMemoriesWhenStopped(activity, (ImmutableList<? extends Pair<Integer, ? extends Behavior<? super E>>>)this.createPriorityPairs(i, immutableList), (Set<Pair<MemoryModuleType<?>, MemoryStatus>>)set, (Set<MemoryModuleType<?>>)set2);
    }

    public void addActivity(Activity activity, ImmutableList<? extends Pair<Integer, ? extends Behavior<? super E>>> immutableList) {
        this.addActivityAndRemoveMemoriesWhenStopped(activity, immutableList, (Set<Pair<MemoryModuleType<?>, MemoryStatus>>)ImmutableSet.of(), Sets.newHashSet());
    }

    public void addActivityWithConditions(Activity activity, ImmutableList<? extends Pair<Integer, ? extends Behavior<? super E>>> immutableList, Set<Pair<MemoryModuleType<?>, MemoryStatus>> set) {
        this.addActivityAndRemoveMemoriesWhenStopped(activity, immutableList, set, Sets.newHashSet());
    }

    private void addActivityAndRemoveMemoriesWhenStopped(Activity activity2, ImmutableList<? extends Pair<Integer, ? extends Behavior<? super E>>> immutableList, Set<Pair<MemoryModuleType<?>, MemoryStatus>> set, Set<MemoryModuleType<?>> set2) {
        this.activityRequirements.put(activity2, set);
        if (!set2.isEmpty()) {
            this.activityMemoriesToEraseWhenStopped.put(activity2, set2);
        }
        for (Pair pair : immutableList) {
            this.availableBehaviorsByPriority.computeIfAbsent((Integer)pair.getFirst(), integer -> Maps.newHashMap()).computeIfAbsent(activity2, activity -> Sets.newLinkedHashSet()).add(pair.getSecond());
        }
    }

    public boolean isActive(Activity activity) {
        return this.activeActivities.contains(activity);
    }

    public Brain<E> copyWithoutBehaviors() {
        Brain<E> brain = new Brain<E>(this.memories.keySet(), this.sensors.keySet(), ImmutableList.of(), this.codec);
        for (Map.Entry<MemoryModuleType<?>, Optional<ExpirableValue<?>>> entry : this.memories.entrySet()) {
            MemoryModuleType<?> memoryModuleType = entry.getKey();
            if (!entry.getValue().isPresent()) continue;
            brain.memories.put(memoryModuleType, entry.getValue());
        }
        return brain;
    }

    public void tick(ServerLevel serverLevel, E livingEntity) {
        this.forgetOutdatedMemories();
        this.tickSensors(serverLevel, livingEntity);
        this.startEachNonRunningBehavior(serverLevel, livingEntity);
        this.tickEachRunningBehavior(serverLevel, livingEntity);
    }

    private void tickSensors(ServerLevel serverLevel, E livingEntity) {
        for (Sensor<? super E> sensor : this.sensors.values()) {
            sensor.tick(serverLevel, livingEntity);
        }
    }

    private void forgetOutdatedMemories() {
        for (Map.Entry<MemoryModuleType<?>, Optional<ExpirableValue<?>>> entry : this.memories.entrySet()) {
            if (!entry.getValue().isPresent()) continue;
            ExpirableValue<?> expirableValue = entry.getValue().get();
            expirableValue.tick();
            if (!expirableValue.hasExpired()) continue;
            this.eraseMemory(entry.getKey());
        }
    }

    public void stopAll(ServerLevel serverLevel, E livingEntity) {
        long l = ((LivingEntity)livingEntity).level.getGameTime();
        for (Behavior<E> behavior : this.getRunningBehaviors()) {
            behavior.doStop(serverLevel, livingEntity, l);
        }
    }

    private void startEachNonRunningBehavior(ServerLevel serverLevel, E livingEntity) {
        long l = serverLevel.getGameTime();
        for (Map<Activity, Set<Behavior<E>>> map : this.availableBehaviorsByPriority.values()) {
            for (Map.Entry<Activity, Set<Behavior<E>>> entry : map.entrySet()) {
                Activity activity = entry.getKey();
                if (!this.activeActivities.contains(activity)) continue;
                Set<Behavior<E>> set = entry.getValue();
                for (Behavior<? super E> behavior : set) {
                    if (behavior.getStatus() != Behavior.Status.STOPPED) continue;
                    behavior.tryStart(serverLevel, livingEntity, l);
                }
            }
        }
    }

    private void tickEachRunningBehavior(ServerLevel serverLevel, E livingEntity) {
        long l = serverLevel.getGameTime();
        for (Behavior<E> behavior : this.getRunningBehaviors()) {
            behavior.tickOrStop(serverLevel, livingEntity, l);
        }
    }

    private boolean activityRequirementsAreMet(Activity activity) {
        if (!this.activityRequirements.containsKey(activity)) {
            return false;
        }
        for (Pair<MemoryModuleType<?>, MemoryStatus> pair : this.activityRequirements.get(activity)) {
            MemoryStatus memoryStatus;
            MemoryModuleType memoryModuleType = (MemoryModuleType)pair.getFirst();
            if (this.checkMemory(memoryModuleType, memoryStatus = (MemoryStatus)pair.getSecond())) continue;
            return false;
        }
        return true;
    }

    private boolean isEmptyCollection(Object object) {
        return object instanceof Collection && ((Collection)object).isEmpty();
    }

    ImmutableList<? extends Pair<Integer, ? extends Behavior<? super E>>> createPriorityPairs(int i, ImmutableList<? extends Behavior<? super E>> immutableList) {
        int j = i;
        ImmutableList.Builder builder = ImmutableList.builder();
        for (Behavior behavior : immutableList) {
            builder.add((Object)Pair.of((Object)j++, (Object)behavior));
        }
        return builder.build();
    }

    static /* synthetic */ Logger method_28314() {
        return LOGGER;
    }

    static /* synthetic */ Stream method_28308(Brain brain) {
        return brain.memories();
    }

    static /* synthetic */ void method_28309(Brain brain, MemoryModuleType memoryModuleType, Optional optional) {
        brain.setMemoryInternal(memoryModuleType, optional);
    }
}
