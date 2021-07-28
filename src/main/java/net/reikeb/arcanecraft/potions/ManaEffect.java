package net.reikeb.arcanecraft.potions;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.*;
import net.minecraft.potion.*;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.network.PacketDistributor;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.capabilities.ManaManager;
import net.reikeb.arcanecraft.network.NetworkManager;
import net.reikeb.arcanecraft.network.packets.MaxManaPacket;

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
        if (!(entity instanceof ServerPlayerEntity)) return;

        entity.getCapability(ManaManager.MANA_CAPABILITY, null).ifPresent(cap -> {
            cap.setMaxMana((cap.getMaxMana() + 0.05));
            NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                    (ServerPlayerEntity) entity), new MaxManaPacket((int) (cap.getMaxMana() + 0.05)));
        });
    }
}
