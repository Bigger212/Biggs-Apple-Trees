package net.bigger212.biggs_apple_trees;

import net.bigger212.biggs_apple_trees.block.ModBlocks;
import net.bigger212.biggs_apple_trees.block.entity.ModBlockEntities;
import net.bigger212.biggs_apple_trees.block.entity.renderer.FruitLeavesBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class BiggsAppleTreesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.FRUIT_LEAF, FruitLeavesBlockEntityRenderer::new);

        // Register render layers
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.APPLE_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.APPLE_SAPLING, RenderLayer.getCutout());

        // Biome tinting
        ColorProviderRegistry.BLOCK.register(
                (state, world, pos, tintIndex) ->
                        world != null && pos != null
                                ? BiomeColors.getFoliageColor(world, pos)
                                : FoliageColors.getDefaultColor(),
                ModBlocks.APPLE_LEAVES
        );

        // Item tinting
        ColorProviderRegistry.ITEM.register(
                (stack, tintIndex) -> FoliageColors.getDefaultColor(),
                ModBlocks.APPLE_LEAVES
        );
    }
}
