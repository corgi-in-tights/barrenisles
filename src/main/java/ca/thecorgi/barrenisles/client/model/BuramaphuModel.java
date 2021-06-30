package ca.thecorgi.barrenisles.client.model;

import ca.thecorgi.barrenisles.entity.BuramaphuEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;

public class BuramaphuModel extends AnimatedGeoModel<BuramaphuEntity> {
    @Override
    public Identifier getAnimationFileLocation(BuramaphuEntity entity) {
        return new Identifier(ModID, "animations/buramaphu.animation.json");
    }

    @Override
    public Identifier getModelLocation(BuramaphuEntity entity) {
        return new Identifier(ModID, "geo/buramaphu.geo.json");
    }

    @Override
    public Identifier getTextureLocation(BuramaphuEntity entity) {
        return new Identifier(ModID, "textures/model/entity/buramaphu.png");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(BuramaphuEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}