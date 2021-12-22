package barrenisles.common.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

import java.util.function.Predicate;

public class VaseContents implements IInventory {
    public static VaseContents createForTileEntity(int size,
                                                   Predicate<PlayerEntity> canPlayerAccessInventoryLambda,
                                                   Notify markDirtyNotificationLambda) {
        return new VaseContents(size, canPlayerAccessInventoryLambda, markDirtyNotificationLambda);
    }

    public static VaseContents createForClientSideContainer(int size) {
        return new VaseContents(size);
    }

    // ----Methods used to load / save the contents to NBT

    public CompoundNBT serializeNBT()  {
        return chestContents.serializeNBT();
    }

    public void deserializeNBT(CompoundNBT nbt)   {
        chestContents.deserializeNBT(nbt);
    }

    public void setCanPlayerAccessInventoryLambda(Predicate<PlayerEntity> canPlayerAccessInventoryLambda) {
        this.canPlayerAccessInventoryLambda = canPlayerAccessInventoryLambda;
    }
    public void setMarkDirtyNotificationLambda(Notify markDirtyNotificationLambda) {
        this.markDirtyNotificationLambda = markDirtyNotificationLambda;
    }
    public void setOpenInventoryNotificationLambda(Notify openInventoryNotificationLambda) {
        this.openInventoryNotificationLambda = openInventoryNotificationLambda;
    }
    public void setCloseInventoryNotificationLambda(Notify closeInventoryNotificationLambda) {
        this.closeInventoryNotificationLambda = closeInventoryNotificationLambda;
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return canPlayerAccessInventoryLambda.test(player);  // on the client, this does nothing. on the server, ask our parent TileEntity.
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return chestContents.isItemValid(index, stack);
    }

    @FunctionalInterface
    public interface Notify {
        void invoke();
    }

    @Override
    public void setChanged() {
        markDirtyNotificationLambda.invoke();
    }

    @Override
    public void startOpen(PlayerEntity player) {
        openInventoryNotificationLambda.invoke();
    }

    @Override
    public void stopOpen(PlayerEntity player) {
        closeInventoryNotificationLambda.invoke();
    }



    @Override
    public int getContainerSize() {
        return chestContents.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < chestContents.getSlots(); ++i) {
            if (!chestContents.getStackInSlot(i).isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return chestContents.getStackInSlot(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return chestContents.extractItem(index, count, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        int maxPossibleItemStackSize = chestContents.getSlotLimit(index);
        return chestContents.extractItem(index, maxPossibleItemStackSize, false);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        chestContents.setStackInSlot(index, stack);
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < chestContents.getSlots(); ++i) {
            chestContents.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    // ---------

    private VaseContents(int size) {
        this.chestContents = new ItemStackHandler(size);
    }

    private VaseContents(int size, Predicate<PlayerEntity> canPlayerAccessInventoryLambda, Notify markDirtyNotificationLambda) {
        this.chestContents = new ItemStackHandler(size);
        this.canPlayerAccessInventoryLambda = canPlayerAccessInventoryLambda;
        this.markDirtyNotificationLambda = markDirtyNotificationLambda;
    }

    private Predicate<PlayerEntity> canPlayerAccessInventoryLambda = x-> true;

    private Notify markDirtyNotificationLambda = ()->{};

    private Notify openInventoryNotificationLambda = ()->{};

    private Notify closeInventoryNotificationLambda = ()->{};

    private final ItemStackHandler chestContents;
}
