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
public class ArrowIceEntity extends AbstractArrowEntity implements IRendersAsItem {

    public ArrowIceEntity(FMLPlayMessages.SpawnEntity packet, World world) {
        super(EntityInit.ARROW_ICE_ENTITY_ENTITY_TYPE, world);
    }

    public ArrowIceEntity(EntityType<? extends ArrowIceEntity> type, World world) {
        super(type, world);
    }

    public ArrowIceEntity(EntityType<? extends ArrowIceEntity> type, double x, double y, double z, World world) {
        super(type, x, y, z, world);
    }

    public ArrowIceEntity(EntityType<? extends ArrowIceEntity> type, LivingEntity entity, World world) {
        super(type, entity, world);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return new ItemStack(Blocks.AIR, 1);
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        entity.setArrowCount(entity.getArrowCount() - 1);
        entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 190, 2));
    }

    @Override
    public void tick() {
        super.tick();
        this.level.addParticle(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY(), this.getZ(),
                6, 1, 1);
        if (this.inGround) {
            this.remove();
        }
    }
}