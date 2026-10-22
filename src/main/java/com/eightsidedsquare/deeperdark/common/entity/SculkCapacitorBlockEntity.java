package com.eightsidedsquare.deeperdark.common.entity;

import com.eightsidedsquare.deeperdark.common.block.SculkCapacitorBlock;
import com.eightsidedsquare.deeperdark.core.DeeperDarkBlockEntities;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.annotation.Debug;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SculkCapacitorBlockEntity extends BlockEntity implements GameEventListener {
    private final SculkSpreadManager spreadManager;
    private final BlockPositionSource positionSource;



    public SculkCapacitorBlockEntity(BlockPos pos, BlockState state) {
        super(DeeperDarkBlockEntities.SCULK_CAPACITOR, pos, state);
        this.spreadManager = SculkSpreadManager.create();
        this.positionSource = new BlockPositionSource(this.pos);
    }

    public void markDirty() {

        super.markDirty();
    }



    public static int getExperienceLevel(BlockState state) {
        return (Integer)state.get(SculkCapacitorBlock.CHARGES);
    }


    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);


    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

    }


    public static int getChargeLevel(BlockState state) {
        return (Integer)state.get(SculkCapacitorBlock.CHARGES);
    }


    public static void serverTick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (!world.isClient) {
            ((SculkCapacitorBlockEntity) blockEntity).getSpreadManager().tick(world, pos, world.getRandom(), true);
            if (state.get(SculkCapacitorBlock.POWERED) == true) {
                if (world.getBlockState(pos.down()).getBlock() instanceof SculkCatalystBlock) {
                    int i = (Integer) state.get(SculkCapacitorBlock.CHARGES);
                    if (i > 0) {

                        if (world.random.nextFloat() < 0.7F) {
                        SculkCapacitorBlock.SetCharges(state, pos, (ServerWorld) world, i - 1);


                            int x = world.random.nextBoolean() ? 1 : -1;
                            int z = world.random.nextBoolean() ? 1 : -1;
//                            ((SculkCapacitorBlockEntity) blockEntity).getSpreadManager().spread(pos.down(1).add(x,0,z), 10);

                            ((SculkCapacitorBlockEntity) blockEntity).getSpreadManager().spread(pos.down(1).add(x,0,z), 10);


                        }

                    }

                }

            }
        }

    }


    @VisibleForTesting
    public SculkSpreadManager getSpreadManager() {
        return this.spreadManager;
    }

    public boolean shouldListenImmediately() {
        return true;
    }

    public PositionSource getPositionSource() {
        return this.positionSource;
    }

   public int getRange() {
       return 8;
   }

    public boolean listen(ServerWorld world, GameEvent.Message event) {
        if (this.isRemoved()) {
            return false;
        } else {
            GameEvent.Emitter emitter = event.getEmitter();
            if (event.getEvent() == GameEvent.ENTITY_DIE) {
                Entity var5 = emitter.sourceEntity();
                if (var5 instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity)var5;
                    if (!livingEntity.isExperienceDroppingDisabled()) {
                        int i = livingEntity.getXpToDrop();
                        if (livingEntity.shouldDropXp() && i > 0) {

                            LivingEntity livingEntity2 = livingEntity.getAttacker();
                            if (livingEntity2 instanceof ServerPlayerEntity) {
                                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)livingEntity2;

                            }
                        }

                        livingEntity.disableExperienceDropping();
                        SculkCapacitorBlock.SetCharges(this.getCachedState(),pos,world,this.getChargeLevel(this.getCachedState())+i);
                        world.playSound((PlayerEntity)null, this.pos, SoundEvents.BLOCK_SCULK_SPREAD, SoundCategory.BLOCKS, 1.0F, 1.0F);

                    }

                    return true;
                }
            }

            return false;
        }
    }
}
