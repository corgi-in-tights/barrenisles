package barrenisles.init;

import static barrenisles.api.tileentities.BarrenIslesContainers.*;

import barrenisles.common.tileentity.inventory.GoldVaseContainer;
import barrenisles.common.tileentity.inventory.VaseContainer;
import barrenisles.core.BarrenIslesMod;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, BarrenIslesMod.MODID);

    static {
    	gold_vase_container =  CONTAINERS.register("gold_vase", () -> IForgeContainerType.create(GoldVaseContainer::new));
    	vase_container =  CONTAINERS.register("vase", () -> IForgeContainerType.create(VaseContainer::new));
    }
    
    public static void register(IEventBus bus) {
		CONTAINERS.register(bus);
	}
}