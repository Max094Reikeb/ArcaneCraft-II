package net.reikeb.arcanecraft.init;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.*;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.items.*;
import net.reikeb.arcanecraft.setup.ItemGroups;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            ArcaneCraft.MODID);

    public static final RegistryObject<Item> CRYSTAL = ITEMS.register("crystal", Crystal::new);

    public static final RegistryObject<Item> CRYSTAL_DUST = ITEMS.register("crystal_dust", CrystalDust::new);

    public static final RegistryObject<Item> CRYSTAL_ORE_ITEM = ITEMS.register("crystal_ore", () ->
            new BlockItem(BlockInit.CRYSTAL_ORE.get(), new Item.Properties().tab(ItemGroups.ARCANECRAFT)));

    public static final RegistryObject<Item> CRYSTAL_SWORD = ITEMS.register("crystal_sword", CrystalSword::new);

    public static final RegistryObject<Item> CRYSTAL_PICKAXE = ITEMS.register("crystal_pickaxe", CrystalPickaxe::new);

    public static final RegistryObject<Item> CRYSTAL_AXE = ITEMS.register("crystal_axe", CrystalAxe::new);

    public static final RegistryObject<Item> CRYSTAL_SHOVEL = ITEMS.register("crystal_shovel", CrystalShovel::new);

    public static final RegistryObject<Item> CRYSTAL_HOE = ITEMS.register("crystal_hoe", CrystalHoe::new);

    public static final RegistryObject<Item> SACRIFICIAL_DAGGER = ITEMS.register("sacrificial_dagger", SacrificialDagger::new);

    public static final RegistryObject<Item> SOUL = ITEMS.register("soul", Soul::new);

    public static final RegistryObject<Item> INK_VIAL = ITEMS.register("ink_vial", InkVial::new);

    public static final RegistryObject<Item> ARCANE_SCROLL_FOCUS = ITEMS.register("arcane_scroll_focus", ArcaneScrollFocus::new);

    public static final RegistryObject<Item> BLANK_SCROLL = ITEMS.register("blank_scroll", BlankScroll::new);

    public static final RegistryObject<Item> ARCANE_SCROLL = ITEMS.register("arcane_scroll", ArcaneScroll::new);

    public static final RegistryObject<Item> CRYSTAL_BLOCK_ITEM = ITEMS.register("crystal_block", () ->
            new BlockItem(BlockInit.CRYSTAL_BLOCK.get(), new Item.Properties().tab(ItemGroups.ARCANECRAFT)));

    public static final RegistryObject<Item> RUNIC_PILLAR_ITEM = ITEMS.register("runic_pillar", () ->
            new BlockItem(BlockInit.RUNIC_PILLAR.get(), new Item.Properties().tab(ItemGroups.ARCANECRAFT)));

    public static final RegistryObject<Item> RUNIC_STONE_ITEM = ITEMS.register("runic_stone", () ->
            new BlockItem(BlockInit.RUNIC_STONE.get(), new Item.Properties().tab(ItemGroups.ARCANECRAFT)));

    public static final RegistryObject<Item> RUNIC_STONE_SLAB_ITEM = ITEMS.register("runic_stone_slab", () ->
            new BlockItem(BlockInit.RUNIC_STONE_SLAB.get(), new Item.Properties().tab(ItemGroups.ARCANECRAFT)));

    public static final RegistryObject<Item> RUNIC_STONE_STAIRS_ITEM = ITEMS.register("runic_stone_stairs", () ->
            new BlockItem(BlockInit.RUNIC_STONE_STAIRS.get(), new Item.Properties().tab(ItemGroups.ARCANECRAFT)));

    public static final RegistryObject<Item> RUNIC_STONE_WALL_ITEM = ITEMS.register("runic_stone_wall", () ->
            new BlockItem(BlockInit.RUNIC_STONE_WALL.get(), new Item.Properties().tab(ItemGroups.ARCANECRAFT)));
}
