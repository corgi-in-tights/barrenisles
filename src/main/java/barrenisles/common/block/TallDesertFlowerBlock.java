package barrenisles.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class TallDesertFlowerBlock extends TallFlowerBlock {
    public TallDesertFlowerBlock(AbstractBlock.Properties settings) {
        super(settings);
    }
    
    @Override
    public boolean mayPlaceOn(BlockState floor, IBlockReader world, BlockPos pos) {
    	return floor.is(BlockTags.SAND);
    }

}