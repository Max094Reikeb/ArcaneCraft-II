package net.reikeb.arcanecraft.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;

import net.minecraftforge.common.ToolType;

import java.util.*;

public class CrystalBlock extends Block {

    public CrystalBlock() {
        super(Properties.of(Material.METAL)
                .sound(SoundType.GLASS)
                .strength(0.15f, 10f)
                .lightLevel(s -> 0)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE));
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }
}
