package barrenisles.common.item;

import javax.annotation.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.Items;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import barrenisles.common.block.QuickSandBlock;

public class BucketBlockItem extends Item {
	   private final Block content;
	   private final java.util.function.Supplier<? extends Block> blockSupplier;

	   // Forge: Use the other constructor that takes a Supplier
	   @Deprecated
	   public BucketBlockItem(Block block, Item.Properties settings) {
	      super(settings);
	      this.content = block;
	      this.blockSupplier = block.delegate;
	   }

	   /**
	    * @param supplier A block supplier such as {@link net.minecraftforge.fml.RegistryObject<Fluid>}
	    */
	   public BucketBlockItem(java.util.function.Supplier<? extends Block> supplier, Item.Properties builder) {
	      super(builder);
	      this.content = null;
	      this.blockSupplier = supplier;
	   }

	   public ActionResult<ItemStack> use(World world, PlayerEntity playerIn, Hand hand) {
	      ItemStack itemstack = playerIn.getItemInHand(hand);
	      RayTraceResult raytraceresult = getPlayerPOVHitResult(world, playerIn, RayTraceContext.FluidMode.NONE);
	      ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(playerIn, world, itemstack, raytraceresult);
	      if (ret != null) return ret;
	      if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
	         return ActionResult.pass(itemstack);
	      } 
	      else if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
	         return ActionResult.pass(itemstack);
	      } 
	      else {
	         BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
	         BlockPos blockpos = blockraytraceresult.getBlockPos();
	         Direction direction = blockraytraceresult.getDirection();
	         BlockPos blockpos1 = blockpos.relative(direction);
	         if (world.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos1, direction, itemstack)) {
	               BlockState blockstate1 = world.getBlockState(blockpos);
	               if (blockstate1.getBlock() instanceof QuickSandBlock) {
	            	  Block block = ((QuickSandBlock) blockstate1.getBlock());
	                  playerIn.awardStat(Stats.ITEM_USED.get(this));
	                  playerIn.playSound(SoundEvents.BUCKET_FILL, 1.0F, 1.0F);
	                  ItemStack itemstack1 = DrinkHelper.createFilledResult(itemstack, playerIn, new ItemStack(block));
	                  if (!world.isClientSide) 
	                  {
	                     CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity)playerIn, new ItemStack(block));
	                  }
	                  return ActionResult.sidedSuccess(itemstack1, world.isClientSide());
	               }

	               return ActionResult.fail(itemstack);
	         } 
	         else {
	         return ActionResult.fail(itemstack);
	         }
	      }
	   }

	   protected ItemStack getEmptySuccessItem(ItemStack items, PlayerEntity playerIn) {
	      return !playerIn.abilities.instabuild ? new ItemStack(Items.BUCKET) : items;
	   }

	   public void checkExtraContent(World p_203792_1_, ItemStack p_203792_2_, BlockPos p_203792_3_) {
	   }

	public boolean emptyBucket(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockRayTraceResult raytraceResult) {
		   BlockState blockstate = world.getBlockState(pos);
	       Material material = blockstate.getMaterial();
	       boolean flag1 = blockstate.getMaterial() == Material.AIR;
	       if (!flag1) {
	    	   return raytraceResult != null && this.emptyBucket(player, world, raytraceResult.getBlockPos().relative(raytraceResult.getDirection()), (BlockRayTraceResult)null);
	       } else {
	    	   if (!world.isClientSide && !material.isLiquid()) {
	    		   world.destroyBlock(pos, true);
	           }
	    	   if (!world.setBlock(pos, this.content.defaultBlockState(), 11)) {
	    		   return false;
	           } else {
	               this.playEmptySound(player, world, pos);
	               return true;
	           }
	       }
	   }

	   protected void playEmptySound(@Nullable PlayerEntity playerIn, IWorld world, BlockPos pos) {
	      world.playSound(playerIn, pos, SoundEvents.BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
	   }

	   @Override
	   public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack items, @Nullable net.minecraft.nbt.CompoundNBT nbt) {
	      if (this.getClass() == BucketBlockItem.class)
	         return new net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper(items);
	      else
	         return super.initCapabilities(items, nbt);
	   }
	   public Block getBlock() { return blockSupplier.get(); }
	}
