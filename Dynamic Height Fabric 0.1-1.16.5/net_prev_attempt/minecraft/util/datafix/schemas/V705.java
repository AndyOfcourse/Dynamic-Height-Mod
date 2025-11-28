/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.templates.Hook$HookFunction
 *  com.mojang.datafixers.types.templates.TypeTemplate
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 */
package net.minecraft.util.datafix.schemas;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.Hook;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.util.datafix.schemas.V100;
import net.minecraft.util.datafix.schemas.V704;
import net.minecraft.util.datafix.schemas.V99;

public class V705
extends NamespacedSchema {
    protected static final Hook.HookFunction ADD_NAMES = new Hook.HookFunction(){

        public <T> T apply(DynamicOps<T> dynamicOps, T object) {
            return V99.addNames(new Dynamic(dynamicOps, object), V704.ITEM_TO_BLOCKENTITY, "minecraft:armor_stand");
        }
    };

    public V705(int i, Schema schema) {
        super(i, schema);
    }

    protected static void registerMob(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V100.equipment(schema));
    }

    protected static void registerThrowableProjectile(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> DSL.optionalFields((String)"inTile", (TypeTemplate)References.BLOCK_NAME.in(schema)));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        HashMap map = Maps.newHashMap();
        schema.registerSimple((Map)map, "minecraft:area_effect_cloud");
        V705.registerMob(schema, map, "minecraft:armor_stand");
        schema.register((Map)map, "minecraft:arrow", string -> DSL.optionalFields((String)"inTile", (TypeTemplate)References.BLOCK_NAME.in(schema)));
        V705.registerMob(schema, map, "minecraft:bat");
        V705.registerMob(schema, map, "minecraft:blaze");
        schema.registerSimple((Map)map, "minecraft:boat");
        V705.registerMob(schema, map, "minecraft:cave_spider");
        schema.register((Map)map, "minecraft:chest_minecart", string -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.BLOCK_NAME.in(schema), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.ITEM_STACK.in(schema))));
        V705.registerMob(schema, map, "minecraft:chicken");
        schema.register((Map)map, "minecraft:commandblock_minecart", string -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.BLOCK_NAME.in(schema)));
        V705.registerMob(schema, map, "minecraft:cow");
        V705.registerMob(schema, map, "minecraft:creeper");
        schema.register((Map)map, "minecraft:donkey", string -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.ITEM_STACK.in(schema)), (String)"SaddleItem", (TypeTemplate)References.ITEM_STACK.in(schema), (TypeTemplate)V100.equipment(schema)));
        schema.registerSimple((Map)map, "minecraft:dragon_fireball");
        V705.registerThrowableProjectile(schema, map, "minecraft:egg");
        V705.registerMob(schema, map, "minecraft:elder_guardian");
        schema.registerSimple((Map)map, "minecraft:ender_crystal");
        V705.registerMob(schema, map, "minecraft:ender_dragon");
        schema.register((Map)map, "minecraft:enderman", string -> DSL.optionalFields((String)"carried", (TypeTemplate)References.BLOCK_NAME.in(schema), (TypeTemplate)V100.equipment(schema)));
        V705.registerMob(schema, map, "minecraft:endermite");
        V705.registerThrowableProjectile(schema, map, "minecraft:ender_pearl");
        schema.registerSimple((Map)map, "minecraft:eye_of_ender_signal");
        schema.register((Map)map, "minecraft:falling_block", string -> DSL.optionalFields((String)"Block", (TypeTemplate)References.BLOCK_NAME.in(schema), (String)"TileEntityData", (TypeTemplate)References.BLOCK_ENTITY.in(schema)));
        V705.registerThrowableProjectile(schema, map, "minecraft:fireball");
        schema.register((Map)map, "minecraft:fireworks_rocket", string -> DSL.optionalFields((String)"FireworksItem", (TypeTemplate)References.ITEM_STACK.in(schema)));
        schema.register((Map)map, "minecraft:furnace_minecart", string -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.BLOCK_NAME.in(schema)));
        V705.registerMob(schema, map, "minecraft:ghast");
        V705.registerMob(schema, map, "minecraft:giant");
        V705.registerMob(schema, map, "minecraft:guardian");
        schema.register((Map)map, "minecraft:hopper_minecart", string -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.BLOCK_NAME.in(schema), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.ITEM_STACK.in(schema))));
        schema.register((Map)map, "minecraft:horse", string -> DSL.optionalFields((String)"ArmorItem", (TypeTemplate)References.ITEM_STACK.in(schema), (String)"SaddleItem", (TypeTemplate)References.ITEM_STACK.in(schema), (TypeTemplate)V100.equipment(schema)));
        V705.registerMob(schema, map, "minecraft:husk");
        schema.register((Map)map, "minecraft:item", string -> DSL.optionalFields((String)"Item", (TypeTemplate)References.ITEM_STACK.in(schema)));
        schema.register((Map)map, "minecraft:item_frame", string -> DSL.optionalFields((String)"Item", (TypeTemplate)References.ITEM_STACK.in(schema)));
        schema.registerSimple((Map)map, "minecraft:leash_knot");
        V705.registerMob(schema, map, "minecraft:magma_cube");
        schema.register((Map)map, "minecraft:minecart", string -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.BLOCK_NAME.in(schema)));
        V705.registerMob(schema, map, "minecraft:mooshroom");
        schema.register((Map)map, "minecraft:mule", string -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.ITEM_STACK.in(schema)), (String)"SaddleItem", (TypeTemplate)References.ITEM_STACK.in(schema), (TypeTemplate)V100.equipment(schema)));
        V705.registerMob(schema, map, "minecraft:ocelot");
        schema.registerSimple((Map)map, "minecraft:painting");
        schema.registerSimple((Map)map, "minecraft:parrot");
        V705.registerMob(schema, map, "minecraft:pig");
        V705.registerMob(schema, map, "minecraft:polar_bear");
        schema.register((Map)map, "minecraft:potion", string -> DSL.optionalFields((String)"Potion", (TypeTemplate)References.ITEM_STACK.in(schema), (String)"inTile", (TypeTemplate)References.BLOCK_NAME.in(schema)));
        V705.registerMob(schema, map, "minecraft:rabbit");
        V705.registerMob(schema, map, "minecraft:sheep");
        V705.registerMob(schema, map, "minecraft:shulker");
        schema.registerSimple((Map)map, "minecraft:shulker_bullet");
        V705.registerMob(schema, map, "minecraft:silverfish");
        V705.registerMob(schema, map, "minecraft:skeleton");
        schema.register((Map)map, "minecraft:skeleton_horse", string -> DSL.optionalFields((String)"SaddleItem", (TypeTemplate)References.ITEM_STACK.in(schema), (TypeTemplate)V100.equipment(schema)));
        V705.registerMob(schema, map, "minecraft:slime");
        V705.registerThrowableProjectile(schema, map, "minecraft:small_fireball");
        V705.registerThrowableProjectile(schema, map, "minecraft:snowball");
        V705.registerMob(schema, map, "minecraft:snowman");
        schema.register((Map)map, "minecraft:spawner_minecart", string -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.BLOCK_NAME.in(schema), (TypeTemplate)References.UNTAGGED_SPAWNER.in(schema)));
        schema.register((Map)map, "minecraft:spectral_arrow", string -> DSL.optionalFields((String)"inTile", (TypeTemplate)References.BLOCK_NAME.in(schema)));
        V705.registerMob(schema, map, "minecraft:spider");
        V705.registerMob(schema, map, "minecraft:squid");
        V705.registerMob(schema, map, "minecraft:stray");
        schema.registerSimple((Map)map, "minecraft:tnt");
        schema.register((Map)map, "minecraft:tnt_minecart", string -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)References.BLOCK_NAME.in(schema)));
        schema.register((Map)map, "minecraft:villager", string -> DSL.optionalFields((String)"Inventory", (TypeTemplate)DSL.list((TypeTemplate)References.ITEM_STACK.in(schema)), (String)"Offers", (TypeTemplate)DSL.optionalFields((String)"Recipes", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"buy", (TypeTemplate)References.ITEM_STACK.in(schema), (String)"buyB", (TypeTemplate)References.ITEM_STACK.in(schema), (String)"sell", (TypeTemplate)References.ITEM_STACK.in(schema)))), (TypeTemplate)V100.equipment(schema)));
        V705.registerMob(schema, map, "minecraft:villager_golem");
        V705.registerMob(schema, map, "minecraft:witch");
        V705.registerMob(schema, map, "minecraft:wither");
        V705.registerMob(schema, map, "minecraft:wither_skeleton");
        V705.registerThrowableProjectile(schema, map, "minecraft:wither_skull");
        V705.registerMob(schema, map, "minecraft:wolf");
        V705.registerThrowableProjectile(schema, map, "minecraft:xp_bottle");
        schema.registerSimple((Map)map, "minecraft:xp_orb");
        V705.registerMob(schema, map, "minecraft:zombie");
        schema.register((Map)map, "minecraft:zombie_horse", string -> DSL.optionalFields((String)"SaddleItem", (TypeTemplate)References.ITEM_STACK.in(schema), (TypeTemplate)V100.equipment(schema)));
        V705.registerMob(schema, map, "minecraft:zombie_pigman");
        V705.registerMob(schema, map, "minecraft:zombie_villager");
        schema.registerSimple((Map)map, "minecraft:evocation_fangs");
        V705.registerMob(schema, map, "minecraft:evocation_illager");
        schema.registerSimple((Map)map, "minecraft:illusion_illager");
        schema.register((Map)map, "minecraft:llama", string -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.ITEM_STACK.in(schema)), (String)"SaddleItem", (TypeTemplate)References.ITEM_STACK.in(schema), (String)"DecorItem", (TypeTemplate)References.ITEM_STACK.in(schema), (TypeTemplate)V100.equipment(schema)));
        schema.registerSimple((Map)map, "minecraft:llama_spit");
        V705.registerMob(schema, map, "minecraft:vex");
        V705.registerMob(schema, map, "minecraft:vindication_illager");
        return map;
    }

    public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map, Map<String, Supplier<TypeTemplate>> map2) {
        super.registerTypes(schema, map, map2);
        schema.registerType(true, References.ENTITY, () -> DSL.taggedChoiceLazy((String)"id", V705.namespacedString(), (Map)map));
        schema.registerType(true, References.ITEM_STACK, () -> DSL.hook((TypeTemplate)DSL.optionalFields((String)"id", (TypeTemplate)References.ITEM_NAME.in(schema), (String)"tag", (TypeTemplate)DSL.optionalFields((String)"EntityTag", (TypeTemplate)References.ENTITY_TREE.in(schema), (String)"BlockEntityTag", (TypeTemplate)References.BLOCK_ENTITY.in(schema), (String)"CanDestroy", (TypeTemplate)DSL.list((TypeTemplate)References.BLOCK_NAME.in(schema)), (String)"CanPlaceOn", (TypeTemplate)DSL.list((TypeTemplate)References.BLOCK_NAME.in(schema)))), (Hook.HookFunction)ADD_NAMES, (Hook.HookFunction)Hook.HookFunction.IDENTITY));
    }
}
