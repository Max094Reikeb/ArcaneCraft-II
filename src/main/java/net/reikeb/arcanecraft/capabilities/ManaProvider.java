package net.reikeb.arcanecraft.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ManaProvider<C extends ManaStorage, S extends Tag> implements ICapabilityProvider, INBTSerializable<S> {

    private final Capability<C> capability;
    private final LazyOptional<C> implementation;
    private final Direction direction;

    protected ManaProvider(@Nonnull final Capability<C> capability, @Nonnull final LazyOptional<C> implementation, @Nonnull final Direction direction) {
        this.capability = capability;
        this.implementation = implementation;
        this.direction = direction;
    }

    public static <C extends ManaStorage> ManaProvider<C, Tag> from(@Nonnull final Capability<C> capability, @Nonnull final NonNullSupplier<C> impl) {
        return from(capability, null, impl);
    }

    @Nonnull
    public static <C extends ManaStorage> ManaProvider<C, Tag> from(@Nonnull final Capability<C> capability, @Nullable final Direction direction, @Nonnull final NonNullSupplier<C> impl) {
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
