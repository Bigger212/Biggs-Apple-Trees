package net.bigger212.bigs_apple_trees.block.entity.renderer;

import net.bigger212.bigs_apple_trees.block.AppleLeavesBlock;
import net.bigger212.bigs_apple_trees.block.entity.AppleLeavesBlockEntity;
import net.bigger212.bigs_apple_trees.item.ModItems;
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
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.List;

public class AppleLeavesBlockEntityRenderer implements BlockEntityRenderer<AppleLeavesBlockEntity> {

    public AppleLeavesBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        // Hmmmm...
    }

    @Override
    public void render(AppleLeavesBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay)
    {
        BlockState state = entity.getCachedState();
        int age = state.getOrEmpty(AppleLeavesBlock.AGE).orElse(0);

        if (age < 2) return;

        int totalFruits = (age == 2) ? 2 : 4;
        final float scale = 0.40f;
        World world = entity.getWorld();
        BlockPos pos = entity.getPos();
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack stack = entity.getItemStack();
        Random random = Random.create(pos.asLong());
        List<Vec2f> baseOffsets = List.of(
                new Vec2f(-0.22f, -0.2f),
                new Vec2f(0.2f, -0.22f),
                new Vec2f(-0.22f, 0.2f),
                new Vec2f(0.2f, 0.22f),
                new Vec2f(0.06f, 0.0f)
        );
        List<Direction> validFaces = new java.util.ArrayList<>();

        for (Direction dir : List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.DOWN)) {

            BlockState neighbor = world.getBlockState(pos.offset(dir));

            if (neighbor.isAir() || neighbor.isReplaceable()) {
                validFaces.add(dir);
            }
        }
        if (validFaces.isEmpty()) return;

        int numFaces = validFaces.size();
        int[] fruitsPerFace = new int[numFaces];
        int allocated = 0;

        // Distribute fruits across faces, up to 3 per face
        outer:
        for (int i = 0; i < totalFruits; i++) {
            for (int j = 0; j < numFaces; j++) {

                Direction face = validFaces.get(j);
                int max = (face == Direction.DOWN) ? 1 : 3; // Limit DOWN fruit count to 1

                if (fruitsPerFace[j] < max) {

                    fruitsPerFace[j]++;
                    allocated++;

                    if (allocated >= totalFruits) break outer;
                }
            }
        }
        for (int i = 0; i < numFaces; i++) {

            Direction face = validFaces.get(i);
            int fruits = fruitsPerFace[i];

            // Pick random unique offsets per face
            List<Vec2f> shuffledOffsets = new java.util.ArrayList<>(baseOffsets);
            java.util.Collections.shuffle(shuffledOffsets, new java.util.Random(random.nextLong()));
            for (int f = 0; f < fruits; f++) {

                Vec2f offset = shuffledOffsets.get(f);

                matrices.push();
                matrices.translate(0.5, 0.5, 0.5);
                switch (face) {
                    case NORTH -> {
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                        matrices.translate(offset.x, offset.y, 0.52);
                    }
                    case SOUTH -> {
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0));
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
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(random.nextInt(8) * 45)); // Keep full spin here if desired
                    }
                }
                matrices.scale(scale, scale, scale);
                itemRenderer.renderItem(
                        stack,
                        ModelTransformationMode.FIXED,
                        getLightLevel(world, pos, stack),
                        OverlayTexture.DEFAULT_UV,
                        matrices,
                        vertexConsumers,
                        world,
                        1
                );
                matrices.pop();
            }
        }
    }

    private int getLightLevel(World world, BlockPos pos, ItemStack stack) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);

        return LightmapTextureManager.pack(bLight, sLight);
    }
}
