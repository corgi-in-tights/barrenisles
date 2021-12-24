package barrenisles.api;

import barrenisles.core.BarrenIslesMod;
import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

public class BarrenIslesBlockTags {
	
	// Plants
	
	public static final ITag.INamedTag<Block> agave = register("agave");
	public static final ITag.INamedTag<Block> barrel_cactus = register("barrel_cactus");
	public static final ITag.INamedTag<Block> desert_lily = register("desert_lily");
	public static final ITag.INamedTag<Block> marigold = register("marigold");
	public static final ITag.INamedTag<Block> poison_ivy = register("poison_ivy");
	public static final ITag.INamedTag<Block> suspicious_berry_bush = register("suspicous_berry_bush");
	public static final ITag.INamedTag<Block> thornweed = register("thornweed");
	public static final ITag.INamedTag<Block> winecup = register("winecup");;
	
	// Misc
	
	public static final ITag.INamedTag<Block> coconut = register("coconut");
	public static final ITag.INamedTag<Block> gold_vase = register("gold_vase");
	public static final ITag.INamedTag<Block> quick_sand = register("quick_sand");
	public static final ITag.INamedTag<Block> vase = register("vase");
	
	// Palm
	
	public static final ITag.INamedTag<Block> palm_log = register("palm_log");
	public static final ITag.INamedTag<Block> palm_door = register("palm_door");
	public static final ITag.INamedTag<Block> palm_leaves = register("palm_leaves");
	public static final ITag.INamedTag<Block> palm_planks = register("palm_planks");
	public static final ITag.INamedTag<Block> palm_sapling = register("palm_sapling");
	public static final ITag.INamedTag<Block> palm_trapdoor = register("palm_trapdoor");
	public static final ITag.INamedTag<Block> stripped_palm_log= register("stripped_palm_log");
	
	private static ITag.INamedTag<Block> register(String name)
	{
		return BlockTags.createOptional(new ResourceLocation(BarrenIslesMod.MODID, name));
	}
	
}