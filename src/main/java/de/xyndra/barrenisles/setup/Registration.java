package de.xyndra.barrenisles.setup;

import de.xyndra.barrenisles.BarrenIsles;
import de.xyndra.barrenisles.tree.PalmTreeFeature;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registration {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BarrenIsles.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BarrenIsles.MOD_ID);

    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);

        ModItems.register();
        ModBlocks.register();

    }

    public static void transparency(){
        ModBlocks.transparency();
    }

    public static void registerx(String key, ConfiguredFeature<BaseTreeFeatureConfig, ?> configuredFeature){
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }



}
