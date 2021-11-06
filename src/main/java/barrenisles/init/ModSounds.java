package barrenisles.init;

import barrenisles.core.BarrenIslesMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static barrenisles.api.sounds.BarrenIslesSounds.barren_night;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSounds {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BarrenIslesMod.MODID);
    
	static {
		barren_night = 
    		SOUNDS.register("barren_night",
    		() -> new SoundEvent(new ResourceLocation(BarrenIslesMod.MODID, "barren_night")));
	}
	
	public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }
}
