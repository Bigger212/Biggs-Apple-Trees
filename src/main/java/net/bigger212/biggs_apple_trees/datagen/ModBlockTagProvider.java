package net.bigger212.biggs_apple_trees.datagen;

import net.bigger212.biggs_apple_trees.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

        getOrCreateTagBuilder(BlockTags.PLANKS)
                .add(ModBlocks.APPLE_PLANKS);

        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.APPLE_LOG)
                .add(ModBlocks.APPLE_WOOD)
                .add(ModBlocks.STRIPPED_APPLE_LOG)
                .add(ModBlocks.STRIPPED_APPLE_WOOD);

    }
}
