package barrenisles.core;

import barrenisles.api.BarrenIslesStructures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class BarrenIslesConfiguredStructures {
    public static StructureFeature<?, ?> configured_badlands_temple = BarrenIslesStructures.badlands_temple.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?, ?> configured_oasis = BarrenIslesStructures.oasis.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?, ?> configured_rock = BarrenIslesStructures.rock.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?, ?> configured_tall_rock = BarrenIslesStructures.tall_rock.get().configured(IFeatureConfig.NONE);

    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(BarrenIslesMod.MODID, "configured_badlands_temple"), configured_badlands_temple);
        Registry.register(registry, new ResourceLocation(BarrenIslesMod.MODID, "configured_oasis"), configured_oasis);
        Registry.register(registry, new ResourceLocation(BarrenIslesMod.MODID, "configured_rock_1"), configured_rock);
        Registry.register(registry, new ResourceLocation(BarrenIslesMod.MODID, "configured_rock_2"), configured_tall_rock);

        FlatGenerationSettings.STRUCTURE_FEATURES.put(BarrenIslesStructures.badlands_temple.get(), configured_badlands_temple);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(BarrenIslesStructures.oasis.get(), configured_oasis);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(BarrenIslesStructures.rock.get(), configured_rock);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(BarrenIslesStructures.tall_rock.get(), configured_tall_rock);
    }
}
