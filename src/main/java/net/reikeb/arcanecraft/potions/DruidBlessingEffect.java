package net.reikeb.arcanecraft.potions;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.reikeb.arcanecraft.ArcaneCraft;

public class DruidBlessingEffect extends MobEffect {

    public DruidBlessingEffect() {
        super(MobEffectCategory.BENEFICIAL, -14328056);
        ResourceLocation potionIcon = ArcaneCraft.RL("mob_effect/druid_blessing");
    }

    @Override
    public String getDescriptionId() {
        return "effect.druid_blessing";
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        Level world = entity.level;
        BlockPos pos = new BlockPos(entity.getX(), entity.getY(), entity.getZ());

        if ((Math.random() < 0.1) && (entity instanceof Player)) {
            {
                if (BoneMealItem.applyBonemeal(new ItemStack(Items.BONE_MEAL), world, pos.below(), (Player) entity)
                        || BoneMealItem.growWaterPlant(new ItemStack(Items.BONE_MEAL), world, pos.below(), null)) {
                }
            }
            if (world.getBlockState(pos.below()).getBlock() == Blocks.FARMLAND) {
                if (BoneMealItem.applyBonemeal(new ItemStack(Items.BONE_MEAL), world, pos, (Player) entity)
                        || BoneMealItem.growWaterPlant(new ItemStack(Items.BONE_MEAL), world, pos, null)) {
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
