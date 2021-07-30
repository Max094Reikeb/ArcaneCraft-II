package net.reikeb.arcanecraft.potions;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import net.reikeb.arcanecraft.ArcaneCraft;

public class CharredStrikeEffect extends MobEffect {

    public CharredStrikeEffect() {
        super(MobEffectCategory.BENEFICIAL, -3124707);
        ResourceLocation potionIcon = ArcaneCraft.RL("mob_effect/charred_strike");
    }

    @Override
    public String getDescriptionId() {
        return "effect.charred_strike";
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
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
