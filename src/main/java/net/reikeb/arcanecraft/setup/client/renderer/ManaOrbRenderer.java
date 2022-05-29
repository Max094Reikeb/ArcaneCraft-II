package net.reikeb.arcanecraft.setup.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.reikeb.arcanecraft.entities.ManaOrb;
import net.reikeb.arcanecraft.misc.Keys;

public class ManaOrbRenderer extends EntityRenderer<ManaOrb> {

    private static final RenderType RENDER_TYPE;

    public ManaOrbRenderer(Context context) {
        super(context);
        this.shadowRadius = 0.15F;
        this.shadowStrength = 0.75F;
    }

    protected int getBlockLightLevel(ManaOrb manaOrb, BlockPos pos) {
        return Mth.clamp(super.getBlockLightLevel(manaOrb, pos) + 7, 0, 15);
    }

    public void render(ManaOrb manaOrb, float posX, float posY, PoseStack poseStack, MultiBufferSource bufferSource, int value) {
        poseStack.pushPose();
        int var7 = manaOrb.getIcon();
        float var8 = (float) (var7 % 4 * 16) / 64.0F;
        float var9 = (float) (var7 % 4 * 16 + 16) / 64.0F;
        float var10 = (float) (var7 / 4 * 16) / 64.0F;
        float var11 = (float) (var7 / 4 * 16 + 16) / 64.0F;
        float var16 = ((float) manaOrb.tickCount + posY) / 2.0F;
        int var17 = (int) ((Mth.sin(var16 + 0.0F) + 1.0F) * 0.5F * 255.0F);
        int var19 = (int) ((Mth.sin(var16 + 4.1887903F) + 1.0F) * 0.1F * 255.0F);
        poseStack.translate(0.0D, 0.10000000149011612D, 0.0D);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        poseStack.scale(0.3F, 0.3F, 0.3F);
        VertexConsumer var21 = bufferSource.getBuffer(RENDER_TYPE);
        PoseStack.Pose var22 = poseStack.last();
        Matrix4f var23 = var22.pose();
        Matrix3f var24 = var22.normal();
        vertex(var21, var23, var24, -0.5F, -0.25F, var17, 255, var19, var8, var11, value);
        vertex(var21, var23, var24, 0.5F, -0.25F, var17, 255, var19, var9, var11, value);
        vertex(var21, var23, var24, 0.5F, 0.75F, var17, 255, var19, var9, var10, value);
        vertex(var21, var23, var24, -0.5F, 0.75F, var17, 255, var19, var8, var10, value);
        poseStack.popPose();
        super.render(manaOrb, posX, posY, poseStack, bufferSource, value);
    }

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, float p_114612_, float p_114613_, int p_114614_, int p_114615_, int p_114616_, float p_114617_, float p_114618_, int p_114619_) {
        vertexConsumer.vertex(matrix4f, p_114612_, p_114613_, 0.0F).color(p_114614_, p_114615_, p_114616_, 128).uv(p_114617_, p_114618_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_114619_).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }

    public ResourceLocation getTextureLocation(ManaOrb manaOrb) {
        return Keys.MANA_ORB;
    }

    static {
        RENDER_TYPE = RenderType.itemEntityTranslucentCull(Keys.MANA_ORB);
    }
}
