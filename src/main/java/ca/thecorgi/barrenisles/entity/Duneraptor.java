package ca.thecorgi.barrenisles.entity;


import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;


public class Duneraptor extends HorseBaseEntity implements IAnimatable, Saddleable {
    AnimationFactory factory = new AnimationFactory(this);

    public Duneraptor(EntityType<? extends HorseBaseEntity> type, World worldIn) {
        super(type, worldIn);
        this.ignoreCameraFrustum = true;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && !this.isAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("duneraptor.run", true));
        } else if (this.isAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("duneraptor.attack", false));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("duneraptor.idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<Duneraptor>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void initGoals() {
        // Entity will walk around.
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1D, 100));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 6.5F));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.goalSelector.add(4, new SwimGoal(this));
        this.targetSelector.add(6, new FollowTargetGoal<>(this, PlayerEntity.class, true));
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
        ActionResult actionResult = itemStack.useOnEntity(player, this, hand);

        if (this.isSaddled() && !this.hasPassengers() && !player.shouldCancelInteraction()) {
            if (!this.world.isClient) {
                player.setYaw(this.getYaw());
                player.setPitch(this.getPitch());
                this.setTame(true);
                this.setOwnerUuid(player.getUuid());
                this.putPlayerOnBack(player);
            }
        }
        return ActionResult.success(this.world.isClient);
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
        if (!this.world.getBlockState(this.getBlockPos().down()).isOf(Blocks.SAND)) {
            this.setMovementSpeed(0.2F);
        }

        super.tickMovement();
        if (!this.world.isClient && this.isAlive()) {
            if (this.random.nextInt(900) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
            }
            this.walkToParent();
        }
    }

}
