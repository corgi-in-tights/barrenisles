package de.xyndra.barrenisles;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;
import de.xyndra.barrenisles.setup.EntityRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = "barrenisles", bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonListener {
	@SubscribeEvent
	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		final Logger LOGGER = LogManager.getLogger();
		LOGGER.debug("Common Listener was called");
		if (!FMLLoader.isProduction()) {
			event.put(EntityRegistry.DUNERAPTOR_ENTITY.get(),
					MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 1.0D).build());
		}
	}
}