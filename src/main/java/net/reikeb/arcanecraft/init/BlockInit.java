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
    public static final RegistryObject<RunicStone> RUNIC_STONE = BLOCKS.register("runic_stone", RunicStone::new);
}
