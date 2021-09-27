package barrenisles.api.entity;

import barrenisles.common.entity.CoyoteEntity;
import barrenisles.common.entity.DuneraptorEntity;
import barrenisles.common.entity.TumbleweedEntity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;

public class BarrenIslesEntities {
	public static RegistryObject<EntityType<DuneraptorEntity>> duneraptor;
	public static RegistryObject<EntityType<CoyoteEntity>> coyote;
	public static RegistryObject<EntityType<TumbleweedEntity>> tumbleweed;
}
