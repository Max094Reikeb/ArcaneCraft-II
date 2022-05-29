package net.reikeb.arcanecraft.events.block;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
        if (event.getWorld().getBlockState(event.getPos()) != Blocks.AMETHYST_CLUSTER.defaultBlockState()) return;
        if ((!event.getPlayer().getMainHandItem().getItem().equals(Items.GLASS_BOTTLE))
                && (!event.getPlayer().getOffhandItem().getItem().equals(Items.GLASS_BOTTLE))) return;

        event.getWorld().setBlockAndUpdate(event.getPos(), BlockInit.AMETHYST_FADED_CLUSTER.get().defaultBlockState());
        event.getPlayer().setItemInHand(event.getHand(), new ItemStack(ItemInit.ARCANE_ESSENCE.get()));
    }
}
