package net.reikeb.arcanecraft.events.block;

import net.minecraft.world.level.block.Blocks;

import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.init.BlockInit;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID)
public class BonemealUsed {

    @SubscribeEvent
    public static void onBonemealUsed(BonemealEvent event) {
        if (event.getBlock() != Blocks.GRASS_BLOCK.defaultBlockState()) return;
        if (Math.random() > 0.05) return;
        event.setCanceled(true);
        event.getWorld().setBlock(event.getPos().above(), BlockInit.MANA_PLANT.get().defaultBlockState(), 3);
    }
}
