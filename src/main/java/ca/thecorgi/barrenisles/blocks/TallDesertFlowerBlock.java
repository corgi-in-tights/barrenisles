package ca.thecorgi.barrenisles.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class TallDesertFlowerBlock extends TallFlowerBlock {
    public TallDesertFlowerBlock(Settings settings) {
        super(settings);
    }
    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.SAND);
    }

}
