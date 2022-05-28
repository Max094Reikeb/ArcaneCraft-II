package net.reikeb.arcanecraft.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.containers.WandWorkbenchContainer;
import net.reikeb.maxilib.abs.AbstractBlockEntity;

import static net.reikeb.arcanecraft.init.BlockEntityInit.WAND_WORKBENCH_BLOCK_ENTITY;

public class WandWorkbenchBlockEntity extends AbstractBlockEntity {

    public WandWorkbenchBlockEntity(BlockPos pos, BlockState state) {
        super(WAND_WORKBENCH_BLOCK_ENTITY.get(), pos, state, "wand_workbench", ArcaneCraft.MODID, 1);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new WandWorkbenchContainer(id, this.getBlockPos(), playerInventory, player);
    }
}
