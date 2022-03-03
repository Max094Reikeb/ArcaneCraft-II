package net.reikeb.arcanecraft.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.fmllegacy.network.NetworkEvent;

import net.reikeb.arcanecraft.blocks.WandWorkbench;
import net.reikeb.arcanecraft.containers.WandWorkbenchContainer;
import net.reikeb.arcanecraft.init.ItemInit;
import net.reikeb.arcanecraft.tileentities.TileWandWorkbench;

import java.util.function.Supplier;

public class WandWorkbenchingPacket {

    public WandWorkbenchingPacket() {
    }

    public static WandWorkbenchingPacket decode(FriendlyByteBuf buf) {
        return new WandWorkbenchingPacket();
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Player playerEntity = context.get().getSender();
            if ((playerEntity == null) || (!(playerEntity.containerMenu instanceof WandWorkbenchContainer))) return;
            TileWandWorkbench tileEntity = ((WandWorkbenchContainer) playerEntity.containerMenu).getTileEntity();
            ItemStack stackInSlot0 = tileEntity.getInventory().getStackInSlot(0);

            if (stackInSlot0.getItem() != ItemInit.ARCANE_SCROLL.get()) return;

            playerEntity.level.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
                    SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.NEUTRAL, 10, 10);

            ItemStack newScroll = new ItemStack(ItemInit.WAND.get());
            newScroll.getOrCreateTag().putString("Wand", stackInSlot0.getOrCreateTag().getString("Scroll"));

            tileEntity.removeItemIndexCount(0, 1);
            playerEntity.addItem(newScroll);

            playerEntity.level.setBlockAndUpdate(tileEntity.getBlockPos(), tileEntity.getBlockState()
                    .setValue(WandWorkbench.USED, false));
        });
        context.get().setPacketHandled(true);
    }
}
