package net.reikeb.arcanecraft.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.*;

import net.minecraft.command.*;
import net.minecraft.command.arguments.GameProfileArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.*;

import net.minecraftforge.fml.network.PacketDistributor;

import net.reikeb.arcanecraft.capabilities.ManaManager;
import net.reikeb.arcanecraft.network.NetworkManager;
import net.reikeb.arcanecraft.network.packets.*;

import java.util.*;

public class ManaCommand {

    private static final DynamicCommandExceptionType ERROR_NEGATIVE_MANA = new DynamicCommandExceptionType((error) -> {
        return new TranslationTextComponent("command.arcanecraft.mana.negative", error);
    });

    private static final DynamicCommandExceptionType ERROR_NEGATIVE_MAX_MANA = new DynamicCommandExceptionType((error) -> {
        return new TranslationTextComponent("command.arcanecraft.mana.negative_max", error);
    });

    private static final DynamicCommandExceptionType ERROR_MAX_INFERIOR = new DynamicCommandExceptionType((error) -> {
        return new TranslationTextComponent("command.arcanecraft.mana.inferior_current", error);
    });

    private static final DynamicCommandExceptionType ERROR_PLAYER = new DynamicCommandExceptionType((error) -> {
        return new TranslationTextComponent("command.arcanecraft.null_player", error);
    });

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register(Commands.literal("mana").requires(commandSource -> {
            return commandSource.hasPermission(2);
        }).then(Commands.literal("set")
                .then(Commands.literal("current")
                        .then(Commands.argument("target", GameProfileArgument.gameProfile())
                                .then(Commands.argument("count", IntegerArgumentType.integer()).executes((command) -> {
                                    return setCurrentMana(command.getSource(), GameProfileArgument.getGameProfiles(command, "target"), IntegerArgumentType.getInteger(command, "count"));
                                }))))
                .then(Commands.literal("max")
                        .then(Commands.argument("target", GameProfileArgument.gameProfile())
                                .then(Commands.argument("count", IntegerArgumentType.integer()).executes((command) -> {
                                    return setMaxMana(command.getSource(), GameProfileArgument.getGameProfiles(command, "target"), IntegerArgumentType.getInteger(command, "count"));

                                }))))).then(Commands.literal("add")
                .then(Commands.literal("current")
                        .then(Commands.argument("target", GameProfileArgument.gameProfile())
                                .then(Commands.argument("count", IntegerArgumentType.integer()).executes((command) -> {
                                    return addCurrentMana(command.getSource(), GameProfileArgument.getGameProfiles(command, "target"), IntegerArgumentType.getInteger(command, "count"));
                                }))))
                .then(Commands.literal("max")
                        .then(Commands.argument("target", GameProfileArgument.gameProfile())
                                .then(Commands.argument("count", IntegerArgumentType.integer()).executes((command) -> {
                                    return addMaxMana(command.getSource(), GameProfileArgument.getGameProfiles(command, "target"), IntegerArgumentType.getInteger(command, "count"));
                                }))))));
    }

    private static int setCurrentMana(CommandSource source, Collection<GameProfile> target, int count) throws CommandSyntaxException {
        if (count < 0) {
            throw ERROR_NEGATIVE_MANA.create(count);
        } else {
            for (GameProfile gameprofile : target) {
                ServerPlayerEntity serverPlayerEntity = source.getServer().getPlayerList().getPlayer(gameprofile.getId());

                if (serverPlayerEntity == null) {
                    throw ERROR_PLAYER.create(gameprofile);
                }

                serverPlayerEntity.getCapability(ManaManager.MANA_CAPABILITY, null).ifPresent(cap -> {
                    if (count > cap.getMaxMana()) {
                        cap.setMaxMana(count);
                        NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                                serverPlayerEntity), new MaxManaPacket(count));
                    }

                    cap.setMana(count);
                    NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                            serverPlayerEntity), new CurrentManaPacket(count));
                });

                source.sendSuccess(new TranslationTextComponent("command.arcanecraft.mana.current_set", TextComponentUtils.getDisplayName(gameprofile), count), true);
            }
        }

        return count;
    }

    private static int setMaxMana(CommandSource source, Collection<GameProfile> target, int count) throws CommandSyntaxException {
        if (count < 0) {
            throw ERROR_NEGATIVE_MAX_MANA.create(count);
        } else {
            for (GameProfile gameprofile : target) {
                ServerPlayerEntity serverPlayerEntity = source.getServer().getPlayerList().getPlayer(gameprofile.getId());

                if (serverPlayerEntity == null) {
                    throw ERROR_PLAYER.create(gameprofile);
                }

                serverPlayerEntity.getCapability(ManaManager.MANA_CAPABILITY, null).ifPresent(cap -> {
                    if (cap.getMana() > count) {
                        try {
                            throw ERROR_MAX_INFERIOR.create(count);
                        } catch (CommandSyntaxException e) {
                            e.printStackTrace();
                        }
                    }

                    cap.setMaxMana(count);
                    NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                            serverPlayerEntity), new MaxManaPacket(count));
                });

                source.sendSuccess(new TranslationTextComponent("command.arcanecraft.mana.max_set", TextComponentUtils.getDisplayName(gameprofile), count), true);
            }
        }

        return count;
    }

    private static int addCurrentMana(CommandSource source, Collection<GameProfile> target, int count) throws CommandSyntaxException {
        for (GameProfile gameprofile : target) {
            ServerPlayerEntity serverPlayerEntity = source.getServer().getPlayerList().getPlayer(gameprofile.getId());

            if (serverPlayerEntity == null) {
                throw ERROR_PLAYER.create(gameprofile);
            }

            serverPlayerEntity.getCapability(ManaManager.MANA_CAPABILITY, null).ifPresent(cap -> {
                int totalMana = cap.getMana() + count;

                if (totalMana < 0) {
                    try {
                        throw ERROR_NEGATIVE_MANA.create(totalMana);
                    } catch (CommandSyntaxException e) {
                        e.printStackTrace();
                    }
                }

                if (totalMana > cap.getMaxMana()) {
                    cap.setMaxMana(totalMana);
                    NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                            serverPlayerEntity), new MaxManaPacket(totalMana));
                }

                cap.setMana(totalMana);
                NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                        serverPlayerEntity), new CurrentManaPacket(totalMana));
            });

            source.sendSuccess(new TranslationTextComponent("command.arcanecraft.mana.current_add", count, TextComponentUtils.getDisplayName(gameprofile)), true);
        }

        return count;
    }

    private static int addMaxMana(CommandSource source, Collection<GameProfile> target, int count) throws CommandSyntaxException {
        for (GameProfile gameprofile : target) {
            ServerPlayerEntity serverPlayerEntity = source.getServer().getPlayerList().getPlayer(gameprofile.getId());

            if (serverPlayerEntity == null) {
                throw ERROR_PLAYER.create(gameprofile);
            }

            serverPlayerEntity.getCapability(ManaManager.MANA_CAPABILITY, null).ifPresent(cap -> {
                double totalMaxMana = cap.getMaxMana() + count;

                if (totalMaxMana < 0) {
                    try {
                        throw ERROR_NEGATIVE_MAX_MANA.create(totalMaxMana);
                    } catch (CommandSyntaxException e) {
                        e.printStackTrace();
                    }
                }

                if (cap.getMana() > totalMaxMana) {
                    try {
                        throw ERROR_MAX_INFERIOR.create(totalMaxMana);
                    } catch (CommandSyntaxException e) {
                        e.printStackTrace();
                    }
                }

                cap.setMaxMana(totalMaxMana);
                NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                        serverPlayerEntity), new MaxManaPacket((int) totalMaxMana));
            });

            source.sendSuccess(new TranslationTextComponent("command.arcanecraft.mana.max_add", count, TextComponentUtils.getDisplayName(gameprofile)), true);
        }

        return count;
    }
}
