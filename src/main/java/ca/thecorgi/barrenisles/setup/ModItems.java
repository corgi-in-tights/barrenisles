package ca.thecorgi.barrenisles.setup;

import ca.thecorgi.barrenisles.Creative_Tab;
import ca.thecorgi.barrenisles.items.*;
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
    public static final RegistryObject<Item> DUNERAPTORFEATHER = Registration.ITEMS.register("dune_feather", () -> new DuneraptorFeather(new Item.Properties().tab(creativeTab)));

    public static void register() {};
}
