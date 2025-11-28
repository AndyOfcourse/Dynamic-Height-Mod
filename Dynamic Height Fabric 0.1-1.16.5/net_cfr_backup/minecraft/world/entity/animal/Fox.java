/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ItemParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.util.Mth
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.AgableMob
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntitySelector
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.SpawnGroupData
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.AvoidEntityGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.LeapAtTargetGoal
 *  net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.animal.AbstractFish
 *  net.minecraft.world.entity.animal.AbstractSchoolingFish
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.entity.animal.Chicken
 *  net.minecraft.world.entity.animal.Fox$DefendTrustedTargetGoal
 *  net.minecraft.world.entity.animal.Fox$FaceplantGoal
 *  net.minecraft.world.entity.animal.Fox$FoxBreedGoal
 *  net.minecraft.world.entity.animal.Fox$FoxEatBerriesGoal
 *  net.minecraft.world.entity.animal.Fox$FoxFloatGoal
 *  net.minecraft.world.entity.animal.Fox$FoxFollowParentGoal
 *  net.minecraft.world.entity.animal.Fox$FoxGroupData
 *  net.minecraft.world.entity.animal.Fox$FoxLookAtPlayerGoal
 *  net.minecraft.world.entity.animal.Fox$FoxLookControl
 *  net.minecraft.world.entity.animal.Fox$FoxMeleeAttackGoal
 *  net.minecraft.world.entity.animal.Fox$FoxMoveControl
 *  net.minecraft.world.entity.animal.Fox$FoxPanicGoal
 *  net.minecraft.world.entity.animal.Fox$FoxPounceGoal
 *  net.minecraft.world.entity.animal.Fox$FoxSearchForItemsGoal
 *  net.minecraft.world.entity.animal.Fox$FoxStrollThroughVillageGoal
 *  net.minecraft.world.entity.animal.Fox$PerchAndSearchGoal
 *  net.minecraft.world.entity.animal.Fox$SeekShelterGoal
 *  net.minecraft.world.entity.animal.Fox$SleepGoal
 *  net.minecraft.world.entity.animal.Fox$StalkPreyGoal
 *  net.minecraft.world.entity.animal.Fox$Type
 *  net.minecraft.world.entity.animal.PolarBear
 *  net.minecraft.world.entity.animal.Rabbit
 *  net.minecraft.world.entity.animal.Turtle
 *  net.minecraft.world.entity.animal.Wolf
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.world.entity.animal;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class Fox
extends Animal {
    private static final EntityDataAccessor<Integer> DATA_TYPE_ID = SynchedEntityData.defineId(Fox.class, (EntityDataSerializer)EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Fox.class, (EntityDataSerializer)EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_0 = SynchedEntityData.defineId(Fox.class, (EntityDataSerializer)EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_1 = SynchedEntityData.defineId(Fox.class, (EntityDataSerializer)EntityDataSerializers.OPTIONAL_UUID);
    private static final Predicate<ItemEntity> ALLOWED_ITEMS = itemEntity -> !itemEntity.hasPickUpDelay() && itemEntity.isAlive();
    private static final Predicate<Entity> TRUSTED_TARGET_SELECTOR = entity -> {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            return livingEntity.getLastHurtMob() != null && livingEntity.getLastHurtMobTimestamp() < livingEntity.tickCount + 600;
        }
        return false;
    };
    private static final Predicate<Entity> STALKABLE_PREY = entity -> entity instanceof Chicken || entity instanceof Rabbit;
    private static final Predicate<Entity> AVOID_PLAYERS = entity -> !entity.isDiscrete() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity);
    private Goal landTargetGoal;
    private Goal turtleEggTargetGoal;
    private Goal fishTargetGoal;
    private float interestedAngle;
    private float interestedAngleO;
    private float crouchAmount;
    private float crouchAmountO;
    private int ticksSinceEaten;

    public Fox(EntityType<? extends Fox> entityType, Level level) {
        super(entityType, level);
        this.lookControl = new FoxLookControl(this);
        this.moveControl = new FoxMoveControl(this);
        this.setPathfindingMalus(BlockPathTypes.DANGER_OTHER, 0.0f);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 0.0f);
        this.setCanPickUpLoot(true);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TRUSTED_ID_0, Optional.empty());
        this.entityData.define(DATA_TRUSTED_ID_1, Optional.empty());
        this.entityData.define(DATA_TYPE_ID, (Object)0);
        this.entityData.define(DATA_FLAGS_ID, (Object)0);
    }

    protected void registerGoals() {
        this.landTargetGoal = new NearestAttackableTargetGoal((Mob)this, Animal.class, 10, false, false, livingEntity -> livingEntity instanceof Chicken || livingEntity instanceof Rabbit);
        this.turtleEggTargetGoal = new NearestAttackableTargetGoal((Mob)this, Turtle.class, 10, false, false, Turtle.BABY_ON_LAND_SELECTOR);
        this.fishTargetGoal = new NearestAttackableTargetGoal((Mob)this, AbstractFish.class, 20, false, false, livingEntity -> livingEntity instanceof AbstractSchoolingFish);
        this.goalSelector.addGoal(0, (Goal)new FoxFloatGoal(this));
        this.goalSelector.addGoal(1, (Goal)new FaceplantGoal(this));
        this.goalSelector.addGoal(2, (Goal)new FoxPanicGoal(this, 2.2));
        this.goalSelector.addGoal(3, (Goal)new FoxBreedGoal(this, 1.0));
        this.goalSelector.addGoal(4, (Goal)new AvoidEntityGoal((PathfinderMob)this, Player.class, 16.0f, 1.6, 1.4, livingEntity -> AVOID_PLAYERS.test((Entity)livingEntity) && !this.trusts(livingEntity.getUUID()) && !this.isDefending()));
        this.goalSelector.addGoal(4, (Goal)new AvoidEntityGoal((PathfinderMob)this, Wolf.class, 8.0f, 1.6, 1.4, livingEntity -> !((Wolf)livingEntity).isTame() && !this.isDefending()));
        this.goalSelector.addGoal(4, (Goal)new AvoidEntityGoal((PathfinderMob)this, PolarBear.class, 8.0f, 1.6, 1.4, livingEntity -> !this.isDefending()));
        this.goalSelector.addGoal(5, (Goal)new StalkPreyGoal(this));
        this.goalSelector.addGoal(6, (Goal)new FoxPounceGoal(this));
        this.goalSelector.addGoal(6, (Goal)new SeekShelterGoal(this, 1.25));
        this.goalSelector.addGoal(7, (Goal)new FoxMeleeAttackGoal(this, (double)1.2f, true));
        this.goalSelector.addGoal(7, (Goal)new SleepGoal(this));
        this.goalSelector.addGoal(8, (Goal)new FoxFollowParentGoal(this, this, 1.25));
        this.goalSelector.addGoal(9, (Goal)new FoxStrollThroughVillageGoal(this, 32, 200));
        this.goalSelector.addGoal(10, (Goal)new FoxEatBerriesGoal(this, (double)1.2f, 12, 2));
        this.goalSelector.addGoal(10, (Goal)new LeapAtTargetGoal((Mob)this, 0.4f));
        this.goalSelector.addGoal(11, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 1.0));
        this.goalSelector.addGoal(11, (Goal)new FoxSearchForItemsGoal(this));
        this.goalSelector.addGoal(12, (Goal)new FoxLookAtPlayerGoal(this, (Mob)this, Player.class, 24.0f));
        this.goalSelector.addGoal(13, (Goal)new PerchAndSearchGoal(this));
        this.targetSelector.addGoal(3, (Goal)new DefendTrustedTargetGoal(this, LivingEntity.class, false, false, livingEntity -> TRUSTED_TARGET_SELECTOR.test((Entity)livingEntity) && !this.trusts(livingEntity.getUUID())));
    }

    public SoundEvent getEatingSound(ItemStack itemStack) {
        return SoundEvents.FOX_EAT;
    }

    public void aiStep() {
        if (!this.level.isClientSide && this.isAlive() && this.isEffectiveAi()) {
            LivingEntity livingEntity;
            ++this.ticksSinceEaten;
            ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (this.canEat(itemStack)) {
                if (this.ticksSinceEaten > 600) {
                    ItemStack itemStack2 = itemStack.finishUsingItem(this.level, (LivingEntity)this);
                    if (!itemStack2.isEmpty()) {
                        this.setItemSlot(EquipmentSlot.MAINHAND, itemStack2);
                    }
                    this.ticksSinceEaten = 0;
                } else if (this.ticksSinceEaten > 560 && this.random.nextFloat() < 0.1f) {
                    this.playSound(this.getEatingSound(itemStack), 1.0f, 1.0f);
                    this.level.broadcastEntityEvent((Entity)this, (byte)45);
                }
            }
            if ((livingEntity = this.getTarget()) == null || !livingEntity.isAlive()) {
                this.setIsCrouching(false);
                this.setIsInterested(false);
            }
        }
        if (this.isSleeping() || this.isImmobile()) {
            this.jumping = false;
            this.xxa = 0.0f;
            this.zza = 0.0f;
        }
        super.aiStep();
        if (this.isDefending() && this.random.nextFloat() < 0.05f) {
            this.playSound(SoundEvents.FOX_AGGRO, 1.0f, 1.0f);
        }
    }

    protected boolean isImmobile() {
        return this.isDeadOrDying();
    }

    private boolean canEat(ItemStack itemStack) {
        return itemStack.getItem().isEdible() && this.getTarget() == null && this.onGround && !this.isSleeping();
    }

    protected void populateDefaultEquipmentSlots(DifficultyInstance difficultyInstance) {
        if (this.random.nextFloat() < 0.2f) {
            float f = this.random.nextFloat();
            ItemStack itemStack = f < 0.05f ? new ItemStack((ItemLike)Items.EMERALD) : (f < 0.2f ? new ItemStack((ItemLike)Items.EGG) : (f < 0.4f ? (this.random.nextBoolean() ? new ItemStack((ItemLike)Items.RABBIT_FOOT) : new ItemStack((ItemLike)Items.RABBIT_HIDE)) : (f < 0.6f ? new ItemStack((ItemLike)Items.WHEAT) : (f < 0.8f ? new ItemStack((ItemLike)Items.LEATHER) : new ItemStack((ItemLike)Items.FEATHER)))));
            this.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
        }
    }

    @Environment(value=EnvType.CLIENT)
    public void handleEntityEvent(byte b) {
        if (b == 45) {
            ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (!itemStack.isEmpty()) {
                for (int i = 0; i < 8; ++i) {
                    Vec3 vec3 = new Vec3(((double)this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0).xRot(-this.xRot * ((float)Math.PI / 180)).yRot(-this.yRot * ((float)Math.PI / 180));
                    this.level.addParticle((ParticleOptions)new ItemParticleOption(ParticleTypes.ITEM, itemStack), this.getX() + this.getLookAngle().x / 2.0, this.getY(), this.getZ() + this.getLookAngle().z / 2.0, vec3.x, vec3.y + 0.05, vec3.z);
                }
            }
        } else {
            super.handleEntityEvent(b);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, (double)0.3f).add(Attributes.MAX_HEALTH, 10.0).add(Attributes.FOLLOW_RANGE, 32.0).add(Attributes.ATTACK_DAMAGE, 2.0);
    }

    public Fox getBreedOffspring(ServerLevel serverLevel, AgableMob agableMob) {
        Fox fox = (Fox)EntityType.FOX.create((Level)serverLevel);
        fox.setFoxType(this.random.nextBoolean() ? this.getFoxType() : ((Fox)agableMob).getFoxType());
        return fox;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        Optional optional = serverLevelAccessor.getBiomeName(this.blockPosition());
        Type type = Type.byBiome((Optional)optional);
        boolean bl = false;
        if (spawnGroupData instanceof FoxGroupData) {
            type = ((FoxGroupData)spawnGroupData).type;
            if (((FoxGroupData)spawnGroupData).getGroupSize() >= 2) {
                bl = true;
            }
        } else {
            spawnGroupData = new FoxGroupData(type);
        }
        this.setFoxType(type);
        if (bl) {
            this.setAge(-24000);
        }
        if (serverLevelAccessor instanceof ServerLevel) {
            this.setTargetGoals();
        }
        this.populateDefaultEquipmentSlots(difficultyInstance);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    private void setTargetGoals() {
        if (this.getFoxType() == Type.RED) {
            this.targetSelector.addGoal(4, this.landTargetGoal);
            this.targetSelector.addGoal(4, this.turtleEggTargetGoal);
            this.targetSelector.addGoal(6, this.fishTargetGoal);
        } else {
            this.targetSelector.addGoal(4, this.fishTargetGoal);
            this.targetSelector.addGoal(6, this.landTargetGoal);
            this.targetSelector.addGoal(6, this.turtleEggTargetGoal);
        }
    }

    protected void usePlayerItem(Player player, ItemStack itemStack) {
        if (this.isFood(itemStack)) {
            this.playSound(this.getEatingSound(itemStack), 1.0f, 1.0f);
        }
        super.usePlayerItem(player, itemStack);
    }

    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        if (this.isBaby()) {
            return entityDimensions.height * 0.85f;
        }
        return 0.4f;
    }

    public Type getFoxType() {
        return Type.byId((int)((Integer)this.entityData.get(DATA_TYPE_ID)));
    }

    private void setFoxType(Type type) {
        this.entityData.set(DATA_TYPE_ID, (Object)type.getId());
    }

    private List<UUID> getTrustedUUIDs() {
        ArrayList list = Lists.newArrayList();
        list.add(((Optional)this.entityData.get(DATA_TRUSTED_ID_0)).orElse(null));
        list.add(((Optional)this.entityData.get(DATA_TRUSTED_ID_1)).orElse(null));
        return list;
    }

    private void addTrustedUUID(@Nullable UUID uUID) {
        if (((Optional)this.entityData.get(DATA_TRUSTED_ID_0)).isPresent()) {
            this.entityData.set(DATA_TRUSTED_ID_1, Optional.ofNullable(uUID));
        } else {
            this.entityData.set(DATA_TRUSTED_ID_0, Optional.ofNullable(uUID));
        }
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        List<UUID> list = this.getTrustedUUIDs();
        ListTag listTag = new ListTag();
        for (UUID uUID : list) {
            if (uUID == null) continue;
            listTag.add((Object)NbtUtils.createUUID((UUID)uUID));
        }
        compoundTag.put("Trusted", (Tag)listTag);
        compoundTag.putBoolean("Sleeping", this.isSleeping());
        compoundTag.putString("Type", this.getFoxType().getName());
        compoundTag.putBoolean("Sitting", this.isSitting());
        compoundTag.putBoolean("Crouching", this.isCrouching());
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        ListTag listTag = compoundTag.getList("Trusted", 11);
        for (int i = 0; i < listTag.size(); ++i) {
            this.addTrustedUUID(NbtUtils.loadUUID((Tag)listTag.get(i)));
        }
        this.setSleeping(compoundTag.getBoolean("Sleeping"));
        this.setFoxType(Type.byName((String)compoundTag.getString("Type")));
        this.setSitting(compoundTag.getBoolean("Sitting"));
        this.setIsCrouching(compoundTag.getBoolean("Crouching"));
        if (this.level instanceof ServerLevel) {
            this.setTargetGoals();
        }
    }

    public boolean isSitting() {
        return this.getFlag(1);
    }

    public void setSitting(boolean bl) {
        this.setFlag(1, bl);
    }

    public boolean isFaceplanted() {
        return this.getFlag(64);
    }

    private void setFaceplanted(boolean bl) {
        this.setFlag(64, bl);
    }

    private boolean isDefending() {
        return this.getFlag(128);
    }

    private void setDefending(boolean bl) {
        this.setFlag(128, bl);
    }

    public boolean isSleeping() {
        return this.getFlag(32);
    }

    private void setSleeping(boolean bl) {
        this.setFlag(32, bl);
    }

    private void setFlag(int i, boolean bl) {
        if (bl) {
            this.entityData.set(DATA_FLAGS_ID, (Object)((byte)((Byte)this.entityData.get(DATA_FLAGS_ID) | i)));
        } else {
            this.entityData.set(DATA_FLAGS_ID, (Object)((byte)((Byte)this.entityData.get(DATA_FLAGS_ID) & ~i)));
        }
    }

    private boolean getFlag(int i) {
        return ((Byte)this.entityData.get(DATA_FLAGS_ID) & i) != 0;
    }

    public boolean canTakeItem(ItemStack itemStack) {
        EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem((ItemStack)itemStack);
        if (!this.getItemBySlot(equipmentSlot).isEmpty()) {
            return false;
        }
        return equipmentSlot == EquipmentSlot.MAINHAND && super.canTakeItem(itemStack);
    }

    public boolean canHoldItem(ItemStack itemStack) {
        Item item = itemStack.getItem();
        ItemStack itemStack2 = this.getItemBySlot(EquipmentSlot.MAINHAND);
        return itemStack2.isEmpty() || this.ticksSinceEaten > 0 && item.isEdible() && !itemStack2.getItem().isEdible();
    }

    private void spitOutItem(ItemStack itemStack) {
        if (itemStack.isEmpty() || this.level.isClientSide) {
            return;
        }
        ItemEntity itemEntity = new ItemEntity(this.level, this.getX() + this.getLookAngle().x, this.getY() + 1.0, this.getZ() + this.getLookAngle().z, itemStack);
        itemEntity.setPickUpDelay(40);
        itemEntity.setThrower(this.getUUID());
        this.playSound(SoundEvents.FOX_SPIT, 1.0f, 1.0f);
        this.level.addFreshEntity((Entity)itemEntity);
    }

    private void dropItemStack(ItemStack itemStack) {
        ItemEntity itemEntity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), itemStack);
        this.level.addFreshEntity((Entity)itemEntity);
    }

    protected void pickUpItem(ItemEntity itemEntity) {
        ItemStack itemStack = itemEntity.getItem();
        if (this.canHoldItem(itemStack)) {
            int i = itemStack.getCount();
            if (i > 1) {
                this.dropItemStack(itemStack.split(i - 1));
            }
            this.spitOutItem(this.getItemBySlot(EquipmentSlot.MAINHAND));
            this.onItemPickup(itemEntity);
            this.setItemSlot(EquipmentSlot.MAINHAND, itemStack.split(1));
            this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0f;
            this.take((Entity)itemEntity, itemStack.getCount());
            itemEntity.remove();
            this.ticksSinceEaten = 0;
        }
    }

    public void tick() {
        super.tick();
        if (this.isEffectiveAi()) {
            boolean bl = this.isInWater();
            if (bl || this.getTarget() != null || this.level.isThundering()) {
                this.wakeUp();
            }
            if (bl || this.isSleeping()) {
                this.setSitting(false);
            }
            if (this.isFaceplanted() && this.level.random.nextFloat() < 0.2f) {
                BlockPos blockPos = this.blockPosition();
                BlockState blockState = this.level.getBlockState(blockPos);
                this.level.levelEvent(2001, blockPos, Block.getId((BlockState)blockState));
            }
        }
        this.interestedAngleO = this.interestedAngle;
        this.interestedAngle = this.isInterested() ? (this.interestedAngle += (1.0f - this.interestedAngle) * 0.4f) : (this.interestedAngle += (0.0f - this.interestedAngle) * 0.4f);
        this.crouchAmountO = this.crouchAmount;
        if (this.isCrouching()) {
            this.crouchAmount += 0.2f;
            if (this.crouchAmount > 3.0f) {
                this.crouchAmount = 3.0f;
            }
        } else {
            this.crouchAmount = 0.0f;
        }
    }

    public boolean isFood(ItemStack itemStack) {
        return itemStack.getItem() == Items.SWEET_BERRIES;
    }

    protected void onOffspringSpawnedFromEgg(Player player, Mob mob) {
        ((Fox)mob).addTrustedUUID(player.getUUID());
    }

    public boolean isPouncing() {
        return this.getFlag(16);
    }

    public void setIsPouncing(boolean bl) {
        this.setFlag(16, bl);
    }

    public boolean isFullyCrouched() {
        return this.crouchAmount == 3.0f;
    }

    public void setIsCrouching(boolean bl) {
        this.setFlag(4, bl);
    }

    public boolean isCrouching() {
        return this.getFlag(4);
    }

    public void setIsInterested(boolean bl) {
        this.setFlag(8, bl);
    }

    public boolean isInterested() {
        return this.getFlag(8);
    }

    @Environment(value=EnvType.CLIENT)
    public float getHeadRollAngle(float f) {
        return Mth.lerp((float)f, (float)this.interestedAngleO, (float)this.interestedAngle) * 0.11f * (float)Math.PI;
    }

    @Environment(value=EnvType.CLIENT)
    public float getCrouchAmount(float f) {
        return Mth.lerp((float)f, (float)this.crouchAmountO, (float)this.crouchAmount);
    }

    public void setTarget(@Nullable LivingEntity livingEntity) {
        if (this.isDefending() && livingEntity == null) {
            this.setDefending(false);
        }
        super.setTarget(livingEntity);
    }

    protected int calculateFallDamage(float f, float g) {
        return Mth.ceil((float)((f - 5.0f) * g));
    }

    private void wakeUp() {
        this.setSleeping(false);
    }

    private void clearStates() {
        this.setIsInterested(false);
        this.setIsCrouching(false);
        this.setSitting(false);
        this.setSleeping(false);
        this.setDefending(false);
        this.setFaceplanted(false);
    }

    private boolean canMove() {
        return !this.isSleeping() && !this.isSitting() && !this.isFaceplanted();
    }

    public void playAmbientSound() {
        SoundEvent soundEvent = this.getAmbientSound();
        if (soundEvent == SoundEvents.FOX_SCREECH) {
            this.playSound(soundEvent, 2.0f, this.getVoicePitch());
        } else {
            super.playAmbientSound();
        }
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        List list;
        if (this.isSleeping()) {
            return SoundEvents.FOX_SLEEP;
        }
        if (!this.level.isDay() && this.random.nextFloat() < 0.1f && (list = this.level.getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0), EntitySelector.NO_SPECTATORS)).isEmpty()) {
            return SoundEvents.FOX_SCREECH;
        }
        return SoundEvents.FOX_AMBIENT;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.FOX_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.FOX_DEATH;
    }

    private boolean trusts(UUID uUID) {
        return this.getTrustedUUIDs().contains(uUID);
    }

    protected void dropAllDeathLoot(DamageSource damageSource) {
        ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!itemStack.isEmpty()) {
            this.spawnAtLocation(itemStack);
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
        super.dropAllDeathLoot(damageSource);
    }

    public static boolean isPathClear(Fox fox, LivingEntity livingEntity) {
        double d = livingEntity.getZ() - fox.getZ();
        double e = livingEntity.getX() - fox.getX();
        double f = d / e;
        int i = 6;
        for (int j = 0; j < 6; ++j) {
            double g = f == 0.0 ? 0.0 : d * (double)((float)j / 6.0f);
            double h = f == 0.0 ? e * (double)((float)j / 6.0f) : g / f;
            for (int k = 1; k < 4; ++k) {
                if (fox.level.getBlockState(new BlockPos(fox.getX() + h, fox.getY() + (double)k, fox.getZ() + g)).getMaterial().isReplaceable()) continue;
                return false;
            }
        }
        return true;
    }

    @Environment(value=EnvType.CLIENT)
    public Vec3 getLeashOffset() {
        return new Vec3(0.0, (double)(0.55f * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4f));
    }

    public /* synthetic */ AgableMob getBreedOffspring(ServerLevel serverLevel, AgableMob agableMob) {
        return this.getBreedOffspring(serverLevel, agableMob);
    }

    static /* synthetic */ boolean method_18256(Fox fox) {
        return fox.canMove();
    }

    static /* synthetic */ Predicate method_18278() {
        return ALLOWED_ITEMS;
    }

    static /* synthetic */ Predicate method_18279() {
        return STALKABLE_PREY;
    }

    static /* synthetic */ boolean method_18263(Fox fox) {
        return fox.jumping;
    }

    static /* synthetic */ void method_18427(Fox fox, boolean bl) {
        fox.setFaceplanted(bl);
    }

    static /* synthetic */ void method_18268(Fox fox) {
        fox.clearStates();
    }

    static /* synthetic */ void method_18258(Fox fox, UUID uUID) {
        fox.addTrustedUUID(uUID);
    }

    static /* synthetic */ List method_18270(Fox fox) {
        return fox.getTrustedUUIDs();
    }

    static /* synthetic */ void method_18259(Fox fox, boolean bl) {
        fox.setDefending(bl);
    }

    static /* synthetic */ void method_18286(Fox fox) {
        fox.wakeUp();
    }

    static /* synthetic */ boolean method_18426(Fox fox, UUID uUID) {
        return fox.trusts(uUID);
    }

    static /* synthetic */ Random method_18287(Fox fox) {
        return fox.random;
    }

    static /* synthetic */ Random method_18288(Fox fox) {
        return fox.random;
    }

    static /* synthetic */ void method_18264(Fox fox, boolean bl) {
        fox.setSleeping(bl);
    }

    static /* synthetic */ Random method_18290(Fox fox) {
        return fox.random;
    }

    static /* synthetic */ boolean method_18292(Fox fox) {
        return fox.isDefending();
    }

    static /* synthetic */ boolean method_24836(Fox fox) {
        return fox.onGround;
    }

    static /* synthetic */ float method_20434(Fox fox, float f) {
        fox.crouchAmount = f;
        return fox.crouchAmount;
    }

    static /* synthetic */ float method_20435(Fox fox, float f) {
        fox.crouchAmountO = f;
        return fox.crouchAmountO;
    }

    static /* synthetic */ boolean method_24837(Fox fox) {
        return fox.onGround;
    }
}
