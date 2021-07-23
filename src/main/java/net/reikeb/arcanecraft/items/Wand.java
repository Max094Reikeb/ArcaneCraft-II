package net.reikeb.arcanecraft.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import net.reikeb.arcanecraft.init.WandInit;
import net.reikeb.arcanecraft.setup.ItemGroups;
import net.reikeb.arcanecraft.spell.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class Wand extends ShootableItem {

    public Wand() {
        super(new Properties()
                .stacksTo(1)
                .durability(421)
                .tab(ItemGroups.ARCANECRAFT));
    }

    public ItemStack getDefaultInstance() {
        return SpellUtils.setWand(super.getDefaultInstance(), WandInit.EMPTY.get());
    }

    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> text, ITooltipFlag flag) {
        SpellUtils.addWandTooltip(stack, text);
    }

    public boolean isFoil(ItemStack stack) {
        return super.isFoil(stack) || !SpellUtils.getSpell(stack).isEmpty();
    }

    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> stacks) {
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
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        playerIn.startUsingItem(handIn);
        return ActionResult.success(itemstack);
    }

    public void onUseTick(World world, LivingEntity entity, ItemStack stack, int power) {
        if (!world.isClientSide && entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity playerEntity = (ServerPlayerEntity) entity;
            stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(playerEntity.getUsedItemHand()));
            new CastSpell(world, (PlayerEntity) entity, stack);
            playerEntity.stopUsingItem();
        }
    }

    /*
    public void releaseUsing(ItemStack stack, World world, LivingEntity entity, int power) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) entity;
            if (playerEntity.getUseItemRemainingTicks() < 71984) {
                if (!playerEntity.getCommandSenderWorld().isClientSide()) {
                    CastSpell.doCastSpell(playerEntity, world, stack);
                }
            }
        }
    }
     */

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.BOW;
    }
}
