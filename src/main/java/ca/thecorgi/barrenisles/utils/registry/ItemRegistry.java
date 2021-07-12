package ca.thecorgi.barrenisles.utils.registry;

import ca.thecorgi.barrenisles.food.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;
import static ca.thecorgi.barrenisles.utils.registry.GroupRegistry.MAIN_GROUP;

public class ItemRegistry {
    public static final Item DUNE_FEATHER = new Item(new FabricItemSettings().group(MAIN_GROUP));
    public static final Item DRUMSTICK = new Item(new FabricItemSettings().group(MAIN_GROUP).food(BarrenIslesFoodComponents.DRUMSTICK_C));
    public static final Item COOKED_DRUMSTICK = new Item(new FabricItemSettings().group(MAIN_GROUP).food(BarrenIslesFoodComponents.COOKED_DRUMSTICK_C));

    public static void register() {
        Registry.register(Registry.ITEM, new Identifier(ModID, "dune_feather"), DUNE_FEATHER);
        Registry.register(Registry.ITEM, new Identifier(ModID, "drumstick"), DRUMSTICK);
        Registry.register(Registry.ITEM, new Identifier(ModID, "cooked_drumstick"), COOKED_DRUMSTICK);
        Registry.register(Registry.ITEM, new Identifier(ModID, "duneraptor_spawn_egg"), new SpawnEggItem(EntityRegistry.DUNERAPTOR,
                0xd5671d, 0xd5951d, new Item.Settings().group(MAIN_GROUP)));
    }
}
