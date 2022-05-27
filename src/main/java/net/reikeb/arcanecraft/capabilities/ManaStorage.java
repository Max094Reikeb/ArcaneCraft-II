package net.reikeb.arcanecraft.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;

public class ManaStorage implements IManaStorage, INBTSerializable<CompoundTag> {

    private static final String MAX_MANA_NBT_KEY = "maxMana";
    private static final String MANA_NBT_KEY = "mana";
    private static final String MANA_PROGRESS_NBT_KEY = "manaProgress";

    private int mana;
    private double maxMana;
    private float manaProgress;

    public ManaStorage() {
        this(0, 0, 0);
    }

    public ManaStorage(final int mana, final double maxMana, final float manaProgress) {
        this.mana = mana;
        this.maxMana = maxMana;
        this.manaProgress = manaProgress;
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
    public float getManaProgress() {
        return this.manaProgress;
    }

    @Override
    public void setMana(int mana) {
        this.mana = mana;
    }

    @Override
    public void setMaxMana(double maxMana) {
        this.maxMana = maxMana;
    }

    @Override
    public void setManaProgress(float manaProgress) {
        this.manaProgress = manaProgress;
    }

    @Nonnull
    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag nbt = new CompoundTag();
        nbt.putInt(MANA_NBT_KEY, this.getMana());
        nbt.putDouble(MAX_MANA_NBT_KEY, this.getMaxMana());
        nbt.putFloat(MANA_PROGRESS_NBT_KEY, this.getManaProgress());
        return nbt;
    }

    @Override
    public void deserializeNBT(@Nonnull final CompoundTag nbt) {
        this.setMana(nbt.getInt(MANA_NBT_KEY));
        this.setMaxMana(nbt.getDouble(MAX_MANA_NBT_KEY));
        this.setManaProgress(nbt.getFloat(MANA_PROGRESS_NBT_KEY));
    }
}
