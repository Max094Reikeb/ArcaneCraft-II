package net.reikeb.arcanecraft.spell;

import com.google.common.collect.ImmutableList;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.reikeb.arcanecraft.init.WandInit;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class WandObject extends ForgeRegistryEntry<WandObject> {

    private final String name;
    private final ImmutableList<Supplier<SpellInstance>> spellInstances;

    @SafeVarargs
    public WandObject(Supplier<SpellInstance>... spellInstances) {
        this(null, spellInstances);
    }

    @SafeVarargs
    public WandObject(@Nullable String name, Supplier<SpellInstance>... spellInstances) {
        this.name = name;
        this.spellInstances = ImmutableList.copyOf(spellInstances);
    }

    public static WandObject byName(String name) {
        return WandInit.WAND_REGISTRY.get().getValue(ResourceLocation.tryParse(name));
    }

    public String getName(String name) {
        if (WandInit.WAND_REGISTRY.get().getKey(this) == null) return "";
        return name + (this.name == null ? WandInit.WAND_REGISTRY.get().getKey(this).getPath() : this.name);
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
