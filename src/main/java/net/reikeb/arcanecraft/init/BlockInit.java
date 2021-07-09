package net.reikeb.arcanecraft.init;

import net.minecraft.block.Block;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.*;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.blocks.*;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            ArcaneCraft.MODID);

    public static final RegistryObject<CrystalOre> CRYSTAL_ORE = BLOCKS.register("crystal_ore", CrystalOre::new);
    public static final RegistryObject<CrystalBlock> CRYSTAL_BLOCK = BLOCKS.register("crystal_block", CrystalBlock::new);
    public static final RegistryObject<RunicPillar> RUNIC_PILLAR = BLOCKS.register("runic_pillar", RunicPillar::new);
    public static final RegistryObject<RunicStone> RUNIC_STONE = BLOCKS.register("runic_stone", RunicStone::new);
    public static final RegistryObject<RunicStoneSlab> RUNIC_STONE_SLAB = BLOCKS.register("runic_stone_slab", RunicStoneSlab::new);
    public static final RegistryObject<RunicStoneStairs> RUNIC_STONE_STAIRS = BLOCKS.register("runic_stone_stairs", RunicStoneStairs::new);
    public static final RegistryObject<RunicStoneWall> RUNIC_STONE_WALL = BLOCKS.register("runic_stone_wall", RunicStoneWall::new);
}
