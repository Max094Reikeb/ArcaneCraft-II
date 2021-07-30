package net.reikeb.arcanecraft.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

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
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        InteractionResult actionResultType = super.onItemUseFirst(stack, context);
        Level world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState state = world.getBlockState(context.getClickedPos());
        Player playerEntity = context.getPlayer();

        if (playerEntity == null) return InteractionResult.FAIL;

        if ((world.isEmptyBlock(blockPos.above())) && (state.getBlock() == Blocks.STONE)) {
            world.setBlock(blockPos.above(), BlockInit.CRYSTAL_BLOCK.get().defaultBlockState(), 3);
            int itemInHand = playerEntity.getItemInHand(context.getHand()).getCount();
            playerEntity.setItemInHand(context.getHand(), (itemInHand > 1 ? new ItemStack(this, (itemInHand - 1)) : ItemStack.EMPTY));
        }

        return actionResultType;
    }
}
