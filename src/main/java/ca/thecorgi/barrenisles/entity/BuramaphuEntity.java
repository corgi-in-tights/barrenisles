package ca.thecorgi.barrenisles.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BuramaphuEntity extends HostileEntity implements Monster, IAnimatable {
    AnimationFactory factory = new AnimationFactory(this);

    public BuramaphuEntity(EntityType<? extends HostileEntity> type, World worldIn) {
        super(type, worldIn);
        this.ignoreCameraFrustum = true;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && !this.isAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("buramaphu.walk", true));
        }
        if (this.isAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("buramaphu.attack", false));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("buramaphu.idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<BuramaphuEntity>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(7, new WanderAroundGoal(this, 0.23D, 20));
        // Entity will look at Player.
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 6));
        // Entity will look around
        this.goalSelector.add(5, new LookAroundGoal(this));
        // Entity will melee-attack
        this.goalSelector.add(4, new MeleeAttackGoal(this, 0.23D, true));
        // Entity will attempt revenge
    }

}
