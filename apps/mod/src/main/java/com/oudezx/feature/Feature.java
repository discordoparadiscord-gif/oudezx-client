package com.oudezx.feature;

public abstract class Feature {
    protected String name;
    protected boolean enabled = false;

    public Feature(String name) {
        this.name = name;
    }

    public abstract void update();

    public void onEnable() {}

    public void onDisable() {}

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;
        this.enabled = enabled;
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public String getName() {
        return name;
    }
}