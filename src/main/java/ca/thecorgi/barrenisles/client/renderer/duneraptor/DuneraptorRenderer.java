package ca.thecorgi.barrenisles.client.renderer.duneraptor;

import ca.thecorgi.barrenisles.client.model.DuneraptorModel;
import ca.thecorgi.barrenisles.client.renderer.GeoMobRenderer;
import ca.thecorgi.barrenisles.entity.DuneraptorEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;

public class DuneraptorRenderer extends GeoMobRenderer<DuneraptorEntity> {
    public DuneraptorRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new DuneraptorModel());
    }

    @Override
    public RenderLayer getRenderType(DuneraptorEntity animatable, float partialTicks, MatrixStack stack,
                                     @Nullable VertexConsumerProvider renderTypeBuffer, @Nullable VertexConsumer vertexBuilder,
                                     int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(this.getTextureLocation(animatable));
    }

    @Override
    public void render(GeoModel model, DuneraptorEntity animatable, float partialTicks, RenderLayer type,
                       MatrixStack matrixStackIn, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
                       int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (animatable.isSaddled()) {
                model.getBone("saddle").get().setHidden(false);
        } else {
                model.getBone("saddle").get().setHidden(true);
        }
        super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder,
                packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}