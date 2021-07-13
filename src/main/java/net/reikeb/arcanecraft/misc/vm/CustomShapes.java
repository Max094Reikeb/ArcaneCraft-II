package net.reikeb.arcanecraft.misc.vm;

import net.minecraft.block.Block;
import net.minecraft.util.math.shapes.*;

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
        return VoxelShapes.join(v1, v2, IBooleanFunction.OR);
    }).get();
}
