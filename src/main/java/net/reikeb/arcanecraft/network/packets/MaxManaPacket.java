package net.reikeb.arcanecraft.network.packets;

import net.minecraft.network.PacketBuffer;

import net.minecraftforge.fml.network.NetworkEvent;

import net.reikeb.arcanecraft.events.OverlayEvent;

import java.util.function.Supplier;

public class MaxManaPacket {

    private final int maxMana;

    public MaxManaPacket(int maxMana) {
        this.maxMana = maxMana;
    }

    public static MaxManaPacket decode(PacketBuffer buf) {
        return new MaxManaPacket(buf.readInt());
    }

    public void encode(PacketBuffer buf) {
        buf.writeInt(maxMana);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            OverlayEvent.manaValue = maxMana;
        });
        context.get().setPacketHandled(true);
    }
}
