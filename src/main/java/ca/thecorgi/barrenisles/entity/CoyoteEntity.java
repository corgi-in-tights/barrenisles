package ca.thecorgi.barrenisles.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.Durations;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

import static ca.thecorgi.barrenisles.BarrenIsles.config;


@SuppressWarnings("EntityConstructor")
public class CoyoteEntity extends AnimalEntity implements IAnimatable, Angerable {
    private static final TrackedData<Integer> ANGER_TIME;
    private static final UniformIntProvider ANGER_TIME_RANGE;
    private UUID targetUuid;

    AnimationFactory factory = new AnimationFactory(this);

    public CoyoteEntity(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
        this.ignoreCameraFrustum = true;
//        this.setTamed(false);
    }


    public static DefaultAttributeContainer.Builder createCoyoteAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, config.stats.coyote_health)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, config.stats.coyote_speed)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, config.stats.coyote_attack_damage);
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && !this.isAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("coyote.walk", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("coyote.idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<CoyoteEntity>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }


    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(4, new PounceAtTargetGoal(this, 0.4F));
        this.goalSelector.add(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(7, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(10, new LookAroundGoal(this));
        this.targetSelector.add(3, (new RevengeGoal(this, new Class[0])).setGroupRevenge());
        this.targetSelector.add(4, new FollowTargetGoal<PlayerEntity>(this, PlayerEntity.class,true));
        this.targetSelector.add(8, new UniversalAngerGoal(this, true));
    }

//    @Override
//    public void setTamed(boolean tamed) {
//    }

    protected boolean isHungry(CoyoteEntity entity) {
        long x = entity.world.getTimeOfDay();
        if (x > 13000 && x < 24000) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void tick() {
//        Box box = this.getBoundingBox().expand(6, 6, 6);
        CoyoteEntity.this.setTarget((LivingEntity)null);
        super.tick();
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public int getAngerTime() {
        return (Integer)this.dataTracker.get(ANGER_TIME);
    }

    @Override
    public void setAngerTime(int ticks) {
        this.dataTracker.set(ANGER_TIME, ticks);
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return this.targetUuid;
    }

    @Override
    public void setAngryAt(@Nullable UUID uuid) {
        this.targetUuid = uuid;
    }

    static {
        ANGER_TIME = DataTracker.registerData(CoyoteEntity.class, TrackedDataHandlerRegistry.INTEGER);
        ANGER_TIME_RANGE = Durations.betweenSeconds(18, 39);
    }

}



