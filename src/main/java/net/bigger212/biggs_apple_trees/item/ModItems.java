package net.bigger212.biggs_apple_trees.item;

import net.bigger212.biggs_apple_trees.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.bigger212.biggs_apple_trees.BiggsAppleTrees.*;

public class ModItems {
////////////////////////////////////////////////// ModItems ////////////////////////////////////////////////////////////
// Init
    private static void addItemsToNaturalItemGroup(FabricItemGroupEntries entries) {

        entries.addAfter(Blocks.CHERRY_LOG,ModBlocks.APPLE_LOG);
        entries.addAfter(Blocks.CHERRY_LEAVES,ModBlocks.APPLE_LEAVES);
        entries.addAfter(Blocks.CHERRY_SAPLING,ModBlocks.APPLE_SAPLING);

    }
    private static void addItemsToBuildingBlocksItemGroup(FabricItemGroupEntries entries) {

        entries.addAfter(Blocks.CHERRY_BUTTON, ModBlocks.APPLE_LOG);
        entries.addAfter(ModBlocks.APPLE_LOG, ModBlocks.APPLE_WOOD);
        entries.addAfter(ModBlocks.APPLE_WOOD, ModBlocks.STRIPPED_APPLE_LOG);
        entries.addAfter(ModBlocks.STRIPPED_APPLE_LOG, ModBlocks.STRIPPED_APPLE_WOOD);
//        entries.addAfter(ModBlocks.STRIPPED_APPLE_WOOD, ModBlocks.APPLE_PLANKS);

    }

    private static void registerItem(String name, Item item) {
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), item);
    }

    public static void registerModItems() {
//        LOGGER.info("Registering Mod Items for " + MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(ModItems::addItemsToNaturalItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(ModItems::addItemsToBuildingBlocksItemGroup);
    }
}
