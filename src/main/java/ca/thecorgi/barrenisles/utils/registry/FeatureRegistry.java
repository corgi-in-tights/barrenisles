package ca.thecorgi.barrenisles.utils.registry;

import ca.thecorgi.barrenisles.feature.tree.PalmFoliagePlacer;
import ca.thecorgi.barrenisles.feature.tree.PalmTreeDecorator;
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

import java.util.Collections;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;
import static ca.thecorgi.barrenisles.utils.registry.BlockRegistry.*;

@SuppressWarnings("deprecation")
public class FeatureRegistry {


    public static void register() {
        RegistryKey<ConfiguredFeature<?, ?>> palmTree = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(ModID, "palm_tree"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, palmTree.getValue(), ConfiguredFeatureRegistry.PALM_TREE);


    }
}
