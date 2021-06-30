package ca.thecorgi.barrenisles.client;

import ca.thecorgi.barrenisles.client.renderer.BuramaphuRenderer;
import ca.thecorgi.barrenisles.client.renderer.DuneraptorRenderer;
import ca.thecorgi.barrenisles.utils.registry.EntityRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.loader.api.FabricLoader;

import static ca.thecorgi.barrenisles.utils.registry.EntityRegistry.DUNERAPTOR_ENTITY;

public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.DUNERAPTOR_ENTITY, DuneraptorRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.BURAMAPHU_ENTITY, BuramaphuRenderer::new);
    }
}
