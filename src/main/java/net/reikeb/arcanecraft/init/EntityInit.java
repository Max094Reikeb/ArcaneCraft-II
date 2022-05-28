package net.reikeb.arcanecraft.init;

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

public class EntityInit {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
            ArcaneCraft.MODID);

    public static final EntityType<AbstractSpellArrow> ABSTRACT_SPELL_ARROW_TYPE = EntityType.Builder
            .<AbstractSpellArrow>of(AbstractSpellArrow::new, MobCategory.MISC)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .setCustomClientFactory(AbstractSpellArrow::new)
            .sized(0.5f, 0.5f)
            .build("spell_arrow");

    public static final EntityType<FireSplashEntity> FIRE_SPLASH_ENTITY_ENTITY_TYPE = EntityType.Builder
            .<FireSplashEntity>of(FireSplashEntity::new, MobCategory.MISC)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .setCustomClientFactory(FireSplashEntity::new)
            .sized(0.5f, 0.5f)
            .build("fire_splash");

    public static final EntityType<AmethystArrow> AMETHYST_ARROW_ENTITY_TYPE = EntityType.Builder
            .<AmethystArrow>of(AmethystArrow::new, MobCategory.MISC)
            .clientTrackingRange(4)
            .updateInterval(20)
            .sized(0.5F, 0.5F)
            .build("amethyst_arrow");

    public static final EntityType<ManaOrb> MANA_ORB_ENTITY_TYPE = EntityType.Builder
            .<ManaOrb>of(ManaOrb::new, MobCategory.MISC)
            .clientTrackingRange(6)
            .updateInterval(20)
            .sized(0.5F, 0.5F)
            .build("mana_orb");

    public static final RegistryObject<EntityType<AbstractSpellArrow>> SPELL_ARROW = ENTITIES.register("spell_arrow", () -> ABSTRACT_SPELL_ARROW_TYPE);
    public static final RegistryObject<EntityType<FireSplashEntity>> FIRE_SPLASH = ENTITIES.register("fire_splash", () -> FIRE_SPLASH_ENTITY_ENTITY_TYPE);
    public static final RegistryObject<EntityType<AmethystArrow>> AMETHYST_ARROW = ENTITIES.register("amethyst_arrow", () -> AMETHYST_ARROW_ENTITY_TYPE);
    public static final RegistryObject<EntityType<ManaOrb>> MANA_ORB = ENTITIES.register("mana_orb", () -> MANA_ORB_ENTITY_TYPE);
}
