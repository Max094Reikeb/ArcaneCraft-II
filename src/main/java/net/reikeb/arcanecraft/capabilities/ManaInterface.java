package net.reikeb.arcanecraft.capabilities;

import net.minecraft.nbt.CompoundNBT;

import net.minecraftforge.common.util.INBTSerializable;

public interface ManaInterface extends INBTSerializable<CompoundNBT> {

    double getMaxMana();

    void setMaxMana(double maxMana);
}
