package com.oudezx.feature.mod;

import net.minecraft.client.MinecraftClient;
import com.oudezx.feature.Feature;

public class FullBright extends Feature {
    public FullBright() {
        super("FullBright");
    }

    @Override
    public void update() {
        if (!enabled) return;
        
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            // Set gamma to max
            client.options.getGamma().setValue(16.0);
        }
    }

    @Override
    public void onDisable() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            // Reset gamma to default
            client.options.getGamma().setValue(1.0);
        }
    }
}