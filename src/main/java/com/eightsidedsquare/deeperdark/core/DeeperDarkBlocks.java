package com.eightsidedsquare.deeperdark.core;

import com.eightsidedsquare.deeperdark.common.block.*;
import com.eightsidedsquare.deeperdark.common.entity.SculkCapacitorBlockEntity;
import com.eightsidedsquare.deeperdark.common.item.SculkCapacitorBlockItem;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.loot.LootTable;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.eightsidedsquare.deeperdark.core.DeeperDarkMod.MOD_ID;

public class DeeperDarkBlocks {
    private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
    private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    public static final BlockSoundGroup VASE = new BlockSoundGroup(1.0f, 1.0f, DeeperDarkMod.VASE_BREAK_SOUND, SoundEvents.BLOCK_POLISHED_DEEPSLATE_STEP, SoundEvents.BLOCK_POLISHED_DEEPSLATE_PLACE, SoundEvents.BLOCK_POLISHED_DEEPSLATE_HIT, SoundEvents.BLOCK_POLISHED_DEEPSLATE_FALL);

    public static final Block ROTUND_SCREAMING_VASE = create("rotund_screaming_vase", new RotundScreamingVaseBlock(AbstractBlock.Settings.of(Material.STONE).strength(0.5f).nonOpaque().dynamicBounds().sounds(BlockSoundGroup.POLISHED_DEEPSLATE)), DeeperDarkItemGroup.DEEPER_DARK);

    public static final Block TURGID_SCREAMING_VASE = create("turgid_screaming_vase", new TurgidScreamingVaseBlock(AbstractBlock.Settings.of(Material.STONE).strength(0.3f).nonOpaque().dynamicBounds().sounds(BlockSoundGroup.POLISHED_DEEPSLATE)), DeeperDarkItemGroup.DEEPER_DARK);

    public static final Block GAUNT_SCREAMING_VASE = create("gaunt_screaming_vase", new GauntScreamingVaseBlock(AbstractBlock.Settings.of(Material.STONE).strength(0.3f).nonOpaque().dynamicBounds().sounds(BlockSoundGroup.POLISHED_DEEPSLATE)), DeeperDarkItemGroup.DEEPER_DARK);


    public static final Block ECHO_BONE = create("echo_bone", new PillarBlock(FabricBlockSettings.copy(Blocks.BONE_BLOCK).dynamicBounds()), DeeperDarkItemGroup.DEEPER_DARK);

    public static final Block SCULK_ORIFICE = create("sculk_orifice", new SculkOrificeBlock(FabricBlockSettings.copy(Blocks.SCULK).strength(0.6f).nonOpaque().dynamicBounds()), DeeperDarkItemGroup.DEEPER_DARK);

    public static final Block SCULK_CAPACITOR = createCapacitor("sculk_capacitor", new SculkCapacitorBlock(AbstractBlock.Settings.of(Material.SCULK).strength(3.0F, 3.0F).sounds(BlockSoundGroup.SCULK_CATALYST).ticksRandomly().nonOpaque().dynamicBounds()), DeeperDarkItemGroup.DEEPER_DARK);






    private static <T extends Block> T createCapacitor(String name, T block, ItemGroup itemGroup) {

        BLOCKS.put(block, new Identifier(MOD_ID, name));
        if (itemGroup != null) {
            ITEMS.put(new SculkCapacitorBlockItem(block, new Item.Settings().group(itemGroup)), BLOCKS.get(block));
        }
        return block;
    }

    private static <T extends Block> T create(String name, T block, ItemGroup itemGroup) {

        BLOCKS.put(block, new Identifier(MOD_ID, name));
        if (itemGroup != null) {
            ITEMS.put(new BlockItem(block, new Item.Settings().group(itemGroup)), BLOCKS.get(block));
        }
        return block;
    }


    public static void init() {
        BLOCKS.keySet().forEach(block -> Registry.register(Registry.BLOCK, BLOCKS.get(block), block));
        ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
    }
}
