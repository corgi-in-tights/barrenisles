//package ca.thecorgi.barrenisles.client.renderer;
//
//import ca.thecorgi.barrenisles.client.model.BuramaphuModel;
//import ca.thecorgi.barrenisles.entity.BuramaphuEntity;
//import net.minecraft.client.render.RenderLayer;
//import net.minecraft.client.render.VertexConsumer;
//import net.minecraft.client.render.VertexConsumerProvider;
//import net.minecraft.client.render.entity.EntityRendererFactory;
//import net.minecraft.client.util.math.MatrixStack;
//import net.minecraft.util.Identifier;
//import org.jetbrains.annotations.Nullable;
//import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
//
//public class BuramaphuRenderer extends GeoEntityRenderer<BuramaphuEntity> {
//    public BuramaphuRenderer(EntityRendererFactory.Context ctx) {
//        super(ctx, new BuramaphuModel());
//    }
//
//    @Override
//    public RenderLayer getRenderType(BuramaphuEntity animatable, float partialTicks, MatrixStack stack,
//                                     @Nullable VertexConsumerProvider renderTypeBuffer, @Nullable VertexConsumer vertexBuilder,
//                                     int packedLightIn, Identifier textureLocation) {
//        return RenderLayer.getEntityTranslucent(this.getTextureLocation(animatable));
//    }
//
//}