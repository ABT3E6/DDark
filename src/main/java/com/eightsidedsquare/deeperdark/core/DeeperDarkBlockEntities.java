package com.eightsidedsquare.deeperdark.core;

import com.eightsidedsquare.deeperdark.common.entity.AncientVaseBlockEntity;
import com.eightsidedsquare.deeperdark.common.entity.SculkCapacitorBlockEntity;
import com.mojang.datafixers.types.Type;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class DeeperDarkBlockEntities {
    public static final BlockEntityType<SculkCapacitorBlockEntity> SCULK_CAPACITOR;

    public static final BlockEntityType<AncientVaseBlockEntity> ROTUND_SCREAMING_VASE;



    public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Identifier.of("tutorial", path), blockEntityType);
    }


    public static void init() {
    }

    static {
        SCULK_CAPACITOR = register(
                "sculk_capacitor",
                FabricBlockEntityTypeBuilder.create(SculkCapacitorBlockEntity::new, DeeperDarkBlocks.SCULK_CAPACITOR).build());

        ROTUND_SCREAMING_VASE = register(
                "rotund_screaming_vase",
                FabricBlockEntityTypeBuilder.create(AncientVaseBlockEntity::new, DeeperDarkBlocks.ROTUND_SCREAMING_VASE).build());
    }


}
