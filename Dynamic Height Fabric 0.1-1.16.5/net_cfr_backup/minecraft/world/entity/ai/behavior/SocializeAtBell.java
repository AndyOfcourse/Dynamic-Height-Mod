/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  net.minecraft.core.GlobalPos
 *  net.minecraft.core.Position
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
import java.util.Optional;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Position;
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

public class SocializeAtBell
extends Behavior<LivingEntity> {
    public SocializeAtBell() {
        super((Map)ImmutableMap.of((Object)MemoryModuleType.WALK_TARGET, (Object)MemoryStatus.REGISTERED, (Object)MemoryModuleType.LOOK_TARGET, (Object)MemoryStatus.REGISTERED, (Object)MemoryModuleType.MEETING_POINT, (Object)MemoryStatus.VALUE_PRESENT, (Object)MemoryModuleType.VISIBLE_LIVING_ENTITIES, (Object)MemoryStatus.VALUE_PRESENT, (Object)MemoryModuleType.INTERACTION_TARGET, (Object)MemoryStatus.VALUE_ABSENT));
    }

    protected boolean checkExtraStartConditions(ServerLevel serverLevel, LivingEntity livingEntity2) {
        Brain brain = livingEntity2.getBrain();
        Optional optional = brain.getMemory(MemoryModuleType.MEETING_POINT);
        return serverLevel.getRandom().nextInt(100) == 0 && optional.isPresent() && serverLevel.dimension() == ((GlobalPos)optional.get()).dimension() && ((GlobalPos)optional.get()).pos().closerThan((Position)livingEntity2.position(), 4.0) && ((List)brain.getMemory(MemoryModuleType.VISIBLE_LIVING_ENTITIES).get()).stream().anyMatch(livingEntity -> EntityType.VILLAGER.equals(livingEntity.getType()));
    }

    protected void start(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        Brain brain = livingEntity.getBrain();
        brain.getMemory(MemoryModuleType.VISIBLE_LIVING_ENTITIES).ifPresent(list -> list.stream().filter(livingEntity -> EntityType.VILLAGER.equals(livingEntity.getType())).filter(livingEntity2 -> livingEntity2.distanceToSqr((Entity)livingEntity) <= 32.0).findFirst().ifPresent(livingEntity -> {
            brain.setMemory(MemoryModuleType.INTERACTION_TARGET, livingEntity);
            brain.setMemory(MemoryModuleType.LOOK_TARGET, (Object)new EntityTracker((Entity)livingEntity, true));
            brain.setMemory(MemoryModuleType.WALK_TARGET, (Object)new WalkTarget((PositionTracker)new EntityTracker((Entity)livingEntity, false), 0.3f, 1));
        }));
    }
}
