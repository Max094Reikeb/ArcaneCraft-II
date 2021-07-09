package net.reikeb.arcanecraft.setup;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.reikeb.arcanecraft.init.*;

public class RegistryHandler {

    public static void init() {
        BlockInit.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ItemInit.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        PotionEffectInit.EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
