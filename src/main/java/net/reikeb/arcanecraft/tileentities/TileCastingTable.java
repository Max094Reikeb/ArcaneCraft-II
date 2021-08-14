package net.reikeb.arcanecraft.tileentities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

import net.reikeb.arcanecraft.containers.CastingTableContainer;
import net.reikeb.arcanecraft.init.ContainerInit;

import static net.reikeb.arcanecraft.init.TileEntityInit.TILE_CASTING_TABLE;

public class TileCastingTable extends AbstractTileEntity {

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
}
