package net.reikeb.arcanecraft.network.packets;

import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.fmllegacy.network.NetworkEvent;

import net.reikeb.arcanecraft.events.OverlayEvent;

import java.util.function.Supplier;

public class CurrentManaPacket {

    private final int currentMana;

    public CurrentManaPacket(int currentMana) {
        this.currentMana = currentMana;
    }

    public static CurrentManaPacket decode(FriendlyByteBuf buf) {
        return new CurrentManaPacket(buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(currentMana);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            OverlayEvent.currentManaValue = currentMana;
        });
        context.get().setPacketHandled(true);
    }
}
