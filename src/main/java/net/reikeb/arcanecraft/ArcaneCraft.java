package net.reikeb.arcanecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import net.minecraftforge.common.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.reikeb.arcanecraft.init.EntityInit;
import net.reikeb.arcanecraft.recipes.AltarRecipe;
import net.reikeb.arcanecraft.recipes.types.RecipeTypeAltar;
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

    // Creates a new recipe type. This is used for storing recipes in the map, and looking them up.
    public static final IRecipeType<AltarRecipe> RITUAL = new RecipeTypeAltar();

    public ArcaneCraft() {

        // Init the RegistryHandler class
        RegistryHandler.init();

        // Registers an event with the mod specific event bus. This is needed to register new stuff.
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(IRecipeSerializer.class, this::registerRecipeSerializers);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ConfiguredFeatures::registerConfiguredFeatures);

        /**
         * Setup arrows renderers
         */
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

    private void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {

        // Vanilla has a registry for recipe types, but it does not actively use this registry.
        // While this makes registering your recipe type an optional step, I recommend
        // registering it anyway to allow other mods to discover your custom recipe types.
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(RITUAL.toString()), RITUAL);

        // Register the recipe serializer. This handles from json, from packet, and to packet.
        event.getRegistry().register(AltarRecipe.SERIALIZER);
    }
}
