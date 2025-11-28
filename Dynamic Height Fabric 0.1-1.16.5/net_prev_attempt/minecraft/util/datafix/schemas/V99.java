/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.Hook$HookFunction
 *  com.mojang.datafixers.types.templates.TypeTemplate
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.util.datafix.schemas;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.Hook;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class V99
extends Schema {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<String, String> ITEM_TO_BLOCKENTITY = (Map)DataFixUtils.make((Object)Maps.newHashMap(), hashMap -> {
        hashMap.put("minecraft:furnace", "Furnace");
        hashMap.put("minecraft:lit_furnace", "Furnace");
        hashMap.put("minecraft:chest", "Chest");
        hashMap.put("minecraft:trapped_chest", "Chest");
        hashMap.put("minecraft:ender_chest", "EnderChest");
        hashMap.put("minecraft:jukebox", "RecordPlayer");
        hashMap.put("minecraft:dispenser", "Trap");
        hashMap.put("minecraft:dropper", "Dropper");
        hashMap.put("minecraft:sign", "Sign");
        hashMap.put("minecraft:mob_spawner", "MobSpawner");
        hashMap.put("minecraft:noteblock", "Music");
        hashMap.put("minecraft:brewing_stand", "Cauldron");
        hashMap.put("minecraft:enhanting_table", "EnchantTable");
        hashMap.put("minecraft:command_block", "CommandBlock");
        hashMap.put("minecraft:beacon", "Beacon");
        hashMap.put("minecraft:skull", "Skull");
        hashMap.put("minecraft:daylight_detector", "DLDetector");
        hashMap.put("minecraft:hopper", "Hopper");
        hashMap.put("minecraft:banner", "Banner");
        hashMap.put("minecraft:flower_pot", "FlowerPot");
        hashMap.put("minecraft:repeating_command_block", "CommandBlock");
        hashMap.put("minecraft:chain_command_block", "CommandBlock");
        hashMap.put("minecraft:standing_sign", "Sign");
        hashMap.put("minecraft:wall_sign", "Sign");
        hashMap.put("minecraft:piston_head", "Piston");
        hashMap.put("minecraft:daylight_detector_inverted", "DLDetector");
        hashMap.put("minecraft:unpowered_comparator", "Comparator");
        hashMap.put("minecraft:powered_comparator", "Comparator");
        hashMap.put("minecraft:wall_banner", "Banner");
        hashMap.put("minecraft:standing_banner", "Banner");
        hashMap.put("minecraft:structure_block", "Structure");
        hashMap.put("minecraft:end_portal", "Airportal");
        hashMap.put("minecraft:end_gateway", "EndGateway");
        hashMap.put("minecraft:shield", "Banner");
    });
    protected static final Hook.HookFunction ADD_NAMES = new Hook.HookFunction(){

        public <T> T apply(DynamicOps<T> dynamicOps, T object) {
            return V99.addNames(new Dynamic(dynamicOps, object), ITEM_TO_BLOCKENTITY, "ArmorStand");
        }
    };

    public V99(int i, Schema schema) {
        super(i, schema);
    }

    protected static TypeTemplate equipment(Schema schema) {
        return DSL.optionalFields((String)"Equipment", (TypeTemplate)DSL.list((TypeTemplate)References.ITEM_STACK.in(schema)));
    }

    protected static void registerMob(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V99.equipment(schema));
    }

    protected static void registerThrowableProjectile(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> DSL.optionalFields((String)"inTile", (TypeTemplate)References.BLOCK_NAME.in(schema)));
    }

    protected static void registerMinecart(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.BLOCK_NAME.in(schema)));
    }

    protected static void registerInventory(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.ITEM_STACK.in(schema))));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        HashMap map = Maps.newHashMap();
        schema.register((Map)map, "Item", string -> DSL.optionalFields((String)"Item", (TypeTemplate)References.ITEM_STACK.in(schema)));
        schema.registerSimple((Map)map, "XPOrb");
        V99.registerThrowableProjectile(schema, map, "ThrownEgg");
        schema.registerSimple((Map)map, "LeashKnot");
        schema.registerSimple((Map)map, "Painting");
        schema.register((Map)map, "Arrow", string -> DSL.optionalFields((String)"inTile", (TypeTemplate)References.BLOCK_NAME.in(schema)));
        schema.register((Map)map, "TippedArrow", string -> DSL.optionalFields((String)"inTile", (TypeTemplate)References.BLOCK_NAME.in(schema)));
        schema.register((Map)map, "SpectralArrow", string -> DSL.optionalFields((String)"inTile", (TypeTemplate)References.BLOCK_NAME.in(schema)));
        V99.registerThrowableProjectile(schema, map, "Snowball");
        V99.registerThrowableProjectile(schema, map, "Fireball");
        V99.registerThrowableProjectile(schema, map, "SmallFireball");
        V99.registerThrowableProjectile(schema, map, "ThrownEnderpearl");
        schema.registerSimple((Map)map, "EyeOfEnderSignal");
        schema.register((Map)map, "ThrownPotion", string -> DSL.optionalFields((String)"inTile", (TypeTemplate)References.BLOCK_NAME.in(schema), (String)"Potion", (TypeTemplate)References.ITEM_STACK.in(schema)));
        V99.registerThrowableProjectile(schema, map, "ThrownExpBottle");
        schema.register((Map)map, "ItemFrame", string -> DSL.optionalFields((String)"Item", (TypeTemplate)References.ITEM_STACK.in(schema)));
        V99.registerThrowableProjectile(schema, map, "WitherSkull");
        schema.registerSimple((Map)map, "PrimedTnt");
        schema.register((Map)map, "FallingSand", string -> DSL.optionalFields((String)"Block", (TypeTemplate)References.BLOCK_NAME.in(schema), (String)"TileEntityData", (TypeTemplate)References.BLOCK_ENTITY.in(schema)));
        schema.register((Map)map, "FireworksRocketEntity", string -> DSL.optionalFields((String)"FireworksItem", (TypeTemplate)References.ITEM_STACK.in(schema)));
        schema.registerSimple((Map)map, "Boat");
        schema.register((Map)map, "Minecart", () -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.BLOCK_NAME.in(schema), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.ITEM_STACK.in(schema))));
        V99.registerMinecart(schema, map, "MinecartRideable");
        schema.register((Map)map, "MinecartChest", string -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.BLOCK_NAME.in(schema), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.ITEM_STACK.in(schema))));
        V99.registerMinecart(schema, map, "MinecartFurnace");
        V99.registerMinecart(schema, map, "MinecartTNT");
        schema.register((Map)map, "MinecartSpawner", () -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.BLOCK_NAME.in(schema), (TypeTemplate)References.UNTAGGED_SPAWNER.in(schema)));
        schema.register((Map)map, "MinecartHopper", string -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.BLOCK_NAME.in(schema), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.ITEM_STACK.in(schema))));
        V99.registerMinecart(schema, map, "MinecartCommandBlock");
        V99.registerMob(schema, map, "ArmorStand");
        V99.registerMob(schema, map, "Creeper");
        V99.registerMob(schema, map, "Skeleton");
        V99.registerMob(schema, map, "Spider");
        V99.registerMob(schema, map, "Giant");
        V99.registerMob(schema, map, "Zombie");
        V99.registerMob(schema, map, "Slime");
        V99.registerMob(schema, map, "Ghast");
        V99.registerMob(schema, map, "PigZombie");
        schema.register((Map)map, "Enderman", string -> DSL.optionalFields((String)"carried", (TypeTemplate)References.BLOCK_NAME.in(schema), (TypeTemplate)V99.equipment(schema)));
        V99.registerMob(schema, map, "CaveSpider");
        V99.registerMob(schema, map, "Silverfish");
        V99.registerMob(schema, map, "Blaze");
        V99.registerMob(schema, map, "LavaSlime");
        V99.registerMob(schema, map, "EnderDragon");
        V99.registerMob(schema, map, "WitherBoss");
        V99.registerMob(schema, map, "Bat");
        V99.registerMob(schema, map, "Witch");
        V99.registerMob(schema, map, "Endermite");
        V99.registerMob(schema, map, "Guardian");
        V99.registerMob(schema, map, "Pig");
        V99.registerMob(schema, map, "Sheep");
        V99.registerMob(schema, map, "Cow");
        V99.registerMob(schema, map, "Chicken");
        V99.registerMob(schema, map, "Squid");
        V99.registerMob(schema, map, "Wolf");
        V99.registerMob(schema, map, "MushroomCow");
        V99.registerMob(schema, map, "SnowMan");
        V99.registerMob(schema, map, "Ozelot");
        V99.registerMob(schema, map, "VillagerGolem");
        schema.register((Map)map, "EntityHorse", string -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.ITEM_STACK.in(schema)), (String)"ArmorItem", (TypeTemplate)References.ITEM_STACK.in(schema), (String)"SaddleItem", (TypeTemplate)References.ITEM_STACK.in(schema), (TypeTemplate)V99.equipment(schema)));
        V99.registerMob(schema, map, "Rabbit");
        schema.register((Map)map, "Villager", string -> DSL.optionalFields((String)"Inventory", (TypeTemplate)DSL.list((TypeTemplate)References.ITEM_STACK.in(schema)), (String)"Offers", (TypeTemplate)DSL.optionalFields((String)"Recipes", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"buy", (TypeTemplate)References.ITEM_STACK.in(schema), (String)"buyB", (TypeTemplate)References.ITEM_STACK.in(schema), (String)"sell", (TypeTemplate)References.ITEM_STACK.in(schema)))), (TypeTemplate)V99.equipment(schema)));
        schema.registerSimple((Map)map, "EnderCrystal");
        schema.registerSimple((Map)map, "AreaEffectCloud");
        schema.registerSimple((Map)map, "ShulkerBullet");
        V99.registerMob(schema, map, "Shulker");
        return map;
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
        HashMap map = Maps.newHashMap();
        V99.registerInventory(schema, map, "Furnace");
        V99.registerInventory(schema, map, "Chest");
        schema.registerSimple((Map)map, "EnderChest");
        schema.register((Map)map, "RecordPlayer", string -> DSL.optionalFields((String)"RecordItem", (TypeTemplate)References.ITEM_STACK.in(schema)));
        V99.registerInventory(schema, map, "Trap");
        V99.registerInventory(schema, map, "Dropper");
        schema.registerSimple((Map)map, "Sign");
        schema.register((Map)map, "MobSpawner", string -> References.UNTAGGED_SPAWNER.in(schema));
        schema.registerSimple((Map)map, "Music");
        schema.registerSimple((Map)map, "Piston");
        V99.registerInventory(schema, map, "Cauldron");
        schema.registerSimple((Map)map, "EnchantTable");
        schema.registerSimple((Map)map, "Airportal");
        schema.registerSimple((Map)map, "Control");
        schema.registerSimple((Map)map, "Beacon");
        schema.registerSimple((Map)map, "Skull");
        schema.registerSimple((Map)map, "DLDetector");
        V99.registerInventory(schema, map, "Hopper");
        schema.registerSimple((Map)map, "Comparator");
        schema.register((Map)map, "FlowerPot", string -> DSL.optionalFields((String)"Item", (TypeTemplate)DSL.or((TypeTemplate)DSL.constType((Type)DSL.intType()), (TypeTemplate)References.ITEM_NAME.in(schema))));
        schema.registerSimple((Map)map, "Banner");
        schema.registerSimple((Map)map, "Structure");
        schema.registerSimple((Map)map, "EndGateway");
        return map;
    }

    public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map, Map<String, Supplier<TypeTemplate>> map2) {
        schema.registerType(false, References.LEVEL, DSL::remainder);
        schema.registerType(false, References.PLAYER, () -> DSL.optionalFields((String)"Inventory", (TypeTemplate)DSL.list((TypeTemplate)References.ITEM_STACK.in(schema)), (String)"EnderItems", (TypeTemplate)DSL.list((TypeTemplate)References.ITEM_STACK.in(schema))));
        schema.registerType(false, References.CHUNK, () -> DSL.fields((String)"Level", (TypeTemplate)DSL.optionalFields((String)"Entities", (TypeTemplate)DSL.list((TypeTemplate)References.ENTITY_TREE.in(schema)), (String)"TileEntities", (TypeTemplate)DSL.list((TypeTemplate)References.BLOCK_ENTITY.in(schema)), (String)"TileTicks", (TypeTemplate)DSL.list((TypeTemplate)DSL.fields((String)"i", (TypeTemplate)References.BLOCK_NAME.in(schema))))));
        schema.registerType(true, References.BLOCK_ENTITY, () -> DSL.taggedChoiceLazy((String)"id", (Type)DSL.string(), (Map)map2));
        schema.registerType(true, References.ENTITY_TREE, () -> DSL.optionalFields((String)"Riding", (TypeTemplate)References.ENTITY_TREE.in(schema), (TypeTemplate)References.ENTITY.in(schema)));
        schema.registerType(false, References.ENTITY_NAME, () -> DSL.constType(NamespacedSchema.namespacedString()));
        schema.registerType(true, References.ENTITY, () -> DSL.taggedChoiceLazy((String)"id", (Type)DSL.string(), (Map)map));
        schema.registerType(true, References.ITEM_STACK, () -> DSL.hook((TypeTemplate)DSL.optionalFields((String)"id", (TypeTemplate)DSL.or((TypeTemplate)DSL.constType((Type)DSL.intType()), (TypeTemplate)References.ITEM_NAME.in(schema)), (String)"tag", (TypeTemplate)DSL.optionalFields((String)"EntityTag", (TypeTemplate)References.ENTITY_TREE.in(schema), (String)"BlockEntityTag", (TypeTemplate)References.BLOCK_ENTITY.in(schema), (String)"CanDestroy", (TypeTemplate)DSL.list((TypeTemplate)References.BLOCK_NAME.in(schema)), (String)"CanPlaceOn", (TypeTemplate)DSL.list((TypeTemplate)References.BLOCK_NAME.in(schema)))), (Hook.HookFunction)ADD_NAMES, (Hook.HookFunction)Hook.HookFunction.IDENTITY));
        schema.registerType(false, References.OPTIONS, DSL::remainder);
        schema.registerType(false, References.BLOCK_NAME, () -> DSL.or((TypeTemplate)DSL.constType((Type)DSL.intType()), (TypeTemplate)DSL.constType(NamespacedSchema.namespacedString())));
        schema.registerType(false, References.ITEM_NAME, () -> DSL.constType(NamespacedSchema.namespacedString()));
        schema.registerType(false, References.STATS, DSL::remainder);
        schema.registerType(false, References.SAVED_DATA, () -> DSL.optionalFields((String)"data", (TypeTemplate)DSL.optionalFields((String)"Features", (TypeTemplate)DSL.compoundList((TypeTemplate)References.STRUCTURE_FEATURE.in(schema)), (String)"Objectives", (TypeTemplate)DSL.list((TypeTemplate)References.OBJECTIVE.in(schema)), (String)"Teams", (TypeTemplate)DSL.list((TypeTemplate)References.TEAM.in(schema)))));
        schema.registerType(false, References.STRUCTURE_FEATURE, DSL::remainder);
        schema.registerType(false, References.OBJECTIVE, DSL::remainder);
        schema.registerType(false, References.TEAM, DSL::remainder);
        schema.registerType(true, References.UNTAGGED_SPAWNER, DSL::remainder);
        schema.registerType(false, References.POI_CHUNK, DSL::remainder);
        schema.registerType(true, References.WORLD_GEN_SETTINGS, DSL::remainder);
    }

    protected static <T> T addNames(Dynamic<T> dynamic, Map<String, String> map, String string) {
        return (T)dynamic.update("tag", dynamic22 -> dynamic22.update("BlockEntityTag", dynamic2 -> {
            String string = dynamic.get("id").asString("");
            String string2 = (String)map.get(NamespacedSchema.ensureNamespaced(string));
            if (string2 != null) {
                return dynamic2.set("id", dynamic.createString(string2));
            }
            LOGGER.warn("Unable to resolve BlockEntity for ItemStack: {}", (Object)string);
            return dynamic2;
        }).update("EntityTag", dynamic2 -> {
            String string2 = dynamic.get("id").asString("");
            if (Objects.equals(NamespacedSchema.ensureNamespaced(string2), "minecraft:armor_stand")) {
                return dynamic2.set("id", dynamic.createString(string));
            }
            return dynamic2;
        })).getValue();
    }
}
