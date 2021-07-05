package ca.thecorgi.barrenisles.client.renderer;

import ca.thecorgi.barrenisles.client.model.DuneraptorModel;
import ca.thecorgi.barrenisles.entity.DuneraptorEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DuneraptorRenderer extends GeoEntityRenderer<DuneraptorEntity> {
    public DuneraptorRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new DuneraptorModel());
    }

    @Override
    public RenderLayer getRenderType(DuneraptorEntity animatable, float partialTicks, MatrixStack stack,
                                     @Nullable VertexConsumerProvider renderTypeBuffer, @Nullable VertexConsumer vertexBuilder,
                                     int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(this.getTextureLocation(animatable));
    }
}