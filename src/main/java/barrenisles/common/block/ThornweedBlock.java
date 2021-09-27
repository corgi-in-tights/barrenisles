package barrenisles.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ThornweedBlock extends BushBlock {
    public ThornweedBlock(AbstractBlock.Properties settings) {
        super(settings);
    }

    @Override
    public boolean mayPlaceOn(BlockState floor, IBlockReader world, BlockPos pos) {
    	return floor.is(BlockTags.SAND);
    }
    
    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.FOX && entityIn.getType() != EntityType.BEE) {
           entityIn.makeStuckInBlock(state, new Vector3d((double)0.800000011920929D, 0.75D, (double)0.800000011920929D));
           if (!world.isClientSide && (entityIn.xOld != entityIn.getX() || entityIn.zOld != entityIn.getZ())) {
              double d0 = Math.abs(entityIn.getX() - entityIn.xOld);
              double d1 = Math.abs(entityIn.getZ() - entityIn.zOld);
              if (d0 >= (double)0.003F || d1 >= (double)0.003F) {
                 entityIn.hurt(DamageSource.SWEET_BERRY_BUSH, 1.0F);
              }
           }

        }
     }
}