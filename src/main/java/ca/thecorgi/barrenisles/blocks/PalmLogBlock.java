//package ca.thecorgi.barrenisles.blocks;
//
//import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.Hand;
//import net.minecraft.util.hit.BlockHitResult;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//
//public class PalmLogBlock extends Block {
//    public PalmLogBlock(Settings settings) {
//        super(settings);
//    }
//
//    @Override
//    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//        ItemStack stack = player.getMainHandStack();
//        if (stack == null){
//            return super.onUse(state, world, pos, player, hand, hit);
//        }
//        if (FabricToolTags.AXES.contains(stack.getItem())) {
//            System.out.println("log stripped");
//        } else {
//            // player not hold an axe
//        }
//        return super.onUse(state, world, pos, player, hand, hit);
//    }
//}
