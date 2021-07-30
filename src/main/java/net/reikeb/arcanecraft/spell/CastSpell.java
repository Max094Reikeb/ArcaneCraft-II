package net.reikeb.arcanecraft.spell;

import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import net.minecraftforge.fmllegacy.network.NetworkHooks;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import net.reikeb.arcanecraft.capabilities.ManaManager;
import net.reikeb.arcanecraft.entities.*;
import net.reikeb.arcanecraft.init.EntityInit;
import net.reikeb.arcanecraft.init.WandInit;
import net.reikeb.arcanecraft.network.NetworkManager;
import net.reikeb.arcanecraft.network.packets.CurrentManaPacket;
import net.reikeb.arcanecraft.network.packets.WooMagicPacket;
import net.reikeb.arcanecraft.utils.Util;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class CastSpell {

    private final Level world;
    private final Player playerEntity;

    public CastSpell(Level world, Player playerEntity, ItemStack wand) {
        this.world = world;
        this.playerEntity = playerEntity;

        WandObject wandObject = SpellUtils.getWand(wand);
        boolean flag = false;
        AtomicInteger playerMana = new AtomicInteger();
        int spellMana = 0;

        List<SpellInstance> spells = SpellUtils.getSpell(wand);
        if (!spells.isEmpty()) {
            for (SpellInstance spellInstance : spells) {
                spellMana = spellInstance.getMana();
            }
        }

        playerEntity.getCapability(ManaManager.MANA_CAPABILITY, null).ifPresent(cap -> {
            playerMana.set(cap.getMana());
        });

        if ((playerMana.get() <= spellMana) && (!playerEntity.isCreative())) return;

        if (wandObject == WandInit.EVOKER.get()) {
            ArrowEvokerEntity arrowEvokerEntity = shootEvokerArrow(0.7000000000000001f, 2.5, 0);
            arrowEvokerEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
            flag = true;

        } else if (wandObject == WandInit.FIRE.get()) {
            ArrowFireEntity arrowFireEntity = shootFireArrow(0.7000000000000001f, 3, 0);
            arrowFireEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
            flag = true;

        } else if (wandObject == WandInit.ICE.get()) {
            ArrowIceEntity arrowIceEntity = shootIceArrow(world, playerEntity, world.random, 0.7000000000000001f, 2, 0);
            arrowIceEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
            flag = true;

        } else if (wandObject == WandInit.LIFE_DRAIN.get()) {
            ArrowLifeEntity arrowLifeEntity = shootLifeArrow(world, playerEntity, world.random, 0.4f, 2, 0);
            arrowLifeEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
            flag = true;

        } else if (wandObject == WandInit.LIGHTNING.get()) {
            ArrowLightningEntity arrowLightningEntity = shootLightningArrow(world, playerEntity, world.random, 0.6f, 3, 0);
            arrowLightningEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
            flag = true;

        } else if (wandObject == WandInit.BOLT.get()) {
            doBolt();
            flag = true;

        }

        if (flag) {
            NetworkManager.INSTANCE.sendToServer(new WooMagicPacket());
            if (!playerEntity.isCreative()) {
                int finalSpellMana = spellMana;
                playerEntity.getCapability(ManaManager.MANA_CAPABILITY, null).ifPresent(cap -> {
                    cap.setMana(playerMana.get() - finalSpellMana);
                    NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                            (ServerPlayer) playerEntity), new CurrentManaPacket(cap.getMana() - finalSpellMana));
                });
            }
        }
    }

    public void doBolt() {
        Entity target = Util.rayTrace(this.world, this.playerEntity, 25D);

        if (target != null) {
            ShulkerBullet smartBullet = new SmartShulkerBullet(this.world, this.playerEntity, target, this.playerEntity.getDirection().getAxis());
            smartBullet.setPos(this.playerEntity.getX() + this.playerEntity.getViewVector(1.0F).x, this.playerEntity.getY() + 1.35, this.playerEntity.getZ() + this.playerEntity.getViewVector(1.0F).z);
            this.world.addFreshEntity(smartBullet);
            return;
        }

        ShulkerBullet dumbBullet = new ShulkerBullet(EntityType.SHULKER_BULLET, this.world) {
            @Override
            public void selectNextMoveDirection(@Nullable Direction.Axis axis) {
            }

            @Override
            protected void onHit(HitResult result) {
                HitResult.Type raytraceresult$type = result.getType();
                if (raytraceresult$type == HitResult.Type.ENTITY) {
                    this.onHitEntity((EntityHitResult) result);
                } else if (raytraceresult$type == HitResult.Type.BLOCK) {
                    this.onHitBlock((BlockHitResult) result);
                }
            }

            @Override
            public void tick() {
                super.tick();
                if (this.getOwner() != null && this.distanceTo(this.getOwner()) >= 40) {
                    this.remove(false);
                }
            }

            @Override
            public Packet<?> getAddEntityPacket() {
                return NetworkHooks.getEntitySpawningPacket(this);
            }

            @Override
            protected void onHitEntity(EntityHitResult result) {
                Entity entity = result.getEntity();
                Entity entity1 = this.getOwner();
                LivingEntity livingentity = entity1 instanceof LivingEntity ? (LivingEntity) entity1 : null;

                if (result.getEntity() == this.getOwner()) return;
                if (livingentity == null) return;

                if (entity.hurt(DamageSource.indirectMobAttack(this, livingentity).setProjectile(), 11.0F)) {
                    this.doEnchantDamageEffects(livingentity, entity);
                    this.remove(false);
                }
            }
        };

        dumbBullet.setNoGravity(true);
        dumbBullet.setOwner(this.playerEntity);
        dumbBullet.setPos(this.playerEntity.getX() + this.playerEntity.getViewVector(1.0F).x, this.playerEntity.getY() + 1.35, this.playerEntity.getZ() + this.playerEntity.getViewVector(1.0F).z);
        dumbBullet.shootFromRotation(this.playerEntity, this.playerEntity.xRot, this.playerEntity.yRot, 1.3F, 1.3F, 1.3F);
        this.world.addFreshEntity(dumbBullet);
        this.world.playSound(null, this.playerEntity.getX(), this.playerEntity.getY(), this.playerEntity.getZ(),
                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.cast_spell")),
                SoundSource.PLAYERS, 1, 1f / (this.world.random.nextFloat() * 0.5f + 1) + (0.6f / 2));
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
                SoundSource.PLAYERS, 1, 1f / (this.world.random.nextFloat() * 0.5f + 1) + (power / 2));
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
                SoundSource.PLAYERS, 1, 1f / (this.world.random.nextFloat() * 0.5f + 1) + (power / 2));
        return arrowFireEntity;
    }

    public static ArrowIceEntity shootIceArrow(Level world, LivingEntity entity, Random random, float power, double damage, int knockback) {
        ArrowIceEntity arrowIceEntity = new ArrowIceEntity(EntityInit.ARROW_ICE_ENTITY_ENTITY_TYPE, entity, world);
        arrowIceEntity.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, power * 2, 0);
        arrowIceEntity.setSilent(true);
        arrowIceEntity.setCritArrow(false);
        arrowIceEntity.setBaseDamage(damage);
        arrowIceEntity.setKnockback(knockback);
        world.addFreshEntity(arrowIceEntity);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell")),
                SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return arrowIceEntity;
    }

    public static ArrowLifeEntity shootLifeArrow(Level world, LivingEntity entity, Random random, float power, double damage, int knockback) {
        ArrowLifeEntity arrowLifeEntity = new ArrowLifeEntity(EntityInit.ARROW_LIFE_ENTITY_ENTITY_TYPE, entity, world);
        arrowLifeEntity.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, power * 2, 0);
        arrowLifeEntity.setSilent(true);
        arrowLifeEntity.setCritArrow(false);
        arrowLifeEntity.setBaseDamage(damage);
        arrowLifeEntity.setKnockback(knockback);
        world.addFreshEntity(arrowLifeEntity);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.cast_spell")),
                SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return arrowLifeEntity;
    }

    public static ArrowLightningEntity shootLightningArrow(Level world, LivingEntity entity, Random random, float power, double damage, int knockback) {
        ArrowLightningEntity arrowLightningEntity = new ArrowLightningEntity(EntityInit.ARROW_LIGHTNING_ENTITY_ENTITY_TYPE, entity, world);
        arrowLightningEntity.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, power * 2, 0);
        arrowLightningEntity.setSilent(true);
        arrowLightningEntity.setCritArrow(false);
        arrowLightningEntity.setBaseDamage(damage);
        arrowLightningEntity.setKnockback(knockback);
        world.addFreshEntity(arrowLightningEntity);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.cast_spell")),
                SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return arrowLightningEntity;
    }
}