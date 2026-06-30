package com.oudezx.feature.hud;

import net.minecraft.client.MinecraftClient;
import com.oudezx.feature.Feature;

public abstract class Hud extends Feature {
    protected int x = 0;
    protected int y = 0;
    protected int width = 100;
    protected int height = 20;

    public Hud(String name) {
        super(name);
    }

    public abstract void render(MinecraftClient client, float tickDelta);

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}