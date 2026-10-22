package com.eightsidedsquare.deeperdark.common.block;

import com.eightsidedsquare.deeperdark.common.properties.TripleBlockPart;
import com.eightsidedsquare.deeperdark.core.DeeperDarkBlocks;
import com.eightsidedsquare.deeperdark.core.DeeperDarkMod;
import com.eightsidedsquare.deeperdark.core.DeeperDarkProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class TurgidScreamingVaseBlock extends AncientVaseBaseBlock {

    public TurgidScreamingVaseBlock(Settings settings) {
        super(settings);
    }

    public static final EnumProperty<BlockHalf> HALF = Properties.BLOCK_HALF;

    @Override
    public float GetVaseHeight(BlockState state) {
        switch (((BlockHalf)state.get(HALF))) {
            case BOTTOM:
            default:
                return 2F;

            case TOP:

                return 1F;
        }

    }

//    @Override
//    public float GetVaseHeight(BlockState state) {
//        return 2F;
//    }
//

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(HALF); builder.add(GENERATED); builder.add(RINGING); builder.add(WATERLOGGED);
    }

    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (world.getBlockState(pos.up()).isOf(Blocks.AIR)) {
            return true;

        } else {
            return false;
        }
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity player, ItemStack itemStack) {

        world.setBlockState(pos,state.with(HALF,BlockHalf.BOTTOM),3);
        world.setBlockState(pos.up(), DeeperDarkBlocks.TURGID_SCREAMING_VASE.getDefaultState().with(HALF,BlockHalf.TOP).with(FACING, player.getHorizontalFacing().getOpposite()),3);

//        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(GENERATED, false)));



    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

        switch (((BlockHalf)state.get(HALF))) {
            case BOTTOM:
            default:
                VoxelShape shape = VoxelShapes.empty();
                shape = VoxelShapes.union(
                        VoxelShapes.cuboid(0, 0, 0, 1, 1, 1)
                );
                return shape;
            case TOP:

                VoxelShape shape1 = VoxelShapes.empty();
                shape1 = VoxelShapes.union(
                        VoxelShapes.cuboid(0, 0, 0, 1, 0.5, 1),
                        VoxelShapes.cuboid(0.1875, 0.5, 0.1875, 0.8125, 0.625, 0.8125)
                );
                return shape1;
        }

//        VoxelShape shape = VoxelShapes.empty();
//        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0, 0, 1, 1.5, 1));
//        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 1.5, 0.1875, 0.8125, 1.625, 0.8125));

//        return shape;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        player.incrementStat(Stats.MINED.getOrCreateStat(this));
        player.addExhaustion(0.005F);
//        world.playSound(player, pos, SoundEvents.BLOCK_SCULK_SHRIEKER_SHRIEK, SoundCategory.BLOCKS, 1, 1);
        if (world instanceof ServerWorld serverWorld) {



            Ring((ServerWorld) world,state,pos, (ServerPlayerEntity) player);

            if (state.get(HALF) == BlockHalf.BOTTOM) {
                if (world.getBlockState(pos.up()).isOf(DeeperDarkBlocks.TURGID_SCREAMING_VASE)) {

                    ((ServerWorld) world).breakBlock(pos.up(),false);
                }
            }

            if (state.get(HALF) == BlockHalf.TOP) {
                if (world.getBlockState(pos.down()).isOf(DeeperDarkBlocks.TURGID_SCREAMING_VASE)) {
                    world.playSound(player, pos, DeeperDarkMod.VASE_BREAK_SOUND, SoundCategory.BLOCKS, 1, 1);
                    ((ServerWorld) world).breakBlock(pos.down(),false);
                }
            }

        }



        super.onBreak(world, pos, state, player);
    }
}
