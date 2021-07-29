package ca.thecorgi.barrenisles.feature.tree;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import static ca.thecorgi.barrenisles.BarrenIsles.PALM_TREE_PLACER;

public class PalmTrunkPlacer extends TrunkPlacer {
    public static final Codec<PalmTrunkPlacer> CODEC = RecordCodecBuilder.create(instance ->
            fillTrunkPlacerFields(instance).apply(instance, PalmTrunkPlacer::new));

    public PalmTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return PALM_TREE_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        int bendHeight = height / 2;

        this.setToDirt(world, replacer, random, startPos.down(), config);

        this.getAndSetState(world, replacer, random, startPos.east(), config);
        this.getAndSetState(world, replacer, random, startPos.west(), config);
        this.getAndSetState(world, replacer, random, startPos.west().up(), config);
        this.getAndSetState(world, replacer, random, startPos.north(), config);
        this.getAndSetState(world, replacer, random, startPos.south(), config);

        for (int i = 0; i < bendHeight; i++) {
            this.getAndSetState(world, replacer, random, startPos.up(i), config);
        }

        for (int i = bendHeight; i < height; i++) {
            this.getAndSetState(world, replacer, random, startPos.up(i).east(), config);
        }

        return ImmutableList.of(new FoliagePlacer.TreeNode(startPos.up(height).east(), 0, false));
//                new FoliagePlacer.TreeNode(startPos.east().north().up(height), 0, false))
    }
}
