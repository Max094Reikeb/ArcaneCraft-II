package net.reikeb.arcanecraft.entities;

import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.network.FMLPlayMessages;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import net.reikeb.arcanecraft.init.EntityInit;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class ArrowFireEntity extends AbstractArrow implements ItemSupplier {

    public ArrowFireEntity(FMLPlayMessages.SpawnEntity packet, Level world) {
        super(EntityInit.ARROW_FIRE_ENTITY_ENTITY_TYPE, world);
    }

    public ArrowFireEntity(EntityType<? extends ArrowFireEntity> type, Level world) {
        super(type, world);
    }

    public ArrowFireEntity(EntityType<? extends ArrowFireEntity> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public ArrowFireEntity(EntityType<? extends ArrowFireEntity> type, LivingEntity entity, Level world) {
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
            this.remove(false);
        }
    }
}
