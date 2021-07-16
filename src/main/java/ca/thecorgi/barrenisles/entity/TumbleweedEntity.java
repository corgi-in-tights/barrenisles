package ca.thecorgi.barrenisles.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import static ca.thecorgi.barrenisles.BarrenIsles.config;


@SuppressWarnings("EntityConstructor")
public class TumbleweedEntity extends PathAwareEntity implements IAnimatable {
    AnimationFactory factory = new AnimationFactory(this);

    public TumbleweedEntity(EntityType<? extends PathAwareEntity> type, World world) {
        super(type, world);
        this.ignoreCameraFrustum = true;
    }

    public static DefaultAttributeContainer.Builder createTumbleweedAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, config.stats.tumbleweed_health)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, config.stats.tumbleweed_speed);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("tumbleweed.tumble", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<TumbleweedEntity>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new TumbleGoal(this, 1.1D,1000F));
    }
}
