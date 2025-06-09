package net.bigger212.bigs_apple_trees.block;

import net.bigger212.bigs_apple_trees.world.tree.AppleSaplingGenerator;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.bigger212.bigs_apple_trees.BigsAppleTrees.*;

public class ModBlocks {

    // Apple
    public static final Block APPLE_LEAVES = registerBlock("apple_leaves",
            new AppleLeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).nonOpaque()));
    public static final Block APPLE_SAPLING = registerBlock("apple_sapling",
            new SaplingBlock(new AppleSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING)));

////////////////////////////////////////////////// Registration ////////////////////////////////////////////////////////

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        LOGGER.info("Registering {}", name);
        return Registry.register(Registries.ITEM, new Identifier(MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        LOGGER.info("Registering ModBlocks for " + MOD_ID);
    }
}
