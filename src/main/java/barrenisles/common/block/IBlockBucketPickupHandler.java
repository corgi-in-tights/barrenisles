package barrenisles.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public interface IBlockBucketPickupHandler {
	Block takeBlock(IWorld p_204508_1_, BlockPos p_204508_2_, BlockState p_204508_3_);
}