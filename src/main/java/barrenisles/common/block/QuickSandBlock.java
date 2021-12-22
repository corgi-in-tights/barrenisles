package barrenisles.common.block;

import barrenisles.api.entity.BarrenIslesEntities;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
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
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || BlockUtil.getBlockStateOn(entity).is(this) && entity.getType() != BarrenIslesEntities.duneraptor.get() && entity.getType() != BarrenIslesEntities.tumbleweed.get()) {
            entity.makeStuckInBlock(state, new Vector3d(0.24F, 0.75D, 0.24F));
        }
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader level, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }



    /* Coming soon ...
    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(SoundEvents.COMPOSTER_FILL);
    }*/
}