package ca.thecorgi.barrenisles;

import ca.thecorgi.barrenisles.biomes.BiomeModifications;
import ca.thecorgi.barrenisles.utils.registry.ConfigStructureRegistry;
import ca.thecorgi.barrenisles.utils.registry.SpawnRegistry;
import ca.thecorgi.barrenisles.utils.registry.StructureRegistry;
import ca.thecorgi.barrenisles.utils.registry.EntityRegistry;
import net.fabricmc.api.ModInitializer;
import software.bernie.geckolib3.GeckoLib;


public class BarrenIsles implements ModInitializer {
    public static String ModID = "barrenisles";

    @Override
    public void onInitialize() {
        GeckoLib.initialize();

        StructureRegistry.init();
        ConfigStructureRegistry.init();
        BiomeModifications.init();
        EntityRegistry.init();
        SpawnRegistry.init();
    }
}
