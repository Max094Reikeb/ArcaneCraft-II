package net.reikeb.arcanecraft.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.reikeb.arcanecraft.init.EntityInit;
import net.reikeb.arcanecraft.init.ItemInit;

public class AmethystArrow extends AbstractArrow {

    public AmethystArrow(EntityType<? extends AmethystArrow> type, Level world) {
        super(type, world);
    }

    public AmethystArrow(Level world, LivingEntity entity) {
        super(EntityInit.AMETHYST_ARROW_ENTITY_TYPE, entity, world);
    }

    public AmethystArrow(Level world, double x, double y, double z) {
        super(EntityInit.AMETHYST_ARROW_ENTITY_TYPE, x, y, z, world);
    }

    public void tick() {
        super.tick();
        if (this.level.isClientSide && !this.isOnGround()) {
            this.level.addParticle(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    protected ItemStack getPickupItem() {
        return new ItemStack(ItemInit.AMETHYST_ARROW.get());
    }

    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        entity.hurt(DamageSource.MAGIC, 5.0F);
    }
}
