package net.reikeb.arcanecraft.init;

import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.spell.ScrollObject;
import net.reikeb.arcanecraft.spell.SpellInstance;

import java.util.Objects;
import java.util.function.Supplier;

public class ScrollInit {

    public static final DeferredRegister<ScrollObject> SCROLL_DEFERRED_REGISTER = DeferredRegister.create(ScrollObject.class, ArcaneCraft.MODID);

    public static Supplier<IForgeRegistry<ScrollObject>> SCROLL_REGISTRY = SCROLL_DEFERRED_REGISTER.makeRegistry("arcane_scroll", () ->
            new RegistryBuilder<ScrollObject>().setMaxID(Integer.MAX_VALUE - 1).onAdd((owner, stage, id, obj, oldObj) ->
                    ArcaneCraft.LOGGER.info("Scroll added: " + getName(obj).toString() + " ")
            ).setDefaultKey(ArcaneCraft.RL("empty"))
    );

    public static final RegistryObject<ScrollObject> EMPTY = SCROLL_DEFERRED_REGISTER.register("empty", ScrollObject::new);
    public static final RegistryObject<ScrollObject> EVOKER = SCROLL_DEFERRED_REGISTER.register("evoker", () -> new ScrollObject(Lazy.of(() -> new SpellInstance(SpellInit.EVOKER.get()))));
    public static final RegistryObject<ScrollObject> FIRE = SCROLL_DEFERRED_REGISTER.register("fire", () -> new ScrollObject(Lazy.of(() -> new SpellInstance(SpellInit.FIRE.get()))));
    public static final RegistryObject<ScrollObject> ICE = SCROLL_DEFERRED_REGISTER.register("ice", () -> new ScrollObject(Lazy.of(() -> new SpellInstance(SpellInit.ICE.get()))));
    public static final RegistryObject<ScrollObject> LIFE_DRAIN = SCROLL_DEFERRED_REGISTER.register("life_drain", () -> new ScrollObject(Lazy.of(() -> new SpellInstance(SpellInit.LIFE_DRAIN.get()))));
    public static final RegistryObject<ScrollObject> LIGHTNING = SCROLL_DEFERRED_REGISTER.register("lightning", () -> new ScrollObject(Lazy.of(() -> new SpellInstance(SpellInit.LIGHTNING.get()))));
    public static final RegistryObject<ScrollObject> PULL = SCROLL_DEFERRED_REGISTER.register("pull", () -> new ScrollObject(Lazy.of(() -> new SpellInstance(SpellInit.PULL.get()))));
    public static final RegistryObject<ScrollObject> PROTECTION_CIRCLE = SCROLL_DEFERRED_REGISTER.register("protection_circle", () -> new ScrollObject(Lazy.of(() -> new SpellInstance(SpellInit.PROTECTION_CIRCLE.get()))));

    public static <T extends IForgeRegistryEntry<?>> ResourceLocation getName(T type) {
        return Objects.requireNonNull(type.getRegistryName());
    }
}
