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

import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import net.reikeb.arcanecraft.capabilities.ManaManager;
import net.reikeb.arcanecraft.entities.*;
import net.reikeb.arcanecraft.init.EntityInit;
import net.reikeb.arcanecraft.init.WandInit;
import net.reikeb.arcanecraft.network.NetworkManager;
import net.reikeb.arcanecraft.network.packets.CurrentManaPacket;
import net.reikeb.arcanecraft.network.packets.WooMagicPacket;
import net.reikeb.arcanecraft.utils.Util;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CastSpell {

    private final Level world;
    private final Player playerEntity;

    private final double playerX;
    private final double playerY;
    private final double playerZ;

    static int healingTimer = 0;
    static int flameTimer = 0;

    public CastSpell(Level world, Player playerEntity, ItemStack wand) {
        this.world = world;
        this.playerEntity = playerEntity;
        this.playerX = playerEntity.getX();
        this.playerY = playerEntity.getY();
        this.playerZ = playerEntity.getZ();

        WandObject wandObject = SpellUtils.getWand(wand);
        boolean flag = false;
        AtomicInteger playerMana = new AtomicInteger();
        int spellMana = 0;

        List<SpellInstance> spells = SpellUtils.getSpell(wand);
        if (!spells.isEmpty()) {
            for (SpellInstance spellInstance : spells) {
                spellMana = spellInstance.getMana();
            }
        }

        playerEntity.getCapability(ManaManager.MANA_CAPABILITY, null).ifPresent(cap -> {
            playerMana.set(cap.getMana());
        });

        if ((playerMana.get() < spellMana) && (!playerEntity.isCreative())) return;

        if (wandObject == WandInit.EVOKER.get()) {
            ArrowEvokerEntity arrowEvokerEntity = new ArrowEvokerEntity(EntityInit.ARROW_EVOKER_ENTITY_ENTITY_TYPE, this.playerEntity, this.world);
            ArrowEvokerEntity arrowEvokerEntity1 = (ArrowEvokerEntity) shootArrow(arrowEvokerEntity, "entity.evoker.cast_spell", 0.7000000000000001f, 2.5, 0);
            arrowEvokerEntity1.pickup = AbstractArrow.Pickup.DISALLOWED;
            flag = true;

        } else if (wandObject == WandInit.FIRE.get()) {
            ArrowFireEntity arrowFireEntity = new ArrowFireEntity(EntityInit.ARROW_FIRE_ENTITY_ENTITY_TYPE, this.playerEntity, this.world);
            ArrowFireEntity arrowFireEntity1 = (ArrowFireEntity) shootArrow(arrowFireEntity, "entity.blaze.shoot", 0.7000000000000001f, 3, 0);
            arrowFireEntity1.pickup = AbstractArrow.Pickup.DISALLOWED;
            flag = true;

        } else if (wandObject == WandInit.ICE.get()) {
            ArrowIceEntity arrowIceEntity = new ArrowIceEntity(EntityInit.ARROW_ICE_ENTITY_ENTITY_TYPE, this.playerEntity, this.world);
            ArrowIceEntity arrowIceEntity1 = (ArrowIceEntity) shootArrow(arrowIceEntity, "entity.evoker.cast_spell", 0.7000000000000001f, 2, 0);
            arrowIceEntity1.pickup = AbstractArrow.Pickup.DISALLOWED;
            flag = true;

        } else if (wandObject == WandInit.LIFE_DRAIN.get()) {
            ArrowLifeEntity arrowLifeEntity = new ArrowLifeEntity(EntityInit.ARROW_LIFE_ENTITY_ENTITY_TYPE, this.playerEntity, this.world);
            ArrowLifeEntity arrowLifeEntity1 = (ArrowLifeEntity) shootArrow(arrowLifeEntity, "entity.illusioner.cast_spell", 0.4f, 2, 0);
            arrowLifeEntity1.pickup = AbstractArrow.Pickup.DISALLOWED;
            flag = true;

        } else if (wandObject == WandInit.LIGHTNING.get()) {
            ArrowLightningEntity arrowLightningEntity = new ArrowLightningEntity(EntityInit.ARROW_LIGHTNING_ENTITY_ENTITY_TYPE, this.playerEntity, this.world);
            ArrowLightningEntity arrowLightningEntity1 = (ArrowLightningEntity) shootArrow(arrowLightningEntity, "entity.illusioner.cast_spell", 0.6f, 3, 0);
            arrowLightningEntity1.pickup = AbstractArrow.Pickup.DISALLOWED;
            flag = true;

        } else if (wandObject == WandInit.PULL.get()) {
            Entity target = Util.rayTrace(this.world, this.playerEntity, 35D);
            if (target != null && target.distanceTo(this.playerEntity) > 5) {
                target.setDeltaMovement(this.playerEntity.getLookAngle().reverse().multiply(5, 5, 5));
            }
            flag = true;

        } else if (wandObject == WandInit.PROTECTION_CIRCLE.get()) {
            List<LivingEntity> livingEntities = this.world.getEntitiesOfClass(LivingEntity.class,
                    new AABB(playerX - 6, playerY - 6, playerZ - 6,
                            playerX + 6, playerY + 6, playerZ + 6),
                            EntitySelector.LIVING_ENTITY_STILL_ALIVE).stream().sorted(new Object() {
                                Comparator<Entity> compareDistOf(double x, double y, double z) {
                                    return Comparator.comparing(axis -> axis.distanceToSqr(x, y, z));
                                }}.compareDistOf(playerX, playerY, playerZ)).collect(Collectors.toList());
            for (LivingEntity entity : livingEntities) {
                if (!(entity instanceof Player)) entity.setDeltaMovement(entity.getLookAngle().reverse().multiply(0.7D,0,0.7D));
            }
            flag = true;

        }

        if (flag) {
            NetworkManager.INSTANCE.sendToServer(new WooMagicPacket());
            if (!playerEntity.isCreative()) {
                int finalSpellMana = spellMana;
                playerEntity.getCapability(ManaManager.MANA_CAPABILITY, null).ifPresent(cap -> {
                    cap.setMana(playerMana.get() - finalSpellMana);
                    NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                            (ServerPlayer) playerEntity), new CurrentManaPacket(cap.getMana() - finalSpellMana));
                });
            }
        }
    }

    public AbstractArrow shootArrow(AbstractArrow arrow, String sound, float power, double damage, int knockback) {
        arrow.shoot(this.playerEntity.getLookAngle().x, this.playerEntity.getLookAngle().y, this.playerEntity.getLookAngle().z, power * 2, 0);
        arrow.setSilent(true);
        arrow.setCritArrow(false);
        arrow.setBaseDamage(damage);
        arrow.setKnockback(knockback);
        this.world.addFreshEntity(arrow);
        this.world.playSound(null, this.playerX, this.playerY, this.playerZ,
                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(sound)),
                SoundSource.PLAYERS, 1, 1f / (this.world.random.nextFloat() * 0.5f + 1) + (power / 2));
        return arrow;
    }
}