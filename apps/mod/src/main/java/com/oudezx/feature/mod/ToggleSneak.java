package com.oudezx.feature.mod;

import net.minecraft.client.MinecraftClient;
import com.oudezx.feature.Feature;

public class ToggleSneak extends Feature {
    private boolean sneakToggled = false;

    public ToggleSneak() {
        super("Toggle Sneak");
    }

    @Override
    public void update() {
        if (!enabled) return;
        
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.options.getSneakKey().wasPressed()) {
            sneakToggled = !sneakToggled;
            if (sneakToggled) {
                client.player.setSneaking(true);
            } else {
                client.player.setSneaking(false);
            }
        }
    }
}