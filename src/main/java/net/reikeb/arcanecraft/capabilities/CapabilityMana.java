package net.reikeb.arcanecraft.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.reikeb.arcanecraft.ArcaneCraft;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityMana {

    public static final Capability<ManaStorage> MANA_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    @Nonnull
    public static Capability<ManaStorage> getManaCapability() {
        return MANA_CAPABILITY;
    }

    public static void register(RegisterCapabilitiesEvent event) {
        event.register(IManaStorage.class);
    }
}
