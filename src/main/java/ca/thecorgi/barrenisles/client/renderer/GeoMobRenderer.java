package ca.thecorgi.barrenisles.client.renderer;

// Change this to your package
//package [your package]

import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GeoMobRenderer<T extends MobEntity & IAnimatable> extends GeoEntityRenderer<T> {
    protected GeoMobRenderer(EntityRendererFactory.Context ctx, AnimatedGeoModel<T> modelProvider) {
        super(ctx, modelProvider);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
        Entity leashHolder = entity.getHoldingEntity();
        if (leashHolder != null) {
            this.method_4073(entity, partialTicks, stack, bufferIn, leashHolder);
        }
    }

    private <E extends Entity> void method_4073(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider provider, E holdingEntity) {
        matrices.push();
        Vec3d vec3d = holdingEntity.method_30951(tickDelta);
        double d = (double)(MathHelper.lerp(tickDelta, entity.bodyYaw, entity.prevBodyYaw) * 0.017453292F) + 1.5707963267948966D;
        Vec3d vec3d2 = entity.getLeashOffset();
        // entity x coords
        double e = Math.cos(d) * vec3d2.z + Math.sin(d) * vec3d2.x;
        double f = Math.sin(d) * vec3d2.z - Math.cos(d) * vec3d2.x;
        // player coords
        double g = MathHelper.lerp((double)tickDelta, entity.prevX, entity.getX()) + e;
        double h = MathHelper.lerp((double)tickDelta, entity.prevY, entity.getY()) + vec3d2.y;
        double i = MathHelper.lerp((double)tickDelta, entity.prevZ, entity.getZ()) + f;
        matrices.translate(e, vec3d2.y, f);
        // moar player coords
        float j = (float)(vec3d.x - g);
        float k = (float)(vec3d.y - h);
        float l = (float)(vec3d.z - i);
        VertexConsumer vertexConsumer = provider.getBuffer(RenderLayer.getLeash());
        Matrix4f matrix4f = matrices.peek().getModel();
        float n = MathHelper.fastInverseSqrt(j * j + l * l) * 0.025F / 2.0F;
        float o = l * n;
        float p = j * n;
        BlockPos blockPos = new BlockPos(entity.getCameraPosVec(tickDelta));
        BlockPos blockPos2 = new BlockPos(holdingEntity.getCameraPosVec(tickDelta));
        int q = this.getBlockLight(entity, blockPos);
        int r = holdingEntity.isOnFire() ? 15 : holdingEntity.world.getLightLevel(LightType.BLOCK, blockPos2);
        int s = entity.world.getLightLevel(LightType.SKY, blockPos);
        int t = entity.world.getLightLevel(LightType.SKY, blockPos2);

        int v;
        for(v = 0; v <= 24; ++v) {
            method_23187(vertexConsumer, matrix4f, j, k, l, q, r, s, t, 0.025F, 0.025F, o, p, v, false);
        }

        for(v = 24; v >= 0; --v) {
            method_23187(vertexConsumer, matrix4f, j, k, l, q, r, s, t, 0.025F, 0.0F, o, p, v, true);
        }

        matrices.pop();
    }

    private static void method_23187(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, int i, int j, int k, int l, float m, float n, float o, float p, int q, boolean bl) {
        float r = (float)q / 24.0F;
        int s = (int)MathHelper.lerp(r, (float)i, (float)j);
        int t = (int)MathHelper.lerp(r, (float)k, (float)l);
        int u = LightmapTextureManager.pack(s, t);
        float v = q % 2 == (bl ? 1 : 0) ? 0.7F : 1.0F;
        float w = 0.5F * v;
        float x = 0.4F * v;
        float y = 0.3F * v;
        float z = f * r;
        float aa = g > 0.0F ? g * r * r : g - g * (1.0F - r) * (1.0F - r);
        float ab = h * r;
        vertexConsumer.vertex(matrix4f, z - o, aa + n, ab + p).color(w, x, y, 1.0F).light(u).next();
        vertexConsumer.vertex(matrix4f, z + o, aa + m - n, ab - p).color(w, x, y, 1.0F).light(u).next();
    }
}