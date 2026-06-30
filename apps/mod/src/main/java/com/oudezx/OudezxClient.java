package com.oudezx;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import com.oudezx.feature.FeatureManager;
import com.oudezx.ui.HudRenderer;
import com.oudezx.network.ApiClient;

public class OudezxClient implements ClientModInitializer {
    public static final String MOD_ID = "oudezx-client";
    public static final String MOD_NAME = "Oudezx Client";

    private static KeyBinding menuKey;
    private static FeatureManager featureManager;
    private static HudRenderer hudRenderer;
    private static ApiClient apiClient;

    @Override
    public void onInitializeClient() {
        System.out.println("[" + MOD_ID + "] Initializing Oudezx Client...");

        // Initialize managers
        featureManager = new FeatureManager();
        hudRenderer = new HudRenderer();
        apiClient = new ApiClient();

        // Register key bindings
        registerKeyBindings();

        // Register tick events
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                handleMenuKey();
                hudRenderer.update(client);
            }
        });

        System.out.println("[" + MOD_ID + "] Initialization complete!");
    }

    private static void registerKeyBindings() {
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.oudezx.menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.oudezx.main"
        ));
    }

    private static void handleMenuKey() {
        while (menuKey.wasPressed()) {
            featureManager.toggleMenu();
        }
    }

    public static FeatureManager getFeatureManager() {
        return featureManager;
    }

    public static HudRenderer getHudRenderer() {
        return hudRenderer;
    }

    public static ApiClient getApiClient() {
        return apiClient;
    }
}