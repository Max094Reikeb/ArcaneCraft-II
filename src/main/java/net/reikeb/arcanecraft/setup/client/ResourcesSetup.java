package net.reikeb.arcanecraft.setup.client;

import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import net.minecraftforge.client.event.EntityRenderersEvent;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.init.EntityInit;
import net.reikeb.arcanecraft.setup.client.renderer.AmethystArrowRenderer;
import net.reikeb.arcanecraft.setup.client.renderer.ManaOrbRenderer;

public class ResourcesSetup {

    /**
     * Setup entity's renderers
     */
    public static void setupRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityInit.FIRE_SPLASH_ENTITY_ENTITY_TYPE,
                rendererProvider -> new MobRenderer(rendererProvider, new SlimeModel<>(rendererProvider.bakeLayer(ModelLayers.SLIME)), 0f) {
                    @Override
                    public ResourceLocation getTextureLocation(Entity entity) {
                        return ArcaneCraft.RL("textures/air.png");
                    }
                });

        // Setup arrows renderers
        event.registerEntityRenderer(EntityInit.AMETHYST_ARROW_ENTITY_TYPE, AmethystArrowRenderer::new);
        event.registerEntityRenderer(EntityInit.MANA_ORB_ENTITY_TYPE, ManaOrbRenderer::new);

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
}
