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
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.util.IntRange
 *  net.minecraft.util.Mth
 *  net.minecraft.util.TimeUtil
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.AgableMob
 *  net.minecraft.world.entity.AgableMob$AgableMobGroupData
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.NeutralMob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.SpawnGroupData
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.FloatGoal
 *  net.minecraft.world.entity.ai.goal.FollowParentGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
 *  net.minecraft.world.entity.ai.goal.RandomStrollGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.entity.animal.Fox
 *  net.minecraft.world.entity.animal.PolarBear$PolarBearAttackPlayersGoal
 *  net.minecraft.world.entity.animal.PolarBear$PolarBearHurtByTargetGoal
 *  net.minecraft.world.entity.animal.PolarBear$PolarBearMeleeAttackGoal
 *  net.minecraft.world.entity.animal.PolarBear$PolarBearPanicGoal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.biome.Biomes
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.world.entity.animal;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.IntRange;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PolarBear
extends Animal
implements NeutralMob {
    private static final EntityDataAccessor<Boolean> DATA_STANDING_ID = SynchedEntityData.defineId(PolarBear.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    private float clientSideStandAnimationO;
    private float clientSideStandAnimation;
    private int warningSoundTicks;
    private static final IntRange PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds((int)20, (int)39);
    private int remainingPersistentAngerTime;
    private UUID persistentAngerTarget;

    public PolarBear(EntityType<? extends PolarBear> entityType, Level level) {
        super(entityType, level);
    }

    public AgableMob getBreedOffspring(ServerLevel serverLevel, AgableMob agableMob) {
        return (AgableMob)EntityType.POLAR_BEAR.create((Level)serverLevel);
    }

    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, (Goal)new FloatGoal((Mob)this));
        this.goalSelector.addGoal(1, (Goal)new PolarBearMeleeAttackGoal(this));
        this.goalSelector.addGoal(1, (Goal)new PolarBearPanicGoal(this));
        this.goalSelector.addGoal(4, (Goal)new FollowParentGoal((Animal)this, 1.25));
        this.goalSelector.addGoal(5, (Goal)new RandomStrollGoal((PathfinderMob)this, 1.0));
        this.goalSelector.addGoal(6, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 6.0f));
        this.goalSelector.addGoal(7, (Goal)new RandomLookAroundGoal((Mob)this));
        this.targetSelector.addGoal(1, (Goal)new PolarBearHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, (Goal)new PolarBearAttackPlayersGoal(this));
        this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, 10, true, false, arg_0 -> ((PolarBear)this).isAngryAt(arg_0)));
        this.targetSelector.addGoal(4, (Goal)new NearestAttackableTargetGoal((Mob)this, Fox.class, 10, true, true, null));
        this.targetSelector.addGoal(5, (Goal)new ResetUniversalAngerTargetGoal((Mob)this, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 30.0).add(Attributes.FOLLOW_RANGE, 20.0).add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.ATTACK_DAMAGE, 6.0);
    }

    public static boolean checkPolarBearSpawnRules(EntityType<PolarBear> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, Random random) {
        Optional optional = levelAccessor.getBiomeName(blockPos);
        if (Objects.equals(optional, Optional.of(Biomes.FROZEN_OCEAN)) || Objects.equals(optional, Optional.of(Biomes.DEEP_FROZEN_OCEAN))) {
            return levelAccessor.getRawBrightness(blockPos, 0) > 8 && levelAccessor.getBlockState(blockPos.below()).is(Blocks.ICE);
        }
        return PolarBear.checkAnimalSpawnRules(entityType, (LevelAccessor)levelAccessor, (MobSpawnType)mobSpawnType, (BlockPos)blockPos, (Random)random);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.readPersistentAngerSaveData((ServerLevel)this.level, compoundTag);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        this.addPersistentAngerSaveData(compoundTag);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.randomValue(this.random));
    }

    public void setRemainingPersistentAngerTime(int i) {
        this.remainingPersistentAngerTime = i;
    }

    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    public void setPersistentAngerTarget(@Nullable UUID uUID) {
        this.persistentAngerTarget = uUID;
    }

    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    protected SoundEvent getAmbientSound() {
        if (this.isBaby()) {
            return SoundEvents.POLAR_BEAR_AMBIENT_BABY;
        }
        return SoundEvents.POLAR_BEAR_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.POLAR_BEAR_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.POLAR_BEAR_DEATH;
    }

    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.POLAR_BEAR_STEP, 0.15f, 1.0f);
    }

    protected void playWarningSound() {
        if (this.warningSoundTicks <= 0) {
            this.playSound(SoundEvents.POLAR_BEAR_WARNING, 1.0f, this.getVoicePitch());
            this.warningSoundTicks = 40;
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_STANDING_ID, (Object)false);
    }

    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            if (this.clientSideStandAnimation != this.clientSideStandAnimationO) {
                this.refreshDimensions();
            }
            this.clientSideStandAnimationO = this.clientSideStandAnimation;
            this.clientSideStandAnimation = this.isStanding() ? Mth.clamp((float)(this.clientSideStandAnimation + 1.0f), (float)0.0f, (float)6.0f) : Mth.clamp((float)(this.clientSideStandAnimation - 1.0f), (float)0.0f, (float)6.0f);
        }
        if (this.warningSoundTicks > 0) {
            --this.warningSoundTicks;
        }
        if (!this.level.isClientSide) {
            this.updatePersistentAnger((ServerLevel)this.level, true);
        }
    }

    public EntityDimensions getDimensions(Pose pose) {
        if (this.clientSideStandAnimation > 0.0f) {
            float f = this.clientSideStandAnimation / 6.0f;
            float g = 1.0f + f;
            return super.getDimensions(pose).scale(1.0f, g);
        }
        return super.getDimensions(pose);
    }

    public boolean doHurtTarget(Entity entity) {
        boolean bl = entity.hurt(DamageSource.mobAttack((LivingEntity)this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (bl) {
            this.doEnchantDamageEffects((LivingEntity)this, entity);
        }
        return bl;
    }

    public boolean isStanding() {
        return (Boolean)this.entityData.get(DATA_STANDING_ID);
    }

    public void setStanding(boolean bl) {
        this.entityData.set(DATA_STANDING_ID, (Object)bl);
    }

    @Environment(value=EnvType.CLIENT)
    public float getStandingAnimationScale(float f) {
        return Mth.lerp((float)f, (float)this.clientSideStandAnimationO, (float)this.clientSideStandAnimation) / 6.0f;
    }

    protected float getWaterSlowDown() {
        return 0.98f;
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        if (spawnGroupData == null) {
            spawnGroupData = new AgableMob.AgableMobGroupData(1.0f);
        }
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }
}
