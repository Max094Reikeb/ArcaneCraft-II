package net.reikeb.arcanecraft.guis;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.containers.WandWorkbenchContainer;
import net.reikeb.arcanecraft.network.NetworkManager;
import net.reikeb.arcanecraft.network.packets.WandWorkbenchingPacket;
import net.reikeb.arcanecraft.tileentities.TileWandWorkbench;
import net.reikeb.arcanecraft.utils.Util;

public class WandWorkbenchWindow extends AbstractWindow<WandWorkbenchContainer> {

    private static final ResourceLocation WAND_WORKBENCH_GUI = ArcaneCraft.RL("textures/guis/wand_workbench_gui.png");
    public TileWandWorkbench tileEntity;

    public WandWorkbenchWindow(WandWorkbenchContainer container, Inventory inv, Component title) {
        super(container, inv, title);
        this.tileEntity = container.getTileEntity();
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        Util.render(this, matrixStack, WAND_WORKBENCH_GUI);
    }

    @Override
    public void init() {
        super.init();
        this.addRenderableWidget(new Button(this.leftPos + 78, this.topPos + 53, 20, 20,
                new TranslatableComponent("gui.arcanecraft.ok_button"), e -> {
            NetworkManager.INSTANCE.sendToServer(new WandWorkbenchingPacket());
        }));
    }
}
