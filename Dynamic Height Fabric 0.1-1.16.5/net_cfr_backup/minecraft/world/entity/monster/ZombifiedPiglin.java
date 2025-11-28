/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.util.IntRange
 *  net.minecraft.util.TimeUtil
 *  net.minecraft.world.Difficulty
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.NeutralMob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.attributes.AttributeInstance
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal
 *  net.minecraft.world.entity.ai.goal.ZombieAttackGoal
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal
 *  net.minecraft.world.entity.monster.Zombie
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.world.entity.monster;

import java.util.Random;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.IntRange;
import net.minecraft.util.TimeUtil;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ZombifiedPiglin
extends Zombie
implements NeutralMob {
    private static final UUID SPEED_MODIFIER_ATTACKING_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
    private static final AttributeModifier SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", 0.05, AttributeModifier.Operation.ADDITION);
    private static final IntRange FIRST_ANGER_SOUND_DELAY = TimeUtil.rangeOfSeconds((int)0, (int)1);
    private int playFirstAngerSoundIn;
    private static final IntRange PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds((int)20, (int)39);
    private int remainingPersistentAngerTime;
    private UUID persistentAngerTarget;
    private static final IntRange ALERT_INTERVAL = TimeUtil.rangeOfSeconds((int)4, (int)6);
    private int ticksUntilNextAlert;

    public ZombifiedPiglin(EntityType<? extends ZombifiedPiglin> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0f);
    }

    public void setPersistentAngerTarget(@Nullable UUID uUID) {
        this.persistentAngerTarget = uUID;
    }

    public double getMyRidingOffset() {
        return this.isBaby() ? -0.05 : -0.45;
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(2, (Goal)new ZombieAttackGoal((Zombie)this, 1.0, false));
        this.goalSelector.addGoal(7, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 1.0));
        this.targetSelector.addGoal(1, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, 10, true, false, arg_0 -> ((ZombifiedPiglin)this).isAngryAt(arg_0)));
        this.targetSelector.addGoal(3, (Goal)new ResetUniversalAngerTargetGoal((Mob)this, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes().add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0).add(Attributes.MOVEMENT_SPEED, (double)0.23f).add(Attributes.ATTACK_DAMAGE, 5.0);
    }

    protected boolean convertsInWater() {
        return false;
    }

    protected void customServerAiStep() {
        AttributeInstance attributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (this.isAngry()) {
            if (!this.isBaby() && !attributeInstance.hasModifier(SPEED_MODIFIER_ATTACKING)) {
                attributeInstance.addTransientModifier(SPEED_MODIFIER_ATTACKING);
            }
            this.maybePlayFirstAngerSound();
        } else if (attributeInstance.hasModifier(SPEED_MODIFIER_ATTACKING)) {
            attributeInstance.removeModifier(SPEED_MODIFIER_ATTACKING);
        }
        this.updatePersistentAnger((ServerLevel)this.level, true);
        if (this.getTarget() != null) {
            this.maybeAlertOthers();
        }
        if (this.isAngry()) {
            this.lastHurtByPlayerTime = this.tickCount;
        }
        super.customServerAiStep();
    }

    private void maybePlayFirstAngerSound() {
        if (this.playFirstAngerSoundIn > 0) {
            --this.playFirstAngerSoundIn;
            if (this.playFirstAngerSoundIn == 0) {
                this.playAngerSound();
            }
        }
    }

    private void maybeAlertOthers() {
        if (this.ticksUntilNextAlert > 0) {
            --this.ticksUntilNextAlert;
            return;
        }
        if (this.getSensing().canSee((Entity)this.getTarget())) {
            this.alertOthers();
        }
        this.ticksUntilNextAlert = ALERT_INTERVAL.randomValue(this.random);
    }

    private void alertOthers() {
        double d = this.getAttributeValue(Attributes.FOLLOW_RANGE);
        AABB aABB = AABB.unitCubeFromLowerCorner((Vec3)this.position()).inflate(d, 10.0, d);
        this.level.getLoadedEntitiesOfClass(ZombifiedPiglin.class, aABB).stream().filter(zombifiedPiglin -> zombifiedPiglin != this).filter(zombifiedPiglin -> zombifiedPiglin.getTarget() == null).filter(zombifiedPiglin -> !zombifiedPiglin.isAlliedTo((Entity)this.getTarget())).forEach(zombifiedPiglin -> zombifiedPiglin.setTarget(this.getTarget()));
    }

    private void playAngerSound() {
        this.playSound(SoundEvents.ZOMBIFIED_PIGLIN_ANGRY, this.getSoundVolume() * 2.0f, this.getVoicePitch() * 1.8f);
    }

    public void setTarget(@Nullable LivingEntity livingEntity) {
        if (this.getTarget() == null && livingEntity != null) {
            this.playFirstAngerSoundIn = FIRST_ANGER_SOUND_DELAY.randomValue(this.random);
            this.ticksUntilNextAlert = ALERT_INTERVAL.randomValue(this.random);
        }
        if (livingEntity instanceof Player) {
            this.setLastHurtByPlayer((Player)livingEntity);
        }
        super.setTarget(livingEntity);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.randomValue(this.random));
    }

    public static boolean checkZombifiedPiglinSpawnRules(EntityType<ZombifiedPiglin> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, Random random) {
        return levelAccessor.getDifficulty() != Difficulty.PEACEFUL && levelAccessor.getBlockState(blockPos.below()).getBlock() != Blocks.NETHER_WART_BLOCK;
    }

    public boolean checkSpawnObstruction(LevelReader levelReader) {
        return levelReader.isUnobstructed((Entity)this) && !levelReader.containsAnyLiquid(this.getBoundingBox());
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        this.addPersistentAngerSaveData(compoundTag);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.readPersistentAngerSaveData((ServerLevel)this.level, compoundTag);
    }

    public void setRemainingPersistentAngerTime(int i) {
        this.remainingPersistentAngerTime = i;
    }

    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    public boolean hurt(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        }
        return super.hurt(damageSource, f);
    }

    protected SoundEvent getAmbientSound() {
        return this.isAngry() ? SoundEvents.ZOMBIFIED_PIGLIN_ANGRY : SoundEvents.ZOMBIFIED_PIGLIN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ZOMBIFIED_PIGLIN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIFIED_PIGLIN_DEATH;
    }

    protected void populateDefaultEquipmentSlots(DifficultyInstance difficultyInstance) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.GOLDEN_SWORD));
    }

    protected ItemStack getSkull() {
        return ItemStack.EMPTY;
    }

    protected void randomizeReinforcementsChance() {
        this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(0.0);
    }

    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public boolean isPreventingPlayerRest(Player player) {
        return this.isAngryAt((LivingEntity)player);
    }
}
