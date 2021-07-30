package net.reikeb.arcanecraft.capabilities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import net.reikeb.arcanecraft.ArcaneCraft;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = ArcaneCraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ManaManager {

    @CapabilityInject(ManaInterface.class)
    @Nonnull
    @SuppressWarnings("ConstantConditions")
    public static Capability<ManaInterface> MANA_CAPABILITY = null;
    public static final ResourceLocation MANA_CAPABILITY_NAME = ArcaneCraft.RL("mana_capability");

    private ManaManager() {
    }

    @Nonnull
    public static Capability<ManaInterface> getManaCapability() {
        return MANA_CAPABILITY;
    }

    public static void commonSetup(FMLCommonSetupEvent event) {
        registerCapabilities();
    }

    public static void registerCapabilities() {
        CapabilityManager.INSTANCE.register(ManaInterface.class);
    }

    @SubscribeEvent
    public static void onPlayerAttachCapabilities(@Nonnull final AttachCapabilitiesEvent<Entity> e) {
        if (!(e.getObject() instanceof Player)) return;
        if (e.getObject().getCapability(MANA_CAPABILITY).isPresent()) return;
        e.addCapability(MANA_CAPABILITY_NAME,
                ManaProvider.from(MANA_CAPABILITY, ManaCapability::new));
    }
}
