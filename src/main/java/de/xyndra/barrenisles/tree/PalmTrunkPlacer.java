package de.xyndra.barrenisles.tree;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import java.util.Set;
import de.xyndra.barrenisles.setup.ModBlocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.ForkyTrunkPlacer;

public class PalmTrunkPlacer extends ForkyTrunkPlacer {

    public PalmTrunkPlacer(int p_i232056_1_, int p_i232056_2_, int p_i232056_3_) {
        super(p_i232056_1_, p_i232056_2_, p_i232056_3_);
    }

    public List<FoliagePlacer.Foliage> placeTrunk(IWorldGenerationReader p_230382_1_, Random p_230382_2_, int p_230382_3_, BlockPos p_230382_4_, Set<BlockPos> p_230382_5_, MutableBoundingBox p_230382_6_, BaseTreeFeatureConfig p_230382_7_) {
        setDirtAt(p_230382_1_, p_230382_4_.below());
        List<FoliagePlacer.Foliage> list = Lists.newArrayList();
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(p_230382_2_);
        int i = p_230382_3_ - p_230382_2_.nextInt(4) - 1;
        int j = 3 - p_230382_2_.nextInt(3);
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        int k = p_230382_4_.getX();
        int l = p_230382_4_.getZ();
        int i1 = 0;

        for(int j1 = 0; j1 < p_230382_3_; ++j1) {
            int k1 = p_230382_4_.getY() + j1;
            if (j1 >= i && j > 0) {
                k += direction.getStepX();
                l += direction.getStepZ();
                --j;
            }

            if (placeLog(p_230382_1_, p_230382_2_, blockpos$mutable.set(k, k1, l), p_230382_5_, p_230382_6_, p_230382_7_)) {
                i1 = k1 + 1;
            }
        }

        list.add(new FoliagePlacer.Foliage(new BlockPos(k, i1, l), 1, false));

        return list;
    }

    protected static boolean placeCoconut(IWorldGenerationReader p_236911_0_, BlockPos p_236911_2_, Set<BlockPos> p_236911_3_, MutableBoundingBox p_236911_4_, Direction dir) {
        if (TreeFeature.validTreePos(p_236911_0_, p_236911_2_)) {
            setBlock(p_236911_0_, p_236911_2_, ModBlocks.COCONUT.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, dir), p_236911_4_);
            p_236911_3_.add(p_236911_2_.immutable());
            return true;
        } else {
            return false;
        }
    }
}