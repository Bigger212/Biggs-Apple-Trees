package net.bigger212.biggs_apple_trees.block.entity;

import net.bigger212.biggs_apple_trees.block.FruitLeavesBlock;
import net.bigger212.biggs_apple_trees.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.bigger212.biggs_apple_trees.BiggsAppleTrees.*;

public class ModBlockEntities {
/////////////////////////////////////////////// ModBlockEntities ///////////////////////////////////////////////////////
// Init
    public static void registerModBlockEntities() {
//        LOGGER.info("Registering Block Entities for " + MOD_ID);
    }

// Fruit Leaf
    public static final BlockEntityType<FruitLeavesBlockEntity> FRUIT_LEAF =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "fruit_leaf"),
                    FabricBlockEntityTypeBuilder.create( // Pass fruit through constructor.
                            (pos, state) -> new FruitLeavesBlockEntity(pos, state, ((FruitLeavesBlock) state.getBlock()).getFruit()),
                            ModBlocks.APPLE_LEAVES
                    ).build());
}
