package barrenisles.common.block;

import barrenisles.api.BarrenIslesBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class PalmLogBlock extends RotatedPillarBlock {
    public PalmLogBlock(Properties properties) {
        super(properties);
    }

    @Override
    @Nullable
    public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
        if(toolType == ToolType.AXE) {
            player.playSound(SoundEvents.AXE_STRIP, 1, 1);
            return BarrenIslesBlocks.stripped_palm_log.get().defaultBlockState();
        } else {
            return null;
        }
    }

}
