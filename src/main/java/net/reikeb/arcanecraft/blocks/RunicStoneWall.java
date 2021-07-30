package net.reikeb.arcanecraft.blocks;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;

import net.minecraftforge.common.ToolType;

import java.util.Collections;
import java.util.List;

public class RunicStoneWall extends WallBlock {

    public RunicStoneWall() {
        super(Properties.of(Material.STONE)
                .sound(SoundType.STONE)
                .strength(1.1f, 17.5f)
                .lightLevel(s -> 0)
                .harvestLevel(0)
                .noOcclusion()
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
