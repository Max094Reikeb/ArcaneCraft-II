package net.reikeb.arcanecraft.entities;

import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;

import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.network.*;

import net.reikeb.arcanecraft.init.EntityInit;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class ArrowFireEntity extends AbstractArrowEntity implements IRendersAsItem {

    public ArrowFireEntity(FMLPlayMessages.SpawnEntity packet, World world) {
        super(EntityInit.ARROW_FIRE_ENTITY_ENTITY_TYPE, world);
    }

    public ArrowFireEntity(EntityType<? extends ArrowFireEntity> type, World world) {
        super(type, world);
    }

    public ArrowFireEntity(EntityType<? extends ArrowFireEntity> type, double x, double y, double z, World world) {
        super(type, x, y, z, world);
    }

    public ArrowFireEntity(EntityType<? extends ArrowFireEntity> type, LivingEntity entity, World world) {
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
        entity.setSecondsOnFire(15);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            Entity fireSplashEntity = new FireSplashEntity(EntityInit.FIRE_SPLASH_ENTITY_ENTITY_TYPE, this.level);
            fireSplashEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.level.random.nextFloat() * 360F, 0);
            this.level.addFreshEntity(fireSplashEntity);
        }
        if (this.inGround) {
            if (this.level.getBlockState(this.blockPosition()).getBlock() == Blocks.CAMPFIRE) {
                this.level.setBlock(this.blockPosition(), Blocks.CAMPFIRE.defaultBlockState(), 3);
            }
            this.remove();
        }
    }
}