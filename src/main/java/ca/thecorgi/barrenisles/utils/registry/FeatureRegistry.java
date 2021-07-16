package ca.thecorgi.barrenisles.utils.registry;

import ca.thecorgi.barrenisles.feature.tree.PalmTreePlacer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.AcaciaFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;
import static ca.thecorgi.barrenisles.utils.registry.BlockRegistry.PALM_SAPLING;
import static net.minecraft.block.Blocks.JUNGLE_LEAVES;
import static net.minecraft.block.Blocks.JUNGLE_LOG;

@SuppressWarnings("deprecation")
public class FeatureRegistry {
    public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_PALM =
            Feature.TREE.configure(new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(JUNGLE_LOG.getDefaultState()),
                    new PalmTreePlacer(2, 2, 2),
                    new SimpleBlockStateProvider(JUNGLE_LEAVES.getDefaultState()),
                    new SimpleBlockStateProvider(PALM_SAPLING.getDefaultState()),
                    new AcaciaFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(2)),
                    new TwoLayersFeatureSize(0, 0, 0)
            ).build());


    public static void register() {
        RegistryKey<ConfiguredFeature<?, ?>> treePalm = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(ModID, "tree_palm"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, treePalm.getValue(), TREE_PALM);
        BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Feature.VEGETAL_DECORATION, treePalm);
    }
}
