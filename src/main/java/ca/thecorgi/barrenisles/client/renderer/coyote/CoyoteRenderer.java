package ca.thecorgi.barrenisles.client.renderer.coyote;

import ca.thecorgi.barrenisles.client.model.CoyoteModel;
import ca.thecorgi.barrenisles.entity.CoyoteEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CoyoteRenderer extends GeoEntityRenderer<CoyoteEntity> {
    public CoyoteRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new CoyoteModel());
        this.addLayer(new CoyoteEyesFeatureRenderer(this));
    }

    @Override
    public RenderLayer getRenderType(CoyoteEntity animatable, float partialTicks, MatrixStack stack,
                                     @Nullable VertexConsumerProvider renderTypeBuffer, @Nullable VertexConsumer vertexBuilder,
                                     int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(this.getTextureLocation(animatable));
    }
}