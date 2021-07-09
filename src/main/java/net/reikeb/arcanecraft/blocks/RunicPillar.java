package net.reikeb.arcanecraft.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.*;
import net.minecraft.world.*;

import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ForgeRegistries;

import net.reikeb.arcanecraft.init.ItemInit;

import java.util.*;

public class RunicPillar extends Block {

    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    protected static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 32, 13);

    public RunicPillar() {
        super(Properties.of(Material.STONE)
                .sound(SoundType.STONE)
                .strength(2f, 10f)
                .lightLevel(s -> 0)
                .harvestLevel(0)
                .noOcclusion()
                .harvestTool(ToolType.PICKAXE));
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(ACTIVE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE;
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
        if (state.getValue(ACTIVE)) {
            for (int l = 0; l < 8; ++l) {
                double d0 = (float) x + 0.5 + (random.nextFloat() - 0.5) * 0.8000000014901161D;
                double d1 = ((float) y + 0.7 + (random.nextFloat() - 0.5) * 0.8000000014901161D) + 0.5;
                double d2 = (float) z + 0.5 + (random.nextFloat() - 0.5) * 0.8000000014901161D;
                world.addParticle(ParticleTypes.ENCHANT, d0, d1, d2, 0, 0, 0);
            }
        }
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult hit) {
        super.use(state, world, pos, playerEntity, hand, hit);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (state.getValue(ACTIVE)) {
            world.setBlockAndUpdate(pos, state.setValue(ACTIVE, false));

            if (!world.isClientSide && !playerEntity.isCreative()) {
                ItemEntity crystalDust = new ItemEntity(world, x, (y + 3), z, new ItemStack(ItemInit.CRYSTAL_DUST.get(), 1));
                crystalDust.setPickUpDelay(10);
                world.addFreshEntity(crystalDust);
            }

            SoundEvent beaconSound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.beacon.deactivate"));
            if (beaconSound == null) return ActionResultType.FAIL;
            world.playSound(null, pos, beaconSound, SoundCategory.NEUTRAL, (float) 1, (float) 1);

        } else {
            if (playerEntity.getItemInHand(hand).getItem() == ItemInit.CRYSTAL_DUST.get()) {
                if (!playerEntity.isCreative()) {
                    int itemInHand = playerEntity.getItemInHand(hand).getCount();
                    playerEntity.setItemInHand(hand, (itemInHand > 1 ? new ItemStack(ItemInit.CRYSTAL_DUST.get(), (itemInHand - 1)) : ItemStack.EMPTY));
                }

                world.setBlockAndUpdate(pos, state.setValue(ACTIVE, true));

                SoundEvent enchantSound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.enchantment_table.use"));
                if (enchantSound == null) return ActionResultType.FAIL;
                world.playSound(null, pos, enchantSound, SoundCategory.NEUTRAL, (float) 1, (float) 1);
            }
        }

        return ActionResultType.SUCCESS;
    }
}
