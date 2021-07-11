package net.reikeb.arcanecraft.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.*;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.spell.*;

import java.util.*;
import java.util.function.Supplier;

public class SpellInit {

    public static final DeferredRegister<Spell> SPELLS_DEFERRED_REGISTER = DeferredRegister.create(Spell.class, ArcaneCraft.MODID);
    public static Supplier<IForgeRegistry<Spell>> SPELLS_REGISTRY = SPELLS_DEFERRED_REGISTER.makeRegistry("spell", () ->
            new RegistryBuilder<Spell>().setMaxID(Integer.MAX_VALUE - 1).onAdd((owner, stage, id, obj, oldObj) ->
                    ArcaneCraft.LOGGER.info("Spell added: " + getName(obj).toString() + " ")
            ).setDefaultKey(new ResourceLocation(ArcaneCraft.MODID, "empty"))
    );

    public static final RegistryObject<Spell> EMPTY = SPELLS_DEFERRED_REGISTER.register("empty", () -> new Spell(TextFormatting.DARK_GRAY));
    public static final RegistryObject<Spell> EVOKER = SPELLS_DEFERRED_REGISTER.register("evoker", () -> new Spell(TextFormatting.DARK_PURPLE));
    public static final RegistryObject<Spell> FIRE = SPELLS_DEFERRED_REGISTER.register("fire", () -> new Spell(TextFormatting.GOLD));
    public static final RegistryObject<Spell> ICE = SPELLS_DEFERRED_REGISTER.register("ice", () -> new Spell(TextFormatting.AQUA));
    public static final RegistryObject<Spell> LIFE_DRAIN = SPELLS_DEFERRED_REGISTER.register("life_drain", () -> new Spell(TextFormatting.DARK_RED));
    public static final RegistryObject<Spell> LIGHTNING = SPELLS_DEFERRED_REGISTER.register("lightning", () -> new Spell(TextFormatting.YELLOW));

    public static <T extends IForgeRegistryEntry<?>> ResourceLocation getName(T type) {
        return Objects.requireNonNull(type.getRegistryName());
    }
}
