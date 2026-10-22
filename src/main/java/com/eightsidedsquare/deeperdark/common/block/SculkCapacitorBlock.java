package com.eightsidedsquare.deeperdark.common.block;

import com.eightsidedsquare.deeperdark.common.entity.SculkCapacitorBlockEntity;
import com.eightsidedsquare.deeperdark.core.DeeperDarkBlockEntities;
import com.eightsidedsquare.deeperdark.core.DeeperDarkParticles;
import com.eightsidedsquare.deeperdark.core.DeeperDarkProperties;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.client.particle.SculkChargeParticle;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SculkChargeParticleEffect;
import net.minecraft.particle.VibrationParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class SculkCapacitorBlock extends BlockWithEntity {
    public static final BooleanProperty POWERED = Properties.POWERED;

    public static final IntProperty CHARGES = DeeperDarkProperties.SCULK_CHARGES;


    public SculkCapacitorBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(POWERED, false)).with(CHARGES, 0));

    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient) {
            boolean bl = (Boolean)state.get(POWERED);
            if (bl != world.isReceivingRedstonePower(pos)) {
                if (bl) {
                    world.createAndScheduleBlockTick(pos, this, 4);
                } else {
                    world.setBlockState(pos, (BlockState)state.cycle(POWERED), 2);
                }
            }

        }
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if ((Boolean)state.get(POWERED) && !world.isReceivingRedstonePower(pos)) {
            world.setBlockState(pos, (BlockState)state.cycle(POWERED), 2);
        }

    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
        builder.add(CHARGES);
    }


    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SculkCapacitorBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, DeeperDarkBlockEntities.SCULK_CAPACITOR, SculkCapacitorBlockEntity::serverTick);
    }

    public static void SetCharges(BlockState state, BlockPos pos, ServerWorld world, int charges) {
        if (charges >= 0) {
            int finalcharge = charges;
            if (finalcharge > 512) {
                finalcharge = 512;
            }
            if (finalcharge < 0) {
                finalcharge = 0;
            }
            world.setBlockState(pos, (BlockState)state.with(CHARGES, finalcharge), 3);
        }
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int i = (Integer) state.get(SculkCapacitorBlock.CHARGES);
        if (i > 0) {
            int particlestospawn = (int) Math.round((i/50));
            for(int ir = 0; ir < particlestospawn + 1; ++ir) {
                this.spawnSculkParticles(world,pos,state);
            }

        }


    }

    private void spawnSculkParticles(World world, BlockPos pos, BlockState state) {

            world.addParticle(new SculkChargeParticleEffect(0f), MathHelper.lerp(world.random.nextDouble(), pos.getX()+0.5-0.3, pos.getX()+0.5+0.3), pos.getY()+0.6, MathHelper.lerp(world.random.nextDouble(), pos.getZ()+0.5-0.3, pos.getZ()+0.5+0.3), (double)0.0F, (double)0.0F, (double)0.0F);

    }

    @Nullable
    public <T extends BlockEntity> GameEventListener getGameEventListener(ServerWorld world, T blockEntity) {
        if (blockEntity instanceof SculkCapacitorBlockEntity sculkCapacitorBlockEntity) {
            return sculkCapacitorBlockEntity;
        } else {
            return null;
        }
    }

    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SculkCapacitorBlockEntity) {
                SculkCapacitorBlockEntity sculkCapacitorBlockEntity = (SculkCapacitorBlockEntity)blockEntity;
                ItemStack itemStack = new ItemStack(this);
                int i = (Integer)state.get(CHARGES);
                if (i >= 0) {

                    NbtCompound nbtCompound = new NbtCompound();
                    nbtCompound.putInt("sculk_charges", i);
                    itemStack.setSubNbt("BlockStateTag", nbtCompound);
                    ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), itemStack);
                    itemEntity.setToDefaultPickupDelay();
                    world.spawnEntity(itemEntity);
                }
            }
        }

        super.onBreak(world, pos, state, player);
    }

}
