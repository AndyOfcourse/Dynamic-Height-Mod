/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.mojang.serialization.DynamicLike
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundEntityEventPacket
 *  net.minecraft.network.protocol.game.ClientboundGameEventPacket
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.GameRules$BooleanValue
 *  net.minecraft.world.level.GameRules$Category
 *  net.minecraft.world.level.GameRules$GameRuleTypeVisitor
 *  net.minecraft.world.level.GameRules$IntegerValue
 *  net.minecraft.world.level.GameRules$Key
 *  net.minecraft.world.level.GameRules$Type
 *  net.minecraft.world.level.GameRules$Value
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.world.level;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.DynamicLike;
import java.util.Comparator;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameRules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class GameRules {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<Key<?>, Type<?>> GAME_RULE_TYPES = Maps.newTreeMap(Comparator.comparing(key -> Key.method_20772((Key)key)));
    public static final Key<BooleanValue> RULE_DOFIRETICK = GameRules.register("doFireTick", Category.UPDATES, BooleanValue.method_20755((boolean)true));
    public static final Key<BooleanValue> RULE_MOBGRIEFING = GameRules.register("mobGriefing", Category.MOBS, BooleanValue.method_20755((boolean)true));
    public static final Key<BooleanValue> RULE_KEEPINVENTORY = GameRules.register("keepInventory", Category.PLAYER, BooleanValue.method_20755((boolean)false));
    public static final Key<BooleanValue> RULE_DOMOBSPAWNING = GameRules.register("doMobSpawning", Category.SPAWNING, BooleanValue.method_20755((boolean)true));
    public static final Key<BooleanValue> RULE_DOMOBLOOT = GameRules.register("doMobLoot", Category.DROPS, BooleanValue.method_20755((boolean)true));
    public static final Key<BooleanValue> RULE_DOBLOCKDROPS = GameRules.register("doTileDrops", Category.DROPS, BooleanValue.method_20755((boolean)true));
    public static final Key<BooleanValue> RULE_DOENTITYDROPS = GameRules.register("doEntityDrops", Category.DROPS, BooleanValue.method_20755((boolean)true));
    public static final Key<BooleanValue> RULE_COMMANDBLOCKOUTPUT = GameRules.register("commandBlockOutput", Category.CHAT, BooleanValue.method_20755((boolean)true));
    public static final Key<BooleanValue> RULE_NATURAL_REGENERATION = GameRules.register("naturalRegeneration", Category.PLAYER, BooleanValue.method_20755((boolean)true));
    public static final Key<BooleanValue> RULE_DAYLIGHT = GameRules.register("doDaylightCycle", Category.UPDATES, BooleanValue.method_20755((boolean)true));
    public static final Key<BooleanValue> RULE_LOGADMINCOMMANDS = GameRules.register("logAdminCommands", Category.CHAT, BooleanValue.method_20755((boolean)true));
    public static final Key<BooleanValue> RULE_SHOWDEATHMESSAGES = GameRules.register("showDeathMessages", Category.CHAT, BooleanValue.method_20755((boolean)true));
    public static final Key<IntegerValue> RULE_RANDOMTICKING = GameRules.register("randomTickSpeed", Category.UPDATES, IntegerValue.method_20764((int)3));
    public static final Key<BooleanValue> RULE_SENDCOMMANDFEEDBACK = GameRules.register("sendCommandFeedback", Category.CHAT, BooleanValue.method_20755((boolean)true));
    public static final Key<BooleanValue> RULE_REDUCEDDEBUGINFO = GameRules.register("reducedDebugInfo", Category.MISC, BooleanValue.method_20757((boolean)false, (minecraftServer, booleanValue) -> {
        byte b = booleanValue.get() ? (byte)22 : (byte)23;
        for (ServerPlayer serverPlayer : minecraftServer.getPlayerList().getPlayers()) {
            serverPlayer.connection.send((Packet)new ClientboundEntityEventPacket((Entity)serverPlayer, b));
        }
    }));
    public static final Key<BooleanValue> RULE_SPECTATORSGENERATECHUNKS = GameRules.register("spectatorsGenerateChunks", Category.PLAYER, BooleanValue.method_20755((boolean)true));
    public static final Key<IntegerValue> RULE_SPAWN_RADIUS = GameRules.register("spawnRadius", Category.PLAYER, IntegerValue.method_20764((int)10));
    public static final Key<BooleanValue> RULE_DISABLE_ELYTRA_MOVEMENT_CHECK = GameRules.register("disableElytraMovementCheck", Category.PLAYER, BooleanValue.method_20755((boolean)false));
    public static final Key<IntegerValue> RULE_MAX_ENTITY_CRAMMING = GameRules.register("maxEntityCramming", Category.MOBS, IntegerValue.method_20764((int)24));
    public static final Key<BooleanValue> RULE_WEATHER_CYCLE = GameRules.register("doWeatherCycle", Category.UPDATES, BooleanValue.method_20755((boolean)true));
    public static final Key<BooleanValue> RULE_LIMITED_CRAFTING = GameRules.register("doLimitedCrafting", Category.PLAYER, BooleanValue.method_20755((boolean)false));
    public static final Key<IntegerValue> RULE_MAX_COMMAND_CHAIN_LENGTH = GameRules.register("maxCommandChainLength", Category.MISC, IntegerValue.method_20764((int)65536));
    public static final Key<BooleanValue> RULE_ANNOUNCE_ADVANCEMENTS = GameRules.register("announceAdvancements", Category.CHAT, BooleanValue.method_20755((boolean)true));
    public static final Key<BooleanValue> RULE_DISABLE_RAIDS = GameRules.register("disableRaids", Category.MOBS, BooleanValue.method_20755((boolean)false));
    public static final Key<BooleanValue> RULE_DOINSOMNIA = GameRules.register("doInsomnia", Category.SPAWNING, BooleanValue.method_20755((boolean)true));
    public static final Key<BooleanValue> RULE_DO_IMMEDIATE_RESPAWN = GameRules.register("doImmediateRespawn", Category.PLAYER, BooleanValue.method_20757((boolean)false, (minecraftServer, booleanValue) -> {
        for (ServerPlayer serverPlayer : minecraftServer.getPlayerList().getPlayers()) {
            serverPlayer.connection.send((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.IMMEDIATE_RESPAWN, booleanValue.get() ? 1.0f : 0.0f));
        }
    }));
    public static final Key<BooleanValue> RULE_DROWNING_DAMAGE = GameRules.register("drowningDamage", Category.PLAYER, BooleanValue.method_20755((boolean)true));
    public static final Key<BooleanValue> RULE_FALL_DAMAGE = GameRules.register("fallDamage", Category.PLAYER, BooleanValue.method_20755((boolean)true));
    public static final Key<BooleanValue> RULE_FIRE_DAMAGE = GameRules.register("fireDamage", Category.PLAYER, BooleanValue.method_20755((boolean)true));
    public static final Key<BooleanValue> RULE_DO_PATROL_SPAWNING = GameRules.register("doPatrolSpawning", Category.SPAWNING, BooleanValue.method_20755((boolean)true));
    public static final Key<BooleanValue> RULE_DO_TRADER_SPAWNING = GameRules.register("doTraderSpawning", Category.SPAWNING, BooleanValue.method_20755((boolean)true));
    public static final Key<BooleanValue> RULE_FORGIVE_DEAD_PLAYERS = GameRules.register("forgiveDeadPlayers", Category.MOBS, BooleanValue.method_20755((boolean)true));
    public static final Key<BooleanValue> RULE_UNIVERSAL_ANGER = GameRules.register("universalAnger", Category.MOBS, BooleanValue.method_20755((boolean)false));
    private final Map<Key<?>, Value<?>> rules;

    private static <T extends Value<T>> Key<T> register(String string, Category category, Type<T> type) {
        Key key = new Key(string, category);
        Type<T> type2 = GAME_RULE_TYPES.put(key, type);
        if (type2 != null) {
            throw new IllegalStateException("Duplicate game rule registration for " + string);
        }
        return key;
    }

    public GameRules(DynamicLike<?> dynamicLike) {
        this();
        this.loadFromTag(dynamicLike);
    }

    public GameRules() {
        this.rules = (Map)GAME_RULE_TYPES.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, entry -> ((Type)entry.getValue()).createRule()));
    }

    private GameRules(Map<Key<?>, Value<?>> map) {
        this.rules = map;
    }

    public <T extends Value<T>> T getRule(Key<T> key) {
        return (T)this.rules.get(key);
    }

    public CompoundTag createTag() {
        CompoundTag compoundTag = new CompoundTag();
        this.rules.forEach((key, value) -> compoundTag.putString(Key.method_20772((Key)key), value.serialize()));
        return compoundTag;
    }

    private void loadFromTag(DynamicLike<?> dynamicLike) {
        this.rules.forEach((key, value) -> dynamicLike.get(Key.method_20772((Key)key)).asString().result().ifPresent(arg_0 -> ((Value)value).deserialize(arg_0)));
    }

    public GameRules copy() {
        return new GameRules((Map)this.rules.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, entry -> ((Value)entry.getValue()).copy())));
    }

    public static void visitGameRuleTypes(GameRuleTypeVisitor gameRuleTypeVisitor) {
        GAME_RULE_TYPES.forEach((key, type) -> GameRules.callVisitorCap(gameRuleTypeVisitor, key, type));
    }

    private static <T extends Value<T>> void callVisitorCap(GameRuleTypeVisitor gameRuleTypeVisitor, Key<?> key, Type<?> type) {
        Key<?> key2 = key;
        Type<?> type2 = type;
        gameRuleTypeVisitor.visit(key2, type2);
        type2.callVisitor(gameRuleTypeVisitor, key2);
    }

    @Environment(value=EnvType.CLIENT)
    public void assignFrom(GameRules gameRules, @Nullable MinecraftServer minecraftServer) {
        gameRules.rules.keySet().forEach(key -> this.assignCap((Key)key, gameRules, minecraftServer));
    }

    @Environment(value=EnvType.CLIENT)
    private <T extends Value<T>> void assignCap(Key<T> key, GameRules gameRules, @Nullable MinecraftServer minecraftServer) {
        T value = gameRules.getRule(key);
        this.getRule(key).setFrom(value, minecraftServer);
    }

    public boolean getBoolean(Key<BooleanValue> key) {
        return this.getRule(key).get();
    }

    public int getInt(Key<IntegerValue> key) {
        return this.getRule(key).get();
    }

    static /* synthetic */ Logger method_20749() {
        return LOGGER;
    }
}
