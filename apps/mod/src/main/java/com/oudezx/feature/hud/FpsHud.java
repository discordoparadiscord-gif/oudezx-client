package com.oudezx.feature.hud;

import net.minecraft.client.MinecraftClient;

public class FpsHud extends Hud {
    private int fps = 0;
    private int tickCounter = 0;
    private int lastFps = 0;

    public FpsHud() {
        super("FPS");
        this.x = 5;
        this.y = 5;
        this.width = 60;
        this.height = 20;
    }

    @Override
    public void update() {
        if (!enabled) return;
        // FPS is calculated automatically by Minecraft
    }

    @Override
    public void render(MinecraftClient client, float tickDelta) {
        if (!enabled) return;
        
        fps = client.getCurrentFps();
        String text = "FPS: " + fps;
        
        // Render using RenderSystem
        // This will be implemented with proper rendering
    }
}