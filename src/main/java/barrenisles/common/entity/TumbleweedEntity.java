package barrenisles.common.entity;

import java.util.EnumSet;

import javax.annotation.Nullable;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class TumbleweedEntity extends CreatureEntity implements IAnimatable {
    AnimationFactory factory = new AnimationFactory(this);
    BlockPos start_pos;

    public TumbleweedEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
        start_pos = this.blockPosition();
    }

    public static MutableAttribute createTumbleweedAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3)
                .add(Attributes.MOVEMENT_SPEED, 0.21F);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("tumbleweed.tumble", true));
        } else {

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
    protected void registerGoals() {
        this.goalSelector.addGoal(10, new TumbleGoal(this, 1.1D,1000000000));
        this.goalSelector.addGoal(11, new WanderGoal());
    }
    
    
    class WanderGoal extends Goal {
        WanderGoal() {
           this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
           return TumbleweedEntity.this.navigation.isDone() && TumbleweedEntity.this.random.nextInt(10) == 0;
        }

        public boolean canContinueToUse() {
           return TumbleweedEntity.this.navigation.isInProgress();
        }

        public void start() {
           Vector3d vector3d = this.findPos();
           if (vector3d != null) {
              TumbleweedEntity.this.navigation.moveTo(TumbleweedEntity.this.navigation.createPath(new BlockPos(vector3d), 1), 1.0D);
           }

        }

        @Nullable
        private Vector3d findPos() {
           Vector3d vector3d;
           if ( !TumbleweedEntity.this.closerThan(TumbleweedEntity.this.start_pos, 22) ) {
              Vector3d vector3d1 = Vector3d.atCenterOf(TumbleweedEntity.this.start_pos);
              vector3d = vector3d1.subtract(TumbleweedEntity.this.position()).normalize();
           } else {
              vector3d = TumbleweedEntity.this.getViewVector(0.0F);
           }
           
           Vector3d vector3d2 = RandomPositionGenerator.getLandPos(TumbleweedEntity.this, 8, 7);
           return vector3d2 != null ? vector3d2 : RandomPositionGenerator.getLandPos(TumbleweedEntity.this, 8, 4);
        }
     }
    
    private boolean closerThan(BlockPos pos, int weight) {
        return pos.closerThan(this.blockPosition(), (double)weight);
    }
    
    public class TumbleGoal extends WanderGoal {
        public final float probability;

        public TumbleGoal(CreatureEntity mob, double speed, float probability) {
            super();
            this.probability = probability;
        }

        @Nullable
        protected Vector3d findPos() {
            if (TumbleweedEntity.this.isInWaterOrBubble()) {
                Vector3d vec3d = RandomPositionGenerator.getLandPos(TumbleweedEntity.this, 15, 7);
                return vec3d == null ? super.findPos() : vec3d;
            } else {
                return TumbleweedEntity.this.getRandom().nextFloat() >= this.probability ? RandomPositionGenerator.getLandPos(TumbleweedEntity.this, 10, 7) : super.findPos();
            }
        }
    }
}