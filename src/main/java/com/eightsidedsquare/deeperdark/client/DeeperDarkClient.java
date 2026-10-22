package com.eightsidedsquare.deeperdark.client;

import com.eightsidedsquare.deeperdark.client.particle.VaseBreakParticle;
import com.eightsidedsquare.deeperdark.core.DeeperDarkBlocks;
import com.eightsidedsquare.deeperdark.core.DeeperDarkParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.render.RenderLayer;


public class DeeperDarkClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
//        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), DeeperDarkBlocks.SCULK_CAPACITOR, DeeperDarkBlocks.ANCIENT_VASE_3);
        BlockRenderLayerMap.INSTANCE.putBlock(DeeperDarkBlocks.SCULK_CAPACITOR,RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DeeperDarkBlocks.SCULK_ORIFICE,RenderLayer.getCutout());
        ParticleFactoryRegistry.getInstance().register(DeeperDarkParticles.VASE_BREAK_PARTICLE, VaseBreakParticle.Factory::new);
    }


}
