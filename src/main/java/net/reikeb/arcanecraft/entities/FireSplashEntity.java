package net.reikeb.arcanecraft.entities;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.world.World;

import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

import net.reikeb.arcanecraft.init.EntityInit;

import java.util.Random;

public class FireSplashEntity extends CreatureEntity {

    public FireSplashEntity(FMLPlayMessages.SpawnEntity packet, World world) {
        this(EntityInit.FIRE_SPLASH_ENTITY_ENTITY_TYPE, world);
    }

    public FireSplashEntity(EntityType<FireSplashEntity> type, World world) {
        super(type, world);
        xpReward = 0;
        setNoAi(true);
        setPersistenceRequired();
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEFINED;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
    }

    @Override
    public net.minecraft.util.SoundEvent getAmbientSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
    }

    @Override
    public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
    }

    @Override
    public net.minecraft.util.SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
    }

    @Override
    protected float getSoundVolume() {
        return 1.0F;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof ArrowEntity)
            return false;
        if (source.getDirectEntity() instanceof PlayerEntity)
            return false;
        if (source.getDirectEntity() instanceof PotionEntity)
            return false;
        if (source == DamageSource.FALL)
            return false;
        if (source == DamageSource.CACTUS)
            return false;
        if (source == DamageSource.DROWN)
            return false;
        if (source == DamageSource.LIGHTNING_BOLT)
            return false;
        return super.hurt(source, amount);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        this.remove();
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return CreatureEntity.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.MAX_HEALTH, 1)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 3);
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    public void aiStep() {
        super.aiStep();
        Random random = this.random;
        for (int l = 0; l < 5; l++) {
            double d0 = (this.getX() + random.nextFloat());
            double d1 = (this.getY() + random.nextFloat());
            double d2 = (this.getZ() + random.nextFloat());
            double d3 = (random.nextFloat() - 0.5D) * 0.4000000014901161D;
            double d4 = (random.nextFloat() - 0.5D) * 0.4000000014901161D;
            double d5 = (random.nextFloat() - 0.5D) * 0.4000000014901161D;
            this.level.addParticle(ParticleTypes.FLAME, d0, d1, d2, d3, d4, d5);
        }
    }
}