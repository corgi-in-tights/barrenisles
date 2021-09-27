package barrenisles.client.model;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import barrenisles.common.entity.TumbleweedEntity;
import barrenisles.core.BarrenIslesMod;

public class TumbleweedModel extends AnimatedGeoModel<TumbleweedEntity> {
    @Override
    public ResourceLocation getAnimationFileLocation(TumbleweedEntity entity) {
        return new ResourceLocation(BarrenIslesMod.MODID, "animations/tumbleweed.animation.json");
    }

    @Override
    public ResourceLocation getModelLocation(TumbleweedEntity entity) {
        return new ResourceLocation(BarrenIslesMod.MODID, "geo/tumbleweed.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(TumbleweedEntity entity) {
        return new ResourceLocation(BarrenIslesMod.MODID, "textures/entity/tumbleweed.png");
    }
}