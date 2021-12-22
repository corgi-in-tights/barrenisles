package barrenisles.api.tileentities;

import barrenisles.common.tileentity.GoldVaseTileEntity;
import barrenisles.common.tileentity.VaseTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class BarrenIslesTileEntityType {
	public static RegistryObject<TileEntityType<VaseTileEntity>> vase_tile_entity;
	public static RegistryObject<TileEntityType<GoldVaseTileEntity>> gold_vase_tile_entity;
}