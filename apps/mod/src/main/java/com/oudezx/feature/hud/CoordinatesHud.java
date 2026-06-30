package com.oudezx.feature.hud;

import net.minecraft.client.MinecraftClient;

public class CoordinatesHud extends Hud {
    public CoordinatesHud() {
        super("Coordinates");
        this.x = 5;
        this.y = 65;
        this.width = 150;
        this.height = 60;
    }

    @Override
    public void update() {
        // Coordinates update automatically
    }

    @Override
    public void render(MinecraftClient client, float tickDelta) {
        if (!enabled || client.player == null) return;
        
        int x = (int) client.player.getX();
        int y = (int) client.player.getY();
        int z = (int) client.player.getZ();
        float yaw = client.player.getYaw();
        float pitch = client.player.getPitch();
        
        String coords = String.format(
            "X: %d Y: %d Z: %d\nYaw: %.1f° Pitch: %.1f°",
            x, y, z, yaw, pitch
        );
        // Render text
    }
}