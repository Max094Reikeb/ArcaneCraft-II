package net.reikeb.arcanecraft.init;

import net.minecraft.world.level.block.Block;

import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.blocks.*;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            ArcaneCraft.MODID);

    public static final RegistryObject<CastingTable> CASTING_TABLE = BLOCKS.register("casting_table", CastingTable::new);
    public static final RegistryObject<ScrollTable> SCROLL_TABLE = BLOCKS.register("scroll_table", ScrollTable::new);
    public static final RegistryObject<WandWorkbench> WAND_WORKBENCH = BLOCKS.register("wand_workbench", WandWorkbench::new);
    public static final RegistryObject<RunicStone> RUNIC_STONE = BLOCKS.register("runic_stone", RunicStone::new);
    public static final RegistryObject<RunicStoneSlab> RUNIC_STONE_SLAB = BLOCKS.register("runic_stone_slab", RunicStoneSlab::new);
    public static final RegistryObject<RunicStoneStairs> RUNIC_STONE_STAIRS = BLOCKS.register("runic_stone_stairs", RunicStoneStairs::new);
    public static final RegistryObject<RunicStoneWall> RUNIC_STONE_WALL = BLOCKS.register("runic_stone_wall", RunicStoneWall::new);
    public static final RegistryObject<ManaBerry> MANA_BERRY = BLOCKS.register("mana_berry", ManaBerry::new);
}
