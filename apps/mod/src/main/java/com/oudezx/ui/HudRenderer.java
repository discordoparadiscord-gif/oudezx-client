package com.oudezx.ui;

import net.minecraft.client.MinecraftClient;
import com.oudezx.OudezxClient;
import com.oudezx.feature.Feature;
import java.util.Map;

public class HudRenderer {
    private boolean showMenu = false;

    public void update(MinecraftClient client) {
        showMenu = OudezxClient.getFeatureManager().isMenuOpen();
    }

    public void render(MinecraftClient client, float tickDelta) {
        if (showMenu) {
            renderMenu(client);
        }

        // Render all enabled HUDs
        OudezxClient.getFeatureManager().getAllFeatures().values().forEach(feature -> {
            if (feature.isEnabled()) {
                feature.update();
            }
        });
    }

    private void renderMenu(MinecraftClient client) {
        // Render main menu UI
        // This would show all features with toggle buttons
    }
}