package net.reikeb.arcanecraft.capabilities;

import net.minecraft.util.Direction;

import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;

import javax.annotation.*;

public class ManaProvider<C> implements ICapabilityProvider {

    private final Capability<C> capability;
    private final LazyOptional<C> implementation;

    protected ManaProvider(@Nonnull final Capability<C> capability, @Nonnull final LazyOptional<C> implementation) {
        this.capability = capability;
        this.implementation = implementation;
    }

    public static <C> ManaProvider<C> from(@Nonnull final Capability<C> cap, @Nonnull final NonNullSupplier<C> impl) {
        return new ManaProvider<>(cap, LazyOptional.of(impl));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side) {
        if (cap == this.capability) return this.implementation.cast();
        return LazyOptional.empty();
    }
}
