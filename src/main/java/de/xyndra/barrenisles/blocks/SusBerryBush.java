package de.xyndra.barrenisles.blocks;

import de.xyndra.barrenisles.setup.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.PlantType;

public class SusBerryBush extends SweetBerryBushBlock {
    public SusBerryBush(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isRandomlyTicking(BlockState p_149653_1_) {
        return p_149653_1_.getValue(AGE) < 4;
    }


    @Override
    public boolean mayPlaceOn(BlockState p_200014_1_, IBlockReader p_200014_2_, BlockPos p_200014_3_) {
        return p_200014_1_.is(Blocks.SAND) || p_200014_1_.is(Blocks.RED_SAND) ||p_200014_1_.is(Blocks.DIRT)||p_200014_1_.is(Blocks.COARSE_DIRT)||p_200014_1_.is(Blocks.GRASS_BLOCK)||p_200014_1_.is(Blocks.GRAVEL);
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.DESERT;
    }

    public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        int i = p_225533_1_.getValue(AGE);
        boolean flag = i == 3;
        if (!flag && p_225533_4_.getItemInHand(p_225533_5_).getItem() == Items.BONE_MEAL) {
            return ActionResultType.PASS;
        } else if (i > 1) {
            int j = 1 + p_225533_2_.random.nextInt(2);
            popResource(p_225533_2_, p_225533_3_, new ItemStack(ModItems.sus_berry, j + (flag ? 1 : 0)));
            p_225533_2_.playSound((PlayerEntity)null, p_225533_3_, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + p_225533_2_.random.nextFloat() * 0.4F);
            p_225533_2_.setBlock(p_225533_3_, p_225533_1_.setValue(AGE, Integer.valueOf(1)), 2);
            return ActionResultType.sidedSuccess(p_225533_2_.isClientSide);
        } else {
            return super.use(p_225533_1_, p_225533_2_, p_225533_3_, p_225533_4_, p_225533_5_, p_225533_6_);
        }
    }

    @Override
    public ItemStack getCloneItemStack(IBlockReader p_185473_1_, BlockPos p_185473_2_, BlockState p_185473_3_) {
        return new ItemStack(ModItems.sus_berry);
    }

}
