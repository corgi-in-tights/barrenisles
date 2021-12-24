package barrenisles.common.tree;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.TwoLayerFeature;

import javax.annotation.Nullable;

import barrenisles.api.BarrenIslesBlocks;

import java.util.Random;

public class NormalPalmTree extends Tree {

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> PALM =
            Feature.TREE.configured(
                    new BaseTreeFeatureConfig.Builder(
                            new SimpleBlockStateProvider(BarrenIslesBlocks.palm_log.get().defaultBlockState()),
                            new SimpleBlockStateProvider(BarrenIslesBlocks.palm_leaves.get().defaultBlockState()),
                            new PalmFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0)),
                            new PalmTrunkPlacer(6,2,1),
                            new TwoLayerFeature(1, 0, 1)
                    ).ignoreVines().build());



    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random randomIn, boolean largeHive) {
        return PALM;
    }


}
