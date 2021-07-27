package ca.thecorgi.barrenisles.setup;

import ca.thecorgi.barrenisles.Creative_Tab;
import ca.thecorgi.barrenisles.items.*;
import ca.thecorgi.barrenisles.setup.ModBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

public class ModItems {
    public static final ItemGroup creativeTab = new Creative_Tab();
    public static final RegistryObject<Item> DRUMSTICK = Registration.ITEMS.register("drumstick", () -> new Drumstick(new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> COOKED_DRUMSTICK = Registration.ITEMS.register("cooked_drumstick", () -> new CookedDrumstick(new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> PALMDOORITEM = Registration.ITEMS.register("palm_door", () -> new PalmDoorItem(new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> PALMLOGITEM = Registration.ITEMS.register("palm_log", () -> new PalmLogItem(new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> PALMPLANKITEM = Registration.ITEMS.register("palm_planks", () -> new PalmPlanksItem(new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> PALMTRAPDOORITEM = Registration.ITEMS.register("palm_trapdoor", () -> new PalmTrapdoorItem(new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> PALMBUTTON = Registration.ITEMS.register("palm_button", () -> new PalmButton(new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> PALMPRESSUREPLATE = Registration.ITEMS.register("palm_pressure_plate", () -> new PalmPressurePlate(new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> A = Registration.ITEMS.register("palm_slab", () -> new BlockItem(ModBlocks.PALMSLAB, new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> B = Registration.ITEMS.register("palm_stairs", () -> new BlockItem(ModBlocks.PALMSTAIRS, new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> C = Registration.ITEMS.register("palm_fence_gate", () -> new BlockItem(ModBlocks.PALMFENCEGATE, new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> D = Registration.ITEMS.register("palm_fence", () -> new BlockItem(ModBlocks.PALMFENCE, new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> E = Registration.ITEMS.register("palm_wood", () -> new BlockItem(ModBlocks.PALMWOOD, new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> F = Registration.ITEMS.register("stripped_palm_log", () -> new BlockItem(ModBlocks.STRIPPEDPALMLOG, new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> G = Registration.ITEMS.register("stripped_palm_wood", () -> new BlockItem(ModBlocks.STRIPPEDPALMWOOD, new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> DUNERAPTORFEATHER = Registration.ITEMS.register("dune_feather", () -> new DuneraptorFeather(new Item.Properties().tab(creativeTab)));

    public static void register() {};
}
