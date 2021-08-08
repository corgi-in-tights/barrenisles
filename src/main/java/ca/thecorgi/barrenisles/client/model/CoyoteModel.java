package ca.thecorgi.barrenisles.client.model;

import ca.thecorgi.barrenisles.entity.CoyoteEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;

public class CoyoteModel extends AnimatedGeoModel<CoyoteEntity> {
    @Override
    public Identifier getAnimationFileLocation(CoyoteEntity entity) {
        return new Identifier(ModID, "animations/coyote.animation.json");
    }

    @Override
    public Identifier getModelLocation(CoyoteEntity entity) {
        return new Identifier(ModID, "geo/coyote.geo.json");
    }

    @Override
    public Identifier getTextureLocation(CoyoteEntity entity) {
        return new Identifier(ModID, "textures/entity/coyote/coyote.png");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(CoyoteEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}