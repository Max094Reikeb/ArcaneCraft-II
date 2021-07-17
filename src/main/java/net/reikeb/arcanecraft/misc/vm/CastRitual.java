package net.reikeb.arcanecraft.misc.vm;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import net.minecraftforge.items.wrapper.RecipeWrapper;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.blocks.RunicPillar;
import net.reikeb.arcanecraft.network.NetworkManager;
import net.reikeb.arcanecraft.network.packets.MagicSummoningPacket;
import net.reikeb.arcanecraft.recipes.AltarRecipe;
import net.reikeb.arcanecraft.tileentities.TileAltar;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class CastRitual {

    private boolean isCorrect = true;
    private final World level;
    private final BlockPos pos;

    public CastRitual(World world, BlockPos pos, boolean isSacrifice) {
        this.level = world;
        this.pos = pos;

        TileEntity tileEntity = this.level.getBlockEntity(this.pos);
        if (!(tileEntity instanceof TileAltar)) return;
        TileAltar tileAltar = ((TileAltar) tileEntity);

        ItemStack stackInSlot0 = tileAltar.getInventory().getStackInSlot(0);
        ItemStack stackInSlot1 = tileAltar.getInventory().getStackInSlot(1);
        AltarRecipe altarRecipe = this.getRecipe(stackInSlot0, stackInSlot1, tileAltar);
        if (altarRecipe == null) return;

        if (this.isCorrect && isPlaced()) {
            ItemStack output = altarRecipe.getResultItem();
            if ((output.getItem() == Blocks.STONE.asItem()) && (!isSacrifice)) {
                ((ServerWorld) this.level).setWeatherParameters(0, 9999, true, false);
                finishRitual(tileAltar);
            } else if ((output.getItem() == Blocks.DIRT.asItem() && (!isSacrifice))) {
                ((ServerWorld) this.level).setWeatherParameters(0, 6000, true, true);
                finishRitual(tileAltar);
            } else if ((output.getItem() == Items.SUNFLOWER) && (!isSacrifice)) {
                if (this.level.getServer() == null) return;
                for (ServerWorld serverWorld : this.level.getServer().getAllLevels()) {
                    serverWorld.setDayTime(1000);
                }
                finishRitual(tileAltar);
            } else if ((output.getItem() == Items.ENDER_PEARL) && (!isSacrifice)) {
                if (this.level.getServer() == null) return;
                for (ServerWorld serverWorld : this.level.getServer().getAllLevels()) {
                    serverWorld.setDayTime(13000);
                }
                finishRitual(tileAltar);
            }
        }
    }

    private void finishRitual(TileAltar tileAltar) {
        NetworkManager.INSTANCE.sendToServer(new MagicSummoningPacket());

        LightningBoltEntity lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(this.level);
        if (lightningBoltEntity == null) return;

        lightningBoltEntity.moveTo(Vector3d.atBottomCenterOf(this.pos));
        lightningBoltEntity.setVisualOnly(false);
        this.level.addFreshEntity(lightningBoltEntity);

        tileAltar.removeItemIndexCount(0, 1);
        tileAltar.removeItemIndexCount(1, 1);

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

    @Nullable
    private AltarRecipe getRecipe(ItemStack stack, ItemStack stack2, TileAltar tileAltar) {
        if (stack == null || stack2 == null) return null;

        Set<IRecipe<?>> recipes = findRecipesByType(ArcaneCraft.RITUAL);
        for (IRecipe<?> iRecipe : recipes) {
            AltarRecipe recipe = (AltarRecipe) iRecipe;
            if (recipe.matches(new RecipeWrapper(tileAltar.getInventory()), this.level)) {
                isCorrect(recipe);
                return recipe;
            }
        }
        return null;
    }

    public Set<IRecipe<?>> findRecipesByType(IRecipeType<?> typeIn) {
        return this.level != null ? this.level.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
    }

    protected void isCorrect(@Nullable IRecipe<?> recipe) {
        if (recipe != null) {
            ItemStack resultItem = recipe.getResultItem();
            if (resultItem.isEmpty()) {
                this.isCorrect = false;
            }
        } else {
            this.isCorrect = false;
        }
    }
}
