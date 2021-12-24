package barrenisles.init;

import static barrenisles.api.BarrenIslesTileEntityType.*;

import java.util.Set;

import barrenisles.common.tileentity.GoldVaseTileEntity;
import com.google.common.collect.Sets;

import barrenisles.api.BarrenIslesBlocks;
import barrenisles.common.tileentity.VaseTileEntity;
import barrenisles.core.BarrenIslesMod;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BarrenIslesMod.MODID);

	static {
		vase_tile_entity = TILE_ENTITIES.register("vase",
				() -> new TileEntityType<>(VaseTileEntity::new, createBlockSet(BarrenIslesBlocks.vase), null));

		gold_vase_tile_entity = TILE_ENTITIES.register("gold_vase",
				() -> new TileEntityType<>(GoldVaseTileEntity::new, createBlockSet(BarrenIslesBlocks.gold_vase), null));
	}
	
	public static Set<Block> createBlockSet(RegistryObject<Block> block) {
	    Set<Block> blocks = Sets.newHashSet();
	    blocks.add(block.get());
	    return blocks;
	  }
	
	public static void register(IEventBus bus) {
		TILE_ENTITIES.register(bus);
	}
}
