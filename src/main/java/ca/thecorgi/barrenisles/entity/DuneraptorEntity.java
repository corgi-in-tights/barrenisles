package ca.thecorgi.barrenisles.entity;


import ca.thecorgi.barrenisles.utils.registry.EntityRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.PassiveEntity;
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
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Objects;
import java.util.UUID;


@SuppressWarnings("EntityConstructor")
public class DuneraptorEntity extends HorseBaseEntity implements IAnimatable, Saddleable {
    AnimationFactory factory = new AnimationFactory(this);
    private static final Ingredient BREEDING_INGREDIENT;

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
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, Math.floor(Math.random()*3*(0.27-0.21+0.01)+0.21))
                .add(EntityAttributes.GENERIC_MAX_HEALTH, Math.floor(Math.random()*(28-20+1)+20))
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5D);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && !this.isAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("duneraptor.run", true));
        } else if (this.isAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("duneraptor.attack", true));
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

        if (this.world.isClient) {
        } else {
            if (this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }

                this.heal((float) Objects.requireNonNull(item.getFoodComponent()).getHunger());
                this.emitGameEvent(GameEvent.MOB_INTERACT, this.getCameraBlockPos());
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
    }

    @Override
    public void tickMovement() {
        if (this.world.getBlockState(this.getBlockPos().down()).isOf(Blocks.SAND) ||
                this.world.getBlockState(this.getBlockPos().down(2)).isOf(Blocks.SAND) ||
                this.world.getBlockState(this.getBlockPos().down()).isOf(Blocks.SANDSTONE) ||
                this.world.getBlockState(this.getBlockPos().down()).isOf(Blocks.SANDSTONE_SLAB) ||
                this.world.getBlockState(this.getBlockPos().down()).isOf(Blocks.RED_SAND)) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1,3));
        }

        super.tickMovement();
        if (!this.world.isClient && this.isAlive()) {
            if (this.random.nextInt(900) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
            }
            this.walkToParent();
        }
    }

    public DuneraptorEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        DuneraptorEntity duneraptorEntity = (DuneraptorEntity)EntityRegistry.DUNERAPTOR.create(serverWorld);
        UUID uUID = this.getOwnerUuid();
        if (uUID != null) {
            duneraptorEntity.setOwnerUuid(uUID);
            duneraptorEntity.setTame(true);
        }

        return duneraptorEntity;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_RAVAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource_1) {
        return SoundEvents.ENTITY_RAVAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_RAVAGER_DEATH;
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
        BREEDING_INGREDIENT = Ingredient.ofItems(Items.PORKCHOP, Items.RABBIT, Items.BEEF, Items.RABBIT, Items.MUTTON);
    }
}
