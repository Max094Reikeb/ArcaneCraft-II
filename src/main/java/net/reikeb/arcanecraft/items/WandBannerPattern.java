package net.reikeb.arcanecraft.items;

import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.level.block.entity.BannerPattern;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.setup.ItemGroups;

public class WandBannerPattern extends BannerPatternItem {

    private static final BannerPattern WAND_PATTERN = ArcaneCraft.addBanner("wand");

    public WandBannerPattern() {
        super(WAND_PATTERN, new Properties().stacksTo(1).tab(ItemGroups.ARCANECRAFT));
    }
}
