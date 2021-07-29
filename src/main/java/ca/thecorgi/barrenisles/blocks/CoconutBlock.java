package ca.thecorgi.barrenisles.blocks;

import ca.thecorgi.barrenisles.utils.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.CocoaBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;

public class CoconutBlock extends CocoaBlock {
    public CoconutBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.offset((Direction)state.get(FACING)));
        return blockState.isOf(BlockRegistry.PALM_LOG);
    }
}
