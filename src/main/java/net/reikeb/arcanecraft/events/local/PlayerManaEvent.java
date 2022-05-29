package net.reikeb.arcanecraft.events.local;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.reikeb.arcanecraft.entities.ManaOrb;
import net.reikeb.arcanecraft.misc.vm.Mana;

/**
 * PlayerManaEvent is fired whenever an event involving player mana occurs. <br>
 * If a method utilizes this {@link net.minecraftforge.eventbus.api.Event} as its parameter, the method will
 * receive every child event of this class.<br>
 * <br>
 * All children of this event are fired on the {@link MinecraftForge#EVENT_BUS}.
 */
public class PlayerManaEvent extends PlayerEvent {

    public PlayerManaEvent(Player player) {
        super(player);
    }

    /**
     * This event is fired after the player collides with a mana orb, but before the player has been given the mana.
     * It can be cancelled, and no further processing will be done.
     */
    @Cancelable
    public static class PickupMana extends PlayerManaEvent {

        private final ManaOrb orb;

        public PickupMana(Player player, ManaOrb orb) {
            super(player);
            this.orb = orb;
        }

        public ManaOrb getOrb() {
            return orb;
        }

    }

    /**
     * This event is fired when the player's mana changes through the {@link Mana#setCurrentMana} or {@link Mana#addCurrentMana} methods.
     * It can be cancelled, and no further processing will be done.
     */
    @Cancelable
    public static class ManaChangeCurrent extends PlayerManaEvent {

        private int amount;

        public ManaChangeCurrent(ServerPlayer player, int amount) {
            super(player);
            this.amount = amount;
        }

        public int getAmount() {
            return this.amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

    }

    /**
     * This event is fired when the player's maximum mana changes through the {@link Mana#setMaxMana} or {@link Mana#addMaxMana} methods.
     * It can be cancelled, and no further processing will be done.
     */
    @Cancelable
    public static class ManaChangeMaximum extends PlayerManaEvent {

        private double amount;

        public ManaChangeMaximum(ServerPlayer player, double amount) {
            super(player);
            this.amount = amount;
        }

        public double getAmount() {
            return this.amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

    }
}
