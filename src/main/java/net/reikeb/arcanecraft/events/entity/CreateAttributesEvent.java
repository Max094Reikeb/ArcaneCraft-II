package net.reikeb.arcanecraft.events.entity;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.entities.FireSplashEntity;
import net.reikeb.arcanecraft.init.EntityInit;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreateAttributesEvent {

    @SubscribeEvent
    public static void createAttributes(EntityAttributeCreationEvent event) {
        FireSplashEntity.createAttributes();
        event.put(EntityInit.FIRE_SPLASH.get(), FireSplashEntity.createAttributes().build());
    }
}