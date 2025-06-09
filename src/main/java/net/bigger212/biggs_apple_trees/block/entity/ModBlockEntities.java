package net.bigger212.biggs_apple_trees.block.entity;

import net.bigger212.biggs_apple_trees.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.bigger212.biggs_apple_trees.BiggsAppleTrees.*;

public class ModBlockEntities {

    public static final BlockEntityType<AppleLeavesBlockEntity> FRUIT_LEAF =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "fruit_leaf"),
                    FabricBlockEntityTypeBuilder.create(AppleLeavesBlockEntity::new,
                            ModBlocks.APPLE_LEAVES
                    ).build());

////////////////////////////////////////////////// Registration ////////////////////////////////////////////////////////

    public static void registerModBlockEntities() {
        LOGGER.info("Registering Block Entities for " + MOD_ID);
    }
}
