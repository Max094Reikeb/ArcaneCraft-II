package net.reikeb.arcanecraft.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.reikeb.arcanecraft.Util;
import net.reikeb.arcanecraft.setup.ItemGroups;

public class AmethystSword extends SwordItem {

    public AmethystSword() {
        super(new Tier() {
            @Override
            public int getUses() {
                return 187;
            }

            @Override
            public float getSpeed() {
                return 4f;
            }

            @Override
            public float getAttackDamageBonus() {
                return 1.5f;
            }

            @Override
            public int getLevel() {
                return 2;
            }

            @Override
            public int getEnchantmentValue() {
                return 33;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.AMETHYST_SHARD, 1));
            }
        }, 3, -2.4f, new Item.Properties().tab(ItemGroups.ARCANECRAFT));
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
        return Util.amethystToolHurtEnemy(super.hurtEnemy(itemstack, entity, sourceentity), entity);
    }
}
