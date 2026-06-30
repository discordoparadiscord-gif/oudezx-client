package com.oudezx.feature.mod;

import net.minecraft.client.MinecraftClient;
import com.oudezx.feature.Feature;
import org.lwjgl.glfw.GLFW;

public class Zoom extends Feature {
    private double defaultFov = 70.0;
    private double zoomFov = 10.0;
    private boolean zoomed = false;

    public Zoom() {
        super("Zoom");
    }

    @Override
    public void update() {
        if (!enabled) return;
        
        MinecraftClient client = MinecraftClient.getInstance();
        
        // Check if zoom key is pressed (default: C)
        if (GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_C) == GLFW.GLFW_PRESS) {
            if (!zoomed) {
                defaultFov = client.options.getFov().getValue();
                client.options.getFov().setValue(zoomFov);
                zoomed = true;
            }
        } else {
            if (zoomed) {
                client.options.getFov().setValue(defaultFov);
                zoomed = false;
            }
        }
    }
}