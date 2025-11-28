/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.Difficulty
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.FlyingMob
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.monster.Enemy
 *  net.minecraft.world.entity.monster.Ghast$GhastLookGoal
 *  net.minecraft.world.entity.monster.Ghast$GhastMoveControl
 *  net.minecraft.world.entity.monster.Ghast$GhastShootFireballGoal
 *  net.minecraft.world.entity.monster.Ghast$RandomFloatAroundGoal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.LargeFireball
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 */
package net.minecraft.world.entity.monster;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class Ghast
extends FlyingMob
implements Enemy {
    private static final EntityDataAccessor<Boolean> DATA_IS_CHARGING = SynchedEntityData.defineId(Ghast.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    private int explosionPower = 1;

    public Ghast(EntityType<? extends Ghast> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 5;
        this.moveControl = new GhastMoveControl(this);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(5, (Goal)new RandomFloatAroundGoal(this));
        this.goalSelector.addGoal(7, (Goal)new GhastLookGoal(this));
        this.goalSelector.addGoal(7, (Goal)new GhastShootFireballGoal(this));
        this.targetSelector.addGoal(1, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, 10, true, false, livingEntity -> Math.abs(livingEntity.getY() - this.getY()) <= 4.0));
    }

    @Environment(value=EnvType.CLIENT)
    public boolean isCharging() {
        return (Boolean)this.entityData.get(DATA_IS_CHARGING);
    }

    public void setCharging(boolean bl) {
        this.entityData.set(DATA_IS_CHARGING, (Object)bl);
    }

    public int getExplosionPower() {
        return this.explosionPower;
    }

    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    public boolean hurt(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        }
        if (damageSource.getDirectEntity() instanceof LargeFireball && damageSource.getEntity() instanceof Player) {
            super.hurt(damageSource, 1000.0f);
            return true;
        }
        return super.hurt(damageSource, f);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_CHARGING, (Object)false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.FOLLOW_RANGE, 100.0);
    }

    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.GHAST_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.GHAST_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.GHAST_DEATH;
    }

    protected float getSoundVolume() {
        return 5.0f;
    }

    public static boolean checkGhastSpawnRules(EntityType<Ghast> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, Random random) {
        return levelAccessor.getDifficulty() != Difficulty.PEACEFUL && random.nextInt(20) == 0 && Ghast.checkMobSpawnRules(entityType, (LevelAccessor)levelAccessor, (MobSpawnType)mobSpawnType, (BlockPos)blockPos, (Random)random);
    }

    public int getMaxSpawnClusterSize() {
        return 1;
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("ExplosionPower", this.explosionPower);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.contains("ExplosionPower", 99)) {
            this.explosionPower = compoundTag.getInt("ExplosionPower");
        }
    }

    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return 2.6f;
    }
}
