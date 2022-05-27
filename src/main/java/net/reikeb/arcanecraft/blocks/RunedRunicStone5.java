package net.reikeb.arcanecraft.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;
import java.util.List;

public class RunedRunicStone5 extends Block {

    public RunedRunicStone5() {
        super(Properties.of(Material.STONE)
                .sound(SoundType.STONE)
                .strength(1.1f, 17.5f)
                .lightLevel(s -> 0));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, BlockGetter world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        list.add(new TranslatableComponent("block.arcanecraft.runed_runic_stone_5.description").withStyle(ChatFormatting.DARK_GRAY));
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }
}
