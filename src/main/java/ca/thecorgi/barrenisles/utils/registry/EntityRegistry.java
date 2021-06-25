package ca.thecorgi.barrenisles.utils.registry;

import ca.thecorgi.barrenisles.entity.duneraptor.DuneraptorEntity;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import software.bernie.example.registry.EntityRegistryBuilder;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;

public class EntityRegistry {
    public static final EntityType<DuneraptorEntity> DUNERAPTOR_ENTITY = buildEntity(DuneraptorEntity::new,
            DuneraptorEntity.class, 3, 1);

    public static <T extends Entity> EntityType<T> buildEntity(EntityType.EntityFactory<T> entity, Class<T> entityClass,
                                                               int width, int height) {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            String name = entityClass.getSimpleName().toLowerCase();
            return EntityRegistryBuilder.<T>createBuilder(new Identifier(ModID, name)).entity(entity)
                    .category(SpawnGroup.CREATURE).dimensions(EntityDimensions.changing(width, height)).build();
        }
        return null;
    }
}