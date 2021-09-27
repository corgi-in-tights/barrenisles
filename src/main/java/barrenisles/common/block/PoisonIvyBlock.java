package barrenisles.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags.Blocks;

public class PoisonIvyBlock extends BushBlock {
    public PoisonIvyBlock(AbstractBlock.Properties settings) {
        super(settings);
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, IBlockReader world, BlockPos pos) {
        return floor.is(Blocks.SAND) || floor.is(Blocks.DIRT);
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity) {
        	entity.makeStuckInBlock(state, new Vector3d((double)0.8F, 0.75D, (double)0.8F));
            ((LivingEntity) entity).addEffect(new EffectInstance(Effects.POISON,3,1,true,false));
        }
    }
}