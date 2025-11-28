/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 *  net.minecraft.util.datafix.fixes.AbstractUUIDFix
 *  net.minecraft.util.datafix.fixes.References
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.AbstractUUIDFix;
import net.minecraft.util.datafix.fixes.References;

public class LevelUUIDFix
extends AbstractUUIDFix {
    public LevelUUIDFix(Schema schema) {
        super(schema, References.LEVEL);
    }

    protected TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("LevelUUIDFix", this.getInputSchema().getType(this.typeReference), typed2 -> typed2.updateTyped(DSL.remainderFinder(), typed -> typed.update(DSL.remainderFinder(), dynamic -> {
            dynamic = this.updateCustomBossEvents((Dynamic<?>)dynamic);
            dynamic = this.updateDragonFight((Dynamic<?>)dynamic);
            dynamic = this.updateWanderingTrader((Dynamic<?>)dynamic);
            return dynamic;
        })));
    }

    private Dynamic<?> updateWanderingTrader(Dynamic<?> dynamic) {
        return LevelUUIDFix.replaceUUIDString(dynamic, (String)"WanderingTraderId", (String)"WanderingTraderId").orElse(dynamic);
    }

    private Dynamic<?> updateDragonFight(Dynamic<?> dynamic2) {
        return dynamic2.update("DimensionData", dynamic -> dynamic.updateMapValues(pair -> pair.mapSecond(dynamic2 -> dynamic2.update("DragonFight", dynamic -> LevelUUIDFix.replaceUUIDLeastMost((Dynamic)dynamic, (String)"DragonUUID", (String)"Dragon").orElse(dynamic)))));
    }

    private Dynamic<?> updateCustomBossEvents(Dynamic<?> dynamic2) {
        return dynamic2.update("CustomBossEvents", dynamic -> dynamic.updateMapValues(pair -> pair.mapSecond(dynamic -> dynamic.update("Players", dynamic22 -> dynamic.createList(dynamic22.asStream().map(dynamic -> LevelUUIDFix.createUUIDFromML((Dynamic)dynamic).orElseGet(() -> {
            LOGGER.warn("CustomBossEvents contains invalid UUIDs.");
            return dynamic;
        })))))));
    }
}
