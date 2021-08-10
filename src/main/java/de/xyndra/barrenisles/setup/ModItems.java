package de.xyndra.barrenisles.setup;

import de.xyndra.barrenisles.Creative_Tab;
import de.xyndra.barrenisles.items.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.TallBlockItem;
import net.minecraftforge.fml.RegistryObject;

public class ModItems {
    public static final ItemGroup creativeTab = new Creative_Tab();
    public static final Item sus_berry = new SUS_BERRY(ModBlocks.SUS_BERRY_BUSH, new Item.Properties().tab(creativeTab));
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
    public static final RegistryObject<Item> PALMLEAVES = Registration.ITEMS.register("palm_leaves", () -> new BlockItem(ModBlocks.H.get(), new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> I = Registration.ITEMS.register("coconut", () -> new BlockItem(ModBlocks.COCONUT, new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> J = Registration.ITEMS.register("coconut_open", () -> new Coconut_open(new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> K = Registration.ITEMS.register("palm_sapling", () -> new BlockItem(ModBlocks.PALMSAPLING, new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> L = Registration.ITEMS.register("winecup", () -> new BlockItem(ModBlocks.WINECUP, new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> M = Registration.ITEMS.register("poison_ivy", () -> new BlockItem(ModBlocks.POISON_IVY, new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> N = Registration.ITEMS.register("agave", () -> new BlockItem(ModBlocks.AGAVE, new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> O = Registration.ITEMS.register("marigold", () -> new BlockItem(ModBlocks.MARIGOLD, new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> P = Registration.ITEMS.register("desert_lily", () -> new TallBlockItem(ModBlocks.DESERT_LILY, new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> Q = Registration.ITEMS.register("thornweed", () -> new BlockItem(ModBlocks.THORNWEED, new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> S = Registration.ITEMS.register("barrel_cactus", () -> new BlockItem(ModBlocks.BARRELCACTUS, new Item.Properties().tab(creativeTab)));
    public static final RegistryObject<Item> R = Registration.ITEMS.register("suspicious_berry_bush", () -> sus_berry);
    public static final RegistryObject<Item> DUNERAPTORFEATHER = Registration.ITEMS.register("dune_feather", () -> new DuneraptorFeather(new Item.Properties().tab(creativeTab)));

    public static void register() {};
}
