package ca.thecorgi.barrenisles.mixin.blocks;

import ca.thecorgi.barrenisles.client.renderer.QuicksandOverlay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGameOverlayRenderer.class)
public class BlockOverlayRendererMixin {

    @Inject(method = "renderOverlays",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameOverlayRenderer;getInWallBlockState(Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/block/BlockState;"),
            locals = LocalCapture.CAPTURE_FAILSOFT,
            cancellable = true)
    private static void barrenisles_blockOverlay(MinecraftClient minecraftClient, MatrixStack matrixStack, CallbackInfo ci, PlayerEntity playerEntity) {
        if (QuicksandOverlay.quicksandOverlay(playerEntity, new BlockPos(playerEntity.getPos()), matrixStack))
            ci.cancel();
    }
}