package net.reikeb.arcanecraft.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.*;

import net.minecraftforge.registries.ForgeRegistries;

import net.reikeb.arcanecraft.init.ItemInit;
import net.reikeb.arcanecraft.setup.ItemGroups;

public class CrystalAxe extends AxeItem {

    public CrystalAxe() {
        super(new IItemTier() {
            @Override
            public int getUses() {
                return 187;
            }

            @Override
            public float getSpeed() {
                return 5.5f;
            }

            @Override
            public float getAttackDamageBonus() {
                return 6.5f;
            }

            @Override
            public int getLevel() {
                return 1;
            }

            @Override
            public int getEnchantmentValue() {
                return 2;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(ItemInit.CRYSTAL.get(), 1));
            }
        }, 1, -3f, new Item.Properties().tab(ItemGroups.ARCANECRAFT));
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
        boolean action = super.hurtEnemy(itemstack, entity, sourceentity);
        SoundEvent grassSound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.grass.break"));
        if (grassSound == null) return false;
        entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                grassSound, SoundCategory.NEUTRAL, (float) 100, (float) 100);
        return action;
    }
}
