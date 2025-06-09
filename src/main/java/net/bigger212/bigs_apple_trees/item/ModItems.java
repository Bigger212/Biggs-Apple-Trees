package net.bigger212.bigs_apple_trees.item;

import net.bigger212.bigs_apple_trees.block.ModBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.bigger212.bigs_apple_trees.BigsAppleTrees.*;

public class ModItems {
////////////////////////////////////////////////// Registration ////////////////////////////////////////////////////////

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {

        entries.add(ModBlocks.APPLE_LEAVES);
        entries.add(ModBlocks.APPLE_SAPLING);

    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), item);
    }

    public static void registerModItems() {
        LOGGER.info("Registering Mod Items for " + MOD_ID);
        // TODO Add to food group.
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
