package ca.thecorgi.barrenisles.entity;


import ca.thecorgi.barrenisles.utils.registry.EntityRegistry;
import ca.thecorgi.barrenisles.utils.registry.SoundRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.EnumSet;
import java.util.Objects;
import java.util.UUID;

import static ca.thecorgi.barrenisles.BarrenIsles.config;


@SuppressWarnings("EntityConstructor")
public class DuneraptorEntity extends HorseBaseEntity implements IAnimatable, Saddleable {
    AnimationFactory factory = new AnimationFactory(this);
    private static final Ingredient BREEDING_INGREDIENT;
    boolean isRunning = false;
    int hunger = 3;

    @Override
    public double getMountedHeightOffset() {
        return 1.15D;
    }

    public DuneraptorEntity(EntityType<? extends HorseBaseEntity> type, World world) {
        super(type, world);
        this.ignoreCameraFrustum = true;
    }

    public static DefaultAttributeContainer.Builder createDuneraptorAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, config.stats.duneraptor_health)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, config.stats.duneraptor_speed)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5D);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && !this.isAttacking()) {
            if (this.world.getBlockState(this.getBlockPos().down()).isOf(Blocks.SAND) ||
                    this.world.getBlockState(this.getBlockPos().down(2)).isOf(Blocks.SAND) ||
                    this.world.getBlockState(this.getBlockPos().down(3)).isOf(Blocks.SAND)) {
//                boolean isRunning = true;
                event.getController().setAnimation(new AnimationBuilder().addAnimation("duneraptor.run", true));
            } else {
                if (isRunning) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("duneraptor.slowdown", false));
                }
                event.getController().setAnimation(new AnimationBuilder().addAnimation("duneraptor.walk", true));
            }
        } else if (this.isAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("duneraptor.duck", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("duneraptor.idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<DuneraptorEntity>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.1D));
        this.goalSelector.add(3, new TemptGoal(this, 0.95D, BREEDING_INGREDIENT, true));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, DuneraptorEntity.class));
        this.goalSelector.add(4, new FollowParentGoal(this, 0.7D));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.85D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 5.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.goalSelector.add(9, new FleeEntityGoal<>(this, HostileEntity.class, 4.5F, 0.8D, 1.2D));
        this.goalSelector.add(10, new DuneraptorAttackGoal(this));
        this.targetSelector.add(1, (new RevengeGoal(this, new Class[0])).setGroupRevenge());
        this.targetSelector.add(2, new FollowTargetGoal(this, RabbitEntity.class, 5, true, false, (entity) -> {
            return (isDuneraptorHungry());
        }));
        this.goalSelector.add(11, new DuneraptorRestGoal(this, 1.2D));
    }

    static class DuneraptorAttackGoal extends MeleeAttackGoal {
        public DuneraptorAttackGoal(DuneraptorEntity duneraptor) {
            super(duneraptor, 1.2D, true);
        }

        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return (4.0F + entity.getWidth());
        }
    }

    static class DuneraptorRestGoal extends Goal {
        protected final PathAwareEntity mob;
        private final double speed;
        private Path path;
        private double targetX;
        private double targetY;
        private double targetZ;
        private int updateCountdownTicks;
        private int cooldown;
        private final int restIntervalTicks = 20;
        private long lastUpdateTime;
        private static final long MAX_RESST_TIME = 20L;

        public DuneraptorRestGoal(DuneraptorEntity mob,Double speed) {
            this.mob = mob;
            this.speed = speed;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        public boolean canStart() {
            long l = this.mob.world.getTime();
            if (l - this.lastUpdateTime < 20L) {
                return false;
            } else {
                this.lastUpdateTime = l;
                LivingEntity livingEntity = this.mob.getTarget();
                if (livingEntity == null) {
                    return false;
                } else if (!livingEntity.isAlive()) {
                    return false;
                } else {
                    this.path = this.mob.getNavigation().findPathTo(livingEntity, 0);
                    if (this.path != null) {
                        return true;
                    } else {
                        return this.getSquaredMaxAttackDistance(livingEntity) >= this.mob.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                    }
                }
            }
        }

        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return (4.0F + entity.getWidth());
        }
    }

    @Override
    public boolean canBeSaddled() {
        PlayerEntity player = this.world.getClosestPlayer(this, 3);
        if (player != null && player.getUuid().equals(this.getOwnerUuid())) {
            return true;
        } else {
            return this.getHealth() <= 25;
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        ActionResult actionResult = itemStack.useOnEntity(player, this, hand);

        if (!this.world.isClient) {
            if (this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                this.eat(player, hand, itemStack);
                this.lovePlayer(player);
                this.heal((float) item.getFoodComponent().getHunger() / 2);
                this.emitGameEvent(GameEvent.MOB_INTERACT, this.getCameraBlockPos());
                hunger = 2;
                return ActionResult.SUCCESS;

            } else if (this.isBreedingItem(itemStack) && this.getHealth() == this.getMaxHealth() && !this.isInLove()) {
                this.eat(player, hand, itemStack);
                this.lovePlayer(player);
                this.emitGameEvent(GameEvent.MOB_INTERACT, this.getCameraBlockPos());
                hunger = 2;
                return ActionResult.SUCCESS;
            }
            if (!this.isBreedingItem(itemStack)) {
                    if (this.isSaddled() && !this.hasPassengers() && !player.shouldCancelInteraction()) {
                        if (!this.world.isClient) {
                            player.setYaw(this.getYaw());
                            player.setPitch(this.getPitch());
                            this.setTame(true);
                            this.setOwnerUuid(player.getUuid());
                            this.putPlayerOnBack(player);
                        }
                    }
                }
            return ActionResult.SUCCESS;
            }
        return super.interactMob(player, hand);
        }

    @Override
    public double getJumpStrength() {
        return this.jumpStrength;
    }

    @Override
    public boolean canBeControlledByRider() {
        return true;
    }

    @Override
    public Entity getPrimaryPassenger() {
        return this.getPassengerList().isEmpty() ? null : this.getPassengerList().get(0);
    }

    public void writeCustomDataToTag(NbtCompound tag) {
        this.setEatingGrass(tag.getBoolean("EatingHaystack"));
        this.setBred(tag.getBoolean("Bred"));
        tag.putInt("Temper", this.getTemper());
        tag.putBoolean("Tame", this.isTame());
        if (this.getOwnerUuid() != null) {
            tag.putUuid("Owner", this.getOwnerUuid());
        }

        if (!this.items.getStack(0).isEmpty()) {
            tag.put("SaddleItem", this.items.getStack(0).writeNbt(new NbtCompound()));
        } else if (this.isSaddled()) {
            tag.put("SaddleItem", new ItemStack(Items.SADDLE).writeNbt(new NbtCompound()));
        }
    }

    public void readCustomDataFromTag(NbtCompound tag) {
        this.setEatingGrass(tag.getBoolean("EatingHaystack"));
        this.setBred(tag.getBoolean("Bred"));
        this.setTemper(tag.getInt("Temper"));
        this.setTame(tag.getBoolean("Tame"));
        UUID uUID2;
        if (tag.containsUuid("Owner")) {
            uUID2 = tag.getUuid("Owner");
        } else {
            String string = tag.getString("Owner");
            uUID2 = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
        }

        if (uUID2 != null) {
            this.setOwnerUuid(uUID2);
        }

        if (tag.contains("SaddleItem", 10)) {
            ItemStack itemStack = ItemStack.fromNbt(tag.getCompound("SaddleItem"));
            if (itemStack.getItem() == Items.SADDLE) {
                this.items.setStack(0, itemStack);
            }
        }
        this.updateSaddle();
    }

    @Override
    public void kill() {
        super.kill();
        this.heal(3F);
        hunger = 3;
    }

    public boolean isDuneraptorHungry() {
        if (hunger == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void tickMovement() {
        if (this.world.getBlockState(this.getBlockPos().down()).isOf(Blocks.SAND) ||
                this.world.getBlockState(this.getBlockPos().down(2)).isOf(Blocks.SAND)) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1,4));
        }

        super.tickMovement();
        if (!this.world.isClient && this.isAlive()) {
            if (this.random.nextInt(700) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
                hunger--;
                System.out.println(hunger);
            }
            this.walkToParent();
        }
    }

    public DuneraptorEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        return (DuneraptorEntity)EntityRegistry.DUNERAPTOR.create(serverWorld);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.ENTITY_DUNERAPTOR_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource_1) {
        return SoundRegistry.ENTITY_DUNERAPTOR_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.ENTITY_DUNERAPTOR_ATTACK;
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        Item item = stack.getItem();
        return item.isFood() && item.getFoodComponent().isMeat();
    }

    static {
        BREEDING_INGREDIENT = Ingredient.ofItems(Items.PORKCHOP, Items.RABBIT, Items.BEEF, Items.RABBIT, Items.MUTTON, Items.CHICKEN);
    }
}
