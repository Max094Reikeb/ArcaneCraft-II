package net.reikeb.arcanecraft.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.network.FMLPlayMessages;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import net.reikeb.arcanecraft.init.EntityInit;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class ArrowLightningEntity extends AbstractArrow implements ItemSupplier {

    public ArrowLightningEntity(FMLPlayMessages.SpawnEntity packet, Level world) {
        super(EntityInit.ARROW_LIGHTNING_ENTITY_ENTITY_TYPE, world);
    }

    public ArrowLightningEntity(EntityType<? extends ArrowLightningEntity> type, Level world) {
        super(type, world);
    }

    public ArrowLightningEntity(EntityType<? extends ArrowLightningEntity> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public ArrowLightningEntity(EntityType<? extends ArrowLightningEntity> type, LivingEntity entity, Level world) {
        super(type, entity, world);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
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

        LightningBolt lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(this.level);
        lightningBoltEntity.moveTo(Vec3.atBottomCenterOf(this.blockPosition()));
        lightningBoltEntity.setVisualOnly(false);
        this.level.addFreshEntity(lightningBoltEntity);
    }

    @Override
    public void tick() {
        super.tick();
        this.level.addParticle(ParticleTypes.SWEEP_ATTACK, this.getX(), this.getY(), this.getZ(),
                1, 1, 1);
        if (this.inGround) {
            this.remove(RemovalReason.KILLED);
        }
    }
}