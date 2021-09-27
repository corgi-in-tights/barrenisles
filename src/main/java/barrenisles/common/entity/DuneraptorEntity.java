package barrenisles.common.entity;

import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.Path;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.EnumSet;
import java.util.UUID;

import barrenisles.api.entity.BarrenIslesEntities;

public class DuneraptorEntity extends AbstractHorseEntity implements IAnimatable {
    AnimationFactory factory = new AnimationFactory(this);
    private static final Ingredient BREEDING_INGREDIENT;
    boolean isRunning = false;
    int hunger = 3;

    public DuneraptorEntity(EntityType<? extends AbstractHorseEntity> type, World world) {
        super(type, world);
    }

    public static MutableAttribute createDuneraptorAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25)
                .add(Attributes.MOVEMENT_SPEED, 0.18F)
                .add(Attributes.ATTACK_DAMAGE, 5D);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && this.getTarget() != null ) {
            if (this.level.getBlockState(this.blockPosition().below()).is(Blocks.SAND) ||
                    this.level.getBlockState(this.blockPosition().below(2)).is(Blocks.SAND) ||
                    this.level.getBlockState(this.blockPosition().below(3)).is(Blocks.SAND)) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("duneraptor.run", true));
            } else {
                if (isRunning) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("duneraptor.slowdown", false));
                }
                event.getController().setAnimation(new AnimationBuilder().addAnimation("duneraptor.walk", true));
            }
        } else if ( this.getTarget() != null ) {
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

    @SuppressWarnings("unchecked")
	@Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 0.95D, BREEDING_INGREDIENT, true));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 0.7D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 5.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.3F));
        this.goalSelector.addGoal(9, new AvoidEntityGoal<>(this, MobEntity.class, 4.5F, 0.8D, 1.2D));
        this.goalSelector.addGoal(10, new DuneraptorAttackGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, RabbitEntity.class, 5, true, false, (entity) -> {
            return (isDuneraptorHungry());
        }));
        this.goalSelector.addGoal(11, new DuneraptorRestGoal(this, 1.2D));
    }

    static class DuneraptorAttackGoal extends MeleeAttackGoal {
        public DuneraptorAttackGoal(DuneraptorEntity duneraptor) {
            super(duneraptor, 1.2D, true);
        }

        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return (4.0F + entity.getBbWidth());
        }
    }

    static class DuneraptorRestGoal extends Goal {
        protected final CreatureEntity mob;
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
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            long l = this.mob.level.getGameTime();
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
                    this.mob.moveTo(livingEntity.position());
                    return this.getSquaredMaxAttackDistance(livingEntity) >= this.mob.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                }
            }
        }

        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return (4.0F + entity.getBbWidth());
        }
    }

    @Override
    public boolean isSaddleable() {
        PlayerEntity player = this.level.getNearestPlayer(this, 3);
        if (player != null && player.getUUID().equals(this.getOwnerUUID())) {
            return true;
        } else {
            return this.getHealth() <= 25;
        }
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!this.level.isClientSide) {
            if (this.isFood(itemStack) && this.getHealth() < this.getMaxHealth()) {
                this.eat(player.level, itemStack);
                this.setInLove(player);
                this.usePlayerItem(player, itemStack);
                hunger = 2;
                return ActionResultType.SUCCESS;

            } else if (this.getHealth() == this.getMaxHealth() && !this.isInLove()) {
                this.eat(player.level, itemStack);
                this.setInLove(player);
                hunger = 2;
                return ActionResultType.SUCCESS;
            }
            if (!this.isFood(itemStack)) {
                    if (this.isSaddled() && !this.getPassengers().isEmpty() && !player.canRiderInteract()) {
                        if (!this.level.isClientSide) {
                            this.setTamed(true);
                            this.setOwnerUUID(player.getUUID());
                            this.push(player);
                        }
                    }
                }
            return ActionResultType.SUCCESS;
            }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean canBeControlledByRider() {
        return true;
    }

    @Override
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    public void writeCustomDataToTag(CompoundNBT tag) {
        this.setEating(tag.getBoolean("EatingHaystack"));
        this.setBred(tag.getBoolean("Bred"));
        tag.putInt("Temper", this.getTemper());
        tag.putBoolean("Tame", this.isTamed());
        if (this.getOwnerUUID() != null) {
            tag.putUUID("Owner", this.getOwnerUUID());
        }

        if (!this.inventory.getItem(0).isEmpty()) {
            tag.put("SaddleItem", this.inventory.getItem(0).serializeNBT());
        } else if (this.isSaddled()) {
            tag.put("SaddleItem", new ItemStack(Items.SADDLE).serializeNBT());
        }
    }

    public void readCustomDataFromTag(CompoundNBT tag) {
        this.setEating(tag.getBoolean("EatingHaystack"));
        this.setBred(tag.getBoolean("Bred"));
        this.setTemper(tag.getInt("Temper"));
        this.setTamed(tag.getBoolean("Tame"));
        UUID uuid;
        if (tag.hasUUID("Owner")) {
            uuid = tag.getUUID("Owner");
        } else {
            String string = tag.getString("Owner");
            uuid = PreYggdrasilConverter.convertMobOwnerIfNecessary(this.getServer(), string);
        }

        if (uuid != null) {
            this.setOwnerUUID(uuid);
        }

        if (tag.contains("SaddleItem", 10)) {
            ItemStack itemStack = ItemStack.of(tag.getCompound("SaddleItem"));
            if (itemStack.getItem() == Items.SADDLE) {
                this.inventory.setItem(0, itemStack);
            }
        }
        this.isSaddled();
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
    public void tick() {
    	if (this.level.getBlockState(this.blockPosition().below()).is(Blocks.SAND) ||
                this.level.getBlockState(this.blockPosition().below(2)).is(Blocks.SAND)) {
            this.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 1,4));
        }

        super.tick();
        if (!this.level.isClientSide && this.isAlive()) {
            if (this.random.nextInt(700) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
                hunger--;
                System.out.println(hunger);
            }
        }
    }

    public DuneraptorEntity createChild(ServerWorld serverWorld, Entity passiveEntity) {
        return (DuneraptorEntity)BarrenIslesEntities.duneraptor.get().create(serverWorld);
    }

    static {
        BREEDING_INGREDIENT = Ingredient.of(Items.PORKCHOP, Items.RABBIT, Items.BEEF, Items.RABBIT, Items.MUTTON, Items.CHICKEN);
    }
}