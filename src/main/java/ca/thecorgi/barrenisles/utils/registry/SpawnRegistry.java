package ca.thecorgi.barrenisles.utils.registry;

import ca.thecorgi.barrenisles.client.renderer.coyote.CoyoteRenderer;
import ca.thecorgi.barrenisles.client.renderer.duneraptor.DuneraptorRenderer;
import ca.thecorgi.barrenisles.client.renderer.tumbleweed.TumbleweedRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.GameRules;
import net.minecraft.world.biome.Biome;

import static ca.thecorgi.barrenisles.BarrenIsles.config;

public class SpawnRegistry {
    @SuppressWarnings("deprecation")
    public static void register() {
//        if (GameRules.DO_MOB_SPAWNING == true) {
            if (config.spawn.spawn_duneraptors == true) {
                net.fabricmc.fabric.api.biome.v1.BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.DESERT), SpawnGroup.CREATURE,
                        EntityRegistry.DUNERAPTOR, 1, 1, 1);
            }

            if (config.spawn.spawn_coyotes == true) {
                net.fabricmc.fabric.api.biome.v1.BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.DESERT), SpawnGroup.CREATURE,
                        EntityRegistry.COYOTE, 2, 1, 5);
            }

            if (config.spawn.spawn_tumbleweeds == true) {
                net.fabricmc.fabric.api.biome.v1.BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.DESERT), SpawnGroup.CREATURE,
                        EntityRegistry.TUMBLEWEED, 2, 1, 1);
            }
        }
//    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.DUNERAPTOR, DuneraptorRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.TUMBLEWEED, TumbleweedRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.COYOTE, CoyoteRenderer::new);
    }
}
