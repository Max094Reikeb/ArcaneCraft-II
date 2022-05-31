package net.reikeb.arcanecraft.setup;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.capabilities.CapabilityMana;
import net.reikeb.arcanecraft.capabilities.ManaProvider;
import net.reikeb.arcanecraft.capabilities.ManaStorage;
import net.reikeb.arcanecraft.misc.Keys;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeSetup {

    /**
     * Attach capabilities to players
     */
    @SubscribeEvent
    public static void onPlayerAttachCapabilities(@Nonnull final AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof Player)) return;
        if (event.getObject().getCapability(CapabilityMana.MANA_CAPABILITY).isPresent()) return;
        event.addCapability(Keys.MANA_CAPABILITY,
                ManaProvider.from(CapabilityMana.MANA_CAPABILITY, ManaStorage::new));
    }
}
