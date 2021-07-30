package net.reikeb.arcanecraft.network.packets;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class MagicSummoningPacket {

    public MagicSummoningPacket() {
    }

    public static MagicSummoningPacket decode(FriendlyByteBuf buf) {
        return new MagicSummoningPacket();
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer entity = context.get().getSender();
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
