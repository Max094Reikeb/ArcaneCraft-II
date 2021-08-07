package net.reikeb.arcanecraft.setup.client.renderer;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.entities.AmethystArrow;

public class AmethystArrowRenderer extends ArrowRenderer<AmethystArrow> {

    public static final ResourceLocation AMETHYST_ARROW_LOCATION = ArcaneCraft.RL("textures/entity/projectiles/amethyst_arrow.png");

    public AmethystArrowRenderer(Context context) {
        super(context);
    }

    public ResourceLocation getTextureLocation(AmethystArrow arrow) {
        return AMETHYST_ARROW_LOCATION;
    }
}
