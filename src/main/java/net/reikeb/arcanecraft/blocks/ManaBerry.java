package net.reikeb.arcanecraft.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.reikeb.arcanecraft.entities.ManaOrb;
import net.reikeb.arcanecraft.init.ItemInit;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class ManaBerry extends BushBlock implements BonemealableBlock {

    public static final int MAX_AGE = 4;
    public static final IntegerProperty AGE_4 = IntegerProperty.create("age", 0, 4);
    protected static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(1.0D, 0.0D, 1.0D, 15.0D, 6.0D, 15.0D),
            Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D),
            Block.box(1.0D, 0.0D, 1.0D, 15.0D, 11.0D, 15.0D),
            Block.box(1.0D, 0.0D, 1.0D, 15.0D, 13.0D, 15.0D),
            Block.box(1.0D, 0.0D, 1.0D, 15.0D, 13.0D, 15.0D)};
    private final Supplier<Item> seedSupplier;

    public ManaBerry() {
        super(BlockBehaviour.Properties.of(Material.PLANT)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.HARD_CROP));
        this.seedSupplier = ItemInit.MANA_BERRY_SEEDS;
        this.registerDefaultState(this.getStateDefinition().any().setValue(AGE_4, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[state.getValue(AGE_4)];
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return state.is(Blocks.FARMLAND);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this.seedSupplier.get(), 1));
    }

    @Override
    public void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, Random random) {
        if (!serverLevel.isAreaLoaded(pos, 1)) return;
        if (serverLevel.getRawBrightness(pos, 0) >= 9) {
            float f = CropBlock.getGrowthSpeed(this, serverLevel, pos);
            if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(serverLevel, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
                int i = state.getValue(AGE_4);
                if (i < MAX_AGE) {
                    serverLevel.setBlock(pos, state.setValue(AGE_4, (i + 1)), 2);
                }
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(serverLevel, pos, state);
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if ((!worldIn.isClientSide) && (state.getValue(AGE_4) == MAX_AGE)) {
            worldIn.setBlockAndUpdate(pos, state.setValue(AGE_4, (state.getValue(AGE_4) - 1)));
            ManaOrb.award((ServerLevel) worldIn, Vec3.atCenterOf(pos), (worldIn.random.nextInt(4) + 1));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos pos, BlockState state) {
        return new ItemStack(this.seedSupplier.get());
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter blockGetter, BlockPos pos, BlockState state, boolean p_57033_) {
        return state.getValue(AGE_4) != MAX_AGE;
    }

    @Override
    public boolean isBonemealSuccess(Level level, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, Random random, BlockPos pos, BlockState state) {
        if ((state.getValue(AGE_4) + 1) > MAX_AGE) return;
        serverLevel.setBlockAndUpdate(pos, state.setValue(AGE_4, (state.getValue(AGE_4) + 1)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> state) {
        state.add(AGE_4);
    }

    @Override
    public net.minecraftforge.common.PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return net.minecraftforge.common.PlantType.CROP;
    }
}
