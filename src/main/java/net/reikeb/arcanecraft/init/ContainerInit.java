package net.reikeb.arcanecraft.init;

import net.minecraft.inventory.container.*;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.*;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.containers.*;

public class ContainerInit {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS,
            ArcaneCraft.MODID);

    public static final RegistryObject<ContainerType<AltarContainer>> ALTAR_CONTAINER = CONTAINERS.register("altar", () -> registerContainer(AltarContainer::new));
    public static final RegistryObject<ContainerType<ScrollTableContainer>> SCROLL_TABLE_CONTAINER = CONTAINERS.register("scroll_table", () -> registerContainer(ScrollTableContainer::new));

    public static <T extends Container> ContainerType<T> registerContainer(IContainerFactory<T> fact) {
        ContainerType<T> type = new ContainerType<T>(fact);
        return type;
    }
}
