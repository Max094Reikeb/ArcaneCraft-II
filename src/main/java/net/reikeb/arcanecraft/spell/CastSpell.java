package net.reikeb.arcanecraft.spell;

import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

import net.minecraftforge.registries.ForgeRegistries;

import net.reikeb.arcanecraft.entities.*;
import net.reikeb.arcanecraft.init.*;
import net.reikeb.arcanecraft.network.NetworkManager;
import net.reikeb.arcanecraft.network.packets.WooMagicPacket;

import java.util.Random;

public class CastSpell {

    public static void doCastSpell(PlayerEntity playerEntity, World world, ItemStack itemStack) {
        NetworkManager.INSTANCE.sendToServer(new WooMagicPacket());

        if (SpellUtils.getWand(itemStack) == WandInit.EVOKER.get()) {
            doEvoker(world, playerEntity);
        } else if (SpellUtils.getWand(itemStack) == WandInit.FIRE.get()) {
            doFire(world, playerEntity);
        } else if (SpellUtils.getWand(itemStack) == WandInit.ICE.get()) {
            doIce(world, playerEntity);
        }
    }

    public static void doEvoker(World world, PlayerEntity playerEntity) {
        ArrowEvokerEntity arrowEvokerEntity = shootEvokerArrow(world, playerEntity, world.random, 0.7000000000000001f, 2.5, 0);
        arrowEvokerEntity.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;
    }

    public static void doFire(World world, PlayerEntity playerEntity) {
        ArrowFireEntity arrowFireEntity = shootFireArrow(world, playerEntity, world.random, 0.7000000000000001f, 3, 0);
        arrowFireEntity.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;
    }

    public static void doIce(World world, PlayerEntity playerEntity) {

    }

    public static ArrowEvokerEntity shootEvokerArrow(World world, LivingEntity entity, Random random, float power, double damage, int knockback) {
        ArrowEvokerEntity arrowEvokerEntity = new ArrowEvokerEntity(EntityInit.ARROW_EVOKER_ENTITY_ENTITY_TYPE, entity, world);
        arrowEvokerEntity.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, power * 2, 0);
        arrowEvokerEntity.setSilent(true);
        arrowEvokerEntity.setCritArrow(false);
        arrowEvokerEntity.setBaseDamage(damage);
        arrowEvokerEntity.setKnockback(knockback);
        world.addFreshEntity(arrowEvokerEntity);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell")),
                SoundCategory.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return arrowEvokerEntity;
    }

    public static ArrowFireEntity shootFireArrow(World world, LivingEntity entity, Random random, float power, double damage, int knockback) {
        ArrowFireEntity arrowFireEntity = new ArrowFireEntity(EntityInit.ARROW_FIRE_ENTITY_ENTITY_TYPE, entity, world);
        arrowFireEntity.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, power * 2, 0);
        arrowFireEntity.setSilent(true);
        arrowFireEntity.setCritArrow(false);
        arrowFireEntity.setBaseDamage(damage);
        arrowFireEntity.setKnockback(knockback);
        world.addFreshEntity(arrowFireEntity);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                SoundCategory.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return arrowFireEntity;
    }
}