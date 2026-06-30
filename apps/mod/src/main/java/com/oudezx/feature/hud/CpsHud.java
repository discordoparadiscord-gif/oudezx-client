package com.oudezx.feature.hud;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class CpsHud extends Hud {
    private int leftClicks = 0;
    private int rightClicks = 0;
    private long lastLeftClickTime = 0;
    private long lastRightClickTime = 0;
    private int leftCps = 0;
    private int rightCps = 0;

    public CpsHud() {
        super("CPS");
        this.x = 5;
        this.y = 45;
        this.width = 100;
        this.height = 20;
    }

    @Override
    public void update() {
        if (!enabled) return;
        
        MinecraftClient client = MinecraftClient.getInstance();
        long now = System.currentTimeMillis();
        
        // Check mouse buttons
        if (GLFW.glfwGetMouseButton(client.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS) {
            if (now - lastLeftClickTime > 50) {
                leftClicks++;
                lastLeftClickTime = now;
            }
        }
        
        if (GLFW.glfwGetMouseButton(client.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS) {
            if (now - lastRightClickTime > 50) {
                rightClicks++;
                lastRightClickTime = now;
            }
        }
    }

    @Override
    public void render(MinecraftClient client, float tickDelta) {
        if (!enabled) return;
        String text = "CPS L: " + leftCps + " R: " + rightCps;
        // Render text
    }
}