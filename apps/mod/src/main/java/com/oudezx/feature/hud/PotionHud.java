package com.oudezx.feature.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

public class PotionHud extends Hud {
    public PotionHud() {
        super("Potion Effects");
        this.x = 10;
        this.y = 155;
        this.width = 200;
        this.height = 100;
    }

    @Override
    public void update() {
        // Potion effects update automatically
    }

    @Override
    public void render(MinecraftClient client, float tickDelta) {
        if (!enabled || client.player == null) return;
        
        int yOffset = 0;
        for (StatusEffectInstance effect : client.player.getStatusEffects()) {
            String text = effect.getEffectType().getName().getString() + " " + (effect.getAmplifier() + 1);
            String duration = String.format("%.1fs", effect.getDuration() / 20.0);
            // Render effect
            yOffset += 15;
        }
    }
}