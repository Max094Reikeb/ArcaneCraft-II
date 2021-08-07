package net.reikeb.arcanecraft.events.entity;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.capabilities.CapabilityMana;
import net.reikeb.arcanecraft.capabilities.ManaStorage;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID)
public class EntityClonedEvent {

    @SubscribeEvent
    public static void onEntityCloned(PlayerEvent.Clone event) {
        if (event == null) return;

        ManaStorage oldManaStorage = event.getOriginal().getCapability(CapabilityMana.MANA_CAPABILITY, null).orElseThrow(() ->
                new IllegalStateException("Tried to get my capability but it wasn't there wtf"));
        ManaStorage newManaStorage = event.getPlayer().getCapability(CapabilityMana.MANA_CAPABILITY, null).orElseThrow(() ->
                new IllegalStateException("Tried to get my capability but it wasn't there wtf"));

        newManaStorage.setMaxMana(oldManaStorage.getMaxMana());
        if (!event.isWasDeath()) {
            newManaStorage.setMana(oldManaStorage.getMana());
        }
    }
}
