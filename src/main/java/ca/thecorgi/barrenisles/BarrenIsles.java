package ca.thecorgi.barrenisles;

import ca.thecorgi.barrenisles.utils.registry.ConfiguredStructures;
import ca.thecorgi.barrenisles.utils.registry.BarrenIslesStructures;
import ca.thecorgi.barrenisles.utils.registry.EntityRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;


import static ca.thecorgi.barrenisles.utils.EntityUtils.*;

public class BarrenIsles implements ModInitializer {
    public static String ModID = "barrenisles";
    public static final Logger LOGGER = LogManager.getLogger();


    @Override
    @SuppressWarnings("deprecation")
    public void onInitialize() {
        GeckoLib.initialize();
            new EntityRegistry();
            FabricDefaultAttributeRegistry.register(EntityRegistry.DUNERAPTOR_ENTITY, createDuneraptor());
            FabricDefaultAttributeRegistry.register(EntityRegistry.BURAMAPHU_ENTITY, createBuramaphu());

        BarrenIslesStructures.setupAndRegisterStructureFeatures();
        ConfiguredStructures.registerConfiguredStructures();

        BiomeModifications.create(new Identifier(ModID, "oasis_addition"))
                .add(
                        ModificationPhase.ADDITIONS,
                        BiomeSelectors.categories(Biome.Category.DESERT),
                        context -> {
                            context.getGenerationSettings().addBuiltInStructure(ConfiguredStructures.CONFIGURED_OASIS);
                        });
        BiomeModifications.create(new Identifier(ModID, "rock_1_addition"))
                .add(
                        ModificationPhase.ADDITIONS,
                        BiomeSelectors.vanilla(),
                        context -> {
                            context.getGenerationSettings().addBuiltInStructure(ConfiguredStructures.CONFIGURED_ROCK_1);
                        });
        BiomeModifications.create(new Identifier(ModID, "rock_2_addition"))
                .add(
                        ModificationPhase.ADDITIONS,
                        BiomeSelectors.vanilla(),
                        context -> {
                            context.getGenerationSettings().addBuiltInStructure(ConfiguredStructures.CONFIGURED_ROCK_2);
                        });
    }
}
