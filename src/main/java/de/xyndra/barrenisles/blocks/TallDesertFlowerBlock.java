package de.xyndra.barrenisles.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.PlantType;

public class TallDesertFlowerBlock extends TallFlowerBlock {
    public TallDesertFlowerBlock(Properties settings) {
        super(settings);
    }

    @Override
    public boolean mayPlaceOn(BlockState p_200014_1_, IBlockReader p_200014_2_, BlockPos p_200014_3_) {
        return p_200014_1_.is(BlockTags.SAND);
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.DESERT;
    }

}
