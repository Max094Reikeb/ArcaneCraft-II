package net.reikeb.arcanecraft.spell;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;

import net.minecraftforge.api.distmarker.*;

import net.reikeb.arcanecraft.init.*;
import net.reikeb.arcanecraft.items.*;

import java.util.*;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class SpellUtils {

    private static final IFormattableTextComponent NO_CONTENT = (new TranslationTextComponent("spell.arcanecraft.empty")).withStyle(TextFormatting.GRAY);

    public static List<SpellInstance> getSpell(ItemStack stack) {
        if (stack.getItem() instanceof ArcaneScroll) {
            return getAllScrollSpells(stack.getTag());
        } else if (stack.getItem() instanceof ArcaneScrollFocus) {
            return getAllScrollFocusSpells(stack.getTag());
        }
        return getAllWandSpells(stack.getTag());
    }

    public static List<SpellInstance> getAllSpells(ScrollObject scrollObject, Collection<SpellInstance> spellInstances) {
        List<SpellInstance> list = Lists.newArrayList();
        for (Supplier<SpellInstance> spellInstanceSupplier : scrollObject.getSpells()) {
            list.add(spellInstanceSupplier.get());
        }
        list.addAll(spellInstances);
        return list;
    }

    public static List<SpellInstance> getAllSpells(ScrollFocusObject scrollFocusObject, Collection<SpellInstance> spellInstances) {
        List<SpellInstance> list = Lists.newArrayList();
        for (Supplier<SpellInstance> spellInstanceSupplier : scrollFocusObject.getSpells()) {
            list.add(spellInstanceSupplier.get());
        }
        list.addAll(spellInstances);
        return list;
    }

    public static List<SpellInstance> getAllSpells(WandObject wandObject, Collection<SpellInstance> spellInstances) {
        List<SpellInstance> list = Lists.newArrayList();
        for (Supplier<SpellInstance> spellInstanceSupplier : wandObject.getSpells()) {
            list.add(spellInstanceSupplier.get());
        }
        list.addAll(spellInstances);
        return list;
    }

    public static List<SpellInstance> getAllScrollSpells(@Nullable CompoundNBT nbt) {
        List<SpellInstance> list = Lists.newArrayList();
        for (Supplier<SpellInstance> spellInstanceSupplier : getScroll(nbt).getSpells()) {
            list.add(spellInstanceSupplier.get());
        }
        getCustomSpells(nbt, list);
        return list;
    }

    public static List<SpellInstance> getAllScrollFocusSpells(@Nullable CompoundNBT nbt) {
        List<SpellInstance> list = Lists.newArrayList();
        for (Supplier<SpellInstance> spellInstanceSupplier : getScrollFocus(nbt).getSpells()) {
            list.add(spellInstanceSupplier.get());
        }
        getCustomSpells(nbt, list);
        return list;
    }

    public static List<SpellInstance> getAllWandSpells(@Nullable CompoundNBT nbt) {
        List<SpellInstance> list = Lists.newArrayList();
        for (Supplier<SpellInstance> spellInstanceSupplier : getWand(nbt).getSpells()) {
            list.add(spellInstanceSupplier.get());
        }
        getCustomSpells(nbt, list);
        return list;
    }

    public static List<SpellInstance> getCustomSpells(ItemStack stack) {
        return getCustomSpells(stack.getTag());
    }

    public static List<SpellInstance> getCustomSpells(@Nullable CompoundNBT nbt) {
        List<SpellInstance> list = Lists.newArrayList();
        getCustomSpells(nbt, list);
        return list;
    }

    public static void getCustomSpells(@Nullable CompoundNBT nbt, List<SpellInstance> spellInstances) {
        if (nbt != null && nbt.contains("CustomSpellEffects", 9)) {
            ListNBT listNBT = nbt.getList("CustomSpellEffects", 10);

            for (int i = 0; i < listNBT.size(); i++) {
                CompoundNBT compoundNBT = listNBT.getCompound(i);
                SpellInstance spellInstance = SpellInstance.load(compoundNBT);
                if (spellInstance != null) {
                    spellInstances.add(spellInstance);
                }
            }
        }
    }

    public static ScrollObject getScroll(ItemStack stack) {
        return getScroll(stack.getTag());
    }

    public static ScrollFocusObject getScrollFocus(ItemStack stack) {
        return getScrollFocus(stack.getTag());
    }

    public static WandObject getWand(ItemStack stack) {
        return getWand(stack.getTag());
    }

    public static ScrollObject getScroll(@Nullable CompoundNBT nbt) {
        return nbt == null ? ScrollInit.EMPTY.get() : ScrollObject.byName(nbt.getString("Scroll"));
    }

    public static ScrollFocusObject getScrollFocus(@Nullable CompoundNBT nbt) {
        return nbt == null ? ScrollFocusInit.EMPTY.get() : ScrollFocusObject.byName(nbt.getString("ScrollFocus"));
    }

    public static WandObject getWand(@Nullable CompoundNBT nbt) {
        return nbt == null ? WandInit.EMPTY.get() : WandObject.byName(nbt.getString("Wand"));
    }

    public static ItemStack setScroll(ItemStack stack, ScrollObject scrollObject) {
        ResourceLocation resourceLocation = ScrollInit.SCROLL_REGISTRY.get().getKey(scrollObject);
        if (scrollObject == ScrollInit.EMPTY.get()) {
            stack.removeTagKey("Scroll");
        } else {
            stack.getOrCreateTag().putString("Scroll", resourceLocation.toString());
        }

        return stack;
    }

    public static ItemStack setScrollFocus(ItemStack stack, ScrollFocusObject scrollFocusObject) {
        ResourceLocation resourceLocation = ScrollFocusInit.SCOLL_FOCUS_REGISTRY.get().getKey(scrollFocusObject);
        if (scrollFocusObject == ScrollFocusInit.EMPTY.get()) {
            stack.removeTagKey("ScrollFocus");
        } else {
            stack.getOrCreateTag().putString("ScrollFocus", resourceLocation.toString());
        }

        return stack;
    }

    public static ItemStack setWand(ItemStack stack, WandObject wandObject) {
        ResourceLocation resourcelocation = WandInit.WAND_REGISTRY.get().getKey(wandObject);
        if (wandObject == WandInit.EMPTY.get()) {
            stack.removeTagKey("Wand");
        } else {
            stack.getOrCreateTag().putString("Wand", resourcelocation.toString());
        }

        return stack;
    }

    public static ItemStack setCustomSpells(ItemStack stack, Collection<SpellInstance> spellInstances) {
        if (!spellInstances.isEmpty()) {
            CompoundNBT compoundnbt = stack.getOrCreateTag();
            ListNBT listnbt = compoundnbt.getList("CustomSpellEffects", 9);

            for (SpellInstance spellInstance : spellInstances) {
                listnbt.add(spellInstance.save(new CompoundNBT()));
            }

            compoundnbt.put("CustomSpellEffects", listnbt);
        }
        return stack;
    }

    @OnlyIn(Dist.CLIENT)
    public static void addSpellItemTooltip(ItemStack stack, List<ITextComponent> text) {
        List<SpellInstance> list = getSpell(stack);
        if (list.isEmpty()) {
            text.add(NO_CONTENT);
        } else {
            for (SpellInstance spellInstance : list) {
                Spell spell = spellInstance.getSpell();
                text.add(new TranslationTextComponent(spell.getDescriptionId()).withStyle(spell.getFormatting()));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void addWandTooltip(ItemStack stack, List<ITextComponent> text) {
        List<SpellInstance> list = getSpell(stack);
        if (list.isEmpty()) {
            text.add(NO_CONTENT);
        } else {
            for (SpellInstance spellInstance : list) {
                Spell spell = spellInstance.getSpell();
                int mana = spellInstance.getMana();
                text.add(new TranslationTextComponent(spell.getDescriptionId()).withStyle(spell.getFormatting()));
                text.add(new TranslationTextComponent("item.arcanecraft.wand_require_mana", mana).withStyle(TextFormatting.DARK_GRAY));
            }
        }
    }
}
