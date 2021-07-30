package net.reikeb.arcanecraft.utils;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import net.minecraftforge.items.IItemHandler;

import net.reikeb.arcanecraft.IntegrationHelper;

import java.util.function.Predicate;

public class Util {

    public static Entity rayTrace(Level world, Player player, double range) {
        Vec3 pos = player.getPosition(0f);
        Vec3 cam1 = player.getLookAngle();
        Vec3 cam2 = cam1.add(cam1.x * range, cam1.y * range, cam1.z * range);
        AABB aabb = player.getBoundingBox().expandTowards(cam1.scale(range)).inflate(1.0F, 1.0F, 1.0F);
        EntityHitResult ray = findEntity(world, player, pos, cam2, aabb, null, range);

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
}