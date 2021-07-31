package net.reikeb.arcanecraft.setup;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

import net.reikeb.arcanecraft.ArcaneCraft;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemGroups {

    public static final CreativeModeTab ARCANECRAFT = new CreativeModeTab("arcanecraft") {
        @OnlyIn(Dist.CLIENT)
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.AMETHYST_SHARD);
        }

        @OnlyIn(Dist.CLIENT)
        public boolean hasSearchBar() {
            return false;
        }
    };
}
