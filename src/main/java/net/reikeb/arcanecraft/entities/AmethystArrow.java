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

    public AmethystArrow(EntityType<? extends AmethystArrow> entityType, Level level) {
        super(entityType, level);
    }

    public AmethystArrow(Level level, LivingEntity livingEntity) {
        super(EntityInit.AMETHYST_ARROW.get(), livingEntity, level);
    }

    public AmethystArrow(Level level, double x, double y, double z) {
        super(EntityInit.AMETHYST_ARROW.get(), x, y, z, level);
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

    protected void doPostHurtEffects(LivingEntity livingEntity) {
        super.doPostHurtEffects(livingEntity);
        livingEntity.hurt(DamageSource.MAGIC, 5.0F);
    }
}
