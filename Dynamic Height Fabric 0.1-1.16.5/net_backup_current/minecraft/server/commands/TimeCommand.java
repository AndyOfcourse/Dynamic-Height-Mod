package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.TimeArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;

public class TimeCommand {
   public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
      commandDispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("time")
                     .requires(commandSourceStack -> commandSourceStack.hasPermission(2)))
                  .then(
                     ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("set")
                                    .then(Commands.literal("day").executes(commandContext -> setTime((CommandSourceStack)commandContext.getSource(), 1000))))
                                 .then(Commands.literal("noon").executes(commandContext -> setTime((CommandSourceStack)commandContext.getSource(), 6000))))
                              .then(Commands.literal("night").executes(commandContext -> setTime((CommandSourceStack)commandContext.getSource(), 13000))))
                           .then(Commands.literal("midnight").executes(commandContext -> setTime((CommandSourceStack)commandContext.getSource(), 18000))))
                        .then(
                           Commands.argument("time", TimeArgument.time())
                              .executes(
                                 commandContext -> setTime(
                                       (CommandSourceStack)commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "time")
                                    )
                              )
                        )
                  ))
               .then(
                  Commands.literal("add")
                     .then(
                        Commands.argument("time", TimeArgument.time())
                           .executes(
                              commandContext -> addTime((CommandSourceStack)commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "time"))
                           )
                     )
               ))
            .then(
               ((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("query")
                        .then(
                           Commands.literal("daytime")
                              .executes(
                                 commandContext -> queryTime(
                                       (CommandSourceStack)commandContext.getSource(), getDayTime(((CommandSourceStack)commandContext.getSource()).getLevel())
                                    )
                              )
                        ))
                     .then(
                        Commands.literal("gametime")
                           .executes(
                              commandContext -> queryTime(
                                    (CommandSourceStack)commandContext.getSource(),
                                    (int)(((CommandSourceStack)commandContext.getSource()).getLevel().getGameTime() % 2147483647L)
                                 )
                           )
                     ))
                  .then(
                     Commands.literal("day")
                        .executes(
                           commandContext -> queryTime(
                                 (CommandSourceStack)commandContext.getSource(),
                                 (int)(((CommandSourceStack)commandContext.getSource()).getLevel().getDayTime() / 24000L % 2147483647L)
                              )
                        )
                  )
            )
      );
   }

   private static int getDayTime(ServerLevel serverLevel) {
      return (int)(serverLevel.getDayTime() % 24000L);
   }

   private static int queryTime(CommandSourceStack commandSourceStack, int i) {
      commandSourceStack.sendSuccess(new TranslatableComponent("commands.time.query", i), false);
      return i;
   }

   public static int setTime(CommandSourceStack commandSourceStack, int i) {
      for(ServerLevel serverLevel : commandSourceStack.getServer().getAllLevels()) {
         serverLevel.setDayTime((long)i);
      }

      commandSourceStack.sendSuccess(new TranslatableComponent("commands.time.set", i), true);
      return getDayTime(commandSourceStack.getLevel());
   }

   public static int addTime(CommandSourceStack commandSourceStack, int i) {
      for(ServerLevel serverLevel : commandSourceStack.getServer().getAllLevels()) {
         serverLevel.setDayTime(serverLevel.getDayTime() + (long)i);
      }

      int j = getDayTime(commandSourceStack.getLevel());
      commandSourceStack.sendSuccess(new TranslatableComponent("commands.time.set", j), true);
      return j;
   }
}
