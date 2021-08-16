package ca.thecorgi.barrenisles.utils.registry;

import ca.thecorgi.barrenisles.blocks.DesertDiscItem;
import ca.thecorgi.barrenisles.food.BarrenIslesFoodComponents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;
import static ca.thecorgi.barrenisles.utils.registry.BlockRegistry.SUSPICIOUS_BERRY_BUSH;
import static ca.thecorgi.barrenisles.utils.registry.GroupRegistry.BARREN_ISLES;

public class ItemRegistry {
    public static final Item DUNE_FEATHER = new Item(new FabricItemSettings().group(BARREN_ISLES));
    public static final Item DRUMSTICK = new Item(new FabricItemSettings().group(BARREN_ISLES).food(BarrenIslesFoodComponents.DRUMSTICK_C));
    public static final Item COOKED_DRUMSTICK = new Item(new FabricItemSettings().group(BARREN_ISLES).food(BarrenIslesFoodComponents.COOKED_DRUMSTICK_C));
    public static final Item SUSPICIOUS_BERRIES = new AliasedBlockItem(SUSPICIOUS_BERRY_BUSH, new FabricItemSettings().group(BARREN_ISLES).food(BarrenIslesFoodComponents.SUSPICIOUS_BERRIES_C));
    public static final Item COCONUT = new AliasedBlockItem(BlockRegistry.COCONUT, new FabricItemSettings().group(BARREN_ISLES).food(FoodComponents.MELON_SLICE));
    public static final Item VASE = new AliasedBlockItem(BlockRegistry.VASE, new FabricItemSettings().group(BARREN_ISLES));
    public static final Item GOLD_VASE = new AliasedBlockItem(BlockRegistry.GOLD_VASE, new FabricItemSettings().group(BARREN_ISLES));
//    public static final Item QUICKSAND_BUCKET = new BucketItem(FluidRegistry.QUICKSAND, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(BARREN_ISLES));

    public static Identifier BARREN_NIGHT_ID = new Identifier("barrenisles:barren_night");
    public static SoundEvent BARREN_NIGHT_SOUND_EVENT = new SoundEvent(BARREN_NIGHT_ID);
    public static final MusicDiscItem BARREN_NIGHT_DISC = new DesertDiscItem(20,BARREN_NIGHT_SOUND_EVENT,new Item.Settings().maxCount(1).group(BARREN_ISLES).rarity(Rarity.RARE));


    public static void register() {
        Registry.register(Registry.ITEM, new Identifier(ModID, "dune_feather"), DUNE_FEATHER);
        Registry.register(Registry.ITEM, new Identifier(ModID, "drumstick"), DRUMSTICK);
        Registry.register(Registry.ITEM, new Identifier(ModID, "cooked_drumstick"), COOKED_DRUMSTICK);
        Registry.register(Registry.ITEM, new Identifier(ModID, "duneraptor_spawn_egg"), new SpawnEggItem(EntityRegistry.DUNERAPTOR,
                0xd5671d, 0xd5951d, new Item.Settings().group(BARREN_ISLES)));
        Registry.register(Registry.ITEM, new Identifier(ModID, "coyote_spawn_egg"), new SpawnEggItem(EntityRegistry.COYOTE,
                0x40372f, 0xd0ae9c, new Item.Settings().group(BARREN_ISLES)));
        Registry.register(Registry.ITEM, new Identifier(ModID, "coconut"), COCONUT);
        Registry.register(Registry.ITEM, new Identifier(ModID, "suspicious_berries"), SUSPICIOUS_BERRIES);
        Registry.register(Registry.ITEM, new Identifier(ModID, "vase"), VASE);
        Registry.register(Registry.ITEM, new Identifier(ModID, "gold_vase"), GOLD_VASE);
//        Registry.register(Registry.ITEM, new Identifier(ModID, "quicksand_bucket"), QUICKSAND_BUCKET);
        Registry.register(Registry.ITEM, new Identifier(ModID, "music_disc_barren_night"), BARREN_NIGHT_DISC);
    }
}
