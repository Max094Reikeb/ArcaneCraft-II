package net.reikeb.arcanecraft.setup.client;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.IntegrationHelper;
import net.reikeb.arcanecraft.guis.CastingTableWindow;
import net.reikeb.arcanecraft.guis.ScrollTableWindow;
import net.reikeb.arcanecraft.guis.WandWorkbenchWindow;
import net.reikeb.arcanecraft.init.BlockInit;
import net.reikeb.arcanecraft.init.ItemInit;
import net.reikeb.arcanecraft.init.SpellInit;
import net.reikeb.arcanecraft.misc.Keys;
import net.reikeb.arcanecraft.spell.SpellInstance;
import net.reikeb.arcanecraft.spell.SpellUtils;

import static net.reikeb.arcanecraft.init.ContainerInit.*;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void init(final FMLClientSetupEvent event) {
        // Connect Containers and Windows
        MenuScreens.register(CASTING_TABLE_CONTAINER.get(), CastingTableWindow::new);
        MenuScreens.register(SCROLL_TABLE_CONTAINER.get(), ScrollTableWindow::new);
        MenuScreens.register(WAND_WORKBENCH_CONTAINER.get(), WandWorkbenchWindow::new);

        // Make this deferred for unsafe threads
        event.enqueueWork(() -> {
            // Cutout
            ItemBlockRenderTypes.setRenderLayer(BlockInit.SCROLL_TABLE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockInit.WAND_WORKBENCH.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockInit.MANA_BERRY.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockInit.MANA_PLANT.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockInit.AMETHYST_FADED_CLUSTER.get(), RenderType.cutout());

            // Item Properties
            ItemProperties.register(ItemInit.ARCANE_SCROLL.get(), Keys.SPELL,
                    (stack, p_239426_1_, player, p_239426_2_) -> getSpellFloat(stack));

            ItemProperties.register(ItemInit.ARCANE_SCROLL_FOCUS.get(), Keys.SPELL, (stack, p_239426_1_, player, p_239426_2_) -> {
                for (SpellInstance spellInstance : SpellUtils.getSpell(stack)) {
                    if (spellInstance.getSpell() == SpellInit.FIRE.get()) {
                        return 1.0F;
                    } else if (spellInstance.getSpell() == SpellInit.ICE.get()) {
                        return 2.0F;
                    } else if (spellInstance.getSpell() == SpellInit.LIFE_DRAIN.get()) {
                        return 3.0F;
                    } else if (spellInstance.getSpell() == SpellInit.LIGHTNING.get()) {
                        return 4.0F;
                    } else if (spellInstance.getSpell() == SpellInit.PULL.get()) {
                        return 5.0F;
                    } else if (spellInstance.getSpell() == SpellInit.PROTECTION_CIRCLE.get()) {
                        return 6.0F;
                    }
                }
                return 0.0F;
            });

            ItemProperties.register(ItemInit.WAND.get(), Keys.SPELL,
                    (stack, p_239426_1_, player, p_239426_2_) -> getSpellFloat(stack));
        });
    }

    public static float getSpellFloat(ItemStack stack) {
        for (SpellInstance spellInstance : SpellUtils.getSpell(stack)) {
            if (spellInstance.getSpell() == SpellInit.EVOKER.get()) {
                return 1.0F;
            } else if (spellInstance.getSpell() == SpellInit.FIRE.get()) {
                return 2.0F;
            } else if (spellInstance.getSpell() == SpellInit.ICE.get()) {
                return 3.0F;
            } else if (spellInstance.getSpell() == SpellInit.LIFE_DRAIN.get()) {
                return 4.0F;
            } else if (spellInstance.getSpell() == SpellInit.LIGHTNING.get()) {
                return 5.0F;
            } else if (spellInstance.getSpell() == SpellInit.PULL.get()) {
                return 6.0F;
            } else if (spellInstance.getSpell() == SpellInit.PROTECTION_CIRCLE.get()) {
                return 7.0F;
            }
        }
        return 0.0F;
    }

    @SubscribeEvent
    public static void registerModels(EntityRenderersEvent.RegisterRenderers event) {
        ResourcesSetup.setupRenderers(event);
    }

    @SubscribeEvent
    public static void textureSwitchEvent(TextureStitchEvent.Pre event) {
        if (event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
            if (ModList.get().isLoaded(IntegrationHelper.CURIOS_MODID)) {
                event.addSprite(Keys.CURIOS_EMPTY_RING);
            }
        }
    }
}
