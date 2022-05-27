package net.reikeb.arcanecraft;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.items.IItemHandler;
import net.reikeb.arcanecraft.curios.CuriosCompat;

import javax.annotation.Nullable;

public class IntegrationHelper {

    public static final String CURIOS_MODID = "curios";

    public static final ResourceLocation CURIOS_EMPTY_RING = ArcaneCraft.RL("items/empty_ring_slot");

    public static void registerMods(InterModEnqueueEvent event) {
        ModList modList = ModList.get();
        if (modList.isLoaded(CURIOS_MODID)) {
            CuriosCompat.registerCuriosSlots(event);
        }
    }

    @Nullable
    public static IItemHandler getCurios(Player player) {
        if (ModList.get().isLoaded(CURIOS_MODID)) {
            return CuriosCompat.getAll(player);
        }
        return null;
    }
}
