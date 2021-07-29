package net.reikeb.arcanecraft.network.packets;

import net.minecraft.network.PacketBuffer;

import net.minecraftforge.fml.network.NetworkEvent;

import net.reikeb.arcanecraft.events.OverlayEvent;

import java.util.function.Supplier;

public class CurrentManaPacket {

    private final int currentMana;

    public CurrentManaPacket(int currentMana) {
        this.currentMana = currentMana;
    }

    public static CurrentManaPacket decode(PacketBuffer buf) {
        return new CurrentManaPacket(buf.readInt());
    }

    public void encode(PacketBuffer buf) {
        buf.writeInt(currentMana);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            OverlayEvent.currentManaValue = currentMana;
        });
        context.get().setPacketHandled(true);
    }
}
