package com.eightsidedsquare.deeperdark.common.worldgen;

import com.eightsidedsquare.deeperdark.common.worldgen.feature.AncientVaseFeature;
import com.eightsidedsquare.deeperdark.common.worldgen.feature.SculkOrificeFeature;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ScatteredOreFeature;

public class DeeperDarkFeatures {
    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return (F) Registry.register(Registry.FEATURE, name, feature);
    }

    public static final Feature<OreFeatureConfig> SCULK_ORIFICE_GEN = register("sculk_orifice_gen", new SculkOrificeFeature(OreFeatureConfig.CODEC));

    public static final Feature<OreFeatureConfig> ROTUND_VASE_SCATTERED_ORE = register("rotund_vase_scattered_ore", new AncientVaseFeature(OreFeatureConfig.CODEC));



    public static void init() {

    }
}
