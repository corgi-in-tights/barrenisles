package de.xyndra.barrenisles.blocks;

import net.minecraft.block.*;
import net.minecraft.pathfinding.PathType;
import net.minecraft.potion.Effect;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.PlantType;

public class DesertFlowerBlock extends BushBlock {

    protected static VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    private final Effect suspiciousStewEffect;
    private final int effectDuration;

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        Vector3d lvt_5_1_ = p_220053_1_.getOffset(p_220053_2_, p_220053_3_);
        return SHAPE.move(lvt_5_1_.x, lvt_5_1_.y, lvt_5_1_.z);
    }

    public AbstractBlock.OffsetType getOffsetType() {
        return AbstractBlock.OffsetType.XZ;
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.DESERT;
    }

    public Effect getSuspiciousStewEffect() {
        return this.suspiciousStewEffect;
    }

    public int getEffectDuration() {
        return this.effectDuration;
    }


    public DesertFlowerBlock(Effect suspiciousStewEffect, int effectDuration, VoxelShape hitbox, AbstractBlock.Properties settings) {
        super(settings.instabreak().noCollission());
        SHAPE = hitbox;
        this.suspiciousStewEffect = suspiciousStewEffect;
        if (suspiciousStewEffect.isInstantenous()) {
            this.effectDuration = effectDuration;
        } else {
            this.effectDuration = effectDuration * 20;
        }
    }

    @Override
    public boolean mayPlaceOn(BlockState p_200014_1_, IBlockReader p_200014_2_, BlockPos p_200014_3_) {
        return p_200014_1_.is(Blocks.SAND) || p_200014_1_.is(Blocks.RED_SAND);
    }
}
