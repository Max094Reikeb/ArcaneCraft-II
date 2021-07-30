package net.reikeb.arcanecraft.init;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.fmllegacy.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.containers.AltarContainer;
import net.reikeb.arcanecraft.containers.ScrollTableContainer;
import net.reikeb.arcanecraft.containers.WandWorkbenchContainer;

public class ContainerInit {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS,
            ArcaneCraft.MODID);

    public static final RegistryObject<MenuType<AltarContainer>> ALTAR_CONTAINER = CONTAINERS.register("altar", () -> registerContainer(AltarContainer::new));
    public static final RegistryObject<MenuType<ScrollTableContainer>> SCROLL_TABLE_CONTAINER = CONTAINERS.register("scroll_table", () -> registerContainer(ScrollTableContainer::new));
    public static final RegistryObject<MenuType<WandWorkbenchContainer>> WAND_WORKBENCH_CONTAINER = CONTAINERS.register("wand_workbench", () -> registerContainer(WandWorkbenchContainer::new));

    public static <T extends AbstractContainerMenu> MenuType<T> registerContainer(IContainerFactory<T> fact) {
        MenuType<T> type = new MenuType<T>(fact);
        return type;
    }
}
