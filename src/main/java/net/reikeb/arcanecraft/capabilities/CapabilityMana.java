package net.reikeb.arcanecraft.capabilities;

import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import net.reikeb.arcanecraft.ArcaneCraft;

import javax.annotation.Nonnull;

public class CapabilityMana {

    @CapabilityInject(IManaStorage.class)
    @Nonnull
    @SuppressWarnings("ConstantConditions")
    public static Capability<ManaStorage> MANA_CAPABILITY = null;
    public static final ResourceLocation MANA_CAPABILITY_NAME = ArcaneCraft.RL("mana_capability");

    private CapabilityMana() {
    }

    @Nonnull
    public static Capability<ManaStorage> getManaCapability() {
        return MANA_CAPABILITY;
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(IManaStorage.class);
    }
}
