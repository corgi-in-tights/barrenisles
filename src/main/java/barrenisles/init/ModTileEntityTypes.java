package barrenisles.init;

import static barrenisles.api.tileentities.BarrenIslesTileEntityType.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import barrenisles.api.blocks.BarrenIslesBlocks;
import barrenisles.common.tileentity.GoldVaseTileEntity;
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
		gold_vase_tileentity = TILE_ENTITIES.register("gold_vase",
				() -> new TileEntityType<>(GoldVaseTileEntity::new, createBlockSet(BarrenIslesBlocks.gold_vase), null));
		
		vase_tileentity = TILE_ENTITIES.register("vase",
				() -> new TileEntityType<>(VaseTileEntity::new, createBlockSet(BarrenIslesBlocks.vase), null));
	}
	
	public static Set<Block> createBlockSet(RegistryObject<? extends Block> block) {
	    Set<Block> blocks = Sets.newHashSet();
	    blocks.add(block.get());
	    return blocks;
	  }
	
	public static List<Block> createBlockList(Set<Block> blocks) {
		return new ArrayList<>(blocks);
	}
	
	public static void register(IEventBus bus) {
		TILE_ENTITIES.register(bus);
	}
}
