package net.reikeb.arcanecraft.network;

import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.network.packets.*;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetworkManager {

    public static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ArcaneCraft.MODID, "main"), () -> NetworkManager.PROTOCOL_VERSION,
            NetworkManager.PROTOCOL_VERSION::equals, NetworkManager.PROTOCOL_VERSION::equals);

    @SuppressWarnings("UnusedAssignment")
    @SubscribeEvent
    public static void registerNetworkStuff(FMLCommonSetupEvent event) {
        int index = 0;
        INSTANCE.registerMessage(index++, WooMagicPacket.class, WooMagicPacket::encode, WooMagicPacket::decode, WooMagicPacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, MagicSummoningPacket.class, MagicSummoningPacket::encode, MagicSummoningPacket::decode, MagicSummoningPacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, ScrollWritingPacket.class, ScrollWritingPacket::encode, ScrollWritingPacket::decode, ScrollWritingPacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, WandWorkbenchingPacket.class, WandWorkbenchingPacket::encode, WandWorkbenchingPacket::decode, WandWorkbenchingPacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, MaxManaPacket.class, MaxManaPacket::encode, MaxManaPacket::decode, MaxManaPacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, CurrentManaPacket.class, CurrentManaPacket::encode, CurrentManaPacket::decode, CurrentManaPacket::whenThisPacketIsReceived);
    }
}
