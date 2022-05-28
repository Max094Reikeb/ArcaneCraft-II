package net.reikeb.arcanecraft.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.reikeb.arcanecraft.events.OverlayEvent;

import java.util.function.Supplier;

public class ManaProgressPacket {

    private final float manaProgress;

    public ManaProgressPacket(float manaProgress) {
        this.manaProgress = manaProgress;
    }

    public static ManaProgressPacket decode(FriendlyByteBuf buf) {
        return new ManaProgressPacket(buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(manaProgress);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            OverlayEvent.manaProgress = manaProgress;
        });
        context.get().setPacketHandled(true);
    }
}
