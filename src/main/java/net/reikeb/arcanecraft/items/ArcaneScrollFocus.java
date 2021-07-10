package net.reikeb.arcanecraft.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import net.minecraftforge.api.distmarker.*;

import net.reikeb.arcanecraft.init.ScrollFocusInit;
import net.reikeb.arcanecraft.setup.ItemGroups;
import net.reikeb.arcanecraft.spell.*;

import javax.annotation.Nullable;
import java.util.List;

public class ArcaneScrollFocus extends Item {

    public ArcaneScrollFocus() {
        super(new Properties()
                .stacksTo(1)
                .tab(ItemGroups.ARCANECRAFT));
    }

    public int getUseDuration(ItemStack stack) {
        return 0;
    }

    public ItemStack getDefaultInstance() {
        return SpellUtils.setScrollFocus(super.getDefaultInstance(), ScrollFocusInit.EMPTY.get());
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> text, ITooltipFlag flag) {
        SpellUtils.addSpellItemTooltip(stack, text);
    }

    public boolean isFoil(ItemStack stack) {
        return super.isFoil(stack) || !SpellUtils.getSpell(stack).isEmpty();
    }

    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> stacks) {
        if (this.allowdedIn(group)) {
            for (ScrollFocusObject scrollFocusObject : ScrollFocusInit.SCOLL_FOCUS_REGISTRY.get()) {
                if (!scrollFocusObject.getSpells().isEmpty()) {
                    stacks.add(SpellUtils.setScrollFocus(new ItemStack(this), scrollFocusObject));
                }
            }
        }
    }
}
