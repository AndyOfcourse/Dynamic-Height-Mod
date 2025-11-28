/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Registry
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.SpawnPlacements$Data
 *  net.minecraft.world.entity.SpawnPlacements$SpawnPredicate
 *  net.minecraft.world.entity.SpawnPlacements$Type
 *  net.minecraft.world.entity.ambient.Bat
 *  net.minecraft.world.entity.animal.AbstractFish
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.entity.animal.Dolphin
 *  net.minecraft.world.entity.animal.MushroomCow
 *  net.minecraft.world.entity.animal.Ocelot
 *  net.minecraft.world.entity.animal.Parrot
 *  net.minecraft.world.entity.animal.PolarBear
 *  net.minecraft.world.entity.animal.Rabbit
 *  net.minecraft.world.entity.animal.Squid
 *  net.minecraft.world.entity.animal.Turtle
 *  net.minecraft.world.entity.monster.Drowned
 *  net.minecraft.world.entity.monster.Endermite
 *  net.minecraft.world.entity.monster.Ghast
 *  net.minecraft.world.entity.monster.Guardian
 *  net.minecraft.world.entity.monster.Husk
 *  net.minecraft.world.entity.monster.MagmaCube
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.monster.PatrollingMonster
 *  net.minecraft.world.entity.monster.Silverfish
 *  net.minecraft.world.entity.monster.Slime
 *  net.minecraft.world.entity.monster.Stray
 *  net.minecraft.world.entity.monster.Strider
 *  net.minecraft.world.entity.monster.ZombifiedPiglin
 *  net.minecraft.world.entity.monster.hoglin.Hoglin
 *  net.minecraft.world.entity.monster.piglin.Piglin
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.world.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class SpawnPlacements {
    private static final Map<EntityType<?>, Data> DATA_BY_TYPE = Maps.newHashMap();

    private static <T extends Mob> void register(EntityType<T> entityType, Type type, Heightmap.Types types, SpawnPredicate<T> spawnPredicate) {
        Data data = DATA_BY_TYPE.put(entityType, new Data(types, type, spawnPredicate));
        if (data != null) {
            throw new IllegalStateException("Duplicate registration for type " + Registry.ENTITY_TYPE.getKey(entityType));
        }
    }

    public static Type getPlacementType(EntityType<?> entityType) {
        Data data = DATA_BY_TYPE.get(entityType);
        return data == null ? Type.NO_RESTRICTIONS : Data.method_6164((Data)data);
    }

    public static Heightmap.Types getHeightmapType(@Nullable EntityType<?> entityType) {
        Data data = DATA_BY_TYPE.get(entityType);
        return data == null ? Heightmap.Types.MOTION_BLOCKING_NO_LEAVES : Data.method_6163((Data)data);
    }

    public static <T extends Entity> boolean checkSpawnRules(EntityType<T> entityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, Random random) {
        Data data = DATA_BY_TYPE.get(entityType);
        return data == null || Data.method_20639((Data)data).test(entityType, serverLevelAccessor, mobSpawnType, blockPos, random);
    }

    static {
        SpawnPlacements.register(EntityType.COD, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkFishSpawnRules);
        SpawnPlacements.register(EntityType.DOLPHIN, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dolphin::checkDolphinSpawnRules);
        SpawnPlacements.register(EntityType.DROWNED, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Drowned::checkDrownedSpawnRules);
        SpawnPlacements.register(EntityType.GUARDIAN, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Guardian::checkGuardianSpawnRules);
        SpawnPlacements.register(EntityType.PUFFERFISH, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkFishSpawnRules);
        SpawnPlacements.register(EntityType.SALMON, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkFishSpawnRules);
        SpawnPlacements.register(EntityType.SQUID, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Squid::checkSquidSpawnRules);
        SpawnPlacements.register(EntityType.TROPICAL_FISH, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkFishSpawnRules);
        SpawnPlacements.register(EntityType.BAT, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Bat::checkBatSpawnRules);
        SpawnPlacements.register(EntityType.BLAZE, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
        SpawnPlacements.register(EntityType.CAVE_SPIDER, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(EntityType.CHICKEN, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(EntityType.COW, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(EntityType.CREEPER, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(EntityType.DONKEY, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(EntityType.ENDERMAN, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(EntityType.ENDERMITE, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Endermite::checkEndermiteSpawnRules);
        SpawnPlacements.register(EntityType.ENDER_DRAGON, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(EntityType.GHAST, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Ghast::checkGhastSpawnRules);
        SpawnPlacements.register(EntityType.GIANT, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(EntityType.HORSE, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(EntityType.HUSK, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Husk::checkHuskSpawnRules);
        SpawnPlacements.register(EntityType.IRON_GOLEM, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(EntityType.LLAMA, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(EntityType.MAGMA_CUBE, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MagmaCube::checkMagmaCubeSpawnRules);
        SpawnPlacements.register(EntityType.MOOSHROOM, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MushroomCow::checkMushroomSpawnRules);
        SpawnPlacements.register(EntityType.MULE, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(EntityType.OCELOT, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Ocelot::checkOcelotSpawnRules);
        SpawnPlacements.register(EntityType.PARROT, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Parrot::checkParrotSpawnRules);
        SpawnPlacements.register(EntityType.PIG, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(EntityType.HOGLIN, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Hoglin::checkHoglinSpawnRules);
        SpawnPlacements.register(EntityType.PIGLIN, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Piglin::checkPiglinSpawnRules);
        SpawnPlacements.register(EntityType.PILLAGER, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules);
        SpawnPlacements.register(EntityType.POLAR_BEAR, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PolarBear::checkPolarBearSpawnRules);
        SpawnPlacements.register(EntityType.RABBIT, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Rabbit::checkRabbitSpawnRules);
        SpawnPlacements.register(EntityType.SHEEP, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(EntityType.SILVERFISH, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Silverfish::checkSliverfishSpawnRules);
        SpawnPlacements.register(EntityType.SKELETON, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(EntityType.SKELETON_HORSE, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(EntityType.SLIME, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Slime::checkSlimeSpawnRules);
        SpawnPlacements.register(EntityType.SNOW_GOLEM, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(EntityType.SPIDER, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(EntityType.STRAY, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Stray::checkStraySpawnRules);
        SpawnPlacements.register(EntityType.STRIDER, Type.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Strider::checkStriderSpawnRules);
        SpawnPlacements.register(EntityType.TURTLE, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Turtle::checkTurtleSpawnRules);
        SpawnPlacements.register(EntityType.VILLAGER, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(EntityType.WITCH, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(EntityType.WITHER, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(EntityType.WITHER_SKELETON, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(EntityType.WOLF, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(EntityType.ZOMBIE, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(EntityType.ZOMBIE_HORSE, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(EntityType.ZOMBIFIED_PIGLIN, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZombifiedPiglin::checkZombifiedPiglinSpawnRules);
        SpawnPlacements.register(EntityType.ZOMBIE_VILLAGER, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(EntityType.CAT, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(EntityType.ELDER_GUARDIAN, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Guardian::checkGuardianSpawnRules);
        SpawnPlacements.register(EntityType.EVOKER, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(EntityType.FOX, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(EntityType.ILLUSIONER, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(EntityType.PANDA, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(EntityType.PHANTOM, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(EntityType.RAVAGER, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(EntityType.SHULKER, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(EntityType.TRADER_LLAMA, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(EntityType.VEX, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(EntityType.VINDICATOR, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(EntityType.WANDERING_TRADER, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
    }
}
