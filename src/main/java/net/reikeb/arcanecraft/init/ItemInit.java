package net.reikeb.arcanecraft.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.items.*;
import net.reikeb.arcanecraft.setup.ItemGroups;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            ArcaneCraft.MODID);

    public static final RegistryObject<Item> AMETHYST_DUST = ITEMS.register("amethyst_dust", AmethystDust::new);

    public static final RegistryObject<Item> AMETHYST_SWORD = ITEMS.register("amethyst_sword", AmethystSword::new);

    public static final RegistryObject<Item> AMETHYST_PICKAXE = ITEMS.register("amethyst_pickaxe", AmethystPickaxe::new);

    public static final RegistryObject<Item> AMETHYST_AXE = ITEMS.register("amethyst_axe", AmethystAxe::new);

    public static final RegistryObject<Item> AMETHYST_SHOVEL = ITEMS.register("amethyst_shovel", AmethystShovel::new);

    public static final RegistryObject<Item> AMETHYST_HOE = ITEMS.register("amethyst_hoe", AmethystHoe::new);

    public static final RegistryObject<Item> AMETHYST_HAMMER = ITEMS.register("amethyst_hammer", AmethystHammer::new);

    public static final RegistryObject<Item> INK_VIAL = ITEMS.register("ink_vial", InkVial::new);

    public static final RegistryObject<Item> ARCANE_SCROLL_FOCUS = ITEMS.register("arcane_scroll_focus", ArcaneScrollFocus::new);

    public static final RegistryObject<Item> BLANK_SCROLL = ITEMS.register("blank_scroll", BlankScroll::new);

    public static final RegistryObject<Item> ARCANE_SCROLL = ITEMS.register("arcane_scroll", ArcaneScroll::new);

    public static final RegistryObject<Item> WAND = ITEMS.register("wand", Wand::new);

    public static final RegistryObject<Item> GOLD_RING = ITEMS.register("gold_ring", GoldRing::new);

    public static final RegistryObject<Item> SPELL_BOOK = ITEMS.register("spell_book", SpellBook::new);

    public static final RegistryObject<Item> CASTING_TABLE_ITEM = ITEMS.register("casting_table", () ->
            new BlockItem(BlockInit.CASTING_TABLE.get(), new Item.Properties().tab(ItemGroups.ARCANECRAFT)));

    public static final RegistryObject<Item> SCROLL_TABLE_ITEM = ITEMS.register("scroll_table", () ->
            new BlockItem(BlockInit.SCROLL_TABLE.get(), new Item.Properties().tab(ItemGroups.ARCANECRAFT)));

    public static final RegistryObject<Item> WAND_WORKBENCH_ITEM = ITEMS.register("wand_workbench", () ->
            new BlockItem(BlockInit.WAND_WORKBENCH.get(), new Item.Properties().tab(ItemGroups.ARCANECRAFT)));

    public static final RegistryObject<Item> RUNIC_STONE_ITEM = ITEMS.register("runic_stone", () ->
            new BlockItem(BlockInit.RUNIC_STONE.get(), new Item.Properties().tab(ItemGroups.ARCANECRAFT)));

    public static final RegistryObject<Item> RUNIC_STONE_SLAB_ITEM = ITEMS.register("runic_stone_slab", () ->
            new BlockItem(BlockInit.RUNIC_STONE_SLAB.get(), new Item.Properties().tab(ItemGroups.ARCANECRAFT)));

    public static final RegistryObject<Item> RUNIC_STONE_STAIRS_ITEM = ITEMS.register("runic_stone_stairs", () ->
            new BlockItem(BlockInit.RUNIC_STONE_STAIRS.get(), new Item.Properties().tab(ItemGroups.ARCANECRAFT)));

    public static final RegistryObject<Item> RUNIC_STONE_WALL_ITEM = ITEMS.register("runic_stone_wall", () ->
            new BlockItem(BlockInit.RUNIC_STONE_WALL.get(), new Item.Properties().tab(ItemGroups.ARCANECRAFT)));

    public static final RegistryObject<Item> MANA_POTION = ITEMS.register("mana_potion", ManaPotion::new);

    public static final RegistryObject<Item> AMETHYST_ARROW = ITEMS.register("amethyst_arrow", AmethystArrowItem::new);

    public static final RegistryObject<Item> SPELL_BOOK_BANNER_PATTERN = ITEMS.register("spell_book_banner_pattern", SpellBookBannerPattern::new);

    public static final RegistryObject<Item> WAND_BANNER_PATTERN = ITEMS.register("wand_banner_pattern", WandBannerPattern::new);
}
