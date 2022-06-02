package net.reikeb.arcanecraft.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkHooks;
import net.reikeb.arcanecraft.capabilities.ManaStorage;
import net.reikeb.arcanecraft.events.local.PlayerManaEvent;
import net.reikeb.arcanecraft.init.EntityInit;
import net.reikeb.arcanecraft.misc.vm.Mana;

import java.util.List;

public class ManaOrb extends Entity {

    private static final int LIFETIME = 6000;
    private static final int ENTITY_SCAN_PERIOD = 20;
    private static final int ORB_GROUPS_PER_AREA = 40;
    private static final double ORB_MERGE_DISTANCE = 0.5D;
    private int age;
    private int health = 5;
    public int value;
    private int count = 1;
    private Player followingPlayer;

    public ManaOrb(Level level, double posX, double posY, double posZ, int value) {
        this(EntityInit.MANA_ORB.get(), level);
        this.setPos(posX, posY, posZ);
        this.setYRot((float) (this.random.nextDouble() * 360.0D));
        this.setDeltaMovement((this.random.nextDouble() * (double) 0.2F - (double) 0.1F) * 2.0D, this.random.nextDouble() * 0.2D * 2.0D, (this.random.nextDouble() * (double) 0.2F - (double) 0.1F) * 2.0D);
        this.value = value;
    }

    public ManaOrb(EntityType<? extends ManaOrb> entityType, Level level) {
        super(entityType, level);
    }

    protected Entity.MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    protected void defineSynchedData() {
    }

    public void tick() {
        super.tick();
        this.xo = this.getX();
        this.yo = this.getY();
        this.zo = this.getZ();

        if (this.isEyeInFluid(FluidTags.WATER)) {
            this.setUnderwaterMovement();
        } else if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.03D, 0.0D));
        }

        if (this.level.getFluidState(this.blockPosition()).is(FluidTags.LAVA)) {
            this.setDeltaMovement((this.random.nextFloat() - this.random.nextFloat()) * 0.2F, 0.2F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
        }

        if (!this.level.noCollision(this.getBoundingBox())) {
            this.moveTowardsClosestSpace(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0D, this.getZ());
        }

        if (this.tickCount % ENTITY_SCAN_PERIOD == 1) {
            this.scanForEntities();
        }

        if (this.followingPlayer != null && (this.followingPlayer.isSpectator() || this.followingPlayer.isDeadOrDying())) {
            this.followingPlayer = null;
        }

        if (this.followingPlayer != null) {
            Vec3 vec3 = new Vec3(this.followingPlayer.getX() - this.getX(), this.followingPlayer.getY() + (double) this.followingPlayer.getEyeHeight() / 2.0D - this.getY(), this.followingPlayer.getZ() - this.getZ());
            double d0 = vec3.lengthSqr();
            if (d0 < 64.0D) {
                double d1 = 1.0D - Math.sqrt(d0) / 8.0D;
                this.setDeltaMovement(this.getDeltaMovement().add(vec3.normalize().scale(d1 * d1 * 0.1D)));
            }
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        float f = 0.98F;
        if (this.onGround) {
            BlockPos pos = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
            f = this.level.getBlockState(pos).getFriction(this.level, pos, this) * 0.98F;
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(f, 0.98D, f));
        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, -0.9D, 1.0D));
        }

        ++this.age;
        if (this.age >= LIFETIME) {
            this.discard();
        }
    }

    private void scanForEntities() {
        if (this.followingPlayer == null || this.followingPlayer.distanceToSqr(this) > 64.0D) {
            this.followingPlayer = this.level.getNearestPlayer(this, 8.0D);
        }

        if (this.level instanceof ServerLevel) {
            for (ManaOrb manaOrb : this.level.getEntities(EntityTypeTest.forClass(ManaOrb.class), this.getBoundingBox().inflate(ORB_MERGE_DISTANCE), this::canMerge)) {
                this.merge(manaOrb);
            }
        }
    }

    public static void award(ServerLevel level, Vec3 vec3, int value) {
        while (value > 0) {
            int i = getManaValue(value);
            value -= 1;
            if (!tryMergeToExisting(level, vec3, i)) {
                level.addFreshEntity(new ManaOrb(level, vec3.x(), vec3.y(), vec3.z(), i));
            }
        }
    }

    private static boolean tryMergeToExisting(ServerLevel level, Vec3 vec3, int value) {
        AABB aabb = AABB.ofSize(vec3, 1.0D, 1.0D, 1.0D);
        int i = level.getRandom().nextInt(ORB_GROUPS_PER_AREA);
        List<ManaOrb> list = level.getEntities(EntityTypeTest.forClass(ManaOrb.class), aabb, (predicate) -> {
            return canMerge(predicate, i, value);
        });
        if (!list.isEmpty()) {
            ManaOrb manaOrb = list.get(0);
            ++manaOrb.count;
            manaOrb.age = 0;
            return true;
        } else {
            return false;
        }
    }

    private boolean canMerge(ManaOrb manaOrb) {
        return manaOrb != this && canMerge(manaOrb, this.getId(), this.value);
    }

    private static boolean canMerge(ManaOrb manaOrb, int id, int value) {
        return !manaOrb.isRemoved() && (manaOrb.getId() - id) % ORB_GROUPS_PER_AREA == 0 && manaOrb.value == value;
    }

    private void merge(ManaOrb manaOrb) {
        this.count += manaOrb.count;
        this.age = Math.min(this.age, manaOrb.age);
        manaOrb.discard();
    }

    private void setUnderwaterMovement() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x * (double) 0.99F, Math.min(vec3.y + (double) 5.0E-4F, 0.06F), vec3.z * (double) 0.99F);
    }

    protected void doWaterSplashEffect() {
    }

    public boolean hurt(DamageSource damageSource, float damage) {
        if (this.level.isClientSide || this.isRemoved()) return false;
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else {
            this.markHurt();
            this.health = (int) ((float) this.health - damage);
            if (this.health <= 0) {
                this.discard();
            }
            return true;
        }
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putShort("Health", (short) this.health);
        tag.putShort("Age", (short) this.age);
        tag.putShort("Value", (short) this.value);
        tag.putInt("Count", this.count);
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        this.health = tag.getShort("Health");
        this.age = tag.getShort("Age");
        this.value = tag.getShort("Value");
        this.count = Math.max(tag.getInt("Count"), 1);
    }

    public void playerTouch(Player player) {
        if (!this.level.isClientSide) {
            if (player.takeXpDelay == 0) {
                player.takeXpDelay = 2;
                player.take(this, 1);

                if (MinecraftForge.EVENT_BUS.post(new PlayerManaEvent.PickupMana(player, this))) return;

                ServerPlayer serverPlayer = (ServerPlayer) player;
                ManaStorage.get(serverPlayer).ifPresent(data -> {
                    if ((data.getMana() + this.count) <= data.getMaxMana()) {
                        Mana.addCurrentMana(data, serverPlayer, this.count);

                        --this.count;
                        if (this.count == 0) {
                            this.discard();
                        }
                    }
                });
            }
        }
    }

    public int getValue() {
        return this.value;
    }

    public int getIcon() {
        if (this.value >= 800) {
            return 10;
        } else if (this.value >= 400) {
            return 9;
        } else if (this.value >= 250) {
            return 8;
        } else if (this.value >= 150) {
            return 7;
        } else if (this.value >= 100) {
            return 6;
        } else if (this.value >= 50) {
            return 5;
        } else if (this.value >= 25) {
            return 4;
        } else if (this.value >= 10) {
            return 3;
        } else if (this.value >= 5) {
            return 2;
        } else {
            return 1;
        }
    }

    public static int getManaValue(int value) {
        if (value >= 800) {
            return 800;
        } else if (value >= 400) {
            return 400;
        } else if (value >= 250) {
            return 250;
        } else if (value >= 150) {
            return 150;
        } else if (value >= 100) {
            return 100;
        } else if (value >= 50) {
            return 50;
        } else if (value >= 25) {
            return 25;
        } else if (value >= 10) {
            return 10;
        } else if (value >= 5) {
            return 5;
        } else {
            return value >= 3 ? 3 : 1;
        }
    }

    public boolean isAttackable() {
        return false;
    }

    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public SoundSource getSoundSource() {
        return SoundSource.AMBIENT;
    }
}
