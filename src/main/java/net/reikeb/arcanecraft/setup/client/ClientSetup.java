package net.reikeb.arcanecraft.setup.client;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.SlimeModel;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.guis.*;
import net.reikeb.arcanecraft.init.*;
import net.reikeb.arcanecraft.spell.*;

import static net.reikeb.arcanecraft.init.ContainerInit.*;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void init(final FMLClientSetupEvent event) {
        // Connect Containers and Windows
        ScreenManager.register(ALTAR_CONTAINER.get(), AltarWindow::new);
        ScreenManager.register(SCROLL_TABLE_CONTAINER.get(), ScrollTableWindow::new);
        ScreenManager.register(WAND_WORKBENCH_CONTAINER.get(), WandWorkbenchWindow::new);

        // Make this deferred for unsafe threads
        event.enqueueWork(() -> {
            // Cutout
            RenderTypeLookup.setRenderLayer(BlockInit.ALTAR.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(BlockInit.RUNIC_PILLAR.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(BlockInit.SCROLL_TABLE.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(BlockInit.WAND_WORKBENCH.get(), RenderType.cutout());

            // Translucent
            RenderTypeLookup.setRenderLayer(BlockInit.CRYSTAL_BLOCK.get(), RenderType.translucent());

            // Item Properties
            ItemModelsProperties.register(ItemInit.ARCANE_SCROLL.get(), new ResourceLocation("spell"), (p_239426_0_, p_239426_1_, p_239426_2_) -> {
                for (SpellInstance spellInstance : SpellUtils.getSpell(p_239426_0_)) {
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
                    }
                }
                return 0.0F;
            });

            ItemModelsProperties.register(ItemInit.ARCANE_SCROLL_FOCUS.get(), new ResourceLocation("spell"), (p_239426_0_, p_239426_1_, p_239426_2_) -> {
                for (SpellInstance spellInstance : SpellUtils.getSpell(p_239426_0_)) {
                    if (spellInstance.getSpell() == SpellInit.FIRE.get()) {
                        return 1.0F;
                    } else if (spellInstance.getSpell() == SpellInit.ICE.get()) {
                        return 2.0F;
                    } else if (spellInstance.getSpell() == SpellInit.LIFE_DRAIN.get()) {
                        return 3.0F;
                    } else if (spellInstance.getSpell() == SpellInit.LIGHTNING.get()) {
                        return 4.0F;
                    }
                }
                return 0.0F;
            });

            ItemModelsProperties.register(ItemInit.WAND.get(), new ResourceLocation("spell"), (p_239426_0_, p_239426_1_, p_239426_2_) -> {
                for (SpellInstance spellInstance : SpellUtils.getSpell(p_239426_0_)) {
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
                    }
                }
                return 0.0F;
            });
        });
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.FIRE_SPLASH_ENTITY_ENTITY_TYPE,
                renderManager -> new MobRenderer(renderManager, new SlimeModel(0), 0f) {
                    @Override
                    public ResourceLocation getTextureLocation(Entity entity) {
                        return new ResourceLocation("arcanecraft:textures/air.png");
                    }
                });
    }
}
