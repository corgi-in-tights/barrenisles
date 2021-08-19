package ca.thecorgi.barrenisles.utils.registry;

import ca.thecorgi.barrenisles.BarrenIsles;
import ca.thecorgi.barrenisles.blocks.*;
import ca.thecorgi.barrenisles.blocks.blockentities.GoldVaseBlockEntity;
import ca.thecorgi.barrenisles.blocks.blockentities.VaseBlockEntity;
import ca.thecorgi.barrenisles.feature.tree.PalmSaplingGenerator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;
import static ca.thecorgi.barrenisles.utils.registry.FeatureRegistry.TREE_PALM;
import static ca.thecorgi.barrenisles.utils.registry.GroupRegistry.BARREN_ISLES;

public class BlockRegistry {
    public static final Block PALM_LOG = new PillarBlock(copyWoodSettings(Blocks.OAK_LOG));
    public static final Block PALM_WOOD = new PillarBlock(copyWoodSettings(Blocks.OAK_WOOD));
    public static final Block STRIPPED_PALM_LOG = new PillarBlock(copyWoodSettings(Blocks.STRIPPED_OAK_LOG));
    public static final Block STRIPPED_PALM_WOOD = new PillarBlock(copyWoodSettings(Blocks.STRIPPED_OAK_WOOD));
    public static final Block PALM_PLANKS = new Block(copyWoodSettings(Blocks.OAK_PLANKS));
    public static final Block PALM_SLAB = new SlabBlock(copyWoodSettings(Blocks.OAK_SLAB));
    public static final Block PALM_STAIRS = new StairsBlock(PALM_PLANKS.getDefaultState(), copyWoodSettings(Blocks.OAK_STAIRS)) {};
    public static final Block PALM_LEAVES = new LeavesBlock(copyWoodSettings(Blocks.OAK_LEAVES));
    public static final Block PALM_DOOR = new PalmDoorBlock(copyWoodSettings(Blocks.OAK_DOOR));
    public static final Block PALM_TRAPDOOR = new PalmTrapdoorBlock(copyWoodSettings(Blocks.OAK_TRAPDOOR));
    public static final Block PALM_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, copyWoodSettings(Blocks.OAK_PRESSURE_PLATE)) {};
    public static final Block PALM_FENCE = new FenceBlock(copyWoodSettings(Blocks.OAK_FENCE));
    public static final Block PALM_FENCE_GATE = new FenceGateBlock(copyWoodSettings(Blocks.OAK_FENCE_GATE));
    public static final Block PALM_BUTTON = new WoodenButtonBlock(copyWoodSettings(Blocks.OAK_BUTTON)) {};
    public static final Block PALM_SAPLING = new SaplingBlock(new PalmSaplingGenerator(TREE_PALM), AbstractBlock.Settings.copy(Blocks.OAK_SAPLING)) {};

    public static final Block WINECUP = new DesertFlowerBlock(StatusEffects.HASTE, 30, FabricBlockSettings.copyOf(Blocks.DANDELION));
    public static final Block POISON_IVY = new PoisonIvyBlock(FabricBlockSettings.copyOf(Blocks.DANDELION));
    public static final Block AGAVE = new DesertFlowerBlock(StatusEffects.WITHER, 70, FabricBlockSettings.copyOf(Blocks.DANDELION));
    public static final Block MARIGOLD = new DesertFlowerBlock(StatusEffects.SPEED, 40, FabricBlockSettings.copyOf(Blocks.DANDELION));
    public static final Block DESERT_LILY = new TallDesertFlowerBlock(FabricBlockSettings.copyOf(Blocks.ROSE_BUSH));
    public static final Block SUSPICIOUS_BERRY_BUSH = new SuspiciousBerryBushBlock(FabricBlockSettings.copy(Blocks.SWEET_BERRY_BUSH));
    public static final Block THORNWEED = new ThornweedBlock(FabricBlockSettings.copyOf(Blocks.DEAD_BUSH));
    public static final Block BARREL_CACTUS = new ThornweedBlock(FabricBlockSettings.copyOf(Blocks.DEAD_BUSH));
    public static final Block COCONUT = new CoconutBlock(FabricBlockSettings.copyOf(Blocks.COCOA));
    public static final Block VASE = new VaseBlock(AbstractBlock.Settings.of(Material.DECORATION).strength(0.3F).nonOpaque());
    public static final Block GOLD_VASE = new GoldVaseBlock(AbstractBlock.Settings.of(Material.DECORATION).strength(0.6F).nonOpaque());
    public static final Block QUICKSAND = new QuicksandBlock(FabricBlockSettings.of(Material.POWDER_SNOW).strength(0.344F).breakByTool(FabricToolTags.SHOVELS).sounds(BlockSoundGroup.POWDER_SNOW).nonOpaque().dynamicBounds());
//    public static final Block QUICKSAND = new QuicksandBlock(AbstractBlock.Settings.of(Material.POWDER_SNOW).strength(0.4F).sounds(BlockSoundGroup.POWDER_SNOW).dynamicBounds());
    public static final BlockEntityType<VaseBlockEntity> VASE_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(VaseBlockEntity::new, VASE).build();
    public static final BlockEntityType<GoldVaseBlockEntity> GOLD_VASE_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(GoldVaseBlockEntity::new, GOLD_VASE).build();

    public static void register() {
        register("palm_log", PALM_LOG);
        register("palm_planks", PALM_PLANKS);
        register("palm_slab", PALM_SLAB);
        register("palm_stairs", PALM_STAIRS);
        register("palm_wood", PALM_WOOD);
        register("stripped_palm_log", STRIPPED_PALM_LOG);
        register("stripped_palm_wood", STRIPPED_PALM_WOOD);
        register("palm_leaves", PALM_LEAVES);
        register("palm_door", PALM_DOOR);
        register("palm_trapdoor", PALM_TRAPDOOR);
        register("palm_pressure_plate", PALM_PRESSURE_PLATE);
        register("palm_fence", PALM_FENCE);
        register("palm_fence_gate", PALM_FENCE_GATE);
        register("palm_button", PALM_BUTTON);
        register("palm_sapling", PALM_SAPLING);
        register("winecup", WINECUP);
        register("marigold", MARIGOLD);
        register("poison_ivy", POISON_IVY);
        register("agave", AGAVE);
        register("desert_lily", DESERT_LILY);
        register("suspicious_berry_bush", SUSPICIOUS_BERRY_BUSH, (Item) null);
        register("thornweed", THORNWEED);
        register("barrel_cactus", BARREL_CACTUS);
        register("coconut", COCONUT, (Item) null);
        register("vase", VASE, (Item) null);
        register("gold_vase", GOLD_VASE, (Item) null);
        register("quicksand", QUICKSAND, (Item) null);

        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(ModID, "vase_block_entity"), VASE_BLOCK_ENTITY);

        FuelRegistry fuelReg = FuelRegistry.INSTANCE;
        fuelReg.add(PALM_FENCE, 300);
        fuelReg.add(PALM_FENCE_GATE, 300);

        FlammableBlockRegistry flammableBlockReg = FlammableBlockRegistry.getDefaultInstance();
        flammableBlockReg.add(PALM_LOG, 5, 5);
        flammableBlockReg.add(PALM_WOOD, 5, 5);
        flammableBlockReg.add(STRIPPED_PALM_LOG, 5, 5);
        flammableBlockReg.add(STRIPPED_PALM_WOOD, 5, 5);
        flammableBlockReg.add(PALM_PLANKS, 5, 20);
        flammableBlockReg.add(PALM_SLAB, 5, 20);
        flammableBlockReg.add(PALM_STAIRS, 5, 20);
        flammableBlockReg.add(PALM_FENCE, 5, 20);
        flammableBlockReg.add(PALM_FENCE_GATE, 5, 20);
        flammableBlockReg.add(PALM_LEAVES, 5, 20);
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderLayer.getCutoutMipped(), PALM_LEAVES
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderLayer.getCutout(), PALM_SAPLING, PALM_DOOR, PALM_TRAPDOOR, MARIGOLD, AGAVE, POISON_IVY, THORNWEED, WINECUP, DESERT_LILY, BARREL_CACTUS, SUSPICIOUS_BERRY_BUSH, COCONUT);

        ColorProviderRegistry.BLOCK.register(
                (state, world, pos, tintIndex) -> {
                    if (world != null && pos != null) {
                        return BiomeColors.getFoliageColor(world, pos);
                    }
                    return FoliageColors.getColor(0.5, 1.0);
                }, PALM_LEAVES
        );

        ColorProviderRegistry.ITEM.register(
                (stack, tintIndex) -> {
                    if (tintIndex > 0) return -1;
                    BlockColors colors = MinecraftClient.getInstance().getBlockColors();
                    return colors.getColor(((BlockItem) stack.getItem()).getBlock().getDefaultState(), null, null, tintIndex);
                }, PALM_LEAVES
        );
    }


    private static void register(String id, Block block) {
        register(id, block, BARREN_ISLES);
    }

    private static void register(String id, Block block, ItemGroup itemGroup) {
        register(id, block, new BlockItem(block, new Item.Settings().group(itemGroup)));
    }

    private static void register(String id, Block block, @Nullable Item item) {
        Registry.register(Registry.BLOCK, BarrenIsles.id(id), block);

        if (item != null) {
            Registry.register(Registry.ITEM, BarrenIsles.id(id), item);
        }
    }

    private static AbstractBlock.Settings copyWoodSettings(Block block) {
        return FabricBlockSettings.copyOf(block).breakByTool(FabricToolTags.AXES);
    }

}
