package net.reikeb.arcanecraft.capabilities;

import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nonnull;

public class ManaCapability implements ManaInterface {

    private static final String INFO_NBT_KEY = "nfo";
    private static final String MANA_NBT_KEY = "mana";
    private int mana;
    private double maxMana;

    public ManaCapability() {
        this(0, 0);
    }

    public ManaCapability(final int mana, final double maxMana) {
        this.mana = mana;
        this.maxMana = maxMana;
    }

    @Override
    public int getMana() {
        return this.mana;
    }

    @Override
    public double getMaxMana() {
        return this.maxMana;
    }

    @Override
    public void setMana(int mana) {
        this.mana = mana;
    }

    @Override
    public void setMaxMana(double maxMana) {
        this.maxMana = maxMana;
    }

    @Nonnull
    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag nbt = new CompoundTag();
        nbt.putInt(MANA_NBT_KEY, this.getMana());
        nbt.putDouble(INFO_NBT_KEY, this.getMaxMana());
        return nbt;
    }

    @Override
    public void deserializeNBT(@Nonnull final CompoundTag nbt) {
        this.setMana(nbt.getInt(MANA_NBT_KEY));
        this.setMaxMana(nbt.getDouble(INFO_NBT_KEY));
    }
}
