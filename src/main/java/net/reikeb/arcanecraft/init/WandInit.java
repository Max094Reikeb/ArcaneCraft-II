package net.reikeb.arcanecraft.init;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.*;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.spell.*;

import java.util.Objects;
import java.util.function.Supplier;

public class WandInit {

    public static final DeferredRegister<WandObject> WAND_DEFERRED_REGISTER = DeferredRegister.create(WandObject.class, ArcaneCraft.MODID);

    public static Supplier<IForgeRegistry<WandObject>> WAND_REGISTRY = WAND_DEFERRED_REGISTER.makeRegistry("wand", () ->
            new RegistryBuilder<WandObject>().setMaxID(Integer.MAX_VALUE - 1).onAdd((owner, stage, id, obj, oldObj) ->
                    ArcaneCraft.LOGGER.info("Wand added: " + getName(obj).toString() + " ")
            ).setDefaultKey(ArcaneCraft.RL("empty"))
    );

    public static final RegistryObject<WandObject> EMPTY = WAND_DEFERRED_REGISTER.register("empty", WandObject::new);
    public static final RegistryObject<WandObject> EVOKER = WAND_DEFERRED_REGISTER.register("evoker", () -> new WandObject(Lazy.of(() -> new SpellInstance(SpellInit.EVOKER.get(), 4))));
    public static final RegistryObject<WandObject> FIRE = WAND_DEFERRED_REGISTER.register("fire", () -> new WandObject(Lazy.of(() -> new SpellInstance(SpellInit.FIRE.get(), 5))));
    public static final RegistryObject<WandObject> ICE = WAND_DEFERRED_REGISTER.register("ice", () -> new WandObject(Lazy.of(() -> new SpellInstance(SpellInit.ICE.get(), 2))));
    public static final RegistryObject<WandObject> LIFE_DRAIN = WAND_DEFERRED_REGISTER.register("life_drain", () -> new WandObject(Lazy.of(() -> new SpellInstance(SpellInit.LIFE_DRAIN.get(), 3))));
    public static final RegistryObject<WandObject> LIGHTNING = WAND_DEFERRED_REGISTER.register("lightning", () -> new WandObject(Lazy.of(() -> new SpellInstance(SpellInit.LIGHTNING.get(), 6))));
    public static final RegistryObject<WandObject> BOLT = WAND_DEFERRED_REGISTER.register("bolt", () -> new WandObject(Lazy.of(() -> new SpellInstance(SpellInit.BOLT.get(), 7))));

    public static <T extends IForgeRegistryEntry<?>> ResourceLocation getName(T type) {
        return Objects.requireNonNull(type.getRegistryName());
    }
}
