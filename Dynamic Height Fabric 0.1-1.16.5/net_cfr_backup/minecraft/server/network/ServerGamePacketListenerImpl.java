/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.primitives.Doubles
 *  com.google.common.primitives.Floats
 *  com.mojang.brigadier.ParseResults
 *  com.mojang.brigadier.StringReader
 *  io.netty.util.concurrent.Future
 *  io.netty.util.concurrent.GenericFutureListener
 *  it.unimi.dsi.fastutil.ints.Int2ShortMap
 *  it.unimi.dsi.fastutil.ints.Int2ShortOpenHashMap
 *  net.minecraft.ChatFormatting
 *  net.minecraft.CrashReport
 *  net.minecraft.CrashReportCategory
 *  net.minecraft.ReportedException
 *  net.minecraft.SharedConstants
 *  net.minecraft.Util
 *  net.minecraft.advancements.Advancement
 *  net.minecraft.advancements.CriteriaTriggers
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.Connection
 *  net.minecraft.network.PacketListener
 *  net.minecraft.network.chat.ChatType
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Component$Serializer
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.TextComponent
 *  net.minecraft.network.chat.TranslatableComponent
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.PacketUtils
 *  net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket
 *  net.minecraft.network.protocol.game.ClientboundChatPacket
 *  net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket
 *  net.minecraft.network.protocol.game.ClientboundContainerAckPacket
 *  net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket
 *  net.minecraft.network.protocol.game.ClientboundDisconnectPacket
 *  net.minecraft.network.protocol.game.ClientboundKeepAlivePacket
 *  net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket$RelativeArgument
 *  net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket
 *  net.minecraft.network.protocol.game.ClientboundTagQueryPacket
 *  net.minecraft.network.protocol.game.ServerGamePacketListener
 *  net.minecraft.network.protocol.game.ServerboundAcceptTeleportationPacket
 *  net.minecraft.network.protocol.game.ServerboundBlockEntityTagQuery
 *  net.minecraft.network.protocol.game.ServerboundChangeDifficultyPacket
 *  net.minecraft.network.protocol.game.ServerboundChatPacket
 *  net.minecraft.network.protocol.game.ServerboundClientCommandPacket
 *  net.minecraft.network.protocol.game.ServerboundClientCommandPacket$Action
 *  net.minecraft.network.protocol.game.ServerboundClientInformationPacket
 *  net.minecraft.network.protocol.game.ServerboundCommandSuggestionPacket
 *  net.minecraft.network.protocol.game.ServerboundContainerAckPacket
 *  net.minecraft.network.protocol.game.ServerboundContainerButtonClickPacket
 *  net.minecraft.network.protocol.game.ServerboundContainerClickPacket
 *  net.minecraft.network.protocol.game.ServerboundContainerClosePacket
 *  net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket
 *  net.minecraft.network.protocol.game.ServerboundEditBookPacket
 *  net.minecraft.network.protocol.game.ServerboundEntityTagQuery
 *  net.minecraft.network.protocol.game.ServerboundInteractPacket
 *  net.minecraft.network.protocol.game.ServerboundInteractPacket$Action
 *  net.minecraft.network.protocol.game.ServerboundJigsawGeneratePacket
 *  net.minecraft.network.protocol.game.ServerboundKeepAlivePacket
 *  net.minecraft.network.protocol.game.ServerboundLockDifficultyPacket
 *  net.minecraft.network.protocol.game.ServerboundMovePlayerPacket
 *  net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket
 *  net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket
 *  net.minecraft.network.protocol.game.ServerboundPickItemPacket
 *  net.minecraft.network.protocol.game.ServerboundPlaceRecipePacket
 *  net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket
 *  net.minecraft.network.protocol.game.ServerboundPlayerActionPacket
 *  net.minecraft.network.protocol.game.ServerboundPlayerActionPacket$Action
 *  net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket
 *  net.minecraft.network.protocol.game.ServerboundPlayerInputPacket
 *  net.minecraft.network.protocol.game.ServerboundRecipeBookChangeSettingsPacket
 *  net.minecraft.network.protocol.game.ServerboundRecipeBookSeenRecipePacket
 *  net.minecraft.network.protocol.game.ServerboundRenameItemPacket
 *  net.minecraft.network.protocol.game.ServerboundResourcePackPacket
 *  net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket
 *  net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket$Action
 *  net.minecraft.network.protocol.game.ServerboundSelectTradePacket
 *  net.minecraft.network.protocol.game.ServerboundSetBeaconPacket
 *  net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket
 *  net.minecraft.network.protocol.game.ServerboundSetCommandBlockPacket
 *  net.minecraft.network.protocol.game.ServerboundSetCommandMinecartPacket
 *  net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket
 *  net.minecraft.network.protocol.game.ServerboundSetJigsawBlockPacket
 *  net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket
 *  net.minecraft.network.protocol.game.ServerboundSignUpdatePacket
 *  net.minecraft.network.protocol.game.ServerboundSwingPacket
 *  net.minecraft.network.protocol.game.ServerboundTeleportToEntityPacket
 *  net.minecraft.network.protocol.game.ServerboundUseItemOnPacket
 *  net.minecraft.network.protocol.game.ServerboundUseItemPacket
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.network.ServerGamePacketListenerImpl$1
 *  net.minecraft.server.network.TextFilter
 *  net.minecraft.stats.ServerRecipeBook
 *  net.minecraft.util.StringUtil
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.ExperienceOrb
 *  net.minecraft.world.entity.MoverType
 *  net.minecraft.world.entity.PlayerRideableJumping
 *  net.minecraft.world.entity.animal.horse.AbstractHorse
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.ChatVisiblity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.AbstractArrow
 *  net.minecraft.world.entity.vehicle.Boat
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.AnvilMenu
 *  net.minecraft.world.inventory.BeaconMenu
 *  net.minecraft.world.inventory.MerchantMenu
 *  net.minecraft.world.inventory.RecipeBookMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.BucketItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.WritableBookItem
 *  net.minecraft.world.level.BaseCommandBlock
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.GameRules$BooleanValue
 *  net.minecraft.world.level.GameType
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.CommandBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.CommandBlockEntity
 *  net.minecraft.world.level.block.entity.CommandBlockEntity$Mode
 *  net.minecraft.world.level.block.entity.JigsawBlockEntity
 *  net.minecraft.world.level.block.entity.SignBlockEntity
 *  net.minecraft.world.level.block.entity.StructureBlockEntity
 *  net.minecraft.world.level.block.entity.StructureBlockEntity$UpdateType
 *  net.minecraft.world.level.block.state.BlockBehaviour$BlockStateBase
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.BooleanOp
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.server.network;

import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import it.unimi.dsi.fastutil.ints.Int2ShortMap;
import it.unimi.dsi.fastutil.ints.Int2ShortOpenHashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundChatPacket;
import net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket;
import net.minecraft.network.protocol.game.ClientboundContainerAckPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.game.ClientboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ClientboundTagQueryPacket;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.network.protocol.game.ServerboundAcceptTeleportationPacket;
import net.minecraft.network.protocol.game.ServerboundBlockEntityTagQuery;
import net.minecraft.network.protocol.game.ServerboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.network.protocol.game.ServerboundClientInformationPacket;
import net.minecraft.network.protocol.game.ServerboundCommandSuggestionPacket;
import net.minecraft.network.protocol.game.ServerboundContainerAckPacket;
import net.minecraft.network.protocol.game.ServerboundContainerButtonClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ServerboundEditBookPacket;
import net.minecraft.network.protocol.game.ServerboundEntityTagQuery;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundJigsawGeneratePacket;
import net.minecraft.network.protocol.game.ServerboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ServerboundLockDifficultyPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
import net.minecraft.network.protocol.game.ServerboundPickItemPacket;
import net.minecraft.network.protocol.game.ServerboundPlaceRecipePacket;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.network.protocol.game.ServerboundRecipeBookChangeSettingsPacket;
import net.minecraft.network.protocol.game.ServerboundRecipeBookSeenRecipePacket;
import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
import net.minecraft.network.protocol.game.ServerboundResourcePackPacket;
import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
import net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
import net.minecraft.network.protocol.game.ServerboundSetBeaconPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundSetCommandBlockPacket;
import net.minecraft.network.protocol.game.ServerboundSetCommandMinecartPacket;
import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
import net.minecraft.network.protocol.game.ServerboundSetJigsawBlockPacket;
import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
import net.minecraft.network.protocol.game.ServerboundSignUpdatePacket;
import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import net.minecraft.network.protocol.game.ServerboundTeleportToEntityPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.network.TextFilter;
import net.minecraft.stats.ServerRecipeBook;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.ChatVisiblity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WritableBookItem;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CommandBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class ServerGamePacketListenerImpl
implements ServerGamePacketListener {
    private static final Logger LOGGER = LogManager.getLogger();
    public final Connection connection;
    private final MinecraftServer server;
    public ServerPlayer player;
    private int tickCount;
    private long keepAliveTime;
    private boolean keepAlivePending;
    private long keepAliveChallenge;
    private int chatSpamTickCount;
    private int dropSpamTickCount;
    private final Int2ShortMap expectedAcks = new Int2ShortOpenHashMap();
    private double firstGoodX;
    private double firstGoodY;
    private double firstGoodZ;
    private double lastGoodX;
    private double lastGoodY;
    private double lastGoodZ;
    private Entity lastVehicle;
    private double vehicleFirstGoodX;
    private double vehicleFirstGoodY;
    private double vehicleFirstGoodZ;
    private double vehicleLastGoodX;
    private double vehicleLastGoodY;
    private double vehicleLastGoodZ;
    private Vec3 awaitingPositionFromClient;
    private int awaitingTeleport;
    private int awaitingTeleportTime;
    private boolean clientIsFloating;
    private int aboveGroundTickCount;
    private boolean clientVehicleIsFloating;
    private int aboveGroundVehicleTickCount;
    private int receivedMovePacketCount;
    private int knownMovePacketCount;

    public ServerGamePacketListenerImpl(MinecraftServer minecraftServer, Connection connection, ServerPlayer serverPlayer) {
        this.server = minecraftServer;
        this.connection = connection;
        connection.setListener((PacketListener)this);
        this.player = serverPlayer;
        serverPlayer.connection = this;
        TextFilter textFilter = serverPlayer.getTextFilter();
        if (textFilter != null) {
            textFilter.join();
        }
    }

    public void tick() {
        this.resetPosition();
        this.player.xo = this.player.getX();
        this.player.yo = this.player.getY();
        this.player.zo = this.player.getZ();
        this.player.doTick();
        this.player.absMoveTo(this.firstGoodX, this.firstGoodY, this.firstGoodZ, this.player.yRot, this.player.xRot);
        ++this.tickCount;
        this.knownMovePacketCount = this.receivedMovePacketCount;
        if (this.clientIsFloating && !this.player.isSleeping()) {
            if (++this.aboveGroundTickCount > 80) {
                LOGGER.warn("{} was kicked for floating too long!", (Object)this.player.getName().getString());
                this.disconnect((Component)new TranslatableComponent("multiplayer.disconnect.flying"));
                return;
            }
        } else {
            this.clientIsFloating = false;
            this.aboveGroundTickCount = 0;
        }
        this.lastVehicle = this.player.getRootVehicle();
        if (this.lastVehicle == this.player || this.lastVehicle.getControllingPassenger() != this.player) {
            this.lastVehicle = null;
            this.clientVehicleIsFloating = false;
            this.aboveGroundVehicleTickCount = 0;
        } else {
            this.vehicleFirstGoodX = this.lastVehicle.getX();
            this.vehicleFirstGoodY = this.lastVehicle.getY();
            this.vehicleFirstGoodZ = this.lastVehicle.getZ();
            this.vehicleLastGoodX = this.lastVehicle.getX();
            this.vehicleLastGoodY = this.lastVehicle.getY();
            this.vehicleLastGoodZ = this.lastVehicle.getZ();
            if (this.clientVehicleIsFloating && this.player.getRootVehicle().getControllingPassenger() == this.player) {
                if (++this.aboveGroundVehicleTickCount > 80) {
                    LOGGER.warn("{} was kicked for floating a vehicle too long!", (Object)this.player.getName().getString());
                    this.disconnect((Component)new TranslatableComponent("multiplayer.disconnect.flying"));
                    return;
                }
            } else {
                this.clientVehicleIsFloating = false;
                this.aboveGroundVehicleTickCount = 0;
            }
        }
        this.server.getProfiler().push("keepAlive");
        long l = Util.getMillis();
        if (l - this.keepAliveTime >= 15000L) {
            if (this.keepAlivePending) {
                this.disconnect((Component)new TranslatableComponent("disconnect.timeout"));
            } else {
                this.keepAlivePending = true;
                this.keepAliveTime = l;
                this.keepAliveChallenge = l;
                this.send((Packet<?>)new ClientboundKeepAlivePacket(this.keepAliveChallenge));
            }
        }
        this.server.getProfiler().pop();
        if (this.chatSpamTickCount > 0) {
            --this.chatSpamTickCount;
        }
        if (this.dropSpamTickCount > 0) {
            --this.dropSpamTickCount;
        }
        if (this.player.getLastActionTime() > 0L && this.server.getPlayerIdleTimeout() > 0 && Util.getMillis() - this.player.getLastActionTime() > (long)(this.server.getPlayerIdleTimeout() * 1000 * 60)) {
            this.disconnect((Component)new TranslatableComponent("multiplayer.disconnect.idling"));
        }
    }

    public void resetPosition() {
        this.firstGoodX = this.player.getX();
        this.firstGoodY = this.player.getY();
        this.firstGoodZ = this.player.getZ();
        this.lastGoodX = this.player.getX();
        this.lastGoodY = this.player.getY();
        this.lastGoodZ = this.player.getZ();
    }

    public Connection getConnection() {
        return this.connection;
    }

    private boolean isSingleplayerOwner() {
        return this.server.isSingleplayerOwner(this.player.getGameProfile());
    }

    public void disconnect(Component component) {
        this.connection.send((Packet)new ClientboundDisconnectPacket(component), future -> this.connection.disconnect(component));
        this.connection.setReadOnly();
        this.server.executeBlocking(() -> ((Connection)this.connection).handleDisconnection());
    }

    private <T> void filterTextPacket(T object2, Consumer<T> consumer, BiFunction<TextFilter, T, CompletableFuture<Optional<T>>> biFunction) {
        MinecraftServer blockableEventLoop = this.player.getLevel().getServer();
        Consumer<Object> consumer2 = object -> {
            if (this.getConnection().isConnected()) {
                consumer.accept(object);
            } else {
                LOGGER.debug("Ignoring packet due to disconnection");
            }
        };
        TextFilter textFilter = this.player.getTextFilter();
        if (textFilter != null) {
            biFunction.apply(textFilter, (TextFilter)object2).thenAcceptAsync(optional -> optional.ifPresent(consumer2), (Executor)blockableEventLoop);
        } else {
            blockableEventLoop.execute(() -> consumer2.accept(object2));
        }
    }

    private void filterTextPacket(String string, Consumer<String> consumer) {
        this.filterTextPacket(string, consumer, TextFilter::processStreamMessage);
    }

    private void filterTextPacket(List<String> list, Consumer<List<String>> consumer) {
        this.filterTextPacket(list, consumer, TextFilter::processMessageBundle);
    }

    public void handlePlayerInput(ServerboundPlayerInputPacket serverboundPlayerInputPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundPlayerInputPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        this.player.setPlayerInput(serverboundPlayerInputPacket.getXxa(), serverboundPlayerInputPacket.getZza(), serverboundPlayerInputPacket.isJumping(), serverboundPlayerInputPacket.isShiftKeyDown());
    }

    private static boolean containsInvalidValues(ServerboundMovePlayerPacket serverboundMovePlayerPacket) {
        if (!(Doubles.isFinite((double)serverboundMovePlayerPacket.getX(0.0)) && Doubles.isFinite((double)serverboundMovePlayerPacket.getY(0.0)) && Doubles.isFinite((double)serverboundMovePlayerPacket.getZ(0.0)) && Floats.isFinite((float)serverboundMovePlayerPacket.getXRot(0.0f)) && Floats.isFinite((float)serverboundMovePlayerPacket.getYRot(0.0f)))) {
            return true;
        }
        return Math.abs(serverboundMovePlayerPacket.getX(0.0)) > 3.0E7 || Math.abs(serverboundMovePlayerPacket.getY(0.0)) > 3.0E7 || Math.abs(serverboundMovePlayerPacket.getZ(0.0)) > 3.0E7;
    }

    private static boolean containsInvalidValues(ServerboundMoveVehiclePacket serverboundMoveVehiclePacket) {
        return !Doubles.isFinite((double)serverboundMoveVehiclePacket.getX()) || !Doubles.isFinite((double)serverboundMoveVehiclePacket.getY()) || !Doubles.isFinite((double)serverboundMoveVehiclePacket.getZ()) || !Floats.isFinite((float)serverboundMoveVehiclePacket.getXRot()) || !Floats.isFinite((float)serverboundMoveVehiclePacket.getYRot());
    }

    public void handleMoveVehicle(ServerboundMoveVehiclePacket serverboundMoveVehiclePacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundMoveVehiclePacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        if (ServerGamePacketListenerImpl.containsInvalidValues(serverboundMoveVehiclePacket)) {
            this.disconnect((Component)new TranslatableComponent("multiplayer.disconnect.invalid_vehicle_movement"));
            return;
        }
        Entity entity = this.player.getRootVehicle();
        if (entity != this.player && entity.getControllingPassenger() == this.player && entity == this.lastVehicle) {
            ServerLevel serverLevel = this.player.getLevel();
            double d = entity.getX();
            double e = entity.getY();
            double f = entity.getZ();
            double g = serverboundMoveVehiclePacket.getX();
            double h = serverboundMoveVehiclePacket.getY();
            double i = serverboundMoveVehiclePacket.getZ();
            float j = serverboundMoveVehiclePacket.getYRot();
            float k = serverboundMoveVehiclePacket.getXRot();
            double l = g - this.vehicleFirstGoodX;
            double m = h - this.vehicleFirstGoodY;
            double n = i - this.vehicleFirstGoodZ;
            double p = l * l + m * m + n * n;
            double o = entity.getDeltaMovement().lengthSqr();
            if (p - o > 100.0 && !this.isSingleplayerOwner()) {
                LOGGER.warn("{} (vehicle of {}) moved too quickly! {},{},{}", (Object)entity.getName().getString(), (Object)this.player.getName().getString(), (Object)l, (Object)m, (Object)n);
                this.connection.send((Packet)new ClientboundMoveVehiclePacket(entity));
                return;
            }
            boolean bl = serverLevel.noCollision(entity, entity.getBoundingBox().deflate(0.0625));
            l = g - this.vehicleLastGoodX;
            m = h - this.vehicleLastGoodY - 1.0E-6;
            n = i - this.vehicleLastGoodZ;
            entity.move(MoverType.PLAYER, new Vec3(l, m, n));
            double q = m;
            l = g - entity.getX();
            m = h - entity.getY();
            if (m > -0.5 || m < 0.5) {
                m = 0.0;
            }
            n = i - entity.getZ();
            p = l * l + m * m + n * n;
            boolean bl2 = false;
            if (p > 0.0625) {
                bl2 = true;
                LOGGER.warn("{} (vehicle of {}) moved wrongly! {}", (Object)entity.getName().getString(), (Object)this.player.getName().getString(), (Object)Math.sqrt(p));
            }
            entity.absMoveTo(g, h, i, j, k);
            boolean bl3 = serverLevel.noCollision(entity, entity.getBoundingBox().deflate(0.0625));
            if (bl && (bl2 || !bl3)) {
                entity.absMoveTo(d, e, f, j, k);
                this.connection.send((Packet)new ClientboundMoveVehiclePacket(entity));
                return;
            }
            this.player.getLevel().getChunkSource().move(this.player);
            this.player.checkMovementStatistics(this.player.getX() - d, this.player.getY() - e, this.player.getZ() - f);
            this.clientVehicleIsFloating = q >= -0.03125 && !this.server.isFlightAllowed() && this.noBlocksAround(entity);
            this.vehicleLastGoodX = entity.getX();
            this.vehicleLastGoodY = entity.getY();
            this.vehicleLastGoodZ = entity.getZ();
        }
    }

    private boolean noBlocksAround(Entity entity) {
        return entity.level.getBlockStates(entity.getBoundingBox().inflate(0.0625).expandTowards(0.0, -0.55, 0.0)).allMatch(BlockBehaviour.BlockStateBase::isAir);
    }

    public void handleAcceptTeleportPacket(ServerboundAcceptTeleportationPacket serverboundAcceptTeleportationPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundAcceptTeleportationPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        if (serverboundAcceptTeleportationPacket.getId() == this.awaitingTeleport) {
            this.player.absMoveTo(this.awaitingPositionFromClient.x, this.awaitingPositionFromClient.y, this.awaitingPositionFromClient.z, this.player.yRot, this.player.xRot);
            this.lastGoodX = this.awaitingPositionFromClient.x;
            this.lastGoodY = this.awaitingPositionFromClient.y;
            this.lastGoodZ = this.awaitingPositionFromClient.z;
            if (this.player.isChangingDimension()) {
                this.player.hasChangedDimension();
            }
            this.awaitingPositionFromClient = null;
        }
    }

    public void handleRecipeBookSeenRecipePacket(ServerboundRecipeBookSeenRecipePacket serverboundRecipeBookSeenRecipePacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundRecipeBookSeenRecipePacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        this.server.getRecipeManager().byKey(serverboundRecipeBookSeenRecipePacket.getRecipe()).ifPresent(arg_0 -> ((ServerRecipeBook)this.player.getRecipeBook()).removeHighlight(arg_0));
    }

    public void handleRecipeBookChangeSettingsPacket(ServerboundRecipeBookChangeSettingsPacket serverboundRecipeBookChangeSettingsPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundRecipeBookChangeSettingsPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        this.player.getRecipeBook().setBookSetting(serverboundRecipeBookChangeSettingsPacket.getBookType(), serverboundRecipeBookChangeSettingsPacket.isOpen(), serverboundRecipeBookChangeSettingsPacket.isFiltering());
    }

    public void handleSeenAdvancements(ServerboundSeenAdvancementsPacket serverboundSeenAdvancementsPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundSeenAdvancementsPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        if (serverboundSeenAdvancementsPacket.getAction() == ServerboundSeenAdvancementsPacket.Action.OPENED_TAB) {
            ResourceLocation resourceLocation = serverboundSeenAdvancementsPacket.getTab();
            Advancement advancement = this.server.getAdvancements().getAdvancement(resourceLocation);
            if (advancement != null) {
                this.player.getAdvancements().setSelectedTab(advancement);
            }
        }
    }

    public void handleCustomCommandSuggestions(ServerboundCommandSuggestionPacket serverboundCommandSuggestionPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundCommandSuggestionPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        StringReader stringReader = new StringReader(serverboundCommandSuggestionPacket.getCommand());
        if (stringReader.canRead() && stringReader.peek() == '/') {
            stringReader.skip();
        }
        ParseResults parseResults = this.server.getCommands().getDispatcher().parse(stringReader, (Object)this.player.createCommandSourceStack());
        this.server.getCommands().getDispatcher().getCompletionSuggestions(parseResults).thenAccept(suggestions -> this.connection.send((Packet)new ClientboundCommandSuggestionsPacket(serverboundCommandSuggestionPacket.getId(), suggestions)));
    }

    public void handleSetCommandBlock(ServerboundSetCommandBlockPacket serverboundSetCommandBlockPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundSetCommandBlockPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        if (!this.server.isCommandBlockEnabled()) {
            this.player.sendMessage((Component)new TranslatableComponent("advMode.notEnabled"), Util.NIL_UUID);
            return;
        }
        if (!this.player.canUseGameMasterBlocks()) {
            this.player.sendMessage((Component)new TranslatableComponent("advMode.notAllowed"), Util.NIL_UUID);
            return;
        }
        BaseCommandBlock baseCommandBlock = null;
        CommandBlockEntity commandBlockEntity = null;
        BlockPos blockPos = serverboundSetCommandBlockPacket.getPos();
        BlockEntity blockEntity = this.player.level.getBlockEntity(blockPos);
        if (blockEntity instanceof CommandBlockEntity) {
            commandBlockEntity = (CommandBlockEntity)blockEntity;
            baseCommandBlock = commandBlockEntity.getCommandBlock();
        }
        String string = serverboundSetCommandBlockPacket.getCommand();
        boolean bl = serverboundSetCommandBlockPacket.isTrackOutput();
        if (baseCommandBlock != null) {
            CommandBlockEntity.Mode mode = commandBlockEntity.getMode();
            Direction direction = (Direction)this.player.level.getBlockState(blockPos).getValue((Property)CommandBlock.FACING);
            switch (1.$SwitchMap$net$minecraft$world$level$block$entity$CommandBlockEntity$Mode[serverboundSetCommandBlockPacket.getMode().ordinal()]) {
                case 1: {
                    BlockState blockState = Blocks.CHAIN_COMMAND_BLOCK.defaultBlockState();
                    this.player.level.setBlock(blockPos, (BlockState)((BlockState)blockState.setValue((Property)CommandBlock.FACING, (Comparable)direction)).setValue((Property)CommandBlock.CONDITIONAL, (Comparable)Boolean.valueOf(serverboundSetCommandBlockPacket.isConditional())), 2);
                    break;
                }
                case 2: {
                    BlockState blockState = Blocks.REPEATING_COMMAND_BLOCK.defaultBlockState();
                    this.player.level.setBlock(blockPos, (BlockState)((BlockState)blockState.setValue((Property)CommandBlock.FACING, (Comparable)direction)).setValue((Property)CommandBlock.CONDITIONAL, (Comparable)Boolean.valueOf(serverboundSetCommandBlockPacket.isConditional())), 2);
                    break;
                }
                default: {
                    BlockState blockState = Blocks.COMMAND_BLOCK.defaultBlockState();
                    this.player.level.setBlock(blockPos, (BlockState)((BlockState)blockState.setValue((Property)CommandBlock.FACING, (Comparable)direction)).setValue((Property)CommandBlock.CONDITIONAL, (Comparable)Boolean.valueOf(serverboundSetCommandBlockPacket.isConditional())), 2);
                }
            }
            blockEntity.clearRemoved();
            this.player.level.setBlockEntity(blockPos, blockEntity);
            baseCommandBlock.setCommand(string);
            baseCommandBlock.setTrackOutput(bl);
            if (!bl) {
                baseCommandBlock.setLastOutput(null);
            }
            commandBlockEntity.setAutomatic(serverboundSetCommandBlockPacket.isAutomatic());
            if (mode != serverboundSetCommandBlockPacket.getMode()) {
                commandBlockEntity.onModeSwitch();
            }
            baseCommandBlock.onUpdated();
            if (!StringUtil.isNullOrEmpty((String)string)) {
                this.player.sendMessage((Component)new TranslatableComponent("advMode.setCommand.success", new Object[]{string}), Util.NIL_UUID);
            }
        }
    }

    public void handleSetCommandMinecart(ServerboundSetCommandMinecartPacket serverboundSetCommandMinecartPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundSetCommandMinecartPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        if (!this.server.isCommandBlockEnabled()) {
            this.player.sendMessage((Component)new TranslatableComponent("advMode.notEnabled"), Util.NIL_UUID);
            return;
        }
        if (!this.player.canUseGameMasterBlocks()) {
            this.player.sendMessage((Component)new TranslatableComponent("advMode.notAllowed"), Util.NIL_UUID);
            return;
        }
        BaseCommandBlock baseCommandBlock = serverboundSetCommandMinecartPacket.getCommandBlock(this.player.level);
        if (baseCommandBlock != null) {
            baseCommandBlock.setCommand(serverboundSetCommandMinecartPacket.getCommand());
            baseCommandBlock.setTrackOutput(serverboundSetCommandMinecartPacket.isTrackOutput());
            if (!serverboundSetCommandMinecartPacket.isTrackOutput()) {
                baseCommandBlock.setLastOutput(null);
            }
            baseCommandBlock.onUpdated();
            this.player.sendMessage((Component)new TranslatableComponent("advMode.setCommand.success", new Object[]{serverboundSetCommandMinecartPacket.getCommand()}), Util.NIL_UUID);
        }
    }

    public void handlePickItem(ServerboundPickItemPacket serverboundPickItemPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundPickItemPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        this.player.inventory.pickSlot(serverboundPickItemPacket.getSlot());
        this.player.connection.send((Packet<?>)new ClientboundContainerSetSlotPacket(-2, this.player.inventory.selected, this.player.inventory.getItem(this.player.inventory.selected)));
        this.player.connection.send((Packet<?>)new ClientboundContainerSetSlotPacket(-2, serverboundPickItemPacket.getSlot(), this.player.inventory.getItem(serverboundPickItemPacket.getSlot())));
        this.player.connection.send((Packet<?>)new ClientboundSetCarriedItemPacket(this.player.inventory.selected));
    }

    public void handleRenameItem(ServerboundRenameItemPacket serverboundRenameItemPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundRenameItemPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        if (this.player.containerMenu instanceof AnvilMenu) {
            AnvilMenu anvilMenu = (AnvilMenu)this.player.containerMenu;
            String string = SharedConstants.filterText((String)serverboundRenameItemPacket.getName());
            if (string.length() <= 35) {
                anvilMenu.setItemName(string);
            }
        }
    }

    public void handleSetBeaconPacket(ServerboundSetBeaconPacket serverboundSetBeaconPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundSetBeaconPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        if (this.player.containerMenu instanceof BeaconMenu) {
            ((BeaconMenu)this.player.containerMenu).updateEffects(serverboundSetBeaconPacket.getPrimary(), serverboundSetBeaconPacket.getSecondary());
        }
    }

    public void handleSetStructureBlock(ServerboundSetStructureBlockPacket serverboundSetStructureBlockPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundSetStructureBlockPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        if (!this.player.canUseGameMasterBlocks()) {
            return;
        }
        BlockPos blockPos = serverboundSetStructureBlockPacket.getPos();
        BlockState blockState = this.player.level.getBlockState(blockPos);
        BlockEntity blockEntity = this.player.level.getBlockEntity(blockPos);
        if (blockEntity instanceof StructureBlockEntity) {
            StructureBlockEntity structureBlockEntity = (StructureBlockEntity)blockEntity;
            structureBlockEntity.setMode(serverboundSetStructureBlockPacket.getMode());
            structureBlockEntity.setStructureName(serverboundSetStructureBlockPacket.getName());
            structureBlockEntity.setStructurePos(serverboundSetStructureBlockPacket.getOffset());
            structureBlockEntity.setStructureSize(serverboundSetStructureBlockPacket.getSize());
            structureBlockEntity.setMirror(serverboundSetStructureBlockPacket.getMirror());
            structureBlockEntity.setRotation(serverboundSetStructureBlockPacket.getRotation());
            structureBlockEntity.setMetaData(serverboundSetStructureBlockPacket.getData());
            structureBlockEntity.setIgnoreEntities(serverboundSetStructureBlockPacket.isIgnoreEntities());
            structureBlockEntity.setShowAir(serverboundSetStructureBlockPacket.isShowAir());
            structureBlockEntity.setShowBoundingBox(serverboundSetStructureBlockPacket.isShowBoundingBox());
            structureBlockEntity.setIntegrity(serverboundSetStructureBlockPacket.getIntegrity());
            structureBlockEntity.setSeed(serverboundSetStructureBlockPacket.getSeed());
            if (structureBlockEntity.hasStructureName()) {
                String string = structureBlockEntity.getStructureName();
                if (serverboundSetStructureBlockPacket.getUpdateType() == StructureBlockEntity.UpdateType.SAVE_AREA) {
                    if (structureBlockEntity.saveStructure()) {
                        this.player.displayClientMessage((Component)new TranslatableComponent("structure_block.save_success", new Object[]{string}), false);
                    } else {
                        this.player.displayClientMessage((Component)new TranslatableComponent("structure_block.save_failure", new Object[]{string}), false);
                    }
                } else if (serverboundSetStructureBlockPacket.getUpdateType() == StructureBlockEntity.UpdateType.LOAD_AREA) {
                    if (!structureBlockEntity.isStructureLoadable()) {
                        this.player.displayClientMessage((Component)new TranslatableComponent("structure_block.load_not_found", new Object[]{string}), false);
                    } else if (structureBlockEntity.loadStructure(this.player.getLevel())) {
                        this.player.displayClientMessage((Component)new TranslatableComponent("structure_block.load_success", new Object[]{string}), false);
                    } else {
                        this.player.displayClientMessage((Component)new TranslatableComponent("structure_block.load_prepare", new Object[]{string}), false);
                    }
                } else if (serverboundSetStructureBlockPacket.getUpdateType() == StructureBlockEntity.UpdateType.SCAN_AREA) {
                    if (structureBlockEntity.detectSize()) {
                        this.player.displayClientMessage((Component)new TranslatableComponent("structure_block.size_success", new Object[]{string}), false);
                    } else {
                        this.player.displayClientMessage((Component)new TranslatableComponent("structure_block.size_failure"), false);
                    }
                }
            } else {
                this.player.displayClientMessage((Component)new TranslatableComponent("structure_block.invalid_structure_name", new Object[]{serverboundSetStructureBlockPacket.getName()}), false);
            }
            structureBlockEntity.setChanged();
            this.player.level.sendBlockUpdated(blockPos, blockState, blockState, 3);
        }
    }

    public void handleSetJigsawBlock(ServerboundSetJigsawBlockPacket serverboundSetJigsawBlockPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundSetJigsawBlockPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        if (!this.player.canUseGameMasterBlocks()) {
            return;
        }
        BlockPos blockPos = serverboundSetJigsawBlockPacket.getPos();
        BlockState blockState = this.player.level.getBlockState(blockPos);
        BlockEntity blockEntity = this.player.level.getBlockEntity(blockPos);
        if (blockEntity instanceof JigsawBlockEntity) {
            JigsawBlockEntity jigsawBlockEntity = (JigsawBlockEntity)blockEntity;
            jigsawBlockEntity.setName(serverboundSetJigsawBlockPacket.getName());
            jigsawBlockEntity.setTarget(serverboundSetJigsawBlockPacket.getTarget());
            jigsawBlockEntity.setPool(serverboundSetJigsawBlockPacket.getPool());
            jigsawBlockEntity.setFinalState(serverboundSetJigsawBlockPacket.getFinalState());
            jigsawBlockEntity.setJoint(serverboundSetJigsawBlockPacket.getJoint());
            jigsawBlockEntity.setChanged();
            this.player.level.sendBlockUpdated(blockPos, blockState, blockState, 3);
        }
    }

    public void handleJigsawGenerate(ServerboundJigsawGeneratePacket serverboundJigsawGeneratePacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundJigsawGeneratePacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        if (!this.player.canUseGameMasterBlocks()) {
            return;
        }
        BlockPos blockPos = serverboundJigsawGeneratePacket.getPos();
        BlockEntity blockEntity = this.player.level.getBlockEntity(blockPos);
        if (blockEntity instanceof JigsawBlockEntity) {
            JigsawBlockEntity jigsawBlockEntity = (JigsawBlockEntity)blockEntity;
            jigsawBlockEntity.generate(this.player.getLevel(), serverboundJigsawGeneratePacket.levels(), serverboundJigsawGeneratePacket.keepJigsaws());
        }
    }

    public void handleSelectTrade(ServerboundSelectTradePacket serverboundSelectTradePacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundSelectTradePacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        int i = serverboundSelectTradePacket.getItem();
        AbstractContainerMenu abstractContainerMenu = this.player.containerMenu;
        if (abstractContainerMenu instanceof MerchantMenu) {
            MerchantMenu merchantMenu = (MerchantMenu)abstractContainerMenu;
            merchantMenu.setSelectionHint(i);
            merchantMenu.tryMoveItems(i);
        }
    }

    public void handleEditBook(ServerboundEditBookPacket serverboundEditBookPacket) {
        int i;
        ItemStack itemStack = serverboundEditBookPacket.getBook();
        if (itemStack.getItem() != Items.WRITABLE_BOOK) {
            return;
        }
        CompoundTag compoundTag = itemStack.getTag();
        if (!WritableBookItem.makeSureTagIsValid((CompoundTag)compoundTag)) {
            return;
        }
        ArrayList list2 = Lists.newArrayList();
        boolean bl = serverboundEditBookPacket.isSigning();
        if (bl) {
            list2.add(compoundTag.getString("title"));
        }
        ListTag listTag = compoundTag.getList("pages", 8);
        for (i = 0; i < listTag.size(); ++i) {
            list2.add(listTag.getString(i));
        }
        i = serverboundEditBookPacket.getSlot();
        if (!Inventory.isHotbarSlot((int)i) && i != 40) {
            return;
        }
        this.filterTextPacket(list2, bl ? list -> this.signBook((String)list.get(0), list.subList(1, list.size()), i) : list -> this.updateBookContents((List<String>)list, i));
    }

    private void updateBookContents(List<String> list, int i) {
        ItemStack itemStack = this.player.inventory.getItem(i);
        if (itemStack.getItem() != Items.WRITABLE_BOOK) {
            return;
        }
        ListTag listTag = new ListTag();
        list.stream().map(StringTag::valueOf).forEach(arg_0 -> listTag.add(arg_0));
        itemStack.addTagElement("pages", (Tag)listTag);
    }

    private void signBook(String string, List<String> list, int i) {
        ItemStack itemStack = this.player.inventory.getItem(i);
        if (itemStack.getItem() != Items.WRITABLE_BOOK) {
            return;
        }
        ItemStack itemStack2 = new ItemStack((ItemLike)Items.WRITTEN_BOOK);
        CompoundTag compoundTag = itemStack.getTag();
        if (compoundTag != null) {
            itemStack2.setTag(compoundTag.copy());
        }
        itemStack2.addTagElement("author", (Tag)StringTag.valueOf((String)this.player.getName().getString()));
        itemStack2.addTagElement("title", (Tag)StringTag.valueOf((String)string));
        ListTag listTag = new ListTag();
        for (String string2 : list) {
            TextComponent component = new TextComponent(string2);
            String string3 = Component.Serializer.toJson((Component)component);
            listTag.add((Object)StringTag.valueOf((String)string3));
        }
        itemStack2.addTagElement("pages", (Tag)listTag);
        this.player.inventory.setItem(i, itemStack2);
    }

    public void handleEntityTagQuery(ServerboundEntityTagQuery serverboundEntityTagQuery) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundEntityTagQuery, (PacketListener)this, (ServerLevel)this.player.getLevel());
        if (!this.player.hasPermissions(2)) {
            return;
        }
        Entity entity = this.player.getLevel().getEntity(serverboundEntityTagQuery.getEntityId());
        if (entity != null) {
            CompoundTag compoundTag = entity.saveWithoutId(new CompoundTag());
            this.player.connection.send((Packet<?>)new ClientboundTagQueryPacket(serverboundEntityTagQuery.getTransactionId(), compoundTag));
        }
    }

    public void handleBlockEntityTagQuery(ServerboundBlockEntityTagQuery serverboundBlockEntityTagQuery) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundBlockEntityTagQuery, (PacketListener)this, (ServerLevel)this.player.getLevel());
        if (!this.player.hasPermissions(2)) {
            return;
        }
        BlockEntity blockEntity = this.player.getLevel().getBlockEntity(serverboundBlockEntityTagQuery.getPos());
        CompoundTag compoundTag = blockEntity != null ? blockEntity.save(new CompoundTag()) : null;
        this.player.connection.send((Packet<?>)new ClientboundTagQueryPacket(serverboundBlockEntityTagQuery.getTransactionId(), compoundTag));
    }

    public void handleMovePlayer(ServerboundMovePlayerPacket serverboundMovePlayerPacket) {
        boolean bl;
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundMovePlayerPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        if (ServerGamePacketListenerImpl.containsInvalidValues(serverboundMovePlayerPacket)) {
            this.disconnect((Component)new TranslatableComponent("multiplayer.disconnect.invalid_player_movement"));
            return;
        }
        ServerLevel serverLevel = this.player.getLevel();
        if (this.player.wonGame) {
            return;
        }
        if (this.tickCount == 0) {
            this.resetPosition();
        }
        if (this.awaitingPositionFromClient != null) {
            if (this.tickCount - this.awaitingTeleportTime > 20) {
                this.awaitingTeleportTime = this.tickCount;
                this.teleport(this.awaitingPositionFromClient.x, this.awaitingPositionFromClient.y, this.awaitingPositionFromClient.z, this.player.yRot, this.player.xRot);
            }
            return;
        }
        this.awaitingTeleportTime = this.tickCount;
        if (this.player.isPassenger()) {
            this.player.absMoveTo(this.player.getX(), this.player.getY(), this.player.getZ(), serverboundMovePlayerPacket.getYRot(this.player.yRot), serverboundMovePlayerPacket.getXRot(this.player.xRot));
            this.player.getLevel().getChunkSource().move(this.player);
            return;
        }
        double d = this.player.getX();
        double e = this.player.getY();
        double f = this.player.getZ();
        double g = this.player.getY();
        double h = serverboundMovePlayerPacket.getX(this.player.getX());
        double i = serverboundMovePlayerPacket.getY(this.player.getY());
        double j = serverboundMovePlayerPacket.getZ(this.player.getZ());
        float k = serverboundMovePlayerPacket.getYRot(this.player.yRot);
        float l = serverboundMovePlayerPacket.getXRot(this.player.xRot);
        double m = h - this.firstGoodX;
        double n = i - this.firstGoodY;
        double o = j - this.firstGoodZ;
        double p = this.player.getDeltaMovement().lengthSqr();
        double q = m * m + n * n + o * o;
        if (this.player.isSleeping()) {
            if (q > 1.0) {
                this.teleport(this.player.getX(), this.player.getY(), this.player.getZ(), serverboundMovePlayerPacket.getYRot(this.player.yRot), serverboundMovePlayerPacket.getXRot(this.player.xRot));
            }
            return;
        }
        ++this.receivedMovePacketCount;
        int r = this.receivedMovePacketCount - this.knownMovePacketCount;
        if (r > 5) {
            LOGGER.debug("{} is sending move packets too frequently ({} packets since last tick)", (Object)this.player.getName().getString(), (Object)r);
            r = 1;
        }
        if (!(this.player.isChangingDimension() || this.player.getLevel().getGameRules().getBoolean(GameRules.RULE_DISABLE_ELYTRA_MOVEMENT_CHECK) && this.player.isFallFlying())) {
            float s;
            float f2 = s = this.player.isFallFlying() ? 300.0f : 100.0f;
            if (q - p > (double)(s * (float)r) && !this.isSingleplayerOwner()) {
                LOGGER.warn("{} moved too quickly! {},{},{}", (Object)this.player.getName().getString(), (Object)m, (Object)n, (Object)o);
                this.teleport(this.player.getX(), this.player.getY(), this.player.getZ(), this.player.yRot, this.player.xRot);
                return;
            }
        }
        AABB aABB = this.player.getBoundingBox();
        m = h - this.lastGoodX;
        n = i - this.lastGoodY;
        o = j - this.lastGoodZ;
        boolean bl2 = bl = n > 0.0;
        if (this.player.isOnGround() && !serverboundMovePlayerPacket.isOnGround() && bl) {
            this.player.jumpFromGround();
        }
        this.player.move(MoverType.PLAYER, new Vec3(m, n, o));
        double t = n;
        m = h - this.player.getX();
        n = i - this.player.getY();
        if (n > -0.5 || n < 0.5) {
            n = 0.0;
        }
        o = j - this.player.getZ();
        q = m * m + n * n + o * o;
        boolean bl22 = false;
        if (!this.player.isChangingDimension() && q > 0.0625 && !this.player.isSleeping() && !this.player.gameMode.isCreative() && this.player.gameMode.getGameModeForPlayer() != GameType.SPECTATOR) {
            bl22 = true;
            LOGGER.warn("{} moved wrongly!", (Object)this.player.getName().getString());
        }
        this.player.absMoveTo(h, i, j, k, l);
        if (!this.player.noPhysics && !this.player.isSleeping() && (bl22 && serverLevel.noCollision((Entity)this.player, aABB) || this.isPlayerCollidingWithAnythingNew((LevelReader)serverLevel, aABB))) {
            this.teleport(d, e, f, k, l);
            return;
        }
        this.clientIsFloating = t >= -0.03125 && this.player.gameMode.getGameModeForPlayer() != GameType.SPECTATOR && !this.server.isFlightAllowed() && !this.player.abilities.mayfly && !this.player.hasEffect(MobEffects.LEVITATION) && !this.player.isFallFlying() && this.noBlocksAround((Entity)this.player);
        this.player.getLevel().getChunkSource().move(this.player);
        this.player.doCheckFallDamage(this.player.getY() - g, serverboundMovePlayerPacket.isOnGround());
        this.player.setOnGround(serverboundMovePlayerPacket.isOnGround());
        if (bl) {
            this.player.fallDistance = 0.0f;
        }
        this.player.checkMovementStatistics(this.player.getX() - d, this.player.getY() - e, this.player.getZ() - f);
        this.lastGoodX = this.player.getX();
        this.lastGoodY = this.player.getY();
        this.lastGoodZ = this.player.getZ();
    }

    private boolean isPlayerCollidingWithAnythingNew(LevelReader levelReader, AABB aABB) {
        Stream stream = levelReader.getCollisions((Entity)this.player, this.player.getBoundingBox().deflate((double)1.0E-5f), entity -> true);
        VoxelShape voxelShape = Shapes.create((AABB)aABB.deflate((double)1.0E-5f));
        return stream.anyMatch(voxelShape2 -> !Shapes.joinIsNotEmpty((VoxelShape)voxelShape2, (VoxelShape)voxelShape, (BooleanOp)BooleanOp.AND));
    }

    public void teleport(double d, double e, double f, float g, float h) {
        this.teleport(d, e, f, g, h, Collections.emptySet());
    }

    public void teleport(double d, double e, double f, float g, float h, Set<ClientboundPlayerPositionPacket.RelativeArgument> set) {
        double i = set.contains(ClientboundPlayerPositionPacket.RelativeArgument.X) ? this.player.getX() : 0.0;
        double j = set.contains(ClientboundPlayerPositionPacket.RelativeArgument.Y) ? this.player.getY() : 0.0;
        double k = set.contains(ClientboundPlayerPositionPacket.RelativeArgument.Z) ? this.player.getZ() : 0.0;
        float l = set.contains(ClientboundPlayerPositionPacket.RelativeArgument.Y_ROT) ? this.player.yRot : 0.0f;
        float m = set.contains(ClientboundPlayerPositionPacket.RelativeArgument.X_ROT) ? this.player.xRot : 0.0f;
        this.awaitingPositionFromClient = new Vec3(d, e, f);
        if (++this.awaitingTeleport == Integer.MAX_VALUE) {
            this.awaitingTeleport = 0;
        }
        this.awaitingTeleportTime = this.tickCount;
        this.player.absMoveTo(d, e, f, g, h);
        this.player.connection.send((Packet<?>)new ClientboundPlayerPositionPacket(d - i, e - j, f - k, g - l, h - m, set, this.awaitingTeleport));
    }

    public void handlePlayerAction(ServerboundPlayerActionPacket serverboundPlayerActionPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundPlayerActionPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        BlockPos blockPos = serverboundPlayerActionPacket.getPos();
        this.player.resetLastActionTime();
        ServerboundPlayerActionPacket.Action action = serverboundPlayerActionPacket.getAction();
        switch (1.$SwitchMap$net$minecraft$network$protocol$game$ServerboundPlayerActionPacket$Action[action.ordinal()]) {
            case 1: {
                if (!this.player.isSpectator()) {
                    ItemStack itemStack = this.player.getItemInHand(InteractionHand.OFF_HAND);
                    this.player.setItemInHand(InteractionHand.OFF_HAND, this.player.getItemInHand(InteractionHand.MAIN_HAND));
                    this.player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
                    this.player.stopUsingItem();
                }
                return;
            }
            case 2: {
                if (!this.player.isSpectator()) {
                    this.player.drop(false);
                }
                return;
            }
            case 3: {
                if (!this.player.isSpectator()) {
                    this.player.drop(true);
                }
                return;
            }
            case 4: {
                this.player.releaseUsingItem();
                return;
            }
            case 5: 
            case 6: 
            case 7: {
                this.player.gameMode.handleBlockBreakAction(blockPos, action, serverboundPlayerActionPacket.getDirection(), this.server.getMaxBuildHeight());
                return;
            }
        }
        throw new IllegalArgumentException("Invalid player action");
    }

    private static boolean wasBlockPlacementAttempt(ServerPlayer serverPlayer, ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            return false;
        }
        Item item = itemStack.getItem();
        return (item instanceof BlockItem || item instanceof BucketItem) && !serverPlayer.getCooldowns().isOnCooldown(item);
    }

    public void handleUseItemOn(ServerboundUseItemOnPacket serverboundUseItemOnPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundUseItemOnPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        ServerLevel serverLevel = this.player.getLevel();
        InteractionHand interactionHand = serverboundUseItemOnPacket.getHand();
        ItemStack itemStack = this.player.getItemInHand(interactionHand);
        BlockHitResult blockHitResult = serverboundUseItemOnPacket.getHitResult();
        BlockPos blockPos = blockHitResult.getBlockPos();
        Direction direction = blockHitResult.getDirection();
        this.player.resetLastActionTime();
        if (blockPos.getY() < this.server.getMaxBuildHeight()) {
            if (this.awaitingPositionFromClient == null && this.player.distanceToSqr((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5) < 64.0 && serverLevel.mayInteract((Player)this.player, blockPos)) {
                InteractionResult interactionResult = this.player.gameMode.useItemOn(this.player, (Level)serverLevel, itemStack, interactionHand, blockHitResult);
                if (direction == Direction.UP && !interactionResult.consumesAction() && blockPos.getY() >= this.server.getMaxBuildHeight() - 1 && ServerGamePacketListenerImpl.wasBlockPlacementAttempt(this.player, itemStack)) {
                    MutableComponent component = new TranslatableComponent("build.tooHigh", new Object[]{this.server.getMaxBuildHeight()}).withStyle(ChatFormatting.RED);
                    this.player.connection.send((Packet<?>)new ClientboundChatPacket((Component)component, ChatType.GAME_INFO, Util.NIL_UUID));
                } else if (interactionResult.shouldSwing()) {
                    this.player.swing(interactionHand, true);
                }
            }
        } else {
            MutableComponent component2 = new TranslatableComponent("build.tooHigh", new Object[]{this.server.getMaxBuildHeight()}).withStyle(ChatFormatting.RED);
            this.player.connection.send((Packet<?>)new ClientboundChatPacket((Component)component2, ChatType.GAME_INFO, Util.NIL_UUID));
        }
        this.player.connection.send((Packet<?>)new ClientboundBlockUpdatePacket((BlockGetter)serverLevel, blockPos));
        this.player.connection.send((Packet<?>)new ClientboundBlockUpdatePacket((BlockGetter)serverLevel, blockPos.relative(direction)));
    }

    public void handleUseItem(ServerboundUseItemPacket serverboundUseItemPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundUseItemPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        ServerLevel serverLevel = this.player.getLevel();
        InteractionHand interactionHand = serverboundUseItemPacket.getHand();
        ItemStack itemStack = this.player.getItemInHand(interactionHand);
        this.player.resetLastActionTime();
        if (itemStack.isEmpty()) {
            return;
        }
        InteractionResult interactionResult = this.player.gameMode.useItem(this.player, (Level)serverLevel, itemStack, interactionHand);
        if (interactionResult.shouldSwing()) {
            this.player.swing(interactionHand, true);
        }
    }

    public void handleTeleportToEntityPacket(ServerboundTeleportToEntityPacket serverboundTeleportToEntityPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundTeleportToEntityPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        if (this.player.isSpectator()) {
            for (ServerLevel serverLevel : this.server.getAllLevels()) {
                Entity entity = serverboundTeleportToEntityPacket.getEntity(serverLevel);
                if (entity == null) continue;
                this.player.teleportTo(serverLevel, entity.getX(), entity.getY(), entity.getZ(), entity.yRot, entity.xRot);
                return;
            }
        }
    }

    public void handleResourcePackResponse(ServerboundResourcePackPacket serverboundResourcePackPacket) {
    }

    public void handlePaddleBoat(ServerboundPaddleBoatPacket serverboundPaddleBoatPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundPaddleBoatPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        Entity entity = this.player.getVehicle();
        if (entity instanceof Boat) {
            ((Boat)entity).setPaddleState(serverboundPaddleBoatPacket.getLeft(), serverboundPaddleBoatPacket.getRight());
        }
    }

    public void onDisconnect(Component component) {
        LOGGER.info("{} lost connection: {}", (Object)this.player.getName().getString(), (Object)component.getString());
        this.server.invalidateStatus();
        this.server.getPlayerList().broadcastMessage((Component)new TranslatableComponent("multiplayer.player.left", new Object[]{this.player.getDisplayName()}).withStyle(ChatFormatting.YELLOW), ChatType.SYSTEM, Util.NIL_UUID);
        this.player.disconnect();
        this.server.getPlayerList().remove(this.player);
        TextFilter textFilter = this.player.getTextFilter();
        if (textFilter != null) {
            textFilter.leave();
        }
        if (this.isSingleplayerOwner()) {
            LOGGER.info("Stopping singleplayer server as player logged out");
            this.server.halt(false);
        }
    }

    public void send(Packet<?> packet) {
        this.send(packet, null);
    }

    public void send(Packet<?> packet, @Nullable GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        if (packet instanceof ClientboundChatPacket) {
            ClientboundChatPacket clientboundChatPacket = (ClientboundChatPacket)packet;
            ChatVisiblity chatVisiblity = this.player.getChatVisibility();
            if (chatVisiblity == ChatVisiblity.HIDDEN && clientboundChatPacket.getType() != ChatType.GAME_INFO) {
                return;
            }
            if (chatVisiblity == ChatVisiblity.SYSTEM && !clientboundChatPacket.isSystem()) {
                return;
            }
        }
        try {
            this.connection.send(packet, genericFutureListener);
        }
        catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.forThrowable((Throwable)throwable, (String)"Sending packet");
            CrashReportCategory crashReportCategory = crashReport.addCategory("Packet being sent");
            crashReportCategory.setDetail("Packet class", () -> packet.getClass().getCanonicalName());
            throw new ReportedException(crashReport);
        }
    }

    public void handleSetCarriedItem(ServerboundSetCarriedItemPacket serverboundSetCarriedItemPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundSetCarriedItemPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        if (serverboundSetCarriedItemPacket.getSlot() < 0 || serverboundSetCarriedItemPacket.getSlot() >= Inventory.getSelectionSize()) {
            LOGGER.warn("{} tried to set an invalid carried item", (Object)this.player.getName().getString());
            return;
        }
        if (this.player.inventory.selected != serverboundSetCarriedItemPacket.getSlot() && this.player.getUsedItemHand() == InteractionHand.MAIN_HAND) {
            this.player.stopUsingItem();
        }
        this.player.inventory.selected = serverboundSetCarriedItemPacket.getSlot();
        this.player.resetLastActionTime();
    }

    public void handleChat(ServerboundChatPacket serverboundChatPacket) {
        String string = StringUtils.normalizeSpace((String)serverboundChatPacket.getMessage());
        if (string.startsWith("/")) {
            PacketUtils.ensureRunningOnSameThread((Packet)serverboundChatPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
            this.handleChat(string);
        } else {
            this.filterTextPacket(string, this::handleChat);
        }
    }

    private void handleChat(String string) {
        if (this.player.getChatVisibility() == ChatVisiblity.HIDDEN) {
            this.send((Packet<?>)new ClientboundChatPacket((Component)new TranslatableComponent("chat.cannotSend").withStyle(ChatFormatting.RED), ChatType.SYSTEM, Util.NIL_UUID));
            return;
        }
        this.player.resetLastActionTime();
        for (int i = 0; i < string.length(); ++i) {
            if (SharedConstants.isAllowedChatCharacter((char)string.charAt(i))) continue;
            this.disconnect((Component)new TranslatableComponent("multiplayer.disconnect.illegal_characters"));
            return;
        }
        if (string.startsWith("/")) {
            this.handleCommand(string);
        } else {
            TranslatableComponent component = new TranslatableComponent("chat.type.text", new Object[]{this.player.getDisplayName(), string});
            this.server.getPlayerList().broadcastMessage((Component)component, ChatType.CHAT, this.player.getUUID());
        }
        this.chatSpamTickCount += 20;
        if (this.chatSpamTickCount > 200 && !this.server.getPlayerList().isOp(this.player.getGameProfile())) {
            this.disconnect((Component)new TranslatableComponent("disconnect.spam"));
        }
    }

    private void handleCommand(String string) {
        this.server.getCommands().performCommand(this.player.createCommandSourceStack(), string);
    }

    public void handleAnimate(ServerboundSwingPacket serverboundSwingPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundSwingPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        this.player.resetLastActionTime();
        this.player.swing(serverboundSwingPacket.getHand());
    }

    public void handlePlayerCommand(ServerboundPlayerCommandPacket serverboundPlayerCommandPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundPlayerCommandPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        this.player.resetLastActionTime();
        switch (1.$SwitchMap$net$minecraft$network$protocol$game$ServerboundPlayerCommandPacket$Action[serverboundPlayerCommandPacket.getAction().ordinal()]) {
            case 1: {
                this.player.setShiftKeyDown(true);
                break;
            }
            case 2: {
                this.player.setShiftKeyDown(false);
                break;
            }
            case 3: {
                this.player.setSprinting(true);
                break;
            }
            case 4: {
                this.player.setSprinting(false);
                break;
            }
            case 5: {
                if (!this.player.isSleeping()) break;
                this.player.stopSleepInBed(false, true);
                this.awaitingPositionFromClient = this.player.position();
                break;
            }
            case 6: {
                if (!(this.player.getVehicle() instanceof PlayerRideableJumping)) break;
                PlayerRideableJumping playerRideableJumping = (PlayerRideableJumping)this.player.getVehicle();
                int i = serverboundPlayerCommandPacket.getData();
                if (!playerRideableJumping.canJump() || i <= 0) break;
                playerRideableJumping.handleStartJump(i);
                break;
            }
            case 7: {
                if (!(this.player.getVehicle() instanceof PlayerRideableJumping)) break;
                PlayerRideableJumping playerRideableJumping = (PlayerRideableJumping)this.player.getVehicle();
                playerRideableJumping.handleStopJump();
                break;
            }
            case 8: {
                if (!(this.player.getVehicle() instanceof AbstractHorse)) break;
                ((AbstractHorse)this.player.getVehicle()).openInventory((Player)this.player);
                break;
            }
            case 9: {
                if (this.player.tryToStartFallFlying()) break;
                this.player.stopFallFlying();
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid client command!");
            }
        }
    }

    public void handleInteract(ServerboundInteractPacket serverboundInteractPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundInteractPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        ServerLevel serverLevel = this.player.getLevel();
        Entity entity = serverboundInteractPacket.getTarget((Level)serverLevel);
        this.player.resetLastActionTime();
        this.player.setShiftKeyDown(serverboundInteractPacket.isUsingSecondaryAction());
        if (entity != null) {
            double d = 36.0;
            if (this.player.distanceToSqr(entity) < 36.0) {
                InteractionHand interactionHand = serverboundInteractPacket.getHand();
                ItemStack itemStack = interactionHand != null ? this.player.getItemInHand(interactionHand).copy() : ItemStack.EMPTY;
                Optional<Object> optional = Optional.empty();
                if (serverboundInteractPacket.getAction() == ServerboundInteractPacket.Action.INTERACT) {
                    optional = Optional.of(this.player.interactOn(entity, interactionHand));
                } else if (serverboundInteractPacket.getAction() == ServerboundInteractPacket.Action.INTERACT_AT) {
                    optional = Optional.of(entity.interactAt((Player)this.player, serverboundInteractPacket.getLocation(), interactionHand));
                } else if (serverboundInteractPacket.getAction() == ServerboundInteractPacket.Action.ATTACK) {
                    if (entity instanceof ItemEntity || entity instanceof ExperienceOrb || entity instanceof AbstractArrow || entity == this.player) {
                        this.disconnect((Component)new TranslatableComponent("multiplayer.disconnect.invalid_entity_attacked"));
                        LOGGER.warn("Player {} tried to attack an invalid entity", (Object)this.player.getName().getString());
                        return;
                    }
                    this.player.attack(entity);
                }
                if (optional.isPresent() && ((InteractionResult)optional.get()).consumesAction()) {
                    CriteriaTriggers.PLAYER_INTERACTED_WITH_ENTITY.trigger(this.player, itemStack, entity);
                    if (((InteractionResult)optional.get()).shouldSwing()) {
                        this.player.swing(interactionHand, true);
                    }
                }
            }
        }
    }

    public void handleClientCommand(ServerboundClientCommandPacket serverboundClientCommandPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundClientCommandPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        this.player.resetLastActionTime();
        ServerboundClientCommandPacket.Action action = serverboundClientCommandPacket.getAction();
        switch (1.$SwitchMap$net$minecraft$network$protocol$game$ServerboundClientCommandPacket$Action[action.ordinal()]) {
            case 1: {
                if (this.player.wonGame) {
                    this.player.wonGame = false;
                    this.player = this.server.getPlayerList().respawn(this.player, true);
                    CriteriaTriggers.CHANGED_DIMENSION.trigger(this.player, Level.END, Level.OVERWORLD);
                    break;
                }
                if (this.player.getHealth() > 0.0f) {
                    return;
                }
                this.player = this.server.getPlayerList().respawn(this.player, false);
                if (!this.server.isHardcore()) break;
                this.player.setGameMode(GameType.SPECTATOR);
                ((GameRules.BooleanValue)this.player.getLevel().getGameRules().getRule(GameRules.RULE_SPECTATORSGENERATECHUNKS)).set(false, this.server);
                break;
            }
            case 2: {
                this.player.getStats().sendStats(this.player);
            }
        }
    }

    public void handleContainerClose(ServerboundContainerClosePacket serverboundContainerClosePacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundContainerClosePacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        this.player.doCloseContainer();
    }

    public void handleContainerClick(ServerboundContainerClickPacket serverboundContainerClickPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundContainerClickPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        this.player.resetLastActionTime();
        if (this.player.containerMenu.containerId == serverboundContainerClickPacket.getContainerId() && this.player.containerMenu.isSynched((Player)this.player)) {
            if (this.player.isSpectator()) {
                NonNullList nonNullList = NonNullList.create();
                for (int i = 0; i < this.player.containerMenu.slots.size(); ++i) {
                    nonNullList.add((Object)((Slot)this.player.containerMenu.slots.get(i)).getItem());
                }
                this.player.refreshContainer(this.player.containerMenu, nonNullList);
            } else {
                ItemStack itemStack = this.player.containerMenu.clicked(serverboundContainerClickPacket.getSlotNum(), serverboundContainerClickPacket.getButtonNum(), serverboundContainerClickPacket.getClickType(), (Player)this.player);
                if (ItemStack.matches((ItemStack)serverboundContainerClickPacket.getItem(), (ItemStack)itemStack)) {
                    this.player.connection.send((Packet<?>)new ClientboundContainerAckPacket(serverboundContainerClickPacket.getContainerId(), serverboundContainerClickPacket.getUid(), true));
                    this.player.ignoreSlotUpdateHack = true;
                    this.player.containerMenu.broadcastChanges();
                    this.player.broadcastCarriedItem();
                    this.player.ignoreSlotUpdateHack = false;
                } else {
                    this.expectedAcks.put(this.player.containerMenu.containerId, serverboundContainerClickPacket.getUid());
                    this.player.connection.send((Packet<?>)new ClientboundContainerAckPacket(serverboundContainerClickPacket.getContainerId(), serverboundContainerClickPacket.getUid(), false));
                    this.player.containerMenu.setSynched((Player)this.player, false);
                    NonNullList nonNullList2 = NonNullList.create();
                    for (int j = 0; j < this.player.containerMenu.slots.size(); ++j) {
                        ItemStack itemStack2 = ((Slot)this.player.containerMenu.slots.get(j)).getItem();
                        nonNullList2.add((Object)(itemStack2.isEmpty() ? ItemStack.EMPTY : itemStack2));
                    }
                    this.player.refreshContainer(this.player.containerMenu, nonNullList2);
                }
            }
        }
    }

    public void handlePlaceRecipe(ServerboundPlaceRecipePacket serverboundPlaceRecipePacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundPlaceRecipePacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        this.player.resetLastActionTime();
        if (this.player.isSpectator() || this.player.containerMenu.containerId != serverboundPlaceRecipePacket.getContainerId() || !this.player.containerMenu.isSynched((Player)this.player) || !(this.player.containerMenu instanceof RecipeBookMenu)) {
            return;
        }
        this.server.getRecipeManager().byKey(serverboundPlaceRecipePacket.getRecipe()).ifPresent(recipe -> ((RecipeBookMenu)this.player.containerMenu).handlePlacement(serverboundPlaceRecipePacket.isShiftDown(), recipe, this.player));
    }

    public void handleContainerButtonClick(ServerboundContainerButtonClickPacket serverboundContainerButtonClickPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundContainerButtonClickPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        this.player.resetLastActionTime();
        if (this.player.containerMenu.containerId == serverboundContainerButtonClickPacket.getContainerId() && this.player.containerMenu.isSynched((Player)this.player) && !this.player.isSpectator()) {
            this.player.containerMenu.clickMenuButton((Player)this.player, serverboundContainerButtonClickPacket.getButtonId());
            this.player.containerMenu.broadcastChanges();
        }
    }

    public void handleSetCreativeModeSlot(ServerboundSetCreativeModeSlotPacket serverboundSetCreativeModeSlotPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundSetCreativeModeSlotPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        if (this.player.gameMode.isCreative()) {
            boolean bl3;
            BlockPos blockPos;
            BlockEntity blockEntity;
            boolean bl = serverboundSetCreativeModeSlotPacket.getSlotNum() < 0;
            ItemStack itemStack = serverboundSetCreativeModeSlotPacket.getItem();
            CompoundTag compoundTag = itemStack.getTagElement("BlockEntityTag");
            if (!itemStack.isEmpty() && compoundTag != null && compoundTag.contains("x") && compoundTag.contains("y") && compoundTag.contains("z") && (blockEntity = this.player.level.getBlockEntity(blockPos = new BlockPos(compoundTag.getInt("x"), compoundTag.getInt("y"), compoundTag.getInt("z")))) != null) {
                CompoundTag compoundTag2 = blockEntity.save(new CompoundTag());
                compoundTag2.remove("x");
                compoundTag2.remove("y");
                compoundTag2.remove("z");
                itemStack.addTagElement("BlockEntityTag", (Tag)compoundTag2);
            }
            boolean bl2 = serverboundSetCreativeModeSlotPacket.getSlotNum() >= 1 && serverboundSetCreativeModeSlotPacket.getSlotNum() <= 45;
            boolean bl4 = bl3 = itemStack.isEmpty() || itemStack.getDamageValue() >= 0 && itemStack.getCount() <= 64 && !itemStack.isEmpty();
            if (bl2 && bl3) {
                if (itemStack.isEmpty()) {
                    this.player.inventoryMenu.setItem(serverboundSetCreativeModeSlotPacket.getSlotNum(), ItemStack.EMPTY);
                } else {
                    this.player.inventoryMenu.setItem(serverboundSetCreativeModeSlotPacket.getSlotNum(), itemStack);
                }
                this.player.inventoryMenu.setSynched((Player)this.player, true);
                this.player.inventoryMenu.broadcastChanges();
            } else if (bl && bl3 && this.dropSpamTickCount < 200) {
                this.dropSpamTickCount += 20;
                this.player.drop(itemStack, true);
            }
        }
    }

    public void handleContainerAck(ServerboundContainerAckPacket serverboundContainerAckPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundContainerAckPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        int i = this.player.containerMenu.containerId;
        if (i == serverboundContainerAckPacket.getContainerId() && this.expectedAcks.getOrDefault(i, (short)(serverboundContainerAckPacket.getUid() + 1)) == serverboundContainerAckPacket.getUid() && !this.player.containerMenu.isSynched((Player)this.player) && !this.player.isSpectator()) {
            this.player.containerMenu.setSynched((Player)this.player, true);
        }
    }

    public void handleSignUpdate(ServerboundSignUpdatePacket serverboundSignUpdatePacket) {
        List<String> list2 = Stream.of(serverboundSignUpdatePacket.getLines()).map(ChatFormatting::stripFormatting).collect(Collectors.toList());
        this.filterTextPacket(list2, (List<String> list) -> this.updateSignText(serverboundSignUpdatePacket, (List<String>)list));
    }

    private void updateSignText(ServerboundSignUpdatePacket serverboundSignUpdatePacket, List<String> list) {
        this.player.resetLastActionTime();
        ServerLevel serverLevel = this.player.getLevel();
        BlockPos blockPos = serverboundSignUpdatePacket.getPos();
        if (serverLevel.hasChunkAt(blockPos)) {
            BlockState blockState = serverLevel.getBlockState(blockPos);
            BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);
            if (!(blockEntity instanceof SignBlockEntity)) {
                return;
            }
            SignBlockEntity signBlockEntity = (SignBlockEntity)blockEntity;
            if (!signBlockEntity.isEditable() || signBlockEntity.getPlayerWhoMayEdit() != this.player) {
                LOGGER.warn("Player {} just tried to change non-editable sign", (Object)this.player.getName().getString());
                return;
            }
            for (int i = 0; i < list.size(); ++i) {
                signBlockEntity.setMessage(i, (Component)new TextComponent(list.get(i)));
            }
            signBlockEntity.setChanged();
            serverLevel.sendBlockUpdated(blockPos, blockState, blockState, 3);
        }
    }

    public void handleKeepAlive(ServerboundKeepAlivePacket serverboundKeepAlivePacket) {
        if (this.keepAlivePending && serverboundKeepAlivePacket.getId() == this.keepAliveChallenge) {
            int i = (int)(Util.getMillis() - this.keepAliveTime);
            this.player.latency = (this.player.latency * 3 + i) / 4;
            this.keepAlivePending = false;
        } else if (!this.isSingleplayerOwner()) {
            this.disconnect((Component)new TranslatableComponent("disconnect.timeout"));
        }
    }

    public void handlePlayerAbilities(ServerboundPlayerAbilitiesPacket serverboundPlayerAbilitiesPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundPlayerAbilitiesPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        this.player.abilities.flying = serverboundPlayerAbilitiesPacket.isFlying() && this.player.abilities.mayfly;
    }

    public void handleClientInformation(ServerboundClientInformationPacket serverboundClientInformationPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundClientInformationPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        this.player.updateOptions(serverboundClientInformationPacket);
    }

    public void handleCustomPayload(ServerboundCustomPayloadPacket serverboundCustomPayloadPacket) {
    }

    public void handleChangeDifficulty(ServerboundChangeDifficultyPacket serverboundChangeDifficultyPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundChangeDifficultyPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        if (!this.player.hasPermissions(2) && !this.isSingleplayerOwner()) {
            return;
        }
        this.server.setDifficulty(serverboundChangeDifficultyPacket.getDifficulty(), false);
    }

    public void handleLockDifficulty(ServerboundLockDifficultyPacket serverboundLockDifficultyPacket) {
        PacketUtils.ensureRunningOnSameThread((Packet)serverboundLockDifficultyPacket, (PacketListener)this, (ServerLevel)this.player.getLevel());
        if (!this.player.hasPermissions(2) && !this.isSingleplayerOwner()) {
            return;
        }
        this.server.setDifficultyLocked(serverboundLockDifficultyPacket.isLocked());
    }
}
