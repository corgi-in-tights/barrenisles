package ca.thecorgi.barrenisles.utils.registry;

import ca.thecorgi.barrenisles.entity.DuneraptorEntity;
import ca.thecorgi.barrenisles.entity.TumbleweedEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;

public class EntityRegistry {
    public static final EntityType<DuneraptorEntity> DUNERAPTOR = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "duneraptor"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DuneraptorEntity::new).dimensions(EntityDimensions.fixed(1.15F, 1.9F)).build());
    public static final EntityType<TumbleweedEntity> TUMBLEWEED = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "tumbleweed"),
            FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, TumbleweedEntity::new).dimensions(EntityDimensions.fixed(1F, 1F)).build());


    public static void register() {
        FabricDefaultAttributeRegistry.register(DUNERAPTOR, DuneraptorEntity.createDuneraptorAttributes());
        FabricDefaultAttributeRegistry.register(TUMBLEWEED, TumbleweedEntity.createTumbleweedAttributes());
    }
}