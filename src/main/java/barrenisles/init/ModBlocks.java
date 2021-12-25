package barrenisles.init;

import barrenisles.common.block.CoconutBlock;
import barrenisles.common.block.DesertFlowerBlock;
import barrenisles.common.block.GoldVaseBlock;
import barrenisles.common.block.PalmDoorBlock;
import barrenisles.common.block.PalmLogBlock;
import barrenisles.common.block.PalmTrapdoorBlock;
import barrenisles.common.block.PoisonIvyBlock;
import barrenisles.common.block.QuickSandBlock;
import barrenisles.common.block.SuspiciousBerryBushBlock;
import barrenisles.common.block.TallDesertFlowerBlock;
import barrenisles.common.block.ThornweedBlock;
import barrenisles.common.block.VaseBlock;
import barrenisles.common.tree.NormalPalmTree;
import barrenisles.core.BarrenIslesMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SoundType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effects;
import net.minecraft.block.StairsBlock;

import static barrenisles.api.BarrenIslesBlocks.*;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS
    = DeferredRegister.create(ForgeRegistries.BLOCKS, BarrenIslesMod.MODID);
    
    static {
    	palm_log = register("palm_log", 
    			() -> new PalmLogBlock(copy(Blocks.OAK_LOG)));
    	
        palm_wood = register("palm_wood", 
    			() -> new RotatedPillarBlock(copy(Blocks.OAK_WOOD)));
        
        stripped_palm_log = register("stripped_palm_log", 
    			() -> new RotatedPillarBlock(copy(Blocks.STRIPPED_OAK_LOG)));
        
        stripped_palm_wood = register("stripped_palm_wood", 
    			() -> new RotatedPillarBlock(copy(Blocks.STRIPPED_OAK_WOOD)));
        
        palm_planks = register("palm_planks", 
    			() -> new Block(copy(Blocks.OAK_PLANKS)));
        
        palm_slab = register("palm_slab", 
    			() -> new SlabBlock(copy(Blocks.OAK_SLAB)));
        
        palm_stairs = register("palm_stairs", 
    			() -> new StairsBlock(() -> palm_planks.get().defaultBlockState(), copy(Blocks.OAK_STAIRS)));
        
        palm_leaves = register("palm_leaves", 
    			() -> new LeavesBlock(copy(Blocks.OAK_LEAVES)));
        
        palm_door = register("palm_door", 
    			() -> new PalmDoorBlock(copy(Blocks.OAK_DOOR)));
        
        palm_trapdoor = register("palm_trapdoor", 
    			() -> new PalmTrapdoorBlock(copy(Blocks.OAK_TRAPDOOR)));
        
        palm_pressure_plate = register("palm_pressure_plate", 
    			() -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, copy(Blocks.OAK_PRESSURE_PLATE)));
        
        palm_fence = register("palm_fence", 
    			() -> new FenceBlock(copy(Blocks.OAK_FENCE)));
        
        palm_fence_gate = register("palm_fence_gate", 
    			() -> new FenceGateBlock(copy(Blocks.OAK_FENCE_GATE)));
        
        palm_button = register("palm_button", 
    			() -> new WoodButtonBlock(copy(Blocks.OAK_BUTTON)));
        
        palm_sapling = register("palm_sapling",
        		() -> new SaplingBlock(new NormalPalmTree(), Block.Properties.copy(Blocks.OAK_SAPLING)));
        

        winecup = register("winecup", 
    			() -> new DesertFlowerBlock(Effects.DIG_SPEED, 30, copy_plant(Blocks.DANDELION)));
        
        poison_ivy = register("poison_ivy", 
    			() -> new PoisonIvyBlock(copy_plant(Blocks.DANDELION)));
    			
        agave = register("agave", 
    			() -> new DesertFlowerBlock(Effects.WITHER, 70, copy_plant(Blocks.DANDELION)));
    			
        marigold = register("marigold",
    			() -> new DesertFlowerBlock(Effects.MOVEMENT_SPEED, 40, copy_plant(Blocks.DANDELION)));
    			
        desert_lily = register("desert_lily", 
    			() -> new TallDesertFlowerBlock(copy_plant(Blocks.ROSE_BUSH)));
    			
        suspicious_berry_bush = registerWithoutBlockItem("suspicious_berry_bush", 
    			() -> new SuspiciousBerryBushBlock(copy_plant(Blocks.SWEET_BERRY_BUSH)));
    			
        thornweed = register("thornweed", 
    			() -> new ThornweedBlock(copy_plant(Blocks.DEAD_BUSH)));
    			
        barrel_cactus = register("barrel_cactus", 
    			() -> new ThornweedBlock(copy_plant(Blocks.DEAD_BUSH)));
    			
        coconut = registerWithoutBlockItem("coconut", 
    			() -> new CoconutBlock(copy_plant(Blocks.COCOA)));
    			
        gold_vase = register("gold_vase", GoldVaseBlock::new);
        vase = register("vase", VaseBlock::new);

        quicksand = registerWithoutBlockItem("quicksand",
    	 		() -> new QuickSandBlock(AbstractBlock.Properties.of(Material.WEB)
                        .strength(0.344F)
                        .requiresCorrectToolForDrops()
                        .harvestTool(ToolType.SHOVEL)
                        .sound(SoundType.SAND)
                        .noCollission()));
    }
    
    // Call in main mod file
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
    
    // Register blocks, that have sub-items(coconut, berry_bush, etc...)
    // name - block id,
    // Supplier<T> block - you can create Supplier with anonymous class: () -> block_name
    private static <T extends Block>RegistryObject<T> registerWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }
    
    // Register blocks, with own texture and model
    // name - block id,
    private static <T extends Block>RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    
    // Register a block for inventory
    // name - block id
    // block - RegistryObject for block, create in barrenisles.api.block
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(ModGroups.BARREN_ISLES)));
    }
    
    // Just a wrapper over Properties.copy
    // block - block for copying.
    private static <T extends Block> AbstractBlock.Properties copy(T block) {
    	return AbstractBlock.Properties.copy(block);
    }
    
    // Copy plant properties
    private static <T extends Block> AbstractBlock.Properties copy_plant(T block) {
    	return AbstractBlock.Properties.copy(block).noOcclusion();
    }
    
    public static void transparency() {
        RenderTypeLookup.setRenderLayer(palm_door.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(palm_trapdoor.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(winecup.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(poison_ivy.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(agave.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(marigold.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(desert_lily.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(thornweed.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(barrel_cactus.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(suspicious_berry_bush.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(palm_sapling.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(palm_leaves.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(coconut.get(), RenderType.cutout());
    }
}
