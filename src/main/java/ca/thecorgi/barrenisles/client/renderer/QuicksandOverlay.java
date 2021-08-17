package ca.thecorgi.barrenisles.client.renderer;

import ca.thecorgi.barrenisles.utils.registry.BlockRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;


public class QuicksandOverlay {
    private static final Identifier QUICKSAND_OVERLAY = new Identifier(ModID, "textures/misc/quicksand_overlay.png");
    public static boolean quicksandOverlay(PlayerEntity player, BlockPos pos, MatrixStack matrixStack) {

        if (player instanceof ClientPlayerEntity clientPlayerEntity && player.world.getBlockState(new BlockPos(player.getCameraPosVec(1))).getBlock() == BlockRegistry.QUICKSAND) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.enableTexture();
            RenderSystem.setShaderTexture(0, QUICKSAND_OVERLAY);
            BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
            float brightnessAtEyes = clientPlayerEntity.getBrightnessAtEyes();
            float textureAlpha = 0.42F;
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(brightnessAtEyes, brightnessAtEyes, brightnessAtEyes, textureAlpha);
            float modifiedYaw = -clientPlayerEntity.getYaw() / 64.0F;
            float modifiedPitch = clientPlayerEntity.getPitch() / 64.0F;
            Matrix4f matrix4f = matrixStack.peek().getModel();
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
            bufferBuilder.vertex(matrix4f, -1.0F, -1.0F, -0.5F).color(brightnessAtEyes, brightnessAtEyes, brightnessAtEyes, textureAlpha).texture(4.0F + modifiedYaw, 4.0F + modifiedPitch).next();
            bufferBuilder.vertex(matrix4f, 1.0F, -1.0F, -0.5F).color(brightnessAtEyes, brightnessAtEyes, brightnessAtEyes, textureAlpha).texture(0.0F + modifiedYaw, 4.0F + modifiedPitch).next();
            bufferBuilder.vertex(matrix4f, 1.0F, 1.0F, -0.5F).color(brightnessAtEyes, brightnessAtEyes, brightnessAtEyes, textureAlpha).texture(0.0F + modifiedYaw, 0.0F + modifiedPitch).next();
            bufferBuilder.vertex(matrix4f, -1.0F, 1.0F, -0.5F).color(brightnessAtEyes, brightnessAtEyes, brightnessAtEyes, textureAlpha).texture(4.0F + modifiedYaw, 0.0F + modifiedPitch).next();
            bufferBuilder.end();
            BufferRenderer.draw(bufferBuilder);
            RenderSystem.disableBlend();
            return true;
        }

        return false;
    }
}