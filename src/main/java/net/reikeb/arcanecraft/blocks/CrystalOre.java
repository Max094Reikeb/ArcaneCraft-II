package net.reikeb.arcanecraft.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;

import net.minecraftforge.common.ToolType;

import net.reikeb.arcanecraft.init.ItemInit;

import java.util.*;

public class CrystalOre extends Block {

    public CrystalOre() {
        super(Properties.of(Material.METAL)
                .sound(SoundType.STONE)
                .strength(1f, 10f)
                .lightLevel(s -> 0)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE));
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(ItemInit.CRYSTAL.get(), 1));
    }
}
