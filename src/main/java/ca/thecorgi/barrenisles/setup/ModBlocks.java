package ca.thecorgi.barrenisles.setup;

import net.minecraft.block.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;


public class ModBlocks {
    public static final Logger LOGGER = LogManager.getLogger();

    public static final WoodButtonBlock PALMBUTTON = new WoodButtonBlock(Block.Properties.copy(Blocks.OAK_BUTTON)) {};
    public static final SlabBlock PALMSLAB = new SlabBlock(Block.Properties.copy(Blocks.OAK_SLAB)) {};
    public static final FenceGateBlock PALMFENCEGATE = new FenceGateBlock(Block.Properties.copy(Blocks.OAK_FENCE_GATE)) {};
    public static final FenceBlock PALMFENCE = new FenceBlock(Block.Properties.copy(Blocks.OAK_FENCE)) {};
    public static final StairsBlock PALMSTAIRS = new StairsBlock(Blocks.OAK_STAIRS.defaultBlockState(), Block.Properties.copy(Blocks.OAK_STAIRS)) {};
    public static final PressurePlateBlock PALMPRESSUREPLATE = new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.copy(Blocks.OAK_BUTTON)) {};
    public static final DoorBlock PALMDOOR = new DoorBlock(Block.Properties.copy(Blocks.OAK_DOOR).noOcclusion()) {};
    public static final RotatedPillarBlock PALMLOG = new RotatedPillarBlock(Block.Properties.copy(Blocks.OAK_LOG)) {
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
    public static final Block PALMWOOD = new Block(Block.Properties.copy(Blocks.OAK_PLANKS)) {
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
    public static final RotatedPillarBlock STRIPPEDPALMLOG = new RotatedPillarBlock(Block.Properties.copy(Blocks.OAK_LOG)) {};
    public static final Block STRIPPEDPALMWOOD = new Block(Block.Properties.copy(Blocks.OAK_PLANKS)) {};
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
    public static final RegistryObject<Block> PALMPRESSUREPLATEREGISTRY = Registration.BLOCKS.register("palm_pressure_plate", () -> PALMPRESSUREPLATE);

    public static void register() {
        LOGGER.debug("HELLO FROM ModBlocks registery METHOD");
    };

    public static void transparency() {
        RenderTypeLookup.setRenderLayer(PALMDOOR, RenderType.translucent());
        RenderTypeLookup.setRenderLayer(PALMTRAPDOOR, RenderType.translucent());
    }
}
