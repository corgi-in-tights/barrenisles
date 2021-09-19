package de.xyndra.barrenisles.entitys.duneraptor;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DuneraptorModel extends AnimatedGeoModel<DuneraptorEntity> {
    @Override
    public ResourceLocation getAnimationFileLocation(DuneraptorEntity entity) {
        return new ResourceLocation("barrenisles", "animations/duneraptor.animation.json");
    }

    @Override
    public ResourceLocation getModelLocation(DuneraptorEntity entity) {
        return new ResourceLocation("barrenisles", "geo/duneraptor.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(DuneraptorEntity entity) {
        return new ResourceLocation("barrenisles", "textures/model/entity/duneraptor.png");
    }
}