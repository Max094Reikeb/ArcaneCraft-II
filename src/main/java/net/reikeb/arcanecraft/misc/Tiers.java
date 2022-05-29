package net.reikeb.arcanecraft.misc;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class Tiers {

    public static final Tier AMETHYST_TIER = TierSortingRegistry.registerTier(
            new ForgeTier(4, 512, 6.0F, 2.0F, 15, Tags.AMETHYST_TAG, () -> Ingredient.of(Items.AMETHYST_SHARD)),
            Keys.AMETHYST, List.of(net.minecraft.world.item.Tiers.DIAMOND), List.of());
}
