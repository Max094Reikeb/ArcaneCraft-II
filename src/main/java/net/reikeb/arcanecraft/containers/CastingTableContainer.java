package net.reikeb.arcanecraft.containers;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import net.reikeb.arcanecraft.tileentities.TileCastingTable;

import static net.reikeb.arcanecraft.init.ContainerInit.CASTING_TABLE_CONTAINER;

public class CastingTableContainer extends AbstractContainer {

    public TileCastingTable tileEntity;

    public CastingTableContainer(MenuType<?> type, int id) {
        super(type, id);
    }

    // Client
    public CastingTableContainer(int id, Inventory inv, FriendlyByteBuf buf) {
        super(CASTING_TABLE_CONTAINER.get(), id);
        this.init(inv, this.tileEntity = (TileCastingTable) inv.player.level.getBlockEntity(buf.readBlockPos()));
    }

    // Server
    public CastingTableContainer(int id, Inventory inv, TileCastingTable tile) {
        super(CASTING_TABLE_CONTAINER.get(), id);
        this.init(inv, this.tileEntity = tile);
    }

    public void init(Inventory playerInv, TileCastingTable tile) {
        if (tileEntity != null) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 59, 33) {
                    public boolean mayPlace(ItemStack itemStack) {
                        return true;
                    }

                    public int getMaxStackSize() {
                        return 1;
                    }
                });
                addSlot(new SlotItemHandler(h, 1, 101, 32) {
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

    public TileCastingTable getTileEntity() {
        return this.tileEntity;
    }
}
