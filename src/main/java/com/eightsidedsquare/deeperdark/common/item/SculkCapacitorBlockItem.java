package com.eightsidedsquare.deeperdark.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class SculkCapacitorBlockItem extends BlockItem {


    public SculkCapacitorBlockItem(Block block, Settings settings) {
        super(block, settings);

    }

    int maxExperience = 512;

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
//        if (stack.getSubNbt("BlockStateTag") != null) {
//            if (stack.getSubNbt("BlockStateTag").contains("experience_charges")) {
                return true;
//            }
//        }
//        return false;
    }

    public float getCharges(ItemStack stack) {
        return (float) (stack.getSubNbt("BlockStateTag").getInt("sculk_charges"));
    }


    @Override
    public int getItemBarStep(ItemStack stack) {
        if (stack.isItemBarVisible()) {
            if (stack.getSubNbt("BlockStateTag") != null) {
                //return Math.round((float) stack.getSubNbt("BlockStateTag").getInt("experience_charges") / (float) this.maxExperience);
//                return 0-(64-(Math.round(13.0F + (float) (64-getCharges(stack)) * 13.0F / (float) this.maxExperience)));
               // return Math.round(getCharges(stack)-(float) this.maxExperience);
                return (int) Math.round(13*((float) stack.getSubNbt("BlockStateTag").getInt("sculk_charges") / (float) this.maxExperience));
                //return 13;
            }
        }
        return 0;

    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        if (stack.isItemBarVisible()) {
            if (stack.getSubNbt("BlockStateTag") != null) {

//                float f = Math.max(0.0F, ((float) 64 + (float) (getCharges(stack)) / (float) 64));
                float f = Math.max(0.0F, ((float) 64 + (float) 0 / (float) 64));
               // float f = Math.max(0.0F, ((float) this.maxExperience + (float) (getCharges(stack)) / (float) this.maxExperience));

                return MathHelper.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
            }
        }
        return MathHelper.hsvToRgb(1.0F, 1.0F, 1.0F);

    }
}
