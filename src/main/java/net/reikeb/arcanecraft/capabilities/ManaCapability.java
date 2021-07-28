package net.reikeb.arcanecraft.capabilities;

import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;

public class ManaCapability implements ManaInterface {

    private static final String INFO_NBT_KEY = "nfo";
    private double maxMana;

    public ManaCapability() {
        this(0);
    }

    public ManaCapability(final double maxMana) {
        this.maxMana = maxMana;
    }

    @Override
    public double getMaxMana() {
        return this.maxMana;
    }

    @Override
    public void setMaxMana(double maxMana) {
        this.maxMana = maxMana;
    }

    @Nonnull
    @Override
    public CompoundNBT serializeNBT() {
        final CompoundNBT nbt = new CompoundNBT();
        nbt.putDouble(INFO_NBT_KEY, maxMana);
        return nbt;
    }

    @Override
    public void deserializeNBT(@Nonnull final CompoundNBT nbt) {
        this.setMaxMana(nbt.getDouble(INFO_NBT_KEY));
    }
}
