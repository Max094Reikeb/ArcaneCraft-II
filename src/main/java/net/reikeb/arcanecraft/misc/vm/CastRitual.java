package net.reikeb.arcanecraft.misc.vm;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import net.reikeb.arcanecraft.blocks.RunicPillar;
import net.reikeb.arcanecraft.network.NetworkManager;
import net.reikeb.arcanecraft.network.packets.MagicSummoningPacket;
import net.reikeb.arcanecraft.tileentities.TileAltar;

public class CastRitual {

    private final World level;
    private final BlockPos pos;
    private final TileAltar tileAltar;

    public CastRitual(World world, BlockPos pos, TileAltar tileAltar, boolean isSacrifice) {
        this.level = world;
        this.pos = pos;
        this.tileAltar = tileAltar;

        ItemStack stackInSlot0 = tileAltar.getInventory().getStackInSlot(0);
        ItemStack stackInSlot1 = tileAltar.getInventory().getStackInSlot(1);

        if (this.isPlaced()) {
            if ((stackInSlot0.getItem() == Items.PRISMARINE_SHARD) // Rain Ritual
                    && (stackInSlot1.getItem() == Items.LAPIS_LAZULI) && (!isSacrifice)) {
                ((ServerWorld) this.level).setWeatherParameters(0, 9999, true, false);
                finishRitual();

            } else if ((stackInSlot0.getItem() == Items.PRISMARINE_SHARD) // Thunder Ritual
                    && (stackInSlot1.getItem() == Items.GLOWSTONE_DUST) && (!isSacrifice)) {
                ((ServerWorld) this.level).setWeatherParameters(0, 6000, true, true);
                finishRitual();

            } else if ((stackInSlot0.getItem() == Items.SUNFLOWER) // Day Ritual
                    && (stackInSlot1.getItem() == Blocks.SAND.asItem()) && (!isSacrifice)) {
                if (this.level.getServer() == null) return;
                for (ServerWorld serverWorld : this.level.getServer().getAllLevels()) {
                    serverWorld.setDayTime(1000);
                }
                finishRitual();

            } else if ((stackInSlot0.getItem() == Items.ENDER_PEARL) // Night ritual
                    && (stackInSlot1.getItem() == Blocks.SAND.asItem()) && (!isSacrifice)) {
                if (this.level.getServer() == null) return;
                for (ServerWorld serverWorld : this.level.getServer().getAllLevels()) {
                    serverWorld.setDayTime(13000);
                }
                finishRitual();
            }
        }
    }

    private void finishRitual() {
        NetworkManager.INSTANCE.sendToServer(new MagicSummoningPacket());

        LightningBoltEntity lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(this.level);
        if (lightningBoltEntity == null) return;

        lightningBoltEntity.moveTo(Vector3d.atBottomCenterOf(this.pos));
        lightningBoltEntity.setVisualOnly(false);
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
