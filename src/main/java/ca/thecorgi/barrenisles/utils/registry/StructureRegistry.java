package ca.thecorgi.barrenisles.utils.registry;

import ca.thecorgi.barrenisles.feature.structures.BadlandsTempleStructure;
import ca.thecorgi.barrenisles.feature.structures.OasisStructure;
import ca.thecorgi.barrenisles.feature.structures.Rock1Structure;
import ca.thecorgi.barrenisles.feature.structures.TallRock2Structure;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;
import static ca.thecorgi.barrenisles.BarrenIsles.config;

@SuppressWarnings("deprecation")
public class StructureRegistry {
    public static StructureFeature<DefaultFeatureConfig> OASIS = new OasisStructure(DefaultFeatureConfig.CODEC);
    public static StructureFeature<DefaultFeatureConfig> ROCK_1 = new Rock1Structure(DefaultFeatureConfig.CODEC);
    public static StructureFeature<DefaultFeatureConfig> ROCK_2 = new TallRock2Structure(DefaultFeatureConfig.CODEC);
    public static StructureFeature<DefaultFeatureConfig> BADLANDS_TEMPLE = new BadlandsTempleStructure(DefaultFeatureConfig.CODEC);


    public static ConfiguredStructureFeature<?, ?> CONFIGURED_OASIS = StructureRegistry.OASIS.configure(DefaultFeatureConfig.DEFAULT);
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_ROCK_1 = StructureRegistry.ROCK_1.configure(DefaultFeatureConfig.DEFAULT);
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_ROCK_2 = StructureRegistry.ROCK_2.configure(DefaultFeatureConfig.DEFAULT);
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_BADLANDS_TEMPLE = StructureRegistry.BADLANDS_TEMPLE.configure(DefaultFeatureConfig.DEFAULT);


    public static void register() {
        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;


        if (config.generation.generate_oasis == true) {
            Registry.register(registry, new Identifier(ModID, "configured_oasis"), CONFIGURED_OASIS);

            net.fabricmc.fabric.api.biome.v1.BiomeModifications.create(new Identifier(ModID, "oasis_addition"))
                    .add(
                            ModificationPhase.ADDITIONS,
                            BiomeSelectors.categories(Biome.Category.DESERT),
                            context -> {
                                context.getGenerationSettings().addBuiltInStructure(CONFIGURED_OASIS);
                            });

            FabricStructureBuilder.create(new Identifier(ModID, "oasis"), OASIS)
                    .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                    .defaultConfig(new StructureConfig(18, 12, 320909345))
                    .superflatFeature(OASIS.configure(FeatureConfig.DEFAULT))
                    .adjustsSurface().register();
        }
        if (config.generation.generate_ore_rock == true) {
            Registry.register(registry, new Identifier(ModID, "configured_rock_1"), CONFIGURED_ROCK_1);

            net.fabricmc.fabric.api.biome.v1.BiomeModifications.create(new Identifier(ModID, "rock_1_addition"))
                    .add(
                            ModificationPhase.ADDITIONS,
                            BiomeSelectors.vanilla(),
                            context -> {
                                context.getGenerationSettings().addBuiltInStructure(CONFIGURED_ROCK_1);
                            });

            FabricStructureBuilder.create(new Identifier(ModID, "ore_rock"), ROCK_1)
                    .step(GenerationStep.Feature.TOP_LAYER_MODIFICATION)
                    .defaultConfig(new StructureConfig(28, 16, 283223445))
                    .superflatFeature(ROCK_1.configure(FeatureConfig.DEFAULT))
                    .adjustsSurface().register();
        }

        if (config.generation.generate_tall_ore_rock == true) {
            Registry.register(registry, new Identifier(ModID, "configured_rock_2"), CONFIGURED_ROCK_2);

            net.fabricmc.fabric.api.biome.v1.BiomeModifications.create(new Identifier(ModID, "rock_2_addition"))
                    .add(
                            ModificationPhase.ADDITIONS,
                            BiomeSelectors.vanilla(),
                            context -> {
                                context.getGenerationSettings().addBuiltInStructure(CONFIGURED_ROCK_2);
                            });

            FabricStructureBuilder.create(new Identifier(ModID, "tall_ore_rock"), ROCK_2)
                    .step(GenerationStep.Feature.TOP_LAYER_MODIFICATION)
                    .defaultConfig(new StructureConfig(40, 29, 579125823))
                    .superflatFeature(ROCK_2.configure(FeatureConfig.DEFAULT))
                    .adjustsSurface().register();

        }

        if (config.generation.generate_badlands_temple == true) {
            Registry.register(registry, new Identifier(ModID, "configured_badlands_temple"), CONFIGURED_BADLANDS_TEMPLE);

            net.fabricmc.fabric.api.biome.v1.BiomeModifications.create(new Identifier(ModID, "badlands_temple_addition"))
                    .add(
                            ModificationPhase.ADDITIONS,
                            BiomeSelectors.categories(Biome.Category.MESA),
                            context -> {
                                context.getGenerationSettings().addBuiltInStructure(CONFIGURED_BADLANDS_TEMPLE);
                            });

            FabricStructureBuilder.create(new Identifier(ModID, "badlands_temple"), BADLANDS_TEMPLE)
                    .step(GenerationStep.Feature.UNDERGROUND_STRUCTURES)
                    .defaultConfig(new StructureConfig(32, 24, 854383894))
                    .superflatFeature(BADLANDS_TEMPLE.configure(FeatureConfig.DEFAULT))
                    .adjustsSurface().register();
        }
    }


}
