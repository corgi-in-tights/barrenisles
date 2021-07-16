package ca.thecorgi.barrenisles.utils.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.Biome;

import static ca.thecorgi.barrenisles.BarrenIsles.config;

public class SpawnRegistry {
    @SuppressWarnings("deprecation")
    public static void register() {
        if (config.spawn.spawn_duneraptors == true) {
            net.fabricmc.fabric.api.biome.v1.BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.DESERT), SpawnGroup.CREATURE,
                    EntityRegistry.DUNERAPTOR, 1, 1, 2);
        }

        if (config.spawn.spawn_tumbleweeds == true) {
            net.fabricmc.fabric.api.biome.v1.BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.DESERT), SpawnGroup.CREATURE,
                    EntityRegistry.TUMBLEWEED, 1, 1, 1);
        }
    }
}
