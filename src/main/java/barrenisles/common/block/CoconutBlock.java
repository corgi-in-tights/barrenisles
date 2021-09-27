package barrenisles.common.block;

import barrenisles.api.blocks.BarrenIslesBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CocoaBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class CoconutBlock extends CocoaBlock {
    public CoconutBlock(AbstractBlock.Properties settings) {
        super(settings);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
    	Block block = world.getBlockState(pos.relative(state.getValue(FACING))).getBlock();
    	return block.is(BarrenIslesBlocks.palm_log.get())|| block.is(BarrenIslesBlocks.palm_log.get()) || block.is(BarrenIslesBlocks.stripped_palm_log.get());
    }
}