package net.reikeb.arcanecraft.curios;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface IBaubleItem {

    Type getBaubleType();

    void onWornTick(ItemStack stack, LivingEntity player);

    default void onEquipped(String identifier, LivingEntity player) { }

    enum Type {
        CURIO("curio"),
        BACK("back"),
        BELT("belt"),
        BODY("body"),
        BRACELET("bracelet"),
        CHARM("charm"),
        HEAD("head"),
        HANDS("hands"),
        NECKLACE("necklace"),
        RING("ring"),
        NONE("none");

        private final String identifier;

        Type(String identifier) {
            this.identifier = identifier;
        }

        public String getIdentifier() {
            return identifier;
        }
    }
}