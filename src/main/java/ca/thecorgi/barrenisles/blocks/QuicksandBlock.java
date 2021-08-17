package ca.thecorgi.barrenisles.blocks;

import ca.thecorgi.barrenisles.utils.registry.BlockRegistry;
import ca.thecorgi.barrenisles.utils.registry.EntityRegistry;
import ca.thecorgi.barrenisles.utils.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;

import java.util.Random;

public class QuicksandBlock extends PowderSnowBlock {
    public QuicksandBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this) && entity.getType() != EntityRegistry.DUNERAPTOR && entity.getType() != EntityRegistry.TUMBLEWEED) {
            entity.slowMovement(state, new Vec3d(0.24, 0.105D, 0.24));
            if (world.getBlockState(new BlockPos(entity.getX(), entity.getY() + 1F, entity.getZ())).getBlock() == BlockRegistry.QUICKSAND && world.getBlockState(pos).getBlock() == BlockRegistry.QUICKSAND) {
                entity.damage(DamageSource.IN_WALL, 2F);
            }
        }
        if (world.isClient) {
            Random random = world.getRandom();
            boolean bl = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();
            if (bl && random.nextBoolean()) {
                world.addParticle(ParticleTypes.WHITE_ASH, entity.getX(), entity.getY()-1, entity.getZ(),0.1,0.1,0.1);
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
    }

