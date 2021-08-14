package net.reikeb.arcanecraft.potions;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.capabilities.CapabilityMana;
import net.reikeb.arcanecraft.capabilities.ManaStorage;
import net.reikeb.arcanecraft.misc.vm.Mana;

public class ManaEffect extends MobEffect {

    public ManaEffect() {
        super(MobEffectCategory.BENEFICIAL, -15545403);
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
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int level) {
        if (!(entity instanceof ServerPlayer)) return;

        ManaStorage manaStorage = entity.getCapability(CapabilityMana.MANA_CAPABILITY, null).orElseThrow(() ->
                        new IllegalStateException("Tried to get my capability but it wasn't there wtf"));
        Mana.addMaxMana(manaStorage, (ServerPlayer) entity, 0.05);
    }
}
