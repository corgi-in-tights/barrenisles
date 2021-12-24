package barrenisles.common.block;

import barrenisles.common.tileentity.GoldVaseTileEntity;
import barrenisles.common.tileentity.VaseTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BellBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.block.BlockRenderType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class GoldVaseBlock extends ContainerBlock
{
	public GoldVaseBlock()
	{
		super(AbstractBlock.Properties.of(Material.DECORATION).strength(0.6F).noOcclusion());
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return newBlockEntity(world);
	}

	@Nullable
	@Override
	public TileEntity newBlockEntity(IBlockReader level) {
		return new GoldVaseTileEntity();
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Override
	public ActionResultType use(BlockState state, World level, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		if (level.isClientSide) return ActionResultType.SUCCESS;
		if(player.isShiftKeyDown()) return ActionResultType.FAIL;

		INamedContainerProvider namedContainerProvider = this.getMenuProvider(state, level, pos);
		if (namedContainerProvider != null) {
			if (!(player instanceof ServerPlayerEntity)) return ActionResultType.FAIL;
			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
			NetworkHooks.openGui(serverPlayerEntity, namedContainerProvider, (packetBuffer)->{});
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	public void onRemove(BlockState state, World level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity tileentity = level.getBlockEntity(pos);
			if (tileentity instanceof GoldVaseTileEntity) {
				GoldVaseTileEntity vaseTileEntity = (GoldVaseTileEntity) tileentity;
				vaseTileEntity.dropAllContents(level, pos);
			}
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return false;
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, World level, BlockPos pos) {
		return 0;
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
		return VASE_SHAPE;
	}

	private static final VoxelShape FIRST_VASE_SHAPE = Block.box(
			3.0, 0.0, 3.0,
			13.0, 9.0, 13.0);
	private static final VoxelShape SECOND_VASE_SHAPE = Block.box(
			4.0, 9.0, 4.0,
			8.0, 2.0, 8.0);
	private static final VoxelShape THIRD_VASE_SHAPE = Block.box(
			5.0, 11.0, 5.0,
			6.0, 3.0, 6.0);
	private static final VoxelShape FOURTH_VASE_SHAPE = Block.box(
			4.0, 14.0, 4.0,
			8.0, 2.0, 8.0);
	private static final VoxelShape VASE_SHAPE = VoxelShapes.or(FIRST_VASE_SHAPE, SECOND_VASE_SHAPE, THIRD_VASE_SHAPE, FOURTH_VASE_SHAPE);
}