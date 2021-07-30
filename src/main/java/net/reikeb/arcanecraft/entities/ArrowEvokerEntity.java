package net.reikeb.arcanecraft.entities;

import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.EvokerFangs;
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
public class ArrowEvokerEntity extends AbstractArrow implements ItemSupplier {

    public ArrowEvokerEntity(FMLPlayMessages.SpawnEntity spawnEntity, Level world) {
        super(EntityInit.ARROW_EVOKER_ENTITY_ENTITY_TYPE, world);
    }

    public ArrowEvokerEntity(EntityType<? extends ArrowEvokerEntity> type, Level world) {
        super(type, world);
    }

    public ArrowEvokerEntity(EntityType<? extends ArrowEvokerEntity> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public ArrowEvokerEntity(EntityType<? extends ArrowEvokerEntity> type, LivingEntity entity, Level world) {
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
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            Entity evokerFangs = new EvokerFangs(EntityType.EVOKER_FANGS, this.level);
            evokerFangs.moveTo(this.getX(), (this.getY() - 1), this.getZ(), this.level.random.nextFloat() * 360F, 0);
            this.level.addFreshEntity(evokerFangs);
        }
        if (this.inGround) {
            this.remove(false);
        }
    }
}
