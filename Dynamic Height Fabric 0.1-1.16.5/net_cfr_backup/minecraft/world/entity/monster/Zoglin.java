/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.game.DebugPackets
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.util.IntRange
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntitySelector
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MobType
 *  net.minecraft.world.entity.ai.Brain
 *  net.minecraft.world.entity.ai.Brain$Provider
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.behavior.Behavior
 *  net.minecraft.world.entity.ai.behavior.BehaviorUtils
 *  net.minecraft.world.entity.ai.behavior.DoNothing
 *  net.minecraft.world.entity.ai.behavior.LookAtTargetSink
 *  net.minecraft.world.entity.ai.behavior.MeleeAttack
 *  net.minecraft.world.entity.ai.behavior.MoveToTargetSink
 *  net.minecraft.world.entity.ai.behavior.RandomStroll
 *  net.minecraft.world.entity.ai.behavior.RunIf
 *  net.minecraft.world.entity.ai.behavior.RunOne
 *  net.minecraft.world.entity.ai.behavior.RunSometimes
 *  net.minecraft.world.entity.ai.behavior.SetEntityLookTarget
 *  net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach
 *  net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget
 *  net.minecraft.world.entity.ai.behavior.StartAttacking
 *  net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.sensing.Sensor
 *  net.minecraft.world.entity.ai.sensing.SensorType
 *  net.minecraft.world.entity.monster.Enemy
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.monster.hoglin.HoglinBase
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.schedule.Activity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 */
package net.minecraft.world.entity.monster;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.IntRange;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunIf;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.RunSometimes;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.hoglin.HoglinBase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class Zoglin
extends Monster
implements Enemy,
HoglinBase {
    private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(Zoglin.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    private int attackAnimationRemainingTicks;
    protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Zoglin>>> SENSOR_TYPES = ImmutableList.of((Object)SensorType.NEAREST_LIVING_ENTITIES, (Object)SensorType.NEAREST_PLAYERS);
    protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of((Object)MemoryModuleType.LIVING_ENTITIES, (Object)MemoryModuleType.VISIBLE_LIVING_ENTITIES, (Object)MemoryModuleType.NEAREST_VISIBLE_PLAYER, (Object)MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, (Object)MemoryModuleType.LOOK_TARGET, (Object)MemoryModuleType.WALK_TARGET, (Object)MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, (Object)MemoryModuleType.PATH, (Object)MemoryModuleType.ATTACK_TARGET, (Object)MemoryModuleType.ATTACK_COOLING_DOWN);

    public Zoglin(EntityType<? extends Zoglin> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 5;
    }

    protected Brain.Provider<Zoglin> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        Brain brain = this.brainProvider().makeBrain(dynamic);
        Zoglin.initCoreActivity((Brain<Zoglin>)brain);
        Zoglin.initIdleActivity((Brain<Zoglin>)brain);
        Zoglin.initFightActivity((Brain<Zoglin>)brain);
        brain.setCoreActivities((Set)ImmutableSet.of((Object)Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void initCoreActivity(Brain<Zoglin> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of((Object)new LookAtTargetSink(45, 90), (Object)new MoveToTargetSink()));
    }

    private static void initIdleActivity(Brain<Zoglin> brain) {
        brain.addActivity(Activity.IDLE, 10, ImmutableList.of((Object)new StartAttacking(Zoglin::findNearestValidAttackTarget), (Object)new RunSometimes((Behavior)new SetEntityLookTarget(8.0f), IntRange.of((int)30, (int)60)), (Object)new RunOne((List)ImmutableList.of((Object)Pair.of((Object)new RandomStroll(0.4f), (Object)2), (Object)Pair.of((Object)new SetWalkTargetFromLookTarget(0.4f, 3), (Object)2), (Object)Pair.of((Object)new DoNothing(30, 60), (Object)1)))));
    }

    private static void initFightActivity(Brain<Zoglin> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 10, ImmutableList.of((Object)new SetWalkTargetFromAttackTargetIfTargetOutOfReach(1.0f), (Object)new RunIf(Zoglin::isAdult, (Behavior)new MeleeAttack(40)), (Object)new RunIf(Zoglin::isBaby, (Behavior)new MeleeAttack(15)), (Object)new StopAttackingIfTargetInvalid()), MemoryModuleType.ATTACK_TARGET);
    }

    private Optional<? extends LivingEntity> findNearestValidAttackTarget() {
        return ((List)this.getBrain().getMemory(MemoryModuleType.VISIBLE_LIVING_ENTITIES).orElse(ImmutableList.of())).stream().filter(Zoglin::isTargetable).findFirst();
    }

    private static boolean isTargetable(LivingEntity livingEntity) {
        EntityType entityType = livingEntity.getType();
        return entityType != EntityType.ZOGLIN && entityType != EntityType.CREEPER && EntitySelector.ATTACK_ALLOWED.test(livingEntity);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_BABY_ID, (Object)false);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        super.onSyncedDataUpdated(entityDataAccessor);
        if (DATA_BABY_ID.equals(entityDataAccessor)) {
            this.refreshDimensions();
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 40.0).add(Attributes.MOVEMENT_SPEED, (double)0.3f).add(Attributes.KNOCKBACK_RESISTANCE, (double)0.6f).add(Attributes.ATTACK_KNOCKBACK, 1.0).add(Attributes.ATTACK_DAMAGE, 6.0);
    }

    public boolean isAdult() {
        return !this.isBaby();
    }

    public boolean doHurtTarget(Entity entity) {
        if (!(entity instanceof LivingEntity)) {
            return false;
        }
        this.attackAnimationRemainingTicks = 10;
        this.level.broadcastEntityEvent((Entity)this, (byte)4);
        this.playSound(SoundEvents.ZOGLIN_ATTACK, 1.0f, this.getVoicePitch());
        return HoglinBase.hurtAndThrowTarget((LivingEntity)this, (LivingEntity)((LivingEntity)entity));
    }

    public boolean canBeLeashed(Player player) {
        return !this.isLeashed();
    }

    protected void blockedByShield(LivingEntity livingEntity) {
        if (!this.isBaby()) {
            HoglinBase.throwTarget((LivingEntity)this, (LivingEntity)livingEntity);
        }
    }

    public double getPassengersRidingOffset() {
        return (double)this.getBbHeight() - (this.isBaby() ? 0.2 : 0.15);
    }

    public boolean hurt(DamageSource damageSource, float f) {
        boolean bl = super.hurt(damageSource, f);
        if (this.level.isClientSide) {
            return false;
        }
        if (!bl || !(damageSource.getEntity() instanceof LivingEntity)) {
            return bl;
        }
        LivingEntity livingEntity = (LivingEntity)damageSource.getEntity();
        if (EntitySelector.ATTACK_ALLOWED.test(livingEntity) && !BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget((LivingEntity)this, (LivingEntity)livingEntity, (double)4.0)) {
            this.setAttackTarget(livingEntity);
        }
        return bl;
    }

    private void setAttackTarget(LivingEntity livingEntity) {
        this.brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        this.brain.setMemoryWithExpiry(MemoryModuleType.ATTACK_TARGET, (Object)livingEntity, 200L);
    }

    public Brain<Zoglin> getBrain() {
        return super.getBrain();
    }

    protected void updateActivity() {
        Activity activity = this.brain.getActiveNonCoreActivity().orElse(null);
        this.brain.setActiveActivityToFirstValid((List)ImmutableList.of((Object)Activity.FIGHT, (Object)Activity.IDLE));
        Activity activity2 = this.brain.getActiveNonCoreActivity().orElse(null);
        if (activity2 == Activity.FIGHT && activity != Activity.FIGHT) {
            this.playAngrySound();
        }
        this.setAggressive(this.brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
    }

    protected void customServerAiStep() {
        this.level.getProfiler().push("zoglinBrain");
        this.getBrain().tick((ServerLevel)this.level, (LivingEntity)this);
        this.level.getProfiler().pop();
        this.updateActivity();
    }

    public void setBaby(boolean bl) {
        this.getEntityData().set(DATA_BABY_ID, (Object)bl);
        if (!this.level.isClientSide && bl) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(0.5);
        }
    }

    public boolean isBaby() {
        return (Boolean)this.getEntityData().get(DATA_BABY_ID);
    }

    public void aiStep() {
        if (this.attackAnimationRemainingTicks > 0) {
            --this.attackAnimationRemainingTicks;
        }
        super.aiStep();
    }

    @Environment(value=EnvType.CLIENT)
    public void handleEntityEvent(byte b) {
        if (b == 4) {
            this.attackAnimationRemainingTicks = 10;
            this.playSound(SoundEvents.ZOGLIN_ATTACK, 1.0f, this.getVoicePitch());
        } else {
            super.handleEntityEvent(b);
        }
    }

    @Environment(value=EnvType.CLIENT)
    public int getAttackAnimationRemainingTicks() {
        return this.attackAnimationRemainingTicks;
    }

    protected SoundEvent getAmbientSound() {
        if (this.level.isClientSide) {
            return null;
        }
        if (this.brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
            return SoundEvents.ZOGLIN_ANGRY;
        }
        return SoundEvents.ZOGLIN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ZOGLIN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOGLIN_DEATH;
    }

    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.ZOGLIN_STEP, 0.15f, 1.0f);
    }

    protected void playAngrySound() {
        this.playSound(SoundEvents.ZOGLIN_ANGRY, 1.0f, this.getVoicePitch());
    }

    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPackets.sendEntityBrain((LivingEntity)this);
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (this.isBaby()) {
            compoundTag.putBoolean("IsBaby", true);
        }
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.getBoolean("IsBaby")) {
            this.setBaby(true);
        }
    }
}
