package barrenisles.common.tileentity;

import barrenisles.api.BarrenIslesTileEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class VaseTileEntity extends TileEntity implements INamedContainerProvider {
    public static final int NUMBER_OF_SLOTS = 3;

    public VaseTileEntity()
    {
        super(BarrenIslesTileEntityType.vase_tile_entity.get());
        vaseContents = VaseContents.createForTileEntity(NUMBER_OF_SLOTS,
                this::canPlayerAccessInventory, this::setChanged);
    }
    public boolean canPlayerAccessInventory(PlayerEntity player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) return false;
        final double X_CENTRE_OFFSET = 0.5;
        final double Y_CENTRE_OFFSET = 0.5;
        final double Z_CENTRE_OFFSET = 0.5;
        final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
        return player.distanceToSqr(worldPosition.getX() + X_CENTRE_OFFSET, worldPosition.getY() + Y_CENTRE_OFFSET, worldPosition.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
    }



    private static final String CHESTCONTENTS_INVENTORY_TAG = "contents";

    @Override
    public CompoundNBT save(CompoundNBT tag)
    {
        super.save(tag);
        CompoundNBT inventoryNBT = vaseContents.serializeNBT();
        tag.put(CHESTCONTENTS_INVENTORY_TAG, inventoryNBT);
        return tag;
    }

    @Override
    public void load(BlockState blockState, CompoundNBT parentNBTTagCompound)
    {
        super.load(blockState, parentNBTTagCompound); // The super call is required to save and load the tiles location
        CompoundNBT inventoryNBT = parentNBTTagCompound.getCompound(CHESTCONTENTS_INVENTORY_TAG);
        vaseContents.deserializeNBT(inventoryNBT);
        if (vaseContents.getContainerSize() != NUMBER_OF_SLOTS)
            throw new IllegalArgumentException("Corrupted NBT: Number of inventory slots did not match expected.");
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        CompoundNBT nbtTagCompound = new CompoundNBT();
        save(nbtTagCompound);
        int tileEntityType = 42;  // arbitrary number; only used for vanilla TileEntities.  You can use it, or not, as you want.
        return new SUpdateTileEntityPacket(this.worldPosition, tileEntityType, nbtTagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        BlockState blockState = level.getBlockState(worldPosition);
        load(blockState, pkt.getTag());
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        CompoundNBT nbtTagCompound = new CompoundNBT();
        save(nbtTagCompound);
        return nbtTagCompound;
    }

    @Override
    public void handleUpdateTag(BlockState blockState, CompoundNBT tag)
    {
        this.load(blockState, tag);
    }

    public void dropAllContents(World world, BlockPos blockPos) {
        InventoryHelper.dropContents(world, blockPos, vaseContents);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.barrenisles.vase");
    }

    @Nullable
    @Override
    public Container createMenu(int containerId, PlayerInventory inventory, PlayerEntity player) {
        return VaseContainer.createContainerServerSide(containerId, inventory, vaseContents);
    }

    private final VaseContents vaseContents;
}