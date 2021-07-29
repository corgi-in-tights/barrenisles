package ca.thecorgi.barrenisles.utils.registry;

import ca.thecorgi.barrenisles.feature.tree.PalmFoliagePlacer;
import ca.thecorgi.barrenisles.feature.tree.PalmTrunkPlacer;
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
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;
import static ca.thecorgi.barrenisles.utils.registry.BlockRegistry.*;

@SuppressWarnings("deprecation")
public class FeatureRegistry {
    public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_PALM =
            Feature.TREE.configure(new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(PALM_LOG.getDefaultState()),
                    new PalmTrunkPlacer(6,2,1),
//                    new ForkingTrunkPlacer(6, 2, 2),
                    new SimpleBlockStateProvider(PALM_LEAVES.getDefaultState()),
                    new SimpleBlockStateProvider(PALM_SAPLING.getDefaultState()),
                    new PalmFoliagePlacer(ConstantIntProvider.create(5), ConstantIntProvider.create(4), ConstantIntProvider.create(3)),
//                    new AcaciaFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0)),
                    new TwoLayersFeatureSize(0, 0, 0)
            )
//                    .decorators(Collections.singletonList(PalmTreeDecorator.INSTANCE))
                    .ignoreVines().build());


    public static void register() {
        RegistryKey<ConfiguredFeature<?, ?>> treePalm = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(ModID, "tree_palm"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, treePalm.getValue(), TREE_PALM);
        BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Feature.VEGETAL_DECORATION, treePalm);
    }
}
