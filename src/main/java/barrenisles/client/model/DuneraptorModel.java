package barrenisles.client.model;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import barrenisles.common.entity.DuneraptorEntity;
import barrenisles.core.BarrenIslesMod;

public class DuneraptorModel extends AnimatedGeoModel<DuneraptorEntity> {
    @Override
    public ResourceLocation getAnimationFileLocation(DuneraptorEntity entity) {
        return new ResourceLocation(BarrenIslesMod.MODID, "animations/duneraptor.animation.json");
    }

    @Override
    public ResourceLocation getModelLocation(DuneraptorEntity entity) {
        return new ResourceLocation(BarrenIslesMod.MODID, "geo/duneraptor.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(DuneraptorEntity entity) {
        return new ResourceLocation(BarrenIslesMod.MODID, "textures/entity/duneraptor.png");
    }

    @Override
    public void setLivingAnimations(DuneraptorEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}