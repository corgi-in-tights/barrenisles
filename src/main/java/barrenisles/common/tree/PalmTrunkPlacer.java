package barrenisles.common.tree;

import com.google.common.collect.Lists;

import barrenisles.api.BarrenIslesBlocks;

import java.util.List;
import java.util.Random;
import java.util.Set;

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

    public PalmTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    public List<FoliagePlacer.Foliage> placeTrunk(IWorldGenerationReader world, Random random, int p_230382_3_, BlockPos pos, Set<BlockPos> setIn, MutableBoundingBox box, BaseTreeFeatureConfig config) {
        setDirtAt(world, pos.below());
        List<FoliagePlacer.Foliage> list = Lists.newArrayList();
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        int i = p_230382_3_ - random.nextInt(4) - 1;
        int j = 3 - random.nextInt(3);
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        int k = pos.getX();
        int l = pos.getZ();
        int i1 = 0;

        for(int j1 = 0; j1 < p_230382_3_; ++j1) {
            int k1 = pos.getY() + j1;
            if (j1 >= i && j > 0) {
                k += direction.getStepX();
                l += direction.getStepZ();
                --j;
            }

            if (placeLog(world, random, blockpos$mutable.set(k, k1, l), setIn, box, config)) {
                i1 = k1 + 1;
            }
        }

        list.add(new FoliagePlacer.Foliage(new BlockPos(k, i1, l), 1, false));

        return list;
    }

    protected static boolean placeCoconut(IWorldGenerationReader world, BlockPos pos, Set<BlockPos> setIn, MutableBoundingBox box, Direction dir) {
        if (TreeFeature.validTreePos(world, pos)) {
            setBlock(world, pos, BarrenIslesBlocks.coconut.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, dir), box);
            setIn.add(pos.immutable());
            return true;
        } else {
            return false;
        }
    }
}