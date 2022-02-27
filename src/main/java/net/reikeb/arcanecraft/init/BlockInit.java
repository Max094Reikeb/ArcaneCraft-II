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
    public static final RegistryObject<ManaPlant> MANA_PLANT = BLOCKS.register("mana_plant", ManaPlant::new);
    public static final RegistryObject<ManaBerry> MANA_BERRY = BLOCKS.register("mana_berry", ManaBerry::new);
    public static final RegistryObject<AmethystFadedCluster> AMETHYST_FADED_CLUSTER = BLOCKS.register("amethyst_faded_cluster", AmethystFadedCluster::new);
    public static final RegistryObject<RunedRunicStone1> RUNED_RUNIC_STONE_1 = BLOCKS.register("runed_runic_stone_1", RunedRunicStone1::new);
    public static final RegistryObject<RunedRunicStone2> RUNED_RUNIC_STONE_2 = BLOCKS.register("runed_runic_stone_2", RunedRunicStone2::new);
    public static final RegistryObject<RunedRunicStone3> RUNED_RUNIC_STONE_3 = BLOCKS.register("runed_runic_stone_3", RunedRunicStone3::new);
    public static final RegistryObject<RunedRunicStone4> RUNED_RUNIC_STONE_4 = BLOCKS.register("runed_runic_stone_4", RunedRunicStone4::new);
    public static final RegistryObject<RunedRunicStone5> RUNED_RUNIC_STONE_5 = BLOCKS.register("runed_runic_stone_5", RunedRunicStone5::new);
    public static final RegistryObject<RunedRunicStone6> RUNED_RUNIC_STONE_6 = BLOCKS.register("runed_runic_stone_6", RunedRunicStone6::new);
    public static final RegistryObject<RunedRunicStone7> RUNED_RUNIC_STONE_7 = BLOCKS.register("runed_runic_stone_7", RunedRunicStone7::new);
    public static final RegistryObject<RunedRunicStone8> RUNED_RUNIC_STONE_8 = BLOCKS.register("runed_runic_stone_8", RunedRunicStone8::new);
}
