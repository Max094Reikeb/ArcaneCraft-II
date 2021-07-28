package net.reikeb.arcanecraft.potions;

import com.google.common.util.concurrent.AtomicDouble;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.*;
import net.minecraft.util.ResourceLocation;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.capabilities.ManaManager;

public class ManaEffect extends Effect {

    public ManaEffect() {
        super(EffectType.BENEFICIAL, -15545403);
        ResourceLocation potionIcon = ArcaneCraft.RL("mob_effect/mana");
    }

    @Override
    public String getDescriptionId() {
        return "effect.mana";
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

    @Override
    public void applyEffectTick(LivingEntity entity, int level) {
        AtomicDouble entityManaMax = new AtomicDouble();
        entity.getCapability(ManaManager.MANA_CAPABILITY, null).ifPresent(cap ->
                entityManaMax.set(cap.getMaxMana()));

        entity.getCapability(ManaManager.MANA_CAPABILITY, null).ifPresent(cap ->
                cap.setMaxMana((entityManaMax.get() + 0.05)));
    }
}
