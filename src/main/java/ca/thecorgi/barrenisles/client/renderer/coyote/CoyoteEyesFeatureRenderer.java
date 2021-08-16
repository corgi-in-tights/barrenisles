package ca.thecorgi.barrenisles.client.renderer.coyote;

import ca.thecorgi.barrenisles.entity.CoyoteEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.LightType;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;

public class CoyoteEyesFeatureRenderer extends GeoLayerRenderer<CoyoteEntity> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(new Identifier(ModID, "textures/entity/coyote/coyote_eyes.png"));

    private final IGeoRenderer<CoyoteEntity> renderer;

    public CoyoteEyesFeatureRenderer(IGeoRenderer<CoyoteEntity> entityRendererIn) {
        super(entityRendererIn);
        this.renderer = entityRendererIn;
    }

    protected boolean isHungry(CoyoteEntity entity) {
        int x = entity.world.getLightLevel(LightType.SKY, entity.getBlockPos()) - entity.world.getAmbientDarkness();
//        if (x > 13000 && x < 24000) {
        System.out.println(x);
        if (x > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, CoyoteEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexConsumer = bufferIn.getBuffer(SKIN);
        if (isHungry(entitylivingbaseIn)) {
            renderer.render(
                    getEntityModel().getModel(getEntityModel().getModelLocation(entitylivingbaseIn)),
                    entitylivingbaseIn,
                    partialTicks,
                    SKIN,
                    matrixStackIn,
                    bufferIn,
                    vertexConsumer,
                    15728640,
                    OverlayTexture.DEFAULT_UV,
                    1.0F, 1.0F, 1.0F, 1.0F
            );
        }
    }

}