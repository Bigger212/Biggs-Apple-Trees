package net.bigger212.biggs_apple_trees.world;

import net.bigger212.biggs_apple_trees.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

import static net.bigger212.biggs_apple_trees.BiggsAppleTrees.*;

public class ModPlacedFeatures {

    public static final RegistryKey<PlacedFeature> APPLE_TREE_PLACED = registerKey("apple_tree_placed");
    public static final RegistryKey<PlacedFeature> FANCY_APPLE_TREE_PLACED = registerKey("fancy_apple_tree_placed");
    public static final RegistryKey<PlacedFeature> FLOWER_FOREST_APPLE_TREE_PLACED = registerKey("flower_forest_apple_tree_placed");
    public static final RegistryKey<PlacedFeature> FLOWER_FOREST_FANCY_APPLE_TREE_PLACED = registerKey("flower_forest_fancy_apple_tree_placed");
    public static final RegistryKey<PlacedFeature> SPARSE_JUNGLE_APPLE_TREE_PLACED = registerKey("sparse_jungle_apple_tree_placed");

////////////////////////////////////////////////// Registration ////////////////////////////////////////////////////////

    public static void boostrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, APPLE_TREE_PLACED, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.APPLE_BEES_005),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.005f, 2),
                        ModBlocks.APPLE_SAPLING));

        register(context, FLOWER_FOREST_APPLE_TREE_PLACED, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.APPLE_BEES_002),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.05f, 2),
                        ModBlocks.APPLE_SAPLING));

        register(context, FLOWER_FOREST_FANCY_APPLE_TREE_PLACED, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.FANCY_APPLE_BEES_002),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.01f, 1),
                        ModBlocks.APPLE_SAPLING));

        register(context, SPARSE_JUNGLE_APPLE_TREE_PLACED, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.JUNGLE_APPLE),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.1f, 2),
                        ModBlocks.APPLE_SAPLING));
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context,
                                 RegistryKey<PlacedFeature> key,
                                 RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}