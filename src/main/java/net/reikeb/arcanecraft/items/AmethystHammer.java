package net.reikeb.arcanecraft.items;

import com.google.common.collect.ImmutableSet;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.misc.Tiers;
import net.reikeb.arcanecraft.setup.ItemGroups;
import net.reikeb.arcanecraft.utils.Util;

import java.util.Set;

public class AmethystHammer extends DiggerItem {

    private static final Set<Block> NOT_EFFECTIVE_BLOCKS = ImmutableSet.of(Blocks.BEDROCK, Blocks.LAVA, Blocks.WATER);
    private static final Tags.IOptionalNamedTag<Block> MINEABLE_WITH_HAMMER = BlockTags.createOptional(ArcaneCraft.RL("mineable/hammer"));

    public AmethystHammer() {
        super(1F, -2.8F, Tiers.AMETHYST_TIER, MINEABLE_WITH_HAMMER,
                new Item.Properties().tab(ItemGroups.ARCANECRAFT));
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return (toolAction.equals(ToolActions.SHOVEL_DIG) || (toolAction.equals(ToolActions.AXE_DIG)) ||
                (toolAction.equals(ToolActions.PICKAXE_DIG)) || (toolAction.equals(ToolActions.HOE_DIG)));
    }

    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity entity) {
        if (!world.isClientSide && state.getDestroySpeed(world, pos) != 0.0F) {
            stack.hurtAndBreak(1, entity, (e) -> {
                e.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
            attemptBreakNeighbors(world, pos, (Player) entity, NOT_EFFECTIVE_BLOCKS, false, 3);
        }
        return true;
    }

    public static void attemptBreakNeighbors(Level world, BlockPos pos, Player player, Set<Block> notEffectiveOn, boolean checkHarvestLevel, int radioImpar) {
        world.setBlockAndUpdate(pos, Blocks.GLASS.defaultBlockState());
        BlockHitResult trace = Util.rayTrace(world, player, ClipContext.Fluid.ANY);
        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

        if (trace.getType() == BlockHitResult.Type.BLOCK) {
            Direction face = trace.getDirection();


            for (int a = ((radioImpar - 1) / 2) * (-1); a <= ((radioImpar - 1) / 2); a++) {
                for (int b = ((radioImpar - 1) / 2) * (-1); b <= ((radioImpar - 1) / 2); b++) {
                    if (a == 0 && b == 0) continue;

                    BlockPos target = null;

                    if (face == Direction.UP || face == Direction.DOWN) target = pos.offset(a, 0, b);
                    if (face == Direction.NORTH || face == Direction.SOUTH) target = pos.offset(a, b, 0);
                    if (face == Direction.EAST || face == Direction.WEST) target = pos.offset(0, a, b);

                    attemptBreak(world, target, player, notEffectiveOn, checkHarvestLevel);
                }
            }
        }
    }

    public static void attemptBreak(Level world, BlockPos pos, Player player, Set<Block> notEffectiveOn, boolean checkHarvestLevel) {
        BlockState state = world.getBlockState(pos);

        boolean validHarvest = !checkHarvestLevel || player.getMainHandItem().isCorrectToolForDrops(state);
        boolean isEffective = !notEffectiveOn.contains(state.getBlock());
        boolean witherImmune = BlockTags.WITHER_IMMUNE.contains(state.getBlock());

        if (validHarvest && isEffective && !witherImmune) {
            Block.dropResources(state, world, pos, null, player, player.getMainHandItem());
            world.destroyBlock(pos, false);
        }
    }
}
