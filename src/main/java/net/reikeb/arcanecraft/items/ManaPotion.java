package net.reikeb.arcanecraft.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        list.add(new TranslatableComponent("item.arcanecraft.mana_potion_effect").withStyle(ChatFormatting.GREEN));
        list.add(TextComponent.EMPTY);
        list.add(new TranslatableComponent("item.arcanecraft.mana_potion_used").withStyle(ChatFormatting.DARK_PURPLE));
        list.add(new TranslatableComponent("item.arcanecraft.mana_potion_result").withStyle(ChatFormatting.DARK_GREEN));
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        Player playerEntity = livingEntity instanceof Player ? (Player) livingEntity : null;
        if (playerEntity == null) return new ItemStack(Items.GLASS_BOTTLE);

        if (!level.isClientSide) {
            playerEntity.addEffect(new MobEffectInstance(PotionEffectInit.MANA.get(), 210));
        }

        if (!playerEntity.abilities.instabuild) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }
            playerEntity.inventory.add(new ItemStack(Items.GLASS_BOTTLE));
        }

        return stack;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player playerEntity, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, playerEntity, hand);
    }
}
