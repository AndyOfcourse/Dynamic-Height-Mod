/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 *  net.minecraft.util.datafix.fixes.AbstractUUIDFix
 *  net.minecraft.util.datafix.fixes.References
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.AbstractUUIDFix;
import net.minecraft.util.datafix.fixes.References;

public class BlockEntityUUIDFix
extends AbstractUUIDFix {
    public BlockEntityUUIDFix(Schema schema) {
        super(schema, References.BLOCK_ENTITY);
    }

    protected TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("BlockEntityUUIDFix", this.getInputSchema().getType(this.typeReference), typed -> {
            typed = this.updateNamedChoice((Typed)typed, "minecraft:conduit", this::updateConduit);
            typed = this.updateNamedChoice((Typed)typed, "minecraft:skull", this::updateSkull);
            return typed;
        });
    }

    private Dynamic<?> updateSkull(Dynamic<?> dynamic3) {
        return dynamic3.get("Owner").get().map(dynamic -> BlockEntityUUIDFix.replaceUUIDString((Dynamic)dynamic, (String)"Id", (String)"Id").orElse(dynamic)).map(dynamic2 -> dynamic3.remove("Owner").set("SkullOwner", dynamic2)).result().orElse(dynamic3);
    }

    private Dynamic<?> updateConduit(Dynamic<?> dynamic) {
        return BlockEntityUUIDFix.replaceUUIDMLTag(dynamic, (String)"target_uuid", (String)"Target").orElse(dynamic);
    }
}
