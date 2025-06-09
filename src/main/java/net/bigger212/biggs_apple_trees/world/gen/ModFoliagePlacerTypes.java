package net.bigger212.biggs_apple_trees.world.gen;

import net.bigger212.biggs_apple_trees.mixin.FoliagePlacerTypeInvoker;
import net.bigger212.biggs_apple_trees.world.tree.AppleFoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import static net.bigger212.biggs_apple_trees.BiggsAppleTrees.*;

public class ModFoliagePlacerTypes {
    public static final FoliagePlacerType<?> APPLE_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.callRegister("apple_foliage_placer", AppleFoliagePlacer.CODEC);

    public static void register() {
        LOGGER.info("Registering Foliage Placer for " + MOD_ID);
    }
}
