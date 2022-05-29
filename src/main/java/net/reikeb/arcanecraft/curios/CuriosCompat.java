package net.reikeb.arcanecraft.curios;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.items.IItemHandler;
import net.reikeb.arcanecraft.misc.Keys;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;

import javax.annotation.Nullable;

public class CuriosCompat {

    @Nullable
    public static IItemHandler getAll(LivingEntity living) {
        return CuriosApi.getCuriosHelper().getEquippedCurios(living).resolve().orElse(null);
    }

    public static void registerCuriosSlots(InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () ->
                new SlotTypeMessage.Builder(IBaubleItem.Type.RING.getIdentifier())
                        .icon(Keys.CURIOS_EMPTY_RING)
                        .priority(100)
                        .build());
    }
}
