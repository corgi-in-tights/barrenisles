package de.xyndra.barrenisles.setup;

import de.xyndra.barrenisles.blocks.DesertFlowerBlock;
import de.xyndra.barrenisles.PalmTree;
import de.xyndra.barrenisles.blocks.SusBerryBush;
import de.xyndra.barrenisles.blocks.TallDesertFlowerBlock;
import de.xyndra.barrenisles.blocks.Thornweed;
import net.minecraft.block.*;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;
import javax.annotation.Nullable;


public class ModBlocks {
    public static final WoodButtonBlock PALMBUTTON = new WoodButtonBlock(Block.Properties.copy(Blocks.OAK_BUTTON)) {};
    public static final SlabBlock PALMSLAB = new SlabBlock(Block.Properties.copy(Blocks.OAK_SLAB)) {};
    public static final SaplingBlock PALMSAPLING = new SaplingBlock(new PalmTree(),Block.Properties.copy(Blocks.OAK_SAPLING)) {};
    public static final FenceGateBlock PALMFENCEGATE = new FenceGateBlock(Block.Properties.copy(Blocks.OAK_FENCE_GATE)) {};
    public static final FenceBlock PALMFENCE = new FenceBlock(Block.Properties.copy(Blocks.OAK_FENCE)) {};
    public static final StairsBlock PALMSTAIRS = new StairsBlock(Blocks.OAK_STAIRS.defaultBlockState(), Block.Properties.copy(Blocks.OAK_STAIRS)) {};
    public static final PressurePlateBlock PALMPRESSUREPLATE = new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.copy(Blocks.OAK_BUTTON)) {};
    public static final DoorBlock PALMDOOR = new DoorBlock(Block.Properties.copy(Blocks.OAK_DOOR).noOcclusion()) {};
    public static final Block PALMLOG = new RotatedPillarBlock(Block.Properties.copy(Blocks.OAK_LOG)) {
        @Override
        @Nullable
        public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
            if(toolType == ToolType.AXE) {
                player.playSound(SoundEvents.AXE_STRIP, 1, 1);
                return STRIPPEDPALMLOG.defaultBlockState();
            } else{
                return null;
            }
        }
    };
    public static final Block PALMPLANKS = new Block(Block.Properties.copy(Blocks.OAK_PLANKS)) {};
    public static final Block PALMWOOD = new Block(Block.Properties.copy(Blocks.OAK_WOOD)) {
        @Override
        @Nullable
        public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
            if(toolType == ToolType.AXE) {
                player.playSound(SoundEvents.AXE_STRIP, 1, 1);
                return STRIPPEDPALMWOOD.defaultBlockState();
            } else{
                return null;
            }
        }
    };
    public static final RotatedPillarBlock STRIPPEDPALMLOG = new RotatedPillarBlock(Block.Properties.copy(Blocks.STRIPPED_OAK_LOG)) {};
    public static final Block STRIPPEDPALMWOOD = new Block(Block.Properties.copy(Blocks.STRIPPED_OAK_WOOD)) {};
    public static final CocoaBlock COCONUT = new CocoaBlock(Block.Properties.copy(Blocks.COCOA)) {
        public VoxelShape[] EAST_AABB = new VoxelShape[]{Block.box(10.0D, 7.0D, 6.0D, 14.0D, 11.0D, 10.0D), Block.box(8.0D, 5.0D, 5.0D, 14.0D, 11.0D, 11.0D), Block.box(6.0D, 4.0D, 4.0D, 14.0D, 12.0D, 12.0D)};
        public VoxelShape[] WEST_AABB = new VoxelShape[]{Block.box(2.0D, 7.0D, 6.0D, 6.0D, 11.0D, 10.0D), Block.box(2.0D, 5.0D, 5.0D, 8.0D, 11.0D, 11.0D), Block.box(2.0D, 4.0D, 4.0D, 10.0D, 12.0D, 12.0D)};
        public VoxelShape[] NORTH_AABB = new VoxelShape[]{Block.box(6.0D, 7.0D, 2.0D, 10.0D, 11.0D, 6.0D), Block.box(5.0D, 5.0D, 2.0D, 11.0D, 11.0D, 8.0D), Block.box(4.0D, 4.0D, 2.0D, 12.0D, 12.0D, 10.0D)};
        public VoxelShape[] SOUTH_AABB = new VoxelShape[]{Block.box(6.0D, 7.0D, 10.0D, 10.0D, 11.0D, 14.0D), Block.box(5.0D, 5.0D, 8.0D, 11.0D, 11.0D, 14.0D), Block.box(4.0D, 4.0D, 6.0D, 12.0D, 12.0D, 14.0D)};
        @Override
        public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
            Block block = world.getBlockState(pos.relative(state.getValue(FACING))).getBlock();
            return block.is(PALMLOG) || block.is(STRIPPEDPALMLOG) || block.is(PALMWOOD) || block.is(STRIPPEDPALMWOOD);
        }
        @Override
        public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
            int i = p_220053_1_.getValue(AGE);
            switch((Direction)p_220053_1_.getValue(FACING)) {
                case SOUTH:
                    return SOUTH_AABB[i];
                case NORTH:
                default:
                    return NORTH_AABB[i];
                case WEST:
                    return WEST_AABB[i];
                case EAST:
                    return EAST_AABB[i];
            }
        }
    };
    public static final Block WINECUP = new DesertFlowerBlock(Effects.DIG_SPEED, 30, Block.box(0.5D,0.0D,0.5D,14.5D,15.0D,14.5D),Block.Properties.copy(Blocks.CACTUS));
    public static final Block POISON_IVY = new DesertFlowerBlock(Effects.POISON, 50, Block.box(0.0D,0.0D,0.0D, 15.0D,15.0D,15.0D),Block.Properties.copy(Blocks.CACTUS));
    public static final Block AGAVE = new DesertFlowerBlock(Effects.WITHER, 70, Block.box(0.0D,0.0D,0.0D,15.0D,15.0D,15.0D),Block.Properties.copy(Blocks.CACTUS));
    public static final Block MARIGOLD = new DesertFlowerBlock(Effects.MOVEMENT_SPEED, 40, Block.box(3.0D,0.0D,3.2D,14.0D,15.0D,14.2D),Block.Properties.copy(Blocks.CACTUS));
    public static final Block DESERT_LILY = new TallDesertFlowerBlock(Block.Properties.copy(Blocks.ROSE_BUSH));
    public static final Block THORNWEED = new Thornweed(Block.Properties.copy(Blocks.SWEET_BERRY_BUSH));
    public static final Block BARRELCACTUS = new Thornweed(Block.Properties.copy(Blocks.SWEET_BERRY_BUSH));
    public static final Block SUS_BERRY_BUSH = new SusBerryBush(Block.Properties.copy(Blocks.SWEET_BERRY_BUSH));
    public static final TrapDoorBlock PALMTRAPDOOR = new TrapDoorBlock(Block.Properties.copy(Blocks.OAK_TRAPDOOR).noOcclusion()) {};
    public static final RegistryObject<Block> PALMDOORREGISTRY = Registration.BLOCKS.register("palm_door", () -> PALMDOOR);
    public static final RegistryObject<Block> PALMLOGREGISTRY = Registration.BLOCKS.register("palm_log", () -> PALMLOG);
    public static final RegistryObject<Block> PALMPLANKSREGISTRY = Registration.BLOCKS.register("palm_planks", () -> PALMPLANKS);
    public static final RegistryObject<Block> PALMTRAPDOORREGISTRY = Registration.BLOCKS.register("palm_trapdoor", () -> PALMTRAPDOOR);
    public static final RegistryObject<Block> PALMBUTTONREGISTRY = Registration.BLOCKS.register("palm_button", () -> PALMBUTTON);
    public static final RegistryObject<Block> A = Registration.BLOCKS.register("palm_slab", () -> PALMSLAB);
    public static final RegistryObject<Block> B = Registration.BLOCKS.register("palm_stairs", () -> PALMSTAIRS);
    public static final RegistryObject<Block> C = Registration.BLOCKS.register("palm_fence_gate", () -> PALMFENCEGATE);
    public static final RegistryObject<Block> D = Registration.BLOCKS.register("palm_fence", () -> PALMFENCE);
    public static final RegistryObject<Block> E = Registration.BLOCKS.register("palm_wood", () -> PALMWOOD);
    public static final RegistryObject<Block> F = Registration.BLOCKS.register("stripped_palm_log", () -> STRIPPEDPALMLOG);
    public static final RegistryObject<Block> G = Registration.BLOCKS.register("stripped_palm_wood", () -> STRIPPEDPALMWOOD);
    public static final RegistryObject<Block> H = Registration.BLOCKS.register("palm_leaves", () -> new LeavesBlock(Block.Properties.copy(Blocks.OAK_LEAVES).noOcclusion().harvestTool(ToolType.HOE)));
    public static final RegistryObject<Block> I = Registration.BLOCKS.register("coconut", () -> COCONUT);
    public static final RegistryObject<Block> J = Registration.BLOCKS.register("palm_sapling", () -> PALMSAPLING);
    public static final RegistryObject<Block> K = Registration.BLOCKS.register("winecup", () -> WINECUP);
    public static final RegistryObject<Block> L = Registration.BLOCKS.register("poison_ivy", () -> POISON_IVY);
    public static final RegistryObject<Block> M = Registration.BLOCKS.register("agave", () -> AGAVE);
    public static final RegistryObject<Block> N = Registration.BLOCKS.register("marigold", () -> MARIGOLD);
    public static final RegistryObject<Block> O = Registration.BLOCKS.register("desert_lily", () -> DESERT_LILY);
    public static final RegistryObject<Block> P = Registration.BLOCKS.register("thornweed", () -> THORNWEED);
    public static final RegistryObject<Block> R = Registration.BLOCKS.register("barrel_cactus", () -> BARRELCACTUS);
    public static final RegistryObject<Block> Q = Registration.BLOCKS.register("suspicious_berry_bush", () -> SUS_BERRY_BUSH);
    public static final RegistryObject<Block> PALMPRESSUREPLATEREGISTRY = Registration.BLOCKS.register("palm_pressure_plate", () -> PALMPRESSUREPLATE);

    public static void register() {};

    public static void transparency() {
        RenderTypeLookup.setRenderLayer(PALMDOOR, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(PALMTRAPDOOR, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(WINECUP, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(POISON_IVY, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AGAVE, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MARIGOLD, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(DESERT_LILY, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(THORNWEED, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BARRELCACTUS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SUS_BERRY_BUSH, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(PALMSAPLING, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(H.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(COCONUT, RenderType.cutout());

    }
}
