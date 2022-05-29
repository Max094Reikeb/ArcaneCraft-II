package net.reikeb.arcanecraft.init;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.entities.AbstractSpellArrow;
import net.reikeb.arcanecraft.entities.AmethystArrow;
import net.reikeb.arcanecraft.entities.FireSplashEntity;
import net.reikeb.arcanecraft.entities.ManaOrb;

import java.util.function.Supplier;

public class EntityInit {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
            ArcaneCraft.MODID);

    public static final RegistryObject<EntityType<AmethystArrow>> AMETHYST_ARROW = register("amethyst_arrow", () -> EntityType.Builder.<AmethystArrow>of(AmethystArrow::new, MobCategory.MISC).clientTrackingRange(4).updateInterval(20).sized(0.5F, 0.5F));
    public static final RegistryObject<EntityType<FireSplashEntity>> FIRE_SPLASH = register("fire_splash", () -> EntityType.Builder.of(FireSplashEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
    public static final RegistryObject<EntityType<ManaOrb>> MANA_ORB = register("mana_orb", () -> EntityType.Builder.<ManaOrb>of(ManaOrb::new, MobCategory.MISC).clientTrackingRange(6).updateInterval(20).sized(0.5F, 0.5F));
    public static final RegistryObject<EntityType<AbstractSpellArrow>> SPELL_ARROW = register("spell_arrow", () -> EntityType.Builder.<AbstractSpellArrow>of(AbstractSpellArrow::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> builder) {
        return ENTITIES.register(name, () -> builder.get().build(name));
    }
}
