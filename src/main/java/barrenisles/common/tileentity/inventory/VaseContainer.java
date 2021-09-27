package barrenisles.common.tileentity.inventory;

import net.minecraft.inventory.container.Container;

import java.util.Objects;

import barrenisles.api.blocks.BarrenIslesBlocks;
import barrenisles.api.tileentities.BarrenIslesContainers;
import barrenisles.common.tileentity.GoldVaseTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;

public class VaseContainer extends Container {
	private IWorldPosCallable canInteractWithCallable;
	private final int containerRows = 27;
	private final IInventory container;
	
	public VaseContainer(int windowId, PlayerInventory playerInv, TileEntity tileEntity) {
		this(BarrenIslesContainers.vase_container.get(), windowId, playerInv, tileEntity, new Inventory(27));
	}
	
	public VaseContainer(int windowId, PlayerInventory playerInv, TileEntity tileEntity, IInventory inventory) {
		this(BarrenIslesContainers.vase_container.get(), windowId, playerInv, tileEntity, inventory);
	}
	
	public VaseContainer(ContainerType<?> menuType, int windowId, PlayerInventory playerInv, TileEntity tileEntity, IInventory inventory) {
		super(menuType, windowId);
		container = inventory;
		
		inventory.startOpen(playerInv.player);
	    int i = (this.containerRows - 4) * 18;

	      for(int j = 0; j < this.containerRows; ++j) {
	         for(int k = 0; k < 9; ++k) {
	            this.addSlot(new Slot(inventory, k + j * 9, 8 + k * 18, 18 + j * 18));
	         }
	      }

	      for(int l = 0; l < 3; ++l) {
	         for(int j1 = 0; j1 < 9; ++j1) {
	            this.addSlot(new Slot(inventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
	         }
	      }

	      for(int i1 = 0; i1 < 9; ++i1) {
	         this.addSlot(new Slot(inventory, i1, 8 + i1 * 18, 161 + i));
	      }
		
		canInteractWithCallable = IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos());
	}
	
	public VaseContainer(final int windowId, final PlayerInventory player_inv, final PacketBuffer data) {
        this(BarrenIslesContainers.vase_container.get(), windowId, player_inv, getTileEntity(player_inv, data), new Inventory(27));
    }

	@Override
	public boolean stillValid(PlayerEntity playerIn) {
		return stillValid(
			canInteractWithCallable, playerIn, BarrenIslesBlocks.vase.get());
	}
	
	private static GoldVaseTileEntity getTileEntity(PlayerInventory inventory, PacketBuffer data) {
		Objects.requireNonNull(inventory, "playerInventory cannot be null");
		Objects.requireNonNull(data, "data cannot be null");

		TileEntity tileAtPos = inventory.player.level.getBlockEntity(data.readBlockPos());

		if (tileAtPos instanceof GoldVaseTileEntity) return (GoldVaseTileEntity) tileAtPos;
		return null;
	}

	   public ItemStack quickMoveStack(PlayerEntity playerIn, int slotsCount) {
	      ItemStack itemstack = ItemStack.EMPTY;
	      Slot slot = this.slots.get(slotsCount);
	      if (slot != null && slot.hasItem()) {
	         ItemStack itemstack1 = slot.getItem();
	         itemstack = itemstack1.copy();
	         if (slotsCount < this.containerRows * 9) {
	            if (!this.moveItemStackTo(itemstack1, this.containerRows * 9, this.slots.size(), true)) {
	               return ItemStack.EMPTY;
	            }
	         } else if (!this.moveItemStackTo(itemstack1, 0, this.containerRows * 9, false)) {
	            return ItemStack.EMPTY;
	         }

	         if (itemstack1.isEmpty()) {
	            slot.set(ItemStack.EMPTY);
	         } else {
	            slot.setChanged();
	         }
	      }

	      return itemstack;
	   }

	   public void removed(PlayerEntity playerIn) {
	      super.removed(playerIn);
	      this.container.stopOpen(playerIn);
	   }

	   public IInventory getContainer() {
	      return this.container;
	   }

	   @OnlyIn(Dist.CLIENT)
	   public int getRowCount() {
	      return this.containerRows;
	   }
}