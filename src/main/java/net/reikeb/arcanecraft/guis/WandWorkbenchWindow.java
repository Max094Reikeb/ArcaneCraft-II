package net.reikeb.arcanecraft.guis;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.containers.WandWorkbenchContainer;
import net.reikeb.arcanecraft.network.NetworkManager;
import net.reikeb.arcanecraft.network.packets.WandWorkbenchingPacket;
import net.reikeb.arcanecraft.tileentities.TileWandWorkbench;

public class WandWorkbenchWindow extends ContainerScreen<WandWorkbenchContainer> {

    private static final ResourceLocation WAND_WORKBENCH_GUI = new ResourceLocation(ArcaneCraft.MODID, "textures/guis/wand_workbench_gui.png");
    public TileWandWorkbench tileEntity;

    public WandWorkbenchWindow(WandWorkbenchContainer container, PlayerInventory inv, ITextComponent title) {
        super(container, inv, title);
        this.tileEntity = container.getTileEntity();
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        // Render nothing
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(WAND_WORKBENCH_GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void removed() {
        super.removed();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);
        this.addButton(new Button(this.leftPos + 78, this.topPos + 53, 20, 20,
                new TranslationTextComponent("gui.arcanecraft.ok_button"), e -> {
            NetworkManager.INSTANCE.sendToServer(new WandWorkbenchingPacket());
        }));
    }
}