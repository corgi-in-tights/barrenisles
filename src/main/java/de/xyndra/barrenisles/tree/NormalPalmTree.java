package de.xyndra.barrenisles.tree;

import de.xyndra.barrenisles.setup.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.ForkyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class NormalPalmTree extends Tree {

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> PALM =
            Feature.TREE.configured(
                    new BaseTreeFeatureConfig.Builder(
                            new SimpleBlockStateProvider(ModBlocks.PALMLOG.defaultBlockState()),
                            new SimpleBlockStateProvider(ModBlocks.PALMLEAVES.defaultBlockState()),
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
