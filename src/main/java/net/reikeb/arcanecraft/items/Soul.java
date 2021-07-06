package net.reikeb.arcanecraft.items;

import net.minecraft.advancements.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import net.reikeb.arcanecraft.setup.ItemGroups;

public class Soul extends Item {

    public Soul() {
        super(new Properties()
                .stacksTo(64)
                .tab(ItemGroups.ARCANECRAFT));
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 0;
    }

    @Override
    public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
        return 1F;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (!(entity instanceof ServerPlayerEntity)) return;
        Advancement advancement = ((ServerPlayerEntity) entity).server.getAdvancements().getAdvancement(new ResourceLocation("arcanecraft:your_soul_is_mine"));
        if (advancement == null) System.out.println("Advancement 'Your soul is mine!' seems to be null");
        if (advancement == null) return;
        AdvancementProgress advancementProgress = ((ServerPlayerEntity) entity).getAdvancements().getOrStartProgress(advancement);
        if (!advancementProgress.isDone()) {
            for (String criteria : advancementProgress.getRemainingCriteria()) {
                ((ServerPlayerEntity) entity).getAdvancements().award(advancement, criteria);
            }
        }
    }
}
