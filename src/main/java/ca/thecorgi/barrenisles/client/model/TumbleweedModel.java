package ca.thecorgi.barrenisles.client.model;

import ca.thecorgi.barrenisles.entity.TumbleweedEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;

public class TumbleweedModel extends AnimatedGeoModel<TumbleweedEntity> {
    @Override
    public Identifier getAnimationFileLocation(TumbleweedEntity entity) {
        return new Identifier(ModID, "animations/tumbleweed.animation.json");
    }

    @Override
    public Identifier getModelLocation(TumbleweedEntity entity) {
        return new Identifier(ModID, "geo/tumbleweed.geo.json");
    }

    @Override
    public Identifier getTextureLocation(TumbleweedEntity entity) {
        return new Identifier(ModID, "textures/entity/tumbleweed.png");
    }

//    @SuppressWarnings({ "unchecked", "rawtypes" })
//    @Override
//    public void setLivingAnimations(TumbleweedEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
//        super.setLivingAnimations(entity, uniqueID, customPredicate);
//        IBone head = this.getAnimationProcessor().getBone("root");
//
//        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
//        if (head != null) {
//            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
//            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
//        }
//    }
}