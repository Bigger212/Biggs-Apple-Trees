package net.bigger212.biggs_apple_trees.datagen;

import net.bigger212.biggs_apple_trees.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {

        addDrop(ModBlocks.APPLE_LOG);
        addDrop(ModBlocks.APPLE_WOOD);
        addDrop(ModBlocks.STRIPPED_APPLE_LOG);
        addDrop(ModBlocks.STRIPPED_APPLE_WOOD);
        addDrop(ModBlocks.APPLE_PLANKS);

    }
}
