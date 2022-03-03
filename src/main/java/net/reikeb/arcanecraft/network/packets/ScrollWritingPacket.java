package net.reikeb.arcanecraft.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.fmllegacy.network.NetworkEvent;

import net.reikeb.arcanecraft.containers.ScrollTableContainer;
import net.reikeb.arcanecraft.init.ItemInit;
import net.reikeb.arcanecraft.tileentities.TileScrollTable;

import java.util.function.Supplier;

public class ScrollWritingPacket {

    public ScrollWritingPacket() {
    }

    public static ScrollWritingPacket decode(FriendlyByteBuf buf) {
        return new ScrollWritingPacket();
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Player playerEntity = context.get().getSender();
            if ((playerEntity == null) || (!(playerEntity.containerMenu instanceof ScrollTableContainer))) return;
            TileScrollTable tileEntity = ((ScrollTableContainer) playerEntity.containerMenu).getTileEntity();
            ItemStack stackInSlot0 = tileEntity.getInventory().getStackInSlot(0);
            ItemStack stackInSlot1 = tileEntity.getInventory().getStackInSlot(1);

            if (stackInSlot0.getItem() != ItemInit.BLANK_SCROLL.get()) return;
            if (stackInSlot1.getItem() != ItemInit.ARCANE_SCROLL_FOCUS.get()) return;

            playerEntity.level.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
                    SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.NEUTRAL, 10, 10);

            ItemStack newScroll = new ItemStack(ItemInit.ARCANE_SCROLL.get());
            newScroll.getOrCreateTag().putString("Scroll", stackInSlot1.getOrCreateTag().getString("ScrollFocus"));

            tileEntity.removeItemIndexCount(0, 1);
            tileEntity.removeItemIndexCount(1, 1);
            tileEntity.setStackIndex(0, newScroll);
            tileEntity.setItemIndexCount(1, 1, ItemInit.INK_VIAL.get());
        });
        context.get().setPacketHandled(true);
    }
}
