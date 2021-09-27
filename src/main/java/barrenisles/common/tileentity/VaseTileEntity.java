package barrenisles.common.tileentity;

import javax.annotation.Nullable;

import barrenisles.api.blocks.BarrenIslesBlocks;
import barrenisles.api.tileentities.BarrenIslesTileEntityType;
import barrenisles.common.block.VaseBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.extensions.IForgeTileEntity;

public class VaseTileEntity extends TileEntity implements IForgeTileEntity, INamedContainerProvider {
	public float openness;
	public float oOpenness;
	public int openCount;

	public VaseTileEntity() {
		super(BarrenIslesTileEntityType.vase_tileentity.get());
	}
	
	public boolean triggerEvent(int num, int count) {
		if (num == 1) {
			this.openCount = count;
	        return true;
	    } else {
	        return super.triggerEvent(num, count);
	    }
	}

	public void setRemoved() {
		this.clearCache();
	    super.setRemoved();
	}

	public void startOpen() {
		++this.openCount;
	    this.level.blockEvent(this.worldPosition, BarrenIslesBlocks.vase.get(), 1, this.openCount);
	}

	public void stopOpen() {
		--this.openCount;
	    this.level.blockEvent(this.worldPosition, BarrenIslesBlocks.vase.get(), 1, this.openCount);
	}

	public boolean stillValid(PlayerEntity player) {
		if (this.level.getBlockEntity(this.worldPosition) != this) {
			return false;
	    } else {
	        return !(player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) > 64.0D);
	    }
	}

	@Nullable
	@Override
	public Container createMenu(int windowId, PlayerInventory playerInv, PlayerEntity player) {
		return ChestContainer.threeRows(windowId, playerInv);
	}

	@Override
	public ITextComponent getDisplayName() {
		return VaseBlock.CONTAINER_TITLE;
	}
}