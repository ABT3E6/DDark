package com.eightsidedsquare.deeperdark.common.entity;

import com.eightsidedsquare.deeperdark.common.block.AncientVaseBaseBlock;
import com.eightsidedsquare.deeperdark.core.DeeperDarkBlockEntities;
import com.mojang.datafixers.types.templates.Tag;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SculkSensorBlockEntity;
import net.minecraft.block.enums.SculkSensorPhase;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;
import net.minecraft.world.event.listener.VibrationListener;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class AncientVaseBlockEntity extends BlockEntity
        implements VibrationListener.Callback {
    private static final Logger field_38236 = LogUtils.getLogger();
    private VibrationListener listener;
    private int lastVibrationFrequency;

    public static final Object2IntMap<GameEvent> FREQUENCIES = Object2IntMaps.unmodifiable(Util.make(new Object2IntOpenHashMap(), map -> {
        map.put(GameEvent.STEP, 1);
    }));

    public AncientVaseBlockEntity(BlockPos pos, BlockState state) {
        super(DeeperDarkBlockEntities.ROTUND_SCREAMING_VASE, pos, state);
        this.listener = new VibrationListener(new BlockPositionSource(this.pos), ((AncientVaseBaseBlock)state.getBlock()).getRange(), this, null, 0.0f, 0);
    }

    public VibrationListener getEventListener() {
        return this.listener;
    }

    public int getLastVibrationFrequency() {
        return this.lastVibrationFrequency;
    }


    public void setLastVibrationFrequency(int lastVibrationFrequency) {
        this.lastVibrationFrequency = lastVibrationFrequency;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.lastVibrationFrequency = nbt.getInt("last_vibration_frequency");
        if (nbt.contains("listener", NbtElement.COMPOUND_TYPE)) {
            VibrationListener.createCodec(this).parse(new Dynamic<NbtElement>(NbtOps.INSTANCE, nbt.getCompound("listener"))).resultOrPartial(field_38236::error).ifPresent(listener -> {
                this.listener = listener;
            });
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("last_vibration_frequency", this.lastVibrationFrequency);
        VibrationListener.createCodec(this).encodeStart(NbtOps.INSTANCE, this.listener).resultOrPartial(field_38236::error).ifPresent(listenerNbt -> nbt.put("listener", (NbtElement)listenerNbt));
    }


    @Override
    public boolean triggersAvoidCriterion() {
        return true;
    }

    @Override
    public boolean accepts(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable GameEvent.Emitter emitter) {
        if (this.isRemoved() || pos.equals(this.getPos()) && (event == GameEvent.BLOCK_DESTROY || event == GameEvent.BLOCK_PLACE)) {
            return false;
        }
        return true;
    }


    public static int getPower(float distance, int range) {
        double d = (double)distance / (double)range;
        return Math.max(1, 15 - MathHelper.floor(d * 15.0));
    }

    @Override
    public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, float distance) {
        BlockState blockState = this.getCachedState();
        if (AncientVaseBaseBlock.isInactive(blockState)) {
            this.lastVibrationFrequency = SculkSensorBlock.FREQUENCIES.getInt(event);
            AncientVaseBaseBlock.setActive(entity, world, this.pos, blockState, AncientVaseBlockEntity.getPower(distance, listener.getRange()));
        }
    }

    @Override
    public void onListen() {
        this.markDirty();
    }
}
