package barrenisles.common.util.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class BlockUtil {
	public static BlockState getBlockStateOn(Entity entityIn) {
		return entityIn.level.getBlockState(BlockUtil.getOnPos(entityIn));
	}
	
	public static BlockPos getOnPos(Entity entityIn) {
		int i = MathHelper.floor(entityIn.getX());
	      int j = MathHelper.floor(entityIn.getY() - (double)0.2F);
	      int k = MathHelper.floor(entityIn.getZ());
	      BlockPos blockpos = new BlockPos(i, j, k);
	      if (entityIn.level.isEmptyBlock(blockpos)) {
	         BlockPos blockpos1 = blockpos.below();
	         BlockState blockstate = entityIn.level.getBlockState(blockpos1);
	         if (blockstate.collisionExtendsVertically(entityIn.level, blockpos1, entityIn)) {
	            return blockpos1;
	         }
	      }

	      return blockpos;
	}
}
