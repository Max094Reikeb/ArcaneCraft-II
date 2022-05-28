package net.reikeb.arcanecraft.containers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.CapabilityItemHandler;
import net.reikeb.arcanecraft.blockentities.CastingTableBlockEntity;
import net.reikeb.maxilib.abs.AbstractContainer;
import net.reikeb.maxilib.inventory.Slots;

import static net.reikeb.arcanecraft.init.ContainerInit.CASTING_TABLE_CONTAINER;

public class CastingTableContainer extends AbstractContainer {

    public CastingTableBlockEntity castingTableBlockEntity;

    public CastingTableContainer(int id, BlockPos pos, Inventory inv, Player player) {
        super(CASTING_TABLE_CONTAINER.get(), id, 2);

        this.castingTableBlockEntity = (CastingTableBlockEntity) player.getCommandSenderWorld().getBlockEntity(pos);
        if (castingTableBlockEntity == null) return;

        castingTableBlockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            addSlot(new Slots(h, 0, 59, 33, 1));
            addSlot(new Slots(h, 1, 101, 32, 1));
        });

        this.layoutPlayerInventorySlots(inv);
    }
}
