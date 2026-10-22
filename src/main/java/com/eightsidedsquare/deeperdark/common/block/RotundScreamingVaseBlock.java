package com.eightsidedsquare.deeperdark.common.block;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class RotundScreamingVaseBlock extends AncientVaseBaseBlock {

    public RotundScreamingVaseBlock(Settings settings) {
        super(settings);
    }

    @Override
    public float GetVaseHeight(BlockState state) {
        return 1.5F;
    }


    private static final VoxelShape SHAPE = Block.createCuboidShape(2,0,2,14,12,14);
    private static final VoxelShape SHAPE2 = Block.createCuboidShape(4,12,4,12,16,12);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

        return VoxelShapes.union(SHAPE,SHAPE2);
    }


}
