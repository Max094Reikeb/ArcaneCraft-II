package net.reikeb.arcanecraft.init;

import net.minecraft.potion.Effect;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.*;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.potions.*;

public class PotionEffectInit {

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS,
            ArcaneCraft.MODID);

    public static final RegistryObject<Effect> CHARRED_STRIKE = EFFECTS.register("charred_strike", CharredStrikeEffect::new);
    public static final RegistryObject<Effect> DRUID_BLESSING = EFFECTS.register("druid_blessing", DruidBlessingEffect::new);
    public static final RegistryObject<Effect> SOUL_TRAPPER = EFFECTS.register("soul_trapper", SoulTrapperEffect::new);
}
