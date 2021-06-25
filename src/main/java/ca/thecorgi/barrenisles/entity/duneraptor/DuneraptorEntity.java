package ca.thecorgi.barrenisles.entity.duneraptor;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.UUID;

public class DuneraptorEntity extends HorseBaseEntity implements Monster, IAnimatable, Saddleable {
    AnimationFactory factory = new AnimationFactory(this);

    public DuneraptorEntity(EntityType<? extends HorseBaseEntity> type, World worldIn) {
        super(type, worldIn);
        this.ignoreCameraFrustum = true;
    }

    private <E extends IAnimatable> PlayState predicate (AnimationEvent<E> event) {
        if (!(lastLimbDistance > -0.23F && lastLimbDistance < 0.23F) && !this.isAttacking()) {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("walk", true)
            );
            return PlayState.CONTINUE;
        }
        if (this.isAttacking()) {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("attack", true)
            );
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(
                new AnimationBuilder().addAnimation("idle", true)
        );
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
        this.goalSelector.add(7, new WanderAroundGoal(this, 0.23D, 20));
        // Entity will look at Player.
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 6));
        // Entity will look around
        this.goalSelector.add(5, new LookAroundGoal(this));
        // Entity will melee-attack
        this.goalSelector.add(4, new MeleeAttackGoal(this, 0.23D, false));
        // Entity will attempt revenge
        this.targetSelector.add(3, new RevengeGoal(this));
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public boolean tryAttack (Entity target) {
        if (target.getUuid().equals(this.getOwnerUuid()) || this.isTame()) {
            Box box = this.getBoundingBox().expand(5, 3, 5);
            List<HostileEntity> hostiles = this.world.getEntitiesByClass(HostileEntity.class, box, EntityPredicates.canBePushedBy(this));
            if (!hostiles.isEmpty()) {
                for (HostileEntity hostile : hostiles) {
                    this.getNavigation().startMovingTo(hostile, 0F);
                    this.getLookControl().lookAt(hostile, 30.0F, 30.0F);
                    this.setTarget(hostile);
                    return super.tryAttack(hostile);
                }
            }

            return false;
        } else {
            return super.tryAttack(target);
        }
    }

    @Override
    public boolean canBeSaddled () {
        PlayerEntity player = this.world.getClosestPlayer(this, 3);
        if (player != null && player.getUuid().equals(this.getOwnerUuid())) {
            return true;
        } else {
            return this.getHealth() <= 24;
        }
    }

    @Override
    public ActionResult interactMob (PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        ActionResult actionResult = itemStack.useOnEntity(player, this, hand);
        if (actionResult.isAccepted()) {
            return actionResult;
        }

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
    public double getJumpStrength () {
        return this.jumpStrength;
    }

    @Override
    public boolean canBeControlledByRider () {
        return true;
    }
    @Override
    public Entity getPrimaryPassenger () {
        return this.getPassengerList().isEmpty() ? null : this.getPassengerList().get(0);
    }

    public void writeCustomDataToTag (NbtCompound tag) {
        tag.putBoolean("EatingHaystack", this.isEatingGrass());
        tag.putBoolean("Bred", this.isBred());
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

    public void readCustomDataFromTag (NbtCompound tag) {
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
    public void kill () {
        super.kill();
        this.heal(2.0F);
    }

}