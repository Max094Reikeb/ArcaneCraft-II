package net.reikeb.arcanecraft.events.entity;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.capabilities.ManaStorage;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID)
public class EntityClonedEvent {

    @SubscribeEvent
    public static void onEntityCloned(PlayerEvent.Clone event) {
        if (event == null) return;

        if (event.getPlayer().level.isClientSide || event.getOriginal().level.isClientSide) return;

        event.getOriginal().reviveCaps();
        ManaStorage.get(event.getOriginal()).ifPresent(dataOriginal -> {
            ManaStorage.get(event.getPlayer()).ifPresent(dataNew -> {
                dataNew.deserializeNBT(dataOriginal.serializeNBT());
            });
        });
        event.getOriginal().invalidateCaps();
    }
}
