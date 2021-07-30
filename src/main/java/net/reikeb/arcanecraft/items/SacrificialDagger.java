package net.reikeb.arcanecraft.items;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;

import net.reikeb.arcanecraft.init.BlockInit;
import net.reikeb.arcanecraft.misc.vm.CastRitual;
import net.reikeb.arcanecraft.setup.ItemGroups;
import net.reikeb.arcanecraft.tileentities.TileAltar;

public class SacrificialDagger extends SwordItem {

    public SacrificialDagger() {
        super(new Tier() {
            @Override
            public int getUses() {
                return 111;
            }

            @Override
            public float getSpeed() {
                return 3.5f;
            }

            @Override
            public float getAttackDamageBonus() {
                return -1f;
            }

            @Override
            public int getLevel() {
                return 1;
            }

            @Override
            public int getEnchantmentValue() {
                return 6;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Blocks.END_PORTAL, 1));
            }
        }, 3, -0.5f, new Item.Properties().tab(ItemGroups.ARCANECRAFT));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult actionResultType = super.useOn(context);

        if (context.getLevel().isClientSide) return actionResultType;
        if (context.getPlayer() == null) return actionResultType;

        if ((context.getLevel().getBlockState(context.getClickedPos()).getBlock() == BlockInit.ALTAR.get())
                && (context.getPlayer().isShiftKeyDown())) {
            BlockEntity tile = context.getLevel().getBlockEntity(context.getClickedPos());
            if (tile instanceof TileAltar) {
                new CastRitual(context.getLevel(), context.getClickedPos(), (TileAltar) tile, context.getPlayer(), true);
            }
        }

        return actionResultType;
    }
}
