package net.reikeb.arcanecraft.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import net.reikeb.arcanecraft.setup.ItemGroups;

public class SpellBook extends Item {

    public SpellBook() {
        super(new Properties()
                .stacksTo(1)
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
}
