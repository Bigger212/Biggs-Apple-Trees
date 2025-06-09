package net.bigger212.bigs_apple_trees;

import net.bigger212.bigs_apple_trees.block.ModBlocks;
import net.bigger212.bigs_apple_trees.block.entity.ModBlockEntities;
import net.bigger212.bigs_apple_trees.item.ModItems;
import net.bigger212.bigs_apple_trees.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BigsAppleTrees implements ModInitializer {
    public static final String MOD_ID = "bigs_apple_trees";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing bigs_apple_trees");
        Config.registerConfigs();
        ModBlocks.registerModBlocks();
        ModItems.registerModItems();
        ModBlockEntities.registerModBlockEntities();
        // TODO Remove vanilla apple drops from leaves.
        // TODO New tree shapes.
        // TODO New logs & planks.
        ModWorldGeneration.generateModWorldGen();
    }

    public static class Config {
        private static final File CONFIG_FILE = new File("config/bigs_apple_trees.properties");
        private static final java.util.Properties CONFIG = new java.util.Properties();

        public static int GROW_CHANCE = 24;

        public static void registerConfigs() {
            if (!CONFIG_FILE.exists()) {
                try {
                    CONFIG_FILE.getParentFile().mkdirs();
                    try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                        writer.write("# BigsAppleTrees Config\n");
                        writer.write("# This config controls ...\n#\n");

                        writer.write("Chance per tick for apple leaves to age. Default= 24" + "\n");
                        writer.write("Apple leaves have 4 ages. They bloom at 2, harvest 2 apples at 3, and harvest 4 apples at 4." + "\n");
                        writer.write("GROW_CHANCE=" + GROW_CHANCE + "\n#\n");
                    }
                } catch (IOException e) {
                    System.err.println("Failed to create BigsAppleTrees config: " + e);
                }
            }
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                CONFIG.load(reader);
                GROW_CHANCE = Integer.parseInt(CONFIG.getProperty("GROW_RATE", "24"));
            } catch (IOException | NumberFormatException e) {
                System.err.println("Failed to load bigs_apple_trees config: " + e);
            }
        }
    } // Maybe unused...
}