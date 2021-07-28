package net.reikeb.arcanecraft.events.entity;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.capabilities.ManaManager;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID)
public class EntityClonedEvent {

    @SubscribeEvent
    public static void onEntityCloned(PlayerEvent.Clone event) {
        if (event == null) return;

        event.getOriginal().getCapability(ManaManager.MANA_CAPABILITY).ifPresent(oldStore -> {
            event.getPlayer().getCapability(ManaManager.MANA_CAPABILITY).ifPresent(newStore -> {
                newStore.setMaxMana(oldStore.getMaxMana());
            });
        });
    }
}
