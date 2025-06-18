package net.bigger212.biggs_apple_trees.block;

import net.bigger212.biggs_apple_trees.BiggsAppleTrees.*;
import net.bigger212.biggs_apple_trees.block.entity.FruitLeavesBlockEntity;
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

public class FruitLeavesBlock extends LeavesBlock implements BlockEntityProvider, Fertilizable {
////////////////////////////////////////////// FruitLeavesBlock ////////////////////////////////////////////////////////
    private final Item fruit;
    public static final int MAX_AGE = 2;
    public static final IntProperty AGE = IntProperty.of("age", 0, MAX_AGE);
    public static final int HARVEST_COUNT = 1;

    public FruitLeavesBlock(Settings settings, Item fruit) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(AGE, 0)
                .with(PERSISTENT, false));
        this.fruit = fruit;
    }

    public Item getFruit() {
        return fruit;
    }

//////////////////////////////////////////////// LeavesBlock
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        if (!canGrow(world, random, pos, state)) return; // Check Fertilizable canGrow()
        if (!(world.getLightLevel(pos.up()) >= 9)) return; // Check light level

        int _age = state.get(AGE);

        if (_age == 0) {
            if (random.nextInt(Config.BLOOM_CHANCE) == 1) {
                world.setBlockState(pos, state.with(AGE, 1), Block.NOTIFY_ALL);
            }
        }
        if (_age == 1) {
            if (random.nextInt(Config.GROW_CHANCE) == 1) {
                world.setBlockState(pos, state.with(AGE, 2), Block.NOTIFY_ALL);
            }
        }
    }

/////////////////////////////////////////////////// Block
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(AGE);
    }

/////////////////////////////////////////////// AbstractBlock
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
                              BlockHitResult hit)
    {
        if (state.get(FruitLeavesBlock.AGE) == 2 && hand == Hand.MAIN_HAND) { //&& player.getStackInHand(hand).getItem() != Items.BONE_MEAL) {
            if (!world.isClient) {

                ItemStack _stack = new ItemStack(getFruit(), HARVEST_COUNT);

                if (!player.getInventory().insertStack(_stack)) {
                    dropStack(world, pos, _stack);
                }
                world.playSound(null, pos, SoundEvents.BLOCK_AZALEA_BREAK,
                        SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(pos, state.with(AGE, 0), Block.NOTIFY_LISTENERS);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

//////////////////////////////////////////// BlockEntityProvider ///////////////////////////////////////////////////////
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FruitLeavesBlockEntity(pos, state, this.fruit);
    }

/////////////////////////////////////////////// Fertilizable ///////////////////////////////////////////////////////////
    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return false; //return state.get(AGE) < 2;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        if (state.get(AGE) == MAX_AGE) return false;
        for (Direction _direction : Direction.values()) {

            BlockPos neighborPos = pos.offset(_direction);
            BlockState neighborState = world.getBlockState(neighborPos);

            if (_direction == Direction.UP) {
                continue;
            }
            if (neighborState.isAir()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int _age = state.get(AGE);

        world.setBlockState(pos, state.with(AGE, Math.min(MAX_AGE, _age + 1)), Block.NOTIFY_ALL);
    }

}
