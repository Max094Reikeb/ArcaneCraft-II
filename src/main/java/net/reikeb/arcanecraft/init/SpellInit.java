package net.reikeb.arcanecraft.init;

import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.spell.Spell;

import java.util.Objects;
import java.util.function.Supplier;

public class SpellInit {

    public static final DeferredRegister<Spell> SPELLS_DEFERRED_REGISTER = DeferredRegister.create(Spell.class, ArcaneCraft.MODID);

    public static Supplier<IForgeRegistry<Spell>> SPELLS_REGISTRY = SPELLS_DEFERRED_REGISTER.makeRegistry("spell", () ->
            new RegistryBuilder<Spell>().setMaxID(Integer.MAX_VALUE - 1).onAdd((owner, stage, id, obj, oldObj) ->
                    ArcaneCraft.LOGGER.info("Spell added: " + getName(obj).toString() + " ")
            ).setDefaultKey(ArcaneCraft.RL("empty"))
    );

    public static final RegistryObject<Spell> EMPTY = SPELLS_DEFERRED_REGISTER.register("empty", () -> new Spell(ChatFormatting.DARK_GRAY));
    public static final RegistryObject<Spell> EVOKER = SPELLS_DEFERRED_REGISTER.register("evoker", () -> new Spell(ChatFormatting.DARK_PURPLE));
    public static final RegistryObject<Spell> FIRE = SPELLS_DEFERRED_REGISTER.register("fire", () -> new Spell(ChatFormatting.GOLD));
    public static final RegistryObject<Spell> ICE = SPELLS_DEFERRED_REGISTER.register("ice", () -> new Spell(ChatFormatting.AQUA));
    public static final RegistryObject<Spell> LIFE_DRAIN = SPELLS_DEFERRED_REGISTER.register("life_drain", () -> new Spell(ChatFormatting.DARK_RED));
    public static final RegistryObject<Spell> LIGHTNING = SPELLS_DEFERRED_REGISTER.register("lightning", () -> new Spell(ChatFormatting.YELLOW));
    public static final RegistryObject<Spell> BOLT = SPELLS_DEFERRED_REGISTER.register("bolt", () -> new Spell(ChatFormatting.WHITE));

    public static <T extends IForgeRegistryEntry<?>> ResourceLocation getName(T type) {
        return Objects.requireNonNull(type.getRegistryName());
    }
}
