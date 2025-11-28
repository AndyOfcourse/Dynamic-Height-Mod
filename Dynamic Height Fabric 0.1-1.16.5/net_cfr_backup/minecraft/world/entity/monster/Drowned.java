/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.tags.Tag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.Difficulty
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.MoverType
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.SpawnGroupData
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.RandomStrollGoal
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.ai.navigation.GroundPathNavigation
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation
 *  net.minecraft.world.entity.animal.IronGolem
 *  net.minecraft.world.entity.animal.Turtle
 *  net.minecraft.world.entity.monster.Drowned$DrownedAttackGoal
 *  net.minecraft.world.entity.monster.Drowned$DrownedGoToBeachGoal
 *  net.minecraft.world.entity.monster.Drowned$DrownedGoToWaterGoal
 *  net.minecraft.world.entity.monster.Drowned$DrownedMoveControl
 *  net.minecraft.world.entity.monster.Drowned$DrownedSwimUpGoal
 *  net.minecraft.world.entity.monster.Drowned$DrownedTridentAttackGoal
 *  net.minecraft.world.entity.monster.RangedAttackMob
 *  net.minecraft.world.entity.monster.Zombie
 *  net.minecraft.world.entity.monster.ZombifiedPiglin
 *  net.minecraft.world.entity.npc.AbstractVillager
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.ThrownTrident
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.biome.Biomes
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.level.pathfinder.Path
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.world.entity.monster;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Drowned
extends Zombie
implements RangedAttackMob {
    private boolean searchingForLand;
    protected final WaterBoundPathNavigation waterNavigation;
    protected final GroundPathNavigation groundNavigation;

    public Drowned(EntityType<? extends Drowned> entityType, Level level) {
        super(entityType, level);
        this.maxUpStep = 1.0f;
        this.moveControl = new DrownedMoveControl(this);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
        this.waterNavigation = new WaterBoundPathNavigation((Mob)this, level);
        this.groundNavigation = new GroundPathNavigation((Mob)this, level);
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(1, (Goal)new DrownedGoToWaterGoal((PathfinderMob)this, 1.0));
        this.goalSelector.addGoal(2, (Goal)new DrownedTridentAttackGoal((RangedAttackMob)this, 1.0, 40, 10.0f));
        this.goalSelector.addGoal(2, (Goal)new DrownedAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(5, (Goal)new DrownedGoToBeachGoal(this, 1.0));
        this.goalSelector.addGoal(6, (Goal)new DrownedSwimUpGoal(this, 1.0, this.level.getSeaLevel()));
        this.goalSelector.addGoal(7, (Goal)new RandomStrollGoal((PathfinderMob)this, 1.0));
        this.targetSelector.addGoal(1, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[]{Drowned.class}).setAlertOthers(new Class[]{ZombifiedPiglin.class}));
        this.targetSelector.addGoal(2, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, 10, true, false, this::okTarget));
        this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, IronGolem.class, true));
        this.targetSelector.addGoal(5, (Goal)new NearestAttackableTargetGoal((Mob)this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        spawnGroupData = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
        if (this.getItemBySlot(EquipmentSlot.OFFHAND).isEmpty() && this.random.nextFloat() < 0.03f) {
            this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack((ItemLike)Items.NAUTILUS_SHELL));
            this.handDropChances[EquipmentSlot.OFFHAND.getIndex()] = 2.0f;
        }
        return spawnGroupData;
    }

    public static boolean checkDrownedSpawnRules(EntityType<Drowned> entityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, Random random) {
        boolean bl;
        Optional optional = serverLevelAccessor.getBiomeName(blockPos);
        boolean bl2 = bl = serverLevelAccessor.getDifficulty() != Difficulty.PEACEFUL && Drowned.isDarkEnoughToSpawn((ServerLevelAccessor)serverLevelAccessor, (BlockPos)blockPos, (Random)random) && (mobSpawnType == MobSpawnType.SPAWNER || serverLevelAccessor.getFluidState(blockPos).is((Tag)FluidTags.WATER));
        if (Objects.equals(optional, Optional.of(Biomes.RIVER)) || Objects.equals(optional, Optional.of(Biomes.FROZEN_RIVER))) {
            return random.nextInt(15) == 0 && bl;
        }
        return random.nextInt(40) == 0 && Drowned.isDeepEnoughToSpawn((LevelAccessor)serverLevelAccessor, blockPos) && bl;
    }

    private static boolean isDeepEnoughToSpawn(LevelAccessor levelAccessor, BlockPos blockPos) {
        return blockPos.getY() < levelAccessor.getSeaLevel() - 5;
    }

    protected boolean supportsBreakDoorGoal() {
        return false;
    }

    protected SoundEvent getAmbientSound() {
        if (this.isInWater()) {
            return SoundEvents.DROWNED_AMBIENT_WATER;
        }
        return SoundEvents.DROWNED_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.isInWater()) {
            return SoundEvents.DROWNED_HURT_WATER;
        }
        return SoundEvents.DROWNED_HURT;
    }

    protected SoundEvent getDeathSound() {
        if (this.isInWater()) {
            return SoundEvents.DROWNED_DEATH_WATER;
        }
        return SoundEvents.DROWNED_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.DROWNED_STEP;
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.DROWNED_SWIM;
    }

    protected ItemStack getSkull() {
        return ItemStack.EMPTY;
    }

    protected void populateDefaultEquipmentSlots(DifficultyInstance difficultyInstance) {
        if ((double)this.random.nextFloat() > 0.9) {
            int i = this.random.nextInt(16);
            if (i < 10) {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.TRIDENT));
            } else {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.FISHING_ROD));
            }
        }
    }

    protected boolean canReplaceCurrentItem(ItemStack itemStack, ItemStack itemStack2) {
        if (itemStack2.getItem() == Items.NAUTILUS_SHELL) {
            return false;
        }
        if (itemStack2.getItem() == Items.TRIDENT) {
            if (itemStack.getItem() == Items.TRIDENT) {
                return itemStack.getDamageValue() < itemStack2.getDamageValue();
            }
            return false;
        }
        if (itemStack.getItem() == Items.TRIDENT) {
            return true;
        }
        return super.canReplaceCurrentItem(itemStack, itemStack2);
    }

    protected boolean convertsInWater() {
        return false;
    }

    public boolean checkSpawnObstruction(LevelReader levelReader) {
        return levelReader.isUnobstructed((Entity)this);
    }

    public boolean okTarget(@Nullable LivingEntity livingEntity) {
        if (livingEntity != null) {
            return !this.level.isDay() || livingEntity.isInWater();
        }
        return false;
    }

    public boolean isPushedByFluid() {
        return !this.isSwimming();
    }

    private boolean wantsToSwim() {
        if (this.searchingForLand) {
            return true;
        }
        LivingEntity livingEntity = this.getTarget();
        return livingEntity != null && livingEntity.isInWater();
    }

    public void travel(Vec3 vec3) {
        if (this.isEffectiveAi() && this.isInWater() && this.wantsToSwim()) {
            this.moveRelative(0.01f, vec3);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
        } else {
            super.travel(vec3);
        }
    }

    public void updateSwimming() {
        if (!this.level.isClientSide) {
            if (this.isEffectiveAi() && this.isInWater() && this.wantsToSwim()) {
                this.navigation = this.waterNavigation;
                this.setSwimming(true);
            } else {
                this.navigation = this.groundNavigation;
                this.setSwimming(false);
            }
        }
    }

    protected boolean closeToNextPos() {
        double d;
        BlockPos blockPos;
        Path path = this.getNavigation().getPath();
        return path != null && (blockPos = path.getTarget()) != null && (d = this.distanceToSqr(blockPos.getX(), blockPos.getY(), blockPos.getZ())) < 4.0;
    }

    public void performRangedAttack(LivingEntity livingEntity, float f) {
        ThrownTrident thrownTrident = new ThrownTrident(this.level, (LivingEntity)this, new ItemStack((ItemLike)Items.TRIDENT));
        double d = livingEntity.getX() - this.getX();
        double e = livingEntity.getY(0.3333333333333333) - thrownTrident.getY();
        double g = livingEntity.getZ() - this.getZ();
        double h = Mth.sqrt((double)(d * d + g * g));
        thrownTrident.shoot(d, e + h * (double)0.2f, g, 1.6f, (float)(14 - this.level.getDifficulty().getId() * 4));
        this.playSound(SoundEvents.DROWNED_SHOOT, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
        this.level.addFreshEntity((Entity)thrownTrident);
    }

    public void setSearchingForLand(boolean bl) {
        this.searchingForLand = bl;
    }

    static /* synthetic */ PathNavigation method_7017(Drowned drowned, PathNavigation pathNavigation) {
        drowned.navigation = pathNavigation;
        return drowned.navigation;
    }

    static /* synthetic */ boolean method_7014(Drowned drowned) {
        return drowned.wantsToSwim();
    }

    static /* synthetic */ boolean method_7019(Drowned drowned) {
        return drowned.searchingForLand;
    }

    static /* synthetic */ boolean method_24841(Drowned drowned) {
        return drowned.onGround;
    }
}
