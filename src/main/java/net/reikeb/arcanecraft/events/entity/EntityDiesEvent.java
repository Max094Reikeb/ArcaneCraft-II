package net.reikeb.arcanecraft.events.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.*;

import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.init.*;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID)
public class EntityDiesEvent {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        Entity attacker = event.getSource().getDirectEntity();
        boolean flag = false;

        if (!(attacker instanceof LivingEntity)) return;

        Collection<EffectInstance> effects = ((LivingEntity) attacker).getActiveEffects();
        for (EffectInstance effectInstance : effects) {
            if (effectInstance.getEffect() == PotionEffectInit.SOUL_TRAPPER.get()) {
                flag = true;
            }
        }

        if (flag && (Math.random() < 0.1)) {
            if (!entity.level.isClientSide) {
                ItemEntity soulItem = new ItemEntity(entity.level, entity.getX(), entity.getY(), entity.getZ(),
                        new ItemStack(ItemInit.SOUL.get()));
                soulItem.setPickUpDelay(10);
                entity.level.addFreshEntity(soulItem);
            }
            entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.ambient")),
                    SoundCategory.NEUTRAL, 1, 1);
        }
    }
}
