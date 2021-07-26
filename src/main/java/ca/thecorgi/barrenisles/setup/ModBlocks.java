package ca.thecorgi.barrenisles.setup;

import net.minecraft.block.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ModBlocks {
    public static final Logger LOGGER = LogManager.getLogger();

    public static final DoorBlock PALMDOOR = new DoorBlock(Block.Properties.copy(Blocks.OAK_DOOR).noOcclusion()) {};
    public static final RotatedPillarBlock PALMLOG = new RotatedPillarBlock(Block.Properties.copy(Blocks.OAK_LOG)) {};
    public static final Block PALMPLANKS = new Block(Block.Properties.copy(Blocks.OAK_PLANKS)) {};
    public static final TrapDoorBlock PALMTRAPDOOR = new TrapDoorBlock(Block.Properties.copy(Blocks.OAK_TRAPDOOR).noOcclusion()) {};
    public static final RegistryObject<Block> PALMDOORREGISTRY = Registration.BLOCKS.register("palm_door", () -> PALMDOOR);
    public static final RegistryObject<Block> PALMLOGREGISTRY = Registration.BLOCKS.register("palm_log", () -> PALMLOG);
    public static final RegistryObject<Block> PALMPLANKSREGISTRY = Registration.BLOCKS.register("palm_planks", () -> PALMPLANKS);
    public static final RegistryObject<Block> PALMTRAPDOORREGISTRY = Registration.BLOCKS.register("palm_trapdoor", () -> PALMTRAPDOOR);

    public static void register() {
        LOGGER.debug("HELLO FROM ModBlocks registery METHOD");
    };

    public static void transparency() {
        RenderTypeLookup.setRenderLayer(PALMDOOR, RenderType.translucent());
        RenderTypeLookup.setRenderLayer(PALMTRAPDOOR, RenderType.translucent());
    }
}
