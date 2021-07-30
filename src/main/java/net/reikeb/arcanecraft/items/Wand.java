package net.reikeb.arcanecraft.items;

import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import net.reikeb.arcanecraft.init.WandInit;
import net.reikeb.arcanecraft.setup.ItemGroups;
import net.reikeb.arcanecraft.spell.CastSpell;
import net.reikeb.arcanecraft.spell.SpellUtils;
import net.reikeb.arcanecraft.spell.WandObject;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class Wand extends ProjectileWeaponItem {

    public Wand() {
        super(new Properties()
                .stacksTo(1)
                .durability(421)
                .tab(ItemGroups.ARCANECRAFT));
    }

    public ItemStack getDefaultInstance() {
        return SpellUtils.setWand(super.getDefaultInstance(), WandInit.EMPTY.get());
    }

    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> text, TooltipFlag flag) {
        SpellUtils.addWandTooltip(stack, text);
    }

    public boolean isFoil(ItemStack stack) {
        return super.isFoil(stack) || !SpellUtils.getSpell(stack).isEmpty();
    }

    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> stacks) {
        if (this.allowdedIn(group)) {
            for (WandObject wandObject : WandInit.WAND_REGISTRY.get()) {
                stacks.add(SpellUtils.setWand(new ItemStack(this), wandObject));
            }
        }
    }

    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ARROW_ONLY;
    }

    public int getDefaultProjectileRange() {
        return 8;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        playerIn.startUsingItem(handIn);
        return InteractionResultHolder.success(itemstack);
    }

    public void onUseTick(Level world, LivingEntity entity, ItemStack stack, int power) {
        if (!world.isClientSide && entity instanceof ServerPlayer) {
            ServerPlayer playerEntity = (ServerPlayer) entity;
            stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(playerEntity.getUsedItemHand()));
            new CastSpell(world, (Player) entity, stack);
            playerEntity.stopUsingItem();
        }
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }
}
