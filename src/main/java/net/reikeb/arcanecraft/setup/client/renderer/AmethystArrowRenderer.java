package net.reikeb.arcanecraft.setup.client.renderer;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.reikeb.arcanecraft.entities.AmethystArrow;
import net.reikeb.arcanecraft.misc.Keys;

public class AmethystArrowRenderer extends ArrowRenderer<AmethystArrow> {

    public AmethystArrowRenderer(Context context) {
        super(context);
    }

    public ResourceLocation getTextureLocation(AmethystArrow arrow) {
        return Keys.AMETHYST_ARROW;
    }
}
