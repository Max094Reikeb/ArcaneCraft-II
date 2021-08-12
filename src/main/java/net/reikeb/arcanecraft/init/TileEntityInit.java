package net.reikeb.arcanecraft.init;

import net.minecraft.world.level.block.entity.BlockEntityType;

import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.tileentities.TileCastingTable;
import net.reikeb.arcanecraft.tileentities.TileScrollTable;
import net.reikeb.arcanecraft.tileentities.TileWandWorkbench;

public class TileEntityInit {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES,
            ArcaneCraft.MODID);

    public static final RegistryObject<BlockEntityType<TileCastingTable>> TILE_CASTING_TABLE = TILE_ENTITIES.register("casting_table", () ->
            BlockEntityType.Builder.of(TileCastingTable::new, BlockInit.CASTING_TABLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<TileScrollTable>> TILE_SCROLL_TABLE = TILE_ENTITIES.register("scroll_table", () ->
            BlockEntityType.Builder.of(TileScrollTable::new, BlockInit.SCROLL_TABLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<TileWandWorkbench>> TILE_WAND_WORKBENCH = TILE_ENTITIES.register("wand_workbench", () ->
            BlockEntityType.Builder.of(TileWandWorkbench::new, BlockInit.WAND_WORKBENCH.get()).build(null));
}