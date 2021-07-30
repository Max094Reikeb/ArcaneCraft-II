package net.reikeb.arcanecraft.capabilities;

import net.minecraft.nbt.CompoundTag;

import net.minecraftforge.common.util.INBTSerializable;

public interface ManaInterface extends INBTSerializable<CompoundTag> {

    int getMana();
    double getMaxMana();

    void setMana(int mana);
    void setMaxMana(double maxMana);
}
