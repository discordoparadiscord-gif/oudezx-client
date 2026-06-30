package com.oudezx.feature.mod;

import com.oudezx.feature.Feature;

public class MotionBlur extends Feature {
    private float blurAmount = 0.5f;

    public MotionBlur() {
        super("Motion Blur");
    }

    @Override
    public void update() {
        // Motion blur would require shader modifications
        // This is a placeholder for the feature
    }

    public float getBlurAmount() {
        return blurAmount;
    }

    public void setBlurAmount(float amount) {
        this.blurAmount = Math.max(0, Math.min(1, amount));
    }
}