package net.reikeb.arcanecraft.guis;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.containers.CastingTableContainer;
import net.reikeb.arcanecraft.tileentities.TileCastingTable;
import net.reikeb.arcanecraft.Util;
import net.reikeb.maxilib.utils.Utils;

public class CastingTableWindow extends AbstractWindow<CastingTableContainer> {

    private static final ResourceLocation CASTING_TABLE_GUI = ArcaneCraft.RL("textures/guis/casting_table_gui.png");
    private static final ResourceLocation ICON1 = ArcaneCraft.RL("textures/guis/icon1.png");
    private static final ResourceLocation ICON2 = ArcaneCraft.RL("textures/guis/icon2.png");
    public TileCastingTable tileEntity;

    public CastingTableWindow(CastingTableContainer container, Inventory inv, Component title) {
        super(container, inv, title, CASTING_TABLE_GUI);
        this.tileEntity = container.getTileEntity();
    }

    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        Utils.bind(GUI_TO_RENDER);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        RenderSystem.setShaderTexture(0, ICON1);
        this.blit(matrixStack, this.getGuiLeft() + 40, this.getGuiTop() + 2, 0, 0, 256, 256);
        RenderSystem.setShaderTexture(0, ICON2);
        this.blit(matrixStack, this.getGuiLeft() + 72, this.getGuiTop() + 28, 0, 0, 256, 256);
    }
}
