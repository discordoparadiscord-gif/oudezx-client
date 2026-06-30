package com.oudezx.feature.hud;

import net.minecraft.client.MinecraftClient;

public class ScoreboardHud extends Hud {
    public ScoreboardHud() {
        super("Scoreboard");
        this.x = 5;
        this.y = 5;
        this.width = 160;
        this.height = 200;
    }

    @Override
    public void update() {
        // Scoreboard updates automatically
    }

    @Override
    public void render(MinecraftClient client, float tickDelta) {
        if (!enabled) return;
        
        // Get scoreboard objective
        if (client.world != null && client.world.getScoreboard() != null) {
            // Render scoreboard
        }
    }
}