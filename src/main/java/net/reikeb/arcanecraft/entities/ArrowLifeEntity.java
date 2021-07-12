package net.reikeb.arcanecraft.entities;

import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.*;
import net.minecraft.world.World;

import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.network.*;

import net.reikeb.arcanecraft.init.EntityInit;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class ArrowLifeEntity extends AbstractArrowEntity implements IRendersAsItem {

    public ArrowLifeEntity(FMLPlayMessages.SpawnEntity packet, World world) {
        super(EntityInit.ARROW_LIFE_ENTITY_ENTITY_TYPE, world);
    }

    public ArrowLifeEntity(EntityType<? extends ArrowLifeEntity> type, World world) {
        super(type, world);
    }

    public ArrowLifeEntity(EntityType<? extends ArrowLifeEntity> type, double x, double y, double z, World world) {
        super(type, x, y, z, world);
    }

    public ArrowLifeEntity(EntityType<? extends ArrowLifeEntity> type, LivingEntity entity, World world) {
        super(type, entity, world);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return new ItemStack(Blocks.AIR, (int) (1));
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        entity.setArrowCount(entity.getArrowCount() - 1);
        Entity shooter = this.getOwner();
        if (!(shooter instanceof LivingEntity)) return;
        ((LivingEntity) shooter).addEffect(new EffectInstance(Effects.REGENERATION, 30, 3));
    }

    @Override
    public void tick() {
        super.tick();
        this.level.addParticle(ParticleTypes.DAMAGE_INDICATOR, this.getX(), this.getY(), this.getZ(),
                1, 1, 1);
        if (this.inGround) {
            this.remove();
        }
    }
}