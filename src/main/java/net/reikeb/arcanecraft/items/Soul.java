package net.reikeb.arcanecraft.items;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

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
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (!(entity instanceof ServerPlayer)) return;
        Advancement advancement = ((ServerPlayer) entity).server.getAdvancements().getAdvancement(new ResourceLocation("arcanecraft:your_soul_is_mine"));
        if (advancement == null) System.out.println("Advancement 'Your soul is mine!' seems to be null");
        if (advancement == null) return;
        AdvancementProgress advancementProgress = ((ServerPlayer) entity).getAdvancements().getOrStartProgress(advancement);
        if (!advancementProgress.isDone()) {
            for (String criteria : advancementProgress.getRemainingCriteria()) {
                ((ServerPlayer) entity).getAdvancements().award(advancement, criteria);
            }
        }
    }
}
