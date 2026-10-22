package com.eightsidedsquare.deeperdark.core;

import com.eightsidedsquare.deeperdark.common.worldgen.DeeperDarkFeatures;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DeeperDarkMod implements ModInitializer {
	public static final String MOD_ID = "deeper_dark";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final DefaultParticleType VASE_BREAK = FabricParticleTypes.simple();


    public static final SoundEvent VASE_BREAK_SOUND = new SoundEvent(new Identifier("deeper_dark", "block.vase.break"));


	@Override
	public void onInitialize() {
        DeeperDarkBlocks.init();
        DeeperDarkParticles.init();
        DeeperDarkBlockEntities.init();
        DeeperDarkFeatures.init();
        DeeperDarkWorld.init();
        Registry.register(Registry.SOUND_EVENT, VASE_BREAK_SOUND.getId(), VASE_BREAK_SOUND);
	}


}
