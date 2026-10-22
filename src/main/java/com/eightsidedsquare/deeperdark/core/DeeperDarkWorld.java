package com.eightsidedsquare.deeperdark.core;

import com.eightsidedsquare.deeperdark.common.worldgen.DeeperDarkFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class DeeperDarkWorld {

    //VASES
    public static final List<OreFeatureConfig.Target> ROTUND_FEATURECONFIG = List.of(
            OreFeatureConfig.createTarget(new BlockMatchRuleTest(Blocks.SCULK), DeeperDarkBlocks.ROTUND_SCREAMING_VASE.getDefaultState()));

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ROTUND_CONFIGUED =
            ConfiguredFeatures.register("rotund_screaming_vase", DeeperDarkFeatures.ROTUND_VASE_SCATTERED_ORE, new OreFeatureConfig(ROTUND_FEATURECONFIG, 1));

//    public static final RegistryEntry<PlacedFeature> ROTUND_PLACED = PlacedFeatures.register("rotund_screaming_vase",
//            ROTUND_CONFIGUED, modifiersWithCount(20,
//                    HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));

    public static final RegistryEntry<PlacedFeature> ROTUND_PLACED = PlacedFeatures.register("rotund_screaming_vase",
            ROTUND_CONFIGUED, modifiersWithCount(160,
                    HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));


    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ROTUND_CONFIGUED_2 =
            ConfiguredFeatures.register("rotund_screaming_vase_2", DeeperDarkFeatures.ROTUND_VASE_SCATTERED_ORE, new OreFeatureConfig(ROTUND_FEATURECONFIG, 1));

    public static final RegistryEntry<PlacedFeature> ROTUND_PLACED_2 = PlacedFeatures.register("rotund_screaming_vase_2",
            ROTUND_CONFIGUED, modifiersWithCount(160,
                    HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ROTUND_CONFIGUED_3 =
            ConfiguredFeatures.register("rotund_screaming_vase_3", DeeperDarkFeatures.ROTUND_VASE_SCATTERED_ORE, new OreFeatureConfig(ROTUND_FEATURECONFIG, 1));

    public static final RegistryEntry<PlacedFeature> ROTUND_PLACED_3 = PlacedFeatures.register("rotund_screaming_vase_3",
            ROTUND_CONFIGUED, modifiersWithCount(160,
                    HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));

//    public static final RegistryEntry<PlacedFeature> ROTUND_PLACED_3 = PlacedFeatures.register("rotund_screaming_vase_3",
//            ROTUND_CONFIGUED, modifiersWithCount(80,
//                    HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));

    //ORIFICE
    public static final List<OreFeatureConfig.Target> SCULK_ORIFICE_FEATURECONFIG = List.of(
            OreFeatureConfig.createTarget(new BlockMatchRuleTest(Blocks.SCULK), DeeperDarkBlocks.SCULK_ORIFICE.getDefaultState()));

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> SCULK_ORIFICE_CONFIGUED =
            ConfiguredFeatures.register("sculk_orifice", DeeperDarkFeatures.SCULK_ORIFICE_GEN, new OreFeatureConfig(SCULK_ORIFICE_FEATURECONFIG, 15));

    public static final RegistryEntry<PlacedFeature> SCULK_ORIFICE_PLACED = PlacedFeatures.register("sculk_orifice",
            SCULK_ORIFICE_CONFIGUED, modifiersWithCount(20,
                    HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));









    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }
    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacementModifier.of(count), heightModifier);
    }
    private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
        return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
    }


    public static void init() {
//        BiomeModifications.addFeature(
//                BiomeSelectors.includeByKey(BiomeKeys.DEEP_DARK),
//                GenerationStep.Feature.UNDERGROUND_DECORATION,
//                RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier("deeper_dark", "sculk_orifice"))
//        );

//        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
//                GenerationStep.Feature.VEGETAL_DECORATION, SCULK_ORIFICE_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_DECORATION, SCULK_ORIFICE_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.VEGETAL_DECORATION, ROTUND_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.VEGETAL_DECORATION, ROTUND_PLACED_2.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.VEGETAL_DECORATION, ROTUND_PLACED_3.getKey().get());
    }
}
