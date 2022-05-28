package net.reikeb.arcanecraft.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.blockentities.CastingTableBlockEntity;
import net.reikeb.arcanecraft.blockentities.ScrollTableBlockEntity;
import net.reikeb.arcanecraft.blockentities.WandWorkbenchBlockEntity;

public class BlockEntityInit {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES,
            ArcaneCraft.MODID);

    public static final RegistryObject<BlockEntityType<CastingTableBlockEntity>> CASTING_TABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("casting_table", () ->
            BlockEntityType.Builder.of(CastingTableBlockEntity::new, BlockInit.CASTING_TABLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<ScrollTableBlockEntity>> SCROLL_TABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("scroll_table", () ->
            BlockEntityType.Builder.of(ScrollTableBlockEntity::new, BlockInit.SCROLL_TABLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<WandWorkbenchBlockEntity>> WAND_WORKBENCH_BLOCK_ENTITY = BLOCK_ENTITIES.register("wand_workbench", () ->
            BlockEntityType.Builder.of(WandWorkbenchBlockEntity::new, BlockInit.WAND_WORKBENCH.get()).build(null));
}
