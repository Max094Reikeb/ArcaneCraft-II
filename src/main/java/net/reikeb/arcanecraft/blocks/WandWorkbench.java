package net.reikeb.arcanecraft.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.*;
import net.minecraft.world.*;

import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

import net.reikeb.arcanecraft.init.ItemInit;
import net.reikeb.arcanecraft.misc.vm.CustomShapes;
import net.reikeb.arcanecraft.tileentities.TileWandWorkbench;

import java.util.*;

public class WandWorkbench extends Block {

    public static final BooleanProperty USED = BooleanProperty.create("used");

    public WandWorkbench() {
        super(Properties.of(Material.HEAVY_METAL)
                .sound(SoundType.METAL)
                .strength(1f, 10f)
                .lightLevel(s -> 0)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
                .noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(USED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(USED);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return CustomShapes.WandWorkbench;
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileEntity = world.getBlockEntity(pos);
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
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
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
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (!worldIn.isClientSide) {
            if ((!state.getValue(USED)) && (player.getItemInHand(handIn).getItem() == ItemInit.WAND.get())
                    && (player.getItemInHand(handIn).getOrCreateTag().getString("Wand").equals(""))) {
                player.setItemInHand(handIn, new ItemStack(Blocks.AIR));
                worldIn.setBlockAndUpdate(pos, state.setValue(USED, true));
                SoundEvent fillSound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.end_portal_frame.fill"));
                if (fillSound == null) return ActionResultType.FAIL;
                worldIn.playSound(null, pos, fillSound, SoundCategory.NEUTRAL, (float) 1, (float) 1);

            } else if (state.getValue(USED) && player.isShiftKeyDown()) {
                ItemEntity wandItem = new ItemEntity(worldIn, x, (y + 2), z, new ItemStack(ItemInit.WAND.get(), 1));
                wandItem.setPickUpDelay(10);
                worldIn.addFreshEntity(wandItem);
                worldIn.setBlockAndUpdate(pos, state.setValue(USED, false));

            } else if (state.getValue(USED)) {
                TileEntity tile = worldIn.getBlockEntity(pos);
                if (tile instanceof TileWandWorkbench) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tile, pos);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public INamedContainerProvider getMenuProvider(BlockState state, World worldIn, BlockPos pos) {
        TileEntity tileEntity = worldIn.getBlockEntity(pos);
        return tileEntity instanceof INamedContainerProvider ? (INamedContainerProvider) tileEntity : null;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileWandWorkbench();
    }

    @Override
    public boolean triggerEvent(BlockState state, World world, BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        TileEntity tileentity = world.getBlockEntity(pos);
        return tileentity != null && tileentity.triggerEvent(eventID, eventParam);
    }
}
