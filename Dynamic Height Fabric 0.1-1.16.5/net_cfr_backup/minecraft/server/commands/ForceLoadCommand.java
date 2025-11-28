/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.commands.arguments.coordinates.BlockPosArgument
 *  net.minecraft.commands.arguments.coordinates.ColumnPosArgument
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.TranslatableComponent
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.server.level.ColumnPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.ChunkPos
 */
package net.minecraft.server.commands;

import com.google.common.base.Joiner;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.ColumnPosArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ColumnPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;

public class ForceLoadCommand {
    private static final Dynamic2CommandExceptionType ERROR_TOO_MANY_CHUNKS = new Dynamic2CommandExceptionType((object, object2) -> new TranslatableComponent("commands.forceload.toobig", new Object[]{object, object2}));
    private static final Dynamic2CommandExceptionType ERROR_NOT_TICKING = new Dynamic2CommandExceptionType((object, object2) -> new TranslatableComponent("commands.forceload.query.failure", new Object[]{object, object2}));
    private static final SimpleCommandExceptionType ERROR_ALL_ADDED = new SimpleCommandExceptionType((Message)new TranslatableComponent("commands.forceload.added.failure"));
    private static final SimpleCommandExceptionType ERROR_NONE_REMOVED = new SimpleCommandExceptionType((Message)new TranslatableComponent("commands.forceload.removed.failure"));

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal((String)"forceload").requires(commandSourceStack -> commandSourceStack.hasPermission(2))).then(Commands.literal((String)"add").then(((RequiredArgumentBuilder)Commands.argument((String)"from", (ArgumentType)ColumnPosArgument.columnPos()).executes(commandContext -> ForceLoadCommand.changeForceLoad((CommandSourceStack)commandContext.getSource(), ColumnPosArgument.getColumnPos((CommandContext)commandContext, (String)"from"), ColumnPosArgument.getColumnPos((CommandContext)commandContext, (String)"from"), true))).then(Commands.argument((String)"to", (ArgumentType)ColumnPosArgument.columnPos()).executes(commandContext -> ForceLoadCommand.changeForceLoad((CommandSourceStack)commandContext.getSource(), ColumnPosArgument.getColumnPos((CommandContext)commandContext, (String)"from"), ColumnPosArgument.getColumnPos((CommandContext)commandContext, (String)"to"), true)))))).then(((LiteralArgumentBuilder)Commands.literal((String)"remove").then(((RequiredArgumentBuilder)Commands.argument((String)"from", (ArgumentType)ColumnPosArgument.columnPos()).executes(commandContext -> ForceLoadCommand.changeForceLoad((CommandSourceStack)commandContext.getSource(), ColumnPosArgument.getColumnPos((CommandContext)commandContext, (String)"from"), ColumnPosArgument.getColumnPos((CommandContext)commandContext, (String)"from"), false))).then(Commands.argument((String)"to", (ArgumentType)ColumnPosArgument.columnPos()).executes(commandContext -> ForceLoadCommand.changeForceLoad((CommandSourceStack)commandContext.getSource(), ColumnPosArgument.getColumnPos((CommandContext)commandContext, (String)"from"), ColumnPosArgument.getColumnPos((CommandContext)commandContext, (String)"to"), false))))).then(Commands.literal((String)"all").executes(commandContext -> ForceLoadCommand.removeAll((CommandSourceStack)commandContext.getSource()))))).then(((LiteralArgumentBuilder)Commands.literal((String)"query").executes(commandContext -> ForceLoadCommand.listForceLoad((CommandSourceStack)commandContext.getSource()))).then(Commands.argument((String)"pos", (ArgumentType)ColumnPosArgument.columnPos()).executes(commandContext -> ForceLoadCommand.queryForceLoad((CommandSourceStack)commandContext.getSource(), ColumnPosArgument.getColumnPos((CommandContext)commandContext, (String)"pos"))))));
    }

    private static int queryForceLoad(CommandSourceStack commandSourceStack, ColumnPos columnPos) throws CommandSyntaxException {
        ChunkPos chunkPos = new ChunkPos(columnPos.x >> 4, columnPos.z >> 4);
        ServerLevel serverLevel = commandSourceStack.getLevel();
        ResourceKey resourceKey = serverLevel.dimension();
        boolean bl = serverLevel.getForcedChunks().contains(chunkPos.toLong());
        if (bl) {
            commandSourceStack.sendSuccess((Component)new TranslatableComponent("commands.forceload.query.success", new Object[]{chunkPos, resourceKey.location()}), false);
            return 1;
        }
        throw ERROR_NOT_TICKING.create((Object)chunkPos, (Object)resourceKey.location());
    }

    private static int listForceLoad(CommandSourceStack commandSourceStack) {
        ServerLevel serverLevel = commandSourceStack.getLevel();
        ResourceKey resourceKey = serverLevel.dimension();
        LongSet longSet = serverLevel.getForcedChunks();
        int i = longSet.size();
        if (i > 0) {
            String string = Joiner.on((String)", ").join(longSet.stream().sorted().map(ChunkPos::new).map(ChunkPos::toString).iterator());
            if (i == 1) {
                commandSourceStack.sendSuccess((Component)new TranslatableComponent("commands.forceload.list.single", new Object[]{resourceKey.location(), string}), false);
            } else {
                commandSourceStack.sendSuccess((Component)new TranslatableComponent("commands.forceload.list.multiple", new Object[]{i, resourceKey.location(), string}), false);
            }
        } else {
            commandSourceStack.sendFailure((Component)new TranslatableComponent("commands.forceload.added.none", new Object[]{resourceKey.location()}));
        }
        return i;
    }

    private static int removeAll(CommandSourceStack commandSourceStack) {
        ServerLevel serverLevel = commandSourceStack.getLevel();
        ResourceKey resourceKey = serverLevel.dimension();
        LongSet longSet = serverLevel.getForcedChunks();
        longSet.forEach(l -> serverLevel.setChunkForced(ChunkPos.getX((long)l), ChunkPos.getZ((long)l), false));
        commandSourceStack.sendSuccess((Component)new TranslatableComponent("commands.forceload.removed.all", new Object[]{resourceKey.location()}), true);
        return 0;
    }

    private static int changeForceLoad(CommandSourceStack commandSourceStack, ColumnPos columnPos, ColumnPos columnPos2, boolean bl) throws CommandSyntaxException {
        int i = Math.min(columnPos.x, columnPos2.x);
        int j = Math.min(columnPos.z, columnPos2.z);
        int k = Math.max(columnPos.x, columnPos2.x);
        int l = Math.max(columnPos.z, columnPos2.z);
        if (i < -30000000 || j < -30000000 || k >= 30000000 || l >= 30000000) {
            throw BlockPosArgument.ERROR_OUT_OF_WORLD.create();
        }
        int o = k >> 4;
        int m = i >> 4;
        int p = l >> 4;
        int n = j >> 4;
        long q = ((long)(o - m) + 1L) * ((long)(p - n) + 1L);
        if (q > 256L) {
            throw ERROR_TOO_MANY_CHUNKS.create((Object)256, (Object)q);
        }
        ServerLevel serverLevel = commandSourceStack.getLevel();
        ResourceKey resourceKey = serverLevel.dimension();
        ChunkPos chunkPos = null;
        int r = 0;
        for (int s = m; s <= o; ++s) {
            for (int t = n; t <= p; ++t) {
                boolean bl2 = serverLevel.setChunkForced(s, t, bl);
                if (!bl2) continue;
                ++r;
                if (chunkPos != null) continue;
                chunkPos = new ChunkPos(s, t);
            }
        }
        if (r == 0) {
            throw (bl ? ERROR_ALL_ADDED : ERROR_NONE_REMOVED).create();
        }
        if (r == 1) {
            commandSourceStack.sendSuccess((Component)new TranslatableComponent("commands.forceload." + (bl ? "added" : "removed") + ".single", new Object[]{chunkPos, resourceKey.location()}), true);
        } else {
            ChunkPos chunkPos2 = new ChunkPos(m, n);
            ChunkPos chunkPos3 = new ChunkPos(o, p);
            commandSourceStack.sendSuccess((Component)new TranslatableComponent("commands.forceload." + (bl ? "added" : "removed") + ".multiple", new Object[]{r, resourceKey.location(), chunkPos2, chunkPos3}), true);
        }
        return r;
    }
}
