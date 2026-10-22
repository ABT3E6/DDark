package com.eightsidedsquare.deeperdark.common.worldgen.feature;

import com.eightsidedsquare.deeperdark.common.block.GauntScreamingVaseBlock;
import com.eightsidedsquare.deeperdark.common.block.RotundScreamingVaseBlock;
import com.eightsidedsquare.deeperdark.common.block.TurgidScreamingVaseBlock;
import com.eightsidedsquare.deeperdark.common.properties.TripleBlockPart;
import com.eightsidedsquare.deeperdark.core.DeeperDarkBlocks;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AncientVaseFeature extends Feature<OreFeatureConfig> {
    private static final int field_31515 = 7;

    public AncientVaseFeature(Codec<OreFeatureConfig> codec) {
        super(codec);
    }

    protected static boolean shouldNotDiscard(Random random, float chance) {
        if (chance <= 0.0f) {
            return true;
        }
        if (chance >= 1.0f) {
            return false;
        }
        return random.nextFloat() >= chance;
    }

    public static boolean shouldPlace(BlockState state, Function<BlockPos, BlockState> posToState, Random random, OreFeatureConfig config, OreFeatureConfig.Target target, BlockPos.Mutable pos) {
//        if (!target.target.test(state, random)) {
        boolean bl = true;
        if (!target.target.test(Blocks.DEEPSLATE_BRICKS.getDefaultState(),random)) {
            bl = false;
        }

        if (!target.target.test(Blocks.POLISHED_DEEPSLATE.getDefaultState(),random)) {
            bl = false;
        }

        if (!target.target.test(Blocks.CRACKED_DEEPSLATE_BRICKS.getDefaultState(),random)) {
            bl = false;
        }

        if (!target.target.test(Blocks.COBBLED_DEEPSLATE.getDefaultState(),random)) {

            bl = false;
        }



//        if (!target.target.test(Blocks.DEEPSLATE_BRICKS.getDefaultState(),random) && !target.target.test(Blocks.POLISHED_DEEPSLATE.getDefaultState(),random) && !target.target.test(Blocks.CRACKED_DEEPSLATE_BRICKS.getDefaultState(),random)  && !target.target.test(Blocks.COBBLED_DEEPSLATE.getDefaultState(),random)) {
//        if (!target.target.test(Blocks.DEEPSLATE_BRICKS.getDefaultState(),random) && !target.target.test(Blocks.POLISHED_DEEPSLATE.getDefaultState(),random) && !target.target.test(Blocks.CRACKED_DEEPSLATE_BRICKS.getDefaultState(),random)  && !target.target.test(Blocks.COBBLED_DEEPSLATE.getDefaultState(),random)) {

        if (!bl) {
            return false;
        }
        if (shouldNotDiscard(random, config.discardOnAirChance)) {
            return true;
        }
        return !OreFeature.isExposedToAir(posToState, pos);
    }



    private static final Direction[] DIRECTIONS = {
           Direction.NORTH,
            Direction.EAST,
            Direction.WEST,
            Direction.SOUTH
    };

    private static final Block[] BLOCKVARS = {
            DeeperDarkBlocks.ROTUND_SCREAMING_VASE,
            DeeperDarkBlocks.ROTUND_SCREAMING_VASE,
            DeeperDarkBlocks.TURGID_SCREAMING_VASE,
            DeeperDarkBlocks.GAUNT_SCREAMING_VASE
    };



    private static final OreFeatureConfig.Target[] TARGETS = {
            OreFeatureConfig.createTarget(new BlockMatchRuleTest(Blocks.DEEPSLATE_BRICKS),DeeperDarkBlocks.ROTUND_SCREAMING_VASE.getDefaultState()),
            OreFeatureConfig.createTarget(new BlockMatchRuleTest(Blocks.CRACKED_DEEPSLATE_BRICKS),DeeperDarkBlocks.ROTUND_SCREAMING_VASE.getDefaultState()),
            OreFeatureConfig.createTarget(new BlockMatchRuleTest(Blocks.POLISHED_DEEPSLATE),DeeperDarkBlocks.ROTUND_SCREAMING_VASE.getDefaultState())
    };

//    public static Direction randomHorizontal(Random random) {
//        return Util.getRandom(HORIZONTAL, random);
//    }

    public static <T> T getRandom(T[] array, Random random) {
        return array[random.nextInt(array.length)];
    }

    @Override
    public boolean generate(FeatureContext<OreFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();
        OreFeatureConfig oreFeatureConfig = context.getConfig();
        BlockPos blockPos = context.getOrigin();
        int i = random.nextInt(oreFeatureConfig.size + 1);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        block0: for (int j = 0; j < i; ++j) {
            this.setPos(mutable, random, blockPos, Math.min(j, 7));
            BlockState blockState = structureWorldAccess.getBlockState(mutable);
//            for (OreFeatureConfig.Target target : oreFeatureConfig.targets) {
            for (OreFeatureConfig.Target target : TARGETS) {
                if (!OreFeature.shouldPlace(blockState, structureWorldAccess::getBlockState, random, oreFeatureConfig, target, mutable)) continue;
                if (!structureWorldAccess.getBlockState(mutable.up()).isOf(Blocks.AIR)) continue;

                Block type = Util.getRandom(BLOCKVARS, random);
                Direction facing = Util.getRandom(DIRECTIONS,random);

                if (type == DeeperDarkBlocks.ROTUND_SCREAMING_VASE) {
                    if (!structureWorldAccess.getBlockState(mutable.up()).isOf(Blocks.AIR)) continue;
                    BlockState var = DeeperDarkBlocks.ROTUND_SCREAMING_VASE.getDefaultState().with(RotundScreamingVaseBlock.GENERATED, true).with(RotundScreamingVaseBlock.FACING, facing);
                    structureWorldAccess.setBlockState(mutable.up(), var, Block.NOTIFY_LISTENERS);
                }

                if (type == DeeperDarkBlocks.TURGID_SCREAMING_VASE) {
                    if (!structureWorldAccess.getBlockState(mutable.up()).isOf(Blocks.AIR)) continue;
                    if (!structureWorldAccess.getBlockState(mutable.up().up()).isOf(Blocks.AIR)) continue;
                    BlockState var = DeeperDarkBlocks.TURGID_SCREAMING_VASE.getDefaultState().with(TurgidScreamingVaseBlock.GENERATED, true).with(TurgidScreamingVaseBlock.FACING, facing).with(TurgidScreamingVaseBlock.HALF, BlockHalf.BOTTOM);
                    BlockState var2 = DeeperDarkBlocks.TURGID_SCREAMING_VASE.getDefaultState().with(TurgidScreamingVaseBlock.GENERATED, true).with(TurgidScreamingVaseBlock.FACING, facing).with(TurgidScreamingVaseBlock.HALF, BlockHalf.TOP);
                    structureWorldAccess.setBlockState(mutable.up(), var, Block.NOTIFY_LISTENERS);
                    structureWorldAccess.setBlockState(mutable.up().up(), var2, Block.NOTIFY_LISTENERS);
                }

                if (type == DeeperDarkBlocks.GAUNT_SCREAMING_VASE) {
                    if (!structureWorldAccess.getBlockState(mutable.up()).isOf(Blocks.AIR)) continue;
                    if (!structureWorldAccess.getBlockState(mutable.up().up()).isOf(Blocks.AIR)) continue;
                    if (!structureWorldAccess.getBlockState(mutable.up().up().up()).isOf(Blocks.AIR)) continue;
                    BlockState var = DeeperDarkBlocks.GAUNT_SCREAMING_VASE.getDefaultState().with(GauntScreamingVaseBlock.GENERATED, true).with(GauntScreamingVaseBlock.FACING, facing).with(GauntScreamingVaseBlock.THIRD, TripleBlockPart.LOWER);
                    BlockState var2 = DeeperDarkBlocks.GAUNT_SCREAMING_VASE.getDefaultState().with(GauntScreamingVaseBlock.GENERATED, true).with(GauntScreamingVaseBlock.FACING, facing).with(GauntScreamingVaseBlock.THIRD, TripleBlockPart.MIDDLE);
                    BlockState var3 = DeeperDarkBlocks.GAUNT_SCREAMING_VASE.getDefaultState().with(GauntScreamingVaseBlock.GENERATED, true).with(GauntScreamingVaseBlock.FACING, facing).with(GauntScreamingVaseBlock.THIRD, TripleBlockPart.UPPER);
                    structureWorldAccess.setBlockState(mutable.up(), var, Block.NOTIFY_LISTENERS);
                    structureWorldAccess.setBlockState(mutable.up().up(), var2, Block.NOTIFY_LISTENERS);
                    structureWorldAccess.setBlockState(mutable.up().up().up(), var3, Block.NOTIFY_LISTENERS);
                }





                continue block0;
            }
        }
        return true;
    }

    private void setPos(BlockPos.Mutable mutable, Random random, BlockPos origin, int spread) {
        int i = this.getSpread(random, spread);
        int j = this.getSpread(random, spread);
        int k = this.getSpread(random, spread);
        mutable.set(origin, i, j, k);
    }

    private int getSpread(Random random, int spread) {
        return Math.round((random.nextFloat() - random.nextFloat()) * (float)spread);
    }


}

