package net.reikeb.arcanecraft.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.potions.CharredStrikeEffect;
import net.reikeb.arcanecraft.potions.DruidBlessingEffect;
import net.reikeb.arcanecraft.potions.ManaEffect;

public class PotionEffectInit {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS,
            ArcaneCraft.MODID);

    public static final RegistryObject<MobEffect> CHARRED_STRIKE = EFFECTS.register("charred_strike", CharredStrikeEffect::new);
    public static final RegistryObject<MobEffect> DRUID_BLESSING = EFFECTS.register("druid_blessing", DruidBlessingEffect::new);
    public static final RegistryObject<MobEffect> MANA = EFFECTS.register("mana", ManaEffect::new);
}
