package ca.thecorgi.barrenisles.client;

import ca.thecorgi.barrenisles.client.renderer.DuneraptorRenderer;
import ca.thecorgi.barrenisles.client.renderer.TumbleweedRenderer;
import ca.thecorgi.barrenisles.utils.registry.EntityRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

import static ca.thecorgi.barrenisles.utils.registry.BlockRegistry.*;

public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.DUNERAPTOR, DuneraptorRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.TUMBLEWEED, TumbleweedRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(PALM_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PALM_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PALM_LEAVES, RenderLayer.getCutout());

    }
}
