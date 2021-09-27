package barrenisles.init;

import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import barrenisles.common.tree.NormalPalmTreeFeature;
import barrenisles.common.tree.PalmTreeFeature;
import barrenisles.core.BarrenIslesMod;

public class ModTreeFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, BarrenIslesMod.MODID);

    public static final RegistryObject<PalmTreeFeature> NORMAL_PALM_TREE = register("normal_palm_tree", () -> new NormalPalmTreeFeature(NoFeatureConfig.CODEC));

    private static <T extends Feature<?>> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return FEATURES.register(name, sup);
    }
}
