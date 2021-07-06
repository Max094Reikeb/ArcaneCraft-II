package net.reikeb.arcanecraft.items;

import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ActionResultType;

import net.reikeb.arcanecraft.setup.ItemGroups;

public class SacrificialDagger extends SwordItem {

    public SacrificialDagger() {
        super(new IItemTier() {
            @Override
            public int getUses() {
                return 111;
            }

            @Override
            public float getSpeed() {
                return 3.5f;
            }

            @Override
            public float getAttackDamageBonus() {
                return -1f;
            }

            @Override
            public int getLevel() {
                return 1;
            }

            @Override
            public int getEnchantmentValue() {
                return 6;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Blocks.END_PORTAL, 1));
            }
        }, 3, -0.5f, new Item.Properties().tab(ItemGroups.ARCANECRAFT));
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        ActionResultType actionResultType = super.useOn(context);

        // Use on block Altar

        return actionResultType;
    }
}