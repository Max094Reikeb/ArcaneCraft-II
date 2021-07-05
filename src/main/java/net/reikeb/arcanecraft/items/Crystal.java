package net.reikeb.arcanecraft.items;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.reikeb.arcanecraft.init.BlockInit;
import net.reikeb.arcanecraft.setup.ItemGroups;

public class Crystal extends Item {

    public Crystal() {
        super(new Properties()
                .stacksTo(64)
                .tab(ItemGroups.ARCANECRAFT));
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 0;
    }

    @Override
    public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
        return 1F;
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        ActionResultType actionResultType = super.onItemUseFirst(stack, context);
        World world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState state = world.getBlockState(context.getClickedPos());
        PlayerEntity playerEntity = context.getPlayer();

        if (playerEntity == null) return ActionResultType.FAIL;

        if ((world.isEmptyBlock(blockPos.above())) && (state.getBlock() == Blocks.STONE)) {
            world.setBlock(blockPos.above(), BlockInit.CRYSTAL_BLOCK.get().defaultBlockState(), 3);
            int itemInHand = playerEntity.getItemInHand(context.getHand()).getCount();
            playerEntity.setItemInHand(context.getHand(), (itemInHand > 1 ? new ItemStack(this, (itemInHand - 1)) : ItemStack.EMPTY));
        }

        return actionResultType;
    }
}
