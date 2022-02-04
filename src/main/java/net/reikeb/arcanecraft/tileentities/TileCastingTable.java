package net.reikeb.arcanecraft.tileentities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.common.util.Constants;

import net.reikeb.arcanecraft.containers.CastingTableContainer;
import net.reikeb.arcanecraft.init.ContainerInit;
import net.reikeb.arcanecraft.init.ItemInit;

import static net.reikeb.arcanecraft.init.TileEntityInit.TILE_CASTING_TABLE;

public class TileCastingTable extends AbstractTileEntity {

    public static final BlockEntityTicker<TileCastingTable> TICKER = (level, pos, state, be) -> be.tick(level, pos, state, be);

    public TileCastingTable(BlockPos pos, BlockState state) {
        super(TILE_CASTING_TABLE.get(), pos, state, 2);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("gui.arcanecraft.casting_table.name");
    }

    @Override
    protected Component getDefaultName() {
        return new TextComponent("casting_table");
    }

    @Override
    public AbstractContainerMenu createMenu(final int windowID, final Inventory playerInv, final Player playerIn) {
        return new CastingTableContainer(windowID, playerInv, this);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory player) {
        return new CastingTableContainer(ContainerInit.CASTING_TABLE_CONTAINER.get(), id);
    }

    public <T extends BlockEntity> void tick(Level world, BlockPos blockPos, BlockState state, T t) {
        if (world == null) return;

        ItemStack slot0 = inventory.getStackInSlot(0);
        ItemStack slot1 = inventory.getStackInSlot(1);

        if ((slot0.getItem() == ItemInit.GOLD_RING.get()) && (slot1.getItem() == ItemInit.ARCANE_ESSENCE.get())) {
            inventory.removeStackFromSlot(1);
            inventory.removeStackFromSlot(0);
            inventory.setStackInSlot(0, new ItemStack(ItemInit.ARCANE_RING.get()));
            inventory.setStackInSlot(1, new ItemStack(Items.GLASS_BOTTLE));
        }

        t.setChanged();
        world.sendBlockUpdated(blockPos, t.getBlockState(), t.getBlockState(),
                Constants.BlockFlags.NOTIFY_NEIGHBORS);
    }
}
