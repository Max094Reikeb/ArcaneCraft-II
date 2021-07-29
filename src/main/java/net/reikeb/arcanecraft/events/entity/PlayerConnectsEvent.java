package net.reikeb.arcanecraft.events.entity;

import net.minecraft.entity.player.*;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.capabilities.ManaManager;
import net.reikeb.arcanecraft.network.NetworkManager;
import net.reikeb.arcanecraft.network.packets.*;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID)
public class PlayerConnectsEvent {

    @SubscribeEvent
    public static void playerConnects(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity playerEntity = event.getPlayer();

        playerEntity.getCapability(ManaManager.MANA_CAPABILITY, null).ifPresent(cap -> {
            NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                    (ServerPlayerEntity) playerEntity), new MaxManaPacket((int) cap.getMaxMana()));
            NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                    (ServerPlayerEntity) playerEntity), new CurrentManaPacket(cap.getMana()));
        });
    }
}
