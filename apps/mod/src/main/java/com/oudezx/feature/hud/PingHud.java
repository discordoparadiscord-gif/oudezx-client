package com.oudezx.feature.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;

public class PingHud extends Hud {
    private int ping = 0;

    public PingHud() {
        super("Ping");
        this.x = 5;
        this.y = 25;
        this.width = 60;
        this.height = 20;
    }

    @Override
    public void update() {
        if (!enabled) return;
        
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.getNetworkHandler() != null) {
            ClientPlayNetworkHandler handler = client.getNetworkHandler();
            ping = handler.getPlayerListEntry(client.player.getUuid()).getLatency();
        }
    }

    @Override
    public void render(MinecraftClient client, float tickDelta) {
        if (!enabled) return;
        String text = "Ping: " + ping + "ms";
        // Render text
    }
}