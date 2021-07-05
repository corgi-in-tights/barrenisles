package ca.thecorgi.barrenisles.utils.registry;

//import ca.thecorgi.barrenisles.entity.BuramaphuEntity;

import ca.thecorgi.barrenisles.entity.DuneraptorEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.example.registry.EntityRegistryBuilder;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;

public class EntityRegistry {
    public static final EntityType<DuneraptorEntity> DUNERAPTOR = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "duneraptor"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DuneraptorEntity::new).dimensions(EntityDimensions.fixed(1.15F, 1.9F)).build());


    public static void init() {
        FabricDefaultAttributeRegistry.register(DUNERAPTOR, DuneraptorEntity.createDuneraptorAttributes());

    }
}