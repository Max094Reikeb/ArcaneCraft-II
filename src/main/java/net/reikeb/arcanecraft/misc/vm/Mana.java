package net.reikeb.arcanecraft.misc.vm;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.PacketDistributor;
import net.reikeb.arcanecraft.capabilities.ManaStorage;
import net.reikeb.arcanecraft.events.local.PlayerManaEvent;
import net.reikeb.arcanecraft.network.NetworkManager;
import net.reikeb.arcanecraft.network.packets.CurrentManaPacket;
import net.reikeb.arcanecraft.network.packets.ManaProgressPacket;
import net.reikeb.arcanecraft.network.packets.MaxManaPacket;

public class Mana {

    /**
     * Adds a specific amount of mana to the current mana of a ServerPlayer
     *
     * @param manaStorage  The ManaStorage capability
     * @param serverPlayer The ServerPlayer we're adding the mana to
     * @param count        The number of current mana we're adding to the mana storage
     */
    public static void addCurrentMana(ManaStorage manaStorage, ServerPlayer serverPlayer, int count) {
        if (MinecraftForge.EVENT_BUS.post(new PlayerManaEvent.ManaChangeCurrent(serverPlayer, count))) return;

        // Add current mana
        int currentMana = manaStorage.getMana();
        currentMana += count;
        manaStorage.setMana(currentMana);
        NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                serverPlayer), new CurrentManaPacket(currentMana));

        // Update mana progress
        float manaProgress = Mth.clamp((float) currentMana / (float) manaStorage.getMaxMana(), 0.0F, 1.0F);
        setProgressMana(manaStorage, serverPlayer, manaProgress);
    }

    /**
     * Sets the current mana of a ServerPlayer to a specific amount
     *
     * @param manaStorage  The ManaStorage capability
     * @param serverPlayer The ServerPlayer we're setting the mana storage
     * @param count        The number of current mana we're setting the mana storage to
     */
    public static void setCurrentMana(ManaStorage manaStorage, ServerPlayer serverPlayer, int count) {
        if (MinecraftForge.EVENT_BUS.post(new PlayerManaEvent.ManaChangeCurrent(serverPlayer, count))) return;

        // Set current mana
        manaStorage.setMana(count);
        NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                serverPlayer), new CurrentManaPacket(count));

        // Update mana progress
        float manaProgress = Mth.clamp((float) count / (float) manaStorage.getMaxMana(), 0.0F, 1.0F);
        setProgressMana(manaStorage, serverPlayer, manaProgress);
    }

    /**
     * Adds a specific amount of mana to the current mana of a ServerPlayer
     *
     * @param manaStorage  The ManaStorage capability
     * @param serverPlayer The ServerPlayer we're adding the mana storage to
     * @param count        The number of maximum mana we're adding to the mana storage
     */
    public static void addMaxMana(ManaStorage manaStorage, ServerPlayer serverPlayer, double count) {
        if (MinecraftForge.EVENT_BUS.post(new PlayerManaEvent.ManaChangeMaximum(serverPlayer, count))) return;

        // Add maximum mana
        double maxMana = manaStorage.getMaxMana();
        maxMana += count;
        manaStorage.setMaxMana(maxMana);
        NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                serverPlayer), new MaxManaPacket((int) maxMana));

        // Update mana progress
        float manaProgress = Mth.clamp((float) manaStorage.getMana() / (float) maxMana, 0.0F, 1.0F);
        setProgressMana(manaStorage, serverPlayer, manaProgress);
    }

    /**
     * Sets the maximum mana of a ServerPlayer to a specific amount
     *
     * @param manaStorage  The ManaStorage capability
     * @param serverPlayer The ServerPlayer we're setting the mana storage
     * @param count        The number of maximum mana we're setting the mana storage to
     */
    public static void setMaxMana(ManaStorage manaStorage, ServerPlayer serverPlayer, double count) {
        if (MinecraftForge.EVENT_BUS.post(new PlayerManaEvent.ManaChangeMaximum(serverPlayer, count))) return;

        // Set maximum mana
        manaStorage.setMaxMana(count);
        NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                serverPlayer), new MaxManaPacket((int) count));

        // Update mana progress
        float manaProgress = Mth.clamp((float) manaStorage.getMana() / (float) count, 0.0F, 1.0F);
        setProgressMana(manaStorage, serverPlayer, manaProgress);
    }

    /**
     * Sets the mana progress of a ServerPlayer to a specific amount
     *
     * @param manaStorage  The ManaStorage capability
     * @param serverPlayer The ServerPlayer we're setting the mana storage
     * @param count        The number of mana progress we're setting the mana storage to
     */
    public static void setProgressMana(ManaStorage manaStorage, ServerPlayer serverPlayer, float count) {
        manaStorage.setManaProgress(count);
        NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                serverPlayer), new ManaProgressPacket(count));
    }
}
