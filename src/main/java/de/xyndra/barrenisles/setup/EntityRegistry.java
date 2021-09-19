package de.xyndra.barrenisles.setup;

import de.xyndra.barrenisles.entitys.duneraptor.DuneraptorEntity;
import de.xyndra.barrenisles.items.DuneraptorSpawnEgg;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class EntityRegistry {

	public static RegistryObject<EntityType<DuneraptorEntity>> DUNERAPTOR_ENTITY = Registration.ENTITIES.register("duneraptor", () -> EntityType.Builder.of(DuneraptorEntity::new, EntityClassification.CREATURE).sized(1.2f, 1.8f).setShouldReceiveVelocityUpdates(true).build("duneraptor"));
	public static RegistryObject<Item> DUNERAPTORSPAWNEGG = Registration.ITEMS.register("duneraptor_spawn_egg", () -> new DuneraptorSpawnEgg(DUNERAPTOR_ENTITY, -2791651, -2779875, new Item.Properties().tab(ModItems.creativeTab)));

	public static void register() {}
}