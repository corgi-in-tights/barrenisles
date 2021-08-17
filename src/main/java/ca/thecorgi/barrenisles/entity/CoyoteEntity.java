package ca.thecorgi.barrenisles.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
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
public class CoyoteEntity extends SpiderEntity implements IAnimatable {
//    private static final TrackedData<Integer> ANGER_TIME;
//    private static final UniformIntProvider ANGER_TIME_RANGE;
//    private UUID targetUuid;

    AnimationFactory factory = new AnimationFactory(this);

    public CoyoteEntity(EntityType<? extends SpiderEntity> type, World world) {
        super(type, world);
        this.ignoreCameraFrustum = true;
    }


    public static DefaultAttributeContainer.Builder createCoyoteAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, config.stats.coyote_health)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, config.stats.coyote_speed)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, config.stats.coyote_attack_damage);
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
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
//
//
//    @Override
//    protected void initGoals() {
//        this.goalSelector.add(1, new SwimGoal(this));
//        this.targetSelector.add(2, (new RevengeGoal(this, new Class[0])).setGroupRevenge());
//        this.targetSelector.add(3, new FollowTargetGoal<PlayerEntity>(this, PlayerEntity.class,true));
//        this.goalSelector.add(4, new PounceAtTargetGoal(this, 0.4F));
//        this.goalSelector.add(5, new NightAttackGoal(this));
//        this.goalSelector.add(6, new AnimalMateGoal(this, 1.0D));
//        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0D));
//        this.targetSelector.add(8, new UniversalAngerGoal(this, true));
//        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
//        this.goalSelector.add(10, new LookAroundGoal(this));
//    }

//    @Override
//    public void setTamed(boolean tamed) {
//    }

//    @Nullable
//    @Override
//    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
//        return null;
//    }
////
//    protected void initDataTracker() {
//        super.initDataTracker();
////        this.dataTracker.startTracking(ANGER_TIME, 0);
//    }
//
//    public void writeCustomDataToNbt(NbtCompound nbt) {
//        super.writeCustomDataToNbt(nbt);
////        this.writeAngerToNbt(nbt);
//    }
//
//    public void readCustomDataFromNbt(NbtCompound nbt) {
//        super.readCustomDataFromNbt(nbt);
////        this.readAngerFromNbt(this.world, nbt);
//    }
//
//    public void tickMovement() {
//        super.tickMovement();
//        if (!this.world.isClient && this.onGround) {
//            this.world.sendEntityStatus(this, (byte)8);
//        }
//
//        if (!this.world.isClient) {
//            this.tickAngerLogic((ServerWorld)this.world, true);
//        }
//
//    }

    public void tick() {
        super.tick();
            }

    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getAttacker();
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof PersistentProjectileEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.damage(source, amount);
        }
    }

    private static class NightAttackGoal extends MeleeAttackGoal {
        public NightAttackGoal(CoyoteEntity coyote) {
            super(coyote, 1.0D, true);
        }

        public boolean canStart() {
            return super.canStart() && !this.mob.hasPassengers();
        }

        private static boolean isNightHungry(World world, BlockPos pos) {
            int i = world.getLightLevel(LightType.SKY, pos) - world.getAmbientDarkness();
            float f = world.getSkyAngleRadians(1.0F);
            if (i > 0) {
                float g = f < 3.1415927F ? 0.0F : 6.2831855F;
                f += (g - f) * 0.2F;
                i = Math.round((float)i * MathHelper.cos(f));
                System.out.println("True");
                return true;
            } else {
                System.out.println("False");
                return false;
            }
        }

        public boolean shouldContinue() {
            if (!isNightHungry(this.mob.world, this.mob.getBlockPos())) {
                this.mob.setTarget((LivingEntity)null);
                return false;
            } else {
                return super.shouldContinue();
            }
        }

        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return (double)(4.0F + entity.getWidth());
        }
    }

    public boolean tryAttack(Entity target) {
        boolean bl = target.damage(DamageSource.mob(this), (float)((int)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)));
        if (bl) {
            this.applyDamageEffects(this, target);
        }

        return bl;
    }

    public void handleStatus(byte status) {
            super.handleStatus(status);
        }


//    public boolean isBreedingItem(ItemStack stack) {
//        Item item = stack.getItem();
//        return item.isFood() && item.getFoodComponent().isMeat();
//    }

    public int getLimitPerChunk() {
        return 8;
    }

//    public int getAngerTime() {
//        return (Integer)this.dataTracker.get(ANGER_TIME);
//    }
//
//    public void setAngerTime(int ticks) {
//        this.dataTracker.set(ANGER_TIME, ticks);
//    }
//
//    public void chooseRandomAngerTime() {
//        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
//    }
//
//    @Nullable
//    public UUID getAngryAt() {
//        return this.targetUuid;
//    }
//
//    public void setAngryAt(@Nullable UUID uuid) {
//        this.targetUuid = uuid;
//    }

//    public CoyoteEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
//        CoyoteEntity coyoteEntity = (CoyoteEntity)EntityType.WOLF.create(serverWorld);
//        if (uUID != null) {
//            coyoteEntity.setOwnerUuid(uUID);
//            coyoteEntity.setTamed(true);
//        }
//
//        return coyoteEntity;
//    }

//    public boolean canBreedWith(AnimalEntity other) {
//        if (other == this) {
//            return false;
//        } else if (!(other instanceof CoyoteEntity)) {
//            return false;
//        } else {
//            CoyoteEntity coyoteEntity = (CoyoteEntity)other;
//            return this.isInLove() && coyoteEntity.isInLove();
//        }
//    }

//    public boolean canBeLeashedBy(PlayerEntity player) {
//        return !this.hasAngerTime() && super.canBeLeashedBy(player);
//    }

    public Vec3d getLeashOffset() {
        return new Vec3d(0.0D, (double)(0.6F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.4F));
    }

//    static {
//        ANGER_TIME = DataTracker.registerData(CoyoteEntity.class, TrackedDataHandlerRegistry.INTEGER);
//        ANGER_TIME_RANGE = Durations.betweenSeconds(20, 39);
//    }
}


//    @Override
//    public void tick() {
////        Box box = this.getBoundingBox().expand(6, 6, 6);
//        CoyoteEntity.this.setTarget(attackingPlayer);
//        super.tick();
//    }
//
//    @Nullable
//    @Override
//    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
//        return null;
//    }
//
//    @Override
//    public int getAngerTime() {
//        return (Integer)this.dataTracker.get(ANGER_TIME);
//    }
//
//    @Override
//    public void setAngerTime(int ticks) {
//        this.dataTracker.set(ANGER_TIME, ticks);
//    }
//
//    @Override
//    public void chooseRandomAngerTime() {
//        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
//    }
//
//    @Nullable
//    @Override
//    public UUID getAngryAt() {
//        return this.targetUuid;
//    }
//
//    @Override
//    public void setAngryAt(@Nullable UUID uuid) {
//        this.targetUuid = uuid;
//    }
//
//    static {
//        ANGER_TIME = DataTracker.registerData(CoyoteEntity.class, TrackedDataHandlerRegistry.INTEGER);
//        ANGER_TIME_RANGE = Durations.betweenSeconds(18, 39);
//    }
//
//}
//
//
//
