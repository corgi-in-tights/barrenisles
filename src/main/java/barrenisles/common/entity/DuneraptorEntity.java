package barrenisles.common.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
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

import javax.annotation.Nullable;

import barrenisles.api.BarrenIslesEntities;

public class DuneraptorEntity extends AbstractHorseEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);
    private boolean saddled = false;
    private boolean isRunning = false;
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.of(Items.PORKCHOP, Items.RABBIT, Items.BEEF, Items.RABBIT, Items.MUTTON, Items.CHICKEN);

    int hunger = 3;

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && !this.swinging) {
            if (this.level.getBlockState(this.blockPosition().below()).is(BlockTags.SAND) ||
                    this.level.getBlockState(this.blockPosition().below(2)).is(BlockTags.SAND) ||
                    this.level.getBlockState(this.blockPosition().below(3)).is(BlockTags.SAND) ||
                    this.level.getBlockState(this.blockPosition().below(4)).is(BlockTags.SAND)) {
                isRunning = true;
                event.getController().setAnimation(new AnimationBuilder().addAnimation("duneraptor.run", true));
            } else {
                if (isRunning) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("duneraptor.slowdown", false));
                }
                event.getController().setAnimation(new AnimationBuilder().addAnimation("duneraptor.walk", true));
            }
        } else if (this.swinging) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("duneraptor.attack", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("duneraptor.idle", true));
        }
        return PlayState.CONTINUE;
    }

    public DuneraptorEntity(EntityType<? extends AbstractHorseEntity> type, World worldIn) {
        super(type, worldIn);
        this.noCulling = true;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 0.95D, BREEDING_INGREDIENT, true));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 0.7D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 5.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.3F));
        this.goalSelector.addGoal(9, new AvoidEntityGoal<>(this, MobEntity.class, 4.5F, 0.8D, 1.2D));
        this.goalSelector.addGoal(10, new MeleeAttackGoal(this, 1.2D, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, RabbitEntity.class, 5, true, false, (entity) -> (isDuneraptorHungry())));
        this.goalSelector.addGoal(11, new DuneraptorRestGoal(this, 1.2D));
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
            }
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

    public boolean canEquipPlayer(PlayerEntity player) {
        return player != null && player.getUUID().equals(this.getOwnerUUID());
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
            if (this.isSaddled() && !itemStack.isEmpty()) {
                ActionResultType actionresulttype = itemStack.interactLivingEntity(player, this, hand);
                if (actionresulttype.consumesAction()) {
                    return actionresulttype;
                }
            }
            if (this.canEquipPlayer(player) && !this.isBaby()) {
                this.doPlayerRide(player);
                return ActionResultType.sidedSuccess(this.level.isClientSide);
            }

            return ActionResultType.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public DuneraptorEntity getBreedOffspring(ServerWorld level, AgeableEntity entity) {
        return BarrenIslesEntities.duneraptor.get().create(level);
    }
    
    @Override
    public void kill() {
        super.kill();
        this.heal(3F);
        hunger = 3;
    }

    public boolean isDuneraptorHungry() {
        return hunger == 0;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Override
    public boolean canBeControlledByRider() {
        return true;
    }
    

    @Override
    protected float getBlockSpeedFactor() {
        return isRunning ? 0.25F : 0.4F;
    }

    public static AttributeModifierMap createDuneraptorAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.JUMP_STRENGTH, 1)
                .build();
    }

    public AttributeModifierManager getAttributes(){
        return new AttributeModifierManager(DuneraptorEntity.createDuneraptorAttributes());
    }

	@Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("eating_haystack", this.isEating());
        tag.putBoolean("bred", this.isBred());
        tag.putInt("temper", this.getTemper());
        tag.putBoolean("tame", this.isTamed());
        if (this.getOwnerUUID() != null) {
            tag.putUUID("owner", this.getOwnerUUID());
        }

        if (!this.inventory.getItem(0).isEmpty()) {
            tag.put("saddle_item", this.inventory.getItem(0).serializeNBT());
        } else if (this.isSaddled()) {
            tag.put("saddle_item", new ItemStack(Items.SADDLE).serializeNBT());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);
        this.setEating(tag.getBoolean("eating_haystack"));
        this.setBred(tag.getBoolean("bred"));
        this.setTemper(tag.getInt("temper"));
        this.setTamed(tag.getBoolean("tame"));
        UUID uuid;
        if (tag.hasUUID("owner")) {
            uuid = tag.getUUID("owner");
        } else {
            String string = tag.getString("owner");
            uuid = PreYggdrasilConverter.convertMobOwnerIfNecessary(this.getServer(), string);
        }

        if (uuid != null) {
            this.setOwnerUUID(uuid);
        }

        if (tag.contains("saddle_item", 10)) {
            ItemStack itemStack = ItemStack.of(tag.getCompound("saddle_item"));
            if (itemStack.getItem() == Items.SADDLE) {
                this.inventory.setItem(0, itemStack);
            }
        }
        this.isSaddled();
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
    
    static class DuneraptorRestGoal extends Goal {
        protected final CreatureEntity mob;
        private long lastUpdateTime;

        public DuneraptorRestGoal(DuneraptorEntity mob, double speed) {
            this.mob = mob;
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
}