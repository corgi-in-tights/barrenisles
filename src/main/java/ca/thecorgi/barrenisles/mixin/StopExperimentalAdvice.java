package ca.thecorgi.barrenisles.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static ca.thecorgi.barrenisles.BarrenIsles.config;

@Mixin(MinecraftClient.class)
public abstract class StopExperimentalAdvice {
        // taken from https://www.curseforge.com/minecraft/mc-mods/fabric-disable-custom-worlds-advice
        @ModifyVariable(
                method = "startIntegratedServer(Ljava/lang/String;Lnet/minecraft/util/registry/DynamicRegistryManager$Impl;Ljava/util/function/Function;Lcom/mojang/datafixers/util/Function4;ZLnet/minecraft/client/MinecraftClient$WorldLoadAction;)V",
                at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient$WorldLoadAction;NONE:Lnet/minecraft/client/MinecraftClient$WorldLoadAction;", ordinal = 0),
                ordinal = 2, index = 11, name = "experimentalAdvice", require = 1
        )
        private boolean noExperimentalAdvice ( boolean experimentalAdvice){
            if (config.client.stop_experimental_advice == true) {
                return false;
            } else {
                return true;
            }
    }
}