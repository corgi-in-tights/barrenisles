package barrenisles.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.potion.Effect;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class DesertFlowerBlock extends FlowerBlock {

    public DesertFlowerBlock(Effect suspiciousStewEffect, int effectDuration, AbstractBlock.Properties settings) {
        super(suspiciousStewEffect, effectDuration, settings);
    }
    
    @Override
    public boolean mayPlaceOn(BlockState floor, IBlockReader world, BlockPos pos) {
    	return floor.is(BlockTags.SAND);
    }
}