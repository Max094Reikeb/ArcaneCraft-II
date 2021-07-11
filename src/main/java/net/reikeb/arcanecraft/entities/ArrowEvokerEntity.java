package net.reikeb.arcanecraft.entities;

import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;

import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.network.*;

import net.reikeb.arcanecraft.init.EntityInit;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class ArrowEvokerEntity extends AbstractArrowEntity implements IRendersAsItem {

    public ArrowEvokerEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        super(EntityInit.ARROW_EVOKER_ENTITY_ENTITY_TYPE, world);
    }

    public ArrowEvokerEntity(EntityType<? extends ArrowEvokerEntity> type, World world) {
        super(type, world);
    }

    public ArrowEvokerEntity(EntityType<? extends ArrowEvokerEntity> type, double x, double y, double z, World world) {
        super(type, x, y, z, world);
    }

    public ArrowEvokerEntity(EntityType<? extends ArrowEvokerEntity> type, LivingEntity entity, World world) {
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
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            Entity evokerFangs = new EvokerFangsEntity(EntityType.EVOKER_FANGS, this.level);
            evokerFangs.moveTo(this.getX(), (this.getY() - 1), this.getZ(), this.level.random.nextFloat() * 360F, 0);
            this.level.addFreshEntity(evokerFangs);
        }
        if (this.inGround) {
            this.remove();
        }
    }
}