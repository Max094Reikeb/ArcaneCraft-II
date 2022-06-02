package net.reikeb.arcanecraft.spell;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;
import net.reikeb.arcanecraft.Util;
import net.reikeb.arcanecraft.capabilities.CapabilityMana;
import net.reikeb.arcanecraft.capabilities.ManaStorage;
import net.reikeb.arcanecraft.entities.AbstractSpellArrow;
import net.reikeb.arcanecraft.init.SpellInit;
import net.reikeb.arcanecraft.init.WandInit;
import net.reikeb.arcanecraft.misc.vm.Mana;
import net.reikeb.arcanecraft.network.NetworkManager;
import net.reikeb.arcanecraft.network.packets.WooMagicPacket;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CastSpell {

    private final Level level;
    private final Player playerEntity;

    private final double playerX;
    private final double playerY;
    private final double playerZ;

    public CastSpell(Level level, Player playerEntity, ItemStack wand) {
        this.level = level;
        this.playerEntity = playerEntity;
        this.playerX = playerEntity.getX();
        this.playerY = playerEntity.getY();
        this.playerZ = playerEntity.getZ();

        WandObject wandObject = SpellUtils.getWand(wand);
        boolean flag = false;
        int spellMana = 0;

        ManaStorage manaStorage = playerEntity.getCapability(CapabilityMana.MANA_CAPABILITY).orElseThrow(() ->
                new IllegalStateException("Tried to get my capability but it wasn't there wtf"));

        List<SpellInstance> spells = SpellUtils.getSpell(wand);
        if (!spells.isEmpty()) {
            for (SpellInstance spellInstance : spells) {
                spellMana = spellInstance.getMana();
            }
        }

        if ((manaStorage.getMana() < spellMana) && (!playerEntity.isCreative())) return;

        if (wandObject == WandInit.EVOKER.get()) {
            AbstractSpellArrow arrowEvoker = new AbstractSpellArrow(SpellInit.EVOKER.get(), this.playerEntity, this.level);
            AbstractSpellArrow arrowEvoker1 = shootArrow(arrowEvoker, "entity.evoker.cast_spell", 0.7000000000000001f, 2.5, 0);
            arrowEvoker1.pickup = AbstractArrow.Pickup.DISALLOWED;
            flag = true;

        } else if (wandObject == WandInit.FIRE.get()) {
            AbstractSpellArrow arrowFire = new AbstractSpellArrow(SpellInit.FIRE.get(), this.playerEntity, this.level);
            AbstractSpellArrow arrowFire1 = shootArrow(arrowFire, "entity.blaze.shoot", 0.7000000000000001f, 3, 0);
            arrowFire1.pickup = AbstractArrow.Pickup.DISALLOWED;
            flag = true;

        } else if (wandObject == WandInit.ICE.get()) {
            AbstractSpellArrow arrowIce = new AbstractSpellArrow(SpellInit.ICE.get(), this.playerEntity, this.level);
            AbstractSpellArrow arrowIce1 = shootArrow(arrowIce, "entity.evoker.cast_spell", 0.7000000000000001f, 2, 0);
            arrowIce1.pickup = AbstractArrow.Pickup.DISALLOWED;
            flag = true;

        } else if (wandObject == WandInit.LIFE_DRAIN.get()) {
            AbstractSpellArrow arrowLife = new AbstractSpellArrow(SpellInit.LIFE_DRAIN.get(), this.playerEntity, this.level);
            AbstractSpellArrow arrowLife1 = shootArrow(arrowLife, "entity.illusioner.cast_spell", 0.4f, 2, 0);
            arrowLife1.pickup = AbstractArrow.Pickup.DISALLOWED;
            flag = true;

        } else if (wandObject == WandInit.LIGHTNING.get()) {
            AbstractSpellArrow arrowLightning = new AbstractSpellArrow(SpellInit.LIGHTNING.get(), this.playerEntity, this.level);
            AbstractSpellArrow arrowLightning1 = shootArrow(arrowLightning, "entity.illusioner.cast_spell", 0.6f, 3, 0);
            arrowLightning1.pickup = AbstractArrow.Pickup.DISALLOWED;
            flag = true;

        } else if (wandObject == WandInit.PULL.get()) {
            Entity target = Util.rayTrace(this.level, this.playerEntity, 35D);
            if (target != null && target.distanceTo(this.playerEntity) > 5) {
                target.setDeltaMovement(this.playerEntity.getLookAngle().reverse().multiply(5, 5, 5));
            }
            flag = true;

        } else if (wandObject == WandInit.PROTECTION_CIRCLE.get()) {
            List<LivingEntity> livingEntities = this.level.getEntitiesOfClass(LivingEntity.class,
                    new AABB(playerX - 6, playerY - 6, playerZ - 6,
                            playerX + 6, playerY + 6, playerZ + 6),
                    EntitySelector.LIVING_ENTITY_STILL_ALIVE).stream().sorted(new Object() {
                Comparator<Entity> compareDistOf(double x, double y, double z) {
                    return Comparator.comparing(axis -> axis.distanceToSqr(x, y, z));
                }
            }.compareDistOf(playerX, playerY, playerZ)).collect(Collectors.toList());
            for (LivingEntity entity : livingEntities) {
                if (!(entity instanceof Player))
                    entity.setDeltaMovement(entity.getLookAngle().reverse().multiply(0.7D, 0, 0.7D));
            }
            flag = true;

        }

        if (flag) {
            NetworkManager.INSTANCE.sendToServer(new WooMagicPacket());
            if (!playerEntity.isCreative()) {
                Mana.addCurrentMana(manaStorage, (ServerPlayer) playerEntity, -spellMana);
            }
        }
    }

    public AbstractSpellArrow shootArrow(AbstractSpellArrow arrow, String sound, float power, double damage, int knockback) {
        arrow.shoot(this.playerEntity.getLookAngle().x, this.playerEntity.getLookAngle().y, this.playerEntity.getLookAngle().z, power * 2, 0);
        arrow.setSilent(true);
        arrow.setCritArrow(false);
        arrow.setBaseDamage(damage);
        arrow.setKnockback(knockback);
        this.level.addFreshEntity(arrow);
        this.level.playSound(null, this.playerX, this.playerY, this.playerZ,
                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(sound)),
                SoundSource.PLAYERS, 1, 1f / (this.level.random.nextFloat() * 0.5f + 1) + (power / 2));
        return arrow;
    }
}