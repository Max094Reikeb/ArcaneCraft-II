package net.reikeb.arcanecraft.potions;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import net.reikeb.arcanecraft.ArcaneCraft;

public class SoulTrapperEffect extends MobEffect {

    public SoulTrapperEffect() {
        super(MobEffectCategory.BENEFICIAL, -12034723);
        ResourceLocation potionIcon = ArcaneCraft.RL("mob_effect/soul_trapper");
    }

    @Override
    public String getDescriptionId() {
        return "effect.soul_trapper";
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
