package ca.thecorgi.barrenisles.utils.registry;

import ca.thecorgi.barrenisles.structures.*;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;

public class BarrenIslesStructures {
    public static StructureFeature<DefaultFeatureConfig> OASIS = new OasisStructure(DefaultFeatureConfig.CODEC);
    public static StructureFeature<DefaultFeatureConfig> ROCK_1 = new Rock1Structure(DefaultFeatureConfig.CODEC);
    public static StructureFeature<DefaultFeatureConfig> ROCK_2 = new Rock2Structure(DefaultFeatureConfig.CODEC);

    public static void setupAndRegisterStructureFeatures() {
        FabricStructureBuilder.create(new Identifier(ModID, "oasis_pool"), OASIS)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(new StructureConfig(18, 12, 399119345))
                .superflatFeature(OASIS.configure(FeatureConfig.DEFAULT))
                .adjustsSurface().register();
        FabricStructureBuilder.create(new Identifier(ModID, "rock_1_pool"), ROCK_1)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(new StructureConfig(30, 14, 283223445))
                .superflatFeature(ROCK_1.configure(FeatureConfig.DEFAULT))
                .adjustsSurface().register();
        FabricStructureBuilder.create(new Identifier(ModID, "rock_2_pool"), ROCK_2)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(new StructureConfig(50, 22, 578325823))
                .superflatFeature(ROCK_2.configure(FeatureConfig.DEFAULT))
                .adjustsSurface().register();
    }


}
