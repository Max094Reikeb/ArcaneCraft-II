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
     * Returns a number between 0 and 1 that defines the progress of mana.
     */
    float getManaProgress();

    /**
     * Sets the amount of current mana to a specific number.
     *
     * @param mana The amount of current mana to set.
     */
    void setMana(int mana);

    /**
     * Sets the amount of manimum mana to a specific number.
     *
     * @param maxMana The amount of maximum mana to set.
     */
    void setMaxMana(double maxMana);

    /**
     * Sets the amount of mana progress to a specific number.
     *
     * @param manaProgress the amount of mana progress to set.
     */
    void setManaProgress(float manaProgress);
}
