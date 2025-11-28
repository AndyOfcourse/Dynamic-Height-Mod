/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.util.Mth
 *  net.minecraft.world.Difficulty
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.SpawnGroupData
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.animal.IronGolem
 *  net.minecraft.world.entity.monster.Enemy
 *  net.minecraft.world.entity.monster.Slime$SlimeAttackGoal
 *  net.minecraft.world.entity.monster.Slime$SlimeFloatGoal
 *  net.minecraft.world.entity.monster.Slime$SlimeKeepOnJumpingGoal
 *  net.minecraft.world.entity.monster.Slime$SlimeMoveControl
 *  net.minecraft.world.entity.monster.Slime$SlimeRandomDirectionGoal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.WorldGenLevel
 *  net.minecraft.world.level.biome.Biomes
 *  net.minecraft.world.level.levelgen.WorldgenRandom
 *  net.minecraft.world.level.storage.loot.BuiltInLootTables
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.world.entity.monster;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Slime
extends Mob
implements Enemy {
    private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(Slime.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public float targetSquish;
    public float squish;
    public float oSquish;
    private boolean wasOnGround;

    public Slime(EntityType<? extends Slime> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SlimeMoveControl(this);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, (Goal)new SlimeFloatGoal(this));
        this.goalSelector.addGoal(2, (Goal)new SlimeAttackGoal(this));
        this.goalSelector.addGoal(3, (Goal)new SlimeRandomDirectionGoal(this));
        this.goalSelector.addGoal(5, (Goal)new SlimeKeepOnJumpingGoal(this));
        this.targetSelector.addGoal(1, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, 10, true, false, livingEntity -> Math.abs(livingEntity.getY() - this.getY()) <= 4.0));
        this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, IronGolem.class, true));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_SIZE, (Object)1);
    }

    protected void setSize(int i, boolean bl) {
        this.entityData.set(ID_SIZE, (Object)i);
        this.reapplyPosition();
        this.refreshDimensions();
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)(i * i));
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double)(0.2f + 0.1f * (float)i));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((double)i);
        if (bl) {
            this.setHealth(this.getMaxHealth());
        }
        this.xpReward = i;
    }

    public int getSize() {
        return (Integer)this.entityData.get(ID_SIZE);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Size", this.getSize() - 1);
        compoundTag.putBoolean("wasOnGround", this.wasOnGround);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        int i = compoundTag.getInt("Size");
        if (i < 0) {
            i = 0;
        }
        this.setSize(i + 1, false);
        super.readAdditionalSaveData(compoundTag);
        this.wasOnGround = compoundTag.getBoolean("wasOnGround");
    }

    public boolean isTiny() {
        return this.getSize() <= 1;
    }

    protected ParticleOptions getParticleType() {
        return ParticleTypes.ITEM_SLIME;
    }

    protected boolean shouldDespawnInPeaceful() {
        return this.getSize() > 0;
    }

    public void tick() {
        this.squish += (this.targetSquish - this.squish) * 0.5f;
        this.oSquish = this.squish;
        super.tick();
        if (this.onGround && !this.wasOnGround) {
            int i = this.getSize();
            for (int j = 0; j < i * 8; ++j) {
                float f = this.random.nextFloat() * ((float)Math.PI * 2);
                float g = this.random.nextFloat() * 0.5f + 0.5f;
                float h = Mth.sin((float)f) * (float)i * 0.5f * g;
                float k = Mth.cos((float)f) * (float)i * 0.5f * g;
                this.level.addParticle(this.getParticleType(), this.getX() + (double)h, this.getY(), this.getZ() + (double)k, 0.0, 0.0, 0.0);
            }
            this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) / 0.8f);
            this.targetSquish = -0.5f;
        } else if (!this.onGround && this.wasOnGround) {
            this.targetSquish = 1.0f;
        }
        this.wasOnGround = this.onGround;
        this.decreaseSquish();
    }

    protected void decreaseSquish() {
        this.targetSquish *= 0.6f;
    }

    protected int getJumpDelay() {
        return this.random.nextInt(20) + 10;
    }

    public void refreshDimensions() {
        double d = this.getX();
        double e = this.getY();
        double f = this.getZ();
        super.refreshDimensions();
        this.setPos(d, e, f);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (ID_SIZE.equals(entityDataAccessor)) {
            this.refreshDimensions();
            this.yRot = this.yHeadRot;
            this.yBodyRot = this.yHeadRot;
            if (this.isInWater() && this.random.nextInt(20) == 0) {
                this.doWaterSplashEffect();
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    public EntityType<? extends Slime> getType() {
        return super.getType();
    }

    public void remove() {
        int i = this.getSize();
        if (!this.level.isClientSide && i > 1 && this.isDeadOrDying()) {
            Component component = this.getCustomName();
            boolean bl = this.isNoAi();
            float f = (float)i / 4.0f;
            int j = i / 2;
            int k = 2 + this.random.nextInt(3);
            for (int l = 0; l < k; ++l) {
                float g = ((float)(l % 2) - 0.5f) * f;
                float h = ((float)(l / 2) - 0.5f) * f;
                Slime slime = (Slime)this.getType().create(this.level);
                if (this.isPersistenceRequired()) {
                    slime.setPersistenceRequired();
                }
                slime.setCustomName(component);
                slime.setNoAi(bl);
                slime.setInvulnerable(this.isInvulnerable());
                slime.setSize(j, true);
                slime.moveTo(this.getX() + (double)g, this.getY() + 0.5, this.getZ() + (double)h, this.random.nextFloat() * 360.0f, 0.0f);
                this.level.addFreshEntity((Entity)slime);
            }
        }
        super.remove();
    }

    public void push(Entity entity) {
        super.push(entity);
        if (entity instanceof IronGolem && this.isDealsDamage()) {
            this.dealDamage((LivingEntity)entity);
        }
    }

    public void playerTouch(Player player) {
        if (this.isDealsDamage()) {
            this.dealDamage((LivingEntity)player);
        }
    }

    protected void dealDamage(LivingEntity livingEntity) {
        if (this.isAlive()) {
            int i = this.getSize();
            if (this.distanceToSqr((Entity)livingEntity) < 0.6 * (double)i * (0.6 * (double)i) && this.canSee((Entity)livingEntity) && livingEntity.hurt(DamageSource.mobAttack((LivingEntity)this), this.getAttackDamage())) {
                this.playSound(SoundEvents.SLIME_ATTACK, 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
                this.doEnchantDamageEffects((LivingEntity)this, (Entity)livingEntity);
            }
        }
    }

    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return 0.625f * entityDimensions.height;
    }

    protected boolean isDealsDamage() {
        return !this.isTiny() && this.isEffectiveAi();
    }

    protected float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.isTiny()) {
            return SoundEvents.SLIME_HURT_SMALL;
        }
        return SoundEvents.SLIME_HURT;
    }

    protected SoundEvent getDeathSound() {
        if (this.isTiny()) {
            return SoundEvents.SLIME_DEATH_SMALL;
        }
        return SoundEvents.SLIME_DEATH;
    }

    protected SoundEvent getSquishSound() {
        if (this.isTiny()) {
            return SoundEvents.SLIME_SQUISH_SMALL;
        }
        return SoundEvents.SLIME_SQUISH;
    }

    protected ResourceLocation getDefaultLootTable() {
        return this.getSize() == 1 ? this.getType().getDefaultLootTable() : BuiltInLootTables.EMPTY;
    }

    public static boolean checkSlimeSpawnRules(EntityType<Slime> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, Random random) {
        if (levelAccessor.getDifficulty() != Difficulty.PEACEFUL) {
            boolean bl;
            if (Objects.equals(levelAccessor.getBiomeName(blockPos), Optional.of(Biomes.SWAMP)) && blockPos.getY() > 50 && blockPos.getY() < 70 && random.nextFloat() < 0.5f && random.nextFloat() < levelAccessor.getMoonBrightness() && levelAccessor.getMaxLocalRawBrightness(blockPos) <= random.nextInt(8)) {
                return Slime.checkMobSpawnRules(entityType, (LevelAccessor)levelAccessor, (MobSpawnType)mobSpawnType, (BlockPos)blockPos, (Random)random);
            }
            if (!(levelAccessor instanceof WorldGenLevel)) {
                return false;
            }
            ChunkPos chunkPos = new ChunkPos(blockPos);
            boolean bl2 = bl = WorldgenRandom.seedSlimeChunk((int)chunkPos.x, (int)chunkPos.z, (long)((WorldGenLevel)levelAccessor).getSeed(), (long)987234911L).nextInt(10) == 0;
            if (random.nextInt(10) == 0 && bl && blockPos.getY() < 40) {
                return Slime.checkMobSpawnRules(entityType, (LevelAccessor)levelAccessor, (MobSpawnType)mobSpawnType, (BlockPos)blockPos, (Random)random);
            }
        }
        return false;
    }

    protected float getSoundVolume() {
        return 0.4f * (float)this.getSize();
    }

    public int getMaxHeadXRot() {
        return 0;
    }

    protected boolean doPlayJumpSound() {
        return this.getSize() > 0;
    }

    protected void jumpFromGround() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, this.getJumpPower(), vec3.z);
        this.hasImpulse = true;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        int i = this.random.nextInt(3);
        if (i < 2 && this.random.nextFloat() < 0.5f * difficultyInstance.getSpecialMultiplier()) {
            ++i;
        }
        int j = 1 << i;
        this.setSize(j, true);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    private float getSoundPitch() {
        float f = this.isTiny() ? 1.4f : 0.8f;
        return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) * f;
    }

    protected SoundEvent getJumpSound() {
        return this.isTiny() ? SoundEvents.SLIME_JUMP_SMALL : SoundEvents.SLIME_JUMP;
    }

    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(0.255f * (float)this.getSize());
    }

    static /* synthetic */ float method_24352(Slime slime) {
        return slime.getSoundPitch();
    }

    static /* synthetic */ boolean method_24842(Slime slime) {
        return slime.onGround;
    }
}
