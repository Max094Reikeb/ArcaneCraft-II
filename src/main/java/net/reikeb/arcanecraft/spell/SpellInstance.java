package net.reikeb.arcanecraft.spell;

import net.minecraft.nbt.CompoundTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpellInstance {

    private static final Logger LOGGER = LogManager.getLogger();
    private final Spell spell;
    private final int mana;

    public SpellInstance(Spell spell) {
        this(spell, 0);
    }

    public SpellInstance(Spell spell, int mana) {
        this.spell = spell;
        this.mana = mana;
    }

    public SpellInstance(SpellInstance spellInstance) {
        this.spell = spellInstance.spell;
        this.mana = spellInstance.mana;
    }

    public Spell getSpell() {
        return this.spell == null ? null : this.spell.delegate.get();
    }

    public int getMana() {
        return this.mana;
    }

    public String getDescriptionId() {
        return this.spell.getDescriptionId();
    }

    public String toString() {
        String s;
        s = this.getDescriptionId();
        return s;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof SpellInstance)) {
            return false;
        } else {
            SpellInstance spellInstance = (SpellInstance) obj;
            return this.mana == spellInstance.mana && this.spell.equals(spellInstance.spell);
        }
    }

    public static SpellInstance load(CompoundTag nbt) {
        Spell spell = Spell.byId(nbt.getString("Id"));
        return spell == null ? null : loadSpecifiedEffect(spell, nbt);
    }

    public CompoundTag save(CompoundTag nbt) {
        nbt.putString("Id", Spell.getId(this.getSpell()));
        nbt.putInt("Mana", this.getMana());
        return nbt;
    }

    private static SpellInstance loadSpecifiedEffect(Spell spell, CompoundTag nbt) {
        int m = nbt.getInt("Mana");
        return new SpellInstance(spell, m);
    }
}
