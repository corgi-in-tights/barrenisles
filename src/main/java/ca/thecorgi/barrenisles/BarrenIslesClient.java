package ca.thecorgi.barrenisles;

import ca.thecorgi.barrenisles.utils.registry.BlockRegistry;
import ca.thecorgi.barrenisles.utils.registry.GUIRegistry;
import ca.thecorgi.barrenisles.utils.registry.SpawnRegistry;
import net.fabricmc.api.ClientModInitializer;

public class BarrenIslesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRegistry.registerClient();
        GUIRegistry.registerClient();
        SpawnRegistry.registerClient();
    }
}