package barrenisles.common.entity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CoyoteEntity extends SpiderEntity implements IAnimatable {
	private static final DataParameter<Integer> DATA_TYPE_ID = EntityDataManager.defineId(CoyoteEntity.class, DataSerializers.INT);
	private static final DataParameter<Byte> DATA_FLAGS_ID = EntityDataManager.defineId(CoyoteEntity.class, DataSerializers.BYTE);
	private static final DataParameter<Optional<UUID>> DATA_TRUSTED_ID_0 = EntityDataManager.defineId(CoyoteEntity.class, DataSerializers.OPTIONAL_UUID);
	private static final DataParameter<Optional<UUID>> DATA_TRUSTED_ID_1 = EntityDataManager.defineId(CoyoteEntity.class, DataSerializers.OPTIONAL_UUID);
	
    AnimationFactory factory = new AnimationFactory(this);
	public float crouchAmount;
	public float crouchAmountO;

    public CoyoteEntity(EntityType<? extends SpiderEntity> type, World world) {
        super(type, world);
    }
    
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TRUSTED_ID_0, Optional.empty());
        this.entityData.define(DATA_TRUSTED_ID_1, Optional.empty());
        this.entityData.define(DATA_TYPE_ID, 0);
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
     }

    public static AttributeModifierMap createCoyoteAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25)
                .add(Attributes.MOVEMENT_SPEED, 0.33F)
                .add(Attributes.ATTACK_DAMAGE, 3.5F)
                .build();
    }
    
    @Override
    protected void registerGoals() {
    	this.goalSelector.addGoal(1, new SwimGoal(this));
    	this.targetSelector.addGoal(2, new RevengeGoal(LivingEntity.class, true, null));
    	this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class,true));
    	this.goalSelector.addGoal(4, new PounceGoal());
    	this.goalSelector.addGoal(5, new NightAttackGoal(this));
      	this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 8.0F));
      	this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
  }

    
    class RevengeGoal extends NearestAttackableTargetGoal<LivingEntity> {
        @Nullable
        private LivingEntity trustedLastHurtBy;
        private LivingEntity trustedLastHurt;
        private int timestamp;

        public RevengeGoal(Class<LivingEntity> targetEntity, boolean mustReach, @Nullable Predicate<LivingEntity> predicate) {
           super(CoyoteEntity.this, targetEntity, 10, true, mustReach, predicate);
        }

		public boolean canUse() {
           if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
              return false;
           } else {
              for(UUID uuid : CoyoteEntity.this.getTrustedUUIDs()) {
                 if (uuid != null && CoyoteEntity.this.level instanceof ServerWorld) {
                    Entity entity = ((ServerWorld)CoyoteEntity.this.level).getEntity(uuid);
                    if (entity instanceof LivingEntity) {
                       LivingEntity livingentity = (LivingEntity)entity;
                       this.trustedLastHurt = livingentity;
                       this.trustedLastHurtBy = livingentity.getLastHurtByMob();
                       int i = livingentity.getLastHurtByMobTimestamp();
                       return i != this.timestamp && this.canAttack(this.trustedLastHurtBy, this.targetConditions);
                    }
                 }
              }

              return false;
           }
        }

        public void start() {
           this.setTarget(this.trustedLastHurtBy);
           this.target = this.trustedLastHurtBy;
           if (this.trustedLastHurt != null) {
              this.timestamp = this.trustedLastHurt.getLastHurtByMobTimestamp();
           }

           CoyoteEntity.this.playSound(SoundEvents.FOX_AGGRO, 1.0F, 1.0F);
           CoyoteEntity.this.setDefending(true);
           CoyoteEntity.this.wakeUp();
           super.start();
        }
     }
    
    private void setDefending(boolean isDefend) {
        this.setFlag(128, isDefend);
     }
    
    private List<UUID> getTrustedUUIDs() {
        List<UUID> list = Lists.newArrayList();
        list.add(this.entityData.get(DATA_TRUSTED_ID_0).orElse(null));
        list.add(this.entityData.get(DATA_TRUSTED_ID_1).orElse(null));
        return list;
     }
    
    private void setFlag(int p_213505_1_, boolean p_213505_2_) {
        if (p_213505_2_) {
        	this.entityData.set(DATA_FLAGS_ID, (byte)(this.entityData.get(DATA_FLAGS_ID) | p_213505_1_));
        } 
        else {
        	this.entityData.set(DATA_FLAGS_ID, (byte)(this.entityData.get(DATA_FLAGS_ID) & ~p_213505_1_));
        }
    }
    
    private void wakeUp() {
        this.stopSleeping();
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

    public void tick() {
        super.tick();
            }

    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getEntity();
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof ProjectileEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.hurt(source, amount);
        }
    }

	private static class NightAttackGoal extends MeleeAttackGoal {
        public NightAttackGoal(CoyoteEntity coyote) {
            super(coyote, 1.0D, true);
        }

        public boolean canUse() {
            return super.canUse() && this.mob.getPassengers().isEmpty();
        }

        private static boolean isNightHungry(World world, BlockPos pos) {
            int i = world.getLightEmission(pos) - world.getSkyDarken();
            float f = world.getSunAngle(1.0F);
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

        public boolean canContinueToUse() {
            if (!isNightHungry(this.mob.level, this.mob.blockPosition())) {
                this.mob.setTarget((LivingEntity)null);
                return false;
            } else {
                return super.canContinueToUse();
            }
        }
    }

    public boolean tryAttack(Entity target) {
        boolean bl = target.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (bl) {
            this.doEnchantDamageEffects(this, target);
        }

        return bl;
    }

    public void handleStatus(byte status) {
    	super.handleEntityEvent(status);
    }

    public int getLimitPerChunk() {
        return 8;
    }
    public Vector3d getLeashOffset() {
        return new Vector3d(0.0D, (double)(0.6F * this.getStandingEyeHeight(this.getPose(), this.getDimensions(this.getPose()))), (double)(this.getBbWidth() * 0.4F));
    }
    
    public class PounceGoal extends net.minecraft.entity.ai.goal.JumpGoal {
        public boolean canUse() {
           if (!CoyoteEntity.this.isFullyCrouched()) {
              return false;
           } else {
              LivingEntity livingentity = CoyoteEntity.this.getTarget();
              if (livingentity != null && livingentity.isAlive()) {
                 if (livingentity.getMotionDirection() != livingentity.getDirection()) {
                    return false;
                 } else {
                    boolean flag = CoyoteEntity.isPathClear(CoyoteEntity.this, livingentity);
                    if (!flag) {
                       CoyoteEntity.this.getNavigation().createPath(livingentity, 0);
                       CoyoteEntity.this.setIsCrouching(false);
                       CoyoteEntity.this.setIsInterested(false);
                    }

                    return flag;
                 }
              } else {
                 return false;
              }
           }
        }

        public boolean canContinueToUse() {
           LivingEntity livingentity = CoyoteEntity.this.getTarget();
           if (livingentity != null && livingentity.isAlive()) {
              double d0 = CoyoteEntity.this.getDeltaMovement().y;
              return (!(d0 * d0 < (double)0.05F) || !(Math.abs(CoyoteEntity.this.xRot) < 15.0F) || !CoyoteEntity.this.onGround) && !CoyoteEntity.this.isFaceplanted();
           } else {
              return false;
           }
        }

        public boolean isInterruptable() {
           return false;
        }

        public void start() {
           CoyoteEntity.this.setJumping(true);
           CoyoteEntity.this.setIsPouncing(true);
           CoyoteEntity.this.setIsInterested(false);
           LivingEntity livingentity = CoyoteEntity.this.getTarget();
           CoyoteEntity.this.getLookControl().setLookAt(livingentity, 60.0F, 30.0F);
           Vector3d vector3d = (new Vector3d(livingentity.getX() - CoyoteEntity.this.getX(), livingentity.getY() - CoyoteEntity.this.getY(), livingentity.getZ() - CoyoteEntity.this.getZ())).normalize();
           CoyoteEntity.this.setDeltaMovement(CoyoteEntity.this.getDeltaMovement().add(vector3d.x * 0.8D, 0.9D, vector3d.z * 0.8D));
           CoyoteEntity.this.getNavigation().stop();
        }

        public void stop() {
           CoyoteEntity.this.setIsCrouching(false);
           CoyoteEntity.this.crouchAmount = 0.0F;
           CoyoteEntity.this.crouchAmountO = 0.0F;
           CoyoteEntity.this.setIsInterested(false);
           CoyoteEntity.this.setIsPouncing(false);
        }

        public void tick() {
           LivingEntity livingentity = CoyoteEntity.this.getTarget();
           if (livingentity != null) {
              CoyoteEntity.this.getLookControl().setLookAt(livingentity, 60.0F, 30.0F);
           }

           if (!CoyoteEntity.this.isFaceplanted()) {
              Vector3d vector3d = CoyoteEntity.this.getDeltaMovement();
              if (vector3d.y * vector3d.y < (double)0.03F && CoyoteEntity.this.xRot != 0.0F) {
                 CoyoteEntity.this.xRot = MathHelper.rotlerp(CoyoteEntity.this.xRot, 0.0F, 0.2F);
              } else {
                 double d0 = Math.sqrt(Entity.getHorizontalDistanceSqr(vector3d));
                 double d1 = Math.signum(-vector3d.y) * Math.acos(d0 / vector3d.length()) * (double)(180F / (float)Math.PI);
                 CoyoteEntity.this.xRot = (float)d1;
              }
           }

           if (livingentity != null && CoyoteEntity.this.distanceTo(livingentity) <= 2.0F) {
              CoyoteEntity.this.doHurtTarget(livingentity);
           } else if (CoyoteEntity.this.xRot > 0.0F && CoyoteEntity.this.onGround && (float)CoyoteEntity.this.getDeltaMovement().y != 0.0F && CoyoteEntity.this.level.getBlockState(CoyoteEntity.this.blockPosition()).is(Blocks.SNOW)) {
              CoyoteEntity.this.xRot = 60.0F;
              CoyoteEntity.this.setTarget((LivingEntity)null);
              CoyoteEntity.this.setFaceplanted(true);
           }

        }
     }

    public static boolean isPathClear(CoyoteEntity p_213481_0_, LivingEntity p_213481_1_) {
        double d0 = p_213481_1_.getZ() - p_213481_0_.getZ();
        double d1 = p_213481_1_.getX() - p_213481_0_.getX();
        double d2 = d0 / d1;

        for(int j = 0; j < 6; ++j) {
           double d3 = d2 == 0.0D ? 0.0D : d0 * (double)((float)j / 6.0F);
           double d4 = d2 == 0.0D ? d1 * (double)((float)j / 6.0F) : d3 / d2;

           for(int k = 1; k < 4; ++k) {
              if (!p_213481_0_.level.getBlockState(new BlockPos(p_213481_0_.getX() + d4, p_213481_0_.getY() + (double)k, p_213481_0_.getZ() + d3)).getMaterial().isReplaceable()) {
                 return false;
              }
           }
        }

        return true;
     }
    
    public void setIsInterested(boolean p_213502_1_) {
        this.setFlag(8, p_213502_1_);
    }
    
    public void setIsCrouching(boolean p_213451_1_) {
        this.setFlag(4, p_213451_1_);
    }
    
    public boolean isFaceplanted() {
        return this.getFlag(64);
    }
    
    private void setFaceplanted(boolean p_213492_1_) {
        this.setFlag(64, p_213492_1_);
    }
    
    private boolean getFlag(int p_213507_1_) {
        return (this.entityData.get(DATA_FLAGS_ID) & p_213507_1_) != 0;
    }
    
    public boolean isPouncing() {
        return this.getFlag(16);
    }

    public void setIsPouncing(boolean p_213461_1_) {
        this.setFlag(16, p_213461_1_);
    }
    
    public boolean isFullyCrouched() {
        return this.crouchAmount == 3.0F;
    }
}
