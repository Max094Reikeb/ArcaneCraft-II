package net.reikeb.arcanecraft.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import net.reikeb.arcanecraft.init.ItemInit;
import net.reikeb.arcanecraft.misc.CustomShapes;
import net.reikeb.arcanecraft.tileentities.TileWandWorkbench;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WandWorkbench extends Block implements EntityBlock {

    public static final BooleanProperty USED = BooleanProperty.create("used");

    public WandWorkbench() {
        super(Properties.of(Material.HEAVY_METAL)
                .sound(SoundType.METAL)
                .strength(1f, 10f)
                .lightLevel(s -> 0)
                .noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(USED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(USED);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return CustomShapes.WandWorkbench;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof TileWandWorkbench) {
                ((TileWandWorkbench) tileEntity).dropItems(world, pos);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        List<ItemStack> usedWorkbench = new ArrayList<>(Collections.singletonList(new ItemStack(this, 1)));
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        if (state.getValue(USED)) {
            usedWorkbench.add(new ItemStack(ItemInit.WAND.get(), 1));
        }
        return usedWorkbench;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        super.animateTick(state, world, pos, random);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (state.getValue(USED)) {
            for (int l = 0; l < 5; l++) {
                double d0 = (float) x + 0.5 + (random.nextFloat() - 0.5) * 0.3999999985098839D;
                double d1 = ((float) y + 0.7 + (random.nextFloat() - 0.5) * 0.3999999985098839D) + 0.5;
                double d2 = (float) z + 0.5 + (random.nextFloat() - 0.5) * 0.3999999985098839D;
                world.addParticle(ParticleTypes.ENCHANT, d0, d1, d2, 0, 0, 0);
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (!worldIn.isClientSide) {
            if ((!state.getValue(USED)) && (player.getItemInHand(handIn).getItem() == ItemInit.WAND.get())
                    && (player.getItemInHand(handIn).getOrCreateTag().getString("Wand").equals(""))) {
                player.setItemInHand(handIn, new ItemStack(Blocks.AIR));
                worldIn.setBlockAndUpdate(pos, state.setValue(USED, true));
                SoundEvent fillSound = SoundEvents.END_PORTAL_FRAME_FILL;
                if (fillSound == null) return InteractionResult.FAIL;
                worldIn.playSound(null, pos, fillSound, SoundSource.NEUTRAL, (float) 1, (float) 1);

            } else if (state.getValue(USED) && player.isShiftKeyDown()) {
                ItemEntity wandItem = new ItemEntity(worldIn, x, (y + 2), z, new ItemStack(ItemInit.WAND.get(), 1));
                wandItem.setPickUpDelay(10);
                worldIn.addFreshEntity(wandItem);
                worldIn.setBlockAndUpdate(pos, state.setValue(USED, false));

            } else if (state.getValue(USED)) {
                BlockEntity tile = worldIn.getBlockEntity(pos);
                if (tile instanceof TileWandWorkbench) {
                    NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) tile, pos);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        return tileEntity instanceof MenuProvider ? (MenuProvider) tileEntity : null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileWandWorkbench(pos, state);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        BlockEntity tileentity = world.getBlockEntity(pos);
        return tileentity != null && tileentity.triggerEvent(eventID, eventParam);
    }
}
