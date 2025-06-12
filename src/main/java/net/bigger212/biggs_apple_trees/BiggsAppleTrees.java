package net.bigger212.biggs_apple_trees;

import net.bigger212.biggs_apple_trees.block.ModBlocks;
import net.bigger212.biggs_apple_trees.block.entity.ModBlockEntities;
import net.bigger212.biggs_apple_trees.item.ModItems;
import net.bigger212.biggs_apple_trees.world.gen.ModFoliagePlacerTypes;
import net.bigger212.biggs_apple_trees.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BiggsAppleTrees implements ModInitializer {
    public static final String MOD_ID = "biggs_apple_trees";
    public static final Logger LOGGER = LoggerFactory.getLogger("Bigg's Apple Trees");

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing...");
        Config.registerConfigs();
        ModBlocks.registerModBlocks();
        ModItems.registerModItems();
        ModBlockEntities.registerModBlockEntities();
        ModFoliagePlacerTypes.register();

        StrippableBlockRegistry.register(ModBlocks.APPLE_LOG, ModBlocks.STRIPPED_APPLE_LOG);
        StrippableBlockRegistry.register(ModBlocks.APPLE_WOOD, ModBlocks.STRIPPED_APPLE_WOOD);

        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.APPLE_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.APPLE_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_APPLE_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_APPLE_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.APPLE_PLANKS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.APPLE_LEAVES, 30, 60);

        ModWorldGeneration.generateModWorldGen();
        // TODO Serene Seasons compat.
        // TODO Falling Petals visuals.
        // TODO Rotten apple.
        // TODO Visual apple ripening.
        // TODO New tree shapes.
        // TODO Immersive Weathering Dynamic flowering leaf_piles.
        // TODO Complete fruit planks type.
    }

    public static class Config {
        private static final File CONFIG_FILE = new File("config/biggs_apple_trees.properties");
        private static final java.util.Properties CONFIG = new java.util.Properties();

        public static int BLOOM_CHANCE = 21;
        public static int GROW_CHANCE = 19;

        public static void registerConfigs() {
            if (!CONFIG_FILE.exists()) {
                try {
                    CONFIG_FILE.getParentFile().mkdirs();
                    try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                        writer.write("# biggs_apple_trees Config\n");
                        writer.write("# Configuration for fruit growth.\n");
                        writer.write("#\n");

                        writer.write("# Default values yield roughly ~18 apples per tree every 30 minutes.\n");
                        writer.write("# A lower number means more likely; higher numbers take longer.\n");
                        writer.write("# Leaves have 3 stages: age 0 (leaves), age 1 (flower), age 2 (fruit).#\n");

                        writer.write("# Chance that apple leaves bloom.\n");
                        writer.write("# Default: 21\n");
                        writer.write("BLOOM_CHANCE=" + BLOOM_CHANCE + "\n");
                        writer.write("#\n");

                        writer.write("# Chance that bloomed apple leaves produce an apple.\n");
                        writer.write("# Default: 19\n");
                        writer.write("GROW_CHANCE=" + GROW_CHANCE + "\n");
                        writer.write("#\n");
                    }
                } catch (IOException e) {

                    System.err.println("Failed to create biggs_apple_trees config: " + e);

                }
            }
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                CONFIG.load(reader);

                BLOOM_CHANCE = Integer.parseInt(CONFIG.getProperty("BLOOM_CHANCE", "21"));
                LOGGER.info("BLOOM_CHANCE={}", BLOOM_CHANCE);

                GROW_CHANCE = Integer.parseInt(CONFIG.getProperty("GROW_CHANCE", "19"));
                LOGGER.info("GROW_CHANCE={}", GROW_CHANCE);
            } catch (IOException | NumberFormatException e) {

                System.err.println("Failed to load biggs_apple_trees config: " + e);

            }
        }
    }
}