package ca.thecorgi.barrenisles;

import ca.thecorgi.barrenisles.feature.tree.PalmTreePlacer;
import ca.thecorgi.barrenisles.mixin.TrunkPlacerTypeInvoker;
import ca.thecorgi.barrenisles.utils.config.BarrenIslesConfig;
import ca.thecorgi.barrenisles.utils.registry.*;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import software.bernie.geckolib3.GeckoLib;


public class BarrenIsles implements ModInitializer {
    public static final String ModID = "barrenisles";
    public static BarrenIslesConfig config;

    public static final TrunkPlacerType<PalmTreePlacer> PALM_TREE_PLACER = TrunkPlacerTypeInvoker.callRegister("palm_trunk_placer", PalmTreePlacer.CODEC);
//    public static final FoliagePlacerType<PalmFoliagePlacer> PALM_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.callRegister("palm_foliage_placer", PalmFoliagePlacer.CODEC);


    @Override
    public void onInitialize() {
        GeckoLib.initialize();



        AutoConfig.register(BarrenIslesConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(BarrenIslesConfig.class).getConfig();

        StructureRegistry.register();
        EntityRegistry.register();
        SpawnRegistry.register();
        ItemRegistry.register();
        FeatureRegistry.register();
        BlockRegistry.register();
    }

}
