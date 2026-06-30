package com.oudezx.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.render.GameRenderer;

@Mixin(GameRenderer.class)
public class ClientMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(float tickDelta, long nanoTime, boolean tick, CallbackInfo ci) {
        // Hook into render cycle for custom rendering
    }
}