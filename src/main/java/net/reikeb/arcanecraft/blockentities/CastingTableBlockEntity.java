package net.reikeb.arcanecraft.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.containers.CastingTableContainer;
import net.reikeb.arcanecraft.init.ItemInit;
import net.reikeb.maxilib.Couple;
import net.reikeb.maxilib.abs.AbstractBlockEntity;

import static net.reikeb.arcanecraft.init.BlockEntityInit.CASTING_TABLE_BLOCK_ENTITY;

public class CastingTableBlockEntity extends AbstractBlockEntity {

    public static final BlockEntityTicker<CastingTableBlockEntity> TICKER = (level, pos, state, be) -> be.tick(level, pos, state, be);

    public CastingTableBlockEntity(BlockPos pos, BlockState state) {
        super(CASTING_TABLE_BLOCK_ENTITY.get(), pos, state, "casting_table", ArcaneCraft.MODID, 2);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new CastingTableContainer(id, this.getBlockPos(), playerInventory, player);
    }

    public <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState state, T t) {
        if (level == null) return;

        Couple<ItemStack, ItemStack> stackInSlots = new Couple<>(inventory.getStackInSlot(0), inventory.getStackInSlot(1));

        if ((stackInSlots.part1().getItem() == ItemInit.GOLD_RING.get()) && (stackInSlots.part2().getItem() == ItemInit.ARCANE_ESSENCE.get())) {
            inventory.removeStackFromSlot(1);
            inventory.removeStackFromSlot(0);
            inventory.setStackInSlot(0, new ItemStack(ItemInit.ARCANE_RING.get()));
            inventory.setStackInSlot(1, new ItemStack(Items.GLASS_BOTTLE));
        }

        /*
        t.setChanged();
        world.sendBlockUpdated(blockPos, t.getBlockState(), t.getBlockState(),
                Constants.BlockFlags.NOTIFY_NEIGHBORS);
         */
    }
}
