package net.bigger212.biggs_apple_trees.world.gen;

import net.bigger212.biggs_apple_trees.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class ModTreeGeneration {

    public static void generateTrees() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(
                        BiomeKeys.FLOWER_FOREST,
                        BiomeKeys.SPARSE_JUNGLE),
                GenerationStep.Feature.VEGETAL_DECORATION,
                ModPlacedFeatures.RARE_APPLE_TREE_PLACED_KEY
        );

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(
                        BiomeKeys.PLAINS,
                        BiomeKeys.MEADOW),
                GenerationStep.Feature.VEGETAL_DECORATION,
                ModPlacedFeatures.VERY_RARE_APPLE_TREE_PLACED_KEY
        );
    }
}
