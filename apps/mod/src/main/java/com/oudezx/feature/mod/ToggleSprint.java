package com.oudezx.feature.mod;

import net.minecraft.client.MinecraftClient;
import com.oudezx.feature.Feature;

public class ToggleSprint extends Feature {
    private boolean sprintToggled = false;

    public ToggleSprint() {
        super("Toggle Sprint");
    }

    @Override
    public void update() {
        if (!enabled) return;
        
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.options.getSprintKey().wasPressed()) {
            sprintToggled = !sprintToggled;
            client.player.setSprinting(sprintToggled);
        }
    }
}