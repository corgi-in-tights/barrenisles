package ca.thecorgi.barrenisles.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;

public class EntityUtils {
    public static DefaultAttributeContainer.Builder createDuneraptor() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 25.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4D)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.8D);
    }
}
