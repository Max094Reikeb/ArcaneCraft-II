package net.reikeb.arcanecraft.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.reikeb.arcanecraft.blockentities.WandWorkbenchBlockEntity;
import net.reikeb.arcanecraft.blocks.WandWorkbench;
import net.reikeb.arcanecraft.containers.WandWorkbenchContainer;
import net.reikeb.arcanecraft.init.ItemInit;

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
            WandWorkbenchBlockEntity blockEntity = ((WandWorkbenchContainer) playerEntity.containerMenu).getBlockEntity();
            ItemStack stackInSlot0 = blockEntity.getInventory().getStackInSlot(0);

            if (stackInSlot0.getItem() != ItemInit.ARCANE_SCROLL.get()) return;

            playerEntity.level.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
                    SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.NEUTRAL, 10, 10);

            ItemStack newScroll = new ItemStack(ItemInit.WAND.get());
            newScroll.getOrCreateTag().putString("Wand", stackInSlot0.getOrCreateTag().getString("Scroll"));

            blockEntity.removeItemIndexCount(0, 1);
            playerEntity.addItem(newScroll);

            playerEntity.level.setBlockAndUpdate(blockEntity.getBlockPos(), blockEntity.getBlockState()
                    .setValue(WandWorkbench.USED, false));
        });
        context.get().setPacketHandled(true);
    }
}
