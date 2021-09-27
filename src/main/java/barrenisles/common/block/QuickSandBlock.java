package barrenisles.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import barrenisles.api.items.BarrenIslesItems;
import barrenisles.common.util.block.BlockUtil;
import java.util.Random;

public class QuickSandBlock extends Block{
	
    public QuickSandBlock(AbstractBlock.Properties settings) {
        super(settings);
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entityIn) {
        if (!(entityIn instanceof LivingEntity) || BlockUtil.getBlockStateOn(entityIn).is(this) /*&& entityIn.getType() != EntityRegistry.DUNERAPTOR && entity.getType() != EntityRegistry.TUMBLEWEED*/) {
            entityIn.setDeltaMovement(new Vector3d(0.24, 0.105D, 0.24));
        }

        if (world.isClientSide()) {
            Random random = world.getRandom();
            if ( random.nextBoolean() ) {
                world.addParticle(ParticleTypes.WHITE_ASH, entityIn.getX(), entityIn.getY(), entityIn.getZ(),0.1,0.1,0.1);
            }

            if ( !world.isClientSide() ) {
                entityIn.setSecondsOnFire(10);
            }
        }
    }
    
    public ItemStack tryPutBlock(World world, BlockPos pos, BlockState state){
    	world.markAndNotifyBlock(pos, (Chunk) world.getChunk(pos), state, state, 0, 0);
        return new ItemStack(BarrenIslesItems.quicksand_bucket.get());
    }

     @SuppressWarnings("deprecation")
	public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
        return super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
     }

     public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return p_200123_1_.getFluidState().isEmpty();
     }

     @SuppressWarnings("deprecation")
	public boolean isPathfindable(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
        return p_196266_4_ == PathType.AIR && !this.hasCollision ? true : super.isPathfindable(p_196266_1_, p_196266_2_, p_196266_3_, p_196266_4_);
     }
    /* Coming soon ...
    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(SoundEvents.COMPOSTER_FILL);
    }*/
}