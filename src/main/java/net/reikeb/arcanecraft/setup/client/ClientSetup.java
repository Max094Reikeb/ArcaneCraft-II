package net.reikeb.arcanecraft.setup.client;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.IntegrationHelper;
import net.reikeb.arcanecraft.guis.AltarWindow;
import net.reikeb.arcanecraft.guis.ScrollTableWindow;
import net.reikeb.arcanecraft.guis.WandWorkbenchWindow;
import net.reikeb.arcanecraft.init.BlockInit;
import net.reikeb.arcanecraft.init.EntityInit;
import net.reikeb.arcanecraft.init.ItemInit;
import net.reikeb.arcanecraft.init.SpellInit;
import net.reikeb.arcanecraft.spell.SpellInstance;
import net.reikeb.arcanecraft.spell.SpellUtils;

import static net.reikeb.arcanecraft.init.ContainerInit.*;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void init(final FMLClientSetupEvent event) {
        // Connect Containers and Windows
        MenuScreens.register(ALTAR_CONTAINER.get(), AltarWindow::new);
        MenuScreens.register(SCROLL_TABLE_CONTAINER.get(), ScrollTableWindow::new);
        MenuScreens.register(WAND_WORKBENCH_CONTAINER.get(), WandWorkbenchWindow::new);

        // Make this deferred for unsafe threads
        event.enqueueWork(() -> {
            // Cutout
            ItemBlockRenderTypes.setRenderLayer(BlockInit.ALTAR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockInit.RUNIC_PILLAR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockInit.SCROLL_TABLE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockInit.WAND_WORKBENCH.get(), RenderType.cutout());

            // Item Properties
            ItemProperties.register(ItemInit.ARCANE_SCROLL.get(), new ResourceLocation("spell"), (stack, p_239426_1_, player, p_239426_2_) -> {
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
                    } else if (spellInstance.getSpell() == SpellInit.BOLT.get()) {
                        return 6.0F;
                    }
                }
                return 0.0F;
            });

            ItemProperties.register(ItemInit.ARCANE_SCROLL_FOCUS.get(), new ResourceLocation("spell"), (stack, p_239426_1_, player, p_239426_2_) -> {
                for (SpellInstance spellInstance : SpellUtils.getSpell(stack)) {
                    if (spellInstance.getSpell() == SpellInit.FIRE.get()) {
                        return 1.0F;
                    } else if (spellInstance.getSpell() == SpellInit.ICE.get()) {
                        return 2.0F;
                    } else if (spellInstance.getSpell() == SpellInit.LIFE_DRAIN.get()) {
                        return 3.0F;
                    } else if (spellInstance.getSpell() == SpellInit.LIGHTNING.get()) {
                        return 4.0F;
                    } else if (spellInstance.getSpell() == SpellInit.BOLT.get()) {
                        return 5.0F;
                    }
                }
                return 0.0F;
            });

        ItemProperties.register(ItemInit.WAND.get(), new ResourceLocation("spell"), (stack, p_239426_1_, player, p_239426_2_) -> {
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
                    } else if (spellInstance.getSpell() == SpellInit.BOLT.get()) {
                        return 6.0F;
                    }
                }
                return 0.0F;
            });
        });
    }

    @SubscribeEvent
    public static void registerModels(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityInit.FIRE_SPLASH_ENTITY_ENTITY_TYPE,
                rendererProvider -> new MobRenderer(rendererProvider, new SlimeModel<>(rendererProvider.bakeLayer(ModelLayers.SLIME)), 0f) {
                    @Override
                    public ResourceLocation getTextureLocation(Entity entity) {
                        return ArcaneCraft.RL("air");
                    }
                });

        // Setup arrows renderers
        event.registerEntityRenderer(EntityInit.ARROW_EVOKER_ENTITY_ENTITY_TYPE,
                renderManager -> new ThrownItemRenderer<>(renderManager, 1.0F, false));
        event.registerEntityRenderer(EntityInit.ARROW_FIRE_ENTITY_ENTITY_TYPE,
                renderManager -> new ThrownItemRenderer<>(renderManager, 1.0F, false));
        event.registerEntityRenderer(EntityInit.ARROW_ICE_ENTITY_ENTITY_TYPE,
                renderManager -> new ThrownItemRenderer<>(renderManager, 1.0F, false));
        event.registerEntityRenderer(EntityInit.ARROW_LIFE_ENTITY_ENTITY_TYPE,
                renderManager -> new ThrownItemRenderer<>(renderManager, 1.0F, false));
        event.registerEntityRenderer(EntityInit.ARROW_LIGHTNING_ENTITY_ENTITY_TYPE,
                renderManager -> new ThrownItemRenderer<>(renderManager, 1.0F, false));
    }

    @SubscribeEvent
    public static void textureSwitchEvent(TextureStitchEvent.Pre event) {
        if (event.getMap().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
            if (ModList.get().isLoaded(IntegrationHelper.CURIOS_MODID)) {
                event.addSprite(IntegrationHelper.CURIOS_EMPTY_RING);
            }
        }
    }
}
