package ca.thecorgi.barrenisles.client;

import ca.thecorgi.barrenisles.client.renderer.DuneraptorRenderer;
import ca.thecorgi.barrenisles.client.renderer.TumbleweedRenderer;
import ca.thecorgi.barrenisles.utils.registry.BlockRegistry;
import ca.thecorgi.barrenisles.utils.registry.EntityRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.DUNERAPTOR, DuneraptorRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.TUMBLEWEED, TumbleweedRenderer::new);
        BlockRegistry.registerClient();
    }
}
