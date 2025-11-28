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
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.util.IntRange
 *  net.minecraft.util.Mth
 *  net.minecraft.util.TimeUtil
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.AgableMob
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.NeutralMob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.BegGoal
 *  net.minecraft.world.entity.ai.goal.BreedGoal
 *  net.minecraft.world.entity.ai.goal.FloatGoal
 *  net.minecraft.world.entity.ai.goal.FollowOwnerGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.LeapAtTargetGoal
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.MeleeAttackGoal
 *  net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
 *  net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal
 *  net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.entity.animal.Turtle
 *  net.minecraft.world.entity.animal.Wolf$WolfAvoidEntityGoal
 *  net.minecraft.world.entity.animal.horse.AbstractHorse
 *  net.minecraft.world.entity.animal.horse.Llama
 *  net.minecraft.world.entity.monster.AbstractSkeleton
 *  net.minecraft.world.entity.monster.Creeper
 *  net.minecraft.world.entity.monster.Ghast
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.AbstractArrow
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.DyeItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.world.entity.animal;

import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.IntRange;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BegGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Wolf
extends TamableAnimal
implements NeutralMob {
    private static final EntityDataAccessor<Boolean> DATA_INTERESTED_ID = SynchedEntityData.defineId(Wolf.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_COLLAR_COLOR = SynchedEntityData.defineId(Wolf.class, (EntityDataSerializer)EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(Wolf.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final Predicate<LivingEntity> PREY_SELECTOR = livingEntity -> {
        EntityType entityType = livingEntity.getType();
        return entityType == EntityType.SHEEP || entityType == EntityType.RABBIT || entityType == EntityType.FOX;
    };
    private float interestedAngle;
    private float interestedAngleO;
    private boolean isWet;
    private boolean isShaking;
    private float shakeAnim;
    private float shakeAnimO;
    private static final IntRange PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds((int)20, (int)39);
    private UUID persistentAngerTarget;

    public Wolf(EntityType<? extends Wolf> entityType, Level level) {
        super(entityType, level);
        this.setTame(false);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, (Goal)new FloatGoal((Mob)this));
        this.goalSelector.addGoal(2, (Goal)new SitWhenOrderedToGoal((TamableAnimal)this));
        this.goalSelector.addGoal(3, (Goal)new WolfAvoidEntityGoal(this, this, Llama.class, 24.0f, 1.5, 1.5));
        this.goalSelector.addGoal(4, (Goal)new LeapAtTargetGoal((Mob)this, 0.4f));
        this.goalSelector.addGoal(5, (Goal)new MeleeAttackGoal((PathfinderMob)this, 1.0, true));
        this.goalSelector.addGoal(6, (Goal)new FollowOwnerGoal((TamableAnimal)this, 1.0, 10.0f, 2.0f, false));
        this.goalSelector.addGoal(7, (Goal)new BreedGoal((Animal)this, 1.0));
        this.goalSelector.addGoal(8, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 1.0));
        this.goalSelector.addGoal(9, (Goal)new BegGoal(this, 8.0f));
        this.goalSelector.addGoal(10, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0f));
        this.goalSelector.addGoal(10, (Goal)new RandomLookAroundGoal((Mob)this));
        this.targetSelector.addGoal(1, (Goal)new OwnerHurtByTargetGoal((TamableAnimal)this));
        this.targetSelector.addGoal(2, (Goal)new OwnerHurtTargetGoal((TamableAnimal)this));
        this.targetSelector.addGoal(3, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(4, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, 10, true, false, arg_0 -> ((Wolf)this).isAngryAt(arg_0)));
        this.targetSelector.addGoal(5, (Goal)new NonTameRandomTargetGoal((TamableAnimal)this, Animal.class, false, PREY_SELECTOR));
        this.targetSelector.addGoal(6, (Goal)new NonTameRandomTargetGoal((TamableAnimal)this, Turtle.class, false, Turtle.BABY_ON_LAND_SELECTOR));
        this.targetSelector.addGoal(7, (Goal)new NearestAttackableTargetGoal((Mob)this, AbstractSkeleton.class, false));
        this.targetSelector.addGoal(8, (Goal)new ResetUniversalAngerTargetGoal((Mob)this, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, (double)0.3f).add(Attributes.MAX_HEALTH, 8.0).add(Attributes.ATTACK_DAMAGE, 2.0);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_INTERESTED_ID, (Object)false);
        this.entityData.define(DATA_COLLAR_COLOR, (Object)DyeColor.RED.getId());
        this.entityData.define(DATA_REMAINING_ANGER_TIME, (Object)0);
    }

    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.WOLF_STEP, 0.15f, 1.0f);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putByte("CollarColor", (byte)this.getCollarColor().getId());
        this.addPersistentAngerSaveData(compoundTag);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.contains("CollarColor", 99)) {
            this.setCollarColor(DyeColor.byId((int)compoundTag.getInt("CollarColor")));
        }
        this.readPersistentAngerSaveData((ServerLevel)this.level, compoundTag);
    }

    protected SoundEvent getAmbientSound() {
        if (this.isAngry()) {
            return SoundEvents.WOLF_GROWL;
        }
        if (this.random.nextInt(3) == 0) {
            if (this.isTame() && this.getHealth() < 10.0f) {
                return SoundEvents.WOLF_WHINE;
            }
            return SoundEvents.WOLF_PANT;
        }
        return SoundEvents.WOLF_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.WOLF_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.WOLF_DEATH;
    }

    protected float getSoundVolume() {
        return 0.4f;
    }

    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide && this.isWet && !this.isShaking && !this.isPathFinding() && this.onGround) {
            this.isShaking = true;
            this.shakeAnim = 0.0f;
            this.shakeAnimO = 0.0f;
            this.level.broadcastEntityEvent((Entity)this, (byte)8);
        }
        if (!this.level.isClientSide) {
            this.updatePersistentAnger((ServerLevel)this.level, true);
        }
    }

    public void tick() {
        super.tick();
        if (!this.isAlive()) {
            return;
        }
        this.interestedAngleO = this.interestedAngle;
        this.interestedAngle = this.isInterested() ? (this.interestedAngle += (1.0f - this.interestedAngle) * 0.4f) : (this.interestedAngle += (0.0f - this.interestedAngle) * 0.4f);
        if (this.isInWaterRainOrBubble()) {
            this.isWet = true;
            if (this.isShaking && !this.level.isClientSide) {
                this.level.broadcastEntityEvent((Entity)this, (byte)56);
                this.cancelShake();
            }
        } else if ((this.isWet || this.isShaking) && this.isShaking) {
            if (this.shakeAnim == 0.0f) {
                this.playSound(SoundEvents.WOLF_SHAKE, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
            }
            this.shakeAnimO = this.shakeAnim;
            this.shakeAnim += 0.05f;
            if (this.shakeAnimO >= 2.0f) {
                this.isWet = false;
                this.isShaking = false;
                this.shakeAnimO = 0.0f;
                this.shakeAnim = 0.0f;
            }
            if (this.shakeAnim > 0.4f) {
                float f = (float)this.getY();
                int i = (int)(Mth.sin((float)((this.shakeAnim - 0.4f) * (float)Math.PI)) * 7.0f);
                Vec3 vec3 = this.getDeltaMovement();
                for (int j = 0; j < i; ++j) {
                    float g = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
                    float h = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
                    this.level.addParticle((ParticleOptions)ParticleTypes.SPLASH, this.getX() + (double)g, (double)(f + 0.8f), this.getZ() + (double)h, vec3.x, vec3.y, vec3.z);
                }
            }
        }
    }

    private void cancelShake() {
        this.isShaking = false;
        this.shakeAnim = 0.0f;
        this.shakeAnimO = 0.0f;
    }

    public void die(DamageSource damageSource) {
        this.isWet = false;
        this.isShaking = false;
        this.shakeAnimO = 0.0f;
        this.shakeAnim = 0.0f;
        super.die(damageSource);
    }

    @Environment(value=EnvType.CLIENT)
    public boolean isWet() {
        return this.isWet;
    }

    @Environment(value=EnvType.CLIENT)
    public float getWetShade(float f) {
        return Math.min(0.5f + Mth.lerp((float)f, (float)this.shakeAnimO, (float)this.shakeAnim) / 2.0f * 0.5f, 1.0f);
    }

    @Environment(value=EnvType.CLIENT)
    public float getBodyRollAngle(float f, float g) {
        float h = (Mth.lerp((float)f, (float)this.shakeAnimO, (float)this.shakeAnim) + g) / 1.8f;
        if (h < 0.0f) {
            h = 0.0f;
        } else if (h > 1.0f) {
            h = 1.0f;
        }
        return Mth.sin((float)(h * (float)Math.PI)) * Mth.sin((float)(h * (float)Math.PI * 11.0f)) * 0.15f * (float)Math.PI;
    }

    @Environment(value=EnvType.CLIENT)
    public float getHeadRollAngle(float f) {
        return Mth.lerp((float)f, (float)this.interestedAngleO, (float)this.interestedAngle) * 0.15f * (float)Math.PI;
    }

    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return entityDimensions.height * 0.8f;
    }

    public int getMaxHeadXRot() {
        if (this.isInSittingPose()) {
            return 20;
        }
        return super.getMaxHeadXRot();
    }

    public boolean hurt(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        }
        Entity entity = damageSource.getEntity();
        this.setOrderedToSit(false);
        if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
            f = (f + 1.0f) / 2.0f;
        }
        return super.hurt(damageSource, f);
    }

    public boolean doHurtTarget(Entity entity) {
        boolean bl = entity.hurt(DamageSource.mobAttack((LivingEntity)this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (bl) {
            this.doEnchantDamageEffects((LivingEntity)this, entity);
        }
        return bl;
    }

    public void setTame(boolean bl) {
        super.setTame(bl);
        if (bl) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0);
            this.setHealth(20.0f);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0);
        }
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        Item item = itemStack.getItem();
        if (this.level.isClientSide) {
            boolean bl = this.isOwnedBy((LivingEntity)player) || this.isTame() || item == Items.BONE && !this.isTame() && !this.isAngry();
            return bl ? InteractionResult.CONSUME : InteractionResult.PASS;
        }
        if (this.isTame()) {
            if (this.isFood(itemStack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.abilities.instabuild) {
                    itemStack.shrink(1);
                }
                this.heal(item.getFoodProperties().getNutrition());
                return InteractionResult.SUCCESS;
            }
            if (item instanceof DyeItem) {
                DyeColor dyeColor = ((DyeItem)item).getDyeColor();
                if (dyeColor == this.getCollarColor()) return super.mobInteract(player, interactionHand);
                this.setCollarColor(dyeColor);
                if (player.abilities.instabuild) return InteractionResult.SUCCESS;
                itemStack.shrink(1);
                return InteractionResult.SUCCESS;
            }
            InteractionResult interactionResult = super.mobInteract(player, interactionHand);
            if (interactionResult.consumesAction() && !this.isBaby() || !this.isOwnedBy((LivingEntity)player)) return interactionResult;
            this.setOrderedToSit(!this.isOrderedToSit());
            this.jumping = false;
            this.navigation.stop();
            this.setTarget(null);
            return InteractionResult.SUCCESS;
        }
        if (item != Items.BONE || this.isAngry()) return super.mobInteract(player, interactionHand);
        if (!player.abilities.instabuild) {
            itemStack.shrink(1);
        }
        if (this.random.nextInt(3) == 0) {
            this.tame(player);
            this.navigation.stop();
            this.setTarget(null);
            this.setOrderedToSit(true);
            this.level.broadcastEntityEvent((Entity)this, (byte)7);
            return InteractionResult.SUCCESS;
        } else {
            this.level.broadcastEntityEvent((Entity)this, (byte)6);
        }
        return InteractionResult.SUCCESS;
    }

    @Environment(value=EnvType.CLIENT)
    public void handleEntityEvent(byte b) {
        if (b == 8) {
            this.isShaking = true;
            this.shakeAnim = 0.0f;
            this.shakeAnimO = 0.0f;
        } else if (b == 56) {
            this.cancelShake();
        } else {
            super.handleEntityEvent(b);
        }
    }

    @Environment(value=EnvType.CLIENT)
    public float getTailAngle() {
        if (this.isAngry()) {
            return 1.5393804f;
        }
        if (this.isTame()) {
            return (0.55f - (this.getMaxHealth() - this.getHealth()) * 0.02f) * (float)Math.PI;
        }
        return 0.62831855f;
    }

    public boolean isFood(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return item.isEdible() && item.getFoodProperties().isMeat();
    }

    public int getMaxSpawnClusterSize() {
        return 8;
    }

    public int getRemainingPersistentAngerTime() {
        return (Integer)this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int i) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, (Object)i);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.randomValue(this.random));
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@Nullable UUID uUID) {
        this.persistentAngerTarget = uUID;
    }

    public DyeColor getCollarColor() {
        return DyeColor.byId((int)((Integer)this.entityData.get(DATA_COLLAR_COLOR)));
    }

    public void setCollarColor(DyeColor dyeColor) {
        this.entityData.set(DATA_COLLAR_COLOR, (Object)dyeColor.getId());
    }

    public Wolf getBreedOffspring(ServerLevel serverLevel, AgableMob agableMob) {
        Wolf wolf = (Wolf)EntityType.WOLF.create((Level)serverLevel);
        UUID uUID = this.getOwnerUUID();
        if (uUID != null) {
            wolf.setOwnerUUID(uUID);
            wolf.setTame(true);
        }
        return wolf;
    }

    public void setIsInterested(boolean bl) {
        this.entityData.set(DATA_INTERESTED_ID, (Object)bl);
    }

    public boolean canMate(Animal animal) {
        if (animal == this) {
            return false;
        }
        if (!this.isTame()) {
            return false;
        }
        if (!(animal instanceof Wolf)) {
            return false;
        }
        Wolf wolf = (Wolf)animal;
        if (!wolf.isTame()) {
            return false;
        }
        if (wolf.isInSittingPose()) {
            return false;
        }
        return this.isInLove() && wolf.isInLove();
    }

    public boolean isInterested() {
        return (Boolean)this.entityData.get(DATA_INTERESTED_ID);
    }

    public boolean wantsToAttack(LivingEntity livingEntity, LivingEntity livingEntity2) {
        if (livingEntity instanceof Creeper || livingEntity instanceof Ghast) {
            return false;
        }
        if (livingEntity instanceof Wolf) {
            Wolf wolf = (Wolf)livingEntity;
            return !wolf.isTame() || wolf.getOwner() != livingEntity2;
        }
        if (livingEntity instanceof Player && livingEntity2 instanceof Player && !((Player)livingEntity2).canHarmPlayer((Player)livingEntity)) {
            return false;
        }
        if (livingEntity instanceof AbstractHorse && ((AbstractHorse)livingEntity).isTamed()) {
            return false;
        }
        return !(livingEntity instanceof TamableAnimal) || !((TamableAnimal)livingEntity).isTame();
    }

    public boolean canBeLeashed(Player player) {
        return !this.isAngry() && super.canBeLeashed(player);
    }

    @Environment(value=EnvType.CLIENT)
    public Vec3 getLeashOffset() {
        return new Vec3(0.0, (double)(0.6f * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4f));
    }

    public /* synthetic */ AgableMob getBreedOffspring(ServerLevel serverLevel, AgableMob agableMob) {
        return this.getBreedOffspring(serverLevel, agableMob);
    }

    static /* synthetic */ Random method_6716(Wolf wolf) {
        return wolf.random;
    }
}
