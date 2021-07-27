package net.reikeb.arcanecraft.potions;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.reikeb.arcanecraft.ArcaneCraft;

public class DruidBlessingEffect extends Effect {

    public DruidBlessingEffect() {
        super(EffectType.BENEFICIAL, -14328056);
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
    public boolean shouldRenderInvText(EffectInstance effect) {
        return true;
    }

    @Override
    public boolean shouldRender(EffectInstance effect) {
        return true;
    }

    @Override
    public boolean shouldRenderHUD(EffectInstance effect) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        World world = entity.level;
        BlockPos pos = new BlockPos(entity.getX(), entity.getY(), entity.getZ());

        if ((Math.random() < 0.1) && (entity instanceof PlayerEntity)) {
            {
                if (BoneMealItem.applyBonemeal(new ItemStack(Items.BONE_MEAL), world, pos.below(), (PlayerEntity) entity)
                        || BoneMealItem.growWaterPlant(new ItemStack(Items.BONE_MEAL), world, pos.below(), null)) {
                }
            }
            if (world.getBlockState(pos.below()).getBlock() == Blocks.FARMLAND) {
                if (BoneMealItem.applyBonemeal(new ItemStack(Items.BONE_MEAL), world, pos, (PlayerEntity) entity)
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
