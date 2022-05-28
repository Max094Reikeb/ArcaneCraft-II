package net.reikeb.arcanecraft.containers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.CapabilityItemHandler;
import net.reikeb.arcanecraft.blockentities.WandWorkbenchBlockEntity;
import net.reikeb.arcanecraft.init.ItemInit;
import net.reikeb.maxilib.abs.AbstractContainer;
import net.reikeb.maxilib.inventory.Slots;

import static net.reikeb.arcanecraft.init.ContainerInit.WAND_WORKBENCH_CONTAINER;

public class WandWorkbenchContainer extends AbstractContainer {

    public WandWorkbenchBlockEntity wandWorkbenchBlockEntity;

    public WandWorkbenchContainer(int id, BlockPos pos, Inventory inv, Player player) {
        super(WAND_WORKBENCH_CONTAINER.get(), id, 1);

        this.wandWorkbenchBlockEntity = (WandWorkbenchBlockEntity) player.getCommandSenderWorld().getBlockEntity(pos);
        if (wandWorkbenchBlockEntity == null) return;

        wandWorkbenchBlockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            addSlot(new Slots(h, 0, 79, 30, c -> c.getItem() == ItemInit.ARCANE_SCROLL.get(), 1));
            addSlot(new Slots(h, 1, 101, 32, 1));
        });

        this.layoutPlayerInventorySlots(inv);
    }

    public WandWorkbenchBlockEntity getBlockEntity() {
        return this.wandWorkbenchBlockEntity;
    }
}
