/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.BlockParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
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
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.NeutralMob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.GolemRandomStrollInVillageGoal
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.MeleeAttackGoal
 *  net.minecraft.world.entity.ai.goal.MoveBackToVillageGoal
 *  net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal
 *  net.minecraft.world.entity.ai.goal.OfferFlowerGoal
 *  net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
 *  net.minecraft.world.entity.ai.goal.target.DefendVillageTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal
 *  net.minecraft.world.entity.animal.AbstractGolem
 *  net.minecraft.world.entity.animal.IronGolem$Crackiness
 *  net.minecraft.world.entity.monster.Creeper
 *  net.minecraft.world.entity.monster.Enemy
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.NaturalSpawner
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.world.entity.animal;

import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GolemRandomStrollInVillageGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveBackToVillageGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.OfferFlowerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.DefendVillageTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class IronGolem
extends AbstractGolem
implements NeutralMob {
    protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(IronGolem.class, (EntityDataSerializer)EntityDataSerializers.BYTE);
    private int attackAnimationTick;
    private int offerFlowerTick;
    private static final IntRange PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds((int)20, (int)39);
    private int remainingPersistentAngerTime;
    private UUID persistentAngerTarget;

    public IronGolem(EntityType<? extends IronGolem> entityType, Level level) {
        super(entityType, level);
        this.maxUpStep = 1.0f;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, (Goal)new MeleeAttackGoal((PathfinderMob)this, 1.0, true));
        this.goalSelector.addGoal(2, (Goal)new MoveTowardsTargetGoal((PathfinderMob)this, 0.9, 32.0f));
        this.goalSelector.addGoal(2, (Goal)new MoveBackToVillageGoal((PathfinderMob)this, 0.6, false));
        this.goalSelector.addGoal(4, (Goal)new GolemRandomStrollInVillageGoal((PathfinderMob)this, 0.6));
        this.goalSelector.addGoal(5, (Goal)new OfferFlowerGoal(this));
        this.goalSelector.addGoal(7, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 6.0f));
        this.goalSelector.addGoal(8, (Goal)new RandomLookAroundGoal((Mob)this));
        this.targetSelector.addGoal(1, (Goal)new DefendVillageTargetGoal(this));
        this.targetSelector.addGoal(2, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]));
        this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, 10, true, false, arg_0 -> ((IronGolem)this).isAngryAt(arg_0)));
        this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, Mob.class, 5, false, false, livingEntity -> livingEntity instanceof Enemy && !(livingEntity instanceof Creeper)));
        this.targetSelector.addGoal(4, (Goal)new ResetUniversalAngerTargetGoal((Mob)this, false));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (Object)0);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 100.0).add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.KNOCKBACK_RESISTANCE, 1.0).add(Attributes.ATTACK_DAMAGE, 15.0);
    }

    protected int decreaseAirSupply(int i) {
        return i;
    }

    protected void doPush(Entity entity) {
        if (entity instanceof Enemy && !(entity instanceof Creeper) && this.getRandom().nextInt(20) == 0) {
            this.setTarget((LivingEntity)entity);
        }
        super.doPush(entity);
    }

    public void aiStep() {
        int k;
        int j;
        int i;
        BlockState blockState;
        super.aiStep();
        if (this.attackAnimationTick > 0) {
            --this.attackAnimationTick;
        }
        if (this.offerFlowerTick > 0) {
            --this.offerFlowerTick;
        }
        if (IronGolem.getHorizontalDistanceSqr((Vec3)this.getDeltaMovement()) > 2.500000277905201E-7 && this.random.nextInt(5) == 0 && !(blockState = this.level.getBlockState(new BlockPos(i = Mth.floor((double)this.getX()), j = Mth.floor((double)(this.getY() - (double)0.2f)), k = Mth.floor((double)this.getZ())))).isAir()) {
            this.level.addParticle((ParticleOptions)new BlockParticleOption(ParticleTypes.BLOCK, blockState), this.getX() + ((double)this.random.nextFloat() - 0.5) * (double)this.getBbWidth(), this.getY() + 0.1, this.getZ() + ((double)this.random.nextFloat() - 0.5) * (double)this.getBbWidth(), 4.0 * ((double)this.random.nextFloat() - 0.5), 0.5, ((double)this.random.nextFloat() - 0.5) * 4.0);
        }
        if (!this.level.isClientSide) {
            this.updatePersistentAnger((ServerLevel)this.level, true);
        }
    }

    public boolean canAttackType(EntityType<?> entityType) {
        if (this.isPlayerCreated() && entityType == EntityType.PLAYER) {
            return false;
        }
        if (entityType == EntityType.CREEPER) {
            return false;
        }
        return super.canAttackType(entityType);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("PlayerCreated", this.isPlayerCreated());
        this.addPersistentAngerSaveData(compoundTag);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setPlayerCreated(compoundTag.getBoolean("PlayerCreated"));
        this.readPersistentAngerSaveData((ServerLevel)this.level, compoundTag);
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

    private float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    public boolean doHurtTarget(Entity entity) {
        this.attackAnimationTick = 10;
        this.level.broadcastEntityEvent((Entity)this, (byte)4);
        float f = this.getAttackDamage();
        float g = (int)f > 0 ? f / 2.0f + (float)this.random.nextInt((int)f) : f;
        boolean bl = entity.hurt(DamageSource.mobAttack((LivingEntity)this), g);
        if (bl) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, (double)0.4f, 0.0));
            this.doEnchantDamageEffects((LivingEntity)this, entity);
        }
        this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0f, 1.0f);
        return bl;
    }

    public boolean hurt(DamageSource damageSource, float f) {
        Crackiness crackiness = this.getCrackiness();
        boolean bl = super.hurt(damageSource, f);
        if (bl && this.getCrackiness() != crackiness) {
            this.playSound(SoundEvents.IRON_GOLEM_DAMAGE, 1.0f, 1.0f);
        }
        return bl;
    }

    public Crackiness getCrackiness() {
        return Crackiness.byFraction((float)(this.getHealth() / this.getMaxHealth()));
    }

    @Environment(value=EnvType.CLIENT)
    public void handleEntityEvent(byte b) {
        if (b == 4) {
            this.attackAnimationTick = 10;
            this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0f, 1.0f);
        } else if (b == 11) {
            this.offerFlowerTick = 400;
        } else if (b == 34) {
            this.offerFlowerTick = 0;
        } else {
            super.handleEntityEvent(b);
        }
    }

    @Environment(value=EnvType.CLIENT)
    public int getAttackAnimationTick() {
        return this.attackAnimationTick;
    }

    public void offerFlower(boolean bl) {
        if (bl) {
            this.offerFlowerTick = 400;
            this.level.broadcastEntityEvent((Entity)this, (byte)11);
        } else {
            this.offerFlowerTick = 0;
            this.level.broadcastEntityEvent((Entity)this, (byte)34);
        }
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        Item item = itemStack.getItem();
        if (item != Items.IRON_INGOT) {
            return InteractionResult.PASS;
        }
        float f = this.getHealth();
        this.heal(25.0f);
        if (this.getHealth() == f) {
            return InteractionResult.PASS;
        }
        float g = 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.2f;
        this.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0f, g);
        if (!player.abilities.instabuild) {
            itemStack.shrink(1);
        }
        return InteractionResult.sidedSuccess((boolean)this.level.isClientSide);
    }

    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 1.0f, 1.0f);
    }

    @Environment(value=EnvType.CLIENT)
    public int getOfferFlowerTick() {
        return this.offerFlowerTick;
    }

    public boolean isPlayerCreated() {
        return ((Byte)this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void setPlayerCreated(boolean bl) {
        byte b = (Byte)this.entityData.get(DATA_FLAGS_ID);
        if (bl) {
            this.entityData.set(DATA_FLAGS_ID, (Object)((byte)(b | 1)));
        } else {
            this.entityData.set(DATA_FLAGS_ID, (Object)((byte)(b & 0xFFFFFFFE)));
        }
    }

    public void die(DamageSource damageSource) {
        super.die(damageSource);
    }

    public boolean checkSpawnObstruction(LevelReader levelReader) {
        BlockPos blockPos = this.blockPosition();
        BlockPos blockPos2 = blockPos.below();
        BlockState blockState = levelReader.getBlockState(blockPos2);
        if (blockState.entityCanStandOn((BlockGetter)levelReader, blockPos2, (Entity)this)) {
            for (int i = 1; i < 3; ++i) {
                BlockState blockState2;
                BlockPos blockPos3 = blockPos.above(i);
                if (NaturalSpawner.isValidEmptySpawnBlock((BlockGetter)levelReader, (BlockPos)blockPos3, (BlockState)(blockState2 = levelReader.getBlockState(blockPos3)), (FluidState)blockState2.getFluidState(), (EntityType)EntityType.IRON_GOLEM)) continue;
                return false;
            }
            return NaturalSpawner.isValidEmptySpawnBlock((BlockGetter)levelReader, (BlockPos)blockPos, (BlockState)levelReader.getBlockState(blockPos), (FluidState)Fluids.EMPTY.defaultFluidState(), (EntityType)EntityType.IRON_GOLEM) && levelReader.isUnobstructed((Entity)this);
        }
        return false;
    }

    @Environment(value=EnvType.CLIENT)
    public Vec3 getLeashOffset() {
        return new Vec3(0.0, (double)(0.875f * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4f));
    }
}
