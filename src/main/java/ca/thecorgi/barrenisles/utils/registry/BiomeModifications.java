package ca.thecorgi.barrenisles.utils.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;

public class BiomeModifications {
    public static void registerBiomeMods() {
        net.fabricmc.fabric.api.biome.v1.BiomeModifications.create(new Identifier(ModID, "oasis_addition"))
                .add(
                        ModificationPhase.ADDITIONS,
                        BiomeSelectors.categories(Biome.Category.DESERT),
                        context -> {
                            context.getGenerationSettings().addBuiltInStructure(ConfiguredStructures.CONFIGURED_OASIS);
                        });
        net.fabricmc.fabric.api.biome.v1.BiomeModifications.create(new Identifier(ModID, "rock_1_addition"))
                .add(
                        ModificationPhase.ADDITIONS,
                        BiomeSelectors.vanilla(),
                        context -> {
                            context.getGenerationSettings().addBuiltInStructure(ConfiguredStructures.CONFIGURED_ROCK_1);
                        });
        net.fabricmc.fabric.api.biome.v1.BiomeModifications.create(new Identifier(ModID, "rock_2_addition"))
                .add(
                        ModificationPhase.ADDITIONS,
                        BiomeSelectors.vanilla(),
                        context -> {
                            context.getGenerationSettings().addBuiltInStructure(ConfiguredStructures.CONFIGURED_ROCK_2);
                        });
    }
    }

