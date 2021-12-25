package barrenisles.common.item;

import javax.annotation.Nullable;

import barrenisles.api.BarrenIslesBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class QuicksandBucket extends Item {
	private final Block content;
	private final java.util.function.Supplier<? extends Block> blockSupplier;

	public QuicksandBucket(java.util.function.Supplier<? extends Block> supplier, Item.Properties builder) {
		super(builder);
		this.content = BarrenIslesBlocks.quicksand.get();
		this.blockSupplier = supplier;
	}

	protected ItemStack getEmptySuccessItem(ItemStack items, PlayerEntity playerIn) {
		return !playerIn.abilities.instabuild ? new ItemStack(Items.BUCKET) : items;
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		BlockRayTraceResult rayTraceResult = getPlayerPOVHitResult(world, player, RayTraceContext.FluidMode.NONE);
		ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(player, world, itemstack, rayTraceResult);
		if (ret != null) return ret;
		if (rayTraceResult.getType() == RayTraceResult.Type.MISS) {
			return ActionResult.pass(itemstack);
		} else if (rayTraceResult.getType() != RayTraceResult.Type.BLOCK) {
			return ActionResult.pass(itemstack);
		} else {
			BlockRayTraceResult blockRayTraceResult = rayTraceResult;
			BlockPos blockpos = blockRayTraceResult.getBlockPos();
			Direction direction = blockRayTraceResult.getDirection();
			BlockPos blockPos1 = blockpos.relative(direction);
			if (world.mayInteract(player, blockpos) && player.mayUseItemAt(blockPos1, direction, itemstack)) {
					BlockState blockstate = world.getBlockState(blockpos);
					BlockPos blockPos2 = blockstate.isAir(world, blockpos) ? blockpos : blockPos1;
					if (this.emptyBucket(player, world, blockPos2, blockRayTraceResult)) {
						if (player instanceof ServerPlayerEntity) {
							CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)player, blockPos2, itemstack);
						}
						if(!player.isCreative()) {
							player.setItemInHand(hand, new ItemStack(Items.BUCKET));
						}
						player.awardStat(Stats.ITEM_USED.get(this));
						return ActionResult.sidedSuccess(this.getEmptySuccessItem(itemstack, player), world.isClientSide());
					} else {
						return ActionResult.fail(itemstack);
					}
				}
		}
		return super.use(world, player, hand);
	}

	public boolean emptyBucket(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockRayTraceResult raytraceResult) {
		BlockState blockstate = world.getBlockState(pos);
		Material material = blockstate.getMaterial();
		boolean flag1 = blockstate.getMaterial() == Material.AIR;
		if (!flag1) return false;
		else {
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
	protected void playEmptySound(@Nullable PlayerEntity player, IWorld world, BlockPos pos) {
		world.playSound(player, pos, SoundEvents.BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}
}
