/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.advancements.CriteriaTriggers
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntitySelector
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.MoverType
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.AvoidEntityGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.PanicGoal
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation
 *  net.minecraft.world.entity.animal.AbstractFish$FishMoveControl
 *  net.minecraft.world.entity.animal.AbstractFish$FishSwimGoal
 *  net.minecraft.world.entity.animal.WaterAnimal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 */
package net.minecraft.world.entity.animal;

import java.util.Random;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractFish
extends WaterAnimal {
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(AbstractFish.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);

    public AbstractFish(EntityType<? extends AbstractFish> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FishMoveControl(this);
    }

    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return entityDimensions.height * 0.65f;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0);
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBucket();
    }

    public static boolean checkFishSpawnRules(EntityType<? extends AbstractFish> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, Random random) {
        return levelAccessor.getBlockState(blockPos).is(Blocks.WATER) && levelAccessor.getBlockState(blockPos.above()).is(Blocks.WATER);
    }

    public boolean removeWhenFarAway(double d) {
        return !this.fromBucket() && !this.hasCustomName();
    }

    public int getMaxSpawnClusterSize() {
        return 8;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BUCKET, (Object)false);
    }

    private boolean fromBucket() {
        return (Boolean)this.entityData.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean bl) {
        this.entityData.set(FROM_BUCKET, (Object)bl);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("FromBucket", this.fromBucket());
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setFromBucket(compoundTag.getBoolean("FromBucket"));
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, (Goal)new PanicGoal((PathfinderMob)this, 1.25));
        this.goalSelector.addGoal(2, (Goal)new AvoidEntityGoal((PathfinderMob)this, Player.class, 8.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(4, (Goal)new FishSwimGoal(this));
    }

    protected PathNavigation createNavigation(Level level) {
        return new WaterBoundPathNavigation((Mob)this, level);
    }

    public void travel(Vec3 vec3) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.01f, vec3);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(vec3);
        }
    }

    public void aiStep() {
        if (!this.isInWater() && this.onGround && this.verticalCollision) {
            this.setDeltaMovement(this.getDeltaMovement().add((double)((this.random.nextFloat() * 2.0f - 1.0f) * 0.05f), (double)0.4f, (double)((this.random.nextFloat() * 2.0f - 1.0f) * 0.05f)));
            this.onGround = false;
            this.hasImpulse = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
        }
        super.aiStep();
    }

    protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.getItem() == Items.WATER_BUCKET && this.isAlive()) {
            this.playSound(SoundEvents.BUCKET_FILL_FISH, 1.0f, 1.0f);
            itemStack.shrink(1);
            ItemStack itemStack2 = this.getBucketItemStack();
            this.saveToBucketTag(itemStack2);
            if (!this.level.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)player, itemStack2);
            }
            if (itemStack.isEmpty()) {
                player.setItemInHand(interactionHand, itemStack2);
            } else if (!player.inventory.add(itemStack2)) {
                player.drop(itemStack2, false);
            }
            this.remove();
            return InteractionResult.sidedSuccess((boolean)this.level.isClientSide);
        }
        return super.mobInteract(player, interactionHand);
    }

    protected void saveToBucketTag(ItemStack itemStack) {
        if (this.hasCustomName()) {
            itemStack.setHoverName(this.getCustomName());
        }
    }

    protected abstract ItemStack getBucketItemStack();

    protected boolean canRandomSwim() {
        return true;
    }

    protected abstract SoundEvent getFlopSound();

    protected SoundEvent getSwimSound() {
        return SoundEvents.FISH_SWIM;
    }

    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
    }
}
