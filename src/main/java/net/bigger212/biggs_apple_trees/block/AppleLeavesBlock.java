package net.bigger212.biggs_apple_trees.block;

import net.bigger212.biggs_apple_trees.BiggsAppleTrees.*;
import net.bigger212.biggs_apple_trees.block.entity.AppleLeavesBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import net.minecraft.block.BlockState;

public class AppleLeavesBlock extends LeavesBlock implements BlockEntityProvider, Fertilizable {

    public static final IntProperty AGE = IntProperty.of("age", 0, 2);
    public static final BooleanProperty PERSISTENT = LeavesBlock.PERSISTENT;
    public static final IntProperty DISTANCE = LeavesBlock.DISTANCE;

    public AppleLeavesBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(AGE, 0)
                .with(PERSISTENT, false)
                .with(DISTANCE, 7)); // 7 = far = decays if not updated;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(AGE);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        if (!isExposedToAir(world, pos)) {
            return;
        }
        int age = state.get(AGE);
        if (age == 0) {
            int light = world.getLightLevel(pos.up());
            if (light >= 9 && random.nextInt(Config.BLOOM_CHANCE) == 1) {
                world.setBlockState(pos, state.with(AGE, age + 1)
                                .with(PERSISTENT, false),
                        Block.NOTIFY_ALL);
            }
        }
        if (age == 1) {
            int light = world.getLightLevel(pos.up());
            if (light >= 9 && random.nextInt(Config.GROW_CHANCE) == 1) {
                world.setBlockState(pos, state.with(AGE, age + 1)
                        .with(PERSISTENT, false),
                        Block.NOTIFY_ALL);
            }
        }
    }

    private boolean isExposedToAir(ServerWorld world, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.offset(direction);
            BlockState neighborState = world.getBlockState(neighborPos);
            if (direction == Direction.UP) {
                continue;
            }
            if (neighborState.isAir()) {
                return true;
            }
        }
        return false;
    }



    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AppleLeavesBlockEntity(pos, state);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < 2;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int age = state.get(AGE);
        world.setBlockState(pos, state.with(AGE, Math.min(3, age + 1)), Block.NOTIFY_ALL);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
                              BlockHitResult hit)
    {
        int age = state.get(AppleLeavesBlock.AGE);
        if (age == 2 && hand == Hand.MAIN_HAND && player.getStackInHand(hand).getItem() != Items.BONE_MEAL) {
            if (!world.isClient) {
                Block block = state.getBlock();
                Item fruitItem = AppleLeavesBlockEntity.FRUIT_BY_LEAF.getOrDefault(block, Items.APPLE);
                int count = 1;

                dropStack(world, pos, new ItemStack(fruitItem, count));
                world.playSound(null, pos, SoundEvents.BLOCK_AZALEA_BREAK,
                        SoundCategory.BLOCKS, 1.0f, 1.0f);

                world.setBlockState(pos, state.with(AGE, 0)
                        .with(PERSISTENT, false),
                        Block.NOTIFY_LISTENERS);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
