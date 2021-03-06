package net.reikeb.arcanecraft.setup.client;

import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.reikeb.arcanecraft.init.EntityInit;
import net.reikeb.arcanecraft.misc.Keys;
import net.reikeb.arcanecraft.setup.client.renderer.AmethystArrowRenderer;
import net.reikeb.arcanecraft.setup.client.renderer.ManaOrbRenderer;

public class ResourcesSetup {

    /**
     * Setup entity's renderers
     */
    public static void setupRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityInit.FIRE_SPLASH.get(),
                rendererProvider -> new MobRenderer(rendererProvider, new SlimeModel<>(rendererProvider.bakeLayer(ModelLayers.SLIME)), 0f) {
                    @Override
                    public ResourceLocation getTextureLocation(Entity entity) {
                        return Keys.AIR;
                    }
                });

        // Setup arrows renderers
        event.registerEntityRenderer(EntityInit.AMETHYST_ARROW.get(), AmethystArrowRenderer::new);
        event.registerEntityRenderer(EntityInit.MANA_ORB.get(), ManaOrbRenderer::new);
        event.registerEntityRenderer(EntityInit.SPELL_ARROW.get(),
                renderManager -> new ThrownItemRenderer<>(renderManager, 1.0F, false));
    }
}
