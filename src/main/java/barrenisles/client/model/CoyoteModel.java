package barrenisles.client.model;

import barrenisles.common.entity.CoyoteEntity;
import barrenisles.core.BarrenIslesMod;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class CoyoteModel extends AnimatedGeoModel<CoyoteEntity> {
	
    @Override
    public ResourceLocation getAnimationFileLocation(CoyoteEntity entity) {
        return new ResourceLocation(BarrenIslesMod.MODID, "animations/coyote.animation.json");
    }

    @Override
    public ResourceLocation getModelLocation(CoyoteEntity entity) {
        return new ResourceLocation(BarrenIslesMod.MODID, "geo/coyote.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(CoyoteEntity entity) {
        return new ResourceLocation(BarrenIslesMod.MODID, "textures/entity/coyote/coyote.png");
    }

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