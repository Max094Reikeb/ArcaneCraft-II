package net.reikeb.arcanecraft.commands;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.commands.CommandSourceStack;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.reikeb.arcanecraft.ArcaneCraft;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandRegistration {

    @SubscribeEvent
    public static void onRegisterCommandEvent(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> commandDispatcher = event.getDispatcher();
        ManaCommand.register(commandDispatcher);
    }
}
