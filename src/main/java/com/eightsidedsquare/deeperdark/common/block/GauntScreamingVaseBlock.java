package com.eightsidedsquare.deeperdark.common.block;

import com.eightsidedsquare.deeperdark.common.properties.TripleBlockPart;
import com.eightsidedsquare.deeperdark.core.DeeperDarkBlocks;
import com.eightsidedsquare.deeperdark.core.DeeperDarkMod;
import com.eightsidedsquare.deeperdark.core.DeeperDarkProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class GauntScreamingVaseBlock extends AncientVaseBaseBlock {

    public static final EnumProperty<TripleBlockPart> THIRD = DeeperDarkProperties.TRIPLE_BLOCK_HALF;

    @Override
    public float GetVaseHeight(BlockState state) {
        switch (((TripleBlockPart)state.get(THIRD))) {
            case LOWER:
            default:
               return 3F;

            case MIDDLE:

                return 2F;
            case UPPER:

                return 1F;
        }

    }

    public GauntScreamingVaseBlock(Settings settings) {
        super(settings);
//        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(GENERATED, true).with(THIRD, TripleBlockPart.LOWER)));
    }

    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(THIRD); builder.add(GENERATED); builder.add(RINGING); builder.add(WATERLOGGED);
    }

    private static Boolean checkBlockPlace(BlockPos blockPos, World world) {
        if (blockPos.up().equals(Blocks.AIR.getDefaultState()) && blockPos.up(2).equals(Blocks.AIR.getDefaultState())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (world.getBlockState(pos.up()).isOf(Blocks.AIR) && world.getBlockState(pos.up(2)).isOf(Blocks.AIR)) {
            return true;

        } else {
            return false;
        }
    }


    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity player, ItemStack itemStack) {

        world.setBlockState(pos,state.with(THIRD,TripleBlockPart.LOWER),3);
        world.setBlockState(pos.up(),DeeperDarkBlocks.GAUNT_SCREAMING_VASE.getDefaultState().with(THIRD,TripleBlockPart.MIDDLE).with(FACING, player.getHorizontalFacing().getOpposite()),3);
        world.setBlockState(pos.up(2),DeeperDarkBlocks.GAUNT_SCREAMING_VASE.getDefaultState().with(THIRD,TripleBlockPart.UPPER).with(FACING, player.getHorizontalFacing().getOpposite()),3);

//        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(GENERATED, false)));


    }




    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // return SHAPE;
        //VoxelShape SHAPE = Block.createCuboidShape(3, 0, 3, 13, 38, 13);
        switch (((TripleBlockPart)state.get(THIRD))) {
            case LOWER:
            default:
                VoxelShape shape = VoxelShapes.empty();
                shape = VoxelShapes.union(
                        VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.8125, 1, 0.8125)
                );
                return shape;
            case MIDDLE:

                VoxelShape shape1 = VoxelShapes.empty();
                shape1 = VoxelShapes.union(
                        VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.8125, 1, 0.8125)
                );
                return shape1;
            case UPPER:

                VoxelShape shape2 = VoxelShapes.empty();
                shape2 = VoxelShapes.union(
                        VoxelShapes.cuboid(0.25, 0.5, 0.25, 0.75, 0.6875, 0.75),
                        VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.8125, 0.5, 0.8125),
                        VoxelShapes.cuboid(0.0625, 0.125, 0.5, 0.1875, 0.3125, 0.5),
                        VoxelShapes.cuboid(0.8125, 0.125, 0.5, 0.9375, 0.3125, 0.5)
                );
                return shape2;
        }
    }


    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        player.incrementStat(Stats.MINED.getOrCreateStat(this));
        player.addExhaustion(0.005F);
//        world.playSound(player, pos, SoundEvents.BLOCK_SCULK_SHRIEKER_SHRIEK, SoundCategory.BLOCKS, 1, 1);
        if (world instanceof ServerWorld serverWorld) {



            Ring((ServerWorld) world,state,pos, (ServerPlayerEntity) player);

            if (state.get(THIRD) == TripleBlockPart.LOWER) {
                if (world.getBlockState(pos.up()).isOf(DeeperDarkBlocks.GAUNT_SCREAMING_VASE) && world.getBlockState(pos.up(2)).isOf(DeeperDarkBlocks.GAUNT_SCREAMING_VASE)) {

                    ((ServerWorld) world).breakBlock(pos.up(),false);
                    ((ServerWorld) world).breakBlock(pos.up(2),false);
                 }
            }

            if (state.get(THIRD) == TripleBlockPart.MIDDLE) {
            if (world.getBlockState(pos.down()).isOf(DeeperDarkBlocks.GAUNT_SCREAMING_VASE) && world.getBlockState(pos.up()).isOf(DeeperDarkBlocks.GAUNT_SCREAMING_VASE)) {
                world.playSound(player, pos, DeeperDarkMod.VASE_BREAK_SOUND, SoundCategory.BLOCKS, 1, 1);
                ((ServerWorld) world).breakBlock(pos.down(),false);
                ((ServerWorld) world).breakBlock(pos.up(),false);
            }
            }

            if (state.get(THIRD) == TripleBlockPart.UPPER) {
            if (world.getBlockState(pos.down()).isOf(DeeperDarkBlocks.GAUNT_SCREAMING_VASE) && world.getBlockState(pos.down(2)).isOf(DeeperDarkBlocks.GAUNT_SCREAMING_VASE)) {
                ((ServerWorld) world).breakBlock(pos.down(),false);
                ((ServerWorld) world).breakBlock(pos.down(2),false);
            }
            }

        }



        super.onBreak(world, pos, state, player);
    }


//    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
//
//        if (state.get(THIRD) == TripleBlockPart.LOWER) {
//            if (world.getBlockState(pos.up()).isOf(DeeperDarkBlocks.GAUNT_SCREAMING_VASE) && world.getBlockState(pos.up(2)).isOf(DeeperDarkBlocks.GAUNT_SCREAMING_VASE)) {
//
//                ((ServerWorld) world).breakBlock(pos.up(),false);
//                ((ServerWorld) world).breakBlock(pos.up(2),false);
//            }
//        }
//        if (state.get(THIRD) == TripleBlockPart.MIDDLE) {
//            if (world.getBlockState(pos.down()).isOf(DeeperDarkBlocks.GAUNT_SCREAMING_VASE) && world.getBlockState(pos.up()).isOf(DeeperDarkBlocks.GAUNT_SCREAMING_VASE)) {
//                ((ServerWorld) world).breakBlock(pos.down(),false);
//                ((ServerWorld) world).breakBlock(pos.up(),false);
//            }
//        }
//
//        if (state.get(THIRD) == TripleBlockPart.UPPER) {
//            if (world.getBlockState(pos.down()).isOf(DeeperDarkBlocks.GAUNT_SCREAMING_VASE) && world.getBlockState(pos.down(2)).isOf(DeeperDarkBlocks.GAUNT_SCREAMING_VASE)) {
//                ((ServerWorld) world).breakBlock(pos.down(),false);
//                ((ServerWorld) world).breakBlock(pos.down(2),false);
//            }
//        }
//    }
}
