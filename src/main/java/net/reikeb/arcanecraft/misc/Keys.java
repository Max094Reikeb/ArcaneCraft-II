package net.reikeb.arcanecraft.misc;

import net.minecraft.resources.ResourceLocation;
import net.reikeb.arcanecraft.ArcaneCraft;

public class Keys {

    public static final ResourceLocation DEFAULT_NULL = new ResourceLocation("");
    public static final ResourceLocation EMPTY = ArcaneCraft.RL("empty");

    public static final ResourceLocation SPELL = new ResourceLocation("spell");

    public static final ResourceLocation MANA_CAPABILITY = ArcaneCraft.RL("mana_capability");

    // Advancement tags
    public static final ResourceLocation WOO_MAGIC_ADVANCEMENT = new ResourceLocation("arcanecraft:woo_magic");

    // GUIs resources
    public static final ResourceLocation CURIOS_EMPTY_RING = ArcaneCraft.RL("items/empty_ring_slot");
    public static final ResourceLocation MANA_BARS = ArcaneCraft.RL("textures/overlay/icons.png");
    public static final ResourceLocation CASTING_TABLE_GUI = ArcaneCraft.RL("textures/guis/casting_table_gui.png");
    public static final ResourceLocation CASTING_TABLE_ICON1 = ArcaneCraft.RL("textures/guis/icon1.png");
    public static final ResourceLocation CASTING_TABLE_ICON2 = ArcaneCraft.RL("textures/guis/icon2.png");
    public static final ResourceLocation SCROLL_TABLE_GUI = ArcaneCraft.RL("textures/guis/scroll_table_gui.png");
    public static final ResourceLocation WAND_WORKBENCH_GUI = ArcaneCraft.RL("textures/guis/wand_workbench_gui.png");

    // Renderers
    public static final ResourceLocation AIR = ArcaneCraft.RL("textures/air.png");
    public static final ResourceLocation AMETHYST_ARROW = ArcaneCraft.RL("textures/entity/projectiles/amethyst_arrow.png");
    public static final ResourceLocation MANA_ORB = ArcaneCraft.RL("textures/entity/mana_orb.png");

    // Tool tags
    public static final ResourceLocation NEEDS_AMETHYST_TOOL = ArcaneCraft.RL("needs_amethyst_tool");
    public static final ResourceLocation AMETHYST = ArcaneCraft.RL("amethyst");
    public static final ResourceLocation HAMMER = ArcaneCraft.RL("mineable/hammer");
}
