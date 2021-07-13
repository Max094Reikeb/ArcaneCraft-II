package net.reikeb.arcanecraft.spell;

import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import net.minecraftforge.registries.ForgeRegistries;

import net.reikeb.arcanecraft.entities.*;
import net.reikeb.arcanecraft.init.*;
import net.reikeb.arcanecraft.network.NetworkManager;
import net.reikeb.arcanecraft.network.packets.WooMagicPacket;
import net.reikeb.arcanecraft.utils.Util;

import java.util.Random;

public class CastSpell {

    private final World world;
    private final PlayerEntity playerEntity;

    public CastSpell(World world, PlayerEntity playerEntity, ItemStack wand) {
        this.world = world;
        this.playerEntity = playerEntity;

        WandObject wandObject = SpellUtils.getWand(wand);
        boolean flag = false;

        if (wandObject == WandInit.EVOKER.get()) {
            ArrowEvokerEntity arrowEvokerEntity = shootEvokerArrow(0.7000000000000001f, 2.5, 0);
            arrowEvokerEntity.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;
            flag = true;
        } else if (wandObject == WandInit.FIRE.get()) {
            ArrowFireEntity arrowFireEntity = shootFireArrow(0.7000000000000001f, 3, 0);
            arrowFireEntity.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;
            flag = true;
        } else if (wandObject == WandInit.ICE.get()) {
            ArrowIceEntity arrowIceEntity = shootIceArrow(world, playerEntity, world.random, 0.7000000000000001f, 2, 0);
            arrowIceEntity.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;
            flag = true;
        } else if (wandObject == WandInit.LIFE_DRAIN.get()) {
            ArrowLifeEntity arrowLifeEntity = shootLifeArrow(world, playerEntity, world.random, 0.4f, 2, 0);
            arrowLifeEntity.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;
            flag = true;
        } else if (wandObject == WandInit.LIGHTNING.get()) {
            ArrowLightningEntity arrowLightningEntity = shootLightningArrow(world, playerEntity, world.random, 0.6f, 3, 0);
            arrowLightningEntity.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;
            flag = true;
        } else if (wandObject == WandInit.BOLT.get()) {
            doBolt();
            flag = true;
        }

        if (flag) {
            NetworkManager.INSTANCE.sendToServer(new WooMagicPacket());
        }
    }

    public void doBolt() {
        Entity target = Util.rayTrace(this.world, this.playerEntity, 25D);

        if (target != null) {
            CustomShulkerBullet bullet = new CustomShulkerBullet(this.world, this.playerEntity, target, this.playerEntity.getDirection().getAxis());
            this.world.addFreshEntity(bullet);
            this.world.playSound(null, this.playerEntity.getX(), this.playerEntity.getY(), this.playerEntity.getZ(),
                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.cast_spell")),
                    SoundCategory.PLAYERS, 1, 1f / (this.world.random.nextFloat() * 0.5f + 1) + (0.6f / 2));
            return;
        }

        DumbShulkerBullet dumbShulkerBullet = new DumbShulkerBullet(EntityType.SHULKER_BULLET, world, this.playerEntity);
        dumbShulkerBullet.shootFromRotation(this.playerEntity, this.playerEntity.xRot, this.playerEntity.yRot, 1.0F, 1.0F, 1.0F);
        dumbShulkerBullet.setDeltaMovement(MathHelper.cos((float) Math.toRadians(this.playerEntity.yRot + 90)) * this.playerEntity.getBbWidth(), 0, MathHelper.sin((float) Math.toRadians(this.playerEntity.yRot + 90)) * this.playerEntity.getBbWidth());
        this.world.addFreshEntity(dumbShulkerBullet);
        this.world.playSound(null, this.playerEntity.getX(), this.playerEntity.getY(), this.playerEntity.getZ(),
                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.cast_spell")),
                SoundCategory.PLAYERS, 1, 1f / (this.world.random.nextFloat() * 0.5f + 1) + (0.6f / 2));
    }

    public ArrowEvokerEntity shootEvokerArrow(float power, double damage, int knockback) {
        ArrowEvokerEntity arrowEvokerEntity = new ArrowEvokerEntity(EntityInit.ARROW_EVOKER_ENTITY_ENTITY_TYPE, this.playerEntity, this.world);
        arrowEvokerEntity.shoot(this.playerEntity.getLookAngle().x, this.playerEntity.getLookAngle().y, this.playerEntity.getLookAngle().z, power * 2, 0);
        arrowEvokerEntity.setSilent(true);
        arrowEvokerEntity.setCritArrow(false);
        arrowEvokerEntity.setBaseDamage(damage);
        arrowEvokerEntity.setKnockback(knockback);
        this.world.addFreshEntity(arrowEvokerEntity);
        this.world.playSound(null, this.playerEntity.getX(), this.playerEntity.getY(), this.playerEntity.getZ(),
                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell")),
                SoundCategory.PLAYERS, 1, 1f / (this.world.random.nextFloat() * 0.5f + 1) + (power / 2));
        return arrowEvokerEntity;
    }

    public ArrowFireEntity shootFireArrow(float power, double damage, int knockback) {
        ArrowFireEntity arrowFireEntity = new ArrowFireEntity(EntityInit.ARROW_FIRE_ENTITY_ENTITY_TYPE, this.playerEntity, this.world);
        arrowFireEntity.shoot(this.playerEntity.getLookAngle().x, this.playerEntity.getLookAngle().y, this.playerEntity.getLookAngle().z, power * 2, 0);
        arrowFireEntity.setSilent(true);
        arrowFireEntity.setCritArrow(false);
        arrowFireEntity.setBaseDamage(damage);
        arrowFireEntity.setKnockback(knockback);
        this.world.addFreshEntity(arrowFireEntity);
        this.world.playSound(null, this.playerEntity.getX(), this.playerEntity.getY(), this.playerEntity.getZ(),
                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                SoundCategory.PLAYERS, 1, 1f / (this.world.random.nextFloat() * 0.5f + 1) + (power / 2));
        return arrowFireEntity;
    }

    public static ArrowIceEntity shootIceArrow(World world, LivingEntity entity, Random random, float power, double damage, int knockback) {
        ArrowIceEntity arrowIceEntity = new ArrowIceEntity(EntityInit.ARROW_ICE_ENTITY_ENTITY_TYPE, entity, world);
        arrowIceEntity.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, power * 2, 0);
        arrowIceEntity.setSilent(true);
        arrowIceEntity.setCritArrow(false);
        arrowIceEntity.setBaseDamage(damage);
        arrowIceEntity.setKnockback(knockback);
        world.addFreshEntity(arrowIceEntity);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell")),
                SoundCategory.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return arrowIceEntity;
    }

    public static ArrowLifeEntity shootLifeArrow(World world, LivingEntity entity, Random random, float power, double damage, int knockback) {
        ArrowLifeEntity arrowLifeEntity = new ArrowLifeEntity(EntityInit.ARROW_LIFE_ENTITY_ENTITY_TYPE, entity, world);
        arrowLifeEntity.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, power * 2, 0);
        arrowLifeEntity.setSilent(true);
        arrowLifeEntity.setCritArrow(false);
        arrowLifeEntity.setBaseDamage(damage);
        arrowLifeEntity.setKnockback(knockback);
        world.addFreshEntity(arrowLifeEntity);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.cast_spell")),
                SoundCategory.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return arrowLifeEntity;
    }

    public static ArrowLightningEntity shootLightningArrow(World world, LivingEntity entity, Random random, float power, double damage, int knockback) {
        ArrowLightningEntity arrowLightningEntity = new ArrowLightningEntity(EntityInit.ARROW_LIGHTNING_ENTITY_ENTITY_TYPE, entity, world);
        arrowLightningEntity.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, power * 2, 0);
        arrowLightningEntity.setSilent(true);
        arrowLightningEntity.setCritArrow(false);
        arrowLightningEntity.setBaseDamage(damage);
        arrowLightningEntity.setKnockback(knockback);
        world.addFreshEntity(arrowLightningEntity);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.cast_spell")),
                SoundCategory.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return arrowLightningEntity;
    }
}