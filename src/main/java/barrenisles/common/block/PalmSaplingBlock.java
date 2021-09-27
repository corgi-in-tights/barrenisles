package barrenisles.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class PalmSaplingBlock extends SaplingBlock {
    public PalmSaplingBlock(Tree tree, AbstractBlock.Properties settings) {
        super(tree, settings);
    }
    
    @Override
    public boolean mayPlaceOn(BlockState floor, IBlockReader world, BlockPos pos) {
    	return floor.is(Blocks.DIRT) || floor.is(Blocks.SAND);
    }
}