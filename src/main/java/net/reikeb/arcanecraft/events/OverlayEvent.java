package net.reikeb.arcanecraft.events;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.misc.Keys;
import net.reikeb.maxilib.utils.Utils;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OverlayEvent {

    public static int currentManaValue;
    public static int maxManaValue;
    public static float manaProgress;

    /**
     * Texture position of mana bar
     */
    final static int MANA_BAR_U = 0;
    final static int MANA_BAR_V = 12; // Empty bar
    final static int MANA_BAR_V2 = 0; // Full bar
    final static int MANA_BAR_WIDTH = 180;
    final static int MANA_BAR_HEIGHT = 12;

    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent event) {
        if (!event.isCancelable() && event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            if (Minecraft.getInstance().player == null) return;
            if (!Minecraft.getInstance().player.isSpectator() && !Minecraft.getInstance().player.isCreative()) {
                Utils.bind(Keys.MANA_BARS);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.disableBlend();

                renderManaBar(event.getMatrixStack(), event.getWindow().getGuiScaledWidth(), event.getWindow().getGuiScaledHeight());
                RenderSystem.enableBlend();
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    private static void renderManaBar(PoseStack mStack, int widthWindow, int heightWindow) {
        int x = (widthWindow / 2) - 91;
        Minecraft.getInstance().getProfiler().push("manaBar");
        Utils.bind(Keys.MANA_BARS);
        int i = maxManaValue - currentManaValue;
        if (i >= 0) {
            int k = (int) (manaProgress * 183.0F);
            int l = heightWindow - 56 + 3;
            Minecraft.getInstance().gui.blit(mStack, x, l, MANA_BAR_U, MANA_BAR_V, MANA_BAR_WIDTH, MANA_BAR_HEIGHT);
            if (k > 0) {
                Minecraft.getInstance().gui.blit(mStack, x, l, MANA_BAR_U, MANA_BAR_V2, k, MANA_BAR_HEIGHT);
            }
        }

        Minecraft.getInstance().getProfiler().pop();
        if (currentManaValue > 0) {
            Minecraft.getInstance().getProfiler().push("manaValue");
            String s = "" + currentManaValue;
            int i1 = (widthWindow - Minecraft.getInstance().gui.getFont().width(s)) / 2;
            int j1 = heightWindow - 51 - 3;
            Minecraft.getInstance().gui.getFont().draw(mStack, s, (float) (i1 + 1), (float) j1, 0);
            Minecraft.getInstance().gui.getFont().draw(mStack, s, (float) (i1 - 1), (float) j1, 0);
            Minecraft.getInstance().gui.getFont().draw(mStack, s, (float) i1, (float) (j1 + 1), 0);
            Minecraft.getInstance().gui.getFont().draw(mStack, s, (float) i1, (float) (j1 - 1), 0);
            Minecraft.getInstance().gui.getFont().draw(mStack, s, (float) i1, (float) j1, 7001065);
            Minecraft.getInstance().getProfiler().pop();
        }
    }
}
