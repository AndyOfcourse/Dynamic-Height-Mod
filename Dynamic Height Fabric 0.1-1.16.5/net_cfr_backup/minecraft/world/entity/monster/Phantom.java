/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.FlyingMob
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.MobType
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.SpawnGroupData
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.control.BodyRotationControl
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.monster.Enemy
 *  net.minecraft.world.entity.monster.Phantom$AttackPhase
 *  net.minecraft.world.entity.monster.Phantom$PhantomAttackPlayerTargetGoal
 *  net.minecraft.world.entity.monster.Phantom$PhantomAttackStrategyGoal
 *  net.minecraft.world.entity.monster.Phantom$PhantomBodyRotationControl
 *  net.minecraft.world.entity.monster.Phantom$PhantomCircleAroundAnchorGoal
 *  net.minecraft.world.entity.monster.Phantom$PhantomLookControl
 *  net.minecraft.world.entity.monster.Phantom$PhantomMoveControl
 *  net.minecraft.world.entity.monster.Phantom$PhantomSweepAttackGoal
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.world.entity.monster;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Phantom
extends FlyingMob
implements Enemy {
    private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(Phantom.class, (EntityDataSerializer)EntityDataSerializers.INT);
    private Vec3 moveTargetPoint = Vec3.ZERO;
    private BlockPos anchorPoint = BlockPos.ZERO;
    private AttackPhase attackPhase = AttackPhase.CIRCLE;

    public Phantom(EntityType<? extends Phantom> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 5;
        this.moveControl = new PhantomMoveControl(this, (Mob)this);
        this.lookControl = new PhantomLookControl(this, (Mob)this);
    }

    protected BodyRotationControl createBodyControl() {
        return new PhantomBodyRotationControl(this, (Mob)this);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, (Goal)new PhantomAttackStrategyGoal(this, null));
        this.goalSelector.addGoal(2, (Goal)new PhantomSweepAttackGoal(this, null));
        this.goalSelector.addGoal(3, (Goal)new PhantomCircleAroundAnchorGoal(this, null));
        this.targetSelector.addGoal(1, (Goal)new PhantomAttackPlayerTargetGoal(this, null));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_SIZE, (Object)0);
    }

    public void setPhantomSize(int i) {
        this.entityData.set(ID_SIZE, (Object)Mth.clamp((int)i, (int)0, (int)64));
    }

    private void updatePhantomSizeInfo() {
        this.refreshDimensions();
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((double)(6 + this.getPhantomSize()));
    }

    public int getPhantomSize() {
        return (Integer)this.entityData.get(ID_SIZE);
    }

    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return entityDimensions.height * 0.35f;
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (ID_SIZE.equals(entityDataAccessor)) {
            this.updatePhantomSizeInfo();
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            float f = Mth.cos((float)((float)(this.getId() * 3 + this.tickCount) * 0.13f + (float)Math.PI));
            float g = Mth.cos((float)((float)(this.getId() * 3 + this.tickCount + 1) * 0.13f + (float)Math.PI));
            if (f > 0.0f && g <= 0.0f) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.PHANTOM_FLAP, this.getSoundSource(), 0.95f + this.random.nextFloat() * 0.05f, 0.95f + this.random.nextFloat() * 0.05f, false);
            }
            int i = this.getPhantomSize();
            float h = Mth.cos((float)(this.yRot * ((float)Math.PI / 180))) * (1.3f + 0.21f * (float)i);
            float j = Mth.sin((float)(this.yRot * ((float)Math.PI / 180))) * (1.3f + 0.21f * (float)i);
            float k = (0.3f + f * 0.45f) * ((float)i * 0.2f + 1.0f);
            this.level.addParticle((ParticleOptions)ParticleTypes.MYCELIUM, this.getX() + (double)h, this.getY() + (double)k, this.getZ() + (double)j, 0.0, 0.0, 0.0);
            this.level.addParticle((ParticleOptions)ParticleTypes.MYCELIUM, this.getX() - (double)h, this.getY() + (double)k, this.getZ() - (double)j, 0.0, 0.0, 0.0);
        }
    }

    public void aiStep() {
        if (this.isAlive() && this.isSunBurnTick()) {
            this.setSecondsOnFire(8);
        }
        super.aiStep();
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        this.anchorPoint = this.blockPosition().above(5);
        this.setPhantomSize(0);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.contains("AX")) {
            this.anchorPoint = new BlockPos(compoundTag.getInt("AX"), compoundTag.getInt("AY"), compoundTag.getInt("AZ"));
        }
        this.setPhantomSize(compoundTag.getInt("Size"));
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("AX", this.anchorPoint.getX());
        compoundTag.putInt("AY", this.anchorPoint.getY());
        compoundTag.putInt("AZ", this.anchorPoint.getZ());
        compoundTag.putInt("Size", this.getPhantomSize());
    }

    @Environment(value=EnvType.CLIENT)
    public boolean shouldRenderAtSqrDistance(double d) {
        return true;
    }

    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.PHANTOM_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.PHANTOM_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.PHANTOM_DEATH;
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    protected float getSoundVolume() {
        return 1.0f;
    }

    public boolean canAttackType(EntityType<?> entityType) {
        return true;
    }

    public EntityDimensions getDimensions(Pose pose) {
        int i = this.getPhantomSize();
        EntityDimensions entityDimensions = super.getDimensions(pose);
        float f = (entityDimensions.width + 0.2f * (float)i) / entityDimensions.width;
        return entityDimensions.scale(f);
    }

    static /* synthetic */ Vec3 method_7085(Phantom phantom) {
        return phantom.moveTargetPoint;
    }

    static /* synthetic */ AttackPhase method_7087(Phantom phantom) {
        return phantom.attackPhase;
    }

    static /* synthetic */ Random method_7080(Phantom phantom) {
        return phantom.random;
    }

    static /* synthetic */ Random method_7098(Phantom phantom) {
        return phantom.random;
    }

    static /* synthetic */ Random method_7099(Phantom phantom) {
        return phantom.random;
    }

    static /* synthetic */ Random method_7096(Phantom phantom) {
        return phantom.random;
    }

    static /* synthetic */ Random method_7093(Phantom phantom) {
        return phantom.random;
    }

    static /* synthetic */ Random method_7092(Phantom phantom) {
        return phantom.random;
    }

    static /* synthetic */ Random method_7090(Phantom phantom) {
        return phantom.random;
    }

    static /* synthetic */ Random method_7086(Phantom phantom) {
        return phantom.random;
    }

    static /* synthetic */ BlockPos method_7082(Phantom phantom) {
        return phantom.anchorPoint;
    }

    static /* synthetic */ BlockPos method_7088(Phantom phantom, BlockPos blockPos) {
        phantom.anchorPoint = blockPos;
        return phantom.anchorPoint;
    }

    static /* synthetic */ Vec3 method_7094(Phantom phantom, Vec3 vec3) {
        phantom.moveTargetPoint = vec3;
        return phantom.moveTargetPoint;
    }

    static /* synthetic */ AttackPhase method_7089(Phantom phantom, AttackPhase attackPhase) {
        phantom.attackPhase = attackPhase;
        return phantom.attackPhase;
    }

    static /* synthetic */ Random method_7095(Phantom phantom) {
        return phantom.random;
    }

    static /* synthetic */ Random method_7100(Phantom phantom) {
        return phantom.random;
    }

    static /* synthetic */ Random method_7081(Phantom phantom) {
        return phantom.random;
    }

    static /* synthetic */ Random method_7083(Phantom phantom) {
        return phantom.random;
    }
}
