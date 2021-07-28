package net.reikeb.arcanecraft.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.*;

import net.minecraft.command.*;
import net.minecraft.command.arguments.GameProfileArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

import net.minecraftforge.fml.network.PacketDistributor;

import net.reikeb.arcanecraft.capabilities.ManaManager;
import net.reikeb.arcanecraft.network.NetworkManager;
import net.reikeb.arcanecraft.network.packets.MaxManaPacket;

import java.util.*;

public class ManaCommand {

    private static final DynamicCommandExceptionType ERROR_NEGATIVE_MANA = new DynamicCommandExceptionType((error) -> {
        return new TranslationTextComponent("command.arcanecraft.negative_mana", error);
    });

    private static final DynamicCommandExceptionType ERROR_NEGATIVE_MAX_MANA = new DynamicCommandExceptionType((error) -> {
        return new TranslationTextComponent("command.arcanecraft.negative_max_mana", error);
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
                });

                serverPlayerEntity.getPersistentData().putInt("Mana", count);
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
                    cap.setMaxMana(count);
                    NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                            serverPlayerEntity), new MaxManaPacket(count));
                });
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

            int totalMana = serverPlayerEntity.getPersistentData().getInt("Mana") + count;

            if (totalMana < 0) {
                throw ERROR_NEGATIVE_MANA.create(totalMana);
            }

            serverPlayerEntity.getCapability(ManaManager.MANA_CAPABILITY, null).ifPresent(cap -> {
                if (totalMana > cap.getMaxMana()) {
                    cap.setMaxMana(totalMana);
                    NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                            serverPlayerEntity), new MaxManaPacket(totalMana));
                }
            });

            serverPlayerEntity.getPersistentData().putInt("Mana", totalMana);
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

                cap.setMaxMana(totalMaxMana);
                NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                        serverPlayerEntity), new MaxManaPacket((int) totalMaxMana));
            });
        }

        return count;
    }
}
