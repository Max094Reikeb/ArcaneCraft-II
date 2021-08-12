package net.reikeb.arcanecraft.misc;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import net.reikeb.arcanecraft.ArcaneCraft;

import java.util.List;

public class Tiers {

    private static final Tag.Named<Block> AMETHYST_TAG = BlockTags.createOptional(ArcaneCraft.RL("needs_amethyst_tool"));

    public static final Tier AMETHYST_TIER = TierSortingRegistry.registerTier(
            new ForgeTier(4, 512, 6.0F, 2.0F, 15, AMETHYST_TAG, () -> Ingredient.of(Items.AMETHYST_SHARD)),
            ArcaneCraft.RL("amethyst"), List.of(net.minecraft.world.item.Tiers.DIAMOND), List.of());
}
