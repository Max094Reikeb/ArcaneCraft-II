package net.reikeb.arcanecraft.capabilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import net.reikeb.arcanecraft.ArcaneCraft;

import javax.annotation.*;

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
        CapabilityManager.INSTANCE.register(
                ManaInterface.class,
                new Capability.IStorage<ManaInterface>() {
                    @Nullable
                    @Override
                    public INBT writeNBT(Capability<ManaInterface> capability, ManaInterface instance, Direction side) {
                        return instance.serializeNBT();
                    }

                    @Override
                    public void readNBT(Capability<ManaInterface> capability, ManaInterface instance, Direction side, INBT nbt) {
                        instance.deserializeNBT((CompoundNBT) nbt);
                    }
                }, ManaCapability::new
        );
    }

    @SubscribeEvent
    public static void onPlayerAttachCapabilities(@Nonnull final AttachCapabilitiesEvent<Entity> e) {
        if (!(e.getObject() instanceof PlayerEntity)) return;
        e.addCapability(MANA_CAPABILITY_NAME,
                ManaProvider.from(MANA_CAPABILITY, ManaCapability::new));
    }
}
