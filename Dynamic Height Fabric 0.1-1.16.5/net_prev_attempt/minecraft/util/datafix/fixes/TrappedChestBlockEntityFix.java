/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.List$ListType
 *  com.mojang.datafixers.types.templates.TaggedChoice$TaggedChoiceType
 *  com.mojang.serialization.Dynamic
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.AddNewChoices;
import net.minecraft.util.datafix.fixes.LeavesFix;
import net.minecraft.util.datafix.fixes.References;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class TrappedChestBlockEntityFix
extends DataFix {
    private static final Logger LOGGER = LogManager.getLogger();

    public TrappedChestBlockEntityFix(Schema schema, boolean bl) {
        super(schema, bl);
    }

    public TypeRewriteRule makeRule() {
        Type type = this.getOutputSchema().getType(References.CHUNK);
        Type type2 = type.findFieldType("Level");
        Type type3 = type2.findFieldType("TileEntities");
        if (!(type3 instanceof List.ListType)) {
            throw new IllegalStateException("Tile entity type is not a list type.");
        }
        List.ListType listType = (List.ListType)type3;
        OpticFinder opticFinder = DSL.fieldFinder((String)"TileEntities", (Type)listType);
        Type type4 = this.getInputSchema().getType(References.CHUNK);
        OpticFinder opticFinder2 = type4.findField("Level");
        OpticFinder opticFinder3 = opticFinder2.type().findField("Sections");
        Type type5 = opticFinder3.type();
        if (!(type5 instanceof List.ListType)) {
            throw new IllegalStateException("Expecting sections to be a list.");
        }
        Type type6 = ((List.ListType)type5).getElement();
        OpticFinder opticFinder4 = DSL.typeFinder((Type)type6);
        return TypeRewriteRule.seq((TypeRewriteRule)new AddNewChoices(this.getOutputSchema(), "AddTrappedChestFix", References.BLOCK_ENTITY).makeRule(), (TypeRewriteRule)this.fixTypeEverywhereTyped("Trapped Chest fix", type4, typed2 -> typed2.updateTyped(opticFinder2, typed -> {
            Optional optional = typed.getOptionalTyped(opticFinder3);
            if (!optional.isPresent()) {
                return typed;
            }
            List list = ((Typed)optional.get()).getAllTyped(opticFinder4);
            IntOpenHashSet intSet = new IntOpenHashSet();
            for (Typed typed2 : list) {
                TrappedChestSection trappedChestSection = new TrappedChestSection(typed2, this.getInputSchema());
                if (trappedChestSection.isSkippable()) continue;
                for (int i = 0; i < 4096; ++i) {
                    int j = trappedChestSection.getBlock(i);
                    if (!trappedChestSection.isTrappedChest(j)) continue;
                    intSet.add(trappedChestSection.getIndex() << 12 | i);
                }
            }
            Dynamic dynamic = (Dynamic)typed.get(DSL.remainderFinder());
            int k = dynamic.get("xPos").asInt(0);
            int l = dynamic.get("zPos").asInt(0);
            TaggedChoice.TaggedChoiceType taggedChoiceType = this.getInputSchema().findChoiceType(References.BLOCK_ENTITY);
            return typed.updateTyped(opticFinder, arg_0 -> TrappedChestBlockEntityFix.method_5176(taggedChoiceType, k, l, (IntSet)intSet, arg_0));
        })));
    }

    private static /* synthetic */ Typed method_5176(TaggedChoice.TaggedChoiceType taggedChoiceType, int i, int j, IntSet intSet, Typed typed2) {
        return typed2.updateTyped(taggedChoiceType.finder(), typed -> {
            int m;
            int l;
            Dynamic dynamic = (Dynamic)typed.getOrCreate(DSL.remainderFinder());
            int k = dynamic.get("x").asInt(0) - (i << 4);
            if (intSet.contains(LeavesFix.getIndex(k, l = dynamic.get("y").asInt(0), m = dynamic.get("z").asInt(0) - (j << 4)))) {
                return typed.update(taggedChoiceType.finder(), pair -> pair.mapFirst(string -> {
                    if (!Objects.equals(string, "minecraft:chest")) {
                        LOGGER.warn("Block Entity was expected to be a chest");
                    }
                    return "minecraft:trapped_chest";
                }));
            }
            return typed;
        });
    }

    public static final class TrappedChestSection
    extends LeavesFix.Section {
        @Nullable
        private IntSet chestIds;

        public TrappedChestSection(Typed<?> typed, Schema schema) {
            super(typed, schema);
        }

        @Override
        protected boolean skippable() {
            this.chestIds = new IntOpenHashSet();
            for (int i = 0; i < this.palette.size(); ++i) {
                Dynamic dynamic = (Dynamic)this.palette.get(i);
                String string = dynamic.get("Name").asString("");
                if (!Objects.equals(string, "minecraft:trapped_chest")) continue;
                this.chestIds.add(i);
            }
            return this.chestIds.isEmpty();
        }

        public boolean isTrappedChest(int i) {
            return this.chestIds.contains(i);
        }
    }
}
