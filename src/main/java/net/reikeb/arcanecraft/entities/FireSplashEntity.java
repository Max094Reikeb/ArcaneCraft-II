package net.reikeb.arcanecraft.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.reikeb.arcanecraft.misc.Keys;

import java.util.Random;

public class FireSplashEntity extends PathfinderMob {

    public FireSplashEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        xpReward = 0;
        setNoAi(true);
        setPersistenceRequired();
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
    }

    @Override
    public net.minecraft.sounds.SoundEvent getAmbientSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(Keys.DEFAULT_NULL);
    }

    @Override
    public net.minecraft.sounds.SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(Keys.DEFAULT_NULL);
    }

    @Override
    public net.minecraft.sounds.SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(Keys.DEFAULT_NULL);
    }

    @Override
    protected float getSoundVolume() {
        return 1.0F;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof Arrow)
            return false;
        if (source.getDirectEntity() instanceof Player)
            return false;
        if (source.getDirectEntity() instanceof ThrownPotion)
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
        this.remove(RemovalReason.KILLED);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
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