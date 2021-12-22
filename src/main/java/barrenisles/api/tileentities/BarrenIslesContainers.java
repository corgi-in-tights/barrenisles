package barrenisles.api.tileentities;

import barrenisles.common.tileentity.GoldVaseContainer;
import barrenisles.common.tileentity.VaseContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;

public class BarrenIslesContainers {
	public static RegistryObject<ContainerType<VaseContainer>> vase_container;
	public static RegistryObject<ContainerType<GoldVaseContainer>> gold_vase_container;
}