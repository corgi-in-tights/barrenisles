package barrenisles.init;

import barrenisles.common.world.structures.structure.BadlandsTempleStructure;
import barrenisles.common.world.structures.structure.OasisStructure;
import barrenisles.common.world.structures.structure.RockStructure;
import barrenisles.common.world.structures.structure.TallRockStructure;
import barrenisles.core.BarrenIslesMod;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

import static barrenisles.api.BarrenIslesStructures.*;

public class ModStructures {
    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, BarrenIslesMod.MODID);

    static {
        badlands_temple = STRUCTURES.register("badlands_temple", BadlandsTempleStructure::new);
        oasis = STRUCTURES.register("oasis", OasisStructure::new);
        rock = STRUCTURES.register("rock_1", RockStructure::new);
        tall_rock = STRUCTURES.register("rock_2", TallRockStructure::new);
    }

    public static void setupStructures() {
        setupMapSpacingAndLand(
                badlands_temple.get(),
                new StructureSeparationSettings(32, // min value between chunks
                        24, // max value between chunks
                        854383894),
                true);

        setupMapSpacingAndLand(
                oasis.get(),
                new StructureSeparationSettings(18, // min value between chunks
                        12, // max value between chunks
                        320909345),
                true);
        setupMapSpacingAndLand(
                rock.get(),
                new StructureSeparationSettings(28, // min value between chunks
                        16, // max value between chunks
                        283223445),
                true);
        setupMapSpacingAndLand(
                tall_rock.get(),
                new StructureSeparationSettings(40, // min value between chunks
                        29, // max value between chunks
                        579125823),
                true);
        // Add more structures here and so on
    }

    public static <F extends Structure<?>> void setupMapSpacingAndLand(
            F structure,
            StructureSeparationSettings structureSeparationSettings,
            boolean transformSurroundingLand)
    {
        // Define structure for Vanilla Structure class.
        Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

        /*
         * NOISE_AFFECTING_FEATURES requires AccessTransformer  (See resources/META-INF/accesstransformer.cfg)
         */
        if(transformSurroundingLand){
            Structure.NOISE_AFFECTING_FEATURES =
                    ImmutableList.<Structure<?>>builder()
                            .addAll(Structure.NOISE_AFFECTING_FEATURES)
                            .add(structure)
                            .build();
        }

        //DEFAULTS requires AccessTransformer  (See resources/META-INF/accesstransformer.cfg

        DimensionStructuresSettings.DEFAULTS =
                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.DEFAULTS)
                        .put(structure, structureSeparationSettings)
                        .build();


        WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().structureSettings().structureConfig();

            /*
             * Pre-caution in case a mod makes the structure map immutable like datapacks do.
             * I take no chances myself. You never know what another mods does...
             *
             * structureConfig requires AccessTransformer  (See resources/META-INF/accesstransformer.cfg)
             */
            if(structureMap instanceof ImmutableMap){
                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, structureSeparationSettings);
                settings.getValue().structureSettings().structureConfig = tempMap;
            }
            else{
                structureMap.put(structure, structureSeparationSettings);
            }
        });
    }

    public static void register(IEventBus bus) {
        STRUCTURES.register(bus);
    }
}
