package net.reikeb.arcanecraft.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.reikeb.arcanecraft.capabilities.CapabilityMana;
import net.reikeb.arcanecraft.capabilities.ManaStorage;
import net.reikeb.arcanecraft.misc.vm.Mana;

import java.util.Collection;

public class ManaCommand {

    private static final DynamicCommandExceptionType ERROR_NEGATIVE_MANA = new DynamicCommandExceptionType((error)
            -> new TranslatableComponent("command.arcanecraft.mana.negative", error));

    private static final DynamicCommandExceptionType ERROR_NEGATIVE_MAX_MANA = new DynamicCommandExceptionType((error)
            -> new TranslatableComponent("command.arcanecraft.mana.negative_max", error));

    private static final DynamicCommandExceptionType ERROR_MAX_INFERIOR = new DynamicCommandExceptionType((error)
            -> new TranslatableComponent("command.arcanecraft.mana.inferior_current", error));

    private static final DynamicCommandExceptionType ERROR_NEGATIVE_PROGRESS = new DynamicCommandExceptionType((error)
            -> new TranslatableComponent("command.arcanecraft.mana.negative_progress", error));

    private static final DynamicCommandExceptionType ERROR_HIGH_PROGRESS = new DynamicCommandExceptionType((error)
            -> new TranslatableComponent("command.arcanecraft.mana.high_progress", error));

    private static final DynamicCommandExceptionType ERROR_PLAYER = new DynamicCommandExceptionType((error)
            -> new TranslatableComponent("command.arcanecraft.null_player", error));

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(Commands.literal("mana").requires(commandSource
                        -> commandSource.hasPermission(2))
                .then(Commands.literal("set")
                        .then(Commands.literal("progress")
                                .then(Commands.argument("target", GameProfileArgument.gameProfile())
                                        .then(Commands.argument("count", FloatArgumentType.floatArg()).executes((command)
                                                -> setManaProgress(command.getSource(), GameProfileArgument.getGameProfiles(command, "target"), FloatArgumentType.getFloat(command, "count"))))))
                        .then(Commands.literal("current")
                                .then(Commands.argument("target", GameProfileArgument.gameProfile())
                                        .then(Commands.argument("count", IntegerArgumentType.integer()).executes((command)
                                                -> setCurrentMana(command.getSource(), GameProfileArgument.getGameProfiles(command, "target"), IntegerArgumentType.getInteger(command, "count"))))))
                        .then(Commands.literal("max")
                                .then(Commands.argument("target", GameProfileArgument.gameProfile())
                                        .then(Commands.argument("count", IntegerArgumentType.integer()).executes((command)
                                                -> setMaxMana(command.getSource(), GameProfileArgument.getGameProfiles(command, "target"), IntegerArgumentType.getInteger(command, "count")))))))
                .then(Commands.literal("add")
                        .then(Commands.literal("current")
                                .then(Commands.argument("target", GameProfileArgument.gameProfile())
                                        .then(Commands.argument("count", IntegerArgumentType.integer()).executes((command)
                                                -> addCurrentMana(command.getSource(), GameProfileArgument.getGameProfiles(command, "target"), IntegerArgumentType.getInteger(command, "count"))))))
                        .then(Commands.literal("max")
                                .then(Commands.argument("target", GameProfileArgument.gameProfile())
                                        .then(Commands.argument("count", IntegerArgumentType.integer()).executes((command)
                                                -> addMaxMana(command.getSource(), GameProfileArgument.getGameProfiles(command, "target"), IntegerArgumentType.getInteger(command, "count"))))
                                ))));
    }

    private static int setManaProgress(CommandSourceStack source, Collection<GameProfile> target, float count) throws CommandSyntaxException {
        if (count < 0.0F) {
            throw ERROR_NEGATIVE_PROGRESS.create(count);
        } else if (count > 1.0F) {
            throw ERROR_HIGH_PROGRESS.create(count);
        } else {
            for (GameProfile gameprofile : target) {
                ServerPlayer serverPlayerEntity = source.getServer().getPlayerList().getPlayer(gameprofile.getId());

                if (serverPlayerEntity == null) {
                    throw ERROR_PLAYER.create(gameprofile);
                }

                ManaStorage manaStorage = serverPlayerEntity.getCapability(CapabilityMana.MANA_CAPABILITY, null).orElseThrow(() ->
                        new IllegalStateException("Tried to get my capability but it wasn't there wtf"));
                Mana.setProgressMana(manaStorage, serverPlayerEntity, count);

                source.sendSuccess(new TranslatableComponent("command.arcanecraft.mana.progress_set", ComponentUtils.getDisplayName(gameprofile), count), true);
            }
        }

        return (int) count;
    }

    private static int setCurrentMana(CommandSourceStack source, Collection<GameProfile> target, int count) throws CommandSyntaxException {
        if (count < 0) {
            throw ERROR_NEGATIVE_MANA.create(count);
        } else {
            for (GameProfile gameprofile : target) {
                ServerPlayer serverPlayerEntity = source.getServer().getPlayerList().getPlayer(gameprofile.getId());

                if (serverPlayerEntity == null) {
                    throw ERROR_PLAYER.create(gameprofile);
                }

                ManaStorage manaStorage = serverPlayerEntity.getCapability(CapabilityMana.MANA_CAPABILITY, null).orElseThrow(() ->
                        new IllegalStateException("Tried to get my capability but it wasn't there wtf"));
                if (count > manaStorage.getMaxMana()) {
                    Mana.setMaxMana(manaStorage, serverPlayerEntity, count);
                }
                Mana.setCurrentMana(manaStorage, serverPlayerEntity, count);

                source.sendSuccess(new TranslatableComponent("command.arcanecraft.mana.current_set", ComponentUtils.getDisplayName(gameprofile), count), true);
            }
        }

        return count;
    }

    private static int setMaxMana(CommandSourceStack source, Collection<GameProfile> target, int count) throws CommandSyntaxException {
        if (count < 0) {
            throw ERROR_NEGATIVE_MAX_MANA.create(count);
        } else {
            for (GameProfile gameprofile : target) {
                ServerPlayer serverPlayerEntity = source.getServer().getPlayerList().getPlayer(gameprofile.getId());

                if (serverPlayerEntity == null) {
                    throw ERROR_PLAYER.create(gameprofile);
                }

                ManaStorage manaStorage = serverPlayerEntity.getCapability(CapabilityMana.MANA_CAPABILITY, null).orElseThrow(() ->
                        new IllegalStateException("Tried to get my capability but it wasn't there wtf"));
                if (manaStorage.getMana() > count) {
                    throw ERROR_MAX_INFERIOR.create(count);
                }
                Mana.setMaxMana(manaStorage, serverPlayerEntity, count);

                source.sendSuccess(new TranslatableComponent("command.arcanecraft.mana.max_set", ComponentUtils.getDisplayName(gameprofile), count), true);
            }
        }

        return count;
    }

    private static int addCurrentMana(CommandSourceStack source, Collection<GameProfile> target, int count) throws CommandSyntaxException {
        for (GameProfile gameprofile : target) {
            ServerPlayer serverPlayerEntity = source.getServer().getPlayerList().getPlayer(gameprofile.getId());

            if (serverPlayerEntity == null) {
                throw ERROR_PLAYER.create(gameprofile);
            }

            ManaStorage manaStorage = serverPlayerEntity.getCapability(CapabilityMana.MANA_CAPABILITY, null).orElseThrow(() ->
                    new IllegalStateException("Tried to get my capability but it wasn't there wtf"));
            if ((manaStorage.getMana() + count) < 0) {
                throw ERROR_NEGATIVE_MANA.create((manaStorage.getMana() + count));
            }
            if ((manaStorage.getMana() + count) > manaStorage.getMaxMana()) {
                Mana.setMaxMana(manaStorage, serverPlayerEntity, (manaStorage.getMana() + count));
            }
            Mana.addCurrentMana(manaStorage, serverPlayerEntity, count);

            source.sendSuccess(new TranslatableComponent("command.arcanecraft.mana.current_add", count, ComponentUtils.getDisplayName(gameprofile)), true);
        }

        return count;
    }

    private static int addMaxMana(CommandSourceStack source, Collection<GameProfile> target, int count) throws CommandSyntaxException {
        for (GameProfile gameprofile : target) {
            ServerPlayer serverPlayerEntity = source.getServer().getPlayerList().getPlayer(gameprofile.getId());

            if (serverPlayerEntity == null) {
                throw ERROR_PLAYER.create(gameprofile);
            }

            ManaStorage manaStorage = serverPlayerEntity.getCapability(CapabilityMana.MANA_CAPABILITY, null).orElseThrow(() ->
                    new IllegalStateException("Tried to get my capability but it wasn't there wtf"));
            if ((manaStorage.getMaxMana() + count) < 0) {
                throw ERROR_NEGATIVE_MAX_MANA.create(manaStorage.getMaxMana() + count);
            }
            if (manaStorage.getMana() > (manaStorage.getMaxMana() + count)) {
                throw ERROR_MAX_INFERIOR.create(manaStorage.getMaxMana() + count);
            }
            Mana.addMaxMana(manaStorage, serverPlayerEntity, count);

            source.sendSuccess(new TranslatableComponent("command.arcanecraft.mana.max_add", count, ComponentUtils.getDisplayName(gameprofile)), true);
        }

        return count;
    }
}