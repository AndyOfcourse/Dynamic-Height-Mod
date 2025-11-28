/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.datafixers.util.Pair
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.tags.Tag
 *  net.minecraft.util.IntRange
 *  net.minecraft.util.TimeUtil
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntitySelector
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.Brain
 *  net.minecraft.world.entity.ai.behavior.BackUpIfTooClose
 *  net.minecraft.world.entity.ai.behavior.Behavior
 *  net.minecraft.world.entity.ai.behavior.BehaviorUtils
 *  net.minecraft.world.entity.ai.behavior.CopyMemoryWithExpiry
 *  net.minecraft.world.entity.ai.behavior.CrossbowAttack
 *  net.minecraft.world.entity.ai.behavior.DismountOrSkipMounting
 *  net.minecraft.world.entity.ai.behavior.DoNothing
 *  net.minecraft.world.entity.ai.behavior.EraseMemoryIf
 *  net.minecraft.world.entity.ai.behavior.GoToCelebrateLocation
 *  net.minecraft.world.entity.ai.behavior.GoToWantedItem
 *  net.minecraft.world.entity.ai.behavior.InteractWith
 *  net.minecraft.world.entity.ai.behavior.InteractWithDoor
 *  net.minecraft.world.entity.ai.behavior.LookAtTargetSink
 *  net.minecraft.world.entity.ai.behavior.MeleeAttack
 *  net.minecraft.world.entity.ai.behavior.Mount
 *  net.minecraft.world.entity.ai.behavior.MoveToTargetSink
 *  net.minecraft.world.entity.ai.behavior.RandomStroll
 *  net.minecraft.world.entity.ai.behavior.RunIf
 *  net.minecraft.world.entity.ai.behavior.RunOne
 *  net.minecraft.world.entity.ai.behavior.RunSometimes
 *  net.minecraft.world.entity.ai.behavior.SetEntityLookTarget
 *  net.minecraft.world.entity.ai.behavior.SetLookAndInteract
 *  net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom
 *  net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach
 *  net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget
 *  net.minecraft.world.entity.ai.behavior.StartAttacking
 *  net.minecraft.world.entity.ai.behavior.StartCelebratingIfTargetDead
 *  net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid
 *  net.minecraft.world.entity.ai.behavior.StopBeingAngryIfTargetDead
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.util.RandomPos
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.monster.hoglin.Hoglin
 *  net.minecraft.world.entity.monster.piglin.AbstractPiglin
 *  net.minecraft.world.entity.monster.piglin.Piglin
 *  net.minecraft.world.entity.monster.piglin.RememberIfHoglinWasKilled
 *  net.minecraft.world.entity.monster.piglin.StartAdmiringItemIfSeen
 *  net.minecraft.world.entity.monster.piglin.StartHuntingHoglin
 *  net.minecraft.world.entity.monster.piglin.StopAdmiringIfItemTooFarAway
 *  net.minecraft.world.entity.monster.piglin.StopAdmiringIfTiredOfTryingToReachItem
 *  net.minecraft.world.entity.monster.piglin.StopHoldingItemIfNoLongerAdmiring
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.schedule.Activity
 *  net.minecraft.world.item.ArmorItem
 *  net.minecraft.world.item.ArmorMaterials
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.storage.loot.BuiltInLootTables
 *  net.minecraft.world.level.storage.loot.LootContext$Builder
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraft.world.phys.Vec3
 */
package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.IntRange;
import net.minecraft.util.TimeUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BackUpIfTooClose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.CopyMemoryWithExpiry;
import net.minecraft.world.entity.ai.behavior.CrossbowAttack;
import net.minecraft.world.entity.ai.behavior.DismountOrSkipMounting;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.EraseMemoryIf;
import net.minecraft.world.entity.ai.behavior.GoToCelebrateLocation;
import net.minecraft.world.entity.ai.behavior.GoToWantedItem;
import net.minecraft.world.entity.ai.behavior.InteractWith;
import net.minecraft.world.entity.ai.behavior.InteractWithDoor;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.Mount;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunIf;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.RunSometimes;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetLookAndInteract;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StartCelebratingIfTargetDead;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.StopBeingAngryIfTargetDead;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.RememberIfHoglinWasKilled;
import net.minecraft.world.entity.monster.piglin.StartAdmiringItemIfSeen;
import net.minecraft.world.entity.monster.piglin.StartHuntingHoglin;
import net.minecraft.world.entity.monster.piglin.StopAdmiringIfItemTooFarAway;
import net.minecraft.world.entity.monster.piglin.StopAdmiringIfTiredOfTryingToReachItem;
import net.minecraft.world.entity.monster.piglin.StopHoldingItemIfNoLongerAdmiring;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class PiglinAi {
    public static final Item BARTERING_ITEM = Items.GOLD_INGOT;
    private static final IntRange TIME_BETWEEN_HUNTS = TimeUtil.rangeOfSeconds((int)30, (int)120);
    private static final IntRange RIDE_START_INTERVAL = TimeUtil.rangeOfSeconds((int)10, (int)40);
    private static final IntRange RIDE_DURATION = TimeUtil.rangeOfSeconds((int)10, (int)30);
    private static final IntRange RETREAT_DURATION = TimeUtil.rangeOfSeconds((int)5, (int)20);
    private static final IntRange AVOID_ZOMBIFIED_DURATION = TimeUtil.rangeOfSeconds((int)5, (int)7);
    private static final IntRange BABY_AVOID_NEMESIS_DURATION = TimeUtil.rangeOfSeconds((int)5, (int)7);
    private static final Set<Item> FOOD_ITEMS = ImmutableSet.of((Object)Items.PORKCHOP, (Object)Items.COOKED_PORKCHOP);

    protected static Brain<?> makeBrain(Piglin piglin, Brain<Piglin> brain) {
        PiglinAi.initCoreActivity(brain);
        PiglinAi.initIdleActivity(brain);
        PiglinAi.initAdmireItemActivity(brain);
        PiglinAi.initFightActivity(piglin, brain);
        PiglinAi.initCelebrateActivity(brain);
        PiglinAi.initRetreatActivity(brain);
        PiglinAi.initRideHoglinActivity(brain);
        brain.setCoreActivities((Set)ImmutableSet.of((Object)Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    protected static void initMemories(Piglin piglin) {
        int i = TIME_BETWEEN_HUNTS.randomValue(piglin.level.random);
        piglin.getBrain().setMemoryWithExpiry(MemoryModuleType.HUNTED_RECENTLY, (Object)true, (long)i);
    }

    private static void initCoreActivity(Brain<Piglin> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of((Object)new LookAtTargetSink(45, 90), (Object)new MoveToTargetSink(), (Object)new InteractWithDoor(), PiglinAi.babyAvoidNemesis(), PiglinAi.avoidZombified(), (Object)new StopHoldingItemIfNoLongerAdmiring(), (Object)new StartAdmiringItemIfSeen(120), (Object)new StartCelebratingIfTargetDead(300, PiglinAi::wantsToDance), (Object)new StopBeingAngryIfTargetDead()));
    }

    private static void initIdleActivity(Brain<Piglin> brain) {
        brain.addActivity(Activity.IDLE, 10, ImmutableList.of((Object)new SetEntityLookTarget(PiglinAi::isPlayerHoldingLovedItem, 14.0f), (Object)new StartAttacking(AbstractPiglin::isAdult, PiglinAi::findNearestValidAttackTarget), (Object)new RunIf(Piglin::canHunt, (Behavior)new StartHuntingHoglin()), PiglinAi.avoidRepellent(), PiglinAi.babySometimesRideBabyHoglin(), PiglinAi.createIdleLookBehaviors(), PiglinAi.createIdleMovementBehaviors(), (Object)new SetLookAndInteract(EntityType.PLAYER, 4)));
    }

    private static void initFightActivity(Piglin piglin, Brain<Piglin> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 10, ImmutableList.of((Object)new StopAttackingIfTargetInvalid(livingEntity -> !PiglinAi.isNearestValidAttackTarget(piglin, livingEntity)), (Object)new RunIf(PiglinAi::hasCrossbow, (Behavior)new BackUpIfTooClose(5, 0.75f)), (Object)new SetWalkTargetFromAttackTargetIfTargetOutOfReach(1.0f), (Object)new MeleeAttack(20), (Object)new CrossbowAttack(), (Object)new RememberIfHoglinWasKilled(), (Object)new EraseMemoryIf(PiglinAi::isNearZombified, MemoryModuleType.ATTACK_TARGET)), MemoryModuleType.ATTACK_TARGET);
    }

    private static void initCelebrateActivity(Brain<Piglin> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.CELEBRATE, 10, ImmutableList.of(PiglinAi.avoidRepellent(), (Object)new SetEntityLookTarget(PiglinAi::isPlayerHoldingLovedItem, 14.0f), (Object)new StartAttacking(AbstractPiglin::isAdult, PiglinAi::findNearestValidAttackTarget), (Object)new RunIf(piglin -> !piglin.isDancing(), (Behavior)new GoToCelebrateLocation(2, 1.0f)), (Object)new RunIf(Piglin::isDancing, (Behavior)new GoToCelebrateLocation(4, 0.6f)), (Object)new RunOne((List)ImmutableList.of((Object)Pair.of((Object)new SetEntityLookTarget(EntityType.PIGLIN, 8.0f), (Object)1), (Object)Pair.of((Object)new RandomStroll(0.6f, 2, 1), (Object)1), (Object)Pair.of((Object)new DoNothing(10, 20), (Object)1)))), MemoryModuleType.CELEBRATE_LOCATION);
    }

    private static void initAdmireItemActivity(Brain<Piglin> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.ADMIRE_ITEM, 10, ImmutableList.of((Object)new GoToWantedItem(PiglinAi::isNotHoldingLovedItemInOffHand, 1.0f, true, 9), (Object)new StopAdmiringIfItemTooFarAway(9), (Object)new StopAdmiringIfTiredOfTryingToReachItem(200, 200)), MemoryModuleType.ADMIRING_ITEM);
    }

    private static void initRetreatActivity(Brain<Piglin> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.AVOID, 10, ImmutableList.of((Object)SetWalkTargetAwayFrom.entity((MemoryModuleType)MemoryModuleType.AVOID_TARGET, (float)1.0f, (int)12, (boolean)true), PiglinAi.createIdleLookBehaviors(), PiglinAi.createIdleMovementBehaviors(), (Object)new EraseMemoryIf(PiglinAi::wantsToStopFleeing, MemoryModuleType.AVOID_TARGET)), MemoryModuleType.AVOID_TARGET);
    }

    private static void initRideHoglinActivity(Brain<Piglin> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.RIDE, 10, ImmutableList.of((Object)new Mount(0.8f), (Object)new SetEntityLookTarget(PiglinAi::isPlayerHoldingLovedItem, 8.0f), (Object)new RunIf(Entity::isPassenger, PiglinAi.createIdleLookBehaviors()), (Object)new DismountOrSkipMounting(8, PiglinAi::wantsToStopRiding)), MemoryModuleType.RIDE_TARGET);
    }

    private static RunOne<Piglin> createIdleLookBehaviors() {
        return new RunOne((List)ImmutableList.of((Object)Pair.of((Object)new SetEntityLookTarget(EntityType.PLAYER, 8.0f), (Object)1), (Object)Pair.of((Object)new SetEntityLookTarget(EntityType.PIGLIN, 8.0f), (Object)1), (Object)Pair.of((Object)new SetEntityLookTarget(8.0f), (Object)1), (Object)Pair.of((Object)new DoNothing(30, 60), (Object)1)));
    }

    private static RunOne<Piglin> createIdleMovementBehaviors() {
        return new RunOne((List)ImmutableList.of((Object)Pair.of((Object)new RandomStroll(0.6f), (Object)2), (Object)Pair.of((Object)InteractWith.of((EntityType)EntityType.PIGLIN, (int)8, (MemoryModuleType)MemoryModuleType.INTERACTION_TARGET, (float)0.6f, (int)2), (Object)2), (Object)Pair.of((Object)new RunIf(PiglinAi::doesntSeeAnyPlayerHoldingLovedItem, (Behavior)new SetWalkTargetFromLookTarget(0.6f, 3)), (Object)2), (Object)Pair.of((Object)new DoNothing(30, 60), (Object)1)));
    }

    private static SetWalkTargetAwayFrom<BlockPos> avoidRepellent() {
        return SetWalkTargetAwayFrom.pos((MemoryModuleType)MemoryModuleType.NEAREST_REPELLENT, (float)1.0f, (int)8, (boolean)false);
    }

    private static CopyMemoryWithExpiry<Piglin, LivingEntity> babyAvoidNemesis() {
        return new CopyMemoryWithExpiry(Piglin::isBaby, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.AVOID_TARGET, BABY_AVOID_NEMESIS_DURATION);
    }

    private static CopyMemoryWithExpiry<Piglin, LivingEntity> avoidZombified() {
        return new CopyMemoryWithExpiry(PiglinAi::isNearZombified, MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, MemoryModuleType.AVOID_TARGET, AVOID_ZOMBIFIED_DURATION);
    }

    protected static void updateActivity(Piglin piglin) {
        Brain brain = piglin.getBrain();
        Activity activity = brain.getActiveNonCoreActivity().orElse(null);
        brain.setActiveActivityToFirstValid((List)ImmutableList.of((Object)Activity.ADMIRE_ITEM, (Object)Activity.FIGHT, (Object)Activity.AVOID, (Object)Activity.CELEBRATE, (Object)Activity.RIDE, (Object)Activity.IDLE));
        Activity activity2 = brain.getActiveNonCoreActivity().orElse(null);
        if (activity != activity2) {
            PiglinAi.getSoundForCurrentActivity(piglin).ifPresent(arg_0 -> ((Piglin)piglin).playSound(arg_0));
        }
        piglin.setAggressive(brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
        if (!brain.hasMemoryValue(MemoryModuleType.RIDE_TARGET) && PiglinAi.isBabyRidingBaby(piglin)) {
            piglin.stopRiding();
        }
        if (!brain.hasMemoryValue(MemoryModuleType.CELEBRATE_LOCATION)) {
            brain.eraseMemory(MemoryModuleType.DANCING);
        }
        piglin.setDancing(brain.hasMemoryValue(MemoryModuleType.DANCING));
    }

    private static boolean isBabyRidingBaby(Piglin piglin) {
        if (!piglin.isBaby()) {
            return false;
        }
        Entity entity = piglin.getVehicle();
        return entity instanceof Piglin && ((Piglin)entity).isBaby() || entity instanceof Hoglin && ((Hoglin)entity).isBaby();
    }

    protected static void pickUpItem(Piglin piglin, ItemEntity itemEntity) {
        ItemStack itemStack;
        PiglinAi.stopWalking(piglin);
        if (itemEntity.getItem().getItem() == Items.GOLD_NUGGET) {
            piglin.take((Entity)itemEntity, itemEntity.getItem().getCount());
            itemStack = itemEntity.getItem();
            itemEntity.remove();
        } else {
            piglin.take((Entity)itemEntity, 1);
            itemStack = PiglinAi.removeOneItemFromItemEntity(itemEntity);
        }
        Item item = itemStack.getItem();
        if (PiglinAi.isLovedItem(item)) {
            piglin.getBrain().eraseMemory(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
            PiglinAi.holdInOffhand(piglin, itemStack);
            PiglinAi.admireGoldItem((LivingEntity)piglin);
            return;
        }
        if (PiglinAi.isFood(item) && !PiglinAi.hasEatenRecently(piglin)) {
            PiglinAi.eat(piglin);
            return;
        }
        boolean bl = piglin.equipItemIfPossible(itemStack);
        if (bl) {
            return;
        }
        PiglinAi.putInInventory(piglin, itemStack);
    }

    private static void holdInOffhand(Piglin piglin, ItemStack itemStack) {
        if (PiglinAi.isHoldingItemInOffHand(piglin)) {
            piglin.spawnAtLocation(piglin.getItemInHand(InteractionHand.OFF_HAND));
        }
        piglin.holdInOffHand(itemStack);
    }

    private static ItemStack removeOneItemFromItemEntity(ItemEntity itemEntity) {
        ItemStack itemStack = itemEntity.getItem();
        ItemStack itemStack2 = itemStack.split(1);
        if (itemStack.isEmpty()) {
            itemEntity.remove();
        } else {
            itemEntity.setItem(itemStack);
        }
        return itemStack2;
    }

    protected static void stopHoldingOffHandItem(Piglin piglin, boolean bl) {
        ItemStack itemStack = piglin.getItemInHand(InteractionHand.OFF_HAND);
        piglin.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
        if (piglin.isAdult()) {
            boolean bl3;
            boolean bl2 = PiglinAi.isBarterCurrency(itemStack.getItem());
            if (bl && bl2) {
                PiglinAi.throwItems(piglin, PiglinAi.getBarterResponseItems(piglin));
            } else if (!bl2 && !(bl3 = piglin.equipItemIfPossible(itemStack))) {
                PiglinAi.putInInventory(piglin, itemStack);
            }
        } else {
            boolean bl2 = piglin.equipItemIfPossible(itemStack);
            if (!bl2) {
                ItemStack itemStack2 = piglin.getMainHandItem();
                if (PiglinAi.isLovedItem(itemStack2.getItem())) {
                    PiglinAi.putInInventory(piglin, itemStack2);
                } else {
                    PiglinAi.throwItems(piglin, Collections.singletonList(itemStack2));
                }
                piglin.holdInMainHand(itemStack);
            }
        }
    }

    protected static void cancelAdmiring(Piglin piglin) {
        if (PiglinAi.isAdmiringItem(piglin) && !piglin.getOffhandItem().isEmpty()) {
            piglin.spawnAtLocation(piglin.getOffhandItem());
            piglin.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
        }
    }

    private static void putInInventory(Piglin piglin, ItemStack itemStack) {
        ItemStack itemStack2 = piglin.addToInventory(itemStack);
        PiglinAi.throwItemsTowardRandomPos(piglin, Collections.singletonList(itemStack2));
    }

    private static void throwItems(Piglin piglin, List<ItemStack> list) {
        Optional optional = piglin.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);
        if (optional.isPresent()) {
            PiglinAi.throwItemsTowardPlayer(piglin, (Player)optional.get(), list);
        } else {
            PiglinAi.throwItemsTowardRandomPos(piglin, list);
        }
    }

    private static void throwItemsTowardRandomPos(Piglin piglin, List<ItemStack> list) {
        PiglinAi.throwItemsTowardPos(piglin, list, PiglinAi.getRandomNearbyPos(piglin));
    }

    private static void throwItemsTowardPlayer(Piglin piglin, Player player, List<ItemStack> list) {
        PiglinAi.throwItemsTowardPos(piglin, list, player.position());
    }

    private static void throwItemsTowardPos(Piglin piglin, List<ItemStack> list, Vec3 vec3) {
        if (!list.isEmpty()) {
            piglin.swing(InteractionHand.OFF_HAND);
            for (ItemStack itemStack : list) {
                BehaviorUtils.throwItem((LivingEntity)piglin, (ItemStack)itemStack, (Vec3)vec3.add(0.0, 1.0, 0.0));
            }
        }
    }

    private static List<ItemStack> getBarterResponseItems(Piglin piglin) {
        LootTable lootTable = piglin.level.getServer().getLootTables().get(BuiltInLootTables.PIGLIN_BARTERING);
        List list = lootTable.getRandomItems(new LootContext.Builder((ServerLevel)piglin.level).withParameter(LootContextParams.THIS_ENTITY, (Object)piglin).withRandom(piglin.level.random).create(LootContextParamSets.PIGLIN_BARTER));
        return list;
    }

    private static boolean wantsToDance(LivingEntity livingEntity, LivingEntity livingEntity2) {
        if (livingEntity2.getType() != EntityType.HOGLIN) {
            return false;
        }
        return new Random(livingEntity.level.getGameTime()).nextFloat() < 0.1f;
    }

    protected static boolean wantsToPickup(Piglin piglin, ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (item.is((Tag)ItemTags.PIGLIN_REPELLENTS)) {
            return false;
        }
        if (PiglinAi.isAdmiringDisabled(piglin) && piglin.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
            return false;
        }
        if (PiglinAi.isBarterCurrency(item)) {
            return PiglinAi.isNotHoldingLovedItemInOffHand(piglin);
        }
        boolean bl = piglin.canAddToInventory(itemStack);
        if (item == Items.GOLD_NUGGET) {
            return bl;
        }
        if (PiglinAi.isFood(item)) {
            return !PiglinAi.hasEatenRecently(piglin) && bl;
        }
        if (PiglinAi.isLovedItem(item)) {
            return PiglinAi.isNotHoldingLovedItemInOffHand(piglin) && bl;
        }
        return piglin.canReplaceCurrentItem(itemStack);
    }

    protected static boolean isLovedItem(Item item) {
        return item.is((Tag)ItemTags.PIGLIN_LOVED);
    }

    private static boolean wantsToStopRiding(Piglin piglin, Entity entity) {
        if (entity instanceof Mob) {
            Mob mob = (Mob)entity;
            return !mob.isBaby() || !mob.isAlive() || PiglinAi.wasHurtRecently((LivingEntity)piglin) || PiglinAi.wasHurtRecently((LivingEntity)mob) || mob instanceof Piglin && mob.getVehicle() == null;
        }
        return false;
    }

    private static boolean isNearestValidAttackTarget(Piglin piglin, LivingEntity livingEntity) {
        return PiglinAi.findNearestValidAttackTarget(piglin).filter(livingEntity2 -> livingEntity2 == livingEntity).isPresent();
    }

    private static boolean isNearZombified(Piglin piglin) {
        Brain brain = piglin.getBrain();
        if (brain.hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED)) {
            LivingEntity livingEntity = (LivingEntity)brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED).get();
            return piglin.closerThan((Entity)livingEntity, 6.0);
        }
        return false;
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(Piglin piglin) {
        Optional optional2;
        Brain brain = piglin.getBrain();
        if (PiglinAi.isNearZombified(piglin)) {
            return Optional.empty();
        }
        Optional optional = BehaviorUtils.getLivingEntityFromUUIDMemory((LivingEntity)piglin, (MemoryModuleType)MemoryModuleType.ANGRY_AT);
        if (optional.isPresent() && PiglinAi.isAttackAllowed((LivingEntity)optional.get())) {
            return optional;
        }
        if (brain.hasMemoryValue(MemoryModuleType.UNIVERSAL_ANGER) && (optional2 = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER)).isPresent()) {
            return optional2;
        }
        optional2 = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
        if (optional2.isPresent()) {
            return optional2;
        }
        Optional optional3 = brain.getMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD);
        if (optional3.isPresent() && PiglinAi.isAttackAllowed((LivingEntity)optional3.get())) {
            return optional3;
        }
        return Optional.empty();
    }

    public static void angerNearbyPiglins(Player player, boolean bl) {
        List list = player.level.getEntitiesOfClass(Piglin.class, player.getBoundingBox().inflate(16.0));
        list.stream().filter(PiglinAi::isIdle).filter(piglin -> !bl || BehaviorUtils.canSee((LivingEntity)piglin, (LivingEntity)player)).forEach(piglin -> {
            if (piglin.level.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                PiglinAi.setAngerTargetToNearestTargetablePlayerIfFound((AbstractPiglin)piglin, (LivingEntity)player);
            } else {
                PiglinAi.setAngerTarget((AbstractPiglin)piglin, (LivingEntity)player);
            }
        });
    }

    public static InteractionResult mobInteract(Piglin piglin, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (PiglinAi.canAdmire(piglin, itemStack)) {
            ItemStack itemStack2 = itemStack.split(1);
            PiglinAi.holdInOffhand(piglin, itemStack2);
            PiglinAi.admireGoldItem((LivingEntity)piglin);
            PiglinAi.stopWalking(piglin);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    protected static boolean canAdmire(Piglin piglin, ItemStack itemStack) {
        return !PiglinAi.isAdmiringDisabled(piglin) && !PiglinAi.isAdmiringItem(piglin) && piglin.isAdult() && PiglinAi.isBarterCurrency(itemStack.getItem());
    }

    protected static void wasHurtBy(Piglin piglin, LivingEntity livingEntity) {
        if (livingEntity instanceof Piglin) {
            return;
        }
        if (PiglinAi.isHoldingItemInOffHand(piglin)) {
            PiglinAi.stopHoldingOffHandItem(piglin, false);
        }
        Brain brain = piglin.getBrain();
        brain.eraseMemory(MemoryModuleType.CELEBRATE_LOCATION);
        brain.eraseMemory(MemoryModuleType.DANCING);
        brain.eraseMemory(MemoryModuleType.ADMIRING_ITEM);
        if (livingEntity instanceof Player) {
            brain.setMemoryWithExpiry(MemoryModuleType.ADMIRING_DISABLED, (Object)true, 400L);
        }
        PiglinAi.getAvoidTarget(piglin).ifPresent(livingEntity2 -> {
            if (livingEntity2.getType() != livingEntity.getType()) {
                brain.eraseMemory(MemoryModuleType.AVOID_TARGET);
            }
        });
        if (piglin.isBaby()) {
            brain.setMemoryWithExpiry(MemoryModuleType.AVOID_TARGET, (Object)livingEntity, 100L);
            if (PiglinAi.isAttackAllowed(livingEntity)) {
                PiglinAi.broadcastAngerTarget((AbstractPiglin)piglin, livingEntity);
            }
            return;
        }
        if (livingEntity.getType() == EntityType.HOGLIN && PiglinAi.hoglinsOutnumberPiglins(piglin)) {
            PiglinAi.setAvoidTargetAndDontHuntForAWhile(piglin, livingEntity);
            PiglinAi.broadcastRetreat(piglin, livingEntity);
            return;
        }
        PiglinAi.maybeRetaliate((AbstractPiglin)piglin, livingEntity);
    }

    protected static void maybeRetaliate(AbstractPiglin abstractPiglin, LivingEntity livingEntity) {
        if (abstractPiglin.getBrain().isActive(Activity.AVOID)) {
            return;
        }
        if (!PiglinAi.isAttackAllowed(livingEntity)) {
            return;
        }
        if (BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget((LivingEntity)abstractPiglin, (LivingEntity)livingEntity, (double)4.0)) {
            return;
        }
        if (livingEntity.getType() == EntityType.PLAYER && abstractPiglin.level.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
            PiglinAi.setAngerTargetToNearestTargetablePlayerIfFound(abstractPiglin, livingEntity);
            PiglinAi.broadcastUniversalAnger(abstractPiglin);
        } else {
            PiglinAi.setAngerTarget(abstractPiglin, livingEntity);
            PiglinAi.broadcastAngerTarget(abstractPiglin, livingEntity);
        }
    }

    public static Optional<SoundEvent> getSoundForCurrentActivity(Piglin piglin) {
        return piglin.getBrain().getActiveNonCoreActivity().map(activity -> PiglinAi.getSoundForActivity(piglin, activity));
    }

    private static SoundEvent getSoundForActivity(Piglin piglin, Activity activity) {
        if (activity == Activity.FIGHT) {
            return SoundEvents.PIGLIN_ANGRY;
        }
        if (piglin.isConverting()) {
            return SoundEvents.PIGLIN_RETREAT;
        }
        if (activity == Activity.AVOID && PiglinAi.isNearAvoidTarget(piglin)) {
            return SoundEvents.PIGLIN_RETREAT;
        }
        if (activity == Activity.ADMIRE_ITEM) {
            return SoundEvents.PIGLIN_ADMIRING_ITEM;
        }
        if (activity == Activity.CELEBRATE) {
            return SoundEvents.PIGLIN_CELEBRATE;
        }
        if (PiglinAi.seesPlayerHoldingLovedItem((LivingEntity)piglin)) {
            return SoundEvents.PIGLIN_JEALOUS;
        }
        if (PiglinAi.isNearRepellent(piglin)) {
            return SoundEvents.PIGLIN_RETREAT;
        }
        return SoundEvents.PIGLIN_AMBIENT;
    }

    private static boolean isNearAvoidTarget(Piglin piglin) {
        Brain brain = piglin.getBrain();
        if (!brain.hasMemoryValue(MemoryModuleType.AVOID_TARGET)) {
            return false;
        }
        return ((LivingEntity)brain.getMemory(MemoryModuleType.AVOID_TARGET).get()).closerThan((Entity)piglin, 12.0);
    }

    protected static boolean hasAnyoneNearbyHuntedRecently(Piglin piglin) {
        return piglin.getBrain().hasMemoryValue(MemoryModuleType.HUNTED_RECENTLY) || PiglinAi.getVisibleAdultPiglins(piglin).stream().anyMatch(abstractPiglin -> abstractPiglin.getBrain().hasMemoryValue(MemoryModuleType.HUNTED_RECENTLY));
    }

    private static List<AbstractPiglin> getVisibleAdultPiglins(Piglin piglin) {
        return (List)piglin.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS).orElse(ImmutableList.of());
    }

    private static List<AbstractPiglin> getAdultPiglins(AbstractPiglin abstractPiglin) {
        return (List)abstractPiglin.getBrain().getMemory(MemoryModuleType.NEARBY_ADULT_PIGLINS).orElse(ImmutableList.of());
    }

    public static boolean isWearingGold(LivingEntity livingEntity) {
        Iterable iterable = livingEntity.getArmorSlots();
        for (ItemStack itemStack : iterable) {
            Item item = itemStack.getItem();
            if (!(item instanceof ArmorItem) || ((ArmorItem)item).getMaterial() != ArmorMaterials.GOLD) continue;
            return true;
        }
        return false;
    }

    private static void stopWalking(Piglin piglin) {
        piglin.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        piglin.getNavigation().stop();
    }

    private static RunSometimes<Piglin> babySometimesRideBabyHoglin() {
        return new RunSometimes((Behavior)new CopyMemoryWithExpiry(Piglin::isBaby, MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, MemoryModuleType.RIDE_TARGET, RIDE_DURATION), RIDE_START_INTERVAL);
    }

    protected static void broadcastAngerTarget(AbstractPiglin abstractPiglin2, LivingEntity livingEntity) {
        PiglinAi.getAdultPiglins(abstractPiglin2).forEach(abstractPiglin -> {
            if (!(livingEntity.getType() != EntityType.HOGLIN || abstractPiglin.canHunt() && ((Hoglin)livingEntity).canBeHunted())) {
                return;
            }
            PiglinAi.setAngerTargetIfCloserThanCurrent(abstractPiglin, livingEntity);
        });
    }

    protected static void broadcastUniversalAnger(AbstractPiglin abstractPiglin2) {
        PiglinAi.getAdultPiglins(abstractPiglin2).forEach(abstractPiglin -> PiglinAi.getNearestVisibleTargetablePlayer(abstractPiglin).ifPresent(player -> PiglinAi.setAngerTarget(abstractPiglin, (LivingEntity)player)));
    }

    protected static void broadcastDontKillAnyMoreHoglinsForAWhile(Piglin piglin) {
        PiglinAi.getVisibleAdultPiglins(piglin).forEach(PiglinAi::dontKillAnyMoreHoglinsForAWhile);
    }

    protected static void setAngerTarget(AbstractPiglin abstractPiglin, LivingEntity livingEntity) {
        if (!PiglinAi.isAttackAllowed(livingEntity)) {
            return;
        }
        abstractPiglin.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        abstractPiglin.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, (Object)livingEntity.getUUID(), 600L);
        if (livingEntity.getType() == EntityType.HOGLIN && abstractPiglin.canHunt()) {
            PiglinAi.dontKillAnyMoreHoglinsForAWhile(abstractPiglin);
        }
        if (livingEntity.getType() == EntityType.PLAYER && abstractPiglin.level.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
            abstractPiglin.getBrain().setMemoryWithExpiry(MemoryModuleType.UNIVERSAL_ANGER, (Object)true, 600L);
        }
    }

    private static void setAngerTargetToNearestTargetablePlayerIfFound(AbstractPiglin abstractPiglin, LivingEntity livingEntity) {
        Optional<Player> optional = PiglinAi.getNearestVisibleTargetablePlayer(abstractPiglin);
        if (optional.isPresent()) {
            PiglinAi.setAngerTarget(abstractPiglin, (LivingEntity)optional.get());
        } else {
            PiglinAi.setAngerTarget(abstractPiglin, livingEntity);
        }
    }

    private static void setAngerTargetIfCloserThanCurrent(AbstractPiglin abstractPiglin, LivingEntity livingEntity) {
        Optional<LivingEntity> optional = PiglinAi.getAngerTarget(abstractPiglin);
        LivingEntity livingEntity2 = BehaviorUtils.getNearestTarget((LivingEntity)abstractPiglin, optional, (LivingEntity)livingEntity);
        if (optional.isPresent() && optional.get() == livingEntity2) {
            return;
        }
        PiglinAi.setAngerTarget(abstractPiglin, livingEntity2);
    }

    private static Optional<LivingEntity> getAngerTarget(AbstractPiglin abstractPiglin) {
        return BehaviorUtils.getLivingEntityFromUUIDMemory((LivingEntity)abstractPiglin, (MemoryModuleType)MemoryModuleType.ANGRY_AT);
    }

    public static Optional<LivingEntity> getAvoidTarget(Piglin piglin) {
        if (piglin.getBrain().hasMemoryValue(MemoryModuleType.AVOID_TARGET)) {
            return piglin.getBrain().getMemory(MemoryModuleType.AVOID_TARGET);
        }
        return Optional.empty();
    }

    public static Optional<Player> getNearestVisibleTargetablePlayer(AbstractPiglin abstractPiglin) {
        if (abstractPiglin.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER)) {
            return abstractPiglin.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);
        }
        return Optional.empty();
    }

    private static void broadcastRetreat(Piglin piglin, LivingEntity livingEntity) {
        PiglinAi.getVisibleAdultPiglins(piglin).stream().filter(abstractPiglin -> abstractPiglin instanceof Piglin).forEach(abstractPiglin -> PiglinAi.retreatFromNearestTarget((Piglin)abstractPiglin, livingEntity));
    }

    private static void retreatFromNearestTarget(Piglin piglin, LivingEntity livingEntity) {
        Brain brain = piglin.getBrain();
        LivingEntity livingEntity2 = livingEntity;
        livingEntity2 = BehaviorUtils.getNearestTarget((LivingEntity)piglin, (Optional)brain.getMemory(MemoryModuleType.AVOID_TARGET), (LivingEntity)livingEntity2);
        livingEntity2 = BehaviorUtils.getNearestTarget((LivingEntity)piglin, (Optional)brain.getMemory(MemoryModuleType.ATTACK_TARGET), (LivingEntity)livingEntity2);
        PiglinAi.setAvoidTargetAndDontHuntForAWhile(piglin, livingEntity2);
    }

    private static boolean wantsToStopFleeing(Piglin piglin) {
        Brain brain = piglin.getBrain();
        if (!brain.hasMemoryValue(MemoryModuleType.AVOID_TARGET)) {
            return true;
        }
        LivingEntity livingEntity = (LivingEntity)brain.getMemory(MemoryModuleType.AVOID_TARGET).get();
        EntityType entityType = livingEntity.getType();
        if (entityType == EntityType.HOGLIN) {
            return PiglinAi.piglinsEqualOrOutnumberHoglins(piglin);
        }
        if (PiglinAi.isZombified(entityType)) {
            return !brain.isMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, (Object)livingEntity);
        }
        return false;
    }

    private static boolean piglinsEqualOrOutnumberHoglins(Piglin piglin) {
        return !PiglinAi.hoglinsOutnumberPiglins(piglin);
    }

    private static boolean hoglinsOutnumberPiglins(Piglin piglin) {
        int i = piglin.getBrain().getMemory(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT).orElse(0) + 1;
        int j = piglin.getBrain().getMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT).orElse(0);
        return j > i;
    }

    private static void setAvoidTargetAndDontHuntForAWhile(Piglin piglin, LivingEntity livingEntity) {
        piglin.getBrain().eraseMemory(MemoryModuleType.ANGRY_AT);
        piglin.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
        piglin.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        piglin.getBrain().setMemoryWithExpiry(MemoryModuleType.AVOID_TARGET, (Object)livingEntity, (long)RETREAT_DURATION.randomValue(piglin.level.random));
        PiglinAi.dontKillAnyMoreHoglinsForAWhile((AbstractPiglin)piglin);
    }

    protected static void dontKillAnyMoreHoglinsForAWhile(AbstractPiglin abstractPiglin) {
        abstractPiglin.getBrain().setMemoryWithExpiry(MemoryModuleType.HUNTED_RECENTLY, (Object)true, (long)TIME_BETWEEN_HUNTS.randomValue(abstractPiglin.level.random));
    }

    private static void eat(Piglin piglin) {
        piglin.getBrain().setMemoryWithExpiry(MemoryModuleType.ATE_RECENTLY, (Object)true, 200L);
    }

    private static Vec3 getRandomNearbyPos(Piglin piglin) {
        Vec3 vec3 = RandomPos.getLandPos((PathfinderMob)piglin, (int)4, (int)2);
        return vec3 == null ? piglin.position() : vec3;
    }

    private static boolean hasEatenRecently(Piglin piglin) {
        return piglin.getBrain().hasMemoryValue(MemoryModuleType.ATE_RECENTLY);
    }

    protected static boolean isIdle(AbstractPiglin abstractPiglin) {
        return abstractPiglin.getBrain().isActive(Activity.IDLE);
    }

    private static boolean hasCrossbow(LivingEntity livingEntity) {
        return livingEntity.isHolding(Items.CROSSBOW);
    }

    private static void admireGoldItem(LivingEntity livingEntity) {
        livingEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.ADMIRING_ITEM, (Object)true, 120L);
    }

    private static boolean isAdmiringItem(Piglin piglin) {
        return piglin.getBrain().hasMemoryValue(MemoryModuleType.ADMIRING_ITEM);
    }

    private static boolean isBarterCurrency(Item item) {
        return item == BARTERING_ITEM;
    }

    private static boolean isFood(Item item) {
        return FOOD_ITEMS.contains(item);
    }

    private static boolean isAttackAllowed(LivingEntity livingEntity) {
        return EntitySelector.ATTACK_ALLOWED.test(livingEntity);
    }

    private static boolean isNearRepellent(Piglin piglin) {
        return piglin.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_REPELLENT);
    }

    private static boolean seesPlayerHoldingLovedItem(LivingEntity livingEntity) {
        return livingEntity.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM);
    }

    private static boolean doesntSeeAnyPlayerHoldingLovedItem(LivingEntity livingEntity) {
        return !PiglinAi.seesPlayerHoldingLovedItem(livingEntity);
    }

    public static boolean isPlayerHoldingLovedItem(LivingEntity livingEntity) {
        return livingEntity.getType() == EntityType.PLAYER && livingEntity.isHolding(PiglinAi::isLovedItem);
    }

    private static boolean isAdmiringDisabled(Piglin piglin) {
        return piglin.getBrain().hasMemoryValue(MemoryModuleType.ADMIRING_DISABLED);
    }

    private static boolean wasHurtRecently(LivingEntity livingEntity) {
        return livingEntity.getBrain().hasMemoryValue(MemoryModuleType.HURT_BY);
    }

    private static boolean isHoldingItemInOffHand(Piglin piglin) {
        return !piglin.getOffhandItem().isEmpty();
    }

    private static boolean isNotHoldingLovedItemInOffHand(Piglin piglin) {
        return piglin.getOffhandItem().isEmpty() || !PiglinAi.isLovedItem(piglin.getOffhandItem().getItem());
    }

    public static boolean isZombified(EntityType entityType) {
        return entityType == EntityType.ZOMBIFIED_PIGLIN || entityType == EntityType.ZOGLIN;
    }
}
