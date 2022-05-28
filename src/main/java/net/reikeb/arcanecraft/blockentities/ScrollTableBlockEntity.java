package net.reikeb.arcanecraft.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.containers.ScrollTableContainer;
import net.reikeb.maxilib.abs.AbstractBlockEntity;

import static net.reikeb.arcanecraft.init.BlockEntityInit.SCROLL_TABLE_BLOCK_ENTITY;

public class ScrollTableBlockEntity extends AbstractBlockEntity {

    public ScrollTableBlockEntity(BlockPos pos, BlockState state) {
        super(SCROLL_TABLE_BLOCK_ENTITY.get(), pos, state, "scroll_table", ArcaneCraft.MODID, 2);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new ScrollTableContainer(id, this.getBlockPos(), playerInventory, player);
    }
}
