package net.reikeb.arcanecraft.network.packets;

import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.fmllegacy.network.NetworkEvent;

import net.reikeb.arcanecraft.events.OverlayEvent;

import java.util.function.Supplier;

public class MaxManaPacket {

    private final int maxMana;

    public MaxManaPacket(int maxMana) {
        this.maxMana = maxMana;
    }

    public static MaxManaPacket decode(FriendlyByteBuf buf) {
        return new MaxManaPacket(buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(maxMana);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            OverlayEvent.maxManaValue = maxMana;
        });
        context.get().setPacketHandled(true);
    }
}
