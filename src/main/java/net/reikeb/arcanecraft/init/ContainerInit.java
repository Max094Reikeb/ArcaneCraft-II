package net.reikeb.arcanecraft.init;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.containers.CastingTableContainer;
import net.reikeb.arcanecraft.containers.ScrollTableContainer;
import net.reikeb.arcanecraft.containers.WandWorkbenchContainer;

public class ContainerInit {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ArcaneCraft.MODID);

    public static final RegistryObject<MenuType<CastingTableContainer>> CASTING_TABLE_CONTAINER = CONTAINERS.register("casting_table",
            () -> IForgeMenuType.create((id, inv, data) -> new CastingTableContainer(id, data.readBlockPos(), inv, inv.player)));
    public static final RegistryObject<MenuType<ScrollTableContainer>> SCROLL_TABLE_CONTAINER = CONTAINERS.register("scroll_table",
            () -> IForgeMenuType.create((id, inv, data) -> new ScrollTableContainer(id, data.readBlockPos(), inv, inv.player)));
    public static final RegistryObject<MenuType<WandWorkbenchContainer>> WAND_WORKBENCH_CONTAINER = CONTAINERS.register("wand_workbench",
            () -> IForgeMenuType.create((id, inv, data) -> new WandWorkbenchContainer(id, data.readBlockPos(), inv, inv.player)));
}
