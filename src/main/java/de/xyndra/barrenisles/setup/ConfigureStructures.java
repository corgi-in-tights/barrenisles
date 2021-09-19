package de.xyndra.barrenisles.setup;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ConfigureStructures {
    /**
     * Static instance of our structure so we can reference it and add it to biomes easily.
     */
    public static StructureFeature<?, ?> CONFIGURED_ROCK_1 = ModStructures.ROCK_1.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?, ?> CONFIGURED_ROCK_2 = ModStructures.ROCK_2.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?, ?> CONFIGURED_OASIS = ModStructures.OASIS.get().configured(IFeatureConfig.NONE);
<<<<<<< Updated upstream
    public static StructureFeature<?, ?> CONFIGURED_BADLANDS_TEMPLE = ModStructures.BADLANDS_TEMPLE.get().configured(IFeatureConfig.NONE);
=======
>>>>>>> Stashed changes

    /**
     * Registers the configured structure which is what gets added to the biomes.
     * Noticed we are not using a forge registry because there is none for configured structures.
     *
     * We can register configured structures at any time before a world is clicked on and made.
     * But the best time to register configured features by code is honestly to do it in FMLCommonSetupEvent.
     */
    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation("barrenisles", "configured_ROCK_1"), CONFIGURED_ROCK_1);
        Registry.register(registry, new ResourceLocation("barrenisles", "configured_ROCK_2"), CONFIGURED_ROCK_2);
        Registry.register(registry, new ResourceLocation("barrenisles", "configured_OASIS"), CONFIGURED_OASIS);
<<<<<<< Updated upstream
        Registry.register(registry, new ResourceLocation("barrenisles", "configured_BADLANDS_TEMPLE"), CONFIGURED_BADLANDS_TEMPLE);
=======
>>>>>>> Stashed changes

        /* Ok so, this part may be hard to grasp but basically, just add your structure to this to
         * prevent any sort of crash or issue with other mod's custom ChunkGenerators. If they use
         * FlatGenerationSettings.STRUCTURE_FEATURES in it and you don't add your structure to it, the game
         * could crash later when you attempt to add the StructureSeparationSettings to the dimension.
         *
         * (It would also crash with superflat worldtype if you omit the below line
         * and attempt to add the structure's StructureSeparationSettings to the world)
         *
         * Note: If you want your structure to spawn in superflat, remove the FlatChunkGenerator check
         * in StructureTutorialMain.addDimensionalSpacing and then create a superflat world, exit it,
         * and re-enter it and your structures will be spawning. I could not figure out why it needs
         * the restart but honestly, superflat is really buggy and shouldn't be your main focus in my opinion.
         *
         * Requires AccessTransformer ( see resources/META-INF/accesstransformer.cfg )
         */
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.ROCK_1.get(), CONFIGURED_ROCK_1);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.ROCK_2.get(), CONFIGURED_ROCK_2);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.OASIS.get(), CONFIGURED_OASIS);
<<<<<<< Updated upstream
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.BADLANDS_TEMPLE.get(), CONFIGURED_BADLANDS_TEMPLE);
=======
>>>>>>> Stashed changes
    }
}
