package net.reikeb.arcanecraft.items;

import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.level.block.entity.BannerPattern;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.setup.ItemGroups;

public class SpellBookBannerPattern extends BannerPatternItem {

    private static final BannerPattern SPELL_BOOK_PATTERN = ArcaneCraft.addBanner("spell_book");

    public SpellBookBannerPattern() {
        super(SPELL_BOOK_PATTERN, new Properties().stacksTo(1).tab(ItemGroups.ARCANECRAFT));
    }
}
