package net.bigger212.biggs_apple_trees.block.entity.renderer;

import net.bigger212.biggs_apple_trees.BiggsAppleTrees.*;
import net.bigger212.biggs_apple_trees.block.FruitLeavesBlock;
import net.bigger212.biggs_apple_trees.block.entity.FruitLeavesBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec2f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.List;

import static java.util.Collections.shuffle;

public class FruitLeavesBlockEntityRenderer implements BlockEntityRenderer<FruitLeavesBlockEntity> {
////////////////////////////////////// FruitLeavesBlockEntityRenderer //////////////////////////////////////////////////
    public static final int RENDER_COUNT = 1;

    public FruitLeavesBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        // Hmmmm...
    }

    private int getLightLevel(World world, BlockPos pos) {
        return LightmapTextureManager.pack(
                world.getLightLevel(LightType.BLOCK, pos),
                world.getLightLevel(LightType.SKY, pos)
        );
    }

//////////////////////////////////////////// BlockEntityRenderer ///////////////////////////////////////////////////////
    @Override
    public void render(FruitLeavesBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay)
    {
        if (entity.getCachedState().get(FruitLeavesBlock.AGE) < FruitLeavesBlock.MAX_AGE) return;

        final float _scale = 0.50f;
        World _world = entity.getWorld();
        BlockPos _pos = entity.getPos();
        ItemRenderer _item_renderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack _stack = entity.getFruit();
        Random _random = new Random(_pos.asLong()); /* java.util.Random for java.util.Collections.shuffle()...
        Seeded with pos. */
        List<Vec2f> _baseOffsets = List.of(
                new Vec2f(-0.22f, -0.2f),
                new Vec2f(0.2f, -0.22f),
                new Vec2f(-0.22f, 0.2f),
                new Vec2f(0.2f, 0.22f),
                new Vec2f(0.06f, 0.0f)
        );
        // Fill a List with valid faces (excludes UP)
        List<Direction> _validFaces = new java.util.ArrayList<>();

        for (Direction dir : List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.DOWN)) {

            BlockState neighbor = _world.getBlockState(_pos.offset(dir));

            if (neighbor.isAir() || neighbor.isReplaceable()) {
                _validFaces.add(dir);
            }
        }
        if (_validFaces.isEmpty()) return;
        shuffle(_validFaces, _random);
        // Distribute fruits across faces, up to 2 per face (total 10)

        List<Vec2f> _shuffledOffsets = new ArrayList<>(_baseOffsets);

        Collections.shuffle(_shuffledOffsets, _random);

        for (int i = 0; i < RENDER_COUNT; i++) {

            Direction face = _validFaces.get(i % _validFaces.size());

            Vec2f offset = _shuffledOffsets.get(i % _shuffledOffsets.size());

            matrices.push();
            matrices.translate(0.5, 0.5, 0.5);
            switch (face) {
                case NORTH -> {
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                    matrices.translate(offset.x, offset.y, 0.52);
                }
                case SOUTH -> {
                    matrices.translate(offset.x, offset.y, 0.52);
                }
                case WEST -> {
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90));
                    matrices.translate(offset.x, offset.y, 0.52);
                }
                case EAST -> {
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
                    matrices.translate(offset.x, offset.y, 0.52);
                }
                case DOWN -> {
                    matrices.translate(offset.x, -0.625f, offset.y);
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(_random.nextInt(8) * 45));
                }
            }
            matrices.scale(_scale, _scale, _scale);
            _item_renderer.renderItem(_stack, ModelTransformationMode.FIXED,
                    getLightLevel(_world, _pos), OverlayTexture.DEFAULT_UV,
                    matrices, vertexConsumers, _world, 1);
            matrices.pop();
        }
    }
}
