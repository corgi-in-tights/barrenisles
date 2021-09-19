package de.xyndra.barrenisles;


import de.xyndra.barrenisles.entitys.duneraptor.DuneraptorRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import de.xyndra.barrenisles.setup.EntityRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.example.client.renderer.entity.ExampleGeoRenderer;

@Mod.EventBusSubscriber(modid = "barrenisles", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientListener {
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void registerRenderers(final FMLClientSetupEvent event) {
		if (!FMLEnvironment.production) {
			RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.DUNERAPTOR_ENTITY.get(),
					DuneraptorRenderer::new);
		}
	}
}