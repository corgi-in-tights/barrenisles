package barrenisles.api.tileentities;

import barrenisles.common.tileentity.inventory.GoldVaseContainer;
import barrenisles.common.tileentity.inventory.VaseContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;

public class BarrenIslesContainers {
	public static RegistryObject<ContainerType<GoldVaseContainer>> gold_vase_container;
	public static RegistryObject<ContainerType<VaseContainer>> vase_container;
}