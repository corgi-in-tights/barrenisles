package ca.thecorgi.barrenisles.utils.registry;

import ca.thecorgi.barrenisles.food.BarrenIslesFoodComponents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;
import static ca.thecorgi.barrenisles.utils.registry.GroupRegistry.BARREN_ISLES;

public class ItemRegistry {
    public static final Item DUNE_FEATHER = new Item(new FabricItemSettings().group(BARREN_ISLES));
    public static final Item DRUMSTICK = new Item(new FabricItemSettings().group(BARREN_ISLES).food(BarrenIslesFoodComponents.DRUMSTICK_C));
    public static final Item COOKED_DRUMSTICK = new Item(new FabricItemSettings().group(BARREN_ISLES).food(BarrenIslesFoodComponents.COOKED_DRUMSTICK_C));
//    public static final Item SUSPICIOUS_BERRIES = new Item(new FabricItemSettings().group(BARREN_ISLES).food(BarrenIslesFoodComponents.SUSPICIOUS_BERRIES_C));
//    public static Item SUSPICIOUS_BERRIES = new AliasedBlockItem(BlockRegistry.SUSPICIOUS_BERRY_BUSH, (new Item.Settings()).group(BARREN_ISLES));

    public static void register() {
        Registry.register(Registry.ITEM, new Identifier(ModID, "dune_feather"), DUNE_FEATHER);
        Registry.register(Registry.ITEM, new Identifier(ModID, "drumstick"), DRUMSTICK);
        Registry.register(Registry.ITEM, new Identifier(ModID, "cooked_drumstick"), COOKED_DRUMSTICK);
        Registry.register(Registry.ITEM, new Identifier(ModID, "duneraptor_spawn_egg"), new SpawnEggItem(EntityRegistry.DUNERAPTOR,
                0xd5671d, 0xd5951d, new Item.Settings().group(BARREN_ISLES)));
        Registry.register(Registry.ITEM, new Identifier(ModID, "coyote_spawn_egg"), new SpawnEggItem(EntityRegistry.COYOTE,
                0x40372f, 0xd0ae9c, new Item.Settings().group(BARREN_ISLES)));
//        Registry.register(Registry.ITEM, new Identifier(ModID, "suspicious_berries"), SUSPICIOUS_BERRIES);
    }
}
