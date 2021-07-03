package ca.thecorgi.barrenisles.utils.registry;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;

public class ConfiguredStructures {
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_OASIS = BarrenIslesStructures.OASIS.configure(DefaultFeatureConfig.DEFAULT);
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_ROCK_1 = BarrenIslesStructures.ROCK_1.configure(DefaultFeatureConfig.DEFAULT);
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_ROCK_2 = BarrenIslesStructures.ROCK_2.configure(DefaultFeatureConfig.DEFAULT);

    public static void registerConfiguredStructures() {
        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new Identifier(ModID, "configured_oasis"), CONFIGURED_OASIS);
        Registry.register(registry, new Identifier(ModID, "configured_rock_1"), CONFIGURED_ROCK_1);
        Registry.register(registry, new Identifier(ModID, "configured_rock_2"), CONFIGURED_ROCK_2);

    }
}
