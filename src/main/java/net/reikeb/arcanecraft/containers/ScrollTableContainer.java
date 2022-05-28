package net.reikeb.arcanecraft.containers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.CapabilityItemHandler;
import net.reikeb.arcanecraft.blockentities.ScrollTableBlockEntity;
import net.reikeb.arcanecraft.init.ItemInit;
import net.reikeb.maxilib.abs.AbstractContainer;
import net.reikeb.maxilib.inventory.Slots;

import static net.reikeb.arcanecraft.init.ContainerInit.SCROLL_TABLE_CONTAINER;

public class ScrollTableContainer extends AbstractContainer {

    public ScrollTableBlockEntity scrollTableBlockEntity;

    public ScrollTableContainer(int id, BlockPos pos, Inventory inv, Player player) {
        super(SCROLL_TABLE_CONTAINER.get(), id, 2);

        this.scrollTableBlockEntity = (ScrollTableBlockEntity) player.getCommandSenderWorld().getBlockEntity(pos);
        if (scrollTableBlockEntity == null) return;

        scrollTableBlockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            addSlot(new Slots(h, 0, 79, 39, c -> c.getItem() == ItemInit.BLANK_SCROLL.get(), 1));
            addSlot(new Slots(h, 1, 34, 39, 1));
        });

        this.layoutPlayerInventorySlots(inv);
    }

    public ScrollTableBlockEntity getBlockEntity() {
        return this.scrollTableBlockEntity;
    }
}
