
package barrenisles.init;

import barrenisles.api.sounds.BarrenIslesSounds;
import barrenisles.common.block.DesertDiscItem;
import barrenisles.common.item.QuickSandBucket;
import net.minecraft.item.Item;
import net.minecraft.item.Foods;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import barrenisles.core.BarrenIslesMod;

import static barrenisles.api.items.BarrenIslesItems.*;
import static barrenisles.init.ModGroups.BARREN_ISLES;

import barrenisles.api.blocks.BarrenIslesBlocks;
import barrenisles.api.food.BarrenIslesFood;
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
    			() -> new QuickSandBucket(BarrenIslesBlocks.quicksand,
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
    }
    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}