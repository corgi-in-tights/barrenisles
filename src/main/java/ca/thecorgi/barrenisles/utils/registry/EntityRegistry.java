package ca.thecorgi.barrenisles.utils.registry;

import ca.thecorgi.barrenisles.entity.BuramaphuEntity;

import ca.thecorgi.barrenisles.entity.DuneraptorEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import software.bernie.example.registry.EntityRegistryBuilder;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;

public class EntityRegistry {
    public static final EntityType<DuneraptorEntity> DUNERAPTOR_ENTITY = buildEntity(DuneraptorEntity::new,
            DuneraptorEntity.class, 1.5F, 2.0F);
    public static final EntityType<BuramaphuEntity> BURAMAPHU_ENTITY = buildEntity(BuramaphuEntity::new,
            BuramaphuEntity.class, 3F, 2.5F);

    public static <T extends Entity> EntityType<T> buildEntity(EntityType.EntityFactory<T> entity, Class<T> entityClass,
                                                               float width, float height) {
            String name = entityClass.getSimpleName().toLowerCase();
            return EntityRegistryBuilder.<T>createBuilder(new Identifier(ModID, name)).entity(entity)
                    .category(SpawnGroup.CREATURE).dimensions(EntityDimensions.changing(width, height)).build();
    }
}