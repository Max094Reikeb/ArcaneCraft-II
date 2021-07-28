package net.reikeb.arcanecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.*;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.reikeb.arcanecraft.capabilities.ManaManager;
import net.reikeb.arcanecraft.init.EntityInit;
import net.reikeb.arcanecraft.setup.RegistryHandler;
import net.reikeb.arcanecraft.setup.client.ClientSetup;
import net.reikeb.arcanecraft.world.gen.features.ConfiguredFeatures;

import org.apache.logging.log4j.*;

@Mod(ArcaneCraft.MODID)
public class ArcaneCraft {

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    // Register the modid
    public static final String MODID = "arcanecraft";

    public ArcaneCraft() {

        // Init the RegistryHandler class
        RegistryHandler.init();

        // Registers an event with the mod specific event bus. This is needed to register new stuff.
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::curiosEvent);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ManaManager::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation RL(String path) {
        return new ResourceLocation(MODID, path);
    }

    public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ConfiguredFeatures::registerConfiguredFeatures);

        // Setup arrows renderers
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.ARROW_EVOKER_ENTITY_ENTITY_TYPE,
                renderManager -> new SpriteRenderer<>(renderManager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.ARROW_FIRE_ENTITY_ENTITY_TYPE,
                renderManager -> new SpriteRenderer<>(renderManager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.ARROW_ICE_ENTITY_ENTITY_TYPE,
                renderManager -> new SpriteRenderer<>(renderManager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.ARROW_LIFE_ENTITY_ENTITY_TYPE,
                renderManager -> new SpriteRenderer<>(renderManager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.ARROW_LIGHTNING_ENTITY_ENTITY_TYPE,
                renderManager -> new SpriteRenderer<>(renderManager, Minecraft.getInstance().getItemRenderer()));
    }

    private void curiosEvent(InterModEnqueueEvent event) {
        IntegrationHelper.registerMods(event);
    }
}
