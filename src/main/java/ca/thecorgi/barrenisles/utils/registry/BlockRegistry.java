package ca.thecorgi.barrenisles.utils.registry;

import ca.thecorgi.barrenisles.blocks.PalmDoorBlock;
import ca.thecorgi.barrenisles.blocks.PalmSaplingBlock;
import ca.thecorgi.barrenisles.feature.tree.PalmSaplingGenerator;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;
import static ca.thecorgi.barrenisles.utils.registry.FeatureRegistry.TREE_PALM;

public class BlockRegistry {
    public static final Block PALM_LOG = new Block(FabricBlockSettings.of(Material.WOOD).strength(2).sounds(BlockSoundGroup.WOOD).breakByTool(FabricToolTags.AXES));
    public static final Block PALM_WOOD = new Block(FabricBlockSettings.of(Material.WOOD).strength(2).sounds(BlockSoundGroup.WOOD).breakByTool(FabricToolTags.AXES));
    public static final Block PALM_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD).strength(2).sounds(BlockSoundGroup.WOOD).breakByTool(FabricToolTags.AXES));
    public static final Block STRIPPED_PALM_LOG = new Block(FabricBlockSettings.of(Material.WOOD).strength(2).sounds(BlockSoundGroup.WOOD).breakByTool(FabricToolTags.AXES));
    public static final Block STRIPPED_PALM_WOOD = new Block(FabricBlockSettings.of(Material.WOOD).strength(2).sounds(BlockSoundGroup.WOOD).breakByTool(FabricToolTags.AXES));
    public static final Block PALM_LEAVES = new Block(FabricBlockSettings.of(Material.LEAVES).nonOpaque().breakByTool(FabricToolTags.SHEARS).strength(0.2F));

    public static PalmDoorBlock PALM_DOOR;
//    public static PalmSaplingBlock PALM_SAPLING;
    public static final PalmSaplingBlock PALM_SAPLING = new PalmSaplingBlock(new PalmSaplingGenerator(TREE_PALM), Block.Settings.copy(Blocks.OAK_SAPLING));

    public static void register() {
        Registry.register(Registry.BLOCK, new Identifier(ModID, "palm_log"), PALM_LOG);
        Registry.register(Registry.ITEM, new Identifier(ModID, "palm_log"), new BlockItem(PALM_LOG, new FabricItemSettings().group(GroupRegistry.MAIN_GROUP)));
        Registry.register(Registry.BLOCK, new Identifier(ModID, "palm_wood"), PALM_WOOD);
        Registry.register(Registry.ITEM, new Identifier(ModID, "palm_wood"), new BlockItem(PALM_WOOD, new FabricItemSettings().group(GroupRegistry.MAIN_GROUP)));
        Registry.register(Registry.BLOCK, new Identifier(ModID, "palm_planks"), PALM_PLANKS);
        Registry.register(Registry.ITEM, new Identifier(ModID, "palm_planks"), new BlockItem(PALM_PLANKS, new FabricItemSettings().group(GroupRegistry.MAIN_GROUP)));
        Registry.register(Registry.BLOCK, new Identifier(ModID, "stripped_palm_log"), STRIPPED_PALM_LOG);
        Registry.register(Registry.ITEM, new Identifier(ModID, "stripped_palm_log"), new BlockItem(STRIPPED_PALM_LOG, new FabricItemSettings().group(GroupRegistry.MAIN_GROUP)));
        Registry.register(Registry.BLOCK, new Identifier(ModID, "stripped_palm_wood"), STRIPPED_PALM_WOOD);
        Registry.register(Registry.ITEM, new Identifier(ModID, "stripped_palm_wood"), new BlockItem(STRIPPED_PALM_WOOD, new FabricItemSettings().group(GroupRegistry.MAIN_GROUP)));
        Registry.register(Registry.BLOCK, new Identifier(ModID, "palm_leaves"), PALM_LEAVES);
        Registry.register(Registry.ITEM, new Identifier(ModID, "palm_leaves"), new BlockItem(PALM_LEAVES, new FabricItemSettings().group(GroupRegistry.MAIN_GROUP)));

        Registry.register(Registry.BLOCK, new Identifier(ModID, "palm_sapling"), PALM_SAPLING);
        Registry.register(Registry.ITEM, new Identifier(ModID, "palm_sapling"), new BlockItem(PALM_SAPLING, new FabricItemSettings().group(GroupRegistry.MAIN_GROUP)));

        PALM_DOOR = new PalmDoorBlock(Block.Settings.copy(Blocks.JUNGLE_DOOR), "palm_door");
    }
}
