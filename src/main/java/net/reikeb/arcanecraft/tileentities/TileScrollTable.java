package net.reikeb.arcanecraft.tileentities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.*;

import net.reikeb.arcanecraft.containers.ScrollTableContainer;
import net.reikeb.arcanecraft.init.ContainerInit;
import net.reikeb.arcanecraft.utils.ItemHandler;

import static net.reikeb.arcanecraft.init.TileEntityInit.TILE_SCROLL_TABLE;

public class TileScrollTable extends LockableLootTileEntity implements ITickableTileEntity {

    private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);
    private final ItemHandler inventory;

    public TileScrollTable() {
        super(TILE_SCROLL_TABLE.get());

        this.inventory = new ItemHandler(2);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("gui.arcanecraft.scroll_table.name");
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new StringTextComponent("scroll_table");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> stacks) {
        this.stacks = stacks;
    }

    @Override
    public Container createMenu(final int windowID, final PlayerInventory playerInv, final PlayerEntity playerIn) {
        return new ScrollTableContainer(windowID, playerInv, this);
    }

    @Override
    public Container createMenu(int id, PlayerInventory player) {
        return new ScrollTableContainer(ContainerInit.SCROLL_TABLE_CONTAINER.get(), id);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
    }

    @Override
    public void tick() {
    }

    public final IItemHandlerModifiable getInventory() {
        return this.inventory;
    }

    public void removeItemIndexCount(int index, int count) {
        this.inventory.decrStackSize(index, count);
    }

    public void setItemIndexCount(int index, int count, Item item) {
        this.inventory.setStackInSlot(index, new ItemStack(item, count));
    }

    public void setStackIndex(int index, ItemStack stack) {
        this.inventory.setStackInSlot(index, stack);
    }

    @Override
    public void load(BlockState blockState, CompoundNBT compoundNBT) {
        super.load(blockState, compoundNBT);
        if (compoundNBT.contains("Inventory")) {
            inventory.deserializeNBT((CompoundNBT) compoundNBT.get("Inventory"));
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT compoundNBT) {
        super.save(compoundNBT);
        compoundNBT.put("Inventory", inventory.serializeNBT());
        return compoundNBT;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.inventory));
    }

    public void dropItems(World world, BlockPos pos) {
        for (int i = 0; i < 2; i++)
            if (!inventory.getStackInSlot(i).isEmpty()) {
                InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(i));
            }
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.worldPosition, 0, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.save(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.load(this.getBlockState(), pkt.getTag());
    }

    @Override
    public int getContainerSize() {
        return 2;
    }
}
