package ca.thecorgi.barrenisles.utils.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.Biome;

public class SpawnRegistry {
    @SuppressWarnings("deprecation")
    public static void register() {
        net.fabricmc.fabric.api.biome.v1.BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.DESERT), SpawnGroup.CREATURE,
                EntityRegistry.DUNERAPTOR, 1, 1, 2);
    }
}
