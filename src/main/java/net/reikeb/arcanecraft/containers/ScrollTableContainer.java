package net.reikeb.arcanecraft.containers;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.reikeb.arcanecraft.init.ItemInit;
import net.reikeb.arcanecraft.tileentities.TileScrollTable;

import static net.reikeb.arcanecraft.init.ContainerInit.SCROLL_TABLE_CONTAINER;

public class ScrollTableContainer extends AbstractContainer {

    public TileScrollTable tileEntity;

    public ScrollTableContainer(MenuType<?> type, int id) {
        super(type, id);
    }

    // Client
    public ScrollTableContainer(int id, Inventory inv, FriendlyByteBuf buf) {
        super(SCROLL_TABLE_CONTAINER.get(), id);
        this.init(inv, this.tileEntity = (TileScrollTable) inv.player.level.getBlockEntity(buf.readBlockPos()));
    }

    // Server
    public ScrollTableContainer(int id, Inventory inv, TileScrollTable tile) {
        super(SCROLL_TABLE_CONTAINER.get(), id);
        this.init(inv, this.tileEntity = tile);
    }

    public void init(Inventory playerInv, TileScrollTable tile) {
        if (tileEntity != null) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 79, 39) {
                    public boolean mayPlace(ItemStack itemStack) {
                        return (itemStack.getItem() == ItemInit.BLANK_SCROLL.get());
                    }

                    public int getMaxStackSize() {
                        return 1;
                    }
                });
                addSlot(new SlotItemHandler(h, 1, 34, 39) {
                    public boolean mayPlace(ItemStack itemStack) {
                        return true;
                    }

                    public int getMaxStackSize() {
                        return 1;
                    }
                });
            });
        }
        this.layoutPlayerInventorySlots(playerInv);
    }

    public TileScrollTable getTileEntity() {
        return this.tileEntity;
    }
}
