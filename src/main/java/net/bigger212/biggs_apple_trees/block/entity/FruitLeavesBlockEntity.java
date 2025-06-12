package net.bigger212.biggs_apple_trees.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class FruitLeavesBlockEntity extends BlockEntity {
////////////////////////////////////////// FruitLeavesBlockEntity //////////////////////////////////////////////////////
    private final Item fruit;

    public FruitLeavesBlockEntity(BlockPos pos, BlockState state, Item fruit) {
        super(ModBlockEntities.FRUIT_LEAF, pos, state);
        this.fruit = fruit;
    }

    public ItemStack getFruit() {
        return new ItemStack(fruit);
    }
}
