package net.reikeb.arcanecraft.events.entity;

import net.minecraft.entity.*;
import net.minecraft.potion.EffectInstance;

import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.init.PotionEffectInit;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID)
public class EntityAttackedEvent {

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        Entity entity = event.getEntity();
        Entity attacker = event.getSource().getDirectEntity();
        boolean flag = false;

        if (!(attacker instanceof LivingEntity)) return;

        Collection<EffectInstance> effects = ((LivingEntity) attacker).getActiveEffects();
        for (EffectInstance effectInstance : effects) {
            if (effectInstance.getEffect() == PotionEffectInit.CHARRED_STRIKE.get()) {
                flag = true;
            }
        }

        if (flag) {
            entity.setSecondsOnFire(15);
        }
    }
}
