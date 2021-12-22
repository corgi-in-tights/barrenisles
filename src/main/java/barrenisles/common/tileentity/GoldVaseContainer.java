package barrenisles.common.tileentity;

import barrenisles.api.tileentities.BarrenIslesContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoldVaseContainer extends Container {

    public static GoldVaseContainer createContainerServerSide(int windowID, PlayerInventory playerInventory, VaseContents vaseContents) {
        return new GoldVaseContainer(windowID, playerInventory, vaseContents);
    }

    public static GoldVaseContainer createContainerClientSide(int windowID, PlayerInventory playerInventory, net.minecraft.network.PacketBuffer extraData) {
        VaseContents vaseContents = VaseContents.createForClientSideContainer(GoldVaseTileEntity.NUMBER_OF_SLOTS);
        return new GoldVaseContainer(windowID, playerInventory, vaseContents);
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = GoldVaseTileEntity.NUMBER_OF_SLOTS;

    public GoldVaseContainer(int windowID, PlayerInventory playerInventory, VaseContents vaseContents) {
        super(BarrenIslesContainers.gold_vase_container.get(), windowID);
        if (BarrenIslesContainers.gold_vase_container == null)
            throw new IllegalStateException("Must initialize containerBasicContainerType before constructing a ContainerBasic!");

        this.vaseContents = vaseContents;

        // Render slots for vase
        for(int x = 0; x < 5; ++x) {
            this.addSlot(new Slot(vaseContents, x, 44 + x * 18, 20));
        }
        // Render player inventory
        for(int y = 0; y < 3; ++y) {
            for(int x = 0; x < 9; ++x) {
                this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 51 + y * 18));
            }
        }

        // Render player hotbar
        for(int x = 0; x < 9; ++x) {
            this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 109));
        }
    }

    @Override
    public boolean stillValid(PlayerEntity player)
    {
        return vaseContents.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerEntity, int sourceSlotIndex)
    {
        Slot sourceSlot = this.slots.get(sourceSlotIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (sourceSlotIndex >= VANILLA_FIRST_SLOT_INDEX && sourceSlotIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!this.moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false)){
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (sourceSlotIndex >= TE_INVENTORY_FIRST_SLOT_INDEX && sourceSlotIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            LOGGER.warn("Invalid slotIndex:" + sourceSlotIndex);
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.isActive();
        }

        sourceSlot.onTake(playerEntity, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public void removed(PlayerEntity player)
    {
        super.removed(player);
    }

    private VaseContents vaseContents;
    private static final Logger LOGGER = LogManager.getLogger();
}