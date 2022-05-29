package net.reikeb.arcanecraft;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.reikeb.arcanecraft.init.ItemInit;
import net.reikeb.arcanecraft.setup.RegistryHandler;
import net.reikeb.arcanecraft.setup.client.ClientSetup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation RL(String path) {
        return new ResourceLocation(MODID, path);
    }

    public static BannerPattern addBanner(String name) {
        return BannerPattern.create(name.toUpperCase(), name, MODID + "." + name, true);
    }

    public void setup(final FMLCommonSetupEvent event) {
        // Custom potion recipes
        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potion.byName("awkward"))),
                Ingredient.of(Items.AMETHYST_SHARD),
                new ItemStack(ItemInit.MANA_POTION.get()));
    }

    private void curiosEvent(InterModEnqueueEvent event) {
        IntegrationHelper.registerMods(event);
    }
}
