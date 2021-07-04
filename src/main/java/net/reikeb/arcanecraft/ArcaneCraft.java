package net.reikeb.arcanecraft;

import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.*;

import org.apache.logging.log4j.*;

@Mod(ArcaneCraft.MODID)
public class ArcaneCraft {

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    // Register the modid
    public static final String MODID = "arcanecraft";

    public ArcaneCraft() {

        // Init the RegistryHandler class
        // RegistryHandler.init();

        // Registers an event with the mod specific event bus. This is needed to register new stuff.
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(AlterationManager::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
}
