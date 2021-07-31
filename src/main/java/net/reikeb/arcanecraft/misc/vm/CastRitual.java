package net.reikeb.arcanecraft.misc.vm;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import net.minecraftforge.registries.ForgeRegistries;

import net.reikeb.arcanecraft.blocks.RunicPillar;
import net.reikeb.arcanecraft.entities.FireSplashEntity;
import net.reikeb.arcanecraft.init.EntityInit;
import net.reikeb.arcanecraft.init.PotionEffectInit;
import net.reikeb.arcanecraft.network.NetworkManager;
import net.reikeb.arcanecraft.network.packets.MagicSummoningPacket;
import net.reikeb.arcanecraft.tileentities.TileAltar;

public class CastRitual {

    private final Level level;
    private final BlockPos pos;
    private final TileAltar tileAltar;

    public CastRitual(Level world, BlockPos pos, TileAltar tileAltar, Player playerEntity, boolean isSacrifice) {
        this.level = world;
        this.pos = pos;
        this.tileAltar = tileAltar;

        ItemStack stackInSlot0 = tileAltar.getInventory().getStackInSlot(0);
        ItemStack stackInSlot1 = tileAltar.getInventory().getStackInSlot(1);

        if (this.isPlaced()) {
            if ((stackInSlot0.getItem() == Items.PRISMARINE_SHARD) // Rain Ritual
                    && (stackInSlot1.getItem() == Items.LAPIS_LAZULI) && (!isSacrifice)) {
                ((ServerLevel) this.level).setWeatherParameters(0, 9999, true, false);
                finishRitual(false);

            } else if ((stackInSlot0.getItem() == Items.PRISMARINE_SHARD) // Thunder Ritual
                    && (stackInSlot1.getItem() == Items.GLOWSTONE_DUST) && (!isSacrifice)) {
                ((ServerLevel) this.level).setWeatherParameters(0, 6000, true, true);
                finishRitual(false);

            } else if ((stackInSlot0.getItem() == Items.SUNFLOWER) // Day Ritual
                    && (stackInSlot1.getItem() == Blocks.SAND.asItem()) && (!isSacrifice)) {
                if (this.level.getServer() == null) return;
                for (ServerLevel serverWorld : this.level.getServer().getAllLevels()) {
                    serverWorld.setDayTime(1000);
                }
                finishRitual(false);

            } else if ((stackInSlot0.getItem() == Items.ENDER_PEARL) // Night ritual
                    && (stackInSlot1.getItem() == Blocks.SAND.asItem()) && (!isSacrifice)) {
                if (this.level.getServer() == null) return;
                for (ServerLevel serverWorld : this.level.getServer().getAllLevels()) {
                    serverWorld.setDayTime(13000);
                }
                finishRitual(false);

            } else if ((stackInSlot0.getItem() == Items.BLAZE_POWDER) // Flame ritual
                    && (stackInSlot1.getItem() == Items.GOLD_INGOT) && (!isSacrifice)) {
                playerEntity.addEffect(new MobEffectInstance(PotionEffectInit.CHARRED_STRIKE.get(), 12000, 0));
                this.level.playSound(null, this.pos.getX(), this.pos.getY(), this.pos.getZ(),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                        SoundSource.NEUTRAL, 4f, 4f);
                if (!this.level.isClientSide) {
                    Entity fireSplash = new FireSplashEntity(EntityInit.FIRE_SPLASH_ENTITY_ENTITY_TYPE, this.level);
                    fireSplash.moveTo(this.pos.above(), this.level.random.nextFloat() * 360F, 0);
                    this.level.addFreshEntity(fireSplash);
                }
                finishRitual(false);

            } else if ((stackInSlot0.getItem() == Blocks.LILY_OF_THE_VALLEY.asItem()) // Growth ritual
                    && (stackInSlot1.getItem() == Blocks.GRASS_BLOCK.asItem()) && (!isSacrifice)) {
                playerEntity.addEffect(new MobEffectInstance(PotionEffectInit.DRUID_BLESSING.get(), 12000, 0));
                finishRitual(false);

            }
        }
    }

    private void finishRitual(boolean isSacrifice) {
        NetworkManager.INSTANCE.sendToServer(new MagicSummoningPacket());

        LightningBolt lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(this.level);
        if (lightningBoltEntity == null) return;

        lightningBoltEntity.moveTo(Vec3.atBottomCenterOf(this.pos));
        lightningBoltEntity.setVisualOnly(isSacrifice);
        this.level.addFreshEntity(lightningBoltEntity);

        this.tileAltar.removeItemIndexCount(0, 1);
        this.tileAltar.removeItemIndexCount(1, 1);

        this.level.setBlockAndUpdate(this.pos.north().north(), this.level.getBlockState(pos.north().north())
                .setValue(RunicPillar.ACTIVE, false));
        this.level.setBlockAndUpdate(this.pos.south().south(), this.level.getBlockState(pos.south().south())
                .setValue(RunicPillar.ACTIVE, false));
        this.level.setBlockAndUpdate(this.pos.east().east(), this.level.getBlockState(pos.east().east())
                .setValue(RunicPillar.ACTIVE, false));
        this.level.setBlockAndUpdate(this.pos.west().west(), this.level.getBlockState(pos.west().west())
                .setValue(RunicPillar.ACTIVE, false));
    }

    private boolean isPlaced() {
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;

        if (this.level.getBlockState(this.pos.north().north()).getBlock() instanceof RunicPillar) {
            flag = this.level.getBlockState(this.pos.north().north()).getValue(RunicPillar.ACTIVE);
        }
        if (this.level.getBlockState(this.pos.south().south()).getBlock() instanceof RunicPillar) {
            flag1 = this.level.getBlockState(this.pos.south().south()).getValue(RunicPillar.ACTIVE);
        }
        if (this.level.getBlockState(this.pos.east().east()).getBlock() instanceof RunicPillar) {
            flag2 = this.level.getBlockState(this.pos.east().east()).getValue(RunicPillar.ACTIVE);
        }
        if (this.level.getBlockState(this.pos.west().west()).getBlock() instanceof RunicPillar) {
            flag3 = this.level.getBlockState(this.pos.west().west()).getValue(RunicPillar.ACTIVE);
        }

        return flag && flag1 && flag2 && flag3;
    }
}
