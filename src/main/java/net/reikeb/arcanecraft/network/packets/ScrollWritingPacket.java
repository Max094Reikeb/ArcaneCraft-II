package net.reikeb.arcanecraft.network.packets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.*;

import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import net.reikeb.arcanecraft.containers.ScrollTableContainer;
import net.reikeb.arcanecraft.init.ItemInit;
import net.reikeb.arcanecraft.tileentities.TileScrollTable;

import java.util.function.Supplier;

public class ScrollWritingPacket {

    public ScrollWritingPacket() {
    }

    public static ScrollWritingPacket decode(PacketBuffer buf) {
        return new ScrollWritingPacket();
    }

    public void encode(PacketBuffer buf) {
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            PlayerEntity playerEntity = context.get().getSender();
            if ((playerEntity == null) || (!(playerEntity.containerMenu instanceof ScrollTableContainer))) return;
            TileScrollTable tileEntity = ((ScrollTableContainer) playerEntity.containerMenu).getTileEntity();
            ItemStack stackInSlot0 = tileEntity.getInventory().getStackInSlot(0);
            ItemStack stackInSlot1 = tileEntity.getInventory().getStackInSlot(1);

            if (stackInSlot0.getItem() != ItemInit.BLANK_SCROLL.get()) return;
            if (stackInSlot1.getItem() != ItemInit.ARCANE_SCROLL_FOCUS.get()) return;

            playerEntity.level.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.enchantment_table.use")),
                    SoundCategory.NEUTRAL, 10, 10);

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
