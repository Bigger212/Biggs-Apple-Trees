package net.bigger212.biggs_apple_trees.world.gen;

import net.bigger212.biggs_apple_trees.mixin.FoliagePlacerTypeInvoker;
import net.bigger212.biggs_apple_trees.world.tree.AppleBlobFoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class ModFoliagePlacerTypes {
    public static final FoliagePlacerType<?> APPLE_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.callRegister("apple_foliage_placer", AppleBlobFoliagePlacer.CODEC);

    public static void register() {
//        LOGGER.info("Registering Foliage Placer for " + MOD_ID);
    }
}
