package com.oudezx.feature;

import com.oudezx.feature.hud.*;
import com.oudezx.feature.mod.*;
import java.util.HashMap;
import java.util.Map;

public class FeatureManager {
    private final Map<String, Feature> features = new HashMap<>();
    private boolean menuOpen = false;

    public FeatureManager() {
        registerFeatures();
    }

    private void registerFeatures() {
        // HUD Features
        features.put("fps", new FpsHud());
        features.put("ping", new PingHud());
        features.put("cps", new CpsHud());
        features.put("coordinates", new CoordinatesHud());
        features.put("armor", new ArmorHud());
        features.put("potion", new PotionHud());
        features.put("keystrokes", new KeystrokesHud());
        features.put("scoreboard", new ScoreboardHud());

        // Mod Features
        features.put("fullbright", new FullBright());
        features.put("zoom", new Zoom());
        features.put("freelook", new Freelook());
        features.put("motion-blur", new MotionBlur());
        features.put("toggle-sprint", new ToggleSprint());
        features.put("toggle-sneak", new ToggleSneak());
        features.put("waypoints", new Waypoints());
    }

    public void toggleMenu() {
        menuOpen = !menuOpen;
    }

    public boolean isMenuOpen() {
        return menuOpen;
    }

    public void toggleFeature(String name) {
        Feature feature = features.get(name);
        if (feature != null) {
            feature.setEnabled(!feature.isEnabled());
        }
    }

    public Feature getFeature(String name) {
        return features.get(name);
    }

    public Map<String, Feature> getAllFeatures() {
        return new HashMap<>(features);
    }

    public void update() {
        features.values().forEach(Feature::update);
    }
}