package net.reikeb.arcanecraft.events;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.capabilities.ManaManager;

import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OverlayEvent {

    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent event) {
        if (!event.isCancelable() && event.getType() == RenderGameOverlayEvent.ElementType.HELMET) {
            int posX = event.getWindow().getGuiScaledWidth() / 2;
            int posY = event.getWindow().getGuiScaledHeight() / 2;
            PlayerEntity entity = Minecraft.getInstance().player;

            if (entity == null) return;
            int playerMana = entity.getPersistentData().getInt("Mana");
            AtomicInteger playerManaMax = new AtomicInteger();
            entity.getCapability(ManaManager.MANA_CAPABILITY, null).ifPresent(cap ->
                    playerManaMax.set((int) cap.getMaxMana()));

            Minecraft.getInstance().font.draw(event.getMatrixStack(), " - " + playerMana + "/" + playerManaMax.get(), (float) (posX + -189), (float) (posY + 99), -4221208);
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableAlphaTest();

            Minecraft.getInstance().getTextureManager().bind(ArcaneCraft.RL("textures/overlay/crystal.png"));
            Minecraft.getInstance().gui.blit(event.getMatrixStack(), posX + -198, posY + 99, 0, 0, 256, 256);
            RenderSystem.depthMask(true);

            RenderSystem.enableDepthTest();
            RenderSystem.enableAlphaTest();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
