package com.eightsidedsquare.deeperdark.common.block;

import com.eightsidedsquare.deeperdark.common.entity.AncientVaseBlockEntity;
import com.eightsidedsquare.deeperdark.common.properties.TripleBlockPart;
import com.eightsidedsquare.deeperdark.core.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SculkSensorPhase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.VibrationParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockStateRaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.VibrationListener;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Stream;

public class AncientVaseBaseBlock extends BlockWithEntity implements Waterloggable {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public static final BooleanProperty GENERATED = BooleanProperty.of("generated");


    public static final BooleanProperty RINGING = DeeperDarkProperties.RINGING;
    private static final int RING_DELAY = 90;

    public static final Integer Face = null;

    private final int range = 30;

    public  float GetVaseHeight(BlockState state) {
        return 1F;
    }


    public static void HandleLogic(ServerWorld serverWorld, BlockState state, BlockPos pos, @Nullable ServerPlayerEntity player) {
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
        Stream<BlockPos> sensors = BlockPos.stream(pos.add(-10, -10, -10), pos.add(10, 10, 10));
        sensors.forEach(blockPos -> {
            BlockState blockState = serverWorld.getBlockState(blockPos);


            if (blockState.getBlock() == Blocks.SCULK_SENSOR) {
                serverWorld.getBlockEntity(blockPos, BlockEntityType.SCULK_SENSOR).ifPresent((blockEntity) -> {


                    GameEvent.Message mess = new GameEvent.Message(GameEvent.BLOCK_DESTROY, player.getPos(), GameEvent.Emitter.of(player), blockEntity.getEventListener(),new Vec3d(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5));

                    blockEntity.getEventListener().listen(serverWorld, mess);

                });


            }
        });

//        Stream<BlockPos> vases = BlockPos.stream(pos.add(-8, -8, -8), pos.add(8, 8, 8));
//        vases.forEach(blockPos -> {
//            BlockState blockState = serverWorld.getBlockState(blockPos);
//
//
//            if (blockState.getBlock() == DeeperDarkBlocks.ROTUND_SCREAMING_VASE) {
//                serverWorld.getBlockEntity(blockPos, DeeperDarkBlockEntities.ROTUND_SCREAMING_VASE).ifPresent((blockEntity) -> {
//
//
//                    GameEvent.Message mess = new GameEvent.Message(GameEvent.BLOCK_DESTROY, Vec3d.ofCenter(pos), GameEvent.Emitter.of(player), blockEntity.getEventListener(),Vec3d.ofCenter(blockPos));
//
//                    blockEntity.getEventListener().listen(serverWorld, mess);
//
//                });
//
//
//            }
//        });
    }

    public static void Ring(ServerWorld serverWorld, BlockState state, BlockPos pos, @Nullable ServerPlayerEntity player) {

        if (!(Boolean)state.get(AncientVaseBaseBlock.RINGING)) {
            float VaseHeight = 1F;
            boolean canRing = false;
            if (state.getBlock() == DeeperDarkBlocks.ROTUND_SCREAMING_VASE && !state.get(AncientVaseBaseBlock.RINGING)) {
                VaseHeight = 1.5F;
                canRing = true;
            }
            if (state.getBlock() == DeeperDarkBlocks.TURGID_SCREAMING_VASE && !state.get(AncientVaseBaseBlock.RINGING)) {
                if ((state.get(TurgidScreamingVaseBlock.HALF)== BlockHalf.BOTTOM)) {
                    VaseHeight = 2.15F;
                }
                if ((state.get(TurgidScreamingVaseBlock.HALF)== BlockHalf.TOP)) {
                    VaseHeight = 1.15F;
                    canRing = true;
                }

            }
            if (state.getBlock() == DeeperDarkBlocks.GAUNT_SCREAMING_VASE && !state.get(AncientVaseBaseBlock.RINGING)) {
                if ((state.get(GauntScreamingVaseBlock.THIRD)==TripleBlockPart.LOWER)) {
                    VaseHeight = 3.25F;
                }
                if ((state.get(GauntScreamingVaseBlock.THIRD)==TripleBlockPart.MIDDLE)) {
                    VaseHeight = 2.25F;
                }
                if ((state.get(GauntScreamingVaseBlock.THIRD)==TripleBlockPart.UPPER)) {
                    VaseHeight = 1.25F;
                    canRing = true;
                }


            }

            serverWorld.spawnParticles(DeeperDarkParticles.VASE_BREAK_PARTICLE, pos.getX() + 0.5, pos.getY() + 0.5 + (VaseHeight-1), pos.getZ() + 0.5, 1, 0.0, 0.0, 0.0, 0.0);

//            if (canRing) {
//                serverWorld.playSound(player, new BlockPos(pos.getX() + 0.5, pos.getY() + 0.5 + (VaseHeight-1), pos.getZ() + 0.5), SoundEvents.BLOCK_SCULK_SHRIEKER_SHRIEK, SoundCategory.BLOCKS, 1, 1);
                serverWorld.playSound(null, new BlockPos(pos.getX() + 0.5, pos.getY() + 0.5 + (VaseHeight-1), pos.getZ() + 0.5), SoundEvents.BLOCK_SCULK_SHRIEKER_SHRIEK, SoundCategory.BLOCKS, 1, 1);
//            }


            serverWorld.setBlockState(pos, (BlockState)state.with(AncientVaseBaseBlock.RINGING, true), 2);
            serverWorld.createAndScheduleBlockTick(pos, state.getBlock(), RING_DELAY);




            HandleLogic(serverWorld,state,pos,player);
        }
    }

    public static boolean isInactive(BlockState state) {
        return state.get(RINGING);
    }


    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(RINGING) != false) {
                world.setBlockState(pos, (BlockState)state.with(RINGING, false), Block.NOTIFY_ALL);
            world.createAndScheduleBlockTick(pos, state.getBlock(), 1);
        }
    }

    public static void setActive(@Nullable Entity entity, World world, BlockPos pos, BlockState state, int power) {
        world.setBlockState(pos, (BlockState)((BlockState)state.with(RINGING, true)), Block.NOTIFY_ALL);
        world.createAndScheduleBlockTick(pos, state.getBlock(), 40);
//        Ring();
        if (world instanceof ServerWorld serverWorld && entity != null) {


            Ring((ServerWorld) world,state,pos, (ServerPlayerEntity) entity);

        }
//        AncientVaseBaseBlock.updateNeighbors(world, pos);
//        world.emitGameEvent(entity, GameEvent.SCULK_SENSOR_TENDRILS_CLICKING, pos);
    }

//    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
//        Entity entity = projectile.getOwner();
//
//
//        if (!world.isClient()) {
//
//
//            if (entity instanceof ServerPlayerEntity player) {
//
//                player.incrementStat(Stats.MINED.getOrCreateStat(this));
//
//                world.playSound(player, hit.getBlockPos(), SoundEvents.BLOCK_SCULK_SHRIEKER_SHRIEK, SoundCategory.BLOCKS, 1, 1);
//                if (world instanceof ServerWorld serverWorld) {
//
//
//                    Ring((ServerWorld) world, state, hit.getBlockPos(), (ServerPlayerEntity) player);
//
//                }
//            }
//            world.breakBlock(hit.getBlockPos(), true);
//        }
//
//    }




//    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
//        if ((Boolean)state.get(RINGING)) {
//            world.setBlockState(pos, (BlockState)state.with(RINGING, false), 3);
//           // world.getBlockEntity(pos, DeeperDarkBlockEntities.SCULK_CAPACITOR).ifPresent((blockEntity) -> blockEntity.warn(world));
//        }
//
//    }





    public AncientVaseBaseBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(GENERATED, false).with(RINGING, false).with(WATERLOGGED, false))));
       // this.listener = new VibrationListener(new BlockPositionSource(this.pos), ((SculkSensorBlock)state.getBlock()).getRange(), this, (VibrationListener.Vibration)null, 0.0F, 0);

    }



    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState base = (BlockState) Objects.requireNonNull(super.getPlacementState(ctx));
        Block vase = base.getBlock();
        BlockPos pos = ctx.getBlockPos();
        PlayerEntity plr = ctx.getPlayer();
        boolean cansucceed = true;

        if (plr != null) {
            Box plrBounds = plr.getBoundingBox();

            if (vase.equals(DeeperDarkBlocks.TURGID_SCREAMING_VASE)) {

                Box bounds = new Box(pos.getX(), pos.getY(), pos.getZ(),
                        pos.getX() + 1, pos.getY() + 1.625, pos.getZ() + 1);
                if (plrBounds.intersects(bounds)) {
                    cansucceed = false;
                }

            } else if (vase.equals(DeeperDarkBlocks.GAUNT_SCREAMING_VASE)) {

                Box bounds = new Box(pos.getX()+0.1875, pos.getY(), pos.getZ()+0.1875,
                        pos.getX() + 0.8125, pos.getY() + 2.6875, pos.getZ() + 0.8125);
                if (plrBounds.intersects(bounds)) {
                    cansucceed = false;
                }

            }
        }

        if (cansucceed == true) {
            return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
        } else {
            return null;
        }
    }


    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED).booleanValue()) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
//        if (state.getBlock().equals(DeeperDarkBlocks.TURGID_SCREAMING_VASE)) {
//            if (state.get(TurgidScreamingVaseBlock.HALF) == BlockHalf.BOTTOM) {
//
//            }
//        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED).booleanValue()) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }


    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(GENERATED);
        builder.add(RINGING);
        builder.add(WATERLOGGED);
    }




    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        // if (!world.isClient && player.isCreative()) {
        // TallPlantBlock.onBreakInCreative(world, pos, state, player);
        //}
        player.incrementStat(Stats.MINED.getOrCreateStat(this));
        player.addExhaustion(0.005F);
        world.playSound(player, pos, DeeperDarkMod.VASE_BREAK_SOUND, SoundCategory.BLOCKS, 1, 1);
        if (world instanceof ServerWorld serverWorld) {


            Ring((ServerWorld) world,state,pos, (ServerPlayerEntity) player);

        }



        super.onBreak(world, pos, state, player);
    }

    public int getRange() {
        return this.range;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AncientVaseBlockEntity(pos,state);
    }
}
