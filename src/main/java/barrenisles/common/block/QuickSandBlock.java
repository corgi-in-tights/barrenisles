package barrenisles.common.block;

import barrenisles.api.BarrenIslesBlocks;
import barrenisles.api.BarrenIslesEntities;
import barrenisles.init.ModEntities;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.EntitySelectionContext;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import barrenisles.common.util.block.BlockUtil;

import java.util.Random;

public class QuickSandBlock extends Block{
	
    public QuickSandBlock(AbstractBlock.Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && entity.getType() != BarrenIslesEntities.duneraptor.get() && entity.getType() != BarrenIslesEntities.tumbleweed.get()) {
            entity.makeStuckInBlock(state, new Vector3d(0.24, 0.105D, 0.24));
            if (world.getBlockState(new BlockPos(entity.getX(), entity.getY() + 1.6F, entity.getZ())).getBlock() == BarrenIslesBlocks.quicksand.get() && world.getBlockState(pos).getBlock() == BarrenIslesBlocks.quicksand.get()) {
                entity.hurt(DamageSource.IN_WALL, 0.5F);
            }
        }

        if (world.isClientSide) {
            Random random = world.getRandom();
            boolean bl = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
            if (bl && random.nextBoolean()) {
                world.addParticle(ParticleTypes.WHITE_ASH, entity.getX(), entity.getY(), entity.getZ(),0.1,0.1,0.1);
            }
        }
    }

    private static final VoxelShape COLLISION_SHAPE = VoxelShapes.box(
            0.0D, 0.0D, 0.0D,
            1.0D, 0.8999999761581421D, 1.0D);

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        if (context instanceof EntitySelectionContext) {
            EntitySelectionContext entityShapeContext = (EntitySelectionContext) context;
            Entity entity = entityShapeContext.getEntity();
            if (entity != null) {
                if (entity.fallDistance > 2.5F) {
                    return COLLISION_SHAPE;
                }

                boolean bl = entity instanceof FallingBlockEntity;
                if (bl && context.isAbove(VoxelShapes.block(), pos, false) && !context.isDescending()) {
                    return super.getCollisionShape(state, world, pos, context);
                }
            }
        }

        return VoxelShapes.empty();
    }
}