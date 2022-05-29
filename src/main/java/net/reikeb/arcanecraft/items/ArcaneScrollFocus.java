package net.reikeb.arcanecraft.items;

import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.reikeb.arcanecraft.init.ScrollFocusInit;
import net.reikeb.arcanecraft.setup.ItemGroups;
import net.reikeb.arcanecraft.spell.ScrollFocusObject;
import net.reikeb.arcanecraft.spell.SpellUtils;

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
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> text, TooltipFlag flag) {
        SpellUtils.addSpellItemTooltip(stack, text);
    }

    public boolean isFoil(ItemStack stack) {
        return super.isFoil(stack) || !SpellUtils.getSpell(stack).isEmpty();
    }

    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> stacks) {
        if (this.allowdedIn(group)) {
            for (ScrollFocusObject scrollFocusObject : ScrollFocusInit.SCOLL_FOCUS_REGISTRY.get()) {
                if (!scrollFocusObject.getSpells().isEmpty()) {
                    stacks.add(SpellUtils.setScrollFocus(new ItemStack(this), scrollFocusObject));
                }
            }
        }
    }
}
