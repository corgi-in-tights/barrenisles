package de.xyndra.barrenisles;

import com.mojang.serialization.Codec;
import de.xyndra.barrenisles.setup.ConfigureStructures;
import de.xyndra.barrenisles.setup.ModStructures;
import de.xyndra.barrenisles.setup.Registration;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(BarrenIsles.MOD_ID)
public class BarrenIsles
{
    public static final String MOD_ID = "barrenisles";

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public BarrenIsles() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModStructures.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
        modEventBus.addListener(this::setup);

        // For events that happen after initialization. This is probably going to be use a lot.
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);

        // The comments for BiomeLoadingEvent and StructureSpawnListGatherEvent says to do HIGH for additions.
        forgeBus.addListener(EventPriority.HIGH, this::biomeModification);


        Registration.register();
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            ModStructures.setupStructures();
            ConfigureStructures.registerConfiguredStructures();
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        Registration.transparency();
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    public void biomeModification(final BiomeLoadingEvent event) {
        /*
         * Add our structure to all biomes including other modded biomes.
         * You can skip or add only to certain biomes based on stuff like biome category,
         * temperature, scale, precipitation, mod id, etc. All kinds of options!
         *
         * You can even use the BiomeDictionary as well! To use BiomeDictionary, do
         * RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName()) to get the biome's
         * registrykey. Then that can be fed into the dictionary to get the biome's types.
         */
        if(event.getCategory() != Biome.Category.NETHER && event.getCategory() != Biome.Category.THEEND) {
            event.getGeneration().getStructures().add(() -> ConfigureStructures.CONFIGURED_ROCK_1);
            event.getGeneration().getStructures().add(() -> ConfigureStructures.CONFIGURED_ROCK_2);
        }
        if(event.getCategory() == Biome.Category.DESERT){
            event.getGeneration().getStructures().add(() -> ConfigureStructures.CONFIGURED_OASIS);
        }
    }

    /**
     * Will go into the world's chunkgenerator and manually add our structure spacing.
     * If the spacing is not added, the structure doesn't spawn.
     *
     * Use this for dimension blacklists for your structure.
     * (Don't forget to attempt to remove your structure too from the map if you are blacklisting that dimension!)
     * (It might have your structure in it already.)
     *
     * Basically use this to make absolutely sure the chunkgenerator can or cannot spawn your structure.
     */
    private static Method GETCODEC_METHOD;
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)event.getWorld();

            /*
             * Skip Terraforged's chunk generator as they are a special case of a mod locking down their chunkgenerator.
             * They will handle your structure spacing for your if you add to WorldGenRegistries.NOISE_GENERATOR_SETTINGS in your structure's registration.
             * This here is done with reflection as this tutorial is not about setting up and using Mixins.
             * If you are using mixins, you can call the codec method with an invoker mixin instead of using reflection.
             */
            try {
                if(GETCODEC_METHOD == null) GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkSource().generator));
                if(cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
            }
            catch(Exception e){
                BarrenIsles.LOGGER.error("Was unable to check if " + serverWorld.dimension().location() + " is using Terraforged's ChunkGenerator.");
            }

            /*
             * Prevent spawning our structure in Vanilla's superflat world as
             * people seem to want their superflat worlds free of modded structures.
             * Also that vanilla superflat is really tricky and buggy to work with in my experience.
             */
            if(serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator &&
                    serverWorld.dimension().equals(World.OVERWORLD)){
                return;
            }

            /*
             * putIfAbsent so people can override the spacing with dimension datapacks themselves if they wish to customize spacing more precisely per dimension.
             * Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
             *
             * NOTE: if you add per-dimension spacing configs, you can't use putIfAbsent as WorldGenRegistries.NOISE_GENERATOR_SETTINGS in FMLCommonSetupEvent
             * already added your default structure spacing to some dimensions. You would need to override the spacing with .put(...)
             * And if you want to do dimension blacklisting, you need to remove the spacing entry entirely from the map below to prevent generation safely.
             */
            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
            tempMap.putIfAbsent(ModStructures.ROCK_1.get(), DimensionStructuresSettings.DEFAULTS.get(ModStructures.ROCK_1.get()));
            tempMap.putIfAbsent(ModStructures.ROCK_2.get(), DimensionStructuresSettings.DEFAULTS.get(ModStructures.ROCK_2.get()));
            tempMap.putIfAbsent(ModStructures.OASIS.get(), DimensionStructuresSettings.DEFAULTS.get(ModStructures.OASIS.get()));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
        }
    }
}
