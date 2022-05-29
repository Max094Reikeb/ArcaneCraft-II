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
import net.reikeb.arcanecraft.init.ScrollInit;
import net.reikeb.arcanecraft.setup.ItemGroups;
import net.reikeb.arcanecraft.spell.ScrollObject;
import net.reikeb.arcanecraft.spell.SpellUtils;

import javax.annotation.Nullable;
import java.util.List;

public class ArcaneScroll extends Item {

    public ArcaneScroll() {
        super(new Properties()
                .stacksTo(1)
                .tab(ItemGroups.ARCANECRAFT));
    }

    public int getUseDuration(ItemStack stack) {
        return 0;
    }

    public ItemStack getDefaultInstance() {
        return SpellUtils.setScroll(super.getDefaultInstance(), ScrollInit.EMPTY.get());
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
            for (ScrollObject scrollObject : ScrollInit.SCROLL_REGISTRY.get()) {
                if (!scrollObject.getSpells().isEmpty()) {
                    stacks.add(SpellUtils.setScroll(new ItemStack(this), scrollObject));
                }
            }
        }
    }
}
