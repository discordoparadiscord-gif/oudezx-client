package com.oudezx.feature.mod;

import net.minecraft.client.MinecraftClient;
import com.oudezx.feature.Feature;
import org.lwjgl.glfw.GLFW;

public class Freelook extends Feature {
    private boolean freelooking = false;
    private float lastYaw = 0.0f;
    private float lastPitch = 0.0f;

    public Freelook() {
        super("Freelook");
    }

    @Override
    public void update() {
        if (!enabled) return;
        
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        
        // Check if freelook key is pressed (default: F)
        if (GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_F) == GLFW.GLFW_PRESS) {
            if (!freelooking) {
                lastYaw = client.player.getYaw();
                lastPitch = client.player.getPitch();
                freelooking = true;
            }
        } else {
            if (freelooking) {
                client.player.setYaw(lastYaw);
                client.player.setPitch(lastPitch);
                freelooking = false;
            }
        }
    }
}