package net.reikeb.arcanecraft.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

import net.minecraftforge.fmllegacy.network.PacketDistributor;

import net.reikeb.arcanecraft.capabilities.CapabilityMana;
import net.reikeb.arcanecraft.network.NetworkManager;
import net.reikeb.arcanecraft.network.packets.CurrentManaPacket;
import net.reikeb.arcanecraft.network.packets.MaxManaPacket;

import java.util.Collection;

public class ManaCommand {

    private static final DynamicCommandExceptionType ERROR_NEGATIVE_MANA = new DynamicCommandExceptionType((error) -> {
        return new TranslatableComponent("command.arcanecraft.mana.negative", error);
    });

    private static final DynamicCommandExceptionType ERROR_NEGATIVE_MAX_MANA = new DynamicCommandExceptionType((error) -> {
        return new TranslatableComponent("command.arcanecraft.mana.negative_max", error);
    });

    private static final DynamicCommandExceptionType ERROR_MAX_INFERIOR = new DynamicCommandExceptionType((error) -> {
        return new TranslatableComponent("command.arcanecraft.mana.inferior_current", error);
    });

    private static final DynamicCommandExceptionType ERROR_PLAYER = new DynamicCommandExceptionType((error) -> {
        return new TranslatableComponent("command.arcanecraft.null_player", error);
    });

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
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

    private static int setCurrentMana(CommandSourceStack source, Collection<GameProfile> target, int count) throws CommandSyntaxException {
        if (count < 0) {
            throw ERROR_NEGATIVE_MANA.create(count);
        } else {
            for (GameProfile gameprofile : target) {
                ServerPlayer serverPlayerEntity = source.getServer().getPlayerList().getPlayer(gameprofile.getId());

                if (serverPlayerEntity == null) {
                    throw ERROR_PLAYER.create(gameprofile);
                }

                serverPlayerEntity.getCapability(CapabilityMana.MANA_CAPABILITY, null).ifPresent(cap -> {
                    if (count > cap.getMaxMana()) {
                        cap.setMaxMana(count);
                        NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                                serverPlayerEntity), new MaxManaPacket(count));
                    }

                    cap.setMana(count);
                    NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                            serverPlayerEntity), new CurrentManaPacket(count));
                });

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

                serverPlayerEntity.getCapability(CapabilityMana.MANA_CAPABILITY, null).ifPresent(cap -> {
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

            serverPlayerEntity.getCapability(CapabilityMana.MANA_CAPABILITY, null).ifPresent(cap -> {
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

            serverPlayerEntity.getCapability(CapabilityMana.MANA_CAPABILITY, null).ifPresent(cap -> {
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

            source.sendSuccess(new TranslatableComponent("command.arcanecraft.mana.max_add", count, ComponentUtils.getDisplayName(gameprofile)), true);
        }

        return count;
    }
}
