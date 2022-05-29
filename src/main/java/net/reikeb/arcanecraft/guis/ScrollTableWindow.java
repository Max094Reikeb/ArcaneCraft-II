package net.reikeb.arcanecraft.guis;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.reikeb.arcanecraft.containers.ScrollTableContainer;
import net.reikeb.arcanecraft.misc.Keys;
import net.reikeb.arcanecraft.network.NetworkManager;
import net.reikeb.arcanecraft.network.packets.ScrollWritingPacket;
import net.reikeb.maxilib.abs.AbstractWindow;

public class ScrollTableWindow extends AbstractWindow<ScrollTableContainer> {

    public ScrollTableWindow(ScrollTableContainer container, Inventory inv, Component title) {
        super(container, inv, title, Keys.SCROLL_TABLE_GUI);
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
