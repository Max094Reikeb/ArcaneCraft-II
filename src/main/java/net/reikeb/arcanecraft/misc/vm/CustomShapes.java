package net.reikeb.arcanecraft.misc.vm;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class CustomShapes {

    /**
     * VoxelShape of the Altar
     */
    public static VoxelShape Altar = Stream.of(
            Block.box(4, 3, 4, 12, 5, 12),
            Block.box(3, 0, 3, 13, 3, 13),
            Block.box(0, 5, 0, 16, 9, 16)
    ).reduce((v1, v2) -> {
        return Shapes.join(v1, v2, BooleanOp.OR);
    }).get();

    /**
     * VoxelShape of the Scroll Table
     */
    public static VoxelShape ScrollTable = Stream.of(
            Block.box(3, 3, 3, 13, 9, 13),
            Block.box(0, 9, 0, 16, 13, 16),
            Block.box(0, 0, 0, 16, 3, 16)
    ).reduce((v1, v2) -> {
        return Shapes.join(v1, v2, BooleanOp.OR);
    }).get();

    /**
     * VoxelShape of the Wand Workbench
     */
    public static VoxelShape WandWorkbench = Stream.of(
            Block.box(2, 0, 2, 14, 3, 14),
            Block.box(3, 3, 3, 13, 5, 13),
            Block.box(1, 5, 1, 15, 9, 15)
    ).reduce((v1, v2) -> {
        return Shapes.join(v1, v2, BooleanOp.OR);
    }).get();
}
