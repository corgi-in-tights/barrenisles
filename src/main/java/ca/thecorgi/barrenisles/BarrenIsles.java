package ca.thecorgi.barrenisles;

import ca.thecorgi.barrenisles.feature.tree.PalmFoliagePlacer;
import ca.thecorgi.barrenisles.feature.tree.PalmTreeDecorator;
import ca.thecorgi.barrenisles.feature.tree.PalmTrunkPlacer;
import ca.thecorgi.barrenisles.mixin.world.features.FoliagePlacerTypeInvoker;
import ca.thecorgi.barrenisles.mixin.world.features.TreeDecoratorTypeInvoker;
import ca.thecorgi.barrenisles.mixin.world.features.TrunkPlacerTypeInvoker;
import ca.thecorgi.barrenisles.utils.config.BarrenIslesConfig;
import ca.thecorgi.barrenisles.utils.registry.*;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import software.bernie.geckolib3.GeckoLib;

public class BarrenIsles implements ModInitializer {
    public static final String ModID = "barrenisles";
    public static BarrenIslesConfig config;

    public static Identifier id(String path) {
        return new Identifier(ModID, path);
    }
    public static final TrunkPlacerType<PalmTrunkPlacer> PALM_TREE_PLACER = TrunkPlacerTypeInvoker.callRegister("palm_trunk_placer", PalmTrunkPlacer.CODEC);
    public static final FoliagePlacerType<PalmFoliagePlacer> PALM_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.callRegister("palm_foliage_placer", PalmFoliagePlacer.CODEC);
    public static final TreeDecoratorType<PalmTreeDecorator> PALM_TREE_DECORATOR = TreeDecoratorTypeInvoker.callRegister("palm_tree_decorator", PalmTreeDecorator.CODEC);

    @Override
    public void onInitialize() {
        LogManager.getLogger().log(Level.INFO, "Barren Isles has been loaded!");
        GeckoLib.initialize();

        AutoConfig.register(BarrenIslesConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(BarrenIslesConfig.class).getConfig();

        StructureRegistry.register();
        EntityRegistry.register();
        SpawnRegistry.register();
        FeatureRegistry.register();
        BlockRegistry.register();
        ItemRegistry.register();
        GUIRegistry.register();
    }
}


