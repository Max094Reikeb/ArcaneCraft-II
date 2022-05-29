package net.reikeb.arcanecraft.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.reikeb.arcanecraft.entities.AmethystArrow;
import net.reikeb.arcanecraft.setup.ItemGroups;

public class AmethystArrowItem extends ArrowItem {

    public AmethystArrowItem() {
        super(new Item.Properties().tab(ItemGroups.ARCANECRAFT));
    }

    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity entity) {
        return new AmethystArrow(level, entity);
    }
}
