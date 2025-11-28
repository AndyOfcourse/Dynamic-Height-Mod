/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.ParseResults
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.suggestion.SuggestionProvider
 *  com.mojang.brigadier.tree.CommandNode
 *  com.mojang.brigadier.tree.RootCommandNode
 *  net.minecraft.ChatFormatting
 *  net.minecraft.SharedConstants
 *  net.minecraft.Util
 *  net.minecraft.commands.CommandRuntimeException
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands$CommandSelection
 *  net.minecraft.commands.Commands$ParseFunction
 *  net.minecraft.commands.SharedSuggestionProvider
 *  net.minecraft.commands.synchronization.ArgumentTypes
 *  net.minecraft.commands.synchronization.SuggestionProviders
 *  net.minecraft.gametest.framework.TestCommand
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$Action
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.ComponentUtils
 *  net.minecraft.network.chat.HoverEvent
 *  net.minecraft.network.chat.HoverEvent$Action
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.network.chat.TextComponent
 *  net.minecraft.network.chat.TranslatableComponent
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundCommandsPacket
 *  net.minecraft.server.commands.AdvancementCommands
 *  net.minecraft.server.commands.AttributeCommand
 *  net.minecraft.server.commands.BanIpCommands
 *  net.minecraft.server.commands.BanListCommands
 *  net.minecraft.server.commands.BanPlayerCommands
 *  net.minecraft.server.commands.BossBarCommands
 *  net.minecraft.server.commands.ClearInventoryCommands
 *  net.minecraft.server.commands.CloneCommands
 *  net.minecraft.server.commands.DataPackCommand
 *  net.minecraft.server.commands.DeOpCommands
 *  net.minecraft.server.commands.DebugCommand
 *  net.minecraft.server.commands.DefaultGameModeCommands
 *  net.minecraft.server.commands.DifficultyCommand
 *  net.minecraft.server.commands.EffectCommands
 *  net.minecraft.server.commands.EmoteCommands
 *  net.minecraft.server.commands.EnchantCommand
 *  net.minecraft.server.commands.ExecuteCommand
 *  net.minecraft.server.commands.ExperienceCommand
 *  net.minecraft.server.commands.FillCommand
 *  net.minecraft.server.commands.ForceLoadCommand
 *  net.minecraft.server.commands.FunctionCommand
 *  net.minecraft.server.commands.GameModeCommand
 *  net.minecraft.server.commands.GameRuleCommand
 *  net.minecraft.server.commands.GiveCommand
 *  net.minecraft.server.commands.HelpCommand
 *  net.minecraft.server.commands.KickCommand
 *  net.minecraft.server.commands.KillCommand
 *  net.minecraft.server.commands.ListPlayersCommand
 *  net.minecraft.server.commands.LocateBiomeCommand
 *  net.minecraft.server.commands.LocateCommand
 *  net.minecraft.server.commands.LootCommand
 *  net.minecraft.server.commands.MsgCommand
 *  net.minecraft.server.commands.OpCommand
 *  net.minecraft.server.commands.PardonCommand
 *  net.minecraft.server.commands.PardonIpCommand
 *  net.minecraft.server.commands.ParticleCommand
 *  net.minecraft.server.commands.PlaySoundCommand
 *  net.minecraft.server.commands.PublishCommand
 *  net.minecraft.server.commands.RecipeCommand
 *  net.minecraft.server.commands.ReloadCommand
 *  net.minecraft.server.commands.ReplaceItemCommand
 *  net.minecraft.server.commands.SaveAllCommand
 *  net.minecraft.server.commands.SaveOffCommand
 *  net.minecraft.server.commands.SaveOnCommand
 *  net.minecraft.server.commands.SayCommand
 *  net.minecraft.server.commands.ScheduleCommand
 *  net.minecraft.server.commands.ScoreboardCommand
 *  net.minecraft.server.commands.SeedCommand
 *  net.minecraft.server.commands.SetBlockCommand
 *  net.minecraft.server.commands.SetPlayerIdleTimeoutCommand
 *  net.minecraft.server.commands.SetSpawnCommand
 *  net.minecraft.server.commands.SetWorldSpawnCommand
 *  net.minecraft.server.commands.SpectateCommand
 *  net.minecraft.server.commands.SpreadPlayersCommand
 *  net.minecraft.server.commands.StopCommand
 *  net.minecraft.server.commands.StopSoundCommand
 *  net.minecraft.server.commands.SummonCommand
 *  net.minecraft.server.commands.TagCommand
 *  net.minecraft.server.commands.TeamCommand
 *  net.minecraft.server.commands.TeamMsgCommand
 *  net.minecraft.server.commands.TeleportCommand
 *  net.minecraft.server.commands.TellRawCommand
 *  net.minecraft.server.commands.TimeCommand
 *  net.minecraft.server.commands.TitleCommand
 *  net.minecraft.server.commands.TriggerCommand
 *  net.minecraft.server.commands.WeatherCommand
 *  net.minecraft.server.commands.WhitelistCommand
 *  net.minecraft.server.commands.WorldBorderCommand
 *  net.minecraft.server.commands.data.DataCommands
 *  net.minecraft.server.level.ServerPlayer
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.commands;

import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.gametest.framework.TestCommand;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
import net.minecraft.server.commands.AdvancementCommands;
import net.minecraft.server.commands.AttributeCommand;
import net.minecraft.server.commands.BanIpCommands;
import net.minecraft.server.commands.BanListCommands;
import net.minecraft.server.commands.BanPlayerCommands;
import net.minecraft.server.commands.BossBarCommands;
import net.minecraft.server.commands.ClearInventoryCommands;
import net.minecraft.server.commands.CloneCommands;
import net.minecraft.server.commands.DataPackCommand;
import net.minecraft.server.commands.DeOpCommands;
import net.minecraft.server.commands.DebugCommand;
import net.minecraft.server.commands.DefaultGameModeCommands;
import net.minecraft.server.commands.DifficultyCommand;
import net.minecraft.server.commands.EffectCommands;
import net.minecraft.server.commands.EmoteCommands;
import net.minecraft.server.commands.EnchantCommand;
import net.minecraft.server.commands.ExecuteCommand;
import net.minecraft.server.commands.ExperienceCommand;
import net.minecraft.server.commands.FillCommand;
import net.minecraft.server.commands.ForceLoadCommand;
import net.minecraft.server.commands.FunctionCommand;
import net.minecraft.server.commands.GameModeCommand;
import net.minecraft.server.commands.GameRuleCommand;
import net.minecraft.server.commands.GiveCommand;
import net.minecraft.server.commands.HelpCommand;
import net.minecraft.server.commands.KickCommand;
import net.minecraft.server.commands.KillCommand;
import net.minecraft.server.commands.ListPlayersCommand;
import net.minecraft.server.commands.LocateBiomeCommand;
import net.minecraft.server.commands.LocateCommand;
import net.minecraft.server.commands.LootCommand;
import net.minecraft.server.commands.MsgCommand;
import net.minecraft.server.commands.OpCommand;
import net.minecraft.server.commands.PardonCommand;
import net.minecraft.server.commands.PardonIpCommand;
import net.minecraft.server.commands.ParticleCommand;
import net.minecraft.server.commands.PlaySoundCommand;
import net.minecraft.server.commands.PublishCommand;
import net.minecraft.server.commands.RecipeCommand;
import net.minecraft.server.commands.ReloadCommand;
import net.minecraft.server.commands.ReplaceItemCommand;
import net.minecraft.server.commands.SaveAllCommand;
import net.minecraft.server.commands.SaveOffCommand;
import net.minecraft.server.commands.SaveOnCommand;
import net.minecraft.server.commands.SayCommand;
import net.minecraft.server.commands.ScheduleCommand;
import net.minecraft.server.commands.ScoreboardCommand;
import net.minecraft.server.commands.SeedCommand;
import net.minecraft.server.commands.SetBlockCommand;
import net.minecraft.server.commands.SetPlayerIdleTimeoutCommand;
import net.minecraft.server.commands.SetSpawnCommand;
import net.minecraft.server.commands.SetWorldSpawnCommand;
import net.minecraft.server.commands.SpectateCommand;
import net.minecraft.server.commands.SpreadPlayersCommand;
import net.minecraft.server.commands.StopCommand;
import net.minecraft.server.commands.StopSoundCommand;
import net.minecraft.server.commands.SummonCommand;
import net.minecraft.server.commands.TagCommand;
import net.minecraft.server.commands.TeamCommand;
import net.minecraft.server.commands.TeamMsgCommand;
import net.minecraft.server.commands.TeleportCommand;
import net.minecraft.server.commands.TellRawCommand;
import net.minecraft.server.commands.TimeCommand;
import net.minecraft.server.commands.TitleCommand;
import net.minecraft.server.commands.TriggerCommand;
import net.minecraft.server.commands.WeatherCommand;
import net.minecraft.server.commands.WhitelistCommand;
import net.minecraft.server.commands.WorldBorderCommand;
import net.minecraft.server.commands.data.DataCommands;
import net.minecraft.server.level.ServerPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class Commands {
    private static final Logger LOGGER = LogManager.getLogger();
    private final CommandDispatcher<CommandSourceStack> dispatcher = new CommandDispatcher();

    public Commands(CommandSelection commandSelection) {
        AdvancementCommands.register(this.dispatcher);
        AttributeCommand.register(this.dispatcher);
        ExecuteCommand.register(this.dispatcher);
        BossBarCommands.register(this.dispatcher);
        ClearInventoryCommands.register(this.dispatcher);
        CloneCommands.register(this.dispatcher);
        DataCommands.register(this.dispatcher);
        DataPackCommand.register(this.dispatcher);
        DebugCommand.register(this.dispatcher);
        DefaultGameModeCommands.register(this.dispatcher);
        DifficultyCommand.register(this.dispatcher);
        EffectCommands.register(this.dispatcher);
        EmoteCommands.register(this.dispatcher);
        EnchantCommand.register(this.dispatcher);
        ExperienceCommand.register(this.dispatcher);
        FillCommand.register(this.dispatcher);
        ForceLoadCommand.register(this.dispatcher);
        FunctionCommand.register(this.dispatcher);
        GameModeCommand.register(this.dispatcher);
        GameRuleCommand.register(this.dispatcher);
        GiveCommand.register(this.dispatcher);
        HelpCommand.register(this.dispatcher);
        KickCommand.register(this.dispatcher);
        KillCommand.register(this.dispatcher);
        ListPlayersCommand.register(this.dispatcher);
        LocateCommand.register(this.dispatcher);
        LocateBiomeCommand.register(this.dispatcher);
        LootCommand.register(this.dispatcher);
        MsgCommand.register(this.dispatcher);
        ParticleCommand.register(this.dispatcher);
        PlaySoundCommand.register(this.dispatcher);
        ReloadCommand.register(this.dispatcher);
        RecipeCommand.register(this.dispatcher);
        ReplaceItemCommand.register(this.dispatcher);
        SayCommand.register(this.dispatcher);
        ScheduleCommand.register(this.dispatcher);
        ScoreboardCommand.register(this.dispatcher);
        SeedCommand.register(this.dispatcher, (commandSelection != CommandSelection.INTEGRATED ? 1 : 0) != 0);
        SetBlockCommand.register(this.dispatcher);
        SetSpawnCommand.register(this.dispatcher);
        SetWorldSpawnCommand.register(this.dispatcher);
        SpectateCommand.register(this.dispatcher);
        SpreadPlayersCommand.register(this.dispatcher);
        StopSoundCommand.register(this.dispatcher);
        SummonCommand.register(this.dispatcher);
        TagCommand.register(this.dispatcher);
        TeamCommand.register(this.dispatcher);
        TeamMsgCommand.register(this.dispatcher);
        TeleportCommand.register(this.dispatcher);
        TellRawCommand.register(this.dispatcher);
        TimeCommand.register(this.dispatcher);
        TitleCommand.register(this.dispatcher);
        TriggerCommand.register(this.dispatcher);
        WeatherCommand.register(this.dispatcher);
        WorldBorderCommand.register(this.dispatcher);
        if (SharedConstants.IS_RUNNING_IN_IDE) {
            TestCommand.register(this.dispatcher);
        }
        if (CommandSelection.method_29576((CommandSelection)commandSelection)) {
            BanIpCommands.register(this.dispatcher);
            BanListCommands.register(this.dispatcher);
            BanPlayerCommands.register(this.dispatcher);
            DeOpCommands.register(this.dispatcher);
            OpCommand.register(this.dispatcher);
            PardonCommand.register(this.dispatcher);
            PardonIpCommand.register(this.dispatcher);
            SaveAllCommand.register(this.dispatcher);
            SaveOffCommand.register(this.dispatcher);
            SaveOnCommand.register(this.dispatcher);
            SetPlayerIdleTimeoutCommand.register(this.dispatcher);
            StopCommand.register(this.dispatcher);
            WhitelistCommand.register(this.dispatcher);
        }
        if (CommandSelection.method_29577((CommandSelection)commandSelection)) {
            PublishCommand.register(this.dispatcher);
        }
        this.dispatcher.findAmbiguities((commandNode, commandNode2, commandNode3, collection) -> LOGGER.warn("Ambiguity between arguments {} and {} with inputs: {}", (Object)this.dispatcher.getPath(commandNode2), (Object)this.dispatcher.getPath(commandNode3), (Object)collection));
        this.dispatcher.setConsumer((commandContext, bl, i) -> ((CommandSourceStack)commandContext.getSource()).onCommandComplete(commandContext, bl, i));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int performCommand(CommandSourceStack commandSourceStack, String string) {
        StringReader stringReader = new StringReader(string);
        if (stringReader.canRead() && stringReader.peek() == '/') {
            stringReader.skip();
        }
        commandSourceStack.getServer().getProfiler().push(string);
        try {
            int n = this.dispatcher.execute(stringReader, (Object)commandSourceStack);
            return n;
        }
        catch (CommandRuntimeException commandRuntimeException) {
            commandSourceStack.sendFailure(commandRuntimeException.getComponent());
            int n = 0;
            return n;
        }
        catch (CommandSyntaxException commandSyntaxException) {
            int i;
            commandSourceStack.sendFailure(ComponentUtils.fromMessage((Message)commandSyntaxException.getRawMessage()));
            if (commandSyntaxException.getInput() != null && commandSyntaxException.getCursor() >= 0) {
                i = Math.min(commandSyntaxException.getInput().length(), commandSyntaxException.getCursor());
                MutableComponent mutableComponent = new TextComponent("").withStyle(ChatFormatting.GRAY).withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, string)));
                if (i > 10) {
                    mutableComponent.append("...");
                }
                mutableComponent.append(commandSyntaxException.getInput().substring(Math.max(0, i - 10), i));
                if (i < commandSyntaxException.getInput().length()) {
                    MutableComponent component = new TextComponent(commandSyntaxException.getInput().substring(i)).withStyle(new ChatFormatting[]{ChatFormatting.RED, ChatFormatting.UNDERLINE});
                    mutableComponent.append((Component)component);
                }
                mutableComponent.append((Component)new TranslatableComponent("command.context.here").withStyle(new ChatFormatting[]{ChatFormatting.RED, ChatFormatting.ITALIC}));
                commandSourceStack.sendFailure((Component)mutableComponent);
            }
            i = 0;
            return i;
        }
        catch (Exception exception) {
            TextComponent mutableComponent2 = new TextComponent(exception.getMessage() == null ? exception.getClass().getName() : exception.getMessage());
            if (LOGGER.isDebugEnabled()) {
                LOGGER.error("Command exception: {}", (Object)string, (Object)exception);
                StackTraceElement[] stackTraceElements = exception.getStackTrace();
                for (int j = 0; j < Math.min(stackTraceElements.length, 3); ++j) {
                    mutableComponent2.append("\n\n").append(stackTraceElements[j].getMethodName()).append("\n ").append(stackTraceElements[j].getFileName()).append(":").append(String.valueOf(stackTraceElements[j].getLineNumber()));
                }
            }
            commandSourceStack.sendFailure((Component)new TranslatableComponent("command.failed").withStyle(arg_0 -> Commands.method_9242((MutableComponent)mutableComponent2, arg_0)));
            if (SharedConstants.IS_RUNNING_IN_IDE) {
                commandSourceStack.sendFailure((Component)new TextComponent(Util.describeError((Throwable)exception)));
                LOGGER.error("'" + string + "' threw an exception", (Throwable)exception);
            }
            int n = 0;
            return n;
        }
        finally {
            commandSourceStack.getServer().getProfiler().pop();
        }
    }

    public void sendCommands(ServerPlayer serverPlayer) {
        HashMap map = Maps.newHashMap();
        RootCommandNode rootCommandNode = new RootCommandNode();
        map.put(this.dispatcher.getRoot(), rootCommandNode);
        this.fillUsableCommands((CommandNode<CommandSourceStack>)this.dispatcher.getRoot(), (CommandNode<SharedSuggestionProvider>)rootCommandNode, serverPlayer.createCommandSourceStack(), map);
        serverPlayer.connection.send((Packet)new ClientboundCommandsPacket(rootCommandNode));
    }

    private void fillUsableCommands(CommandNode<CommandSourceStack> commandNode, CommandNode<SharedSuggestionProvider> commandNode2, CommandSourceStack commandSourceStack, Map<CommandNode<CommandSourceStack>, CommandNode<SharedSuggestionProvider>> map) {
        for (CommandNode commandNode3 : commandNode.getChildren()) {
            RequiredArgumentBuilder requiredArgumentBuilder;
            if (!commandNode3.canUse((Object)commandSourceStack)) continue;
            ArgumentBuilder argumentBuilder = commandNode3.createBuilder();
            argumentBuilder.requires(sharedSuggestionProvider -> true);
            if (argumentBuilder.getCommand() != null) {
                argumentBuilder.executes(commandContext -> 0);
            }
            if (argumentBuilder instanceof RequiredArgumentBuilder && (requiredArgumentBuilder = (RequiredArgumentBuilder)argumentBuilder).getSuggestionsProvider() != null) {
                requiredArgumentBuilder.suggests(SuggestionProviders.safelySwap((SuggestionProvider)requiredArgumentBuilder.getSuggestionsProvider()));
            }
            if (argumentBuilder.getRedirect() != null) {
                argumentBuilder.redirect(map.get(argumentBuilder.getRedirect()));
            }
            CommandNode commandNode4 = argumentBuilder.build();
            map.put((CommandNode<CommandSourceStack>)commandNode3, (CommandNode<SharedSuggestionProvider>)commandNode4);
            commandNode2.addChild(commandNode4);
            if (commandNode3.getChildren().isEmpty()) continue;
            this.fillUsableCommands((CommandNode<CommandSourceStack>)commandNode3, (CommandNode<SharedSuggestionProvider>)commandNode4, commandSourceStack, map);
        }
    }

    public static LiteralArgumentBuilder<CommandSourceStack> literal(String string) {
        return LiteralArgumentBuilder.literal((String)string);
    }

    public static <T> RequiredArgumentBuilder<CommandSourceStack, T> argument(String string, ArgumentType<T> argumentType) {
        return RequiredArgumentBuilder.argument((String)string, argumentType);
    }

    public static Predicate<String> createValidator(ParseFunction parseFunction) {
        return string -> {
            try {
                parseFunction.parse(new StringReader(string));
                return true;
            }
            catch (CommandSyntaxException commandSyntaxException) {
                return false;
            }
        };
    }

    public CommandDispatcher<CommandSourceStack> getDispatcher() {
        return this.dispatcher;
    }

    @Nullable
    public static <S> CommandSyntaxException getParseException(ParseResults<S> parseResults) {
        if (!parseResults.getReader().canRead()) {
            return null;
        }
        if (parseResults.getExceptions().size() == 1) {
            return (CommandSyntaxException)((Object)parseResults.getExceptions().values().iterator().next());
        }
        if (parseResults.getContext().getRange().isEmpty()) {
            return CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().createWithContext(parseResults.getReader());
        }
        return CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().createWithContext(parseResults.getReader());
    }

    public static void validate() {
        RootCommandNode rootCommandNode = new Commands(CommandSelection.ALL).getDispatcher().getRoot();
        Set set = ArgumentTypes.findUsedArgumentTypes((CommandNode)rootCommandNode);
        Set set2 = set.stream().filter(argumentType -> !ArgumentTypes.isTypeRegistered((ArgumentType)argumentType)).collect(Collectors.toSet());
        if (!set2.isEmpty()) {
            LOGGER.warn("Missing type registration for following arguments:\n {}", (Object)set2.stream().map(argumentType -> "\t" + argumentType).collect(Collectors.joining(",\n")));
            throw new IllegalStateException("Unregistered argument types");
        }
    }

    private static /* synthetic */ Style method_9242(MutableComponent mutableComponent, Style style) {
        return style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (Object)mutableComponent));
    }
}
