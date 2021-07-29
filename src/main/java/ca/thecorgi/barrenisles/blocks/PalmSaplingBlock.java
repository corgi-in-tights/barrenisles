package ca.thecorgi.barrenisles.blocks;

import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;

public class PalmSaplingBlock extends SaplingBlock {
    public PalmSaplingBlock(SaplingGenerator generator, Settings settings) {
        super(generator, settings);
    }


}
//    public PalmSaplingBlock(PalmSaplingGenerator block$Settings_1, FabricBlockSettings name) {
//        super(block$Settings_1);
//        Registry.register(Registry.BLOCK, new Identifier(BarrenIsles.ModID, name), this);
//        Registry.register(Registry.ITEM, new Identifier(BarrenIsles.ModID, name), new BlockItem(this, new Item.Settings().group(GroupRegistry.BARREN_ISLES)));
//    }
//}