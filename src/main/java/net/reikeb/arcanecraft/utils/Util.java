package net.reikeb.arcanecraft.utils;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import net.minecraftforge.items.IItemHandler;

import net.reikeb.arcanecraft.IntegrationHelper;

import java.util.function.Predicate;

public class Util {

    public static boolean amethystToolHurtEnemy(boolean action, LivingEntity entity) {
        SoundEvent grassSound = SoundEvents.GRASS_BREAK;
        if (grassSound == null) return false;
        entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), grassSound,
                SoundSource.NEUTRAL, (float) 100, (float) 100);
        return action;
    }

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

    public static void bind(ResourceLocation res) {
        RenderSystem.setShaderTexture(0, res);
    }
}