package net.reikeb.arcanecraft.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.entities.*;

public class EntityInit {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
            ArcaneCraft.MODID);

    public static final EntityType<ArrowEvokerEntity> ARROW_EVOKER_ENTITY_ENTITY_TYPE = EntityType.Builder
            .<ArrowEvokerEntity>of(ArrowEvokerEntity::new, MobCategory.MISC)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .setCustomClientFactory(ArrowEvokerEntity::new)
            .sized(0.5f, 0.5f)
            .build("arrow_evoker");

    public static final EntityType<ArrowFireEntity> ARROW_FIRE_ENTITY_ENTITY_TYPE = EntityType.Builder
            .<ArrowFireEntity>of(ArrowFireEntity::new, MobCategory.MISC)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .setCustomClientFactory(ArrowFireEntity::new)
            .sized(0.5f, 0.5f)
            .build("arrow_fire");

    public static final EntityType<ArrowIceEntity> ARROW_ICE_ENTITY_ENTITY_TYPE = EntityType.Builder
            .<ArrowIceEntity>of(ArrowIceEntity::new, MobCategory.MISC)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .setCustomClientFactory(ArrowIceEntity::new)
            .sized(0.5f, 0.5f)
            .build("arrow_ice");

    public static final EntityType<ArrowLifeEntity> ARROW_LIFE_ENTITY_ENTITY_TYPE = EntityType.Builder
            .<ArrowLifeEntity>of(ArrowLifeEntity::new, MobCategory.MISC)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .setCustomClientFactory(ArrowLifeEntity::new)
            .sized(0.5f, 0.5f)
            .build("arrow_life");

    public static final EntityType<ArrowLightningEntity> ARROW_LIGHTNING_ENTITY_ENTITY_TYPE = EntityType.Builder
            .<ArrowLightningEntity>of(ArrowLightningEntity::new, MobCategory.MISC)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .setCustomClientFactory(ArrowLightningEntity::new)
            .sized(0.5f, 0.5f)
            .build("arrow_lightning");

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

    public static final RegistryObject<EntityType<ArrowEvokerEntity>> ARROW_EVOKER = ENTITIES.register("arrow_evoker", () -> ARROW_EVOKER_ENTITY_ENTITY_TYPE);
    public static final RegistryObject<EntityType<ArrowFireEntity>> ARROW_FIRE = ENTITIES.register("arrow_fire", () -> ARROW_FIRE_ENTITY_ENTITY_TYPE);
    public static final RegistryObject<EntityType<ArrowIceEntity>> ARROW_ICE = ENTITIES.register("arrow_ice", () -> ARROW_ICE_ENTITY_ENTITY_TYPE);
    public static final RegistryObject<EntityType<ArrowLifeEntity>> ARROW_LIFE = ENTITIES.register("arrow_life", () -> ARROW_LIFE_ENTITY_ENTITY_TYPE);
    public static final RegistryObject<EntityType<ArrowLightningEntity>> ARROW_LIGHTNING = ENTITIES.register("arrow_lightning", () -> ARROW_LIGHTNING_ENTITY_ENTITY_TYPE);
    public static final RegistryObject<EntityType<FireSplashEntity>> FIRE_SPLASH = ENTITIES.register("fire_splash", () -> FIRE_SPLASH_ENTITY_ENTITY_TYPE);
    public static final RegistryObject<EntityType<AmethystArrow>> AMETHYST_ARROW = ENTITIES.register("amethyst_arrow", () -> AMETHYST_ARROW_ENTITY_TYPE);
    public static final RegistryObject<EntityType<ManaOrb>> MANA_ORB = ENTITIES.register("mana_orb", () -> MANA_ORB_ENTITY_TYPE);
}
