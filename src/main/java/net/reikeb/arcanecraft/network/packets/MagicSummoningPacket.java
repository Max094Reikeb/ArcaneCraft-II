package net.reikeb.arcanecraft.network.packets;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MagicSummoningPacket {

    public MagicSummoningPacket() {
    }

    public static MagicSummoningPacket decode(PacketBuffer buf) {
        return new MagicSummoningPacket();
    }

    public void encode(PacketBuffer buf) {
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayerEntity entity = context.get().getSender();
            if (entity == null) return;
            Advancement advancement = entity.server.getAdvancements().getAdvancement(new ResourceLocation("arcanecraft:magic_summoning"));
            if (advancement == null) System.out.println("Advancement 'Magic Summoning!' seems to be null");
            if (advancement == null) return;
            AdvancementProgress advancementProgress = entity.getAdvancements().getOrStartProgress(advancement);
            if (!advancementProgress.isDone()) {
                for (String criteria : advancementProgress.getRemainingCriteria()) {
                    entity.getAdvancements().award(advancement, criteria);
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
