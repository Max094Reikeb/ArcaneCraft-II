package net.reikeb.arcanecraft.entities;

import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.network.*;

import net.reikeb.arcanecraft.init.EntityInit;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class ArrowLightningEntity extends AbstractArrowEntity implements IRendersAsItem {

    public ArrowLightningEntity(FMLPlayMessages.SpawnEntity packet, World world) {
        super(EntityInit.ARROW_LIGHTNING_ENTITY_ENTITY_TYPE, world);
    }

    public ArrowLightningEntity(EntityType<? extends ArrowLightningEntity> type, World world) {
        super(type, world);
    }

    public ArrowLightningEntity(EntityType<? extends ArrowLightningEntity> type, double x, double y, double z, World world) {
        super(type, x, y, z, world);
    }

    public ArrowLightningEntity(EntityType<? extends ArrowLightningEntity> type, LivingEntity entity, World world) {
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

        LightningBoltEntity lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(this.level);
        lightningBoltEntity.moveTo(Vector3d.atBottomCenterOf(this.blockPosition()));
        lightningBoltEntity.setVisualOnly(false);
        this.level.addFreshEntity(lightningBoltEntity);
    }

    @Override
    public void tick() {
        super.tick();
        this.level.addParticle(ParticleTypes.SWEEP_ATTACK, this.getX(), this.getY(), this.getZ(),
                1, 1, 1);
        if (this.inGround) {
            this.remove();
        }
    }
}