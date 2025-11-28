/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.Brain
 *  net.minecraft.world.entity.ai.behavior.Behavior
 *  net.minecraft.world.entity.ai.behavior.EntityTracker
 *  net.minecraft.world.entity.ai.behavior.PositionTracker
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.memory.MemoryStatus
 *  net.minecraft.world.entity.ai.memory.WalkTarget
 *  net.minecraft.world.entity.ai.util.RandomPos
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class PlayTagWithOtherKids
extends Behavior<PathfinderMob> {
    public PlayTagWithOtherKids() {
        super((Map)ImmutableMap.of((Object)MemoryModuleType.VISIBLE_VILLAGER_BABIES, (Object)MemoryStatus.VALUE_PRESENT, (Object)MemoryModuleType.WALK_TARGET, (Object)MemoryStatus.VALUE_ABSENT, (Object)MemoryModuleType.LOOK_TARGET, (Object)MemoryStatus.REGISTERED, (Object)MemoryModuleType.INTERACTION_TARGET, (Object)MemoryStatus.REGISTERED));
    }

    protected boolean checkExtraStartConditions(ServerLevel serverLevel, PathfinderMob pathfinderMob) {
        return serverLevel.getRandom().nextInt(10) == 0 && this.hasFriendsNearby(pathfinderMob);
    }

    protected void start(ServerLevel serverLevel, PathfinderMob pathfinderMob, long l) {
        LivingEntity livingEntity2 = this.seeIfSomeoneIsChasingMe((LivingEntity)pathfinderMob);
        if (livingEntity2 != null) {
            this.fleeFromChaser(serverLevel, pathfinderMob, livingEntity2);
            return;
        }
        Optional<LivingEntity> optional = this.findSomeoneBeingChased(pathfinderMob);
        if (optional.isPresent()) {
            PlayTagWithOtherKids.chaseKid(pathfinderMob, optional.get());
            return;
        }
        this.findSomeoneToChase(pathfinderMob).ifPresent(livingEntity -> PlayTagWithOtherKids.chaseKid(pathfinderMob, livingEntity));
    }

    private void fleeFromChaser(ServerLevel serverLevel, PathfinderMob pathfinderMob, LivingEntity livingEntity) {
        for (int i = 0; i < 10; ++i) {
            Vec3 vec3 = RandomPos.getLandPos((PathfinderMob)pathfinderMob, (int)20, (int)8);
            if (vec3 == null || !serverLevel.isVillage(new BlockPos(vec3))) continue;
            pathfinderMob.getBrain().setMemory(MemoryModuleType.WALK_TARGET, (Object)new WalkTarget(vec3, 0.6f, 0));
            return;
        }
    }

    private static void chaseKid(PathfinderMob pathfinderMob, LivingEntity livingEntity) {
        Brain brain = pathfinderMob.getBrain();
        brain.setMemory(MemoryModuleType.INTERACTION_TARGET, (Object)livingEntity);
        brain.setMemory(MemoryModuleType.LOOK_TARGET, (Object)new EntityTracker((Entity)livingEntity, true));
        brain.setMemory(MemoryModuleType.WALK_TARGET, (Object)new WalkTarget((PositionTracker)new EntityTracker((Entity)livingEntity, false), 0.6f, 1));
    }

    private Optional<LivingEntity> findSomeoneToChase(PathfinderMob pathfinderMob) {
        return this.getFriendsNearby(pathfinderMob).stream().findAny();
    }

    private Optional<LivingEntity> findSomeoneBeingChased(PathfinderMob pathfinderMob) {
        Map<LivingEntity, Integer> map = this.checkHowManyChasersEachFriendHas(pathfinderMob);
        return map.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue)).filter(entry -> (Integer)entry.getValue() > 0 && (Integer)entry.getValue() <= 5).map(Map.Entry::getKey).findFirst();
    }

    private Map<LivingEntity, Integer> checkHowManyChasersEachFriendHas(PathfinderMob pathfinderMob) {
        HashMap map = Maps.newHashMap();
        this.getFriendsNearby(pathfinderMob).stream().filter(this::isChasingSomeone).forEach(livingEntity2 -> map.compute(this.whoAreYouChasing((LivingEntity)livingEntity2), (livingEntity, integer) -> integer == null ? 1 : integer + 1));
        return map;
    }

    private List<LivingEntity> getFriendsNearby(PathfinderMob pathfinderMob) {
        return (List)pathfinderMob.getBrain().getMemory(MemoryModuleType.VISIBLE_VILLAGER_BABIES).get();
    }

    private LivingEntity whoAreYouChasing(LivingEntity livingEntity) {
        return (LivingEntity)livingEntity.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).get();
    }

    @Nullable
    private LivingEntity seeIfSomeoneIsChasingMe(LivingEntity livingEntity) {
        return ((List)livingEntity.getBrain().getMemory(MemoryModuleType.VISIBLE_VILLAGER_BABIES).get()).stream().filter(livingEntity2 -> this.isFriendChasingMe(livingEntity, (LivingEntity)livingEntity2)).findAny().orElse(null);
    }

    private boolean isChasingSomeone(LivingEntity livingEntity) {
        return livingEntity.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).isPresent();
    }

    private boolean isFriendChasingMe(LivingEntity livingEntity, LivingEntity livingEntity22) {
        return livingEntity22.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).filter(livingEntity2 -> livingEntity2 == livingEntity).isPresent();
    }

    private boolean hasFriendsNearby(PathfinderMob pathfinderMob) {
        return pathfinderMob.getBrain().hasMemoryValue(MemoryModuleType.VISIBLE_VILLAGER_BABIES);
    }
}
