package net.reikeb.arcanecraft.tileentities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

import net.reikeb.arcanecraft.containers.WandWorkbenchContainer;
import net.reikeb.arcanecraft.init.ContainerInit;

import static net.reikeb.arcanecraft.init.TileEntityInit.TILE_WAND_WORKBENCH;

public class TileWandWorkbench extends AbstractTileEntity {

    public TileWandWorkbench(BlockPos pos, BlockState state) {
        super(TILE_WAND_WORKBENCH.get(), pos, state, 1);
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
    public AbstractContainerMenu createMenu(final int windowID, final Inventory playerInv, final Player playerIn) {
        return new WandWorkbenchContainer(windowID, playerInv, this);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory player) {
        return new WandWorkbenchContainer(ContainerInit.WAND_WORKBENCH_CONTAINER.get(), id);
    }
}
