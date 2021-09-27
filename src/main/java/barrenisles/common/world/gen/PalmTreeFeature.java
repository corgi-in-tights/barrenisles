package barrenisles.common.world.gen;

import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import barrenisles.api.blocks.BarrenIslesBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.common.util.Constants;

public class PalmTreeFeature extends TreeFeature {
    public PalmTreeFeature(Codec<BaseTreeFeatureConfig> codec) {
        super(codec);
    }

    public ConfiguredFeature<BaseTreeFeatureConfig, ?> setConfiguration() {
        return this.configured(
                new BaseTreeFeatureConfig.Builder(
                        new SimpleBlockStateProvider(BarrenIslesBlocks.palm_log.get().defaultBlockState()),
                        new SimpleBlockStateProvider(BarrenIslesBlocks.palm_leaves.get().defaultBlockState()),
                        new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3),
                        new StraightTrunkPlacer(5, 2, 0),
                        new TwoLayerFeature(1, 0, 1)
                ).ignoreVines().decorators(ImmutableList.of(new BeehiveTreeDecorator(0.002F))).build());
    }
    
    private static final Direction[] DIRECTIONS = ArrayUtils.removeElement(Direction.values(), Direction.UP);
    public static void spawnCoconuts(IWorldGenerationReader world, BlockPos pos, Random random, int chance, BlockState state) {
        final BlockState coconut = BarrenIslesBlocks.coconut.get().defaultBlockState();
        for (Direction d : DIRECTIONS) {
            BlockPos pos2 = pos.relative(d);
            if (random.nextInt(chance) == 0 && TreeFeature.isAirOrLeaves(world, pos2)) {
                world.setBlock(pos2, coconut.setValue(BlockStateProperties.FACING, d.getOpposite()).setValue(BlockStateProperties.AGE_2, 1), Constants.BlockFlags.DEFAULT);
            }
        }
    }
}