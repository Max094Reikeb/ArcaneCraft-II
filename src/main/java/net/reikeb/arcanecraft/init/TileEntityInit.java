package net.reikeb.arcanecraft.init;

import net.minecraft.tileentity.TileEntityType;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.*;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.tileentities.*;

public class TileEntityInit {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES,
            ArcaneCraft.MODID);

    public static final RegistryObject<TileEntityType<TileAltar>> TILE_ALTAR = TILE_ENTITIES.register("altar", () ->
            TileEntityType.Builder.of(TileAltar::new, BlockInit.ALTAR.get()).build(null));
}