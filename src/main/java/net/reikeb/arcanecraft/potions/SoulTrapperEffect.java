package net.reikeb.arcanecraft.potions;

import net.minecraft.potion.*;
import net.minecraft.util.ResourceLocation;

import net.reikeb.arcanecraft.ArcaneCraft;

public class SoulTrapperEffect extends Effect {

    public SoulTrapperEffect() {
        super(EffectType.BENEFICIAL, -12034723);
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
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
