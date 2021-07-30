package net.reikeb.arcanecraft.spell;

import com.google.common.collect.ImmutableList;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;

import net.minecraftforge.registries.ForgeRegistryEntry;

import net.reikeb.arcanecraft.init.ScrollInit;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class ScrollObject extends ForgeRegistryEntry<ScrollObject> {

    private final String name;
    private final ImmutableList<Supplier<SpellInstance>> spellInstances;

    @SafeVarargs
    public ScrollObject(Supplier<SpellInstance>... spellInstances) {
        this(null, spellInstances);
    }

    @SafeVarargs
    public ScrollObject(@Nullable String name, Supplier<SpellInstance>... spellInstances) {
        this.name = name;
        this.spellInstances = ImmutableList.copyOf(spellInstances);
    }

    public static ScrollObject byName(String name) {
        return ScrollInit.SCROLL_REGISTRY.get().getValue(ResourceLocation.tryParse(name));
    }

    public boolean isIn(Tag<ScrollObject> tag) {
        return tag.contains(this);
    }

    public String getName(String name) {
        if (ScrollInit.SCROLL_REGISTRY.get().getKey(this) == null) return "";
        return name + (this.name == null ? ScrollInit.SCROLL_REGISTRY.get().getKey(this).getPath() : this.name);
    }

    public List<Supplier<SpellInstance>> getSpells() {
        return this.spellInstances;
    }

    public boolean hasInstantAlterations() {
        if (!this.spellInstances.isEmpty()) {
            for (Supplier<SpellInstance> spellInstance : this.spellInstances) {

                if (spellInstance.get().getSpell().isInstantenous()) {
                    return true;
                }
            }
        }
        return false;
    }
}
