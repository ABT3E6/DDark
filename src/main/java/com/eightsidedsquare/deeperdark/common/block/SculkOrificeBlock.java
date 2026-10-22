package com.eightsidedsquare.deeperdark.common.block;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class SculkOrificeBlock extends Block {
    public SculkOrificeBlock(Settings settings) {
        super(settings);
    }

    private static final VoxelShape RAYCAST_SHAPE = AbstractCauldronBlock.createCuboidShape(1.0, 4.0, 1.0, 15.0, 16.0, 15.0);
    protected static final VoxelShape OUTLINE_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.union(AbstractCauldronBlock.createCuboidShape(1.0, 0.0, 1.0, 15.0, 3.0, 15.0), RAYCAST_SHAPE), BooleanBiFunction.ONLY_FIRST);


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return RAYCAST_SHAPE;
    }


    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this)) {
//            entity.slowMovement(state, new Vec3d(0.9f, 1.5, 0.9f));
            entity.slowMovement(state, new Vec3d(0.25, 0.1f, 0.25));
//            if (world.isClient) {
//                boolean bl;
                Random random = world.getRandom();
//                boolean bl2 = bl = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();
//                if (bl && random.nextBoolean()) {
//                    world.addParticle(
//                            new BlockStateParticleEffect(ParticleTypes.BLOCK, state),
//                            entity.getX(), pos.getY() + 1, entity.getZ(),
//                            MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f, 0.05f, MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f
//                    );
//
////                    world.addParticle(ParticleTypes.SNOWFLAKE, entity.getX(), pos.getY() + 1, entity.getZ(), MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f, 0.05f, MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f);
//                }

//                if (random.nextBoolean()) {
                    world.addParticle(
                            new BlockStateParticleEffect(ParticleTypes.BLOCK, state),
                            entity.getX(), pos.getY() + 1, entity.getZ(),
                            MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f, 0.05f, MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f
                    );
//                }
//            }
        }
//        entity.setInPowderSnow(true);
        if (!world.isClient) {
//            if (entity.isOnFire() && (world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) || entity instanceof PlayerEntity) && entity.canModifyAt(world, pos)) {
//                world.breakBlock(pos, false);
//            }

            entity.damage(DamageSource.GENERIC,2f);
//            entity.setOnFire(false);
        }
    }
}
