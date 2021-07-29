package net.reikeb.arcanecraft.capabilities;

import net.minecraft.nbt.CompoundNBT;

import net.minecraftforge.common.util.INBTSerializable;

public interface ManaInterface extends INBTSerializable<CompoundNBT> {

    int getMana();
    double getMaxMana();

    void setMana(int mana);
    void setMaxMana(double maxMana);
}
