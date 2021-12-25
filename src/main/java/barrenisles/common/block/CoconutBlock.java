package barrenisles.common.block;

import barrenisles.api.BarrenIslesBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CocoaBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class CoconutBlock extends CocoaBlock {
    public CoconutBlock(AbstractBlock.Properties settings) {
        super(settings);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
    	Block block = world.getBlockState(pos.relative(state.getValue(FACING))).getBlock();
    	return block.is(BarrenIslesBlocks.palm_log.get())|| block.is(BarrenIslesBlocks.stripped_palm_log.get()) || block.is(BarrenIslesBlocks.palm_wood.get()) || block.is(BarrenIslesBlocks.stripped_palm_wood.get());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        int i = state.getValue(AGE);
        switch(state.getValue(FACING)) {
            case SOUTH:
                return SOUTH_AABB[i];
            case NORTH:
            default:
                return NORTH_AABB[i];
            case WEST:
                return WEST_AABB[i];
            case EAST:
                return EAST_AABB[i];
        }
    }

    public VoxelShape[] EAST_AABB = new VoxelShape[]{Block.box(10.0D, 7.0D, 6.0D, 14.0D, 11.0D, 10.0D), Block.box(8.0D, 5.0D, 5.0D, 14.0D, 11.0D, 11.0D), Block.box(6.0D, 4.0D, 4.0D, 14.0D, 12.0D, 12.0D)};
    public VoxelShape[] WEST_AABB = new VoxelShape[]{Block.box(2.0D, 7.0D, 6.0D, 6.0D, 11.0D, 10.0D), Block.box(2.0D, 5.0D, 5.0D, 8.0D, 11.0D, 11.0D), Block.box(2.0D, 4.0D, 4.0D, 10.0D, 12.0D, 12.0D)};
    public VoxelShape[] NORTH_AABB = new VoxelShape[]{Block.box(6.0D, 7.0D, 2.0D, 10.0D, 11.0D, 6.0D), Block.box(5.0D, 5.0D, 2.0D, 11.0D, 11.0D, 8.0D), Block.box(4.0D, 4.0D, 2.0D, 12.0D, 12.0D, 10.0D)};
    public VoxelShape[] SOUTH_AABB = new VoxelShape[]{Block.box(6.0D, 7.0D, 10.0D, 10.0D, 11.0D, 14.0D), Block.box(5.0D, 5.0D, 8.0D, 11.0D, 11.0D, 14.0D), Block.box(4.0D, 4.0D, 6.0D, 12.0D, 12.0D, 14.0D)};

}