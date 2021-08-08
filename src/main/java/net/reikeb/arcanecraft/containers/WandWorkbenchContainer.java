package net.reikeb.arcanecraft.containers;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import net.reikeb.arcanecraft.init.ItemInit;
import net.reikeb.arcanecraft.tileentities.TileWandWorkbench;
import net.reikeb.arcanecraft.utils.Util;

import static net.reikeb.arcanecraft.init.ContainerInit.WAND_WORKBENCH_CONTAINER;

public class WandWorkbenchContainer extends AbstractContainerMenu {

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
        Util.layoutPlayerInventorySlots(this, playerInv);
    }

    public TileWandWorkbench getTileEntity() {
        return this.tileEntity;
    }

    @Override
    public boolean stillValid(Player playerEntity) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        return Util.quickMoveStack(this, playerIn, index, 1);
    }
}
