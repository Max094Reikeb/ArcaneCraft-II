package net.reikeb.arcanecraft.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.EvokerFangs;
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
import net.reikeb.arcanecraft.init.SpellInit;
import net.reikeb.arcanecraft.spell.Spell;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class AbstractSpellArrow extends AbstractArrow implements ItemSupplier {

    private Spell spell;

    public AbstractSpellArrow(FMLPlayMessages.SpawnEntity packet, Level world) {
        super(EntityInit.ABSTRACT_SPELL_ARROW_TYPE, world);
    }

    public AbstractSpellArrow(EntityType<? extends AbstractSpellArrow> type, Level world) {
        super(type, world);
    }

    public AbstractSpellArrow(EntityType<? extends AbstractSpellArrow> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public AbstractSpellArrow(Spell spell, LivingEntity entity, Level world) {
        super(EntityInit.ABSTRACT_SPELL_ARROW_TYPE, entity, world);
        this.spell = spell;
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
        Entity shooter = this.getOwner();
        if (!(shooter instanceof LivingEntity)) return;
        if (this.spell == SpellInit.FIRE.get()) entity.setSecondsOnFire(15);
        if (this.spell == SpellInit.ICE.get()) entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 190, 2));
        if (this.spell == SpellInit.LIFE_DRAIN.get()) ((LivingEntity) shooter).addEffect(new MobEffectInstance(MobEffects.REGENERATION, 30, 3));
        if (this.spell == SpellInit.LIGHTNING.get()) {
            LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(this.level);
            if (lightningBolt == null) return;
            lightningBolt.moveTo(Vec3.atBottomCenterOf(this.blockPosition()));
            lightningBolt.setVisualOnly(false);
            this.level.addFreshEntity(lightningBolt);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            if (this.spell == SpellInit.FIRE.get()) {
                Entity fireSplashEntity = new FireSplashEntity(EntityInit.FIRE_SPLASH_ENTITY_ENTITY_TYPE, this.level);
                fireSplashEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.level.random.nextFloat() * 360F, 0);
                this.level.addFreshEntity(fireSplashEntity);
            } else if (this.spell == SpellInit.EVOKER.get()) {
                Entity evokerFangs = new EvokerFangs(EntityType.EVOKER_FANGS, this.level);
                evokerFangs.moveTo(this.getX(), (this.getY() - 1), this.getZ(), this.level.random.nextFloat() * 360F, 0);
                this.level.addFreshEntity(evokerFangs);
            } else if (this.spell == SpellInit.ICE.get()) {
                this.level.addParticle(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY(), this.getZ(),
                        6, 1, 1);
            } else if (this.spell == SpellInit.LIFE_DRAIN.get()) {
                this.level.addParticle(ParticleTypes.DAMAGE_INDICATOR, this.getX(), this.getY(), this.getZ(),
                        5, 1, 1);
            } else if (this.spell == SpellInit.LIGHTNING.get()) {
                this.level.addParticle(ParticleTypes.SWEEP_ATTACK, this.getX(), this.getY(), this.getZ(),
                        5, 1, 1);
            }
        }
        if (this.inGround) {
            if (this.spell == SpellInit.FIRE.get()) {
                if (this.level.getBlockState(this.blockPosition()).getBlock() == Blocks.CAMPFIRE) {
                    this.level.setBlock(this.blockPosition(), Blocks.CAMPFIRE.defaultBlockState(), 3);
                }
            }
            this.remove(RemovalReason.KILLED);
        }
    }
}
