package de.xyndra.barrenisles.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.xyndra.barrenisles.setup.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.DarkOakFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;
import java.util.Set;

public class PalmFoliagePlacer extends FoliagePlacer {


    public PalmFoliagePlacer(FeatureSpread p_i241999_1_, FeatureSpread p_i241999_2_) {
        super(p_i241999_1_, p_i241999_2_);

    }

    @Override
    protected FoliagePlacerType<?> type() {
        return  new FoliagePlacerType<FoliagePlacer>(PalmFoliagePlacer.CODEC) {
            public <P extends FoliagePlacer> FoliagePlacerType<P> register(String p_236773_0_, Codec<P> p_236773_1_) {
                return Registry.register(Registry.FOLIAGE_PLACER_TYPES, p_236773_0_, new FoliagePlacerType<>(p_236773_1_));
            }
        }.register( "palm_foliage_placer", PalmFoliagePlacer.CODEC);
    }

    @Override
    protected void createFoliage(IWorldGenerationReader world, Random random, BaseTreeFeatureConfig p_230372_3_, int p_230372_4_, Foliage p_230372_5_, int p_230372_6_, int p_230372_7_, Set<BlockPos> posSet, int p_230372_9_, MutableBoundingBox p_230372_10_) {
       /*for(int i = p_230372_9_; i >= p_230372_9_ - p_230372_6_; --i) {
            int j = Math.max(p_230372_7_ + p_230372_5_.radiusOffset() - 1 - i / 2, 0);
            this.placeLeavesRow(p_230372_1_, p_230372_2_, p_230372_3_, p_230372_5_.foliagePos(), j, p_230372_8_, i, p_230372_5_.doubleTrunk(), p_230372_10_);
        }*/
        BlockPos pos = p_230372_5_.foliagePos();
        pos = pos.immutable();

        int i = pos.getX(), j = pos.getY(), k = pos.getZ();

        placeLeaf(world, i, j, k);
        placeLeaf(world, i+3, j, k);
        placeLeaf(world, i+1, j, k);
        placeLeaf(world, i+1, j+1, k);
        placeLeaf(world, i+2, j+1, k);
        placeLeaf(world, i+3, j+1, k);
        placeLeaf(world, i+1, j-1, k);
        placeLeaf(world, i+2, j-1, k);
        placeLeaf(world, i+2, j-2, k);
        placeLeaf(world, i-3, j, k);
        placeLeaf(world, i-1, j, k);
        placeLeaf(world, i-1, j+1, k);
        placeLeaf(world, i-2, j+1, k);
        placeLeaf(world, i-3, j+1, k);
        placeLeaf(world, i-1, j-1, k);
        placeLeaf(world, i-2, j-1, k);
        placeLeaf(world, i-2, j-2, k);
        placeLeaf(world, i, j, k+3);
        placeLeaf(world, i, j, k+1);
        placeLeaf(world, i, j+1, k+1);
        placeLeaf(world, i, j+1, k+2);
        placeLeaf(world, i, j+1, k+3);
        placeLeaf(world, i, j-1, k+1);
        placeLeaf(world, i, j-1, k+2);
        placeLeaf(world, i, j-2, k+2);
        placeLeaf(world, i, j, k-3);
        placeLeaf(world, i, j, k-1);
        placeLeaf(world, i, j+1, k-1);
        placeLeaf(world, i, j+1, k-2);
        placeLeaf(world, i, j+1, k-3);
        placeLeaf(world, i, j-1, k-1);
        placeLeaf(world, i, j-1, k-2);
        placeLeaf(world, i, j-2, k-2);


        //spawnCoconuts(world, new BlockPos(i, j , k), random, 2, getLeaf());
    }

    protected final BlockState getLeaf() {
        return ModBlocks.PALMLEAVES.defaultBlockState();
    }

    protected final BlockState getLog() {
        return ModBlocks.PALMLOG.defaultBlockState();
    }

    protected void placeLeaf(final IWorldGenerationReader world, int x, int y, int z) {
        this.placeLeaf(world, new BlockPos(x, y, z));
    }

    protected void placeLeaf(final IWorldGenerationReader world, BlockPos pos) {
        // From FoliagePlacer
        if (TreeFeature.isAir(world, pos)) {
            setBlock(world, pos, getLeaf());
        }
    }

    protected void placeLog(final IWorldGenerationReader world, int x, int y, int z) {
        this.placeLog(world, new BlockPos(x, y, z));
    }

    protected void placeLog(final IWorldGenerationReader world, BlockPos pos) {
        if (TreeFeature.validTreePos(world, pos)) {
            setBlock(world, pos, getLog());
        }
    }

    private static final Direction[] DIRECTIONS = ArrayUtils.removeElement(Direction.values(), Direction.UP);
    public static void spawnCoconuts(IWorldGenerationReader world, BlockPos pos, Random random, int chance, BlockState leaf) {
        final BlockState coconut = ModBlocks.COCONUT.defaultBlockState();
        for (Direction d : DIRECTIONS) {
            BlockPos pos2 = pos.relative(d);
            if (random.nextInt(chance) == 0 && TreeFeature.isAirOrLeaves(world, pos2)) {
                world.setBlock(pos2, coconut.setValue(BlockStateProperties.FACING, d.getOpposite()).setValue(BlockStateProperties.AGE_2, 1), Constants.BlockFlags.DEFAULT);
            }
        }
    }

    protected void setBlock(IWorldWriter p_230367_1_, BlockPos p_230367_2_, BlockState p_230367_3_) {
        p_230367_1_.setBlock(p_230367_2_, p_230367_3_, 3);
    }

    @Override
    public int foliageHeight(Random p_230374_1_, int p_230374_2_, BaseTreeFeatureConfig p_230374_3_) {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(Random p_230373_1_, int p_230373_2_, int p_230373_3_, int p_230373_4_, int p_230373_5_, boolean p_230373_6_) {
        return false;
    }
}
