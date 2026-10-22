package com.eightsidedsquare.deeperdark.core;

import com.eightsidedsquare.deeperdark.common.properties.TripleBlockPart;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;

public class DeeperDarkProperties extends Properties {
    public static final EnumProperty<TripleBlockPart> TRIPLE_BLOCK_HALF;
    public static final IntProperty SCULK_CHARGES;
    public static final BooleanProperty RINGING;

    public DeeperDarkProperties() {
    }

    static {
        TRIPLE_BLOCK_HALF = EnumProperty.of("third", TripleBlockPart.class);
        SCULK_CHARGES = IntProperty.of("sculk_charges", 0, 512);
        RINGING = BooleanProperty.of("ringing");
    }
}
