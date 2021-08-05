package net.reikeb.arcanecraft.capabilities;

public interface IManaStorage {

    /**
     * Returns the amount of current mana currently stored.
     */
    int getMana();

    /**
     * Returns the amount of maximum mana currently stored.
     */
    double getMaxMana();

    /**
     * Sets the amount of current mana to a specific number.
     * @param mana The amount of current mana to set.
     */
    void setMana(int mana);

    /**
     * Sets the amount of manimum mana to a specific number.
     * @param maxMana The amount of maximum mana to set.
     */
    void setMaxMana(double maxMana);
}
