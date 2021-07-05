package ca.thecorgi.barrenisles.utils.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.Biome;

public class SpawnRegistry {
    public static void init() {
        net.fabricmc.fabric.api.biome.v1.BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.DESERT), SpawnGroup.CREATURE,
                EntityRegistry.DUNERAPTOR, 1, 1, 2);
    }
}
