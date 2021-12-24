package barrenisles.init;

import static barrenisles.api.BarrenIslesEntities.*;

import barrenisles.common.entity.CoyoteEntity;
import barrenisles.common.entity.DuneraptorEntity;
import barrenisles.common.entity.TumbleweedEntity;
import barrenisles.core.BarrenIslesMod;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, BarrenIslesMod.MODID);
	
	static {
		duneraptor = ENTITIES.register("duneraptor", 
				() -> EntityType.Builder.of(DuneraptorEntity::new, EntityClassification.MONSTER)
				.sized(2.1875F, 1.325f)
				.setShouldReceiveVelocityUpdates(true)
				.build("duneraptor"));
		
		coyote = ENTITIES.register("coyote", 
				() -> EntityType.Builder.of(CoyoteEntity::new, EntityClassification.MONSTER)
				.sized(0.9f, 1.0f)
				.setShouldReceiveVelocityUpdates(true)
				.build("coyote"));
		
		tumbleweed = ENTITIES.register("tumbleweed", 
				() -> EntityType.Builder.of(TumbleweedEntity::new, EntityClassification.MONSTER)
				.sized(0.8125f, 0.8125f)
				.setShouldReceiveVelocityUpdates(true)
				.build("tumbleweed"));
	}
	
	public static void register(IEventBus bus) {
		ENTITIES.register(bus);
	}
}
