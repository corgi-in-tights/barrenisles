package barrenisles.common.block;

import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import static barrenisles.api.items.BarrenIslesItems.suspicious_berries;

import barrenisles.api.items.BarrenIslesItems; 

public class SuspiciousBerryBushBlock extends SweetBerryBushBlock {
	
	public SuspiciousBerryBushBlock(AbstractBlock.Properties settings) {
		super(settings);
	}

    @Override
    public ItemStack getCloneItemStack(IBlockReader world, BlockPos pos, BlockState state) {
        return new ItemStack(suspicious_berries.get());
    }
    
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult raytrace) {
        int i = state.getValue(AGE);
        boolean flag = i == 3;
        if (!flag && player.getItemInHand(hand).getItem() == Items.BONE_MEAL) {
           return ActionResultType.PASS;
        } else if (i > 1) {
           int j = 1 + world.random.nextInt(2);
           popResource(world, pos, new ItemStack(BarrenIslesItems.suspicious_berries.get(), j + (flag ? 1 : 0)));
           world.playSound((PlayerEntity)null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
           world.setBlock(pos, state.setValue(AGE, Integer.valueOf(1)), 2);
           return ActionResultType.sidedSuccess(world.isClientSide);
        } else {
           return super.use(state, world, pos, player, hand, raytrace);
        }
     }
    
    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.FOX && entityIn.getType() != EntityType.BEE) {
           entityIn.makeStuckInBlock(state, new Vector3d((double)0.8F, 0.75D, (double)0.8F));
           if (!world.isClientSide && state.getValue(AGE) > 0 && (entityIn.xOld != entityIn.getX() || entityIn.zOld != entityIn.getZ())) {
              double d0 = Math.abs(entityIn.getX() - entityIn.xOld);
              double d1 = Math.abs(entityIn.getZ() - entityIn.zOld);
              if (d0 >= (double)0.003F || d1 >= (double)0.003F) {
                 entityIn.hurt(DamageSource.SWEET_BERRY_BUSH, 1.0F);
              }
           }

        }
     }
}