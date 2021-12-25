
package barrenisles.init;

import barrenisles.api.BarrenIslesEntities;
import barrenisles.api.BarrenIslesSounds;
import barrenisles.common.block.DesertDiscItem;
import barrenisles.common.item.ModSpawnEggItem;
import barrenisles.common.item.QuicksandBucket;
import net.minecraft.item.Item;
import net.minecraft.item.Foods;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import barrenisles.core.BarrenIslesMod;

import static barrenisles.api.BarrenIslesItems.*;
import static barrenisles.init.ModGroups.BARREN_ISLES;

import barrenisles.api.BarrenIslesBlocks;
import barrenisles.api.BarrenIslesFood;
import net.minecraft.item.BlockItem;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BarrenIslesMod.MODID);
    
    static {
    	dune_feather = ITEMS.register("dune_feather",
            () -> new Item(new Item.Properties()
					.tab(BARREN_ISLES)));
    
    	drumstick = ITEMS.register("drumstick", 
    		() -> new Item(new Item.Properties()
    				.tab(BARREN_ISLES)
    				.food(BarrenIslesFood.DRUMSTICK_C)));
    	
    	cooked_drumstick = ITEMS.register("cooked_drumstick", 
    			() -> new Item(new Item.Properties()
    					.tab(BARREN_ISLES)
    					.food(BarrenIslesFood.COOKED_DRUMSTICK_C)));
    	
    	suspicious_berries = ITEMS.register("suspicious_berries", 
    			() -> new BlockItem(BarrenIslesBlocks.suspicious_berry_bush.get(), 
    					new Item.Properties()
    					.tab(BARREN_ISLES)
    					.food(BarrenIslesFood.SUSPICIOUS_BERRIES_C)));
    	
    	coconut = ITEMS.register("coconut",
    			() -> new BlockItem(BarrenIslesBlocks.coconut.get(),
    					new Item.Properties()
    					.tab(BARREN_ISLES)
						.food(Foods.MELON_SLICE)));
    	
    	quicksand_bucket = ITEMS.register("quicksand_bucket",
    			() -> new QuicksandBucket(BarrenIslesBlocks.quicksand,
    					new Item.Properties()
    					.craftRemainder(Items.BUCKET)
    					.stacksTo(1)
    					.tab(BARREN_ISLES)));
    	
    	barren_night_disc = ITEMS.register("music_disc_barren_night",
    			() -> new DesertDiscItem(20, () -> BarrenIslesSounds.barren_night.get(),
    					new Item.Properties()
    					.stacksTo(1)
    					.tab(BARREN_ISLES)
    					.rarity(Rarity.RARE)));

		tumbleweed_spawn_egg = ITEMS.register("tumbleweed_spawn_egg",
				() -> new ModSpawnEggItem(() -> BarrenIslesEntities.tumbleweed.get(), 0x4d3c02, 0x6b5505,
						new Item.Properties().tab(BARREN_ISLES)));

		coyote_spawn_egg = ITEMS.register("coyote_spawn_egg",
				() -> new ModSpawnEggItem(() -> BarrenIslesEntities.coyote.get(), 0x40372f, 0xd0ae9c,
						new Item.Properties().tab(BARREN_ISLES)));

		duneraptor_spawn_egg = ITEMS.register("duneraptor_spawn_egg",
				() -> new ModSpawnEggItem(() -> BarrenIslesEntities.duneraptor.get(), 0xd5671d, 0xd5951d,
						new Item.Properties().tab(BARREN_ISLES)));

	}
    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}