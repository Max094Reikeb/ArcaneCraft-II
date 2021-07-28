package net.reikeb.arcanecraft.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import net.minecraftforge.api.distmarker.*;

import net.reikeb.arcanecraft.init.PotionEffectInit;
import net.reikeb.arcanecraft.setup.ItemGroups;

import javax.annotation.Nullable;
import java.util.List;

public class ManaPotion extends Item {

    public ManaPotion() {
        super(new Properties()
                .stacksTo(1)
                .tab(ItemGroups.ARCANECRAFT));
    }

    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.DRINK;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
        list.add(new TranslationTextComponent("item.arcanecraft.mana_potion_effect").withStyle(TextFormatting.GREEN));
        list.add(StringTextComponent.EMPTY);
        list.add(new TranslationTextComponent("item.arcanecraft.mana_potion_used").withStyle(TextFormatting.DARK_PURPLE));
        list.add(new TranslationTextComponent("item.arcanecraft.mana_potion_result").withStyle(TextFormatting.DARK_GREEN));
    }

    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity livingEntity) {
        PlayerEntity playerEntity = livingEntity instanceof PlayerEntity ? (PlayerEntity) livingEntity : null;
        if (playerEntity == null) return new ItemStack(Items.GLASS_BOTTLE);

        if (!world.isClientSide) {
            playerEntity.addEffect(new EffectInstance(PotionEffectInit.MANA.get(), 210));
        }

        if (!playerEntity.abilities.instabuild) {
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }
            playerEntity.inventory.add(new ItemStack(Items.GLASS_BOTTLE));
        }

        return stack;
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        return DrinkHelper.useDrink(world, playerEntity, hand);
    }
}
