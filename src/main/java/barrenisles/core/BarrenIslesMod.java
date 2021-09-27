package barrenisles.core;

import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import barrenisles.api.blocks.BarrenIslesBlocks;
import barrenisles.api.entity.BarrenIslesEntities;
import barrenisles.api.tileentities.BarrenIslesContainers;
import barrenisles.client.gui.inventory.screen.GoldVaseScreen;
import barrenisles.client.gui.inventory.screen.VaseScreen;
import barrenisles.client.renderer.CoyoteRenderer;
import barrenisles.client.renderer.DuneraptorRenderer;
import barrenisles.client.renderer.TumbleweedRenderer;
import barrenisles.common.entity.CoyoteEntity;
import barrenisles.common.entity.DuneraptorEntity;
import barrenisles.common.entity.TumbleweedEntity;
import barrenisles.init.ModBlocks;
import barrenisles.init.ModContainers;
import barrenisles.init.ModEntities;
import barrenisles.init.ModItems;
import barrenisles.init.ModTileEntityTypes;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BarrenIslesMod.MODID)
public class BarrenIslesMod
{
    public static final Logger logger = LogManager.getLogger();
    public static final String MODID = "barrenisles";

    public BarrenIslesMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::entityAttributeCreationEvent);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::ModificateAttributes);

        ModItems.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntities.register(FMLJavaModLoadingContext.get().getModEventBus());
        
        ModTileEntityTypes.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModContainers.register(FMLJavaModLoadingContext.get().getModEventBus());
        
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    private void preInit(final FMLCommonSetupEvent event)
    {
        logger.info("Barren Isles is starting preinit");
    }

    private void clientSetup(final FMLClientSetupEvent event) 
    {
    	logger.info("Barren Isles setup in client...");
    	
    	// Register Vases GUI.
    	ScreenManager.register(BarrenIslesContainers.gold_vase_container.get(), GoldVaseScreen::new);
    	ScreenManager.register(BarrenIslesContainers.vase_container.get(), VaseScreen::new);
    	
    	// Register Entity renderer
    	RenderingRegistry.registerEntityRenderingHandler(BarrenIslesEntities.coyote.get(),
				manager -> new CoyoteRenderer(manager));
    	RenderingRegistry.registerEntityRenderingHandler(BarrenIslesEntities.duneraptor.get(),
				manager -> new DuneraptorRenderer(manager));
    	RenderingRegistry.registerEntityRenderingHandler(BarrenIslesEntities.tumbleweed.get(),
				manager -> new TumbleweedRenderer(manager));
    	
    	//Hacks to create translucent flowers.
    	RenderTypeLookup.setRenderLayer (BarrenIslesBlocks.palm_sapling.get(), RenderType.translucent());
    	RenderTypeLookup.setRenderLayer (BarrenIslesBlocks.agave.get(), RenderType.translucent());
    	RenderTypeLookup.setRenderLayer (BarrenIslesBlocks.barrel_cactus.get(), RenderType.translucent());
    	RenderTypeLookup.setRenderLayer (BarrenIslesBlocks.desert_lily.get(), RenderType.translucent());
    	RenderTypeLookup.setRenderLayer (BarrenIslesBlocks.marigold.get(), RenderType.translucent());
    	RenderTypeLookup.setRenderLayer (BarrenIslesBlocks.poison_ivy.get(), RenderType.translucent());
    	RenderTypeLookup.setRenderLayer (BarrenIslesBlocks.thornweed.get(), RenderType.translucent());
    	RenderTypeLookup.setRenderLayer (BarrenIslesBlocks.winecup.get(), RenderType.translucent());
    }
    
    public void entityAttributeCreationEvent(EntityAttributeCreationEvent event) {
		logger.info("BarrenIsles is starting entity attribute event");
		event.put(BarrenIslesEntities.coyote.get(), CoyoteEntity.createCoyoteAttributes().build());
		event.put(BarrenIslesEntities.duneraptor.get(), DuneraptorEntity.createDuneraptorAttributes().build());
		event.put(BarrenIslesEntities.tumbleweed.get(), TumbleweedEntity.createTumbleweedAttributes().build());
	}
    
    public void ModificateAttributes(EntityAttributeModificationEvent e) {
    	e.add(BarrenIslesEntities.duneraptor.get(), Attributes.ATTACK_DAMAGE, 5D);
    }
}
