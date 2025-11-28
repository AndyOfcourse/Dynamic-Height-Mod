/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.Brain
 *  net.minecraft.world.entity.ai.behavior.Behavior
 *  net.minecraft.world.entity.ai.behavior.EntityTracker
 *  net.minecraft.world.entity.ai.behavior.PositionTracker
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.memory.MemoryStatus
 *  net.minecraft.world.entity.ai.memory.WalkTarget
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class InteractWith<E extends LivingEntity, T extends LivingEntity>
extends Behavior<E> {
    private final int maxDist;
    private final float speedModifier;
    private final EntityType<? extends T> type;
    private final int interactionRangeSqr;
    private final Predicate<T> targetFilter;
    private final Predicate<E> selfFilter;
    private final MemoryModuleType<T> memory;

    public InteractWith(EntityType<? extends T> entityType, int i, Predicate<E> predicate, Predicate<T> predicate2, MemoryModuleType<T> memoryModuleType, float f, int j) {
        super((Map)ImmutableMap.of((Object)MemoryModuleType.LOOK_TARGET, (Object)MemoryStatus.REGISTERED, (Object)MemoryModuleType.WALK_TARGET, (Object)MemoryStatus.VALUE_ABSENT, (Object)MemoryModuleType.VISIBLE_LIVING_ENTITIES, (Object)MemoryStatus.VALUE_PRESENT));
        this.type = entityType;
        this.speedModifier = f;
        this.interactionRangeSqr = i * i;
        this.maxDist = j;
        this.targetFilter = predicate2;
        this.selfFilter = predicate;
        this.memory = memoryModuleType;
    }

    public static <T extends LivingEntity> InteractWith<LivingEntity, T> of(EntityType<? extends T> entityType, int i, MemoryModuleType<T> memoryModuleType, float f, int j) {
        return new InteractWith<LivingEntity, LivingEntity>(entityType, i, livingEntity -> true, livingEntity -> true, memoryModuleType, f, j);
    }

    protected boolean checkExtraStartConditions(ServerLevel serverLevel, E livingEntity) {
        return this.selfFilter.test(livingEntity) && this.seesAtLeastOneValidTarget(livingEntity);
    }

    private boolean seesAtLeastOneValidTarget(E livingEntity) {
        List list = (List)livingEntity.getBrain().getMemory(MemoryModuleType.VISIBLE_LIVING_ENTITIES).get();
        return list.stream().anyMatch(this::isTargetValid);
    }

    private boolean isTargetValid(LivingEntity livingEntity) {
        return this.type.equals((Object)livingEntity.getType()) && this.targetFilter.test(livingEntity);
    }

    protected void start(ServerLevel serverLevel, E livingEntity, long l) {
        Brain brain = livingEntity.getBrain();
        brain.getMemory(MemoryModuleType.VISIBLE_LIVING_ENTITIES).ifPresent(list -> list.stream().filter(livingEntity -> this.type.equals((Object)livingEntity.getType())).map(livingEntity -> livingEntity).filter(livingEntity2 -> livingEntity2.distanceToSqr((Entity)livingEntity) <= (double)this.interactionRangeSqr).filter(this.targetFilter).findFirst().ifPresent(livingEntity -> {
            brain.setMemory(this.memory, livingEntity);
            brain.setMemory(MemoryModuleType.LOOK_TARGET, (Object)new EntityTracker((Entity)livingEntity, true));
            brain.setMemory(MemoryModuleType.WALK_TARGET, (Object)new WalkTarget((PositionTracker)new EntityTracker((Entity)livingEntity, false), this.speedModifier, this.maxDist));
        }));
    }
}
