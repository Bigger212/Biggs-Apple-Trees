package net.bigger212.bigs_apple_trees;

import net.bigger212.bigs_apple_trees.block.ModBlocks;
import net.bigger212.bigs_apple_trees.block.entity.ModBlockEntities;
import net.bigger212.bigs_apple_trees.block.entity.renderer.AppleLeavesBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class BigsAppleTreesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.FRUIT_LEAF, AppleLeavesBlockEntityRenderer::new);
        // Register render layers
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.APPLE_LEAVES, RenderLayer.getCutout());
        // Block tinting (biome-dependent)
        ColorProviderRegistry.BLOCK.register(
                (state, world, pos, tintIndex) ->
                        world != null && pos != null
                                ? BiomeColors.getFoliageColor(world, pos)
                                : FoliageColors.getDefaultColor(),
                ModBlocks.APPLE_LEAVES
        );
        // Item tinting (use default foliage color)
        ColorProviderRegistry.ITEM.register(
                (stack, tintIndex) -> FoliageColors.getDefaultColor(),
                ModBlocks.APPLE_LEAVES
        );
    }
}
