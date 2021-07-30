package net.reikeb.arcanecraft.items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import net.minecraftforge.registries.ForgeRegistries;

import net.reikeb.arcanecraft.init.ItemInit;
import net.reikeb.arcanecraft.setup.ItemGroups;

public class CrystalShovel extends ShovelItem {

    public CrystalShovel() {
        super(new Tier() {
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
                return 0f;
            }

            @Override
            public int getLevel() {
                return 1;
            }

            @Override
            public int getEnchantmentValue() {
                return 33;
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
                grassSound, SoundSource.NEUTRAL, (float) 100, (float) 100);
        return action;
    }
}
