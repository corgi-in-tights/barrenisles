package barrenisles.common.util.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.TreeFeature;

public class FeatureUtil {

    public static boolean goesBeyondWorldSize(final ISeedReader world, final int y, final int height) {
        return y < 1 || y + height + 1 > world.getMaxBuildHeight();
    }

    public static boolean isBBAvailable(final ISeedReader world, final BlockPos pos, final int height) {
        for (int y = 0; y <= 1 + height; y++) {
            BlockPos checkPos = pos.above(y);
            int size = 1;
            if (checkPos.getY() < 0 || checkPos.getY() >= world.getMaxBuildHeight()) {
                return false;
            }

            if (y == 0) {
                size = 0;
            }

            if (y >= (1 + height) - 2) {
                size = 2;
            }
            Iterable<BlockPos> posit = BlockPos.betweenClosed(checkPos.offset(-size, 0, -size), checkPos.offset(size, 0, size));

            boolean a = true;
            for (BlockPos posx : posit){
                if(!TreeFeature.isAirOrLeaves(world, posx)) {
                    a = false;
                }
            }
            if (!a) {
                return false;
            }
        }

        return true;
    }

    protected static boolean isSoil(final IWorld world, final BlockPos pos) {
        final BlockState blockState = world.getBlockState(pos);
        final Block block = blockState.getBlock();
        return block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.GRASS_BLOCK || block == Blocks.PODZOL;
    }
}