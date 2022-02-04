package net.reikeb.arcanecraft.events.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.init.BlockInit;
import net.reikeb.arcanecraft.init.ItemInit;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID)
public class BlockRightClicked {

    @SubscribeEvent
    public static void onBlockRightClicked(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getWorld();
        BlockPos pos = event.getPos();
        Player player = event.getPlayer();

        if (level.getBlockState(pos) != Blocks.AMETHYST_CLUSTER.defaultBlockState()) return;
        if ((!player.getMainHandItem().getItem().equals(Items.GLASS_BOTTLE))
                && (!player.getOffhandItem().getItem().equals(Items.GLASS_BOTTLE))) return;

        level.setBlockAndUpdate(pos, BlockInit.AMETHYST_FADED_CLUSTER.get().defaultBlockState());
        player.setItemInHand(event.getHand(), new ItemStack(ItemInit.ARCANE_ESSENCE.get()));
    }
}
