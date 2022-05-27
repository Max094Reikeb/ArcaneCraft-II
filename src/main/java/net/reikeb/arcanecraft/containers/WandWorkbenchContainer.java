package net.reikeb.arcanecraft.containers;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.reikeb.arcanecraft.init.ItemInit;
import net.reikeb.arcanecraft.tileentities.TileWandWorkbench;

import static net.reikeb.arcanecraft.init.ContainerInit.WAND_WORKBENCH_CONTAINER;

public class WandWorkbenchContainer extends AbstractContainer {

    public TileWandWorkbench tileEntity;

    public WandWorkbenchContainer(MenuType<?> type, int id) {
        super(type, id);
    }

    // Client
    public WandWorkbenchContainer(int id, Inventory inv, FriendlyByteBuf buf) {
        super(WAND_WORKBENCH_CONTAINER.get(), id);
        this.init(inv, this.tileEntity = (TileWandWorkbench) inv.player.level.getBlockEntity(buf.readBlockPos()));
    }

    // Server
    public WandWorkbenchContainer(int id, Inventory inv, TileWandWorkbench tile) {
        super(WAND_WORKBENCH_CONTAINER.get(), id);
        this.init(inv, this.tileEntity = tile);
    }

    public void init(Inventory playerInv, TileWandWorkbench tile) {
        if (tileEntity != null) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 79, 30) {
                    public boolean mayPlace(ItemStack itemStack) {
                        return (itemStack.getItem() == ItemInit.ARCANE_SCROLL.get());
                    }

                    public int getMaxStackSize() {
                        return 1;
                    }
                });
            });
        }
        this.layoutPlayerInventorySlots(playerInv);
    }

    public TileWandWorkbench getTileEntity() {
        return this.tileEntity;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < 1) {
                if (!this.moveItemStackTo(itemstack1, 1, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                if (index < 1 + 27) {
                    if (!this.moveItemStackTo(itemstack1, 1 + 27, this.slots.size(), true)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (!this.moveItemStackTo(itemstack1, 1, 1 + 27, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                return ItemStack.EMPTY;
            }
            if (itemstack1.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerIn, itemstack1);
        }
        return itemstack;
    }
}
