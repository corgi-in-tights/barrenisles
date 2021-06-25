package ca.thecorgi.barrenisles.client;

import ca.thecorgi.barrenisles.client.renderer.DuneraptorRenderer;
import ca.thecorgi.barrenisles.registry.EntityRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.loader.api.FabricLoader;

public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            EntityRendererRegistry.INSTANCE.register(EntityRegistry.DUNERAPTOR_ENTITY, DuneraptorRenderer::new);
        }
    }
}
