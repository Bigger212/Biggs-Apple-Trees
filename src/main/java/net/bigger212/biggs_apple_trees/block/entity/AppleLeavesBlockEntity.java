package net.bigger212.biggs_apple_trees.block.entity;

import net.bigger212.biggs_apple_trees.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public class AppleLeavesBlockEntity extends BlockEntity {

    public static final Map<Block, Item> FRUIT_BY_LEAF = Map.of(
            ModBlocks.APPLE_LEAVES, Items.APPLE
    );

    public AppleLeavesBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FRUIT_LEAF, pos, state);
    }

    public ItemStack getItemStack() {
        Block block = getCachedState().getBlock();
        Item fruit = FRUIT_BY_LEAF.get(block);

        return fruit != null ? new ItemStack(fruit) : ItemStack.EMPTY;
    }
}
