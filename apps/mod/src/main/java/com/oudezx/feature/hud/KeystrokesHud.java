package com.oudezx.feature.hud;

import net.minecraft.client.MinecraftClient;

public class KeystrokesHud extends Hud {
    private boolean w = false, a = false, s = false, d = false;
    private boolean space = false, lshift = false;

    public KeystrokesHud() {
        super("Keystrokes");
        this.x = 10;
        this.y = 260;
        this.width = 150;
        this.height = 80;
    }

    @Override
    public void update() {
        if (!enabled) return;
        
        MinecraftClient client = MinecraftClient.getInstance();
        // Get key states from input handler
        // Update: w, a, s, d, space, lshift
    }

    @Override
    public void render(MinecraftClient client, float tickDelta) {
        if (!enabled) return;
        
        // Render keyboard layout
        // W A S D keys in grid with SPACE and SHIFT below
    }
}