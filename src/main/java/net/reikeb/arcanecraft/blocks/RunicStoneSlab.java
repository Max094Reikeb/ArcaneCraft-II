package net.reikeb.arcanecraft.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.properties.SlabType;

import net.minecraftforge.common.ToolType;

import java.util.*;

public class RunicStoneSlab extends SlabBlock {

    public RunicStoneSlab() {
        super(Properties.of(Material.STONE)
                .sound(SoundType.STONE)
                .strength(1.1f, 17.5f)
                .lightLevel(s -> 0)
                .harvestLevel(0)
                .harvestTool(ToolType.PICKAXE));
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, state.getValue(TYPE) == SlabType.DOUBLE ? 2 : 1));
    }
}
