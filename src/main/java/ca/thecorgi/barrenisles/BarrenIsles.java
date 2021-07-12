package ca.thecorgi.barrenisles;

import ca.thecorgi.barrenisles.biomes.BiomeModifications;
import ca.thecorgi.barrenisles.utils.registry.*;
import net.fabricmc.api.ModInitializer;
import software.bernie.geckolib3.GeckoLib;


public class BarrenIsles implements ModInitializer {
    public static String ModID = "barrenisles";

    @Override
    public void onInitialize() {
        GeckoLib.initialize();

        StructureRegistry.register();
        BiomeModifications.register();
        EntityRegistry.register();
        SpawnRegistry.register();
        ItemRegistry.register();
    }
}
