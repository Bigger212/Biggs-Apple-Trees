package net.bigger212.biggs_apple_trees.world;

import net.bigger212.biggs_apple_trees.block.FruitLeavesBlock;
import net.bigger212.biggs_apple_trees.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.LeavesVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;
import java.util.OptionalInt;

import static net.bigger212.biggs_apple_trees.BiggsAppleTrees.*;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> APPLE_SAPLING = registerKey("apple_sapling");
    public static final RegistryKey<ConfiguredFeature<?, ?>> APPLE_BEES_002 = registerKey("apple_bees_002");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FANCY_APPLE_BEES_002 = registerKey("fancy_apple_bees_002");
    public static final RegistryKey<ConfiguredFeature<?, ?>> APPLE_BEES_005 = registerKey("apple_bees_005");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FANCY_APPLE_BEES_005 = registerKey("fancy_apple_bees_005");
    public static final RegistryKey<ConfiguredFeature<?, ?>> JUNGLE_APPLE = registerKey("jungle_apple");

////////////////////////////////////////////////// Registration ////////////////////////////////////////////////////////

    public static void boostrap(Registerable<ConfiguredFeature<?, ?>> context) {
        // Apple Trees
        register(context, APPLE_SAPLING, Feature.TREE, new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(ModBlocks.APPLE_LOG),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.of(ModBlocks.APPLE_LEAVES.getDefaultState().with(FruitLeavesBlock.AGE, 0)),
                        new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)
                )
                        .ignoreVines()
                        .build()
        );
        register(context, APPLE_BEES_002, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.APPLE_LOG),
                new StraightTrunkPlacer(4, 2, 0),
                BlockStateProvider.of(ModBlocks.APPLE_LEAVES.getDefaultState().with(FruitLeavesBlock.AGE, 1)),
                new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)
        )
                .decorators(List.of(new BeehiveTreeDecorator(0.02f)))
                .ignoreVines()
                .build()
        );
        register(context, FANCY_APPLE_BEES_002, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.APPLE_LOG),
                new LargeOakTrunkPlacer(3, 11, 0),
                BlockStateProvider.of(ModBlocks.APPLE_LEAVES.getDefaultState().with(FruitLeavesBlock.AGE, 1)),
                new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(4), 4),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
                )
                .decorators(List.of(new BeehiveTreeDecorator(0.02f)))
                .ignoreVines()
                .build()
        );
        register(context, APPLE_BEES_005, Feature.TREE, new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(ModBlocks.APPLE_LOG),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.of(ModBlocks.APPLE_LEAVES.getDefaultState().with(FruitLeavesBlock.AGE, 1)),
                        new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)
                )
                        .decorators(List.of(new BeehiveTreeDecorator(0.05f)))
                        .ignoreVines()
                        .build()
        );
        register(context, FANCY_APPLE_BEES_005, Feature.TREE, new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(ModBlocks.APPLE_LOG),
                        new LargeOakTrunkPlacer(3, 11, 0),
                        BlockStateProvider.of(ModBlocks.APPLE_LEAVES.getDefaultState().with(FruitLeavesBlock.AGE, 1)),
                        new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(4), 4),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
                )
                        .decorators(List.of(new BeehiveTreeDecorator(0.05f)))
                        .ignoreVines()
                        .build()
        );
        register(context, JUNGLE_APPLE, Feature.TREE, new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(ModBlocks.APPLE_LOG),
                        new StraightTrunkPlacer(5, 3, 0),
                        BlockStateProvider.of(ModBlocks.APPLE_LEAVES.getDefaultState().with(FruitLeavesBlock.AGE, 1)),
                        new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)
                )
                        .decorators(List.of(TrunkVineTreeDecorator.INSTANCE,
                                new LeavesVineTreeDecorator(0.25f)))
                        .ignoreVines()
                        .build()
        );
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(
            Registerable<ConfiguredFeature<?, ?>> context,
            RegistryKey<ConfiguredFeature<?, ?>> key,
            F feature,
            FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
