package net.reikeb.arcanecraft.events.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.world.gen.features.ConfiguredFeatures;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID)
public class BiomeLoadingEvent {

    @SubscribeEvent
    public static void addFeatureToBiomes(net.minecraftforge.event.world.BiomeLoadingEvent event) {
        if (!event.getCategory().equals(Biome.Category.NETHER)
                && !event.getCategory().equals(Biome.Category.THEEND)
                && !event.getCategory().equals(Biome.Category.NONE)) {
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> ConfiguredFeatures.CRYSTAL_ORE_CONFIGURED_FEATURE);
        }
    }
}