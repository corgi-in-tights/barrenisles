package ca.thecorgi.barrenisles;

import ca.thecorgi.barrenisles.utils.registry.ConfiguredStructures;
import ca.thecorgi.barrenisles.utils.registry.BarrenIslesStructures;
import ca.thecorgi.barrenisles.utils.registry.EntityRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;


import static ca.thecorgi.barrenisles.utils.EntityUtils.*;
import static ca.thecorgi.barrenisles.utils.registry.BiomeModifications.registerBiomeMods;

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
        registerBiomeMods();
    }
}
