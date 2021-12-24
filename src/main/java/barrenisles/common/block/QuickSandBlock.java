package barrenisles.common.block;

import barrenisles.api.BarrenIslesEntities;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import barrenisles.common.util.block.BlockUtil;

public class QuickSandBlock extends Block{
	
    public QuickSandBlock(AbstractBlock.Properties settings) {
        super(settings);
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity || BlockUtil.getBlockStateOn(entity).is(this) && entity.getType() != BarrenIslesEntities.duneraptor.get() && entity.getType() != BarrenIslesEntities.tumbleweed.get()) {
            entity.makeStuckInBlock(state, new Vector3d(0.24F, 0.75D, 0.24F));
        }
    }

    public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader level, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }
}