package net.bigger212.biggs_apple_trees.datagen;

import net.bigger212.biggs_apple_trees.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.APPLE_LEAVES); // NOT LEAVES DO MANUAL!
        blockStateModelGenerator.registerTintableCross(ModBlocks.APPLE_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);

        blockStateModelGenerator.registerLog(ModBlocks.APPLE_LOG).log(ModBlocks.APPLE_LOG).wood(ModBlocks.APPLE_WOOD);
        blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_APPLE_LOG).log(ModBlocks.STRIPPED_APPLE_LOG).wood(ModBlocks.STRIPPED_APPLE_WOOD);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.APPLE_PLANKS);

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}
