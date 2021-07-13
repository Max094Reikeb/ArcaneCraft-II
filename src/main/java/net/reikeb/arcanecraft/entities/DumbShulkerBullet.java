package net.reikeb.arcanecraft.entities;

import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import net.minecraftforge.fml.network.NetworkHooks;

public class DumbShulkerBullet extends ShulkerBulletEntity {

    private World world;
    private Entity entity;
    private Vector3d pos;

    public DumbShulkerBullet(EntityType<? extends ShulkerBulletEntity> type, World world, Entity entity) {
        super(type, world);
        this.entity = entity;
        this.pos = entity.position();
        this.setNoGravity(true);
        this.moveTo(entity.getX(), entity.getY() + 1, entity.getZ());
    }

    @Override
    protected void onHit(RayTraceResult result) {
        RayTraceResult.Type raytraceresult$type = result.getType();
        if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
            this.onHitEntity((EntityRayTraceResult) result);
        } else if (raytraceresult$type == RayTraceResult.Type.BLOCK) {
            this.onHitBlock((BlockRayTraceResult) result);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (distanceTo(entity) >= 30) this.remove();
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        Entity entity = result.getEntity();
        Entity entity1 = this.getOwner();
        LivingEntity livingentity = entity1 instanceof LivingEntity ? (LivingEntity) entity1 : null;

        if (result.getEntity() == this.entity) return;

        if (entity.hurt(DamageSource.indirectMobAttack(this, livingentity).setProjectile(), 6.0F)) {
            this.doEnchantDamageEffects(livingentity, entity);
            this.remove();
        }
    }
}