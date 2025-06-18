package net.bigger212.biggs_apple_trees.world.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.bigger212.biggs_apple_trees.block.FruitLeavesBlock;
import net.bigger212.biggs_apple_trees.world.gen.ModFoliagePlacerTypes;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class AppleBlobFoliagePlacer extends FoliagePlacer {
    public static final Codec<AppleBlobFoliagePlacer> CODEC = RecordCodecBuilder.create(chestnutFoliagePlacerInstance ->
            fillFoliagePlacerFields(chestnutFoliagePlacerInstance).and(Codec.intRange(0, 12).fieldOf("height")
                    .forGetter(instance -> instance.height)).apply(chestnutFoliagePlacerInstance, AppleBlobFoliagePlacer::new));
    private final int height;

    public AppleBlobFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
        super(radius, offset);
        this.height = height;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return ModFoliagePlacerTypes.APPLE_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config,
                            int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {
        BlockPos center = treeNode.getCenter();

        for (int y = offset; y >= offset - foliageHeight; --y) {

            int layerRadius = Math.max(radius + treeNode.getFoliageRadius() - 1 - y / 2, 0);

            for (int dx = -layerRadius; dx <= layerRadius; dx++) {
                for (int dz = -layerRadius; dz <= layerRadius; dz++) {

                    BlockPos pos = center.add(dx, y, dz);

                    if (isReplaceable(world, pos)) continue;

                    int distance = Math.max(Math.abs(dx), Math.abs(dz));
                    int age = (distance < 2) ? 0 : random.nextInt(2); // 0 = no bloom, 1 = bloom
                    BlockState base = config.foliageProvider.get(random, pos);
                    BlockState state = base.with(FruitLeavesBlock.AGE, age);
                    if (state.contains(Properties.WATERLOGGED)) {

                        state = state.with(Properties.WATERLOGGED, false);

                    }

                    placer.placeBlock(pos, state);
                }
            }
        }
    }

    private boolean isReplaceable(TestableWorld world, BlockPos pos) { // PHEW..!
        return !world.testBlockState(pos, state ->
                state.isAir() || state.isReplaceable());
    }


    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return this.height;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return false;
    }
}