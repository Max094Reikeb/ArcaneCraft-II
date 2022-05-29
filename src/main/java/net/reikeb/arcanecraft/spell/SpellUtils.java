package net.reikeb.arcanecraft.spell;

import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.reikeb.arcanecraft.init.ScrollFocusInit;
import net.reikeb.arcanecraft.init.ScrollInit;
import net.reikeb.arcanecraft.init.WandInit;
import net.reikeb.arcanecraft.items.ArcaneScroll;
import net.reikeb.arcanecraft.items.ArcaneScrollFocus;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class SpellUtils {

    private static final MutableComponent NO_CONTENT = (new TranslatableComponent("spell.arcanecraft.empty")).withStyle(ChatFormatting.GRAY);

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

    public static List<SpellInstance> getAllScrollSpells(@Nullable CompoundTag nbt) {
        List<SpellInstance> list = Lists.newArrayList();
        for (Supplier<SpellInstance> spellInstanceSupplier : getScroll(nbt).getSpells()) {
            list.add(spellInstanceSupplier.get());
        }
        getCustomSpells(nbt, list);
        return list;
    }

    public static List<SpellInstance> getAllScrollFocusSpells(@Nullable CompoundTag nbt) {
        List<SpellInstance> list = Lists.newArrayList();
        for (Supplier<SpellInstance> spellInstanceSupplier : getScrollFocus(nbt).getSpells()) {
            list.add(spellInstanceSupplier.get());
        }
        getCustomSpells(nbt, list);
        return list;
    }

    public static List<SpellInstance> getAllWandSpells(@Nullable CompoundTag nbt) {
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

    public static List<SpellInstance> getCustomSpells(@Nullable CompoundTag nbt) {
        List<SpellInstance> list = Lists.newArrayList();
        getCustomSpells(nbt, list);
        return list;
    }

    public static void getCustomSpells(@Nullable CompoundTag nbt, List<SpellInstance> spellInstances) {
        if (nbt != null && nbt.contains("CustomSpellEffects", 9)) {
            ListTag listNBT = nbt.getList("CustomSpellEffects", 10);

            for (int i = 0; i < listNBT.size(); i++) {
                CompoundTag compoundNBT = listNBT.getCompound(i);
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

    public static ScrollObject getScroll(@Nullable CompoundTag nbt) {
        return nbt == null ? ScrollInit.EMPTY.get() : ScrollObject.byName(nbt.getString("Scroll"));
    }

    public static ScrollFocusObject getScrollFocus(@Nullable CompoundTag nbt) {
        return nbt == null ? ScrollFocusInit.EMPTY.get() : ScrollFocusObject.byName(nbt.getString("ScrollFocus"));
    }

    public static WandObject getWand(@Nullable CompoundTag nbt) {
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
            CompoundTag compoundnbt = stack.getOrCreateTag();
            ListTag listnbt = compoundnbt.getList("CustomSpellEffects", 9);

            for (SpellInstance spellInstance : spellInstances) {
                listnbt.add(spellInstance.save(new CompoundTag()));
            }

            compoundnbt.put("CustomSpellEffects", listnbt);
        }
        return stack;
    }

    @OnlyIn(Dist.CLIENT)
    public static void addSpellItemTooltip(ItemStack stack, List<Component> text) {
        List<SpellInstance> list = getSpell(stack);
        if (list.isEmpty()) {
            text.add(NO_CONTENT);
        } else {
            for (SpellInstance spellInstance : list) {
                Spell spell = spellInstance.getSpell();
                text.add(new TranslatableComponent(spell.getDescriptionId()).withStyle(spell.getFormatting()));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void addWandTooltip(ItemStack stack, List<Component> text) {
        List<SpellInstance> list = getSpell(stack);
        if (list.isEmpty()) {
            text.add(NO_CONTENT);
        } else {
            for (SpellInstance spellInstance : list) {
                Spell spell = spellInstance.getSpell();
                int mana = spellInstance.getMana();
                text.add(new TranslatableComponent(spell.getDescriptionId()).withStyle(spell.getFormatting()));
                text.add(new TranslatableComponent("item.arcanecraft.wand_require_mana", mana).withStyle(ChatFormatting.DARK_GRAY));
            }
        }
    }
}
