package barrenisles.core;

import barrenisles.api.blocks.BarrenIslesBlocks;
import barrenisles.api.items.BarrenIslesItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import barrenisles.init.ModSounds;
import barrenisles.init.ModTileEntityTypes;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BarrenIslesMod.MODID)
public class BarrenIslesMod
{
    public static final Logger logger = LogManager.getLogger();
    public static final String MODID = "barrenisles";

    public BarrenIslesMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::preInit);
        bus.addListener(this::clientSetup);
        bus.addListener(this::entityAttributeCreationEvent);
        bus.addListener(this::ModificateAttributes);

		ModSounds.register(bus);
        ModItems.register(bus);
        ModBlocks.register(bus);
        ModEntities.register(bus);
        
        ModTileEntityTypes.register(bus);
        ModContainers.register(bus);
        
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::onBlockClickEvent);
    }
    
    private void preInit(final FMLCommonSetupEvent event)
    {
    }

    private void clientSetup(final FMLClientSetupEvent event) 
    {
    	logger.info("Barren Isles setup in client...");
    	
    	// Register Vases GUI.
    	ScreenManager.register(BarrenIslesContainers.vase_container.get(), VaseScreen::new);
        ScreenManager.register(BarrenIslesContainers.gold_vase_container.get(), GoldVaseScreen::new);
    	
    	// Register Entity renderer
    	RenderingRegistry.registerEntityRenderingHandler(BarrenIslesEntities.coyote.get(),
                CoyoteRenderer::new);
    	RenderingRegistry.registerEntityRenderingHandler(BarrenIslesEntities.duneraptor.get(),
                DuneraptorRenderer::new);
    	RenderingRegistry.registerEntityRenderingHandler(BarrenIslesEntities.tumbleweed.get(),
                TumbleweedRenderer::new);

    	ModBlocks.transparency();
    }
    
    public void entityAttributeCreationEvent(EntityAttributeCreationEvent event) {
		logger.info("BarrenIsles is starting entity attribute event");
		event.put(BarrenIslesEntities.coyote.get(), CoyoteEntity.createCoyoteAttributes());
		event.put(BarrenIslesEntities.duneraptor.get(), DuneraptorEntity.createDuneraptorAttributes());
		event.put(BarrenIslesEntities.tumbleweed.get(), TumbleweedEntity.createTumbleweedAttributes());
	}
    
    public void ModificateAttributes(EntityAttributeModificationEvent e) {
    	e.add(BarrenIslesEntities.duneraptor.get(), Attributes.ATTACK_DAMAGE, 5D);
    }

    public void onBlockClickEvent(PlayerInteractEvent.RightClickBlock e) {
        if(e.getPlayer().getItemInHand(e.getHand()).getItem() instanceof BucketItem &&
                e.getPlayer().level.getBlockState(e.getPos()).is(BarrenIslesBlocks.quicksand.get())) {
            ItemStack quickSandBucket = new ItemStack(BarrenIslesItems.quicksand_bucket.get());
            e.getPlayer().awardStat(Stats.ITEM_USED.get(BarrenIslesItems.quicksand_bucket.get()));
            e.getPlayer().playSound(SoundEvents.BUCKET_FILL, 1.0F, 1.0F);
            e.getPlayer().level.destroyBlock(e.getPos(), true);
            e.getPlayer().setItemInHand(e.getHand(), quickSandBucket);
            if (!e.getPlayer().level.isClientSide) CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity)e.getPlayer(), quickSandBucket);
        }
    }
}
