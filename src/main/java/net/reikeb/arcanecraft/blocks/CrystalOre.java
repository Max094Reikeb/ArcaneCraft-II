package net.reikeb.arcanecraft.blocks;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;

import net.minecraftforge.common.ToolType;

import net.reikeb.arcanecraft.init.ItemInit;

import java.util.Collections;
import java.util.List;

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
