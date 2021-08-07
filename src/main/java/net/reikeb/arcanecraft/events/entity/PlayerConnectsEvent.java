package net.reikeb.arcanecraft.events.entity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.capabilities.CapabilityMana;
import net.reikeb.arcanecraft.capabilities.ManaStorage;
import net.reikeb.arcanecraft.network.NetworkManager;
import net.reikeb.arcanecraft.network.packets.CurrentManaPacket;
import net.reikeb.arcanecraft.network.packets.MaxManaPacket;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID)
public class PlayerConnectsEvent {

    @SubscribeEvent
    public static void playerConnects(PlayerEvent.PlayerLoggedInEvent event) {
        Player playerEntity = event.getPlayer();

        ManaStorage manaStorage = playerEntity.getCapability(CapabilityMana.MANA_CAPABILITY, null).orElseThrow(() ->
                new IllegalStateException("Tried to get my capability but it wasn't there wtf"));

        NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                (ServerPlayer) playerEntity), new MaxManaPacket((int) manaStorage.getMaxMana()));
        NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                (ServerPlayer) playerEntity), new CurrentManaPacket(manaStorage.getMana()));
    }
}
