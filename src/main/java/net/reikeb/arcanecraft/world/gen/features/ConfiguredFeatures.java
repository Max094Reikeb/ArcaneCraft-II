package net.reikeb.arcanecraft.world.gen.features;

import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.init.BlockInit;

public class ConfiguredFeatures {

    public static ConfiguredFeature<?, ?> CRYSTAL_ORE_CONFIGURED_FEATURE = new OreFeature(OreFeatureConfig.CODEC)
            .configured(new OreFeatureConfig(new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD),
                    BlockInit.CRYSTAL_ORE.get().defaultBlockState(), 10)).range(40)
            .squared().count(5);

    public static void registerConfiguredFeatures() {
        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        Registry.register(registry, new ResourceLocation(ArcaneCraft.MODID, "crystal_ore"), CRYSTAL_ORE_CONFIGURED_FEATURE);
    }
}
