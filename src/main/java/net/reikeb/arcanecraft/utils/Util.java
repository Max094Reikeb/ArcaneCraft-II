package net.reikeb.arcanecraft.utils;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import net.minecraftforge.items.IItemHandler;

import net.reikeb.arcanecraft.IntegrationHelper;

import java.util.function.Predicate;

public class Util {

    public static BlockHitResult rayTrace(Level world, Player player, ClipContext.Fluid fluidHandling) {
        float f = player.xRot;
        float g = player.yRot;
        Vec3 vec3d = player.getPosition(1.0f);
        float h = (float) Math.cos(-g * 0.017453292F - 3.1415927F);
        float i = (float) Math.sin(-g * 0.017453292F - 3.1415927F);
        float j = (float) -Math.cos(-f * 0.017453292F);
        float k = (float) Math.sin(-f * 0.017453292F);
        float l = i * j;
        float n = h * j;
        Vec3 vec3d2 = new Vec3(l * 5.0D, k * 5.0D, n * 5.0D);
        return world.clip(new ClipContext(vec3d, vec3d.add(vec3d2), ClipContext.Block.OUTLINE, fluidHandling, player));
    }

    public static Entity rayTrace(Level world, Player player, double range) {
        Vec3 pos = player.getPosition(0f);
        Vec3 cam1 = player.getLookAngle();
        Vec3 cam2 = cam1.add(cam1.x * range, cam1.y * range, cam1.z * range);
        AABB aabb = player.getBoundingBox().expandTowards(cam1.scale(range)).inflate(1.0F, 1.0F, 1.0F);
        EntityHitResult ray = findEntity(world, player, pos, cam2, aabb, EntitySelector.LIVING_ENTITY_STILL_ALIVE, range);

        if (ray != null) {
            if (ray.getType() == HitResult.Type.ENTITY) {
                return ray.getEntity() instanceof LivingEntity && !(ray.getEntity() instanceof Player) ? ray.getEntity() : null;
            }
        }
        return null;
    }

    private static EntityHitResult findEntity(Level world, Player player, Vec3 pos, Vec3 look, AABB aabb, Predicate<Entity> filter, double range) {
        for (Entity entity1 : world.getEntities(player, aabb, filter)) {
            AABB mob = entity1.getBoundingBox().inflate(1.0F);
            if (intersect(pos, look, mob, range)) {
                return new EntityHitResult(entity1);
            }
        }
        return null;
    }

    private static boolean intersect(Vec3 pos, Vec3 look, AABB mob, double range) {
        Vec3 invDir = new Vec3(1f / look.x, 1f / look.y, 1f / look.z);

        boolean signDirX = invDir.x < 0;
        boolean signDirY = invDir.y < 0;
        boolean signDirZ = invDir.z < 0;

        Vec3 max = new Vec3(mob.maxX, mob.maxY, mob.maxZ);
        Vec3 min = new Vec3(mob.minX, mob.minY, mob.minZ);

        Vec3 bbox = signDirX ? max : min;
        double tmin = (bbox.x - pos.x) * invDir.x;
        bbox = signDirX ? min : max;
        double tmax = (bbox.x - pos.x) * invDir.x;
        bbox = signDirY ? max : min;
        double tymin = (bbox.y - pos.y) * invDir.y;
        bbox = signDirY ? min : max;
        double tymax = (bbox.y - pos.y) * invDir.y;

        if ((tmin > tymax) || (tymin > tmax)) {
            return false;
        }

        if (tymin > tmin) {
            tmin = tymin;
        }

        if (tymax < tmax) {
            tmax = tymax;
        }

        bbox = signDirZ ? max : min;
        double tzmin = (bbox.z - pos.z) * invDir.z;
        bbox = signDirZ ? min : max;
        double tzmax = (bbox.z - pos.z) * invDir.z;

        if ((tmin > tzmax) || (tzmin > tmax)) {
            return false;
        }
        if (tzmin > tmin) {
            tmin = tzmin;
        }
        if (tzmax < tmax) {
            tmax = tzmax;
        }
        if ((tmin < range) && (tmax > 0)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a player has an item in one of his Curios slot
     *
     * @param playerEntity The player
     * @param stack        The item we want
     * @return true if he has it, false if he hasn't
     */
    public static boolean hasCuriosItem(Player playerEntity, ItemStack stack) {
        IItemHandler curios = IntegrationHelper.getCurios(playerEntity);
        if (curios != null) {
            for (int i = 0; i < curios.getSlots(); i++) {
                ItemStack stacks = curios.getStackInSlot(i);
                if ((!stacks.isEmpty()) && (stacks.equals(stack))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void layoutPlayerInventorySlots(AbstractContainerMenu container, Inventory playerInv) {
        int si;
        int sj;
        for (si = 0; si < 3; ++si)
            for (sj = 0; sj < 9; ++sj)
                container.addSlot(new Slot(playerInv, sj + (si + 1) * 9, 8 + sj * 18, 84 + si * 18));
        for (si = 0; si < 9; ++si)
            container.addSlot(new Slot(playerInv, si, 8 + si * 18, 142));
    }

    public static ItemStack quickMoveStack(AbstractContainerMenu container, Player playerIn, int index, int slots) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = container.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < slots) {
                if (!container.moveItemStackTo(itemstack1, slots, container.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (!container.moveItemStackTo(itemstack1, 0, slots, false)) {
                if (index < slots + 27) {
                    if (!container.moveItemStackTo(itemstack1, slots + 27, container.slots.size(), true)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (!container.moveItemStackTo(itemstack1, slots, slots + 27, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                return ItemStack.EMPTY;
            }
            if (itemstack1.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerIn, itemstack1);
        }
        return itemstack;
    }
}