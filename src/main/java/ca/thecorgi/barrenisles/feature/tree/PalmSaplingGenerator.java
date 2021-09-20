package ca.thecorgi.barrenisles.feature.tree;

import ca.thecorgi.barrenisles.utils.registry.ConfiguredFeatureRegistry;
import ca.thecorgi.barrenisles.utils.registry.FeatureRegistry;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;


public class PalmSaplingGenerator extends SaplingGenerator {
    private final ConfiguredFeature<TreeFeatureConfig, ?> feature;

    public PalmSaplingGenerator(ConfiguredFeature<?, ?> feature) {
        this.feature = (ConfiguredFeature<TreeFeatureConfig, ?>) feature;
    }

    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bees) {
        return ConfiguredFeatureRegistry.PALM_TREE;
    }
}
