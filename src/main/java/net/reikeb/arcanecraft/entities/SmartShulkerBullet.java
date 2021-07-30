package net.reikeb.arcanecraft.entities;

import com.google.common.collect.Lists;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class SmartShulkerBullet extends ShulkerBullet {

    private Level world;
    private LivingEntity entity;
    private Entity targetEntity;
    private Direction.Axis direction;

    public SmartShulkerBullet(Level world, LivingEntity entity, Entity targetEntity, Direction.Axis direction) {
        super(world, entity, targetEntity, direction);
        this.world = world;
        this.entity = entity;
        this.targetEntity = targetEntity;
        this.direction = direction;
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide) {
            if (this.finalTarget == null && this.targetId != null) {
                this.finalTarget = ((ServerLevel) this.level).getEntity(this.targetId);
                if (this.finalTarget == null) {
                    this.targetId = null;
                }
            }

            if (this.finalTarget == null || !this.finalTarget.isAlive() || this.finalTarget instanceof Player && ((Player) this.finalTarget).isSpectator()) {
                if (!this.isNoGravity()) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
                }
            } else {
                this.targetDeltaX = Mth.clamp(this.targetDeltaX * 1.025D, -1.0D, 1.0D);
                this.targetDeltaY = Mth.clamp(this.targetDeltaY * 1.025D, -1.0D, 1.0D);
                this.targetDeltaZ = Mth.clamp(this.targetDeltaZ * 1.025D, -1.0D, 1.0D);
                Vec3 vector3d = this.getDeltaMovement();
                this.setDeltaMovement(vector3d.add((this.targetDeltaX - vector3d.x) * 0.5, (this.targetDeltaY - vector3d.y) * 0.5, (this.targetDeltaZ - vector3d.z) * 0.5));
            }

            HitResult raytraceresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
            if (raytraceresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                this.onHit(raytraceresult);
            }
        }

        this.checkInsideBlocks();
        Vec3 vector3d1 = this.getDeltaMovement();
        this.setPos(this.getX() + vector3d1.x, this.getY() + vector3d1.y, this.getZ() + vector3d1.z);
        ProjectileUtil.rotateTowardsMovement(this, 0.5F);

        if (this.level.isClientSide) {
            this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getX() - vector3d1.x, this.getY() - vector3d1.y + 0.15D, this.getZ() - vector3d1.z, 0.0D, 0.0D, 0.0D);
        } else if (this.finalTarget != null && !this.finalTarget.isRemoved()) {
            if (this.flightSteps > 0) {
                --this.flightSteps;
                if (this.flightSteps == 0) {
                    this.selectNextMoveDirection(this.currentMoveDirection == null ? null : this.currentMoveDirection.getAxis());
                }
            }

            if (this.currentMoveDirection != null) {
                BlockPos blockpos = this.blockPosition();
                Direction.Axis direction$axis = this.currentMoveDirection.getAxis();
                if (this.level.loadedAndEntityCanStandOn(blockpos.relative(this.currentMoveDirection), this)) {
                    this.selectNextMoveDirection(direction$axis);
                } else {
                    BlockPos blockpos1 = this.finalTarget.blockPosition();
                    if (direction$axis == Direction.Axis.X && blockpos.getX() == blockpos1.getX() || direction$axis == Direction.Axis.Z && blockpos.getZ() == blockpos1.getZ() || direction$axis == Direction.Axis.Y && blockpos.getY() == blockpos1.getY()) {
                        this.selectNextMoveDirection(direction$axis);
                    }
                }
            }
        }
    }

    @Override
    public void selectNextMoveDirection(@Nullable Direction.Axis axis) {
        double d0 = 0.5D;
        BlockPos blockpos;
        if (this.finalTarget == null) {
            blockpos = this.blockPosition().below();
        } else {
            d0 = (double) this.finalTarget.getBbHeight() * 0.5D;
            blockpos = new BlockPos(this.finalTarget.getX(), this.finalTarget.getY() + d0, this.finalTarget.getZ());
        }

        double d1 = (double) blockpos.getX() + 0.5D;
        double d2 = (double) blockpos.getY() + d0;
        double d3 = (double) blockpos.getZ() + 0.5D;
        Direction direction = null;
        if (!blockpos.closerThan(this.position(), 2.0D)) {
            BlockPos blockpos1 = this.blockPosition();
            List<Direction> list = Lists.newArrayList();
            if (axis != Direction.Axis.X) {
                if (blockpos1.getX() < blockpos.getX() && this.level.isEmptyBlock(blockpos1.east())) {
                    list.add(Direction.EAST);
                } else if (blockpos1.getX() > blockpos.getX() && this.level.isEmptyBlock(blockpos1.west())) {
                    list.add(Direction.WEST);
                }
            }

            if (axis != Direction.Axis.Y) {
                if (blockpos1.getY() < blockpos.getY() && this.level.isEmptyBlock(blockpos1.above())) {
                    list.add(Direction.UP);
                } else if (blockpos1.getY() > blockpos.getY() && this.level.isEmptyBlock(blockpos1.below())) {
                    list.add(Direction.DOWN);
                }
            }

            if (axis != Direction.Axis.Z) {
                if (blockpos1.getZ() < blockpos.getZ() && this.level.isEmptyBlock(blockpos1.south())) {
                    list.add(Direction.SOUTH);
                } else if (blockpos1.getZ() > blockpos.getZ() && this.level.isEmptyBlock(blockpos1.north())) {
                    list.add(Direction.NORTH);
                }
            }

            direction = Direction.getRandom(this.random);
            if (list.isEmpty()) {
                for (int i = 1; !this.level.isEmptyBlock(blockpos1.relative(direction)) && i > 0; --i) {
                    direction = Direction.getRandom(this.random);
                }
            } else {
                direction = list.get(this.random.nextInt(list.size()));
            }

            d1 = this.getX() + (double) direction.getStepX();
            d2 = this.getY() + (double) direction.getStepY();
            d3 = this.getZ() + (double) direction.getStepZ();
        }

        this.setMoveDirection(direction);
        double d6 = d1 - this.getX();
        double d7 = d2 - this.getY();
        double d4 = d3 - this.getZ();
        double d5 = Mth.sqrt((float) (d6 * d6 + d7 * d7 + d4 * d4));

        if (d5 == 0.0D) {
            this.targetDeltaX = 0.0D;
            this.targetDeltaY = 0.0D;
            this.targetDeltaZ = 0.0D;
        } else {
            this.targetDeltaX = d6 / d5 * 0.38D;
            this.targetDeltaY = d7 / d5 * 0.38D;
            this.targetDeltaZ = d4 / d5 * 0.38D;
        }

        this.hasImpulse = true;
        this.flightSteps = 10;
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
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
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
}
