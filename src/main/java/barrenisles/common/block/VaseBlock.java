package barrenisles.common.block;

import barrenisles.common.tileentity.VaseTileEntity;
import net.minecraft.block.AbstractBlock;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class VaseBlock extends ContainerBlock
{
	public VaseBlock()
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
		return new VaseTileEntity();
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Override
	public ActionResultType use(BlockState state, World level, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		if (level.isClientSide) return ActionResultType.SUCCESS;

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
			if (tileentity instanceof VaseTileEntity) {
				VaseTileEntity vaseTileEntity = (VaseTileEntity) tileentity;
				vaseTileEntity.dropAllContents(level, pos);
			}
			super.onRemove(state, level, pos, newState, isMoving);  // call it last, because it removes the TileEntity
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
		return CHEST_SHAPE;
	}

	private static final Vector3d CHEST_MIN_CORNER = new Vector3d(1.0, 0.0, 1.0);
	private static final Vector3d CHEST_MAX_CORNER = new Vector3d(15.0, 8.0, 15.0);
	private static final VoxelShape CHEST_SHAPE = Block.box(
			CHEST_MIN_CORNER.x, CHEST_MIN_CORNER.y, CHEST_MIN_CORNER.z,
			CHEST_MAX_CORNER.x, CHEST_MAX_CORNER.y, CHEST_MAX_CORNER.z);
}