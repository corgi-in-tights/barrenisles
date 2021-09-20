package ca.thecorgi.barrenisles.blocks;

import ca.thecorgi.barrenisles.utils.registry.BlockRegistry;
import ca.thecorgi.barrenisles.utils.registry.EntityRegistry;
import ca.thecorgi.barrenisles.utils.registry.ItemRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;

import java.util.Optional;
import java.util.Random;

public class QuicksandBlock extends PowderSnowBlock {
    private static final VoxelShape field_31220 = VoxelShapes.cuboid(0.0D, 0.0D, 0.0D, 1.0D, 0.8999999761581421D, 1.0D);

    public QuicksandBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.isOf(this) ? true : super.isSideInvisible(state, stateFrom, direction);
    }


    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this) && entity.getType() != EntityRegistry.DUNERAPTOR && entity.getType() != EntityRegistry.TUMBLEWEED) {
            entity.slowMovement(state, new Vec3d(0.24, 0.105D, 0.24));
            if (world.getBlockState(new BlockPos(entity.getX(), entity.getY() + 1.6F, entity.getZ())).getBlock() == BlockRegistry.QUICKSAND && world.getBlockState(pos).getBlock() == BlockRegistry.QUICKSAND) {
                entity.damage(DamageSource.IN_WALL, 0.5F);
            }
        }

        if (world.isClient) {
            Random random = world.getRandom();
            boolean bl = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();
            if (bl && random.nextBoolean()) {
                world.addParticle(ParticleTypes.WHITE_ASH, entity.getX(), entity.getY(), entity.getZ(),0.1,0.1,0.1);
            }

            if (!world.isClient) {
                entity.setOnFire(false);
            }
        }
    }

        @Override
        public ItemStack tryDrainFluid (WorldAccess world, BlockPos pos, BlockState state){
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            if (!world.isClient()) {
                world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
            }

            return new ItemStack(ItemRegistry.QUICKSAND_BUCKET);
        }

        @Override
        public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
            if (context instanceof EntityShapeContext) {
                EntityShapeContext entityShapeContext = (EntityShapeContext)context;
                Optional<Entity> optional = entityShapeContext.getEntity();
                if (optional.isPresent()) {
                    Entity entity = (Entity)optional.get();
                    if (entity.fallDistance > 2.5F) {
                        return field_31220;
                    }

                    boolean bl = entity instanceof FallingBlockEntity;
                    if (bl || canWalkOnPowderSnow(entity) && context.isAbove(VoxelShapes.fullCube(), pos, false) && !context.isDescending()) {
                        return super.getCollisionShape(state, world, pos, context);
                    }
                }
            }

            return VoxelShapes.empty();
        }

        @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(SoundEvents.ITEM_BUCKET_FILL_POWDER_SNOW);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return true;
    }
    }

