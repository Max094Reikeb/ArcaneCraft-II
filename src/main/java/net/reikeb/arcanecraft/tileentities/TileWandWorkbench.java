package net.reikeb.arcanecraft.tileentities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import net.reikeb.arcanecraft.containers.WandWorkbenchContainer;
import net.reikeb.arcanecraft.init.ContainerInit;
import net.reikeb.arcanecraft.utils.ItemHandler;

import static net.reikeb.arcanecraft.init.TileEntityInit.TILE_WAND_WORKBENCH;

public class TileWandWorkbench extends RandomizableContainerBlockEntity {

    private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
    private final ItemHandler inventory;

    public TileWandWorkbench(BlockPos pos, BlockState state) {
        super(TILE_WAND_WORKBENCH.get(), pos, state);

        this.inventory = new ItemHandler(1);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("gui.arcanecraft.wand_workbench.name");
    }

    @Override
    protected Component getDefaultName() {
        return new TextComponent("wand_workbench");
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
    public AbstractContainerMenu createMenu(final int windowID, final Inventory playerInv, final Player playerIn) {
        return new WandWorkbenchContainer(windowID, playerInv, this);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory player) {
        return new WandWorkbenchContainer(ContainerInit.WAND_WORKBENCH_CONTAINER.get(), id);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
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
    public void load(CompoundTag compoundNBT) {
        super.load(compoundNBT);
        if (compoundNBT.contains("Inventory")) {
            inventory.deserializeNBT((CompoundTag) compoundNBT.get("Inventory"));
        }
    }

    @Override
    public CompoundTag save(CompoundTag compoundNBT) {
        super.save(compoundNBT);
        compoundNBT.put("Inventory", inventory.serializeNBT());
        return compoundNBT;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.inventory));
    }

    public void dropItems(Level world, BlockPos pos) {
        for (int i = 0; i < 1; i++)
            if (!inventory.getStackInSlot(i).isEmpty()) {
                Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(i));
            }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 0, this.getUpdateTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.save(new CompoundTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    @Override
    public int getContainerSize() {
        return 1;
    }
}
