package net.reikeb.arcanecraft.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.core.Direction;

import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;

import javax.annotation.*;

public class ManaProvider<C extends ManaInterface, S extends Tag> implements ICapabilityProvider, INBTSerializable<S> {

    private final Capability<C> capability;
    private final LazyOptional<C> implementation;
    private final Direction direction;

    protected ManaProvider(@Nonnull final Capability<C> capability, @Nonnull final LazyOptional<C> implementation, @Nonnull final Direction direction) {
        this.capability = capability;
        this.implementation = implementation;
        this.direction = direction;
    }

    public static <C extends ManaInterface> ManaProvider<C, Tag> from(@Nonnull final Capability<C> capability, @Nonnull final NonNullSupplier<C> impl) {
        return from(capability, null, impl);
    }

    @Nonnull
    public static <C extends ManaInterface> ManaProvider<C, Tag> from(@Nonnull final Capability<C> capability, @Nullable final Direction direction, @Nonnull final NonNullSupplier<C> impl) {
        return new ManaProvider<>(capability, LazyOptional.of(impl), direction);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side) {
        if (cap == this.capability) return this.implementation.cast();
        return LazyOptional.empty();
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public S serializeNBT() {
        return (S) this.getInstance().serializeNBT();
    }

    @Override
    public void deserializeNBT(@Nonnull final S nbt) {
        this.getInstance().deserializeNBT((CompoundTag) nbt);
    }

    @Nonnull
    private C getInstance() {
        return this.implementation.orElseThrow(() -> new IllegalArgumentException("Unable to obtain capability instance"));
    }
}
