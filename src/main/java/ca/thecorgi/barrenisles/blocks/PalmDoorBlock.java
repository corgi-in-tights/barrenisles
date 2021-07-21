package ca.thecorgi.barrenisles.blocks;

import ca.thecorgi.barrenisles.BarrenIsles;
import ca.thecorgi.barrenisles.utils.registry.GroupRegistry;
import net.minecraft.block.DoorBlock;
import net.minecraft.item.Item;
import net.minecraft.item.TallBlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PalmDoorBlock extends DoorBlock {
    public PalmDoorBlock(Settings block$Settings_1, String name) {
        super(block$Settings_1);
        Registry.register(Registry.BLOCK, new Identifier(BarrenIsles.ModID, name), this);
        Registry.register(Registry.ITEM, new Identifier(BarrenIsles.ModID, name), new TallBlockItem(this, new Item.Settings().group(GroupRegistry.MAIN_GROUP)));
    }
}
