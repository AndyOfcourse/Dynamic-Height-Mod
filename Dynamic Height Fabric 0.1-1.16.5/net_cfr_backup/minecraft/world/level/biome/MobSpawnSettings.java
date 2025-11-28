/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.Keyable
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  net.minecraft.Util
 *  net.minecraft.core.Registry
 *  net.minecraft.util.StringRepresentable
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.MobCategory
 *  net.minecraft.world.level.biome.MobSpawnSettings$MobSpawnCost
 *  net.minecraft.world.level.biome.MobSpawnSettings$SpawnerData
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Keyable;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class MobSpawnSettings {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final MobSpawnSettings EMPTY = new MobSpawnSettings(0.1f, (Map)Stream.of(MobCategory.values()).collect(ImmutableMap.toImmutableMap(mobCategory -> mobCategory, mobCategory -> ImmutableList.of())), (Map<EntityType<?>, MobSpawnCost>)ImmutableMap.of(), false);
    public static final MapCodec<MobSpawnSettings> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group((App)Codec.FLOAT.optionalFieldOf("creature_spawn_probability", (Object)Float.valueOf(0.1f)).forGetter(mobSpawnSettings -> Float.valueOf(mobSpawnSettings.creatureGenerationProbability)), (App)Codec.simpleMap((Codec)MobCategory.CODEC, (Codec)SpawnerData.CODEC.listOf().promotePartial(Util.prefix((String)"Spawn data: ", arg_0 -> ((Logger)LOGGER).error(arg_0))), (Keyable)StringRepresentable.keys((StringRepresentable[])MobCategory.values())).fieldOf("spawners").forGetter(mobSpawnSettings -> mobSpawnSettings.spawners), (App)Codec.simpleMap((Codec)Registry.ENTITY_TYPE, (Codec)MobSpawnCost.CODEC, (Keyable)Registry.ENTITY_TYPE).fieldOf("spawn_costs").forGetter(mobSpawnSettings -> mobSpawnSettings.mobSpawnCosts), (App)Codec.BOOL.fieldOf("player_spawn_friendly").orElse((Object)false).forGetter(MobSpawnSettings::playerSpawnFriendly)).apply((Applicative)instance, MobSpawnSettings::new));
    private final float creatureGenerationProbability;
    private final Map<MobCategory, List<SpawnerData>> spawners;
    private final Map<EntityType<?>, MobSpawnCost> mobSpawnCosts;
    private final boolean playerSpawnFriendly;

    private MobSpawnSettings(float f, Map<MobCategory, List<SpawnerData>> map, Map<EntityType<?>, MobSpawnCost> map2, boolean bl) {
        this.creatureGenerationProbability = f;
        this.spawners = map;
        this.mobSpawnCosts = map2;
        this.playerSpawnFriendly = bl;
    }

    public List<SpawnerData> getMobs(MobCategory mobCategory) {
        return this.spawners.getOrDefault(mobCategory, (List<SpawnerData>)ImmutableList.of());
    }

    @Nullable
    public MobSpawnCost getMobSpawnCost(EntityType<?> entityType) {
        return this.mobSpawnCosts.get(entityType);
    }

    public float getCreatureProbability() {
        return this.creatureGenerationProbability;
    }

    public boolean playerSpawnFriendly() {
        return this.playerSpawnFriendly;
    }
}
