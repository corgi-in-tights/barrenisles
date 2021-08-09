package de.xyndra.barrenisles.blocks;


import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.PlantType;


public class Thornweed extends DeadBushBlock {
    public Thornweed(AbstractBlock.Properties settings) {
        super(settings);
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.DESERT;
    }

    @Override
    public boolean mayPlaceOn(BlockState p_200014_1_, IBlockReader p_200014_2_, BlockPos p_200014_3_) {
        return p_200014_1_.is(Blocks.SAND) || p_200014_1_.is(Blocks.RED_SAND);
    }

    @Override
    public void entityInside(BlockState p_196262_1_, World p_196262_2_, BlockPos p_196262_3_, Entity p_196262_4_) {
        if (p_196262_4_ instanceof LivingEntity && p_196262_4_.getType() != EntityType.FOX && p_196262_4_.getType() != EntityType.BEE) {
            p_196262_4_.makeStuckInBlock(p_196262_1_, new Vector3d((double)0.8F, 0.75D, (double)0.8F));
            if (!p_196262_2_.isClientSide && (p_196262_4_.xOld != p_196262_4_.getX() || p_196262_4_.zOld != p_196262_4_.getZ())) {
                double d0 = Math.abs(p_196262_4_.getX() - p_196262_4_.xOld);
                double d1 = Math.abs(p_196262_4_.getZ() - p_196262_4_.zOld);
                if (d0 >= (double)0.003F || d1 >= (double)0.003F) {
                    p_196262_4_.hurt(DamageSource.SWEET_BERRY_BUSH, 1.0F);
                }
            }

        }
    }
}
