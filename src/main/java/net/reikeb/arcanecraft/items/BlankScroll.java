package net.reikeb.arcanecraft.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import net.minecraftforge.api.distmarker.*;

import net.reikeb.arcanecraft.setup.ItemGroups;

import java.util.List;

public class BlankScroll extends Item {

    public BlankScroll() {
        super(new Properties()
                .stacksTo(1)
                .tab(ItemGroups.ARCANECRAFT));
    }

    public int getUseDuration(ItemStack stack) {
        return 0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(new TranslationTextComponent("spell.arcanecraft.empty").withStyle(TextFormatting.DARK_GRAY));
    }
}
