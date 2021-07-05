package ca.thecorgi.barrenisles.utils.registry;

import ca.thecorgi.barrenisles.structures.*;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;

public class StructureRegistry {
    public static final Logger LOGGER = LogManager.getLogger();
    public static StructureFeature<DefaultFeatureConfig> OASIS = new OasisStructure(DefaultFeatureConfig.CODEC);
    public static StructureFeature<DefaultFeatureConfig> ROCK_1 = new Rock1Structure(DefaultFeatureConfig.CODEC);
    public static StructureFeature<DefaultFeatureConfig> ROCK_2 = new Rock2Structure(DefaultFeatureConfig.CODEC);

    public static void init() {
        FabricStructureBuilder.create(new Identifier(ModID, "oasis"), OASIS)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(new StructureConfig(18, 12, 320909345))
                .superflatFeature(OASIS.configure(FeatureConfig.DEFAULT))
                .adjustsSurface().register();
        FabricStructureBuilder.create(new Identifier(ModID, "ore_rock"), ROCK_1)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(new StructureConfig(32, 16, 283223445))
                .superflatFeature(ROCK_1.configure(FeatureConfig.DEFAULT))
                .adjustsSurface().register();
        FabricStructureBuilder.create(new Identifier(ModID, "tall_ore_rock"), ROCK_2)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(new StructureConfig(50, 27, 578325823))
                .superflatFeature(ROCK_2.configure(FeatureConfig.DEFAULT))
                .adjustsSurface().register();
    }


}
