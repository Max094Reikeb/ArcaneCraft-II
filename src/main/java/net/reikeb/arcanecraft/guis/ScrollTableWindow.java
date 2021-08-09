package net.reikeb.arcanecraft.guis;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.containers.ScrollTableContainer;
import net.reikeb.arcanecraft.network.NetworkManager;
import net.reikeb.arcanecraft.network.packets.ScrollWritingPacket;
import net.reikeb.arcanecraft.tileentities.TileScrollTable;
import net.reikeb.arcanecraft.utils.Util;

public class ScrollTableWindow extends AbstractWindow<ScrollTableContainer> {

    private static final ResourceLocation SCROLL_TABLE_GUI = ArcaneCraft.RL("textures/guis/scroll_table_gui.png");
    public TileScrollTable tileEntity;

    public ScrollTableWindow(ScrollTableContainer container, Inventory inv, Component title) {
        super(container, inv, title);
        this.tileEntity = container.getTileEntity();
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        Util.render(this, matrixStack, SCROLL_TABLE_GUI);
    }

    @Override
    public void init() {
        super.init();
        this.addRenderableWidget(new Button(this.leftPos + 112, this.topPos + 38, 20, 20,
                new TranslatableComponent("gui.arcanecraft.ok_button"), e -> {
            NetworkManager.INSTANCE.sendToServer(new ScrollWritingPacket());
        }));
    }
}
