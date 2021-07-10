package net.reikeb.arcanecraft.setup;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.reikeb.arcanecraft.init.*;

public class RegistryHandler {

    public static void init() {
        BlockInit.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ItemInit.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        PotionEffectInit.EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ScrollInit.SCROLL_DEFERRED_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        ScrollFocusInit.SCROLL_FOCUS_DEFERRED_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        SpellInit.SPELLS_DEFERRED_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        WandInit.WAND_DEFERRED_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
