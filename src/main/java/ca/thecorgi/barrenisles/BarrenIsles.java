package ca.thecorgi.barrenisles;

import ca.thecorgi.barrenisles.registry.EntityRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import software.bernie.example.EntityUtils;
import software.bernie.geckolib3.GeckoLib;

public class BarrenIsles implements ModInitializer {
    public static String ModID = "barrenisles";

    @Override
    public void onInitialize() {
        GeckoLib.initialize();
            new EntityRegistry();
            FabricDefaultAttributeRegistry.register(EntityRegistry.DUNERAPTOR_ENTITY,
                    EntityUtils.createGenericEntityAttributes());

    }
}
